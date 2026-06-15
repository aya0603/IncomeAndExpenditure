package com.example.accountapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.accountapp.entity.IncomeExpenseType;

public interface IncomeExpenseTypeRepository extends JpaRepository<IncomeExpenseType, Integer> {
}