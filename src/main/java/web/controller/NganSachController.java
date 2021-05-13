package web.controller;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
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
import web.model.NganSach;
import web.ultis.SqlDateEditor;


@Slf4j
@Controller
@RequestMapping(path = "/quan-ly/ngan-sach" , produces = "application/json" )
public class NganSachController {
	private RestTemplate rest = new RestTemplate();
    
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("dd/MM/yyyy")));
    }

    @GetMapping
    public String getAll(Model model){
        String url = "https://htttqlt5-server.herokuapp.com/budget";
        List<NganSach> nganSachs = Arrays.asList(rest.getForObject(url, NganSach[].class));
        model.addAttribute("list", nganSachs);
        return "quan-ly/ngan-sach/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model){
        String url = "https://htttqlt5-server.herokuapp.com/budget/" + id;
        NganSach nganSach = rest.getForObject(url, NganSach.class);
        model.addAttribute("model", nganSach);
        return "quan-ly/ngan-sach/edit";
    }

    @GetMapping("/{month}/{year}")
    public String getAllByMonthAndYear(@PathVariable("month") int month, @PathVariable("year") int year,Model model){
        String url = "https://htttqlt5-server.herokuapp.com/budget/" + month + "/" + year;
        NganSach nganSach = rest.getForObject(url, NganSach.class);
        model.addAttribute("model", nganSach);
        return "quan-ly/ngan-sach/edit";
    }

    @GetMapping("/add")
    public String add(Model model){
        NganSach nganSach = new NganSach();
        model.addAttribute("model", nganSach);
        return "quan-ly/ngan-sach/add";
    }

    @PostMapping("/add")
    public String save(NganSach ns){
        String url = "https://htttqlt5-server.herokuapp.com/budget";
        rest.postForObject(url, ns, Void.class);
        return "redirect:/quan-ly/ngan-sach";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") int id, NganSach ns) {
        String url = "https://htttqlt5-server.herokuapp.com/budget/" + id;
        rest.put(url, ns);
        return "redirect:/quan-ly/ngan-sach";
    }
}
