package com.egeroo.roocvthree.answer;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public class AnswerController {
	//private static final Logger LOG = Logger.getLogger(AnswerController.class);
	
	@Autowired
	AnswerService answerService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Answer> getAnswers(@RequestHeader HttpHeaders headers) {
		List<Answer> result = this.answerService.getAllAnswers(headers.get("tenant").get(0));
		return result;
	}
}
