package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Scientist extends Actor {
	
	public String VATNumber;
	public Double auditScore;
	
	@NotBlank
	@Pattern(regexp="^([E]{1}[S]{1})([A-Z]{1})([0-9]{8})$")
	public String getVATNumber() {
		return VATNumber;
	}

	public void setVATNumber(String VATNumber) {
		this.VATNumber = VATNumber;
	}
	
	
	public Double getAuditScore() {
		return auditScore;
	}

	public void setAuditScore(Double auditScore) {
		this.auditScore = auditScore;
	}



	//Relationships ---------------------------------------------------
	private CreditCard creditCard;
	private Collection<AiBox> aiBoxes;
	
	@OneToOne (optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@OneToMany (mappedBy = "scientist")
	public Collection<AiBox> getAiBoxes() {
		return aiBoxes;
	}

	public void setAiBoxes(Collection<AiBox> aiBoxes) {
		this.aiBoxes = aiBoxes;
	}
	
	

}
