package web.controller;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import web.dto.GiaoDichDto;
import web.model.GiaoDich;
import web.model.TheNganHang;

import java.text.SimpleDateFormat;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import web.ultis.SqlDateEditor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/nhan-vien-giao-dich/the-ngan-hang" , produces = "application/json" )
public class TheNganHangController {
	private RestTemplate rest = new RestTemplate();
    
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("dd/MM/yyyy")));
    }
    
    @GetMapping
    public String getAll(Model model){
        String url = "https://htttqlt5-server.herokuapp.com/atm-card";
        List<TheNganHang> theNganHangs = Arrays.asList(rest.getForObject(url, TheNganHang[].class));
        model.addAttribute("list", theNganHangs);
        return "nhan-vien-giao-dich/the-ngan-hang/list";
    }

    @GetMapping("/edit/{id}")
    public String getById(@PathVariable("id") int id,Model model){
        String url = "https://htttqlt5-server.herokuapp.com/atm-card/detail/" + id;
        TheNganHang theNganHang = rest.getForObject(url, TheNganHang.class);
        model.addAttribute("model", theNganHang);
        return "nhan-vien-giao-dich/the-ngan-hang/edit";
    }

    @GetMapping("/edit1/{taikhoan_id}")
    public String getAllByCustomerId(@PathVariable("taikhoan_id") int id,Model model){
        String url = "https://htttqlt5-server.herokuapp.com/atm-card/" + id;
        TheNganHang theNganHang = rest.getForObject(url, TheNganHang.class);
        model.addAttribute("model", theNganHang);
        return "nhan-vien-giao-dich/the-ngan-hang/edit";
    }

    @GetMapping("/status-list/{status}")
    public String getByStatus(@PathVariable("status") int status,Model model) {
        String url = "https://htttqlt5-server.herokuapp.com/atm-card/status-list/"+status;
        List<TheNganHang> theNganHangs = Arrays.asList(rest.getForObject(url, TheNganHang[].class));
        model.addAttribute("list", theNganHangs);
        return "nhan-vien-giao-dich/the-ngan-hang/list";
    }

    @PutMapping("/approve/{id}")
    public String update(@PathVariable("id") int id){
        String url = "https://htttqlt5-server.herokuapp.com/atm-card/approve/"+id;
        rest.put(url, Void.class);
        return "nhan-vien-giao-dich/the-ngan-hang/list";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable ("id") int id) {
        rest.delete("https://htttqlt5-server.herokuapp.com/atm-card/{id}",id);
        return "nhan-vien-giao-dich/the-ngan-hang/list";
    }


    @GetMapping("/add")
    public String add(Model model){
        TheNganHang theNganHang = new TheNganHang();
        model.addAttribute("model", theNganHang);
        return "nhan-vien-giao-dich/the-ngan-hang/add";
    }

    @PostMapping("/add")
    public String save(TheNganHang theNganHang){
        String url = "https://htttqlt5-server.herokuapp.com/atm-card";
        rest.postForObject(url, theNganHang, Void.class);
        return "redirect:/nhan-vien-giao-dich/the-ngan-hang";
    }

    @PostMapping("/giao-dich")
    public String giaoDichTien(GiaoDichDto dto){
        String url = "https://htttqlt5-server.herokuapp.com/atm-card";
        rest.postForObject(url, dto, Void.class);
        return "redirect:/nhan-vien-giao-dich/the-ngan-hang";
    }
}
