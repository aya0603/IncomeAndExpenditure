package com.example.accountapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AccountInfoTest {
  @Test
  void calculateBalance_残高を計算する() {
    //テスト対象
    AccountInfo info = new AccountInfo();
    //収入合計に値をセット
    info.setIncomeTotal(5);
    info.setExpenseTotal(2);
    //支出合計に値をセット
    assertEquals(3, info.getBalance());
  }
}
