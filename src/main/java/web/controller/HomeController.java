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
public class HomeController {
	private RestTemplate rest = new RestTemplate();

	@GetMapping(value = {"","/login"})
	public String home(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		if (session.getAttribute("account") == null) {
			return "login";
		}
		model.addAttribute("account", thanhVien);
		return "index";
	}

	@PostMapping("/login")
	public String login(@RequestParam("taikhoan") String tk, @RequestParam("matkhau") String mk,
			HttpServletRequest request, Model model) {
		String url = "http://htttqlt5-server.herokuapp.com/account/login/" + tk + "/" + mk;
		ThanhVien thanhVien = rest.getForObject(url, ThanhVien.class);
		if (thanhVien == null) {
			model.addAttribute("model", thanhVien);
			return "login";
		}
		request.getSession().setAttribute("account", thanhVien);
		request.getSession().setMaxInactiveInterval(900);
		return "redirect:/";
	}

	@GetMapping("/register")
	public String registry(Model model) {
		ThanhVien thanhVien = new ThanhVien();
		model.addAttribute("model", thanhVien);
		return "register";
	}
	@PostMapping("/register")
	public String create(Model model,@RequestParam("pass") String mk,@RequestParam("repass") String remk, 
		 ThanhVien tv) {
		tv.setChucvu("KhachHang");
		if(!mk.equals(remk)) {
			model.addAttribute("model", tv);
			return "register";
		}
		String url = "http://htttqlt5-server.herokuapp.com/account/create/" + mk;
		rest.postForObject(url, tv, String.class);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("account");;
		return "redirect:/";
	}
}
