package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.AiBox;
import domain.Audit;
import domain.Auditor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class AuditServiceTest extends AbstractTest {

	
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private AiBoxService aiBoxService;
	

	
	
	
	//	Se comprueba que solo los auditor pueden crear los audites.
	@Test
	public void driverCreateAudit(){
		
		final Object testingData[][] = {{"auditor1", null},
										{"auditor2", null},
										{"customer1",null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateAudit((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateAudit(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.auditService.create((AiBox) aiBoxService.findAll().toArray()[0]);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que los auditors pueden guardar las betpools.
	@Test
	public void testSave(){
		
		authenticate("auditor1");
		
		Audit audit = auditService.create((AiBox) aiBoxService.findAll().toArray()[0]);
		
		audit.setIsFinal(true);
		audit.setMoment(new Date(System.currentTimeMillis()-1000));
		audit.setScore(6);
		audit.setText("hola");

		
		Audit result = auditService.save(audit);
		
		Assert.isTrue(auditService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede guardar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("auditor1");

		Audit audit = auditService.create((AiBox) aiBoxService.findAll().toArray()[0]);
		
		audit.setIsFinal(true);
		audit.setMoment(new Date(System.currentTimeMillis()+50000));
		audit.setScore(65);
		audit.setText("");
		
		Audit result = auditService.save(audit);
		
		Assert.isTrue(auditService.findAll().contains(result));
		
		unauthenticate();
	}
	

	
	//	Se comprueba que los auditors pueden actualizar los audites
	@Test
	public void testUpdate(){
		
		authenticate("auditor1");
		
		Audit audit = (Audit) auditService.findAll().toArray()[0];
		
		audit.setMoment(new Date(System.currentTimeMillis()-1000));
		audit.setScore(10);
		audit.setText("updateado");
		
		Audit result = auditService.save(audit);
		
		Assert.isTrue(auditService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede actualizar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testUpdateIncorrectData(){
		
		authenticate("auditor1");
		
		Audit audit = (Audit) auditService.findAll().toArray()[0];
		
		audit.setIsFinal(true);
		audit.setMoment(new Date(System.currentTimeMillis()+50000));
		audit.setScore(68);
		audit.setText("");
		
		Audit result = auditService.save(audit);
		
		Assert.isTrue(auditService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede eliminar sin estar logeado como auditor.
	public void testDeleteNotAuthenticated(){
		
		authenticate(null);
		
		Audit audit = (Audit) auditService.findAll().toArray()[0];
		
	    auditService.delete(audit);
		
		Assert.isTrue(!auditService.findAll().contains(audit));
		
		unauthenticate();
	}
	

}