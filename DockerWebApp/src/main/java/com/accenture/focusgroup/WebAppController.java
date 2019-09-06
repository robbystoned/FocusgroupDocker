package com.accenture.focusgroup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebAppController {
	@Value("${spring.application.name}")
	String appName;

	@GetMapping("/")
	public String homePage(Model model)  {
		try {
			model.addAttribute("appName", getHTML("http://backend:8080"));
		} catch (Exception e) {
			model.addAttribute("appName", "error connecting to the backend");
			e.printStackTrace();
		}
		
		return "home";
	}
	
	private String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }
}