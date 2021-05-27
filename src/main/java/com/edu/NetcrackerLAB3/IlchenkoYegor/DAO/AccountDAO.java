package com.edu.NetcrackerLAB3.IlchenkoYegor.DAO;

import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Account;

import java.sql.ResultSet;
import java.util.List;

public interface AccountDAO {
    public Account findAccount(String id);
    public List<Account> getAll();
    public void saveAccount(Account account);
    public Account getAccountById(int id);
    public void updateAccount(Account account);
}
