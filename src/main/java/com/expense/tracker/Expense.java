package com.expense.tracker;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;
    private String category;
    private String spendingNature;
    private String accountType;
    private String date;
    private String createdAt;

    public Expense() {}

    public Expense(String description, Double amount, String category, String spendingNature, String accountType, String date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.spendingNature = spendingNature;
        this.accountType = accountType;
        this.date = date;
    }

    @PrePersist
    public void onPrePersist() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | hh:mm:ss a");
        this.createdAt = LocalDateTime.now().format(formatter);
        if (this.date == null || this.date.isEmpty()) {
            this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSpendingNature() { return spendingNature; }
    public void setSpendingNature(String spendingNature) { this.spendingNature = spendingNature; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}