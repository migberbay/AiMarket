package controllers.actor;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.AuditService;
import services.AuditorService;
import services.CommentService;
import services.CreditCardService;
import services.CustomerService;
import services.AiBoxService;
import services.PurchaseService;
import services.ScientistService;
import controllers.AbstractController;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.Comment;
import domain.CreditCard;
import domain.Customer;
import domain.AiBox;
import domain.Purchase;
import domain.Scientist;

@Controller
@RequestMapping("actor/")
public class ActorDeleteController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ScientistService scientistService;
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private AiBoxService aiBoxService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private AuditService auditService;

	// Constructors ------------------------------------------------------------

	public ActorDeleteController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteActor() {
		ModelAndView res;
		Collection <Authority> auths = LoginService.getPrincipal().getAuthorities();
		Authority customerAuth = new Authority();
		Authority scientistAuth = new Authority();

		customerAuth.setAuthority(Authority.CUSTOMER);
		scientistAuth.setAuthority(Authority.SCIENTIST);


		if (auths.contains(customerAuth)) {
			res = this.deleteCustomer();
		}else if(auths.contains(scientistAuth)){
			res = this.deleteScientist();
		}else{
			res = new ModelAndView("error/access");
		}
		
		return res;
	}
	
	
	public ModelAndView deleteCustomer() {
		
		ModelAndView res = new ModelAndView("redirect:/j_spring_security_logout");
	try {
		Customer c = customerService.getPrincipal();
		
		for (Comment comment : c.getComments()) {
			c.getComments().remove(comment);
			c = customerService.save(c);
			System.out.println("deleting: "+ comment);
			comment.setAiBox(null);
			comment = commentService.save(comment);
			commentService.delete(comment);
		}
		
		for (Purchase p : c.getPurchases()) {
			p.getAiBox().getPurchases().remove(p);
			aiBoxService.save(p.getAiBox());
			System.out.println("deleting: "+ p);
			purchaseService.delete(p);
		}
		
		
		UserAccount ua = c.getUserAccount();
		
		CreditCard creditCard = c.getCreditCard();
		c.setCreditCard(null);
		c = customerService.save(c);
		creditCardService.delete(creditCard);
		
		customerService.delete(c);
		userAccountService.delete(ua);
	} catch (Exception e) {
		deleteCustomer();
	}
		return res;
	}
	
	public ModelAndView deleteScientist() {
		
		ModelAndView res = new ModelAndView("redirect:/j_spring_security_logout");
//		ModelAndView res = new ModelAndView("redirect:/");
		
		try {

		Scientist s = scientistService.getPrincipal();
		
		for (Comment comment : s.getComments()) {
			s.getComments().remove(comment);
			s = scientistService.save(s);
			System.out.println("deleting: "+ comment);
			comment.setAiBox(null);
			comment = commentService.save(comment);
			commentService.delete(comment);
		}
		
		Collection<AiBox> robots = s.getAiBoxes();
		
		for (int i = 0; i < robots.size(); i++) {
		AiBox aiBox = (AiBox) robots.toArray()[i];
		
		Collection<Purchase> purchases = aiBox.getPurchases(); 
		System.out.println(purchases);
			for (int j = 0; j < purchases.size(); j++) {
				Purchase p =(Purchase) purchases.toArray()[j];
				System.out.println("deleted: " + p);
				aiBox.getPurchases().remove(p);
				purchaseService.delete(p);
			}
			
		Collection<Audit> audits = auditService.getAuditsByAiBox(aiBox);
		System.out.println(audits);
			for (Audit a : audits) {
				Auditor auditor = auditorService.findByAudit(a);
				auditor.getAudits().remove(a);
				System.out.println("deleted: " + a);
				auditService.delete(a);
			}
			
		Collection<Comment> comments = commentService.findByAiBox(aiBox);
		System.out.println(comments);
			for (Comment c : comments) {
				Actor a = actorService.findByComment(c);
				if(a != null){
					a.getComments().remove(c);
				}
				System.out.println("deleted: " + c);
				commentService.delete(c);
			}
			
			s.getAiBoxes().remove(aiBox);
			System.out.println("deleting: "+ aiBox);
			aiBoxService.delete(aiBox);
		}

		UserAccount ua = s.getUserAccount();	
		
		CreditCard creditCard = s.getCreditCard();
		s.setCreditCard(null);
		s = scientistService.save(s);
		creditCardService.delete(creditCard);
		
		scientistService.delete(s);
		userAccountService.delete(ua);
		
		
		} catch (Exception e) {
			//e.printStackTrace();
			//System.out.println("Caught one boss!");
			deleteScientist();
		}
		
		return res;
	}
}
