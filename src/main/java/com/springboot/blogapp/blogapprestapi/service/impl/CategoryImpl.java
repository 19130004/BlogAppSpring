package com.springboot.blogapp.blogapprestapi.service.impl;

import com.springboot.blogapp.blogapprestapi.entity.Category;
import com.springboot.blogapp.blogapprestapi.exception.BlogAPIException;
import com.springboot.blogapp.blogapprestapi.exception.ResourceNotFoundException;
import com.springboot.blogapp.blogapprestapi.payload.CategoryDto;
import com.springboot.blogapp.blogapprestapi.repository.CategoryRepository;
import com.springboot.blogapp.blogapprestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id: ", "id", categoryId)
        );

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id: ", "id", categoryId)
        );
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id: ", "id", categoryId)
        );
        try {
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new BlogAPIException( HttpStatus.BAD_REQUEST, "Category cannot be deleted");
        }
    }
}
