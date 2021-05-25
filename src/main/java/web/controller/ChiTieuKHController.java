package web.controller;
import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import web.dto.ThanhVien;
import web.model.ChiTieuKH;
import web.model.TaiKhoan;

@Slf4j
@Controller
@RequestMapping(path = "/khach-hang/chi-tieu", produces = "application/json")
public class ChiTieuKHController {
	private RestTemplate rest = new RestTemplate();

	@GetMapping
	public String getAll(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String url = "http://htttqlt5-server.herokuapp.com/spend-customer/all/"+thanhVien.getId();
		List<ChiTieuKH> chitieus = Arrays.asList(rest.getForObject(url, ChiTieuKH[].class));
		model.addAttribute("list", chitieus);
		return "khach-hang/chi-tieu/list.html";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id,Model model) {
		String url = "http://htttqlt5-server.herokuapp.com/spend-customer/" + id;
		ChiTieuKH chitieu = rest.getForObject(url, ChiTieuKH.class);
		model.addAttribute("model", chitieu);
		return "khach-hang/chi-tieu/edit";
	}

	@GetMapping("/add")
	public String add(Model model) {
		ChiTieuKH chitieu = new ChiTieuKH();
		model.addAttribute("model", chitieu);
		return "khach-hang/chi-tieu/add";
	}

	@PostMapping("/add")
	public String save(ChiTieuKH chitieukh, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String url = "http://htttqlt5-server.herokuapp.com/spend-customer";
		TaiKhoan tk = rest.getForObject("http://htttqlt5-server.herokuapp.com/account/" + thanhVien.getId(), TaiKhoan.class);
		chitieukh.setTk(tk);
		rest.postForObject(url, chitieukh, Void.class);
		return "redirect:/khach-hang/chi-tieu";
	}
}
