package zw.co.equals.customersupportservice.requests;


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
import zw.co.equals.customersupportservice.dto.IssueLogDto;
import zw.co.equals.customersupportservice.enums.IssueType;
import zw.co.equals.customersupportservice.models.IssueLog;

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
public class CustomerServiceTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    /**
     * @throws Exception It tests log support issue
     */
    @Test
    @Order(1)
    public void logIssueTest() throws Exception {

        IssueLogDto issueLogDto = IssueLogDto.builder()
                .issueType(IssueType.TRANSACTIONAL)
                .description("This is just a test issue being logged by me")
                .fromAccount("4145121212201")
                .build();

        String json = new ObjectMapper().writeValueAsString(issueLogDto);
        mockMvc.perform(post("/support/log")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)).andDo(print()).andExpect(status().isCreated())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));
    }

    /**
     * @throws Exception It tests track issue with ticket id
     */
    @Test
    @Order(2)
    public void trackIssueWithTicketNumberTest() throws Exception {
        mockMvc.perform(get("/support/track/1")).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint())));

    }
}









