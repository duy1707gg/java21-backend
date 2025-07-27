package com.example.chamcong.dto;

public class LoginRequest {
    private String username;
    private String password;

    // Constructor không tham số (bắt buộc cho Jackson khi deserialize JSON)
    public LoginRequest() {}

    // Getter và Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
