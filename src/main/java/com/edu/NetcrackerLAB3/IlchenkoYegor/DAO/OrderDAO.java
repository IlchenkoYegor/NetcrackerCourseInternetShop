package com.edu.NetcrackerLAB3.IlchenkoYegor.DAO;

import com.edu.NetcrackerLAB3.IlchenkoYegor.model.CartInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.OrderDetailInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.OrderInfo;

import java.util.List;

public interface OrderDAO {
    public void saveOrder(CartInfo cartInfo);
    public List<OrderInfo> listOrderInfo();
    public OrderInfo getOrderInfo(int orderId);
    public List<OrderDetailInfo> listOrderDetailInfos(int orderId);
}
