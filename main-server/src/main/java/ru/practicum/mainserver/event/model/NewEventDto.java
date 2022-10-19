package ru.practicum.mainserver.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.mainserver.common.validators.PlusTwoHours;
import ru.practicum.mainserver.location.Location;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewEventDto {
    @NotNull
    @Size(min = 20, message = "The minimum number of characters must be at least 20")
    @Size(max = 2000, message = "The maximum number of characters should be no more than 2000")
    private String annotation;

    @NotNull
    private Long category;

    @NotNull
    @Size(min = 20, message = "The minimum number of characters must be at least 20")
    @Size(max = 7000, message = "The maximum number of characters should be no more than 7000")
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @PlusTwoHours
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    @Value("false")
    private Boolean paid;

    @Value("0")
    @PositiveOrZero
    private Long participantLimit;

    @Value("true")
    private Boolean requestModeration;

    @NotNull
    @Size(min = 3, message = "The minimum number of characters must be at least 3")
    @Size(max = 120, message = "The maximum number of characters should be no more than 120")
    private String title;
}
