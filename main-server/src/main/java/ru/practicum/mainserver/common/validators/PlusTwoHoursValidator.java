package ru.practicum.mainserver.common.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class PlusTwoHoursValidator implements ConstraintValidator<PlusTwoHours, LocalDateTime> {
    @Override
    public final void initialize(final PlusTwoHours annotation) {
    }

    @Override
    public final boolean isValid(final LocalDateTime value,
                                 final ConstraintValidatorContext context) {
        return value.isAfter(LocalDateTime.now().plusHours(2));
    }
}

