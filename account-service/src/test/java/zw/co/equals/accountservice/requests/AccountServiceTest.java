package zw.co.equals.accountservice.requests;

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
import zw.co.equals.accountservice.dto.UserAccountDto;
import zw.co.equals.accountservice.enums.AccountType;

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
public class AccountServiceTest {

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    /**
     * @throws Exception It tests create customer account
     */
    @Test
    @Order(1)
    public void createCustomerAccount() throws Exception {

        //create account for Pep
        UserAccountDto userAccountDto = UserAccountDto.builder()
                .accountNumber("41445062354200").accountName("Pep Joseph Guardiola")
                .accountDetails("This is a test Saving account for Pep")
                .accountType(AccountType.SAVINGS_ACCOUNT)
                .build();

        String json = new ObjectMapper().writeValueAsString(userAccountDto);
        mockMvc.perform(post("/account/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)).andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));


        //create another account for Manuel Akanji
        userAccountDto = UserAccountDto.builder()
                .accountNumber("4145121212201").accountName("Manuel Akanji")
                .accountDetails("This is a test checking account for Akanji")
                .accountType(AccountType.CHECKING_ACCOUNT)
                .build();

        json = new ObjectMapper().writeValueAsString(userAccountDto);
        mockMvc.perform(post("/account/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)).andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }

    /**
     * @throws Exception It tests find customer account
     */
    @Test
    @Order(2)
    public void findCustomerAccountTest() throws Exception {
        mockMvc.perform(get("/account/find/41445062354200")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));
    }

    /**
     * @throws Exception It tests find non-existing customer account
     */
    @Test
    @Order(2)
    public void findNonExistingCustomerAccountTest() throws Exception {
        mockMvc.perform(get("/account/find/41325435476200")).andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));
    }
}
