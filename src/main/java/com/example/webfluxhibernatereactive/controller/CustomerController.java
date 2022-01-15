package com.example.webfluxhibernatereactive.controller;

import com.example.webfluxhibernatereactive.dto.CustomerDto;
import com.example.webfluxhibernatereactive.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{name}")
    public Mono<CustomerDto> getCustomer(@PathVariable String name) {
        return customerService.getCustomer(name);
    }

    @PostMapping
    public Mono<Void> save(@RequestBody CustomerDto customerDto) {
        return customerService.save(customerDto);
    }

    @PutMapping
    public Mono<Integer> update(@RequestBody CustomerDto customerDto) {
        return customerService.update(customerDto);
    }

    @DeleteMapping("/{name}")
    public Mono<Integer> delete(@PathVariable String name) {
        return customerService.delete(name);
    }

}
