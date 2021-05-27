package com.edu.NetcrackerLAB3.IlchenkoYegor.DAO;

import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.ProductLocation;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Products;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.*;

import java.util.List;

public interface ProductDAO {
    public Products findProduct(int id);

    public ProductInfo findProductInfo(int id) ;
    public ProductLocation findProductLocation(int id);


    public List<ProductInfo> queryProducts();

    public List<ProductInfo> queryProducts( String likeName);

    public void save(ProductInfo productInfo);
}
