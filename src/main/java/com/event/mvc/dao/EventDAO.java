package com.event.mvc.dao;

import com.event.mvc.entity.Event;
import com.event.mvc.entity.EventType;

import java.time.LocalDateTime;
import java.util.List;

public interface EventDAO {
    Event findEventById(Long eventId);

    Event insertEvent(Event event);

    Event updateEvent(Event newEvent);

    Event deleteEvent(Event eventID);

    List<Event> findAll();

    List<Event> findEventByType(EventType type);

    List<Event> findTodayEvent();

    List<Event> findTomorrowEvent();

    List<Event> findWeekendEvent();

    List<Event> findMonthEvent();

    List<Event> findEventByDate(LocalDateTime date);

    void deleteAll();
}

