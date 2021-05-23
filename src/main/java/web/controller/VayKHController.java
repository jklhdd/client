package web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;
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
import web.dto.ThanhVien;
import web.model.*;

@Slf4j
@Controller
@RequestMapping(path = "/khach-hang/vay")
public class VayKHController {
	private RestTemplate rest = new RestTemplate();

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("MM/dd/yyyy")));
	}

	@GetMapping
	public String getAll(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String url = "https://htttqlt5-server.herokuapp.com/loan/" + thanhVien.getId();
		Vay vay = null;
		try {
			vay = rest.getForObject(url, Vay.class);
		} catch (Exception ex) {

		}
		List<Vay> vays = new ArrayList();
		if (vay != null)
			vays.add(vay);
		model.addAttribute("list", vays);
		return "khach-hang/vay/list.html";
	}

	@GetMapping("/{taikhoan_id}")
	public String getAllByCustomerId(@PathVariable("taikhoan_id") int id, Model model) {
		Vay vay = rest.getForObject("https://htttqlt5-server.herokuapp.com/loan/" + id, Vay.class);
		model.addAttribute("model", vay);
		return "khach-hang/vay/edit.html";
	}

	@GetMapping("/add")
	public String vayMoi(Model model) {
		Vay vay = new Vay();
		model.addAttribute("model", vay);
		return "khach-hang/vay/add.html";
	}

	@PostMapping("/add")
	public String save(Vay vay, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		TaiKhoan tk = rest.getForObject("https://htttqlt5-server.herokuapp.com/account/" + thanhVien.getId(), TaiKhoan.class);
		vay.setTk(tk);
		vay.setStatus(0);
		rest.postForObject("https://htttqlt5-server.herokuapp.com/loan", vay, Void.class);
		return "redirect:/khach-hang/vay";
	}

	@GetMapping("/tra-no")
	public String traNo(Model model, HttpSession session) {
		ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
		String urlVay = "https://htttqlt5-server.herokuapp.com/loan/" + thanhVien.getId();
		Vay vay = null;
		try {
			vay = rest.getForObject(urlVay, Vay.class);
		} catch (Exception ex) {

		}
		List<Vay> vays = new ArrayList();
		if (vay != null)
			vays.add(vay);
		model.addAttribute("listVay", vays);

		String urlNH = "https://htttqlt5-server.herokuapp.com/atm-card/" + thanhVien.getId();
		TheNganHang theNH = rest.getForObject(urlNH, TheNganHang.class);
		List<TheNganHang> theNganHangs = new ArrayList<TheNganHang>();
		if (theNH != null)
			theNganHangs.add(theNH);

		model.addAttribute("listCard", theNganHangs);

		GiaoDichDto dto = new GiaoDichDto();
		model.addAttribute("model", dto);
		return "khach-hang/vay/tra-no";
	}

	@PostMapping("/tra-no")
	public String ptraNo(@ModelAttribute("model") GiaoDichDto dto, Model model, HttpSession session) {
		String url = "https://htttqlt5-server.herokuapp.com/loan/tra-no";
		String msg = rest.postForObject(url, dto, String.class);
		if (!msg.equals("Tra no thanh cong!")) {
			ThanhVien thanhVien = (ThanhVien) session.getAttribute("account");
			String urlVay = "https://htttqlt5-server.herokuapp.com/loan/" + thanhVien.getId();
			Vay vay = null;
			try {
				vay = rest.getForObject(urlVay, Vay.class);
			} catch (Exception ex) {

			}
			List<Vay> vays = new ArrayList();
			if (vay != null)
				vays.add(vay);
			model.addAttribute("listVay", vays);

			String urlNH = "https://htttqlt5-server.herokuapp.com/atm-card/" + thanhVien.getId();
			TheNganHang theNH = rest.getForObject(urlNH, TheNganHang.class);
			List<TheNganHang> theNganHangs = new ArrayList<TheNganHang>();
			if (theNH != null)
				theNganHangs.add(theNH);
			model.addAttribute("listCard", theNganHangs);
			model.addAttribute("model", dto);
			model.addAttribute("msg", msg);
			return "khach-hang/vay/tra-no";
		}
		model.addAttribute("msg", msg);
		return "redirect:/khach-hang/vay";
	}
}
