package controllers.auditor;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AiBoxService;
import services.CommentService;
import services.AuditService;
import services.PurchaseService;
import services.AuditorService;
import controllers.AbstractController;
import domain.Actor;
import domain.AiBox;
import domain.Comment;
import domain.CreditCard;
import domain.Audit;
import domain.Purchase;
import domain.Auditor;
import forms.KeywordForm;

@Controller
@RequestMapping("audit/auditor")
public class AuditAuditorController extends AbstractController {

	@Autowired
	private AuditService auditService;
	
	@Autowired
	private AiBoxService aiBoxService;
		
	@Autowired
	private AuditorService auditorService;
	


	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){

		ModelAndView result;
		Auditor a = auditorService.getPrincipal();
		
		result =  new ModelAndView("audit/list");
		result.addObject("audits",a.getAudits());

		return result;
	}

	// Show -----------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int auditId){

		ModelAndView result = new ModelAndView("audit/show");
		Audit a = auditService.findOne(auditId);
		
		if(auditorService.getPrincipal().getAudits().contains(a)){
		result.addObject("audit", a);
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}

	// Create -----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int aiBoxId) {
		ModelAndView result;
		
		AiBox box = aiBoxService.findOne(aiBoxId);
		Audit audit = auditService.create(box);
		
		if (auditService.findIfHasAuditWithAiBox(box)) {
			result = new ModelAndView("aiBox/list");
			result.addObject("aiBoxes", aiBoxService.findAll());
			result.addObject("loggedUser",true);
			result.addObject("isCustomer",false);
			result.addObject("isAuditor",true);
			result.addObject("auditCreated",true);
			result.addObject("form", new KeywordForm());
			result.addObject("requestURI","aiBox/list.do");
		}else{
			result = this.createEditModelAndView(audit);
		}
		return result;

	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int auditId) {
		
		ModelAndView result;
		Audit audit = auditService.findOne(auditId);
		
		if (auditorService.getPrincipal().getAudits().contains(audit) && audit.getIsFinal() == false) {
			result = new ModelAndView();
			result = this.createEditModelAndView(audit);
		}else{
			result = new ModelAndView("error/access");
		}

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Audit audit, BindingResult binding) {
		ModelAndView result;

		
		if (binding.hasErrors()) {
			System.out.println(binding);
			result = this.createEditModelAndView(audit);
		} else
			try {
				Audit saved; 
				if (audit.getId() == 0) {
					audit.setMoment(new Date(System.currentTimeMillis()-1000));
					saved = auditService.save(audit);
					Auditor a = auditorService.getPrincipal();
					a.getAudits().add(saved);
					auditorService.save(a);
				}else{
					saved = auditService.findOne(audit.getId());
					saved.setIsFinal(audit.getIsFinal());
					saved.setMoment(new Date(System.currentTimeMillis()-1000));
					saved.setScore(audit.getScore());
					saved.setText(audit.getText());
					saved = auditService.save(saved);
				}
				
				System.out.println(saved);
				
				Double cont = 0.;
				Double total = null;
				AiBox aiBox = saved.getAiBox();
				
				for (Audit aud : auditService.getFinalAuditsByAiBox(aiBox)){
					if(total == null){total = 0.;}
					cont ++;
					total += new Double(aud.getScore());
				}
				if(total == null){
					aiBox.setAuditScore(null);
					aiBoxService.save(aiBox);
				}else{
					System.out.println(total/cont);
					aiBox.setAuditScore((total/cont)/10);
					aiBoxService.save(aiBox);
				}
				
				result = new ModelAndView("redirect:/audit/auditor/list.do");

			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(audit,"message.commit.error");
			}
		return result;
	}
	
	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int auditId) {
			
			ModelAndView result;
			Audit audit = auditService.findOne(auditId);
			Auditor a = auditorService.getPrincipal();
			
			if (a.getAudits().contains(audit) && audit.getIsFinal() == false){
				a.getAudits().remove(audit);
				auditorService.save(a);
				auditService.delete(audit);
				result = new ModelAndView("redirect:/audit/auditor/list.do");
				
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}
	
	//Ancillary ----------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Audit audit) {
		ModelAndView result;
		result = this.createEditModelAndView(audit, null);
		return result;
	}

	private ModelAndView createEditModelAndView(Audit audit, final String message) {
		ModelAndView result;
		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);
		result.addObject("message", message);
		return result;
	}

}
