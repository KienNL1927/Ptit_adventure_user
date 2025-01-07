package com.example.ptitadventure.DTO;

public class ForgotPasswordRequest {
    private String emailOrUsername;

    public ForgotPasswordRequest(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }
}
