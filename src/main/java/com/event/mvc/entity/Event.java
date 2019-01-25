package com.event.mvc.entity;

import com.event.mvc.entity.converter.StringArrayConverter;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "EVENTS")
@NamedQueries({
        @NamedQuery(name = "findAll", query = "select e from Event e"),
        @NamedQuery(name = "findByDate", query = "select e from Event e where e.startDate = :startDate"),
        @NamedQuery(name = "delete_all", query = "delete from Event")})
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = -1L;
    private String title;
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Fetch(FetchMode.JOIN)
    @ElementCollection
    List<String> members;
    private String description;
    private LocalDateTime startDate;
    private String location;

    public Event() {

    }

    public Event(String title, EventType type, List<String> members, String description, LocalDateTime startDate, String location) {
        this.title = title;
        this.type = type;
        this.members = members;
        this.description = description;
        this.startDate = startDate;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title +
                ", type=" + type +
                ", members=" + members +
                ", description='" + description +
                ", startDate=" + startDate +
                ", location='" + location +
                "}";
    }


}
