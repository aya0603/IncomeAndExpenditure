package com.example.accountapp.controller;

import com.example.accountapp.entity.AccountEntry;
import com.example.accountapp.entity.AccountInfo;
import com.example.accountapp.service.TransactionService;
import com.example.accountapp.service.CategoryService;
import com.example.accountapp.service.IncomeExpenseTypeService;
import com.example.accountapp.service.PaymentMethodService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 取引に関する画面表示や処理を担当するController
 */
@Controller
public class TransactionController {

  // ControllerはServiceを通して処理を行う
  private final TransactionService transactionService;
  private final CategoryService categoryService;
  private final IncomeExpenseTypeService incomeExpenseTypeService;
  private final PaymentMethodService paymentMethodService;

  // SpringがServiceを自動で渡す
  public TransactionController(
      TransactionService transactionService,
      CategoryService categoryService,
      IncomeExpenseTypeService incomeExpenseTypeService,
      PaymentMethodService paymentMethodService) {
    this.transactionService = transactionService;
    this.categoryService = categoryService;
    this.incomeExpenseTypeService = incomeExpenseTypeService;
    this.paymentMethodService = paymentMethodService;
  }

  /**
   * 取引一覧画面を表示する
   * GET /transactions にアクセスされたときに実行
   *
   * @param startDate 開始日。入力されていなければnullになる
   * @param endDate   終了日。入力されていなければnullになる
   * @param type      収入・支出区分。入力されていなければnullになる
   * @param model     ControllerからThymeleafのHTMLへデータを渡すための箱
   * @return 取引一覧画面のテンプレート名
   */
  @GetMapping("/transactions")
  public String list(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam(required = false) Integer type,
      Model model) {

    /** 検索条件に応じた取引一覧をServiceから取得 */
    AccountInfo accountInfo = transactionService.search(startDate, endDate, type);

    /** 画面に表示する一覧を渡す */
    model.addAttribute("transactions", accountInfo.getAccountEntryList());
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);
    model.addAttribute("type", type);
    model.addAttribute("incomeTotal", accountInfo.getIncomeTotal());
    model.addAttribute("expenseTotal", accountInfo.getExpenseTotal());
    model.addAttribute("balance", accountInfo.getBalance());

    return "transactions";
  }

  /**
   * 新規登録画面を表示する
   * GET /transactions/new にアクセスされたときに実行
   *
   * @param model       ControllerからThymeleafのHTMLへデータを渡すための箱
   * @param transaction DBとクラス（オブジェクト）を1対1で対応づけるための箱
   * @return 取引登録画面のテンプレート名
   */
  @GetMapping("/transactions/new")
  public String newForm(Model model) {
    AccountEntry transaction = new AccountEntry();

    transaction.setTransactionDate(LocalDate.now());

    // 画面に表示する一覧を渡す
    model.addAttribute("transaction", transaction);
    model.addAttribute("categories", categoryService.findAll());
    model.addAttribute("incomeExpenseTypes", incomeExpenseTypeService.findAll());
    model.addAttribute("paymentMethods", paymentMethodService.findAll());

    return "transaction_form";
  }

  /**
   * 新規登録処理
   * POST /transactions にフォーム送信されたときに実行
   *
   * @param model       ControllerからThymeleafのHTMLへデータを渡すための箱
   * @param transaction DBとクラス（オブジェクト）を1対1で対応づけるための箱
   * @return 取引登録画面のテンプレート名(transaction_form)
   * @return 取引一覧画面のテンプレート名(redirect:/transactions)
   */
  @PostMapping("/transactions")
  public String create(
      @Valid AccountEntry transaction,
      BindingResult bindingResult,
      Model model) {

    // 入力エラーがある場合は、更新せずに編集画面へ戻す
    if (bindingResult.hasErrors()) {
      model.addAttribute("categories", categoryService.findAll());
      model.addAttribute("incomeExpenseTypes", incomeExpenseTypeService.findAll());
      model.addAttribute("paymentMethods", paymentMethodService.findAll());
      return "transaction_form";
    }

    // 入力エラーがなければ、Serviceに更新処理を依頼する
    transactionService.create(transaction);

    // 更新後は一覧画面へリダイレクトする
    return "redirect:/transactions";
  }

  /**
   * 編集画面を表示する
   * GET /transactions/{id}/edit にアクセスされたときに実行される
   *
   * @param model       ControllerからThymeleafのHTMLへデータを渡すための箱
   * @param transaction DBとクラス（オブジェクト）を1対1で対応づけるための箱
   * @return 取引編集画面のテンプレート名
   */
  @GetMapping("/transactions/{id}/edit")
  public String editForm(@PathVariable Integer id, Model model) {
    AccountEntry transaction = transactionService.findById(id);

    model.addAttribute("transaction", transaction);
    model.addAttribute("categories", categoryService.findAll());
    model.addAttribute("incomeExpenseTypes", incomeExpenseTypeService.findAll());
    model.addAttribute("paymentMethods", paymentMethodService.findAll());

    return "transaction_edit";
  }

  /**
   * 更新処理
   * POST /transactions/{id}/edit にフォーム送信されたときに実行される
   *
   * @param model       ControllerからThymeleafのHTMLへデータを渡すための箱
   * @param transaction DBとクラス（オブジェクト）を1対1で対応づけるための箱
   * @return 取引編集画面のテンプレート名(transaction_edit)
   * @return 取引一覧画面のテンプレート名(redirect:/transactions)
   */
  @PostMapping("/transactions/{id}/edit")
  public String update(
      @PathVariable Integer id,
      @Valid AccountEntry transaction,
      BindingResult bindingResult,
      Model model) {

    // 入力エラーがある場合は、更新せずに編集画面へ戻す
    if (bindingResult.hasErrors()) {
      transaction.setId(id);
      model.addAttribute("transaction", transaction);
      model.addAttribute("categories", categoryService.findAll());
      model.addAttribute("incomeExpenseTypes", incomeExpenseTypeService.findAll());
      model.addAttribute("paymentMethods", paymentMethodService.findAll());
      return "transaction_edit";
    }

    // 入力エラーがなければ、Serviceに更新処理を依頼する
    transactionService.update(id, transaction);

    // 更新後は一覧画面へリダイレクトする
    return "redirect:/transactions";
  }

  /**
   * 削除処理
   * POST /transactions/{id}/delete に送信されたときに実行される
   *
   * @return 取引一覧画面のテンプレート名
   */
  @PostMapping("/transactions/{id}/delete")
  public String delete(@PathVariable Integer id) {
    // URLから受け取ったidの取引を削除する
    transactionService.delete(id);

    // 削除後は一覧画面へリダイレクトする
    return "redirect:/transactions";
  }
}