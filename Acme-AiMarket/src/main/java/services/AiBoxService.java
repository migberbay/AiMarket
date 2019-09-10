package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AiBoxRepository;
import domain.AiBox;
import domain.Purchase;
import domain.Scientist;


@Service
@Transactional
public class AiBoxService {

	//Managed Repository -----
	
	@Autowired
	private AiBoxRepository aiBoxRepository;
	
	//Supporting Services -----
	
	ScientistService scientistService;
	
	//Simple CRUD methods -----
	
	public AiBox create(Scientist s){
		AiBox res = new AiBox();
		res.setScientist(s);
		System.out.println(generateTicker(s));
		res.setTicker(generateTicker(s));
		res.setPurchases(new ArrayList<Purchase>());
		
		return res;
	}
	
	public Collection<AiBox> findAll(){
		return aiBoxRepository.findAll();
	}
	
	public AiBox findOne(int Id){
		return aiBoxRepository.findOne(Id);
	}
	
	public AiBox save(AiBox a){
		AiBox saved = aiBoxRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(AiBox a){
		aiBoxRepository.delete(a);
	}
	
	//Other business methods -----
	String generateTicker(Scientist s){
		String res = "";
		Scientist scientist = s;
		String surnames = scientist.getSurnames();
		if(surnames.length() >4){
			res = res + surnames.substring(0, 4).toUpperCase();
		}else{
			int numberOfX = 4-surnames.length();
			res += surnames.toUpperCase();
			for (int i = 0; i < numberOfX; i++) {
				res += "X";
			}
		}
		res += "-";
		Random r = new Random();
		Integer rndNumber = r.nextInt(9999);
		res += rndNumber.toString();
		
		return res;
	}

	public Collection<AiBox> findByKeyword(String keyword) {
		return this.aiBoxRepository.findByKeyword(keyword);
	}

	public Collection<AiBox> findByScientist(Scientist scientist) {
		return this.aiBoxRepository.findByScientist(scientist);
	}
	
	public Integer getMinimumAiBoxPerScientist(){
		return this.aiBoxRepository.getMinAiBoxesPerScientist();
	}
	
	public Integer getMaximumAiBoxPerScientist(){
		return this.aiBoxRepository.getMaxAiBoxesPerScientist();
	}
	
	public Double getAverageAiBoxPerScientist(){
		return this.aiBoxRepository.getAvgAiBoxesPerScientist();
	}
	
	public Double getStdevAiBoxPerScientist(){
		return this.aiBoxRepository.getStdevAiBoxesPerScientist();
	}
	
	public Collection<AiBox> top10BestSelling(){
		List<AiBox> res = new ArrayList<>(this.aiBoxRepository.Top10SellingAiBoxes());
		Collections.reverse(res);
		if(res.size() > 10){
			res = res.subList(0, 10);
		}
		return res;
	}
	
	public Double getAvgAuditScore(){
		return this.aiBoxRepository.getAvgAuditScore();
	}
	
	public Double getMinAuditScore(){
		return this.aiBoxRepository.getMinAuditScore();	
	}
	
	public Double getMaxAuditScore(){
		return this.aiBoxRepository.getMaxAuditScore();
	}
	
	public Double getStdevAuditScore(){
		return this.aiBoxRepository.getStdevAuditScore();
	}

	
	

}