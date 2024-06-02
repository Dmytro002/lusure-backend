package com.example.lesure.subscription;

import com.example.lesure.user.model.User;
import com.example.lesure.pulse.event.models.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    @PostMapping("/{eventId}")
    public void subscription(@PathVariable Long eventId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        subscriptionService.subscribe(eventId, user.getId());
    }

    @GetMapping
    public List<EventDto> getSubscribedEvents(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return subscriptionService.getEventsSubscribedByUser(user.getId());
    }

    @GetMapping("/{eventId}/status")
    public Boolean getSubscription(@PathVariable Long eventId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return subscriptionService.getSubscription(eventId, user.getId());
    }

    @DeleteMapping("/{subId}")
    public void deleteSubscription(@PathVariable Long subId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        subscriptionService.deleteSubscription(subId, user.getId());
    }
}
