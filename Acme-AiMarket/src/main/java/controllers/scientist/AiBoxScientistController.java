package controllers.scientist;

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
import services.AuditService;
import services.CommentService;
import services.AiBoxService;
import services.PurchaseService;
import services.ScientistService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.CreditCard;
import domain.AiBox;
import domain.Purchase;
import domain.Scientist;
import forms.KeywordForm;

@Controller
@RequestMapping("aiBox/scientist")
public class AiBoxScientistController extends AbstractController {

	@Autowired
	private AiBoxService aiBoxService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ScientistService scientistService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private ActorService actorService;


	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){

		ModelAndView result;
		Scientist s = scientistService.getPrincipal();
		CreditCard cc =  s.getCreditCard();
		
		if(cc != null){
			Date in7 = new Date(System.currentTimeMillis()+604800000L); //2592000000L <- 30 days to test;  604800000L <- 7 days
			System.out.println("Expiration date: " +cc.getExpirationDate()+"  Checking date: "+ in7);
			if(cc.getExpirationDate().after(in7)){
				result = new ModelAndView("aiBox/list");
				result.addObject("aiBoxes", aiBoxService.findByScientist(s));
				result.addObject("loggedUser", true);
				result.addObject("isOwner", true);
				result.addObject("form", new KeywordForm());
				result.addObject("requestURI","aiBox/scientist/list.do");
			}else{
				result =  new ModelAndView("error/access");
				result.addObject("creditCardNotValid",true);
			}
		}else{
			result =  new ModelAndView("error/access");
			result.addObject("creditCardNotValid",true);
		}
		
		

		return result;
	}

	// Show -----------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(Integer aiBoxId){

		ModelAndView result = new ModelAndView("aiBox/show");
		AiBox i = aiBoxService.findOne(aiBoxId);
		if(i.getScientist().equals(scientistService.getPrincipal())){
		result.addObject("aiBox", i);
		result.addObject("comments", commentService.findByAiBox(i));
		result.addObject("audits",auditService.getFinalAuditsByAiBox(i));
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}

	// Create -----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Scientist s = scientistService.getPrincipal();
		CreditCard cc =  s.getCreditCard();
		
		if(cc != null){
			Date in7 = new Date(System.currentTimeMillis()+604800000L);
			if(cc.getExpirationDate().after(in7)){
				AiBox aiBox = aiBoxService.create(s);
				result = this.createEditModelAndView(aiBox);
			}else{
				result =  new ModelAndView("error/access");
				result.addObject("creditCardNotValid",true);
			}
		}else{
			result =  new ModelAndView("error/access");
			result.addObject("creditCardNotValid",true);
		}
		

		return result;

	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int aiBoxId) {
		
		ModelAndView result;
		AiBox aiBox = aiBoxService.findOne(aiBoxId);
		
		if (aiBox.getScientist().equals(scientistService.getPrincipal())) {
			result = new ModelAndView();
			result = this.createEditModelAndView(aiBox);
		}else{
			result = new ModelAndView("error/access");
		}

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid AiBox aiBox, BindingResult binding) {
		ModelAndView result;

		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(aiBox);
		} else
			try {
				AiBox saved; 
				if (aiBox.getId() == 0) {
					saved = aiBoxService.save(aiBox);
					Scientist s = scientistService.getPrincipal();
					s.getAiBoxes().add(saved);
					scientistService.save(s);
				}else{
					
					AiBox ir = aiBoxService.findOne(aiBox.getId());
					ir.setDescription(aiBox.getDescription());
					ir.setIsDecomissioned(aiBox.getIsDecomissioned());
					ir.setPrice(aiBox.getPrice());
					ir.setTitle(aiBox.getTitle());
					aiBoxService.save(ir);
				}
				
				result = new ModelAndView("redirect:/aiBox/scientist/list.do");

			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(aiBox,"message.commit.error");
			}
		return result;
	}
	
	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int aiBoxId) {
			
			ModelAndView result;
			AiBox aiBox = aiBoxService.findOne(aiBoxId);
			Scientist s = aiBox.getScientist();
			
			if (aiBox.getScientist().equals(scientistService.getPrincipal())) {
				for (Purchase p : aiBox.getPurchases()) {
					System.out.println("deleted: " + p);
					purchaseService.delete(p);
				}
				
				Collection<Comment> comments = commentService.findByAiBox(aiBox);
				System.out.println(comments);
				
				for (Comment c : comments) {
					Actor a = actorService.findByComment(c);
					a.getComments().remove(c);
					System.out.println("deleted: " + c);
					commentService.delete(c);
				}
				
				s.getAiBoxes().remove(aiBox);
				aiBoxService.delete(aiBox);
				result = new ModelAndView("redirect:/aiBox/scientist/list.do");
				
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}
	
	//Ancillary ----------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(AiBox aiBox) {
		ModelAndView result;
		result = this.createEditModelAndView(aiBox, null);
		return result;
	}

	private ModelAndView createEditModelAndView(AiBox aiBox, final String message) {
		ModelAndView result;
		result = new ModelAndView("aiBox/edit");
		result.addObject("aiBox", aiBox);
		result.addObject("message", message);
		return result;
	}

}
