package com.haythem.Security.demoController;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managment")
public class ManagmentController {
    @GetMapping
    public String get() {
        return "GET : managment controller";
    }

    @PostMapping
    public String post() {
        return "POST : managment controller";
    }

    @DeleteMapping
    public String delete() {
        return "DELETE : managment controller";
    }

    @PutMapping
    public String put() {
        return "PUT : managment controller";
    }
}
