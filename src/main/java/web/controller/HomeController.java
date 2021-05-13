package web.controller;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import web.dto.ThanhVien;

@Slf4j
@Controller
@RequestMapping(path = "/")
@SessionAttributes("account")
public class HomeController {
	private RestTemplate rest = new RestTemplate();

	@GetMapping(value = {"","/login"})
	public String home(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		if (thanhVien == null) {
			return "login";
		}
		model.addAttribute("account", thanhVien);
		return "index";
	}

	@PostMapping("/login")
	public String login(@RequestParam("taikhoan") String tk, @RequestParam("matkhau") String mk,
			HttpServletRequest request) {
		String url = "https://htttqlt5-server.herokuapp.com/account/login/" + tk + "/" + mk;
		ThanhVien thanhVien = rest.getForObject(url, ThanhVien.class);
		if (thanhVien == null) {
			return "login";
		}
		request.getSession().setAttribute("account", thanhVien);
		request.getSession().setMaxInactiveInterval(900);
		return "index";
	}

	@GetMapping("/create")
	public String registry(Model model) {
		ThanhVien thanhVien = new ThanhVien();
		model.addAttribute("thanhVien", thanhVien);
		return "register";
	}
	@PostMapping("/create/{pass}")
	public String create(@PathVariable("pass") String mk, @RequestBody ThanhVien tv) {
		String url = "https://htttqlt5-server.herokuapp.com/account/create/" + mk;
		rest.postForObject(url, tv, ThanhVien.class);
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		request.getSession().removeAttribute("account");;
		return "redirect:/";
	}
}
