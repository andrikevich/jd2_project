package it.academy.cv_storage.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import it.academy.cv_storage.model.utilities.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table
public class Candidate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "gen", strategy = "uuid")
	@GeneratedValue(generator = "gen")
	@Column(name="ID")
	private String id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "GENDER")
	private Gender gender;

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private Set<Phone> phone;

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private Set<Site> site;

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private Set<Email> email;

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private Set<Skype> skype;

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private Set<Knowledge> knowledge;

	public Candidate(String firstName, String middleName, String lastName, Date birthDate, Gender gender) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	public void addPhone(Phone thePhone) {
		if (phone == null) {
			phone = new HashSet<Phone>();
		}
		phone.add(thePhone);
		thePhone.setCandidate(this);
	}
	
	public void addSite(Site theSite) {
		if (site == null) {
			site = new HashSet<Site>();
		}
		site.add(theSite);
		theSite.setCandidate(this);
	}
	
	public void addEmail(Email theEmail) {
		if (email == null) {
			email = new HashSet<Email>();
		}
		email.add(theEmail);
		theEmail.setCandidate(this);
	}
	
	public void addSkype(Skype theSkype) {
		if (skype == null) {
			skype = new HashSet<Skype>();
		}
		skype.add(theSkype);
		theSkype.setCandidate(this);
	}
	public void addKnowledge(Knowledge theKnowledge) {
		if (knowledge == null) {
			knowledge = new HashSet<Knowledge>();
		}
		knowledge.add(theKnowledge);
		theKnowledge.setCandidate(this);
	}
}
