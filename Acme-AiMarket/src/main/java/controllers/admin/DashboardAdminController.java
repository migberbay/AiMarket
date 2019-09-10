package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ScientistService;
import services.AiBoxService;

import controllers.AbstractController;


@Controller
@RequestMapping("/admin/")
public class DashboardAdminController extends AbstractController {
	
	
	@Autowired
	private AiBoxService aiBoxService;

	@Autowired
	private ScientistService scientistService;
	
	

	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView res;
	
		res = new ModelAndView("admin/dashboard");

		res.addObject("avgAiBoxesPerScientist", Math.round(aiBoxService.getAverageAiBoxPerScientist()*100.0d)/100.0d);
		res.addObject("minAiBoxesPerScientist", aiBoxService.getMinimumAiBoxPerScientist());
		res.addObject("maxAiBoxesPerScientist", aiBoxService.getMaximumAiBoxPerScientist());
		res.addObject("stdevAiBoxesPerScientist", Math.round(aiBoxService.getStdevAiBoxPerScientist()*100.0d)/100.0d);
		
		res.addObject("avgAuditScoreAiBox",aiBoxService.getAvgAuditScore());
		res.addObject("minAuditScoreAiBox",aiBoxService.getMinAuditScore());
		res.addObject("maxAuditScoreAiBox", aiBoxService.getMaxAuditScore());
		res.addObject("stdevAuditScoreAiBox", aiBoxService.getStdevAuditScore());
		
		res.addObject("avgAuditScoreScientist", scientistService.getAvgAuditScore());
		res.addObject("minAuditScoreScientist",scientistService.getMinAuditScore());
		res.addObject("maxAuditScoreScientist", scientistService.getMaxAuditScore());
		res.addObject("stdevAuditScoreScientist", scientistService.getStdevAuditScore());
		
		res.addObject("top10bestSellingScientist", scientistService.Top10BestSelling());
		res.addObject("top10bestSellingAiBoxes", aiBoxService.top10BestSelling());
		res.addObject("top3ScientistAuditScore", scientistService.top3InAuditScore());

		return res;
	}
	
	
}