package ru.practicum.mainserver.event.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainserver.category.model.Category;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String annotation;

    @OneToOne
    private Category category;

    private Long confirmedRequests;

    private String createdOn;

    private String description;

    private String eventDate;
    @OneToOne
    private User initiator;
    @OneToOne
    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventState state;

    private String title;

    private Long views;
}
