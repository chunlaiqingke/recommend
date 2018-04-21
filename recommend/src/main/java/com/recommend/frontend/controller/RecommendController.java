package com.recommend.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("recommend")
public class RecommendController {

	@RequestMapping("")
	@ResponseBody
	public String getRecommend(){
		return "ok";
	}
}
