package web.controller;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
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
import web.dto.ThanhVien;
import web.model.GiaoDich;
import web.model.TaiKhoan;

@Slf4j
@Controller
@RequestMapping(path = "/khach-hang/giao-dich" , produces = "application/json" )
public class GiaoDichKHController {
	private RestTemplate rest = new RestTemplate();

    @Autowired
    private Environment env;
    
    @GetMapping
    public String getAll(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
        String url = env.getProperty("web.server.url") + "/giaodich/"+thanhVien.getId();
        List<GiaoDich> giaoDichs = Arrays.asList(rest.getForObject(url, GiaoDich[].class));
        model.addAttribute("list", giaoDichs);
        return "khach-hang/giao-dich/list";
    }

    @GetMapping("/{customer}")
    public String getCustomerHistory(@PathVariable("customer") int id,Model model){
        String url = env.getProperty("web.server.url") + "/giaodich";
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
    @GetMapping("/edit/{id}")
    public String edit(Model model,@PathVariable("id") int id){
        String url = env.getProperty("web.server.url") + "/giaodich/"+ id;
        GiaoDich giaoDich = rest.getForObject(url, GiaoDich.class);
        model.addAttribute("model", giaoDich);
        return "khach-hang/giao-dich/edit";
    }
    @PostMapping("/add")
    public String save(GiaoDich giaoDich, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		TaiKhoan tk = rest.getForObject(env.getProperty("web.server.url") + "/account/" + thanhVien.getId(), TaiKhoan.class);
        String url = env.getProperty("web.server.url") + "/giaodich";   
        giaoDich.setTk(tk);
        rest.postForObject(url, giaoDich, Void.class);
        return "redirect:/khach-hang/giao-dich";

    }

}
