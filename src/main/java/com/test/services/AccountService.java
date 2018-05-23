package com.test.services;

import com.test.dao.AccountDAO;
import com.test.utils.responses.BalanceResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.test.utils.Validator;
import com.test.utils.requests.SumBody;

@Service
@Transactional
public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public ResponseEntity create(String id) {
        if (!Validator.checkId(id)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (accountDAO.getById(id) != null) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        accountDAO.create(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    public ResponseEntity deposit(String id, SumBody sumBody) {
        final double sum = sumBody.getSum();
        if (!Validator.checkId(id) || !Validator.ckeckSum(sum)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (accountDAO.getById(id) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        accountDAO.deposit(id, sum);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity withdraw(String id, SumBody sumBody) {
        final double sum = sumBody.getSum();
        if (!Validator.checkId(id) || !Validator.ckeckSum(sum)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (accountDAO.getById(id) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (accountDAO.getBalance(id) < sum) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        accountDAO.withdraw(id, sum);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity balance(String id) {
        if (!Validator.checkId(id)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (accountDAO.getById(id) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new BalanceResponce(accountDAO.getBalance(id)),HttpStatus.CREATED);
    }

}
