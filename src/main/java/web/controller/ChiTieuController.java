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
import org.springframework.core.env.Environment;
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


import lombok.extern.slf4j.Slf4j;
import web.model.ChiTieu;
import web.ultis.SqlDateEditor;

@Slf4j
@Controller
@RequestMapping(path = "/quan-ly/chi-tieu", produces = "application/json")
public class ChiTieuController {
	private RestTemplate rest = new RestTemplate();

	@Autowired
	private Environment env;
	
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("MM/dd/yyyy")));
	}

	@GetMapping
	public String getAll(Model model) {
		String url = env.getProperty("web.server.url") + "/spend";
		List<ChiTieu> chitieus = Arrays.asList(rest.getForObject(url, ChiTieu[].class));
		model.addAttribute("list", chitieus);
		return "quan-ly/chi-tieu/list";
	}

	@GetMapping("/edit/{id}")
	public String detail(@PathVariable("id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/spend/" + id;
		ChiTieu chitieu = rest.getForObject(url, ChiTieu.class);
		model.addAttribute("model", chitieu);
		return "quan-ly/chi-tieu/edit";
	}

	@GetMapping("/add")
	public String add(Model model) {
		ChiTieu chitieu = new ChiTieu();
		model.addAttribute("model", chitieu);
		return "quan-ly/chi-tieu/add";
	}

	@PostMapping("/add")
	public String save(ChiTieu chitieu) {
		String url = env.getProperty("web.server.url") + "/spend";
		rest.postForObject(url, chitieu, Void.class);
		return "redirect:/quan-ly/chi-tieu";
	}
}
