package ru.practicum.mainserver.request.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.mainserver.common.enums.EventState;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private EventState status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}