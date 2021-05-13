package web.controller;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import web.model.SoTietKiem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import web.ultis.SqlDateEditor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
@Slf4j
@Controller
@RequestMapping(path = "/nhan-vien-giao-dich/so-tiet-kiem" , produces = "application/json" )
public class SoTietKiemController {
	private RestTemplate rest = new RestTemplate();

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("dd/MM/yyyy")));
    }

    @GetMapping
    public String getAll(Model model){
        String url = "http://localhost:8080/saving";
        List<SoTietKiem> soTietKiems = Arrays.asList(rest.getForObject(url, SoTietKiem[].class));
        model.addAttribute("list", soTietKiems);
        return "nhan-vien-giao-dich/so-tiet-kiem/list.html";
    }

    @GetMapping("/edit/{id}")
    public String getById(@PathVariable("id") int id,Model model){
        String url = "http://localhost:8080/saving/" + id;
        SoTietKiem soTietKiem = rest.getForObject(url, SoTietKiem.class);
        model.addAttribute("model", soTietKiem);
        return "nhan-vien-giao-dich/so-tiet-kiem/edit.html";
    }

    @GetMapping("/{taikhoan_id}")
    public String getAllByCustomerId(@PathVariable("taikhoan_id") int id,Model model){
        String url = "http://localhost:8080/saving/" + id;
        SoTietKiem soTietKiem = rest.getForObject(url, SoTietKiem.class);
        model.addAttribute("model", soTietKiem);
        return "nhan-vien-giao-dich/so-tiet-kiem/edit.html";
    }

    @GetMapping("/status-list/{status}")
    public String getByStatus(@PathVariable("status") int status,Model model) {
        String url = "http://localhost:8080/saving";
        List<SoTietKiem> soTietKiems = Arrays.asList(rest.getForObject(url, SoTietKiem[].class));
        model.addAttribute("list", soTietKiems);
        return "nhan-vien-giao-dich/so-tiet-kiem/list.html";
    }

    @PutMapping("/approve/{id}")
    public String update(@PathVariable("id") int id){
        String url = "http://localhost:8080/saving/approve/" + id;
        rest.put(url, Void.class);
        return "nhan-vien-giao-dich/so-tiet-kiem/list.html";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable ("id") int id) {
        rest.delete("http://localhost:8080/saving/{id}",id);
        return "nhan-vien-giao-dich/so-tiet-kiem/list.html";
    }


    @GetMapping("/add")
    public String add(Model model){
        SoTietKiem soTietKiem = new SoTietKiem();
        model.addAttribute("model", soTietKiem);
        return "nhan-vien-giao-dich/so-tiet-kiem/add";
    }

    @PostMapping("/add")
    public String save(SoTietKiem ttd){
        String url = "http://localhost:8080/saving";   
        rest.postForObject(url, ttd, Void.class);
        return "redirect:/nhan-vien-giao-dich/so-tiet-kiem";
    }
}
