package com.test.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.test.services.AccountService;
import com.test.utils.requests.SumBody;

@RestController
@RequestMapping(path = "/bankaccount/{id}")
public class AccountController {
    private static final String JSON = MediaType.APPLICATION_JSON_UTF8_VALUE;

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity create(@PathVariable("id") String id) {
        return accountService.create(id);
    }

    @PutMapping(path = "/deposit", consumes = JSON, produces = JSON)
    public ResponseEntity deposit(@PathVariable("id") String id,
                                  @RequestBody SumBody sum) {
        return accountService.deposit(id, sum);
    }

    @PutMapping(path = "/withdraw", consumes = JSON, produces = JSON)
    public ResponseEntity withdraw(@PathVariable("id") String id,
                                   @RequestBody SumBody sum) {
        return accountService.withdraw(id, sum);
    }

    @GetMapping(path = "/balance", produces = JSON)
    public ResponseEntity balance(@PathVariable("id") String id) {
        return accountService.balance(id);
    }

}
