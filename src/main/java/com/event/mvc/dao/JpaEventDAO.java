package com.event.mvc.dao;

import com.event.mvc.entity.Event;
import com.event.mvc.entity.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Repository("eventDAO")
@Transactional
@SqlResultSetMapping(name = "eventMapping", entities = @EntityResult(entityClass = Event.class))
public class JpaEventDAO implements EventDAO {
    protected EntityManager entityManager;

    private static Logger logger = LoggerFactory.getLogger(JpaEventDAO.class);

    private static final String FIND_ALL = "findAll";
    private static final String FIND_BY_DATE = "findByDate";
    private static final String DELETE_ALL = "delete_all";

    public JpaEventDAO() {
    }

    @Autowired(required = false)
    public JpaEventDAO(Logger logger) {
        this.logger = logger;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional(readOnly = true)
    public Event findEventById(Long eventId) {
        Event event = entityManager.find(Event.class, eventId);
        return event;
    }

    @Override
    @Transactional
    public Event insertEvent(Event event) throws NullPointerException {
        entityManager.persist(event);
        logger.info("Entity successfully insert with ID: " + event.getId());
        return event;
    }

    @Override
    @Transactional
    public Event updateEvent(Event newEvent) {
        entityManager.merge(newEvent);
        logger.info(String.format("Entity with ID: %d was updated", newEvent.getId()));
        return newEvent;
    }

    @Override
    @Transactional
    public Event deleteEvent(Event event) {
        entityManager.detach(event);
        logger.info(String.format("Entity %s was successfully deleted", event.toString()));

        return event;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findAll() {
        List<Event> resultList = entityManager.createNamedQuery(FIND_ALL, Event.class).getResultList();
        logger.info(String.format("Find a %d event com.event.com.event.mvc.entity", resultList.size()));
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findEventByType(EventType type) {
        List<Event> resultList = entityManager
                .createQuery("SELECT e from Event e WHERE e.type = :type", Event.class)
                .setParameter("type", type).getResultList();
        logger.info(String.format("Find a %d event com.event.com.event.mvc.entity", resultList.size()));
        return resultList;
    }

    @Override
    public List<Event> findTodayEvent() {
        List<Event> todayEventsResultList = findEventByDate(LocalDateTime.now());
        logger.info(String.format("Find a %d event com.event.com.event.mvc.entity", todayEventsResultList.size()));

        return todayEventsResultList;
    }

    @Override
    public List<Event> findTomorrowEvent() {
        List<Event> tomorrowEventsResultList = findEventByDate(LocalDateTime.now().plusDays(1));
        logger.info(String.format("Find a %d event com.event.com.event.mvc.entity", tomorrowEventsResultList.size()));

        return tomorrowEventsResultList;
    }

    @Override
    @Transactional
    public List<Event> findWeekendEvent() {
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        Path<LocalDateTime> startDate = root.get("startDate");
        Expression<LocalDateTime> sqlDayOfWeekOfCurrentDate = builder.function("DAY_OF_WEEK", LocalDateTime.class, root.get("startDate"));
        Predicate isEventOnThisMonth = builder.equal(sqlDayOfWeekOfCurrentDate, dayOfWeek.getValue());
        CriteriaQuery<Event> criteriaQuery = query.select(root).where(isEventOnThisMonth);

        List<Event> eventsOnCurrentMonthResultList = entityManager.createQuery(criteriaQuery).getResultList();
        logger.info(String.format("Find a %d event com.event.com.event.mvc.entity", eventsOnCurrentMonthResultList.size()));
        return eventsOnCurrentMonthResultList;
    }

    @Override
    @Transactional
    public List<Event> findMonthEvent() {
        Month month = LocalDateTime.now().getMonth();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        Path<LocalDateTime> startDate = root.get("startDate");
        Expression<LocalDateTime> sqlMonthFromDate = builder.function("month", LocalDateTime.class, root.get("startDate"));
        Predicate isEventOnThisMonth = builder.equal(sqlMonthFromDate, month.getValue());
        CriteriaQuery<Event> criteriaQuery = query.select(root).where(isEventOnThisMonth);

        List<Event> eventsOnCurrentMonthResultList = entityManager.createQuery(criteriaQuery).getResultList();

        logger.info(String.format("Find a %d event com.event.com.event.mvc.entity", eventsOnCurrentMonthResultList.size()));
        return eventsOnCurrentMonthResultList;
    }

    @Override
    @Transactional
    public List<Event> findEventByDate(LocalDateTime date) {
        return entityManager.createNamedQuery(FIND_BY_DATE, Event.class)
                .setParameter("startDate", date)
                .getResultList();
    }

    @Transactional
    public List<Event> findEventBetweenDates(LocalDateTime from, LocalDateTime to) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        Path<LocalDateTime> startDate = root.get("startDate");
        Predicate isEventStartDateBetweenFromToPeriod = builder.between(startDate, from, to);
        CriteriaQuery<Event> criteriaQuery = query.select(root).where(isEventStartDateBetweenFromToPeriod);

        List<Event> eventsBetweenDatesPeriod = entityManager.createQuery(criteriaQuery).getResultList();

        logger.info(String.format("Find a %d event com.event.com.event.mvc.entity", eventsBetweenDatesPeriod.size()));
        return eventsBetweenDatesPeriod;

    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createNamedQuery(DELETE_ALL, Void.TYPE).executeUpdate();
    }
}
