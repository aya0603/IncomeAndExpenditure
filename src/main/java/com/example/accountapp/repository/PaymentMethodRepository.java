package com.example.accountapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.accountapp.entity.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}