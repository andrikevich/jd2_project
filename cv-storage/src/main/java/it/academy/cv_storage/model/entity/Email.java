package it.academy.cv_storage.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="email")
public class Email implements Serializable{
	

	private static final long serialVersionUID = 3L;

	@Id
	@GenericGenerator(name = "gen", strategy = "uuid")
	@GeneratedValue(generator = "gen")
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
			  CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="CANDIDATE_ID")
	private Candidate candidate;

	public Email(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "emailAdress=" + emailAddress;
	}
	


}
