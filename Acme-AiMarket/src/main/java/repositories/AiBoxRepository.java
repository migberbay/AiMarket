
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AiBox;
import domain.Scientist;

@Repository
public interface AiBoxRepository extends JpaRepository<AiBox, Integer> {

	@Query("select i from AiBox i where i.title like %?1% or " +
			"i.ticker like %?1% or i.description like %?1%") 
	Collection<AiBox> findByKeyword(String keyword);

	@Query("select s.aiBoxes from Scientist s where s = ?1") 
	Collection<AiBox> findByScientist(Scientist scientist);
	
	@Query("select avg(s.aiBoxes.size) from Scientist s")
	Double getAvgAiBoxesPerScientist();

	@Query("select min(s.aiBoxes.size) from Scientist s")
	Integer getMinAiBoxesPerScientist();
	
	@Query("select max(s.aiBoxes.size) from Scientist s")
	Integer getMaxAiBoxesPerScientist();
	
	@Query("select stddev(s.aiBoxes.size) from Scientist s")
	Double getStdevAiBoxesPerScientist();
	
	@Query("select i from AiBox i order by size(i.purchases)")
	Collection<AiBox> Top10SellingAiBoxes();
	
	@Query("select avg(a.auditScore) from AiBox a where a.auditScore is not null")
	Double getAvgAuditScore();

	@Query("select min(a.auditScore) from AiBox a where a.auditScore is not null")
	Double getMinAuditScore();
	
	@Query("select max(a.auditScore) from AiBox a where a.auditScore is not null")
	Double getMaxAuditScore();
	
	@Query("select stddev(a.auditScore) from AiBox a where a.auditScore is not null")
	Double getStdevAuditScore();
	
	
	
}
