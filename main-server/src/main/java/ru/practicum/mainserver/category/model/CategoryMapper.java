package ru.practicum.mainserver.category.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public static Category toCategory(NewCategoryDto createDto) {
        return new Category(null, createDto.getName());
    }

    public static List<CategoryDto> toCategoryDtoList (List<Category> categories){
        return categories.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }
}