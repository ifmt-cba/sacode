package br.com.edu.ifmt.sacode.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GenericController {
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting"; // Encontrado em :: src/main/resources/templates/greeting.html)
	}
}
//https://spring.io/guides/gs/serving-web-content