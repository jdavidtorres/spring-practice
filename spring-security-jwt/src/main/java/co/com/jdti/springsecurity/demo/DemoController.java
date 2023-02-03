package co.com.jdti.springsecurity.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DemoController {
	
	@GetMapping("/demo")
	public ResponseEntity<String>sayHello(){
		return ResponseEntity.ok("Hello World!!");
	}
}
