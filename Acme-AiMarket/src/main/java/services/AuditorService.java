package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AuditorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.AiBox;
import domain.Audit;
import domain.Auditor;


@Service
@Transactional
public class AuditorService {

	//Managed Repository -----
	
	@Autowired
	private AuditorRepository auditorRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Auditor create(UserAccount ua){
		Auditor res = new Auditor();
		res.setUserAccount(ua);
		res.setNotified(true);//new users do not need to be notified of the change.
		
		return res;
	}
	
	public Collection<Auditor> findAll(){
		return auditorRepository.findAll();
	}
	
	public Auditor findOne(int Id){
		return auditorRepository.findOne(Id);
	}
	
	public Auditor save(Auditor a){
		
		Auditor saved = auditorRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Auditor a){
		auditorRepository.delete(a);
	}

	public Auditor getPrincipal() {
		return this.auditorRepository.findByPrincipal(LoginService.getPrincipal());
	}

	public Auditor findByAudit(Audit a) {
		Auditor res = null;
		
		for (Auditor auditor : this.findAll()) {
			if(auditor.getAudits().contains(a)){
				res = auditor;
				break;
			}
		}
		return res;
	}
	
	//Other business methods -----
	
	

}