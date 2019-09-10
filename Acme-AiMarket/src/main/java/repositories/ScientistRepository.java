
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.AiBox;
import domain.Scientist;

@Repository
public interface ScientistRepository extends JpaRepository<Scientist, Integer> {
	
	@Query("select s from Scientist s where s.userAccount = ?1") 
	Scientist findByPrincipal(UserAccount principal);
	
	@Query("select s from Scientist s where s.auditScore is not null order by s.auditScore desc") 
	Collection<Scientist> top3ByAuditScore();

	@Query("select avg(s.auditScore) from Scientist s where s.auditScore is not null")
	Double getAvgAuditScore();

	@Query("select min(s.auditScore) from Scientist s where s.auditScore is not null")
	Double getMinAuditScore();
	
	@Query("select max(s.auditScore) from Scientist s where s.auditScore is not null")
	Double getMaxAuditScore();
	
	@Query("select stddev(s.auditScore) from Scientist s where s.auditScore is not null")
	Double getStdevAuditScore();

}
