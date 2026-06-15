package com.example.accountapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  /** localhost:8080の場合index.htmlを返す */
  @GetMapping("/")
  public String index() {
    return "index";
  }
}
