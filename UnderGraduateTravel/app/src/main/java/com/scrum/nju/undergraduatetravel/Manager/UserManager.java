package com.scrum.nju.undergraduatetravel.Manager;

public class userManager {
    private userManager() {}
    private static userManager instance = null;
    private String accountId;

    public static userManager getInstance() {
        if(instance == null) {
            instance = new userManager();
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
