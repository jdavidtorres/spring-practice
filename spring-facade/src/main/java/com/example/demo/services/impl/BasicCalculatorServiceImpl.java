package com.example.demo.services.impl;

import com.example.demo.services.BasicCalculatorService;
import com.example.demo.services.BasicMathOperation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BasicCalculatorServiceImpl implements BasicCalculatorService {

	// private List<BasicMathOperation> operationsList;
	private Map<String, BasicMathOperation> operations;

	public BasicCalculatorServiceImpl(List<BasicMathOperation> operationsList) {
		operations = operationsList.stream()
			.collect(Collectors.toMap(BasicMathOperation::getOperator, Function.identity()));
	}

	@Override
	public String doMath(String x, String y, String operation) {
		try {
			// return operationsList.stream().filter(oper ->
			// operation.equals(oper.getOperator())).findFirst().get().operation(x, y);
			return operations.get(operation).operation(x, y);
		} catch (Exception e) {
			return "Operacion no encontrada";
		}
	}
}
