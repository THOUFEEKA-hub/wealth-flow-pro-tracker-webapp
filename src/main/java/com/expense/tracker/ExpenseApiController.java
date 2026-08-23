package com.expense.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExpenseApiController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired(required = false)
    private GoalRepository goalRepository;

    // --- EXPENSE ENDPOINTS ---
    
    @GetMapping("/expenses/all")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @PostMapping("/expenses/add")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    @DeleteMapping("/expenses/delete/{id}")
    public Map<String, Boolean> deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    // --- GOAL ENDPOINTS ---

    @GetMapping("/goals/all")
    public List<Goal> getAllGoals() {
        if (goalRepository != null) {
            return goalRepository.findAll();
        }
        return new ArrayList<>();
    }

    @PostMapping("/goals/add")
    public Goal addGoal(@RequestBody Goal goal) {
        if (goalRepository != null) {
            List<Goal> existing = goalRepository.findAll();
            for (Goal g : existing) {
                if (g.getName() != null && g.getName().equalsIgnoreCase(goal.getName())) {
                    g.setSavedAmount(goal.getSavedAmount());
                    g.setTargetAmount(goal.getTargetAmount());
                    return goalRepository.save(g);
                }
            }
            return goalRepository.save(goal);
        }
        return goal;
    }

    @DeleteMapping("/goals/delete/{id}")
    public Map<String, Boolean> deleteGoal(@PathVariable Long id) {
        if (goalRepository != null) {
            goalRepository.deleteById(id);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    // --- DIRECT LOGIN ENDPOINT ---

    @PostMapping("/auth/direct-login")
    public Map<String, Object> directLogin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        Map<String, Object> response = new HashMap<>();

        if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Username & Email required!");
            return response;
        }

        response.put("success", true);
        response.put("message", "Login Successful!");
        response.put("username", username);
        return response;
    }
}