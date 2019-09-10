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
import domain.Scientist;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class AiBoxServiceTest extends AbstractTest {

	
	
	@Autowired
	private AiBoxService aiBoxService;
	
	@Autowired
	private ScientistService scientistService;
	

	
	
	
	//	Se comprueba que solo los scientist pueden crear los aiBoxes.
	@Test
	public void driverCreateAiBox(){
		
		final Object testingData[][] = {{"scientist1", null},
										{"scientist2", null},
										{"customer1", NullPointerException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateAiBox((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateAiBox(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.aiBoxService.create(scientistService.getPrincipal());
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que los scientists pueden guardar las betpools.
	@Test
	public void testSave(){
		
		authenticate("scientist1");
		
		AiBox aiBox = aiBoxService.create(scientistService.getPrincipal());
		
		aiBox.setTitle("Title aiBox");
		aiBox.setDescription("Description aiBox");
		aiBox.setIsDecomissioned(false);
		aiBox.setPrice(678);
		
		AiBox result = aiBoxService.save(aiBox);
		
		Assert.isTrue(aiBoxService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede guardar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("scientist1");

		AiBox aiBox = aiBoxService.create(scientistService.getPrincipal());
		
		aiBox.setTitle("");
		aiBox.setDescription("");	
		aiBox.setIsDecomissioned(null);
		aiBox.setPrice(null);
		
		AiBox result = aiBoxService.save(aiBox);
		
		Assert.isTrue(aiBoxService.findAll().contains(result));
		
		unauthenticate();
	}
	

	
	//	Se comprueba que los scientists pueden actualizar los aiBoxes
	@Test
	public void testUpdate(){
		
		authenticate("scientist1");
		
		AiBox aiBox = (AiBox) aiBoxService.findAll().toArray()[0];
		
		aiBox.setTitle("Updated title aiBox");
		aiBox.setDescription("Updated description aiBox");
		
		AiBox result = aiBoxService.save(aiBox);
		
		Assert.isTrue(aiBoxService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede actualizar con campos en blanco.
	@Test(expected = ConstraintViolationException.class)
	public void testUpdateIncorrectData(){
		
		authenticate("scientist1");
		
		AiBox aiBox = (AiBox) aiBoxService.findAll().toArray()[0];
		
		aiBox.setTitle("");
		aiBox.setDescription("");
		
		AiBox result = aiBoxService.save(aiBox);
		
		Assert.isTrue(aiBoxService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	Se comprueba que no se puede eliminar sin estar logeado como scientist.
	@Test(expected = DataIntegrityViolationException.class)
	public void testDeleteNotAuthenticated(){
		
		authenticate(null);
		
		AiBox aiBox = (AiBox) aiBoxService.findAll().toArray()[0];
		
	    aiBoxService.delete(aiBox);
		
		Assert.isTrue(!aiBoxService.findAll().contains(aiBox));
		
		unauthenticate();
	}
	

}