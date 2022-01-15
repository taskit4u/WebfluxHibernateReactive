package com.example.webfluxhibernatereactive.service;

import com.example.webfluxhibernatereactive.dao.CustomerDao;
import com.example.webfluxhibernatereactive.dto.CustomerDto;
import com.example.webfluxhibernatereactive.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;

    public Mono<CustomerDto> getCustomer(String name) {
        log.info("Search customer with name: {}", name);
        var completableFutureOfCustomer = customerDao.getCustomer(name);
        return Mono.fromFuture(completableFutureOfCustomer)
                .cast(Customer.class)
                .map(customer -> new CustomerDto(customer.getName(), customer.getSex(), customer.getAge()));
    }

    public Mono<Void> save(CustomerDto customerDto) {
        var customer = new Customer(customerDto.name(), customerDto.sex(), customerDto.age());
        var cfInsert = customerDao.save(customer);
        return Mono.fromFuture(cfInsert)
                .cast(Void.class)
                .doOnNext(v -> log.info("Customer with name: {} saved", customer.getName()));
    }

    public Mono<Integer> update(CustomerDto customerDto) {
        var customer = new Customer(customerDto.name(), customerDto.sex(), customerDto.age());
        var cFUpdate = customerDao.update(customer);
        return Mono.fromFuture(cFUpdate)
                .cast(Integer.class)
                .doOnNext(i -> log.info("{} customers were update", i));
    }

    public Mono<Integer> delete(String name) {
        var completableFutureOfDelete = customerDao.delete(name);
        return Mono.fromFuture(completableFutureOfDelete)
                .cast(Integer.class)
                .doOnNext(i -> log.info("{} customers with name: {} removed", i, name));
    }

}
