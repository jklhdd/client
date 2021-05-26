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
@RequestMapping(path = "/quan-ly/giao-dich" , produces = "application/json" )
public class GiaoDichController {
	private RestTemplate rest = new RestTemplate();

    @Autowired
    private Environment env;

    @GetMapping
    public String getAll(Model model){
        String url = env.getProperty("web.server.url") + "/account/customer";
        List<ThanhVien> thanhViens = Arrays.asList(rest.getForObject(url, ThanhVien[].class));
        model.addAttribute("list", thanhViens);
        return "quan-ly/giao-dich/list";
    }

    @GetMapping("/edit/{customer}")
    public String getCustomerHistory(@PathVariable("customer") int id,Model model){
        String url = env.getProperty("web.server.url") + "/giaodich/"+id;
        List<GiaoDich> giaoDichs = Arrays.asList(rest.getForObject(url, GiaoDich[].class));
        model.addAttribute("list", giaoDichs);
        model.addAttribute("tk", giaoDichs.get(0).getTk());
        return "quan-ly/giao-dich/edit";
    }

    @GetMapping("/add")
    public String add(Model model){
        GiaoDich giaoDich = new GiaoDich();
        model.addAttribute("model", giaoDich);
        return "quan-ly/giao-dich/add";
    }
    @PostMapping("/add")
    public String save(GiaoDich giaoDich, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		TaiKhoan tk = rest.getForObject(env.getProperty("web.server.url") + "/account/" + thanhVien.getId(), TaiKhoan.class);
        String url = env.getProperty("web.server.url") + "/giaodich";   
        giaoDich.setTk(tk);
        rest.postForObject(url, giaoDich, Void.class);
        return "redirect:/quan-ly/giao-dich";

    }

}
