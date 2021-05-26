package web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import lombok.extern.slf4j.Slf4j;
import web.dto.ThanhVien;
import web.model.Luong;

@Slf4j
@Controller
@RequestMapping(path = "/nhan-vien/luong", produces = "application/json")
public class LuongNVController {
	private RestTemplate rest = new RestTemplate();

	@Autowired
	private Environment env;

	@ModelAttribute
	public void addService(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		List<ThanhVien> thanhViens = Arrays
				.asList(rest.getForObject(env.getProperty("web.server.url") + "/account/staff", ThanhVien[].class));
		model.addAttribute("listStaff", thanhViens);
	}

	@GetMapping
	public String getAll(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String url = env.getProperty("web.server.url") + "/salary/staff/" + thanhVien.getId();
		Luong luong = null;
		try {
			luong = rest.getForObject(url, Luong.class);
		} catch (Exception ex) {

		}
		List<Luong> luongs = new ArrayList();
		if (luong != null)
			luongs.add(luong);
		model.addAttribute("list", luongs);
		return "nhan-vien/luong/list";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/salary/" + id;
		Luong luong = rest.getForObject(url, Luong.class);
		model.addAttribute("model", luong);
		return "nhan-vien/luong/edit";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Luong luong = new Luong();
		model.addAttribute("model", luong);
		return "nhan-vien/luong/add";
	}

	@PostMapping("/add")
	public String save(Luong luong) {
		String url = env.getProperty("web.server.url") + "/salary";
		rest.postForObject(url, luong, Void.class);
		return "redirect:/nhan-vien/luong";
	}

	@PutMapping("/edit/{id}")
	public String update(Luong luong, @PathVariable("id") int id) {
		String url = env.getProperty("web.server.url") + "/salary/" + id;
		rest.put(url, luong);
		return "redirect:/nhan-vien/luong";
	}
}
