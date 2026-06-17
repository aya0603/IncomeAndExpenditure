package com.example.accountapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * DBのtransactiontypesテーブルとクラス（オブジェクト）を1対1で対応づけるEntity
 * バリデーションチェックも行う
 */
@Data
@Entity
@Table(name = "transactiontypes")
public class IncomeExpenseType {

  // ID
  @Id
  @Column(name = "incomeexpensecd")
  private Integer incomeExpenseCd;

  // 名前
  @Column(name = "name")
  private String name;
}