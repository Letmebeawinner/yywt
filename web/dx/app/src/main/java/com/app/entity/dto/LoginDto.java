package com.app.entity.dto;

public class LoginDto {
    private String userName;
    private String password;
    private String ranCode;
    private String codeName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRanCode() {
        return ranCode;
    }

    public void setRanCode(String ranCode) {
        this.ranCode = ranCode;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", ranCode='" + ranCode + '\'' +
                ", codeName='" + codeName + '\'' +
                '}';
    }
}
