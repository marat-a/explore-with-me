package ru.practicum.mainserver.request.model;

import lombok.Data;
import ru.practicum.mainserver.common.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "event_id")
    private Long eventId;
    @Column(name = "requestor_id")
    private Long requestorId;
    @Enumerated(EnumType.STRING)
    private EventState status;
    private LocalDateTime created;
}