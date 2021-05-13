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
import web.model.GoiTinDung;


import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/quan-ly/goi-tin-dung" , produces = "application/json" )
public class GoiTinDungController {
	private RestTemplate rest = new RestTemplate();

    @GetMapping
    public String getAll(Model model){
        String url = "https://htttqlt5-server.herokuapp.com/credit-type";
        List<GoiTinDung> goiTinDungs = Arrays.asList(rest.getForObject(url, GoiTinDung[].class));
        model.addAttribute("list", goiTinDungs);
        return "quan-ly/goi-tin-dung/list.html";
    }

    @GetMapping("/edit/{id}")
    public String getAll(@PathVariable("id") int id,Model model){
        String url = "https://htttqlt5-server.herokuapp.com/credit-type/" + id;
        GoiTinDung goiTinDung = rest.getForObject(url, GoiTinDung.class);
        model.addAttribute("model", goiTinDung);
        return "quan-ly/goi-tin-dung/edit";
    }

    @GetMapping("/add")
    public String add(Model model){
        GoiTinDung goiTinDung = new GoiTinDung();
        model.addAttribute("model", goiTinDung);
        return "quan-ly/goi-tin-dung/add";
    }

    @PostMapping("/add")
    public String save(GoiTinDung gtd){
        String url = "https://htttqlt5-server.herokuapp.com/credit-type";
        rest.postForObject(url, gtd, Void.class);
        return "redirect:/quan-ly/goi-tin-dung";
    }
}
