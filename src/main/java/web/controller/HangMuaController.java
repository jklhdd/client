package web.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import web.model.ChiTieu;
import web.model.HangMua;

@Slf4j
@Controller
@RequestMapping(path = "/quan-ly/hang-mua", produces = "application/json")
public class HangMuaController {
	private RestTemplate rest = new RestTemplate();

	@GetMapping
	public String getAll(@ModelAttribute("msg") String msg, Model model) {
		String url = "http://localhost:8080/product";
		List<HangMua> chitieus = Arrays.asList(rest.getForObject(url, HangMua[].class));
		model.addAttribute("list", chitieus);
		model.addAttribute("msg", msg);
		return "quan-ly/hang-mua/list";
	}

	@GetMapping("/buy/{hang_id}")
	public String buyItem(@PathVariable("tk_id") int tk_id, @PathVariable("hang_id") int hang_id,
			RedirectAttributes redirectAttributes) {
		String url = "http://localhost:8080/product/buy/" + tk_id + "-" + hang_id;
		String msg = rest.getForObject(url, String.class);
		redirectAttributes.addFlashAttribute("msg", msg);
		return "redirect:/quan-ly/hang-mua";
	}

	@GetMapping("/add")
	public String add(Model model) {
		HangMua hangMua = new HangMua();
		model.addAttribute("model", hangMua);
		return "quan-ly/hang-mua/add";
	}

	@PostMapping("/add")
	public String save(HangMua hangMua, RedirectAttributes redirectAttributes) {
		String url = "http://localhost:8080/product";
		String msg = rest.postForObject(url, hangMua, String.class);
		redirectAttributes.addFlashAttribute("msg", msg);
		return "redirect:/quan-ly/hang-mua";
	}
}
