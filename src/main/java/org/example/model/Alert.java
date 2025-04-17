package org.example.model;

public class Alert {
    private String accountId;
    private String message;

    public Alert(String accountId, String message) {
        this.accountId = accountId;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Alert{accountId='" + accountId + "', message='" + message + "'}";
    }
}