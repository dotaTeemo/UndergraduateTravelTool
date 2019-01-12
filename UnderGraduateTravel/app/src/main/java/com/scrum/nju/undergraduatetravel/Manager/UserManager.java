package com.scrum.nju.undergraduatetravel.Manager;

public class UserManager {
    private UserManager() {}
    private static UserManager instance;
    private String accountId;

    public static  UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
        }
        return  instance;
    }
    public  boolean isLogined() {
        return accountId != null;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }
    public String getAccountId(){
        return this.accountId;
    }
}
