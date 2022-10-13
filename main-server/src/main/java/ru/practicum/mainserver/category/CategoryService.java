package ru.practicum.mainserver.category;

import ru.practicum.mainserver.category.model.Category;
import ru.practicum.mainserver.category.model.CategoryDto;
import ru.practicum.mainserver.category.model.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    Category getCategory(Long catId);

    CategoryDto getCategoryDto(Long catId);

    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(long catId);
}
