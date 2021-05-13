package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import web.dto.ThanhVien;
import web.model.HoTen;
import web.model.TaiKhoan;
import web.model.ThongTinCaNhan;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping(path = "/quan-ly/khach-hang" , produces = "application/json" )
public class KhachHangController {
    private RestTemplate rest = new RestTemplate();

    @GetMapping
    public String getAll(Model model){
        String url = "https://htttqlt5-server.herokuapp.com/account";
        List<ThanhVien> thanhViens = Arrays.asList(rest.getForObject(url, ThanhVien[].class));
        model.addAttribute("list", thanhViens);
        return "quan-ly/khach-hang/list";
    }
    @GetMapping("/staff")
    public String getAllStaff(Model model){
        String url = "https://htttqlt5-server.herokuapp.com/account/staff";
        List<ThanhVien> thanhViens = Arrays.asList(rest.getForObject(url, ThanhVien[].class));
        model.addAttribute("list", thanhViens);
        return "quan-ly/khach-hang/list";    
    }

    @GetMapping("/edit/{id}")
    public String getAccountById(@PathVariable int id,Model model){
        String url = "https://htttqlt5-server.herokuapp.com/account/" + id;
        ThanhVien thanhVien = rest.getForObject(url, ThanhVien.class);
        model.addAttribute("model", thanhVien);
        return "quan-ly/khach-hang/edit";
    }

    @GetMapping("/customer")
    public String getAllCustomer(Model model){
        String url = "https://htttqlt5-server.herokuapp.com/account/customer";
        List<ThanhVien> thanhViens = Arrays.asList(rest.getForObject(url, ThanhVien[].class));
        model.addAttribute("list", thanhViens);
        return "quan-ly/khach-hang/list";  
    }


    @GetMapping("/add")
    public String add(Model model){
        ThanhVien thanhVien = new ThanhVien();
        model.addAttribute("model", thanhVien);
        return "quan-ly/khach-hang/edit";
    }

    @PostMapping("/add")
    public String save(ThanhVien thanhVien){
        String url = "https://htttqlt5-server.herokuapp.com/account";
        rest.postForObject(url, thanhVien, Void.class);
        return "redirect:/quan-ly/khach-hang";
    }

    @PutMapping("/edit/{id}")
    public String update(ThanhVien thanhVien, @PathVariable("id") int id) {
        String url = "https://htttqlt5-server.herokuapp.com/account/"+id;
        rest.put(url, thanhVien);
        return "redirect:/quan-ly/khach-hang";
    }
}
