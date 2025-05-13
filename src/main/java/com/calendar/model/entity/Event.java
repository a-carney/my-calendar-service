package com.calendar.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "all_day")
    private Boolean allDay = false;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.SCHEDULED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attendee> attendees = new HashSet<>();

    public enum EventStatus {
        SCHEDULED, HAPPENING, COMPLETED, CANCELED
    }
}
