package ca.sfu.delta.controllers;

import javax.validation.Valid;

import ca.sfu.delta.models.FormData;
import ca.sfu.delta.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ServiceRequestController extends WebMvcConfigurerAdapter {
    @Autowired
    FormRepository formRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @RequestMapping(value = "/api/form/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> getForm(@PathVariable("id") Long id) {
        FormData form = formRepository.findOne(id);
        return form.jsonify();
    }

    @RequestMapping(value = "/api/form/search", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Object>> search() {
        List<Map<String, Object>> forms = new ArrayList<Map<String, Object>>();

        for (FormData form : formRepository.findAll()) {
            forms.add(form.jsonify());
        }

        return forms;
    }

    @RequestMapping(value = "/api/form/save", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String addForm(
            @RequestParam(required=false) String department,
            @RequestParam(required=false) String requesterName,
            @RequestParam(required=false) String phoneNumber,
            @RequestParam(required=false) String requestedOnDate,
            @RequestParam(required=false) String requesterID,
            @RequestParam(required=false) String authorizationDate,
            @RequestParam(required=false) String paymentAccountCode,
            @RequestParam(required=false) String emailAddress,
            @RequestParam(required=false) String times,
            @RequestParam(required=false) String eventName,
            @RequestParam(required=false) Boolean isLicensed,
            @RequestParam(required=false) Integer numAttendees,
            @RequestParam(required=false) String authorizerId,
            @RequestParam(required=false) String authorizerPhoneNumber,
            @RequestParam(required=false) String serviceRequestNumber,
            @RequestParam(required=false) String eventLocation,
            @RequestParam(required=false) String authorizerName,
            @RequestParam(required=false) String eventDates,
            @RequestParam(required=false) String eventDetails,
            @RequestParam(required=false) String faxNumber

            ) {
        FormData form = new FormData();
        form.setDepartment(department);
        form.setRequesterName(requesterName);
        form.setPhoneNumber(phoneNumber);
        form.setRequestedOnDate(requestedOnDate);
        form.setRequesterID(requesterID);
        form.setAuthorizationDate(authorizationDate);
        form.setPaymentAccountCode(paymentAccountCode);
        form.setEmailAddress(emailAddress);
        form.setTimes(times);
        form.setEventName(eventName);
        form.setIsLicensed(isLicensed);
        form.setNumAttendees(numAttendees);
        form.setAuthorizerID(authorizerId);
        form.setAuthorizerPhoneNumber(authorizerPhoneNumber);
        form.setServiceRequestNumber(serviceRequestNumber);
        form.setEventLocation(eventLocation);
        form.setAuthorizerName(authorizerName);
        form.setEventDates(eventDates);
        form.setEventDetails(eventDetails);
        form.setFaxNumber(faxNumber);


        form = formRepository.save(form);

        if (form != null) {
            return String.valueOf(form.getId());
        } else {
            return "ERROR: form didn't save";
        }
    }

    @GetMapping("/")
    public String showForm(FormData serviceRequestForm) {
        return "form";
    }

    @PostMapping("/")
    public String checkFormRequest(@Valid FormData serviceRequestForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        saveFormToDatabase(serviceRequestForm);

        return "redirect:/results";
    }

    private void saveFormToDatabase(FormData formData) {
        formRepository.save(formData);
    }

    @ModelAttribute("FormData")
	public FormData createModel() {
		return new FormData();
	}
}