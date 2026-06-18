package com.example.accountapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * DBのpaymentmethodテーブルとクラス（オブジェクト）を1対1で対応づけるEntity
 * バリデーションチェックも行う
 */
@Data
@Entity
@Table(name = "paymentmethod")
public class PaymentMethod {

  // ID
  @Id
  @Column(name = "paymentmethodcd")
  private Integer paymentMethodCd;

  // 名前
  @Column(name = "name")
  private String name;
}