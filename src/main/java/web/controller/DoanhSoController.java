package web.controller;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import web.model.DoanhSo;
import web.ultis.SqlDateEditor;



@Controller
@RequestMapping(path = "/quan-ly/doanh-so" , produces = "application/json" )
public class DoanhSoController {
	private RestTemplate rest = new RestTemplate();

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("dd/MM/yyyy")));
    }

    @GetMapping
    public String getAll(Model model){
        String url = "http://localhost:8080/sale";
        List<DoanhSo> doanhSos = Arrays.asList(rest.getForObject(url, DoanhSo[].class));
        model.addAttribute("list", doanhSos);
        return "quan-ly/doanh-so/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model){
        String url = "http://localhost:8080/sale/" + id;
        DoanhSo doanhSo = rest.getForObject(url, DoanhSo.class);
        model.addAttribute("model", doanhSo);
        return "quan-ly/doanh-so/edit";

    }

    @GetMapping("/add")
    public String add(Model model){
        DoanhSo doanhSo = new DoanhSo();
        model.addAttribute("model", doanhSo);
        return "quan-ly/doanh-so/add";
    }

    @PostMapping("/add")
    public String save(@RequestBody DoanhSo ds){
        String url = "http://localhost:8080/sale";
        rest.postForObject(url, ds, Void.class);
        return "redirect:/quan-ly/doanh-so";
    }
}
