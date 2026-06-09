package com.example.accountapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "名前を入力してください")
  private String name;

  @NotNull(message = "金額を入力してください")
  @Min(value = 0, message = "金額は0円以上で入力してください")
  private Integer amount;

  @Column(name = "transactiondate")
  @NotNull(message = "日付を入力してください")
  private LocalDate transactionDate;

  @Column(name = "categorycd")
  @NotNull(message = "項目を入力してください")
  private Integer categorycd;

  @Column(name = "incomeexpensecd")
  @NotNull(message = "収入・支出を選択してください")
  private Integer incomeexpensecd;

  @Column(name = "created")
  private LocalDate created;

  @Column(name = "updated")
  private LocalDate updated;

  private String memo;

  @Column(name = "paymentmethodcd")
  @NotNull(message = "支払方法を選択してください")
  private Integer paymentmethodcd;


  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getAmount() {
    return amount;
  }

  public String getMemo() {
    return memo;
  }

  public Integer getCategorycd() {
    return categorycd;
  }

  public Integer getIncomeexpensecd() {
    return incomeexpensecd;
  }

  public LocalDate getCreated() {
    return created;
  }

  public LocalDate getUpdated() {
    return updated;
  }

  public Integer getPaymentmethodcd() {
    return paymentmethodcd;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setCategorycd(Integer categorycd) {
    this.categorycd = categorycd;
  }

  public void setIncomeexpensecd(Integer incomeexpensecd) {
    this.incomeexpensecd = incomeexpensecd;
  }

  public void setCreated(LocalDate created) {
    this.created = created;
  }

  public void setUpdated(LocalDate updated) {
    this.updated = updated;
  }

  public void setPaymentmethodcd(Integer paymentmethodcd) {
    this.paymentmethodcd = paymentmethodcd;
  }


}