package com.scrum.nju.undergraduatetravel.MiddleClass;

public class User {
    private String accountId;

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public User(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId(){
        return this.accountId;
    }
}
