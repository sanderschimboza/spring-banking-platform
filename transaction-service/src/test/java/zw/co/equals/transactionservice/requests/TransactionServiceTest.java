package zw.co.equals.transactionservice.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import zw.co.equals.transactionservice.dto.FundsTransferDto;
import zw.co.equals.transactionservice.enums.TransactionType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@RunWith(OrderedTestRunner.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class TransactionServiceTest {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    /**
     * @throws Exception It tests deposit funds into an existing account
     */
    @Test
    @Order(1)
    public void depositFundsTest() throws Exception {
        mockMvc.perform(post("/transaction/deposit/4145121212201/400000")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests balance enquiry
     */
    @Test
    @Order(2)
    public void checkBalanceTest() throws Exception {
        mockMvc.perform(get("/transaction/balance?account=4145121212201")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests deposit funds with an invalid amount
     */
    @Test
    @Order(3)
    public void depositFundsInvalidAmountTest() throws Exception {
        mockMvc.perform(post("/transaction/deposit/4145121212201/-3000")).andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests deposit funds with into a non-existing account
     */
    @Test
    @Order(4)
    public void depositFundsInvalidAccountTest() throws Exception {
        mockMvc.perform(post("/transaction/deposit/4145121212201111/2000")).andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests withdraw funds
     */
    @Test
    @Order(5)
    public void withdrawFundsTest() throws Exception {
        mockMvc.perform(post("/transaction/withdraw/4145121212201/4500")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests withdraw funds without sufficient credit
     */
    @Test
    @Order(6)
    public void withdrawFundsInsufficientCreditTest() throws Exception {
        mockMvc.perform(post("/transaction/withdraw/4145121212201/500000")).andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests transfer funds between accounts
     */
    @Test
    @Order(7)
    public void fundsTransferTest() throws Exception {
        FundsTransferDto fundsTransferDto = FundsTransferDto.builder()
                .fromAccount("4145121212201")
                .toAccount("41445062354200")
                .amount(10000)
                .transactionType(TransactionType.INTERNAL_TRANSFER)
                .build();

        String json = new ObjectMapper().writeValueAsString(fundsTransferDto);
        mockMvc.perform(post("/transaction/transfer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)).andDo(print()).andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests generate account statement
     */
    @Test
    @Order(8)
    public void generateStatementTest() throws Exception {
        mockMvc.perform(get("/transaction/statement/4145121212201")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests generate account statement
     */
    @Test
    @Order(9)
    public void generateStatementBetweenDatesTest() throws Exception {
        mockMvc.perform(get("/transaction/statement/4145121212201?startDate=2023-11-23&endDate=2023-11-24"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    @Test
    @Order(10)
    public void generateStatementFromDateTest() throws Exception {
        mockMvc.perform(get("/transaction/statement/4145121212201?startDate=2023-11-23"))
                .andDo(print()).andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    @Test
    @Order(11)
    public void generateStatementToDateTest() throws Exception {
        mockMvc.perform(get("/transaction/statement/4145121212201?endDate=2023-11-20"))
                .andDo(print()).andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

}
