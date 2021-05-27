package com.edu.NetcrackerLAB3.IlchenkoYegor.config;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.AccountDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.OrderDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.ProductDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAOImpl.AccountDaoImpl;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAOImpl.OrderDaoImpl;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAOImpl.ProductDaoImpl;
import com.edu.NetcrackerLAB3.IlchenkoYegor.REST.DAO.ProductStatisticDAOImpl;
import com.edu.NetcrackerLAB3.IlchenkoYegor.REST.DAO.PruductStatisticsDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.REST.Entity.ProductStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = {"com.edu.NetcrackerLAB3.IlchenkoYegor.*","com.edu.NetcrackerLAB3.IlchenkoYegor.REST.*"})
@EnableTransactionManagement
public class ApplicationContextConfig {
    @Value("Online_shop/DB")
    private String dbName;

    @Autowired
    private Environment env;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
        rb.setBasenames(new String[] { "messages/validator" });
        return rb;
    }

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();

        return commonsMultipartResolver;
    }
    @Bean(name = "dataSource", destroyMethod = "")
    public DataSource getDataSource() throws NamingException {
        Hashtable env = new Hashtable();
        env.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://localhost:7001");
        Context context = new InitialContext( env );
        DataSource  ds=(javax.sql.DataSource) context.lookup (dbName);
        return ds;
    }

    @Bean(name = "accountDAO")
    public AccountDAO getApplicantDAO() {
        return new AccountDaoImpl();
    }

    @Bean(name = "productStatisticDAO")
    public PruductStatisticsDAO getStatistics(){
        return new ProductStatisticDAOImpl();
    }

    @Bean(name = "productDAO")
    public ProductDAO getProductDAO() {
        return new ProductDaoImpl();
    }

    @Bean(name = "orderDAO")
    public OrderDAO getOrderDAO() {
        return new OrderDaoImpl();
    }

    @Bean(name = "accountDAO")
    public AccountDAO getAccountDAO()  {
        return new AccountDaoImpl();
    }

}
