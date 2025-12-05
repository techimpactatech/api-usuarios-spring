package com.example.api_usuarios_spring.handler;

public class ErrorResponse {

    private String title;
    private int status;
    private String detail;

    public ErrorResponse(String title, int status, String detail) {
        this.title = title;
        this.status = status;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }
}

