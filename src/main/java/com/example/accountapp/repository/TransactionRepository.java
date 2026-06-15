package com.example.accountapp.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.accountapp.entity.AccountEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<AccountEntry, Integer> {

        /** 収入・支出区分で検索する */
        List<AccountEntry> findByIncomeExpenseCd(Integer incomeExpenseCd);

        /** 収入・支出区分と期間で検索する */
        List<AccountEntry> findByTransactionDateBetweenAndIncomeExpenseCd(
                        LocalDate startDate,
                        LocalDate endDate,
                        Integer incomeExpenseCd);

        /** 期間で検索する */
        List<AccountEntry> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}