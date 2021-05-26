package web.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
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
import web.model.DoanhSo;
import web.ultis.SqlDateEditor;

@Controller
@RequestMapping(path = "/quan-ly/doanh-so", produces = "application/json")
public class DoanhSoController {
	private RestTemplate rest = new RestTemplate();

	@Autowired
	private Environment env;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(java.sql.Date.class, new SqlDateEditor(new SimpleDateFormat("MM/dd/yyyy")));
	}

	@GetMapping
	public String getAll(Model model) {
		String url = env.getProperty("web.server.url") + "/sale";
		List<DoanhSo> doanhSos = Arrays.asList(rest.getForObject(url, DoanhSo[].class));
		model.addAttribute("list", doanhSos);
		return "quan-ly/doanh-so/list";
	}

	@PostMapping("/search")
	public String search(Model model, @RequestParam("searchDate") String msg) {
		String[] date = msg.split(" - ");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		Date startDate = Date.valueOf(LocalDate.parse(date[0], dateFormat));
		Date endDate = Date.valueOf(LocalDate.parse(date[1], dateFormat));

		String url = env.getProperty("web.server.url") + "/sale/" + startDate + "/" + endDate;
		List<DoanhSo> doanhSos = Arrays.asList(rest.getForObject(url, DoanhSo[].class));
		model.addAttribute("list", doanhSos);
		model.addAttribute("msg", "Tìm kiếm theo ngày: " + msg);
		return "quan-ly/doanh-so/list";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		String url = env.getProperty("web.server.url") + "/sale/" + id;
		DoanhSo doanhSo = rest.getForObject(url, DoanhSo.class);
		model.addAttribute("model", doanhSo);
		return "quan-ly/doanh-so/edit";

	}

	@GetMapping("/add")
	public String add(Model model) {
		DoanhSo doanhSo = new DoanhSo();
		model.addAttribute("model", doanhSo);
		return "quan-ly/doanh-so/add";
	}

	@PostMapping("/add")
	public String save(DoanhSo ds) {
		String url = env.getProperty("web.server.url") + "/sale";
		rest.postForObject(url, ds, Void.class);
		return "redirect:/quan-ly/doanh-so";
	}
}
