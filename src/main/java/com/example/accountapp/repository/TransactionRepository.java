package com.example.accountapp.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.accountapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

        List<Transaction> findByIncomeexpensecd(Integer incomeexpensecd);

        List<Transaction> findByTransactionDateBetweenAndIncomeexpensecd(
                        LocalDate startDate,
                        LocalDate endDate,
                        Integer incomeexpensecd);

        List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}