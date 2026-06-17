package com.example.accountapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * DBのcategoriesテーブルとクラス（オブジェクト）を1対1で対応づけるEntity
 * バリデーションチェックも行う
 */
@Data
@Entity
@Table(name = "categories")
public class Category {

  // ID
  @Id
  @Column(name = "categorycd")
  private Integer categoryCd;

  // 名前
  @Column(name = "name")
  private String categoryName;
}