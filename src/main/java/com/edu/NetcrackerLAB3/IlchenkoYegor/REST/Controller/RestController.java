package com.edu.NetcrackerLAB3.IlchenkoYegor.REST.Controller;

import com.edu.NetcrackerLAB3.IlchenkoYegor.Controller.AdminPanelController;
import com.edu.NetcrackerLAB3.IlchenkoYegor.DAO.ProductDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.REST.DAO.PruductStatisticsDAO;
import com.edu.NetcrackerLAB3.IlchenkoYegor.REST.Entity.ProductStatistic;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RestController {
    @Autowired
    PruductStatisticsDAO pruductStatisticsDAO;

    private static final Logger LOGGER = Logger.getLogger(RestController.class);

    @Autowired
    ProductDAO productDAO;

    @RequestMapping(value = {"/getStatistics"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getStatistics(){

        List<ProductStatistic> productStatistic = pruductStatisticsDAO.getProductStatistic();
        if(productStatistic == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productStatistic);
    }

    @RequestMapping(value = {"/findByName"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ProductInfo>> searchHandler(@RequestParam(value = "name")String name, Model model){
        List<ProductInfo> result = productDAO.queryProducts(name);
        model.addAttribute("paginationProducts", result);
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = {"/deleteProduct/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteProduct(@PathVariable("id")int id){
        if(pruductStatisticsDAO.deleteProduct(id))
        {
            return "redirect:/productList";
        }else{
            return ResponseEntity.badRequest().body("bad id or cannot handle operation in current time").toString();
        }

    }
}
