package com.test;

import com.test.dao.AccountDAO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class ControllerTests {
    @Autowired
    private MockMvc mock;

    @Mock
    private
    AccountDAO accountDAO;

    @After
    public void clear() {
        accountDAO.clear();
    }

    @Test
    public void testAccountCreated() throws Exception {
        mock.perform(post("/bankaccount/12341")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAccountConflict() throws Exception {
        mock.perform(post("/bankaccount/12341")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mock.perform(post("/bankaccount/12341")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void testAccountBadRequest() throws Exception {
        mock.perform(post("/bankaccount/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mock.perform(post("/bankaccount/12341343")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mock.perform(post("/bankaccount/wdbhc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDepositBadRequest() throws Exception {
        mock.perform(post("/bankaccount/12341")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mock.perform(put("/bankaccount/1/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isBadRequest());
        mock.perform(put("/bankaccount/12341343/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isBadRequest());
        mock.perform(put("/bankaccount/wdbhc/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isBadRequest());
        mock.perform(put("/bankaccount/12341/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 0}"))
                .andExpect(status().isBadRequest());
        mock.perform(put("/bankaccount/12341/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": -10.99}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDepositNotFound() throws Exception {
        mock.perform(put("/bankaccount/12345/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDepositOkAndCorrect() throws Exception {
        mock.perform(post("/bankaccount/12341")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mock.perform(put("/bankaccount/12341/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isOk());
        mock.perform(get("/bankaccount/12341/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("balance").value(100.23));
    }

    @Test
    public void testWithdrawBadRequest() throws Exception {
        mock.perform(post("/bankaccount/12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mock.perform(put("/bankaccount/1/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isBadRequest());
        mock.perform(put("/bankaccount/12341343/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isBadRequest());
        mock.perform(put("/bankaccount/wdbhc/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isBadRequest());
        mock.perform(put("/bankaccount/12345/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 0}"))
                .andExpect(status().isBadRequest());
        mock.perform(put("/bankaccount/12345/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": -10.99}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWithdrawNotFound() throws Exception {
        mock.perform(put("/bankaccount/12345/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testWithdrawForbidden() throws Exception {
        mock.perform(post("/bankaccount/12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mock.perform(put("/bankaccount/12345/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isForbidden());
        mock.perform(put("/bankaccount/12345/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isOk());
        mock.perform(put("/bankaccount/12345/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.24}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testWithdrawOkAndCorrect() throws Exception {
        mock.perform(post("/bankaccount/12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mock.perform(put("/bankaccount/12345/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100.23}"))
                .andExpect(status().isOk());
        mock.perform(put("/bankaccount/12345/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\": 100}"))
                .andExpect(status().isOk());
        mock.perform(get("/bankaccount/12345/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("balance").value(0.23));
    }

    @Test
    public void testBalanceNotFound() throws Exception {
        mock.perform(get("/bankaccount/12345/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBalanceBadRequest() throws Exception {
        mock.perform(get("/bankaccount/123456/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mock.perform(get("/bankaccount/1234/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mock.perform(get("/bankaccount/123sd/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
