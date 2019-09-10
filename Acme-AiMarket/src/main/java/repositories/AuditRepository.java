
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AiBox;
import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {
	
	@Query("select a from Audit a where a.aiBox = ?1")
	Collection<Audit> getAuditsByAiBox(AiBox aiBox);
	
	@Query("select a from Audit a where a.aiBox = ?1 and a.isFinal = true")
	Collection<Audit> getFinalAuditsByAiBox(AiBox aiBox);
	
}
