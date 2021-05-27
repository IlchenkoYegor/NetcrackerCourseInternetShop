package com.edu.NetcrackerLAB3.IlchenkoYegor.REST.DAO;

import com.edu.NetcrackerLAB3.IlchenkoYegor.REST.Entity.ProductStatistic;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface PruductStatisticsDAO {
    public boolean deleteProduct(int id);
    public List<ProductStatistic> getProductStatistic();
}
