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
@Table(name="phone")
public class Phone implements Serializable{

	private static final long serialVersionUID = 2L;

	@Id
	@GenericGenerator(name = "gen", strategy = "uuid")
	@GeneratedValue(generator = "gen")
	@Column(name="PHONE_ID")
	private String phoneId;
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
			  CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="CANDIDATE_ID")
	private Candidate candidate;

	public Phone(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "phoneNumber=" + phoneNumber;
	}
	
	
}
