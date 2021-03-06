package web.controller;

import java.util.ArrayList;
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
import web.model.TaiKhoan;
import web.model.TheNganHang;

import java.text.SimpleDateFormat;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import web.ultis.SqlDateEditor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(path = "/khach-hang/the-ngan-hang", produces = "application/json")
public class TheNganHangKHController {
	private RestTemplate rest = new RestTemplate();

	@Autowired
	private Environment env;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("MM/dd/yyyy")));
	}

	@GetMapping
	public String getAll(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String url = env.getProperty("web.server.url") + "/atm-card/" + thanhVien.getId();
		TheNganHang theNH = rest.getForObject(url, TheNganHang.class);
		List<TheNganHang> theNganHangs = new ArrayList<TheNganHang>();
		if (theNH != null)
			theNganHangs.add(theNH);
		model.addAttribute("list", theNganHangs);
		return "khach-hang/the-ngan-hang/list";
	}

	@GetMapping("/edit/{id}")
	public String getById(@PathVariable("id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/atm-card/detail/" + id;
		TheNganHang theNganHang = rest.getForObject(url, TheNganHang.class);
		model.addAttribute("model", theNganHang);
		return "khach-hang/the-ngan-hang/edit";
	}

	@GetMapping("/edit1/{taikhoan_id}")
	public String getAllByCustomerId(@PathVariable("taikhoan_id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/atm-card/" + id;
		TheNganHang theNganHang = rest.getForObject(url, TheNganHang.class);
		model.addAttribute("model", theNganHang);
		return "khach-hang/the-ngan-hang/edit";
	}

	@GetMapping("/status-list/{status}")
	public String getByStatus(@PathVariable("status") int status, Model model) {
		String url = env.getProperty("web.server.url") + "/atm-card/status-list/" + status;
		List<TheNganHang> theNganHangs = Arrays.asList(rest.getForObject(url, TheNganHang[].class));
		model.addAttribute("list", theNganHangs);
		return "khach-hang/the-ngan-hang/list";
	}

	@PutMapping("/approve/{id}")
	public String update(@PathVariable("id") int id) {
		String url = env.getProperty("web.server.url") + "/atm-card/approve/" + id;
		rest.put(url, Void.class);
		return "khach-hang/the-ngan-hang/list";
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id) {
		rest.delete(env.getProperty("web.server.url") + "/atm-card/{id}", id);
		return "khach-hang/the-ngan-hang/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		TheNganHang theNganHang = new TheNganHang();
		model.addAttribute("model", theNganHang);
		return "khach-hang/the-ngan-hang/add";
	}

	@PostMapping("/add")
	public String save(TheNganHang theNganHang, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		TaiKhoan tk = rest.getForObject(env.getProperty("web.server.url") + "/account/" + thanhVien.getId(), TaiKhoan.class);
		String url = env.getProperty("web.server.url") + "/atm-card";
		theNganHang.setTk(tk);
		theNganHang.setStatus(0);
		rest.postForObject(url, theNganHang, Void.class);
		return "redirect:/khach-hang/the-ngan-hang";
	}

	@PostMapping("/giao-dich")
	public String giaoDichTien(GiaoDichDto dto) {
		String url = env.getProperty("web.server.url") + "/atm-card";
		rest.postForObject(url, dto, Void.class);
		return "redirect:/khach-hang/the-ngan-hang";
	}

	@GetMapping("/chuyen-tien")
	public String chuyenTien(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String url = env.getProperty("web.server.url") + "/atm-card/" + thanhVien.getId();
		TheNganHang theNH = rest.getForObject(url, TheNganHang.class);
		List<TheNganHang> theNganHangs = new ArrayList<TheNganHang>();
		if (theNH != null)
			theNganHangs.add(theNH);
		GiaoDichDto dto = new GiaoDichDto();
		model.addAttribute("model", dto);
		model.addAttribute("listCard", theNganHangs);
		return "khach-hang/the-ngan-hang/chuyen-tien";
	}

	@PostMapping("/chuyen-tien")
	public String pchuyenTien(GiaoDichDto dto) {
		dto.setPhuongThuc("Chuyen Tien");
		String url = env.getProperty("web.server.url") + "/atm-card/giao-dich";
		rest.postForObject(url, dto, Void.class);
		return "redirect:/khach-hang/the-ngan-hang";
	}

	@GetMapping("/rut-tien")
	public String rutTien(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String url = env.getProperty("web.server.url") + "/atm-card/" + thanhVien.getId();
		TheNganHang theNH = rest.getForObject(url, TheNganHang.class);
		List<TheNganHang> theNganHangs = new ArrayList<TheNganHang>();
		if (theNH != null)
			theNganHangs.add(theNH);
		GiaoDichDto dto = new GiaoDichDto();
		model.addAttribute("model", dto);
		model.addAttribute("listCard", theNganHangs);
		return "khach-hang/the-ngan-hang/rut-tien";
	}

	@PostMapping("/rut-tien")
	public String prutTien(GiaoDichDto dto) {
		dto.setPhuongThuc("Rut Tien");
		String url = env.getProperty("web.server.url") + "/atm-card/giao-dich";
		rest.postForObject(url, dto, Void.class);
		return "redirect:/khach-hang/the-ngan-hang";
	}

	@GetMapping("/gui-tien")
	public String guiTien(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String url = env.getProperty("web.server.url") + "/atm-card/" + thanhVien.getId();
		TheNganHang theNH = rest.getForObject(url, TheNganHang.class);
		List<TheNganHang> theNganHangs = new ArrayList<TheNganHang>();
		if (theNH != null)
			theNganHangs.add(theNH);
		GiaoDichDto dto = new GiaoDichDto();
		model.addAttribute("model", dto);
		model.addAttribute("listCard", theNganHangs);
		return "khach-hang/the-ngan-hang/gui-tien";
	}

	@PostMapping("/gui-tien")
	public String pguiTien(GiaoDichDto dto) {
		dto.setPhuongThuc("Gui Tien");
		String url = env.getProperty("web.server.url") + "/atm-card/giao-dich";
		rest.postForObject(url, dto, Void.class);
		return "redirect:/khach-hang/the-ngan-hang";
	}
}
