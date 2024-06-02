package com.example.lesure.subscription;

import com.example.lesure.pulse.event.EventRepository;
import com.example.lesure.pulse.event.EventService;
import com.example.lesure.pulse.event.models.EventDto;
import com.example.lesure.subscription.email.EmailService;
import com.example.lesure.subscription.models.Subscription;
import com.example.lesure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;
    private final EmailService emailService;

    @Transactional
    public void subscribe(Long eventId, Long userId) {
        Subscription subscription = new Subscription();
        subscription.setEvent(eventRepository.getReferenceById(eventId));
        subscription.setUser(userRepository.getReferenceById(userId));
        Subscription sub = subscriptionRepository.save(subscription);
        emailService.prepareAndSendEmail(sub);
    }

    @Transactional(readOnly = true)
    public List<EventDto> getEventsSubscribedByUser(Long userId) {
        return subscriptionRepository.findEventsByUserId(userId).stream()
                .map(eventService::toDto)
                .toList();
    }
    @Transactional(readOnly = true)
    public Boolean getSubscription(Long eventId, Long userId) {
        return subscriptionRepository.isUserSubscribedToEvent(userId, eventId);
    }

    @Transactional
    public void deleteSubscription(Long subId, Long userId) {
        subscriptionRepository.deleteByEventIdAndUserId(subId,userId);
    }



}
