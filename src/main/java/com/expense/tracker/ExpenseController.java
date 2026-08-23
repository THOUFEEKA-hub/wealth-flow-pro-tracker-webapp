package com.expense.tracker;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseRepository repository;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Expense> expenses = repository.findAll();
        double totalAmount = expenses.stream().mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0).sum();
        
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("expenseCount", expenses.size());
        model.addAttribute("expense", new Expense());
        return "index";
    }

    @PostMapping("/add")
    public String addExpense(@ModelAttribute Expense expense) {
        if (expense.getDate() == null || expense.getDate().isEmpty()) {
            expense.setDate(LocalDate.now().toString());
        }
        repository.save(expense);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}