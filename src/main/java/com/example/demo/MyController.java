package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MyController {
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";

	@RequestMapping("/")
	public String home(Model m) {

		return "home";
	}

	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(HttpServletRequest req, Model m, @RequestParam("imgurl1") MultipartFile file) {
		// public String upload(@ModelAttribute() Product product, Model m,
		// @RequestParam("imgurl1") MultipartFile[] files) {
         Product p = new Product();
        p.setId(Integer.parseInt(req.getParameter("id").toString()));
        p.setName(req.getParameter("name").toString());
        p.setBrand(req.getParameter("brand").toString());
        p.setPrice(Double.parseDouble(req.getParameter("price").toString()));
   
        p.setEdate(java.sql.Date.valueOf(req.getParameter("edate").toString()));
        
        p.setStock(Integer.parseInt(req.getParameter("stock").toString()));
        p.setCategory(req.getParameter("category").toString());
  
        
		StringBuilder filename = new StringBuilder();
		Path filenameandpath = Paths.get(uploadDirectory, file.getOriginalFilename());
		filename.append(file.getOriginalFilename());
		try {
			Files.write(filenameandpath, file.getBytes());
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(DataIntegrityViolationException ex){
		    
		        //duplicate primary key 
			m.addAttribute("error","ex.getMessage()");
			return "error";
		    
		}
		
		ProductDao pd = new ProductDao();
		p.setImgurl1("images/"+filename.toString());
		pd.insert(p);
		m.addAttribute("filename", filename);

		// StringBuilder filenames = new StringBuilder();
//		for (MultipartFile file : files) {
//			Path filenameandpath = Paths.get(uploadDirectory, file.getOriginalFilename());
//			filenames.append(file.getOriginalFilename() + "\n");
//			try {
//				Files.write(filenameandpath, file.getBytes());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		// m.addAttribute("filenames", filenames);
		
	
		
		
		m.addAttribute("products",pd.allProduct());
		return "success";
	}
	
	
	
	
//	@RequestMapping("/error")
//	public String error(Model m) {
//
//		return "error";
//	}
	
}
