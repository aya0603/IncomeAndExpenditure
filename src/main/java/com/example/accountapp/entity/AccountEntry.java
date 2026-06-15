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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * すること: エンティティ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class AccountEntry {

  /** ID: 主キーIDを、DB側の自動採番に任せる */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 名前: 空白不可 */
  @NotBlank(message = "名前を入力してください")
  private String name;

  /** 金額: 空白不可/0以上 */
  @NotNull(message = "金額を入力してください")
  @Min(value = 0, message = "金額は0円以上で入力してください")
  private Integer amount;

  /** 日付: データベースのtransactiondateと対応/空白不可*/
  @Column(name = "transactiondate")
  @NotNull(message = "日付を入力してください")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate transactionDate;

  /** カテゴリ: DBのcategorycdと対応/空白不可 */
  @Column(name = "categorycd")
  @NotNull(message = "項目を入力してください")
  private Integer categoryCd;

  /** 収支: DBの incomeexpensecdと対応/空白不可 */
  @Column(name = "incomeexpensecd")
  @NotNull(message = "収入・支出を選択してください")
  private Integer incomeExpenseCd;

  /** 作成日時: DBのcreatedと対応 */
  @Column(name = "created")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate created;

  /** 更新日時: DBのupdatedと対応 */
  @Column(name = "updated")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate updated;

  /** メモ: 空白可*/
  private String memo;

  /** 支払方法: DBのpaymentmethodcdと対応/空白不可 */
  @Column(name = "paymentmethodcd")
  @NotNull(message = "支払方法を選択してください")
  private Integer paymentmethodcd;
}