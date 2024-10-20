package org.zerock.myapp.common;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

// 테스트용 컨트롤러
@RequestMapping("/api/")
@RestController
public class CommonApiController {
	@Autowired RequestMappingHandlerMapping handlerMapping;
	
	
	@PostConstruct
	void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.handlerMapping);
		log.info("\t+ this.handlerMapping: {}", this.handlerMapping);
	} // postConstruct
	
	@GetMapping("/mappings")
	Map<String, String> mappings() {
		log.trace("mappings() invoked.");
	
		return this.handlerMapping
				   	.getHandlerMethods()
				   	.entrySet()
				   	.stream()
				   	.collect(Collectors.toMap(
						   		entry -> entry.getKey().toString(),
						   		entry -> entry.getValue().toString()));
	} // mappings
	
} // end class
