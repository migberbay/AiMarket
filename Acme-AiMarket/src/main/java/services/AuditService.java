package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AuditRepository;
import domain.AiBox;
import domain.Audit;
import domain.Auditor;


@Service
@Transactional
public class AuditService {

	//Managed Repository -----
	
	@Autowired
	private AuditRepository auditRepository;
	
	//Supporting Services -----
	
	@Autowired
	private AuditorService auditorService;
	
	//Simple CRUD methods -----
	
	public Audit create(AiBox aiBox){
		Audit res = new Audit();
		res.setAiBox(aiBox);
		
		return res;
	}
	
	public Collection<Audit> findAll(){
		return auditRepository.findAll();
	}
	
	public Audit findOne(int Id){
		return auditRepository.findOne(Id);
	}
	
	public Audit save(Audit a){
		
		Audit saved = auditRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Audit a){
		auditRepository.delete(a);
	}

	public boolean findIfHasAuditWithAiBox(AiBox box) {
		Auditor auditor = auditorService.getPrincipal();
		for (Audit a : auditor.getAudits()) {
			if(a.getAiBox() == box){
				return true;
			}
		}
		return false;
	}
	
	//Other business methods -----
	
	public Collection<Audit> getAuditsByAiBox(AiBox aiBox){
		return this.auditRepository.getAuditsByAiBox(aiBox);
	}

	public Collection<Audit> getFinalAuditsByAiBox(AiBox a) {
		return this.auditRepository.getFinalAuditsByAiBox(a);
	}

}