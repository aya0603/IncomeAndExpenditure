package com.example.accountapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * すること: エンティティ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {

  // 明細
  private List<AccountEntry> accountEntryList;

  // 収入合計
  private int incomeTotal;

  // 支出合計
  private int expenseTotal;

  /**
   * 残高を計算して返却する
   *
   * @return 残高
   */
  public int getBalance() {
    return incomeTotal - expenseTotal;
  }
}