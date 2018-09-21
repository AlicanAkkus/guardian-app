package com.aakkus.quardiantest;

import com.aakkus.annotation.Validate;
import com.aakkus.validation.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@SpringBootApplication
@ComponentScan("com.aakkus")
public class GuardianTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuardianTestApplication.class, args);
    }
}

@Component
class HelloWorldService {

    @Validate(validatorClass = HelloWorldValidation.class)
    String hello(String s) {
        return format("Helloo %s!", s);
    }
}

@Component
class HelloWorldValidation implements Validation<String> {

    @Override
    public void validate(String s) {
        if (StringUtils.isBlank(s)) {
            throw new RuntimeException("String should not be null!");
        }
    }
}

@RestController
class HelloWorldRestController {

    private final HelloWorldService helloWorldService;


    HelloWorldRestController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/hi")
    public String hi(@RequestParam(name = "q", required = false) String q) {
        return helloWorldService.hello(q);
    }
}