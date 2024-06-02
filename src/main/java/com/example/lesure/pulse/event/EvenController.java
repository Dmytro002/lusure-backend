package com.example.lesure.pulse.event;

import com.example.lesure.domain.constants.EventType;
import com.example.lesure.pulse.event.models.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EvenController {

    private final EventService eventService;

    @GetMapping("/get-data")
    public void createEvents() throws IOException {
        eventService.getEvents();
    }

    @GetMapping("/{id}")
    public EventDto getById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public void createEvent(@RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto);
    }

    @PutMapping("/{eventId}")
    public void updateEvent(@RequestBody EventDto eventDto, @PathVariable Long eventId) {
        eventService.updateEvent(eventDto,eventId);
    }

    @GetMapping("/by-month-year")
    public List<EventDto> getEventsByMonthAndYear(@RequestParam int month, @RequestParam int year) {
        return eventService.getEventsByMonthAndYear(month, year);
    }

    @GetMapping("/search")
    public List<EventDto> searchEventsByName(@RequestParam String name) {
        return eventService.searchByName(name);
    }

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/type/{eventType}")
    public Page<EventDto> getEventsByEventType(@PathVariable EventType eventType,
                                               Pageable pageable) {
        return eventService.getAllByEventType(eventType, pageable);
    }
}
