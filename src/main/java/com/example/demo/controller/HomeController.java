package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.LocationStates;
import com.example.demo.services.CoronaVirusDataServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {

	CoronaVirusDataServices crnService;

	@Autowired
	public void setCrnService(CoronaVirusDataServices crnService) {
		this.crnService = crnService;
	}

	@GetMapping("/home")
	public String home(Model model) {
		List<LocationStates> allstates = crnService.getAllstates();
		int totalDeathsReported = allstates.stream().mapToInt(stat -> stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates", allstates);
		model.addAttribute("totalDeathsReported", totalDeathsReported);
		return "home";
	}

	@RequestMapping(path = "/collectChartData", produces = { "application/json" })
	@ResponseBody
	public List<LocationStates> collectChartData(Model model) {
		List<LocationStates> allstates = crnService.getAllstates();
		int totalDeathsReported = allstates.stream().mapToInt(stat -> stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates", allstates);
		model.addAttribute("totalDeathsReported", totalDeathsReported);
		return allstates;
	}


	@GetMapping("/viewchart2")
	public String country2(Model model) {
		List<LocationStates> allstates = crnService.getAllstates();
		int totalDeathsReported = allstates.stream().mapToInt(stat -> stat.getLatestTotalDeaths()).sum();
		Map<String, Integer> hmap = new HashMap<>();
		for (int i = 0; i < allstates.size(); i++) {
			hmap.put(allstates.get(i).getCountry(), allstates.get(i).getLatestTotalDeaths());
		}
		model.addAttribute("country", hmap.keySet());
		model.addAttribute("DeathCount", hmap.values());
		return "viewchart2";
	}

}