package ru.practicum.mainserver.common.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ FIELD, METHOD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlusTwoHoursValidator.class)
@Documented
public @interface PlusTwoHours {
    String message() default "The time of event cannot be earlier than two hours from the current moment";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}