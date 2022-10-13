package ru.practicum.mainserver.common.validators;

import ru.practicum.mainserver.common.enums.SortType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class SortTypeSubSetValidator implements ConstraintValidator<SortTypeSubset, SortType> {
    private SortType[] subset;

    @Override
    public void initialize(SortTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(SortType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}