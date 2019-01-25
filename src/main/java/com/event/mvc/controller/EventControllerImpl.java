package com.event.mvc.controller;

import com.event.mvc.entity.Event;
import com.event.mvc.entity.EventType;
import com.event.mvc.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class EventControllerImpl{
    private EventService eventService;

    public EventControllerImpl(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String eventsView(Model model) {
        model.addAttribute("event", new Event());
        return "events";
    }

    @ModelAttribute("allEvents")
    public List<Event> allEvents() {
        return eventService.findAll();
    }

    @ModelAttribute("allEventTypes")
    public List<EventType> eventTypes() {
        return Arrays.asList(EventType.values());
    }

    public String findEventById(Long id) {
        return null;
    }

    @PostMapping("/events")
    public String createEvent(@Valid @ModelAttribute("event") Event event, Model model) {
        eventService.insertEvent(event);
        model.addAttribute("event", event);

        return "events";
    }

    public void updateEvent(Long id, Event event) {

    }

    public void deleteEvent(Long id, Event event) {

    }
}
