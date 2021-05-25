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
import web.dto.ThanhVien;
import web.model.Luong;

@Slf4j
@Controller
@RequestMapping(path = "/quan-ly/luong", produces = "application/json")
public class LuongController {
	private RestTemplate rest = new RestTemplate();

	@ModelAttribute
	public void addService(Model model) {
		List<ThanhVien> thanhViens = Arrays
				.asList(rest.getForObject("http://htttqlt5-server.herokuapp.com/account/staff", ThanhVien[].class));
		model.addAttribute("listStaff", thanhViens);
	}

	@GetMapping
	public String getAll(Model model) {
		String url = "http://htttqlt5-server.herokuapp.com/salary";
		List<Luong> luongs = Arrays.asList(rest.getForObject(url, Luong[].class));
		model.addAttribute("list", luongs);
		return "quan-ly/luong/list";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		String url = "http://htttqlt5-server.herokuapp.com/salary/" + id;
		Luong luong = rest.getForObject(url, Luong.class);
		model.addAttribute("model", luong);
		return "quan-ly/luong/edit";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Luong luong = new Luong();
		model.addAttribute("model", luong);
		return "quan-ly/luong/add";
	}

	@PostMapping("/add")
	public String save(Luong luong) {
		String url = "http://htttqlt5-server.herokuapp.com/salary";
		rest.postForObject(url, luong, Void.class);
		return "redirect:/quan-ly/luong";
	}

	@PutMapping("/edit/{id}")
	public String update(Luong luong, @PathVariable("id") int id) {
		String url = "http://htttqlt5-server.herokuapp.com/salary/" + id;
		rest.put(url, luong);
		return "redirect:/quan-ly/luong";
	}
}
