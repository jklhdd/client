package web.controller;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import web.model.GiaoDich;
import web.model.TaiKhoan;

@Slf4j
@Controller
@RequestMapping(path = "/khach-hang/giao-dich" , produces = "application/json" )
public class GiaoDichController {
	private RestTemplate rest = new RestTemplate();

    @GetMapping
    public String getAll(Model model){
        String url = "http://localhost:8080/giaodich";
        List<GiaoDich> giaoDichs = Arrays.asList(rest.getForObject(url, GiaoDich[].class));
        model.addAttribute("list", giaoDichs);
        return "khach-hang/giao-dich/list";
    }

    @GetMapping("/{customer}")
    public String getCustomerHistory(@PathVariable("customer") int id,Model model){
        String url = "http://localhost:8080/giaodich";
        List<GiaoDich> giaoDichs = Arrays.asList(rest.getForObject(url, GiaoDich[].class));
        model.addAttribute("list", giaoDichs);
        return "khach-hang/giao-dich/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        GiaoDich giaoDich = new GiaoDich();
        model.addAttribute("model", giaoDich);
        return "khach-hang/giao-dich/add";
    }
    @PostMapping("/add")
    public String save(GiaoDich giaoDich){
        String url = "http://localhost:8080/giaodich";   
        rest.postForObject(url, giaoDich, Void.class);
        return "redirect:/quan-ly/giao-dich";

    }

}
