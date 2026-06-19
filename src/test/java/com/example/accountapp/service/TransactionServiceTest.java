package com.example.accountapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.accountapp.entity.AccountEntry;
import com.example.accountapp.repository.TransactionRepository;
import com.example.accountapp.entity.AccountInfo;

/**
 * TransactionServiceの収支計算のロジックTest
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @Mock
  private TransactionRepository transactionRepository;

  @Test
  void calculateIncomeTotal_収入合計を計算する() {
    // 準備
    List<AccountEntry> transactions = new ArrayList<>();

    AccountEntry income = new AccountEntry();
    income.setIncomeExpenseCd(1);
    income.setAmount(5);

    transactions.add(income);

    TransactionService transactionService = new TransactionService(transactionRepository);

    // 実行
    int totalResult = transactionService.calculateIncomeTotal(transactions);

    // 検証
    assertEquals(5, totalResult);
  }

  @Test
  void calculateExpenseTotal_支出合計を計算する() {
    // 準備
    List<AccountEntry> transactions = new ArrayList<>();

    AccountEntry expenditure = new AccountEntry();
    expenditure.setIncomeExpenseCd(2);
    expenditure.setAmount(5);

    transactions.add(expenditure);

    TransactionService transactionService = new TransactionService(transactionRepository);

    // 実行
    int totalResult = transactionService.calculateExpenseTotal(transactions);

    // 検証
    assertEquals(-5, totalResult);
  }

  @Test
  void calculateBalance_残高を計算する() {
    // 準備
    AccountInfo accountInfo = new AccountInfo();

    // 実行
    int totalResult = accountInfo.getBalance();

    // 検証
    assertEquals(1, totalResult);
  }
}