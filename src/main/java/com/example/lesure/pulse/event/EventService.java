package com.example.lesure.pulse.event;

import com.example.lesure.api.seatgreek.service.SeatgeekRestApiClient;
import com.example.lesure.domain.constants.EventType;
import com.example.lesure.pulse.event.models.Event;
import com.example.lesure.pulse.comment.CommentService;
import com.example.lesure.pulse.event.models.EventData;
import com.example.lesure.pulse.event.models.EventDto;
import com.example.lesure.pulse.reaction.ReactionService;
import com.example.lesure.pulse.reaction.model.ReactionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

    private final SeatgeekRestApiClient seatgeekRestApiClient;
    private final EventRepository eventRepository;
    private final CommentService commentService;
    private final ReactionService reactionService;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::toDtoWithReactions)
                .toList();

    }

    @Transactional(readOnly = true)
    public EventDto getEventById(Long id) {
        EventDto eventDto = eventRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
        if (eventDto != null) {
            eventDto.setComments(commentService.getAllCommentByEventId(id));
        }
        return eventDto;
    }

    @Transactional(readOnly = true)
    public List<EventDto> searchByName(String name) {
        return eventRepository.findByNameContainingIgnoreCase(name).stream().map(this::toDtoWithReactions).toList();
    }

    @Transactional(readOnly = true)
    public Page<EventDto> getAllByEventType(EventType eventType, Pageable pageable) {
        return eventRepository.findAllByEventType(eventType, pageable)
                .map(this::toDtoWithReactions);
    }

    @Transactional
    public void createEvent(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setEventType(eventDto.getEventType());
        event.setImageUrl(eventDto.getImageUrl());
        event.setLocation(eventDto.getLocation());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        eventRepository.save(event);
    }

    @Transactional
    public void getEvents() throws IOException {
        String send = seatgeekRestApiClient.send(
                HttpMethod.GET,
                "/events",
                null,
                String.class
        );
        fetchAndParseEvents(send);
    }

    @Transactional
    public void createEvents(List<Event> event) {
        eventRepository.saveAll(event);
    }

    @Transactional
    public void updateEvent(EventDto eventDto, Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setEventType(eventDto.getEventType());
        event.setImageUrl(eventDto.getImageUrl());
        event.setLocation(eventDto.getLocation());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public void fetchAndParseEvents(String json) throws IOException {
        EventData data = objectMapper.readValue(json, EventData.class);

        List<Event> events = new ArrayList<>();
        List<Map<String, Object>> eventsList = (List<Map<String, Object>>) data.getUnknownFields().get("events");
        if (eventsList != null) {
            for (Map<String, Object> eventMap : eventsList) {
                Event event = new Event();
                event.setName((String) eventMap.get("title"));
                event.setDescription((String) eventMap.get("description"));

                // Витягуємо та встановлюємо місце проведення івенту
                Map<String, Object> venue = (Map<String, Object>) eventMap.get("venue");
                event.setLocation(venue.get("name") + ", " + venue.get("city"));

                // Витягуємо та перетворюємо дату на LocalDate
                String dateTimeUtc = (String) eventMap.get("datetime_utc");
                LocalDate eventDate = LocalDate.parse(dateTimeUtc, DateTimeFormatter.ISO_DATE_TIME);
                event.setDate(eventDate);

                // Витягуємо та встановлюємо зображення івенту
                List<Map<String, Object>> performers = (List<Map<String, Object>>) eventMap.get("performers");
                if (performers != null) {
                    for (Map<String, Object> performer : performers) {
                        if ((boolean) performer.get("primary")) {
                            event.setImageUrl((String) performer.get("image"));
                            break;
                        }
                    }
                }
                event.setEventType(EventType.valueOf(((List<Map<String, String>>) eventMap.get("taxonomies")).get(0).get("name").toUpperCase()));

                event.setVenueCapacity((Integer) venue.get("capacity"));
                event.setCreatedAt(LocalDateTime.now());
                event.setUpdatedAt(LocalDateTime.now());
                events.add(event);
            }
        }
        createEvents(events);
    }

    private EventDto toDtoWithReactions(Event event) {
        EventDto eventDto = toDto(event);
        ReactionResponse reactionResponse = reactionService.getReactionCountsByEventId(event.getId());
        eventDto.setReactionResponse(reactionResponse);
        return eventDto;
    }

    @Transactional(readOnly = true)
    public List<EventDto> getEventsByMonthAndYear(int month, int year) {
        return eventRepository.findByMonthAndYear(month, year).stream().map(this::toDto).toList();
    }

    public EventDto toDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setPrice(event.getPrice());
        eventDto.setId(event.getId());
        eventDto.setDate(event.getDate());
        eventDto.setDescription(event.getDescription());
        eventDto.setEventType(event.getEventType());
        eventDto.setLocation(event.getLocation());
        eventDto.setName(event.getName());
        eventDto.setImageUrl(event.getImageUrl());
        return eventDto;
    }
}
