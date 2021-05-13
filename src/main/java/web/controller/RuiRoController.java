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
import web.model.RuiRo;

@Slf4j
@Controller
@RequestMapping(path = "/quan-ly/rui-ro" , produces = "application/json" )
public class RuiRoController {
	private RestTemplate rest = new RestTemplate();

    @GetMapping
    public String getAll(Model model){
        String url = "http://localhost:8080/risk";
        List<RuiRo> ruiRos = Arrays.asList(rest.getForObject(url, RuiRo[].class));
        model.addAttribute("list", ruiRos);
        return "quan-ly/rui-ro/list";
    }

    @GetMapping("/edit/{id}")
    public String getAll(@PathVariable("id") int id,Model model){
        String url = "http://localhost:8080/risk/" + id;
        RuiRo ruiRo = rest.getForObject(url, RuiRo.class);
        model.addAttribute("model", ruiRo);
        return "quan-ly/rui-ro/edit";
    }

    @GetMapping("/add")
    public String add(Model model){
        RuiRo ruiRo = new RuiRo();
        model.addAttribute("model", ruiRo);
        return "quan-ly/rui-ro/add";
    }
    @PostMapping("/add")
    public String save(RuiRo rr){
        String url = "http://localhost:8080/risk";   
        rest.postForObject(url, rr, Void.class);
        return "redirect:/quan-ly/rui-ro";
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable("id") int id,RuiRo rr) {
        String url = "http://localhost:8080/risk";
        rest.put(url, rr);
        return "redirect:/quan-ly/rui-ro";
    }
}
