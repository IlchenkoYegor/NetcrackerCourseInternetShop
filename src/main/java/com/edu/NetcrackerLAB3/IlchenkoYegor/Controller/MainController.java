package com.edu.NetcrackerLAB3.IlchenkoYegor.Controller;

import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.AccountDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.OrderDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.ProductDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.Utils.Utils;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Account;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.ProductLocation;
import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Products;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.CartInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.CustomerInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.ProductInfo;
import com.edu.NetcrackerLAB3.IlchenkoYegor.validator.CustomerInfoValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@EnableWebMvc
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private CustomerInfoValidator customerInfoValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        else if (target.getClass() == Account.class) {
            dataBinder.setValidator(customerInfoValidator);
        }

    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @RequestMapping("/")
    public String home() {
        return "index1";
    }

    @RequestMapping({ "/productList" })
    public String listProductHandler(Model model, //
                                     @RequestParam(value = "name", defaultValue = "") String likeName) {
        List<ProductInfo> result = productDAO.queryProducts( likeName);
        model.addAttribute("paginationProducts", result);
        return "productList";
    }

    @RequestMapping(value = {"/findByNameAndStay"}, method = RequestMethod.POST)
    public String findByName(Model model, HttpServletRequest request) {
        String likeName = request.getParameter("name");
        List<ProductInfo> result = productDAO.queryProducts(likeName);
        model.addAttribute("paginationProducts", result);
        return "productList";
    }


    @RequestMapping({ "/buyProduct" })
    public String listProductHandler(HttpServletRequest request, Model model,
                                     @RequestParam(value = "id", defaultValue = "") int id) {

        Products product = null;
        ProductLocation productLocation = null;
        if (id> 0) {
            product = productDAO.findProduct(id);
            productLocation = productDAO.findProductLocation(id);
        }
        if (product != null) {
            CartInfo cartInfo = Utils.getCartInSession(request);
            ProductInfo productInfo = new ProductInfo(product,productLocation);
            cartInfo.addProduct(productInfo, 1);
        }
        return "redirect:/shoppingCart";
    }

    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model, //
                                       @RequestParam(value = "id", defaultValue = "")int id) {
        Products product = null;
        ProductLocation productLocation = null;
        if (id > 0) {
            product = productDAO.findProduct(id);
            productLocation = productDAO.findProductLocation(id);
        }
        if (product != null) {
            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product,productLocation);

            cartInfo.removeProduct(productInfo);

        }
        return "redirect:/shoppingCart";
    }

    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request,
                                        Model model,
                                        @ModelAttribute("cartForm") CartInfo cartForm) {

        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);
        return "redirect:/shoppingCart";
    }

    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo myCart = Utils.getCartInSession(request);
        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }


    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountDAO.findAccount(userDetails.getUsername());
        CartInfo cartInfo = Utils.getCartInSession(request);
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        if (customerInfo == null) {
            customerInfo = new CustomerInfo();
            customerInfo.setAccount(account);
            customerInfo.setValid(true);
            cartInfo.setCustomerInfo(customerInfo);
        }

        model.addAttribute("customerForm", customerInfo);
        model.addAttribute("currentCustomerInfo", account);
        return "shoppingCartConfirmation";
    }
    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String registerCustomerAccount(HttpServletRequest request, Model model){
        model.addAttribute("customerForm",new Account());
        return "registerForm";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerCustomerAccount(HttpServletRequest request, Model model, @ModelAttribute("customerForm")@Validated Account customerForm, BindingResult result){
        if(accountDAO.findAccount(customerForm.getUserName())!=null){
            return "registrationError";
        }
        if(result.hasErrors()){
            return "registerForm";
        }
        customerForm.setUserRole(Account.ROLE_EMPLOYEE);
        customerForm.setActive(true);
        accountDAO.saveAccount(customerForm);
        return "login";
    }

    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(@ModelAttribute("customerForm") CustomerInfo customerForm){
       return "redirect:/shoppingCartCustomer";
    }

    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.getCustomerInfo().getAccount().getCustomerName();
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/shoppingCartCustomer";
        }

        return "shoppingCartConfirmation";
    }
    @RequestMapping(value = {"/getAccountDetails"}, method = RequestMethod.GET)
    public String getAccountDetails(HttpServletRequest request, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account =  accountDAO.findAccount(userDetails.getUsername());
        model.addAttribute("currentCustomerInfo", account);
        return "index1";
    }

    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);

        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/shoppingCartCustomer";
        }
        try {
            orderDAO.saveOrder(cartInfo);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return "shoppingCartConfirmation";
        }
        Utils.removeCartInSession(request);

        Utils.storeLastOrderedCartInSession(request, cartInfo);

        return "redirect:/shoppingCartFinalize";
    }

    @RequestMapping(value = {"/editCustomerInfo"}, method = RequestMethod.GET)
    public String editCustomerInfo(HttpServletRequest request, Model model)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account =  accountDAO.findAccount(userDetails.getUsername());
        model.addAttribute("customerForm", account);
        return "editCustomerInfo";
    }

    @RequestMapping(value = {"/editCustomerInfo"}, method = RequestMethod.POST)
    public String editCustomerInfoP(HttpServletRequest request, Model model, @ModelAttribute("customerForm")@Validated Account customerForm, BindingResult result)
    {
        if (result.hasErrors()) {
            return "editCustomerInfo";
        }
        CartInfo cartInfo = Utils.getCartInSession(request);
        Account account =  customerForm;
        accountDAO.updateAccount(account);
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setAccount(account);
        cartInfo.setCustomerInfo(customerInfo);
        Utils.storeLastOrderedCartInSession(request, cartInfo);
        LOGGER.debug(cartInfo.getCartLines().get(0).getProductInfo().getName());
        return "redirect:/shoppingCartConfirmation";
    }

    @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {
        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }

        return "shoppingCartFinalize";
    }

    @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam("id") int id) throws IOException {
        Products product = null;
        if (id > 0) {
            product = this.productDAO.findProduct(id);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }

}