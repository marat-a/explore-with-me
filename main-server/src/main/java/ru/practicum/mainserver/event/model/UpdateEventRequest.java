package ru.practicum.mainserver.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import ru.practicum.mainserver.common.validators.PlusTwoHours;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdateEventRequest {

    @Size(min = 20, message = "{validation.name.size.too_short}")
    @Size(max = 2000, message = "{validation.name.size.too_long}")
    private String annotation;

    @NotNull
    private Long category;

    @Size(min = 20, message = "{validation.name.size.too_short}")
    @Size(max = 7000, message = "{validation.name.size.too_long}")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @PlusTwoHours
    private LocalDateTime eventDate;

    @NumberFormat
    @NotNull
    private Long eventId;

    private Boolean paid;

    private Long participantLimit;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 120, message = "{validation.name.size.too_long}")
    private String title;
}
