package com.example.accountapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.accountapp.entity.Category;
import com.example.accountapp.repository.CategoryRepository;

@Service
@Transactional(readOnly = true)
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  /** カテゴリ一覧を取得する */
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}