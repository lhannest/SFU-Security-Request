package ca.sfu.delta.controllers;

import ca.sfu.delta.models.FormData;
import ca.sfu.delta.repository.AuthorizedUserRepository;
import ca.sfu.delta.repository.DistributionEmailRepository;
import ca.sfu.delta.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

// Tutorial from http://www.springboottutorial.com/unit-testing-for-spring-boot-rest-services

@RunWith(SpringRunner.class)
@WebMvcTest(value = FormServiceRequestController.class, secure = false)
public class FormServiceRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // These unused mock beans are dependencies for FormServiceRequestController.
    // Spring will be angry if you remove them.
    @MockBean
    private AuthorizedUserRepository mockAuth;
    @MockBean
    private DistributionEmailRepository mockDist;
    @MockBean
    private UserRepository mockUser;
    @MockBean
    private JavaMailSender mockEmail;

    @MockBean
    private FormServiceRequestController formService;

    private String exampleFormJson = "{\"department\":\"Alumni Relations\",\"requesterName\":\"1\",\"phoneNumber\":\"(111) 111-1111\",\"requestedOnDate\":\"\",\"requesterID\":\"1\",\"authorizationDate\":\"\",\"paymentAccountCode\":\"\",\"emailAddress\":\"1@1.com\",\"times\":\"\",\"eventName\":\"1\",\"isLicensed\":\"\",\"numAttendees\":\"1\",\"authorizerId\":\"\",\"authorizerPhoneNumber\":\"\",\"serviceRequestNumber\":\"\",\"eventLocation\":\"Surrey\",\"authorizerName\":\"\",\"eventDates\":\"2017/11/26 23:00-2017/11/26 23:30\",\"eventDetails\":\"\",\"faxNumber\":\"1\",\"requestID\":\"\",\"authorizerEmailAddress\":\"1@1.com\",\"guardType\":\"Guard\",\"numGuards\":\"1\"}";

    private MockHttpServletResponse sendMockSaveFormPostRequest(String saveFormJson) throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/form/save")
                .accept(MediaType.TEXT_PLAIN_VALUE).content(saveFormJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        return result.getResponse();
    }

    @Test
    public void addValidForm() throws Exception {
        String testJson = generateValidTestJson();
        MockHttpServletResponse response = sendMockSaveFormPostRequest(testJson);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void invalidRequesterName() throws Exception {
        FormData mockForm = generateMockForm();
        mockForm.setRequesterName("");
        String testJson = generateJsonFromFormData(mockForm);
        MockHttpServletResponse response = sendMockSaveFormPostRequest(testJson);

        System.out.println(testJson);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void EmptyTest() throws Exception {

    }

    private String generateValidTestJson() {
        return generateJsonFromFormData(generateMockForm());
    }

    private FormData generateMockForm() {
        FormData mockForm = new FormData();
        mockForm.setAuthorizerEmailAddress("1@1.com");
        mockForm.setDepartment("Alumni Relations");
        mockForm.setEmailAddress("1@1.com");
        mockForm.setEventDates("2017/11/26 23:00-2017/11/26 23:30");
        mockForm.setEventLocation("Surrey");
        mockForm.setEventName("Event Name");
        mockForm.setGuardType("Guard");
        mockForm.setNumAttendees(1);
        mockForm.setNumGuards("1");
        mockForm.setPhoneNumber("(111) 111-1111");
        mockForm.setRequesterID("test");
        mockForm.setRequesterName("Requester Name");
        return mockForm;
    }

    private String generateJsonFromFormData(FormData mockForm) {

        return "{"
                + "\"department\":\"" + mockForm.getDepartment()
                + "\",\"requesterName\":\"" + mockForm.getRequesterName()
                + "\",\"phoneNumber\":\"" + mockForm.getPhoneNumber()
                + "\",\"requestedOnDate\":\"" + mockForm.getRequestedOnDate()
                + "\",\"requesterID\":\"" + mockForm.getRequesterID()
                + "\",\"authorizationDate\":\"" + mockForm.getAuthorizationDate()
                + "\",\"paymentAccountCode\":\"" + mockForm.getPaymentAccountCode()
                + "\",\"emailAddress\":\"" + mockForm.getEmailAddress()
                + "\",\"times\":\"" + mockForm.getTimes()
                + "\",\"eventName\":\"" + mockForm.getEventName()
                + "\",\"isLicensed\":\"" + mockForm.getIsLicensed()
                + "\",\"numAttendees\":\"" + mockForm.getNumAttendees()
                + "\",\"eventLocation\":\"" + mockForm.getEventLocation()
                + "\",\"eventDates\":\"" + mockForm.getEventDates()
                + "\",\"authorizerEmailAddress\":\"" + mockForm.getAuthorizerEmailAddress()
                + "\",\"guardType\":\"" + mockForm.getGuardType()
                + "\",\"numGuards\":\"" + mockForm.getNumGuards()
                + "\"}";
    }
}
