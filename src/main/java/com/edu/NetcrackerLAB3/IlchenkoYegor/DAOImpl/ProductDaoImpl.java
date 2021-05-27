package com.edu.NetcrackerLAB3.IlchenkoYegor.DAOImpl;

import com.edu.NetcrackerLAB3.IlchenkoYegor.Controller.AdminPanelController;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.ProductDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.ProductLocation;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Products;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.ProductInfo;
import oracle.sql.BLOB;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class);

    @Autowired
    private DataSource dataSource;

    private byte[] convertBlob(Blob blob) throws SQLException {
        if(blob!=null) {
            int blobLength = (int) blob.length();
            byte[] imageAsBytes = blob.getBytes(1, blobLength);
            blob.free();
            return imageAsBytes;
        }
        return null;
    }
    private Blob convertByte(byte[] bytes) throws SQLException {
        Blob blob = new SerialBlob(bytes);
        blob.setBytes(1,bytes);
        return blob;
    }
    private Products parseProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        int rec = resultSet.getInt(2);
        String code = resultSet.getString(3);
        byte[] image = convertBlob(resultSet.getBlob(4));
        String name = resultSet.getString(5);
        float price = resultSet.getFloat(6);
        Date createDate = resultSet.getDate(7);
        return new Products(id, rec,code,image,price,createDate,name);
    }
    private ProductLocation parseProductLoc(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String address = resultSet.getString(4);
        String city = resultSet.getString(2);
        String country = resultSet.getString(3);
        return new ProductLocation(id,address,city,country);
    }
    @Override
    public Products findProduct(int id) {

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement pr = null;
            pr = connection.prepareStatement("SELECT * FROM PRODUCTS where PRODUCT_ID = ?");
            pr.setInt(1, id);
            ResultSet resultSet = pr.executeQuery();
            Products products = null;
            if(resultSet.next()) {
                products = parseProduct(resultSet);
            }
            return products;
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
            return null;
        }

    }
    @Override
    public ProductLocation findProductLocation(int id) {
        try (Connection connection = dataSource.getConnection();){
            PreparedStatement pr = null;
            pr = connection.prepareStatement("SELECT * FROM PRODUCT_LOCATION where PRODUCT_ID = ?");
            pr.setInt(1, id);
            ResultSet resultSet = pr.executeQuery();
            ProductLocation productLocation = null;
            if(resultSet.next()) {
                productLocation = parseProductLoc(resultSet);
            }
            return productLocation;
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return null;

    }
    public ProductInfo parseAll(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        int rec = resultSet.getInt(2);
        String code = resultSet.getString(3);
        byte[] image = convertBlob(resultSet.getBlob(4));
        String name = resultSet.getString(5);
        float price = resultSet.getFloat(6);
        Date createDate = resultSet.getDate(7);
        int nid = resultSet.getInt(8);
        String address = resultSet.getString(9);
        String city = resultSet.getString(10);
        String country = resultSet.getString(11);

        return new ProductInfo(
                new Products(id,rec,code,image,price,createDate,name), new ProductLocation(nid,city,country,address));
    }
    @Override
    public ProductInfo findProductInfo(int id) {
        Products products =  findProduct(id);
        ProductLocation productLocation = findProductLocation(id);
        if(products == null || productLocation == null){
            return null;
        }
        ProductInfo productInfo = new ProductInfo(products,productLocation);
        return productInfo;
    }

    @Override
    public void save(ProductInfo productInfo) {
        int code = productInfo.getProductId();

        Products product = null;
        byte[] imageToInsert = null;

        boolean isNew = false;
        if (code != 0) {
            product = this.findProduct(code);
            imageToInsert = product.getImage();
        }
        if (product == null) {
            isNew = true;
        }
        if (productInfo.getFileData() != null) {
            byte[] image = productInfo.getFileData().getBytes();
            if (image != null && image.length > 0) {
                    imageToInsert = image;
            }
        }
        boolean neededToBeClosed = false;
        if (isNew) {
            PreparedStatement preparedStatement = null;
            try (Connection connection = dataSource.getConnection()){

                preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTS (RECOMENDED, CODE, IMAGE, NAME, PRICE) VALUES (?,?,?,?,?)");
                preparedStatement.setNull(1,productInfo.getRecommended());
                preparedStatement.setString(2, productInfo.getCode());
                BLOB blob = null;
                if(imageToInsert != null) {
                    blob = BLOB.createTemporary(connection, false, BLOB.DURATION_SESSION);
                    OutputStream outputStream = blob.setBinaryStream(0L);
                    InputStream inputStream = new ByteArrayInputStream(imageToInsert);
                    byte[] buffer = new byte[blob.getBufferSize()];
                    int byteread = 0;
                    while ((byteread = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, byteread);
                    }
                    outputStream.close();
                    inputStream.close();
                }
                preparedStatement.setBlob(3,blob);
                preparedStatement.setString(4, productInfo.getName());
                preparedStatement.setFloat(5, productInfo.getPrice());
                preparedStatement.executeUpdate();
                connection.commit();
                preparedStatement.close();
                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO PRODUCT_LOCATION (PRODUCT_ID, CITY, COUNTRY, ADDRES) VALUES ((SELECT Max(PRODUCT_ID) from PRODUCTS),?,?,?) ");
                preparedStatement2.setString(1,productInfo.getLocationCity());
                preparedStatement2.setString(2,productInfo.getLocationCountry());
                preparedStatement2.setString(3, productInfo.getLocationAddress());
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
            } catch ( SQLException e ) {
                LOGGER.error(e.getMessage());
            } catch ( IOException e ) {
                LOGGER.error(e.getMessage());
            }

        }else{
            PreparedStatement preparedStatement = null;
            try (Connection connection = dataSource.getConnection()){
                preparedStatement = connection.prepareStatement("UPDATE PRODUCTS SET RECOMENDED = ?, CODE=?, IMAGE=?, NAME=?, PRICE=? where PRODUCT_ID = ?");
                preparedStatement.setNull(1,productInfo.getRecommended());
                preparedStatement.setString(2, productInfo.getCode());
                BLOB blob = null;
                if(imageToInsert != null) {
                    blob = BLOB.createTemporary(connection, false, BLOB.DURATION_SESSION);
                    OutputStream outputStream = blob.setBinaryStream(0L);
                    InputStream inputStream = new ByteArrayInputStream(imageToInsert);
                    byte[] buffer = new byte[blob.getBufferSize()];
                    int byteread = 0;
                    while ((byteread = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, byteread);
                    }
                    outputStream.close();
                    inputStream.close();
                }

                    preparedStatement.setBlob(3, blob);
                    preparedStatement.setString(4, productInfo.getName());
                    preparedStatement.setFloat(5, productInfo.getPrice());
                    preparedStatement.setInt(6, productInfo.getProductId());
                    preparedStatement.executeUpdate();
                    connection.commit();
                    preparedStatement.close();
                PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE PRODUCT_LOCATION SET  CITY = ?, COUNTRY = ?, ADDRES = ? where PRODUCT_ID = ?");
                preparedStatement2.setString(1,productInfo.getLocationCity());
                preparedStatement2.setString(2,productInfo.getLocationCountry());
                preparedStatement2.setString(3, productInfo.getLocationAddress());
                preparedStatement2.setInt(4,productInfo.getProductId());
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
            } catch ( SQLException e ) {
                LOGGER.error(e.getMessage());
            } catch ( IOException e ) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @Override
    public List<ProductInfo> queryProducts(String likeName) {
        try (Connection connection = dataSource.getConnection()){

            String sql = "SELECT pr.*, pl.*  from PRODUCTS pr, PRODUCT_LOCATION pl";
            sql += " where lower(pr.NAME) like ? and pr.PRODUCT_ID = pl.PRODUCT_ID";
            sql += " order by pr.create_date desc ";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(sql);

            if (likeName != null && likeName.length() > 0) {
                preparedStatement.setString(1,  "%" + likeName.toLowerCase() + "%");
            }
            else{
                preparedStatement.setString(1,  "%");
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProductInfo> list = new ArrayList<>();
            while(resultSet.next()){
                list.add(parseAll(resultSet));
            }
            return list;
        } catch ( SQLException e ) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<ProductInfo> queryProducts() {
        return queryProducts(null);
    }
}
