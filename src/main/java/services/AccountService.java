package services;

import dao.AccountDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utils.Validator;

@Service
public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public ResponseEntity create(String id) {
        Validator.checkId(id);
        if (accountDAO.getById(id) != null) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        accountDAO.create(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
