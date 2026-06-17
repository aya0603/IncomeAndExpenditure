package com.example.accountapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.accountapp.entity.PaymentMethod;
import com.example.accountapp.repository.PaymentMethodRepository;

/**
 * TransactionControllerとpaymentMethodRepositoryを仲介するService
 */
@Service
@Transactional(readOnly = true)
public class PaymentMethodService {

  private final PaymentMethodRepository paymentMethodRepository;

  public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
    this.paymentMethodRepository = paymentMethodRepository;
  }

  // 支払方法一覧を取得する
  public List<PaymentMethod> findAll() {
    return paymentMethodRepository.findAll();
  }
}