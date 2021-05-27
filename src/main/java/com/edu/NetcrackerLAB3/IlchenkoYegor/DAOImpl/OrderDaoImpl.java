package com.edu.NetcrackerLAB3.IlchenkoYegor.DAOImpl;

import com.edu.NetcrackerLAB3.IlchenkoYegor.Controller.AdminPanelController;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.AccountDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.OrderDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.ProductDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Order;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.OrderDetails;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.CartInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.CartLineInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.OrderDetailInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.OrderInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDaoImpl implements OrderDAO {
    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ProductDAO productDAO;

    private int getMaxOrderNum() {

        try (Connection connection = dataSource.getConnection();){

            PreparedStatement pr = connection.prepareStatement("SELECT max(ORDER_NUM) from ORDERS");
            ResultSet rs = pr.executeQuery();

            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }
    private int getOrderId(){
        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement pr = connection.prepareStatement("SELECT max(ORDER_ID) from ORDERS");
            ResultSet rs =  pr.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return -2000;
    }
    @Override
    public void saveOrder(CartInfo cartInfo) {
        int orderNum = this.getMaxOrderNum() + 1;
        int orderId = this.getOrderId()+1;
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =  connection.prepareStatement("INSERT INTO ORDERS ( ACCOUNT_ID, AMOUNT, ORDER_NUM) values (?,?,?)");
            preparedStatement.setInt(1, accountDAO.findAccount(cartInfo.getCustomerInfo().getAccount().getUserName()).getUserId());
            preparedStatement.setDouble(2,cartInfo.getAmountTotal());
            preparedStatement.setInt(3,orderNum);
            preparedStatement.execute();
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }

        List<CartLineInfo> lines = cartInfo.getCartLines();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pr = connection.prepareStatement("insert into ORDER_DETAILS (AMOUNT, PRICE, QUANITY, ORDER_ID, PRODUCT_ID) VALUES (?,?,?,?,?)"))

        {
            try {
                connection.setAutoCommit(false);

                for (CartLineInfo line : lines) {

                    pr.setFloat(1, line.getAmount());
                    pr.setFloat(2, line.getProductInfo().getPrice());
                    pr.setInt(3, line.getQuantity());
                    pr.setInt(4, orderId);
                    pr.setInt(5, line.getProductInfo().getProductId());
                    pr.execute();
                    connection.commit();
                }
                connection.setAutoCommit(true);
            }catch ( SQLException e ){
                LOGGER.error(e.getMessage());
                if (connection != null) {
                    try {
                        LOGGER.error("rollback");
                        connection.rollback();
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        cartInfo.setOrderNum(orderNum);
    }


    @Override
    public List<OrderInfo> listOrderInfo() {

        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection();){

            List<OrderInfo> list = new ArrayList<>();
            PreparedStatement pr = connection.prepareStatement("SELECT * from ORDERS");
            resultSet = pr.executeQuery();
            while(resultSet.next()) {
                int orderId = resultSet.getInt("ORDER_ID");
                int accId = resultSet.getInt("ACCOUNT_ID");
                float amount = resultSet.getFloat("AMOUNT");
                Date crDate = resultSet.getDate("ORDER_DATE");
                int orderNum = resultSet.getInt("ORDER_NUM");
                list.add(new OrderInfo(new Order(orderId, accId, amount, crDate, orderNum)));
            }
            return list;
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public Order findOrder(int orderId) {
        try ( Connection connection = dataSource.getConnection();){

            PreparedStatement pr = connection.prepareStatement("SELECT * from ORDERS where ORDER_ID = ?");
            pr.setInt(1,orderId);
            ResultSet resultSet =  pr.executeQuery();
            if(resultSet.next()) {
                int accId = resultSet.getInt("ACCOUNT_ID");
                float amount = resultSet.getFloat("AMOUNT");
                Date crDate = resultSet.getDate("ORDER_DATE");
                int orderNum = resultSet.getInt("ORDER_NUM");

                Order order = new Order(orderId, accId, amount, crDate, orderNum);
                return order;
            }
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public OrderInfo getOrderInfo(int orderId) {
        Order order = this.findOrder(orderId);

        if (order == null) {
            return null;
        }
        return new OrderInfo(order);
    }

    @Override
    public List<OrderDetailInfo> listOrderDetailInfos(int orderId) {
        try( Connection connection = dataSource.getConnection();) {

            PreparedStatement preparedStatement =  connection.prepareStatement("SELECT od.*, pr.name as PRODUCT_NAME FROM ORDER_DETAILS od, PRODUCTS pr where ORDER_ID = ? and od.PRODUCT_ID = pr.PRODUCT_ID");
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderDetailInfo> orderDetailInfos = new ArrayList<>();
            while (resultSet.next()){
                String name = resultSet.getString("DETAIL_ID");
                float amount = resultSet.getFloat("AMOUNT");
                float price = resultSet.getFloat("PRICE");
                int quantity = resultSet.getInt("QUANITY");
                int productId = resultSet.getInt("PRODUCT_ID");
                String productName = resultSet.getString("PRODUCT_NAME");
                orderDetailInfos.add(new OrderDetailInfo(new OrderDetails(name,amount,price,quantity,orderId,productId), productName));
            }
            return orderDetailInfos;
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }

        return null;
    }
}
