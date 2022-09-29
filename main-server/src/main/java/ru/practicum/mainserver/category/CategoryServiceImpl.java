package ru.practicum.mainserver.category;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.category.model.Category;
import ru.practicum.mainserver.category.model.CategoryDto;
import ru.practicum.mainserver.category.model.NewCategoryDto;
import ru.practicum.mainserver.common.exceptions.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size);
        List<Category> categories = categoryRepository.findAll(pageRequest).toList();
        log.info("Categories was requested with parameters: from =" +from+ ", size=" + size + ".");
        return CategoryMapper.toCategoryDtoList(categories);
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id =" + catId + " not found."));
        log.info("Category with id " + catId + " was requested.");
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        if (!categoryRepository.existsByName(newCategoryDto.getName())) {
            Category category = CategoryMapper.toCategory(newCategoryDto);
            category = categoryRepository.save(category);
            log.info("Category '" + category.getName() + "' with id " + category.getId() + " was added.");
            return CategoryMapper.toCategoryDto(category);
        } else throw new NotFoundException("Category with  name '" + newCategoryDto.getName() + "' is exist.");
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (!categoryRepository.existsById(categoryDto.getId())) {
            throw new NotFoundException("Category with id " + categoryDto.getId() + " not found.");
        }
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new NotFoundException("Category with  name '" + categoryDto.getName() + "' is exist.");
        }
        Category category = CategoryMapper.toCategory(categoryDto);
        category = categoryRepository.save(category);
        log.info("Category with id " + categoryDto.getId() + " was updated.");
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(long catId) {
        if (categoryRepository.existsById(catId)) {
            categoryRepository.deleteById(catId);
            log.info("Category with id " + catId + " was deleted.");
        } else throw new NotFoundException("Category with id " + catId + " not found.");
    }
}
