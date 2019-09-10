
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.AiBox;
import domain.Audit;
import domain.Auditor;
import domain.Customer;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor, Integer> {
	
	
	@Query("select a from Auditor a where a.userAccount = ?1") 
	Auditor findByPrincipal(UserAccount principal);
	
//	@Query("select a from Auditor a where :audit in (a.audits)")
//	Auditor findByAudit(Audit audit);
	
	
}
