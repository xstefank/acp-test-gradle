package org.acme.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

    private final GreetingRepository repository;

    public GreetingController(GreetingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Greeting> list() {
        return repository.findAll();
    }

    @PostMapping
    public Greeting create(@RequestBody Greeting greeting) {
        return repository.save(greeting);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Greeting> get(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
