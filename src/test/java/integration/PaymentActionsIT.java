package integration;

import com.eszop.paymentservice.PaymentServiceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = PaymentServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class PaymentActionsIT {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createPayment() throws Exception {
        String payment = """
                {
                    "userId": 1,
                    "price": 123.0,
                    "status": "IN_PROGRESS",
                    "offerTitles": [
                        "example title 1",
                        "example title 2"
                    ]
                }
                """;
        MvcResult offerCreationResult = mockMvc
                .perform(post("/payments").contentType(MediaType.APPLICATION_JSON).content(payment))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<String> titleList = new ArrayList<>(List.of("example title 1","example title 2"));

        mockMvc
                .perform(get("/payments/" + 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.price").value(new BigDecimal("123.0")))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.offerTitles").value(titleList))
                .andReturn();
    }
}
