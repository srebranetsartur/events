package com.event.mvc.controller;

import com.event.mvc.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.event.mvc.service.EventService;
import org.thymeleaf.spring5.view.ThymeleafView;

import java.util.List;

public class RestEventControllerImpl{
    private EventService eventService;
    private static Logger logger = LoggerFactory.getLogger(RestEventControllerImpl.class);

    public RestEventControllerImpl(EventService eventService) {
        this.eventService = eventService;
    }



    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/events")
    @ModelAttribute(name= "allEvents")
    public List<Event> allEvents() {
        List<Event> events = eventService.findAll();
        logger.info("Getting all event list");
        return events;
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String findEventById(@PathVariable Long id, Model model) {
        Event event = eventService.findEventById(id);
        logger.info("Getting a event with ID:" + event.getId());
        return "Hello";
    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event event) {
        eventService.insertEvent(event);
        logger.info("Event ID: " + event.getId() + "successfully inserted");
        return event;
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(@PathVariable Long id, Event event) {
        eventService.updateEvent(event);
        logger.info("Event ID: " + event.getId() + "was updated");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id")
    public void deleteEvent(@PathVariable Long id, Event event) {
        eventService.deleteEvent(event);
        logger.info("Event ID: " + event.getId() + "was deleted");
    }

    @Bean
    @Scope("prototype")
    public ThymeleafView eventsView() {
        ThymeleafView view = new ThymeleafView("events");
        return view;
    }
}
