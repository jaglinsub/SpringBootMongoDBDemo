package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.stereotype.Component;

@Component
public class EmailStatus {
    private boolean isEmailSent;
    private String errorMessage;

    public boolean isEmailSent() {
        return isEmailSent;
    }

    public void setEmailSent(boolean emailSent) {
        isEmailSent = emailSent;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
