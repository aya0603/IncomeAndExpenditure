package com.example.accountapp.controller;

import com.example.accountapp.entity.Transaction;
import com.example.accountapp.repository.TransactionRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransactionController {

  private final TransactionRepository transactionRepository;

  public TransactionController(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @GetMapping("/transactions")
  public String list(

      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,

      @RequestParam(required = false) Integer type,

      Model model) {

    List<Transaction> transactions;

    boolean hasDate = startDate != null && endDate != null;
    boolean hasType = type != null;

    if (hasDate && hasType) {

      transactions = transactionRepository
          .findByTransactionDateBetweenAndIncomeexpensecd(startDate, endDate, type);

    } else if (hasDate) {

      transactions = transactionRepository
          .findByTransactionDateBetween(startDate, endDate);

    } else if (hasType) {

      transactions = transactionRepository.findByIncomeexpensecd(type);

    } else {

      transactions = transactionRepository.findAll();
    }

    int incomeTotal = transactions.stream()
        .filter(t -> Integer.valueOf(1).equals(t.getIncomeexpensecd()))
        .mapToInt(t -> t.getAmount() == null ? 0 : t.getAmount())
        .sum();

    int expenseTotal = transactions.stream()
        .filter(t -> Integer.valueOf(2).equals(t.getIncomeexpensecd()))
        .mapToInt(t -> t.getAmount() == null ? 0 : t.getAmount())
        .sum();

    int balance = incomeTotal - expenseTotal;

    model.addAttribute("transactions", transactions);
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);
    model.addAttribute("type", type);
    model.addAttribute("incomeTotal", incomeTotal);
    model.addAttribute("expenseTotal", expenseTotal);
    model.addAttribute("balance", balance);

    return "transactions";
  }

  @GetMapping("/transactions/new")
  public String newForm(Model model) {
    model.addAttribute("transaction", new Transaction());
    return "transaction_form";
  }

  @PostMapping("/transactions")
  public String create(
      @Valid Transaction transaction,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "transaction_form";
    }

    transactionRepository.save(transaction);
    return "redirect:/transactions";
  }

  @GetMapping("/transactions/{id}/edit")
  public String editForm(@PathVariable Integer id, Model model) {
    Transaction transaction = transactionRepository.findById(id).orElseThrow();

    model.addAttribute("transaction", transaction);
    return "transaction_edit";
  }

  @PostMapping("/transactions/{id}/edit")
  public String update(
      @PathVariable Integer id,
      @Valid Transaction form,
      BindingResult bindingResult,
      Model model) {

    if (bindingResult.hasErrors()) {
      form.setId(id);
      model.addAttribute("transaction", form);
      return "transaction_edit";
    }

    Transaction transaction = transactionRepository.findById(id).orElseThrow();

    transaction.setName(form.getName());
    transaction.setAmount(form.getAmount());
    transaction.setMemo(form.getMemo());
    transaction.setTransactionDate(form.getTransactionDate());
    transaction.setIncomeexpensecd(form.getIncomeexpensecd());
    transaction.setCategorycd(form.getCategorycd());
    transaction.setPaymentmethodcd(form.getPaymentmethodcd());

    transactionRepository.save(transaction);

    return "redirect:/transactions";
  }

  @PostMapping("/transactions/{id}/delete")
  public String delete(@PathVariable Integer id) {

    transactionRepository.deleteById(id);

    return "redirect:/transactions";
  }
}