package controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.AccountService;

@RestController
@RequestMapping(path = "/bankaccount")
public class AccountController {
    private static final String JSON = MediaType.APPLICATION_JSON_UTF8_VALUE;

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/{id}", consumes = JSON, produces = JSON)
    public ResponseEntity changeUser(@PathVariable("id") String id) {
        return accountService.create(id);
    }
}
