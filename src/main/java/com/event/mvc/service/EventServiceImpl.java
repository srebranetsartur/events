package com.event.mvc.service;

import com.event.mvc.dao.EventDAO;
import com.event.mvc.entity.Event;
import com.event.mvc.entity.EventType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private EventDAO eventDAO;

    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public Event findEventById(Long eventId) {
        return eventDAO.findEventById(eventId);
    }

    @Override
    public Event insertEvent(Event event) {
        return eventDAO.insertEvent(event);
    }

    @Override
    public Event updateEvent(Event newEvent) {
        return eventDAO.updateEvent(newEvent);
    }

    @Override
    public void deleteEvent(Event event) {
        eventDAO.deleteEvent(event);
    }

    @Override
    public List<Event> findAll() {
        return eventDAO.findAll();
    }

    @Override
    public List<Event> findEventByType(EventType type) {
        return eventDAO.findEventByType(type);
    }

    @Override
    public List<Event> findTodayEvent() {
        return eventDAO.findTodayEvent();
    }

    @Override
    public List<Event> findTomorrowEvent() {
        return eventDAO.findTomorrowEvent();
    }

    @Override
    public List<Event> findWeekendEvent() {
        return eventDAO.findWeekendEvent();
    }

    @Override
    public List<Event> findMonthEvent() {
        return eventDAO.findMonthEvent();
    }

    @Override
    public List<Event> findEventByDate(LocalDateTime date) {
        return eventDAO.findEventByDate(date);
    }

    @Override
    public void deleteAll() {
        eventDAO.deleteAll();
    }

}
