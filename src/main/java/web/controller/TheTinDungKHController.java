package web.controller;
import java.util.ArrayList;
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
import web.dto.ThanhVien;
import web.model.GiaoDich;
import web.model.TaiKhoan;
import web.model.TheNganHang;
import web.model.TheTinDung;

import java.text.SimpleDateFormat;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import web.ultis.SqlDateEditor;
import lombok.extern.slf4j.Slf4j;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(path = "/khach-hang/the-tin-dung" , produces = "application/json" )
public class TheTinDungKHController {
	private RestTemplate rest = new RestTemplate();
    
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("MM/dd/yyyy")));
    }
    
    @GetMapping
    public String getAll(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
        String url = "https://htttqlt5-server.herokuapp.com/credit-card/"+thanhVien.getId();
        TheTinDung theTinDung = null;
        try {
            theTinDung = rest.getForObject(url, TheTinDung.class);
        } catch(Exception ex) {
        	
        }
        List<TheTinDung> theTinDungs = new ArrayList();
        if(theTinDung!= null)theTinDungs.add(theTinDung);
        model.addAttribute("list", theTinDungs);
        return "khach-hang/the-tin-dung/list";
    }

    @GetMapping("/edit/{id}")
    public String getById(@PathVariable("id") int id,Model model){
        String url = "https://htttqlt5-server.herokuapp.com/credit-card/detail/" + id;
        TheTinDung theTinDung = rest.getForObject(url, TheTinDung.class);
        model.addAttribute("model", theTinDung);
        return "khach-hang/the-tin-dung/edit";
    }

    @GetMapping("/{taikhoan_id}")
    public String getByCustomerId(@PathVariable("taikhoan_id") int id,Model model){
        String url = "https://htttqlt5-server.herokuapp.com/credit-card/" + id;
        TheTinDung theTinDung = rest.getForObject(url, TheTinDung.class);
        model.addAttribute("model", theTinDung);
        return "khach-hang/the-tin-dung/edit";
    }

    @PutMapping("/approve/{id}")
    public String update(@PathVariable("id") int id){
        String url = "https://htttqlt5-server.herokuapp.com/credit-card/approve/"+id;
        rest.put(url, Void.class);
        return "khach-hang/the-tin-dung/list";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable ("id") int id) {
        rest.delete("https://htttqlt5-server.herokuapp.com/atm-card/{id}",id);
        return "khach-hang/the-tin-dung/list";
    }

    @GetMapping("/add")
    public String add(Model model){
        TheTinDung theTinDung  = new TheTinDung();
        model.addAttribute("model", theTinDung);
        return "khach-hang/the-tin-dung/add";
    }
    @PostMapping("/add")
    public String save(TheTinDung ttd, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		TaiKhoan tk = rest.getForObject("https://htttqlt5-server.herokuapp.com/account/" + thanhVien.getId(), TaiKhoan.class);
		ttd.setTk(tk);
		ttd.setStatus(0);
        String url = "https://htttqlt5-server.herokuapp.com/credit-card";
        rest.postForObject(url, ttd, Void.class);
        return "redirect:/khach-hang/the-tin-dung";
    }

    @PostMapping("/tra-no")
    public String traNo(GiaoDichDto dto){
        String url = "https://htttqlt5-server.herokuapp.com/credit-card";
        rest.postForObject(url, dto, String.class);
        return "redirect:/khach-hang/the-tin-dung";
    }

}
