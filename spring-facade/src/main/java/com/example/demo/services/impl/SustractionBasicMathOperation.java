package com.example.demo.services.impl;

import com.example.demo.services.BasicMathOperation;
import org.springframework.stereotype.Service;

@Service
public class SustractionBasicMathOperation implements BasicMathOperation {

	@Override
	public String operation(String x, String y) {
		return Integer.parseInt(x) - Integer.parseInt(y) + "";
	}

	@Override
	public String getOperator() {
		return "SUS";
	}
}
