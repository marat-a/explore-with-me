package ru.practicum.mainserver.event.model;

import com.vividsolutions.jts.geom.Point;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainserver.category.model.Category;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "events")

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String annotation;

    @OneToOne
    private Category category;

    private Long confirmedRequests;

    private LocalDateTime createdOn;

    private String description;

    private LocalDateTime eventDate;
    @OneToOne
    private User initiator;

    private Point coordinates;

    private Boolean paid;

    private Long participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private EventState state;

    private String title;
    @Transient
    private Long views;
}
