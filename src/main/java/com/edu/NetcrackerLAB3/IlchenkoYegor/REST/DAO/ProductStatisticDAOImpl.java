package com.edu.NetcrackerLAB3.IlchenkoYegor.REST.DAO;

import com.edu.NetcrackerLAB3.IlchenkoYegor.Controller.AdminPanelController;
import com.edu.NetcrackerLAB3.IlchenkoYegor.REST.Entity.ProductStatistic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductStatisticDAOImpl implements PruductStatisticsDAO {
    @Autowired
    DataSource dataSource;
    private static final Logger LOGGER = Logger.getLogger(ProductStatisticDAOImpl.class);
    @Override
    public boolean deleteProduct(int id) {
        try (Connection connection = dataSource.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PRODUCTS WHERE PRODUCT_ID = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            return true;
        } catch ( SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<ProductStatistic> getProductStatistic() {
        List<ProductStatistic> productStatisticList = null;
        try (Connection connection = dataSource.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT NAME, PRICE FROM PRODUCTS");
            ResultSet rs = preparedStatement.executeQuery();
            productStatisticList = new ArrayList<>();
            while(rs.next()){
                String name = rs.getString(1);
                float price = rs.getFloat(2);
                productStatisticList.add(new ProductStatistic(name,price));
            }
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return productStatisticList;
    }
}
