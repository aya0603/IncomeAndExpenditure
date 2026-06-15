package com.example.accountapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.accountapp.entity.IncomeExpenseType;
import com.example.accountapp.repository.IncomeExpenseTypeRepository;

@Service
@Transactional(readOnly = true)
public class IncomeExpenseTypeService {

  private final IncomeExpenseTypeRepository incomeExpenseTypeRepository;

  public IncomeExpenseTypeService(IncomeExpenseTypeRepository incomeExpenseTypeRepository) {
    this.incomeExpenseTypeRepository = incomeExpenseTypeRepository;
  }

  /** 収支一覧を取得する */
  public List<IncomeExpenseType> findAll() {
    return incomeExpenseTypeRepository.findAll();
  }
}