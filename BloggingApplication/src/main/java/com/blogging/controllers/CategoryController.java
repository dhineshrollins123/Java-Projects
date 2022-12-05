package com.blogging.controllers;

import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.CategoryDto;
import com.blogging.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @RequestParam Integer catId){
        CategoryDto category = categoryService.updateCategory(categoryDto,catId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteParticularCategory(@RequestParam Integer catId){
        categoryService.deleteCategory(catId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CategoryDto> getParticularCategory(@RequestParam Integer catId){
        CategoryDto category = categoryService.getCategory(catId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<CategoryDto>> retrieveAllCategories(){
        List<CategoryDto> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}

