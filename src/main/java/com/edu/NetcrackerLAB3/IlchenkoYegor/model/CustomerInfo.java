package com.edu.NetcrackerLAB3.IlchenkoYegor.model;

import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Account;

public class CustomerInfo {

    private Account account;

    private boolean valid;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}