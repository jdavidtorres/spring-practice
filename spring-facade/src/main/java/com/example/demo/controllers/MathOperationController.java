package com.example.demo.controllers;

import com.example.demo.services.BasicCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathOperationController {

	@Autowired
	private BasicCalculatorService basicCalculatorService;

	@GetMapping
	public String basicMathOperation(@RequestParam String num1, @RequestParam String num2, @RequestParam String oper) {
		return basicCalculatorService.doMath(num1, num2, oper);
	}
}
