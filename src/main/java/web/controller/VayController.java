package web.controller;
import java.util.Arrays;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import web.ultis.SqlDateEditor;
import lombok.extern.slf4j.Slf4j;
import web.dto.GiaoDichDto;
import web.model.*;

@Slf4j
@Controller
@RequestMapping(path = "/nhan-vien-tin-dung/vay")
public class VayController {
    private RestTemplate rest = new RestTemplate();
    
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("dd/MM/yyyy")));
    }
    
    @GetMapping
    public String getAll(Model model){
	List<Vay> vays = Arrays
				.asList(rest.getForObject("http://localhost:8080/loan", Vay[].class));
		model.addAttribute("list",vays);
        return "nhan-vien-tin-dung/vay/list.html";
    }

    @GetMapping("/{taikhoan_id}")
    public String getAllByCustomerId(@PathVariable("taikhoan_id") int id, Model model){
	Vay vay = rest.getForObject("http://localhost:8080/loan/"+id, Vay.class);
	model.addAttribute("model",vay);
        return "nhan-vien-tin-dung/vay/edit.html";
    }

    @GetMapping("/add")
    public String vayMoi(Model model){
	Vay vay = new Vay();
	model.addAttribute("model",vay);
        return "nhan-vien-tin-dung/vay/add.html";
    }

    @PostMapping("/add")
    public String save(Vay vay){
       	rest.postForObject("http://localhost:8080/loan", vay, Vay.class);
	return "nhan-vien-tin-dung/vay/list.html";
    }

    @PostMapping("/tra-no")
    public String traNo(GiaoDichDto dto){
        String url = "http://localhost:8080/loan/tra-no";   
        rest.postForObject(url, dto, String.class);
        return "nhan-vien-tin-dung/vay/list.html";
    }
}
