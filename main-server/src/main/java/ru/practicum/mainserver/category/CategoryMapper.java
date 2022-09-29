package ru.practicum.mainserver.category;

import ru.practicum.mainserver.category.model.Category;
import ru.practicum.mainserver.category.model.CategoryDto;
import ru.practicum.mainserver.category.model.NewCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

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