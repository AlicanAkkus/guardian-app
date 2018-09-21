# What?
A spring boot validation library for your projects.

## Usage?

1. Implement a Validation interface and write own business logic, you want to validate.
2. Use @Validate annotation before call method.

## Sample
```java
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
```
