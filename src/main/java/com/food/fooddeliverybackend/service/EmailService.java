package com.food.fooddeliverybackend.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
