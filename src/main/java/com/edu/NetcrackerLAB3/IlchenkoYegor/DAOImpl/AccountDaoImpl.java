package com.edu.NetcrackerLAB3.IlchenkoYegor.DAOImpl;

import com.edu.NetcrackerLAB3.IlchenkoYegor.Controller.AdminPanelController;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.AccountDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Account;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDAO {
    private static final Logger LOGGER = Logger.getLogger(AccountDaoImpl.class);
    @Autowired
    private DataSource dataSource;

    private List<Account> parseAccount(ResultSet resultSet){
        List<Account> accountList = new ArrayList<Account>();
        try {

            while(resultSet.next()){
                int id = resultSet.getInt("ACCOUNT_ID");
                String name =  resultSet.getString("USER_NAME");
                boolean active = resultSet.getBoolean("ACTIVE");
                String password =  resultSet.getString("PASSWORD");
                String uRole = resultSet.getString("USER_ROLE");
                String adr = resultSet.getString("CUSTOMER_ADDRESS");
                String email = resultSet.getString("CUSTOMER_EMAIL");
                String custName = resultSet.getString("CUSTOMER_NAME");
                String phone = resultSet.getString("CUSTOMER_PHONE");
                accountList.add(new Account(name,password,active,uRole, adr,email,custName,phone,id));
            }
            return accountList;
        } catch ( SQLException throwables ) {
            LOGGER.error(throwables.getMessage());
        }
        return accountList;
    }
    @Override
    public Account findAccount(String userName) {
        try (Connection connection = dataSource.getConnection()){

            PreparedStatement pr =  connection.prepareStatement("select * from ACCOUNTS where USER_NAME = ?");
            pr.setString(1,userName);
            pr.execute();
            ResultSet rs = pr.getResultSet();
            List<Account> accountList = parseAccount(rs);
            if(accountList.size()>0) {
                return accountList.get(0);
            }
            return null;
        } catch ( SQLException throwables ) {
            LOGGER.error(throwables.getMessage());
        }
        return null;
    }

    @Override
    public List<Account> getAll() {
        try(Connection connection = dataSource.getConnection();) {

            PreparedStatement pr = connection.prepareStatement("select  * from ACCOUNTS");
            pr.execute();
            ResultSet all = pr.getResultSet();
            return parseAccount(all);
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void saveAccount(Account account) {
        try(Connection connection = dataSource.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ACCOUNTS (USER_NAME, ACTIVE, PASSWORD, USER_ROLE, CUSTOMER_ADDRESS, CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PHONE) values (?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,account.getUserName());
            preparedStatement.setBoolean(2,account.isActive());
            preparedStatement.setString(3,account.getPassword());
            preparedStatement.setString(4,account.getUserRole());
            preparedStatement.setString(5,account.getCustomerAdress());
            preparedStatement.setString(6,account.getCustomerEmail());
            preparedStatement.setString(7,account.getCustomerName());
            preparedStatement.setString(8,account.getCustomerPhone());
            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public Account getAccountById(int id) {
        try (Connection connection = dataSource.getConnection()){

            PreparedStatement pr =  connection.prepareStatement("select * from ACCOUNTS where ACCOUNT_ID = ?");
            pr.setInt(1,id);
            pr.execute();
            ResultSet rs = pr.getResultSet();
            List<Account> accountList = parseAccount(rs);
            if(accountList.size()>0) {
                return accountList.get(0);
            }
            return null;
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateAccount(Account account) {
        try(Connection connection = dataSource.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ACCOUNTS SET  PASSWORD = ?, CUSTOMER_ADDRESS = ?, CUSTOMER_EMAIL = ?, CUSTOMER_NAME = ?, CUSTOMER_PHONE = ? WHERE ACCOUNT_ID = ?");
            preparedStatement.setString(1,account.getPassword());
            preparedStatement.setString(2,account.getCustomerAdress());
            preparedStatement.setString(3,account.getCustomerEmail());
            preparedStatement.setString(4,account.getCustomerName());
            preparedStatement.setString(5,account.getCustomerPhone());
            preparedStatement.setInt(6, account.getUserId());
            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());;
        }
    }
}
