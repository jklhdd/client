package web.controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.env.Environment;
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
import web.model.GoiTinDung;
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

@Slf4j
@Controller
@RequestMapping(path = "/nhan-vien-tin-dung/the-tin-dung", produces = "application/json")
public class TheTinDungController {
	private RestTemplate rest = new RestTemplate();

	@Autowired
	private Environment env;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("MM/dd/yyyy")));
	}

	@ModelAttribute
	public void addService(Model model) {
		List<ThanhVien> thanhViens = Arrays
				.asList(rest.getForObject(env.getProperty("web.server.url") + "/account/customer", ThanhVien[].class));
		model.addAttribute("listKhach", thanhViens);
		String urlGTD = env.getProperty("web.server.url") + "/credit-type";
		List<GoiTinDung> goiTinDungs = Arrays.asList(rest.getForObject(urlGTD, GoiTinDung[].class));
		model.addAttribute("listGoi", goiTinDungs);
	}

	@GetMapping
	public String getAll(Model model) {
		String url = env.getProperty("web.server.url") + "/credit-card";
		List<TheTinDung> theTinDungs = Arrays.asList(rest.getForObject(url, TheTinDung[].class));
		model.addAttribute("list", theTinDungs);
		return "nhan-vien-tin-dung/the-tin-dung/list";
	}

	@GetMapping("/edit/{id}")
	public String getById(@PathVariable("id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/credit-card/detail/" + id;
		TheTinDung theTinDung = rest.getForObject(url, TheTinDung.class);
		model.addAttribute("model", theTinDung);
		return "nhan-vien-tin-dung/the-tin-dung/edit";
	}

	@GetMapping("/{taikhoan_id}")
	public String getByCustomerId(@PathVariable("taikhoan_id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/credit-card/" + id;
		TheTinDung theTinDung = rest.getForObject(url, TheTinDung.class);
		model.addAttribute("model", theTinDung);
		return "nhan-vien-tin-dung/the-tin-dung/edit";
	}

	@GetMapping("/approve/{id}")
	public String update(@PathVariable("id") int id) {
		String url = env.getProperty("web.server.url") + "/credit-card/approve/" + id;
		rest.put(url, Void.class);
		return "redirect:/nhan-vien-tin-dung/the-tin-dung";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		rest.delete(env.getProperty("web.server.url") + "/atm-card/{id}", id);
		return "nhan-vien-tin-dung/the-tin-dung/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		TheTinDung theTinDung = new TheTinDung();
		model.addAttribute("model", theTinDung);
		return "nhan-vien-tin-dung/the-tin-dung/add";
	}

	@PostMapping("/add")
	public String save(TheTinDung ttd) {
		String url = env.getProperty("web.server.url") + "/credit-card";
		rest.postForObject(url, ttd, Void.class);
		return "redirect:/nhan-vien-tin-dung/the-tin-dung";
	}

	@PostMapping("/tra-no")
	public String traNo(GiaoDichDto dto) {
		String url = env.getProperty("web.server.url") + "/credit-card";
		rest.postForObject(url, dto, String.class);
		return "redirect:/nhan-vien-tin-dung/the-tin-dung";
	}

}
