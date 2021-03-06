package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.core.env.Environment;
import web.dto.ThanhVien;
import web.model.HoTen;
import web.model.TaiKhoan;
import web.model.ThongTinCaNhan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/quan-ly/khach-hang", produces = "application/json")
public class KhachHangController {
	private RestTemplate rest = new RestTemplate();

	@Autowired
	private Environment env;

	@GetMapping
	public String getAll(Model model) {
		String url = env.getProperty("web.server.url") + "/account";
		List<ThanhVien> thanhViens = Arrays.asList(rest.getForObject(url, ThanhVien[].class));
		model.addAttribute("list", thanhViens);
		return "quan-ly/khach-hang/list";
	}

	@GetMapping("/staff")
	public String getAllStaff(Model model) {
		String url = env.getProperty("web.server.url") + "/account/staff";
		List<ThanhVien> thanhViens = Arrays.asList(rest.getForObject(url, ThanhVien[].class));
		model.addAttribute("list", thanhViens);
		return "quan-ly/khach-hang/list";
	}

	@GetMapping("/edit/{id}")
	public String getAccountById(@PathVariable int id, Model model) {
		List<String> listChucVu = new ArrayList();
		listChucVu.add("KhachHang");
		listChucVu.add("Nhan Vien Giao Dich");
		listChucVu.add("Nhan Vien Tin Dung");
		listChucVu.add("QuanLy");
		model.addAttribute("listChucVu", listChucVu);
		String url = env.getProperty("web.server.url") + "/account/search/" + id;
		TaiKhoan thanhVien = rest.getForObject(url, TaiKhoan.class);
		model.addAttribute("model", thanhVien);
		return "quan-ly/khach-hang/edit";
	}

	@GetMapping("/customer")
	public String getAllCustomer(Model model) {
		String url = env.getProperty("web.server.url") + "/account/customer";
		List<ThanhVien> thanhViens = Arrays.asList(rest.getForObject(url, ThanhVien[].class));
		model.addAttribute("list", thanhViens);
		return "quan-ly/khach-hang/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		List<String> listChucVu = new ArrayList();
		listChucVu.add("KhachHang");
		listChucVu.add("Nhan Vien Giao Dich");
		listChucVu.add("Nhan Vien Tin Dung");
		listChucVu.add("QuanLy");
		model.addAttribute("listChucVu", listChucVu);
		ThanhVien thanhVien = new ThanhVien();
		model.addAttribute("model", thanhVien);
		return "quan-ly/khach-hang/add";
	}

	@PostMapping("/add")
	public String save(ThanhVien tv, @RequestParam("pass") String mk) {
		String url = env.getProperty("web.server.url") + "/account/create/" + mk;
		rest.postForObject(url, tv, String.class);
		return "redirect:/quan-ly/khach-hang";
	}

	@PutMapping("/edit/{id}")
	public String update(TaiKhoan thanhVien, @PathVariable("id") int id) {
		String url = env.getProperty("web.server.url") + "/account";
		rest.put(url, thanhVien);
		return "redirect:/quan-ly/khach-hang";
	}
}
