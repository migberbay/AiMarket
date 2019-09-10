package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.ConfigurationService;
import services.ScientistService;
import controllers.AbstractController;
import domain.Actor;
import domain.AiBox;
import domain.Audit;
import domain.Configuration;
import domain.Scientist;

@Controller
@RequestMapping("/admin/")
public class ConfigurationAdminController extends AbstractController {
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ScientistService scientistService;
	
	@Autowired
	private AuditService auditService;



	//Edit-------------------------------------------------------------
	@RequestMapping(value="/configuration", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView res;
		
		//como solo debe existir una se puede hacer esto.
		Configuration c = (Configuration) configurationService.findAll().toArray()[0];
		res = this.createEditModelAndView(c);
		
		return res;
	}
	
	//Save FORM-------------------------------------------------------------	
	@RequestMapping(value="/configuration", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Configuration config, BindingResult binding){
		ModelAndView res;
		
		System.out.println(config.getBanner()+ " " +config.getSystemName()+ " "+ config.getWelcomeTextEnglish());
		System.out.println(binding);
		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			res = this.createEditModelAndView(config);
		} else{
		try {
			Configuration configuration = configurationService.findOne(config.getId());
			System.out.println(config);
			config.setVersion(configuration.getVersion());
			System.out.println(config);
			configurationService.save(config);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (Throwable e) {
			res = new ModelAndView("admin/configuration");
			res.addObject("configuration", config);
			res.addObject("errorMessage", "admin.commit.error");
			e.printStackTrace();
		}
	}
		return res;
	}
	
	//Notify Users ----------------------------------------------
	
	@RequestMapping(value="/notifyUsers", method=RequestMethod.GET)
	public ModelAndView notifyUsers(){
		ModelAndView res;
		
		Configuration c = (Configuration) configurationService.findAll().toArray()[0];
		if (c.getNotificationHappened() == false) {
			c.setNotificationHappened(true);
			configurationService.save(c);
			for (Actor a : actorService.findAll()) {
				a.setNotified(false);
				actorService.save(a);
			}
			res = new ModelAndView("redirect:/welcome/index.do");
		}else{
			res = new ModelAndView("error/access");
		}

		return res;
	}
	
	//Score-----------------------------------------------------
	
	@RequestMapping(value="/computeScore", method=RequestMethod.GET)
	public ModelAndView computeScore(){
		ModelAndView res;
		
		Double cont;
		Double total;
		
		Collection<Scientist> scientists = scientistService.findAll();
		
		for (Scientist s : scientists) {
			cont = 0.;
			total = null;
			for (AiBox a : s.getAiBoxes()) {
				for (Audit audit : auditService.getFinalAuditsByAiBox(a)) {
					if(total == null){total = 0.;}
					total += new Double(audit.getScore());
					cont++;
				}
			}
			if(total == null){
				s.setAuditScore(null);
			}else{
				s.setAuditScore((total/cont)/10);
			}
			scientistService.save(s);
		}
		
		res = new ModelAndView("admin/auditScore");
		res.addObject("scientists",scientists);
		return res;
	}
	
	

	
	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(Configuration config){
		ModelAndView res;
		res = createEditModelAndView(config, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Configuration config, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("admin/configuration");
		
//		Boolean language = false;
//		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
//			language=true;
//		}
//		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
//			language = false;
//		}
//		
		res.addObject("configuration", config);
		res.addObject("errorMessage", messageCode);
		
		return res;
	}
	
}