package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.AiBoxService;
import services.ScientistService;
import domain.AiBox;
import forms.KeywordForm;

@Controller
@RequestMapping("aiBox/")
public class AiBoxController extends AbstractController {

	@Autowired
	private AiBoxService aiBoxService;
	
	@Autowired
	private ScientistService scientistService;

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) KeywordForm keyForm,@RequestParam(required = false) Integer scientistId ) {

		ModelAndView result = new ModelAndView("aiBox/list");
		Collection<AiBox> aiBoxes;
		
		Authority customerAuth = new Authority();
		customerAuth.setAuthority(Authority.CUSTOMER);
		
		Authority auditorAuth = new Authority();
		auditorAuth.setAuthority(Authority.AUDITOR);
		
		
		if(keyForm ==  null){
			aiBoxes = aiBoxService.findAll();
			result.addObject("form", new KeywordForm());
		}else{
			result.addObject("form", keyForm);
			if (keyForm.getKeyword() == "") {
				aiBoxes = aiBoxService.findAll();
			}else{
				aiBoxes = aiBoxService.findByKeyword(keyForm.getKeyword());
			}
		}
		
		if (scientistId != null) {
			aiBoxes = aiBoxService.findByScientist(scientistService.findOne(scientistId));
		}
		
		Boolean loggedUser;
		Boolean isCustomer;
		Boolean isAuditor;
		try {
			UserAccount principal = LoginService.getPrincipal();
			loggedUser = principal != null;
			isCustomer = principal.getAuthorities().contains(customerAuth);
			isAuditor = principal.getAuthorities().contains(auditorAuth);
			
			System.out.println("auths: " + principal.getAuthorities());
			
		} catch (Exception e) {
			loggedUser = false;
			isCustomer = false;
			isAuditor = false;
		}
		
		result.addObject("aiBoxes", aiBoxes);
		result.addObject("loggedUser",loggedUser);
		result.addObject("isCustomer",isCustomer);
		result.addObject("isAuditor",isAuditor);
		result.addObject("requestURI","aiBox/list.do");

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "save")
	public ModelAndView list2(@ModelAttribute("form") KeywordForm keyForm, BindingResult binding){
		return list(keyForm, null);
	}

	// Show -----------------------------------------------------------


}
