package web.controller;
import java.util.Arrays;
import java.util.List;

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
import lombok.extern.slf4j.Slf4j;
import web.model.Luong;

@Slf4j
@Controller
@RequestMapping(path = "/quan-ly/luong" , produces = "application/json" )
public class LuongController {
	private RestTemplate rest = new RestTemplate();

    @GetMapping
    public String getAll(Model model){
        String url = "http://localhost:8080/salary";
        List<Luong> luongs = Arrays.asList(rest.getForObject(url, Luong[].class));
        model.addAttribute("list", luongs);
        return "quan-ly/luong/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model){
        String url = "http://localhost:8080/salary/" + id;
        Luong luong = rest.getForObject(url, Luong.class);
        model.addAttribute("model", luong);
        return "quan-ly/luong/edit";
    }

    @GetMapping("/add")
    public String add(Model model){
        Luong luong = new Luong();
        model.addAttribute("model", luong);
        return "quan-ly/luong/add";
    }
    @PostMapping
    public String save(@RequestBody Luong luong){
        String url = "http://localhost:8080/salary";   
        rest.postForObject(url, luong, Void.class);
        return "redirect:/quan-ly/luong";
    }

    @PutMapping("/edit/{id}")
    public String update(Luong luong, @PathVariable("id") int id) {
        String url = "http://localhost:8080/salary/"+id;
        rest.put(url, luong);
        return "redirect:/quan-ly/luong";
    }
}
