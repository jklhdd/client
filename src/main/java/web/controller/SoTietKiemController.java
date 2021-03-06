package web.controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.env.Environment;
import web.dto.ThanhVien;
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
@RequestMapping(path = "/nhan-vien-giao-dich/so-tiet-kiem", produces = "application/json")
public class SoTietKiemController {
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
	}

	@GetMapping
	public String getAll(Model model) {
		String url = env.getProperty("web.server.url") + "/saving";
		List<SoTietKiem> soTietKiems = Arrays.asList(rest.getForObject(url, SoTietKiem[].class));
		model.addAttribute("list", soTietKiems);
		return "nhan-vien-giao-dich/so-tiet-kiem/list.html";
	}

	@GetMapping("/edit/{id}")
	public String getById(@PathVariable("id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/saving/" + id;
		SoTietKiem soTietKiem = rest.getForObject(url, SoTietKiem.class);
		model.addAttribute("model", soTietKiem);
		return "nhan-vien-giao-dich/so-tiet-kiem/edit.html";
	}

	@GetMapping("/{taikhoan_id}")
	public String getAllByCustomerId(@PathVariable("taikhoan_id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/saving/" + id;
		SoTietKiem soTietKiem = rest.getForObject(url, SoTietKiem.class);
		model.addAttribute("model", soTietKiem);
		return "nhan-vien-giao-dich/so-tiet-kiem/edit.html";
	}

	@GetMapping("/status-list/{status}")
	public String getByStatus(@PathVariable("status") int status, Model model) {
		String url = env.getProperty("web.server.url") + "/saving";
		List<SoTietKiem> soTietKiems = Arrays.asList(rest.getForObject(url, SoTietKiem[].class));
		model.addAttribute("list", soTietKiems);
		return "nhan-vien-giao-dich/so-tiet-kiem/list.html";
	}

	@GetMapping("/approve/{id}")
	public String update(@PathVariable("id") int id) {
		String url = env.getProperty("web.server.url") + "/saving/approve/" + id;
		try {
			rest.put(url, Void.class);
		} catch(Exception ex) {
			
		}
		return "redirect:/nhan-vien-giao-dich/so-tiet-kiem";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		rest.delete(env.getProperty("web.server.url") + "/saving/{id}", id);
		return "nhan-vien-giao-dich/so-tiet-kiem/list.html";
	}

	@GetMapping("/add")
	public String add(Model model) {
		SoTietKiem soTietKiem = new SoTietKiem();
		model.addAttribute("model", soTietKiem);
		return "nhan-vien-giao-dich/so-tiet-kiem/add";
	}

	@PostMapping("/add")
	public String save(SoTietKiem ttd) {
		String url = env.getProperty("web.server.url") + "/saving";
		rest.postForObject(url, ttd, Void.class);
		return "redirect:/nhan-vien-giao-dich/so-tiet-kiem";
	}
}
