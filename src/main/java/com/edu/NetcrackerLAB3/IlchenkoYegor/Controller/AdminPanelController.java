package com.edu.NetcrackerLAB3.IlchenkoYegor.Controller;


import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.AccountDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.OrderDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.ProductDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Account;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.OrderDetailInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.OrderInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.ProductInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.validator.ProductInfoValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@EnableWebMvc
public class AdminPanelController {
    private static final Logger LOGGER = Logger.getLogger(AdminPanelController.class);
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ProductInfoValidator productInfoValidator;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == ProductInfo.class) {
            dataBinder.setValidator(productInfoValidator);
            //image
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }


    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    @RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model, //
                            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        List<OrderInfo> paginationResult //
                = orderDAO.listOrderInfo();

        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }


    @RequestMapping(value = { "/product" }, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "id", defaultValue = "-100") int id) {

        ProductInfo productInfo = null;
        productInfo = productDAO.findProductInfo(id);
        if (productInfo == null) {
            productInfo = new ProductInfo();
            productInfo.setNewProduct(true);
        }
        model.addAttribute("productForm", productInfo);
        return "product";
    }


    @RequestMapping(value = { "/product" }, method = RequestMethod.POST)
    public String productSave(Model model, //
                              @ModelAttribute("productForm")@Validated ProductInfo productInfo, //
                              BindingResult result) {
        if (result.hasErrors()) {
            return "product";
        }
        try {
            productDAO.save(productInfo);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            String message = e.getMessage();
            model.addAttribute("message", message);
            return "product";
        }
        return "redirect:/productList";
    }

    @RequestMapping(value = { "/order" }, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") int orderId) {
        OrderInfo orderInfo = null;
        orderInfo = this.orderDAO.getOrderInfo(orderId);
        Account account= accountDAO.getAccountById(orderInfo.getAccountId());
        if (orderInfo == null) {
            return "redirect:/orderList";
        }
        List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);
        model.addAttribute("customerInformation", account);
        model.addAttribute("orderInfo", orderInfo);
        return "order";
    }

}
