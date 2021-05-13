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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import web.model.DauTu;


@Slf4j
@Controller
@RequestMapping(path = "/quan-ly/dau-tu" , produces = "application/json" )
public class DauTuController {
	private RestTemplate rest = new RestTemplate();

    @GetMapping
    public String getAll(Model model){
        String url = "https://htttqlt5-server.herokuapp.com/invest";
        List<DauTu> dauTus = Arrays.asList(rest.getForObject(url, DauTu[].class));
        model.addAttribute("list", dauTus);
        return "quan-ly/dau-tu/list.html";
    }

    @GetMapping("/add")
    public String add(Model model){
        DauTu dauTu = new DauTu();
        model.addAttribute("model", dauTu);
        return "quan-ly/dau-tu/add";
    }
    @PostMapping("/add")
    public String save(DauTu dt){
        String url = "https://htttqlt5-server.herokuapp.com/invest";
        rest.postForObject(url, dt, DauTu.class);
		return "redirect:/quan-ly/dau-tu";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        String url = "https://htttqlt5-server.herokuapp.com/invest/" + id;
        DauTu dauTu = rest.getForObject(url, DauTu.class);
        model.addAttribute("model", dauTu);
        return "quan-ly/dau-tu/edit";
    }


    @PutMapping("/edit/{id}")
    public String update(DauTu dt,@PathVariable("id") int id) {
        String url = "https://htttqlt5-server.herokuapp.com/invest/" + id;
        rest.put(url, dt);
		return "redirect:/quan-ly/dau-tu";
    }
}
