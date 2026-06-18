package com.example.accountapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.accountapp.entity.AccountEntry;
import com.example.accountapp.entity.AccountInfo;
import com.example.accountapp.repository.TransactionRepository;

@Service
@Transactional(readOnly = true)
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private static final Integer INCOME = 1;
  private static final Integer EXPENDITURE = 2;

  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  /** 検索条件に応じて取引一覧を取得する */
  public AccountInfo search(LocalDate startDate, LocalDate endDate, Integer type) {
    boolean hasDate = startDate != null && endDate != null;
    boolean hasType = type != null;
    // 返却データ
    AccountInfo info = new AccountInfo();
    // 明細データ
    List<AccountEntry> accountEntryList = new ArrayList<AccountEntry>();

    if (hasDate && hasType) {
      accountEntryList = transactionRepository
          .findByTransactionDateBetweenAndIncomeExpenseCd(startDate, endDate, type);
    } else if (hasDate) {
      accountEntryList = transactionRepository
          .findByTransactionDateBetween(startDate, endDate);
    } else if (hasType) {
      accountEntryList = transactionRepository.findByIncomeExpenseCd(type);
    } else {
      accountEntryList = transactionRepository.findAll();
    }
    // 返却用クラスの明細部に取得した値をセットする
    info.setAccountEntryList(accountEntryList);

    // 集計
    // 取得した取引一覧から収入合計を計算する
    info.setIncomeTotal(calculateIncomeTotal(accountEntryList));
    // 取得した取引一覧から支出合計を計算する
    info.setExpenseTotal(calculateExpenseTotal(accountEntryList));

    return info;
  }

  /** IDで取引を1件取得する */
  public AccountEntry findById(Integer id) {
    return transactionRepository.findById(id).orElseThrow();
  }

  /** 収入合計を計算する */
  public int calculateIncomeTotal(List<AccountEntry> transactions) {
    return transactions.stream()
        .filter(t -> INCOME.equals(t.getIncomeExpenseCd()))
        .mapToInt(t -> t.getAmount() == null ? 0 : t.getAmount())
        .sum();
  }

  /** 支出合計を計算する */
  public int calculateExpenseTotal(List<AccountEntry> transactions) {
    return transactions.stream()
        .filter(t -> EXPENDITURE.equals(t.getIncomeExpenseCd()))
        .mapToInt(t -> t.getAmount() == null ? 0 : t.getAmount())
        .sum();
  }

  /** 取引を新規登録する */
  @Transactional
  public void create(AccountEntry transaction) {
    transactionRepository.save(transaction);
  }

  /** 取引を更新する */
  @Transactional
  public void update(Integer id, AccountEntry form) {
    AccountEntry transaction = transactionRepository.findById(id).orElseThrow();

    transaction.setName(form.getName());
    transaction.setAmount(form.getAmount());
    transaction.setMemo(form.getMemo());
    transaction.setTransactionDate(form.getTransactionDate());
    transaction.setIncomeExpenseCd(form.getIncomeExpenseCd());
    transaction.setCategoryCd(form.getCategoryCd());
    transaction.setPaymentmethodcd(form.getPaymentmethodcd());

    transaction.setUpdated(LocalDate.now());

    transactionRepository.save(transaction);
  }

  /** 取引を削除する */
  @Transactional
  public void delete(Integer id) {
    transactionRepository.deleteById(id);
  }
}