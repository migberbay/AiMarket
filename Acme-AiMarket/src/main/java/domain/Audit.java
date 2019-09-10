
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {
	
	// Attributes -----------------------------------------------------------

	private Date moment;
	private String text;
	private Integer score;
	private Boolean isFinal;


	// Getters and Setters ---------------------------------------------------
	
	@Past
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Min(0)
	@Max(10)
	@NotNull
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	// Relationships ----------------------------------------------------------

	private AiBox aiBox;

	public void setAiBox(AiBox aiBox) {
		this.aiBox = aiBox;
	}

	@ManyToOne
	public AiBox getAiBox() {
		return aiBox;
	}

	public void seAiBox(AiBox aiBox) {
		this.aiBox = aiBox;
	}
	

	

}
