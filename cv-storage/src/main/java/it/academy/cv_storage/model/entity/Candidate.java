package it.academy.cv_storage.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import it.academy.cv_storage.model.utilities.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "candidate")
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
			CascadeType.REFRESH },targetEntity = Phone.class)
	private Set<Phone> phones;

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH },targetEntity = Site.class)
	private Set<Site> sites;

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH },targetEntity = Email.class)
	private Set<Email> emails;

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH },targetEntity = Skype.class)
	private Set<Skype> skypes;

	@ManyToMany (cascade = {CascadeType.DETACH,CascadeType.MERGE,
			CascadeType.PERSIST,CascadeType.REFRESH},targetEntity = Knowledge.class)
	@JoinTable(
			name = "candidate_knowledge",
			joinColumns = @JoinColumn(name = "CANDIDATE_ID"	),
			inverseJoinColumns = @JoinColumn(name = "KNOWLEDGE_ID"))
	private Set<Knowledge> knowledges;

	public Candidate(String firstName, String middleName, String lastName, Date birthDate, Gender gender) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	public void addPhone(Phone thePhone) {
		if (phones == null) {
			phones = new HashSet<Phone>();
		}
		phones.add(thePhone);
		thePhone.setCandidate(this);
	}
	
	public void addSite(Site theSite) {
		if (sites == null) {
			sites = new HashSet<Site>();
		}
		sites.add(theSite);
		theSite.setCandidate(this);
	}
	
	public void addEmail(Email theEmail) {
		if (emails == null) {
			emails = new HashSet<Email>();
		}
		emails.add(theEmail);
		theEmail.setCandidate(this);
	}
	
	public void addSkype(Skype theSkype) {
		if (skypes == null) {
			skypes = new HashSet<Skype>();
		}
		skypes.add(theSkype);
		theSkype.setCandidate(this);
	}
	public void addKnowledge(Knowledge theKnowledge) {
		if (knowledges == null) {
			knowledges = new HashSet<Knowledge>();
		}
		knowledges.add(theKnowledge);
		//theKnowledge.setCandidate(this);
	}


	@Override
	public String toString() {
		return "Candidate{" +
				"firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				", birthDate=" + birthDate +
				", gender=" + gender +
				", phones=" + phones +
				", sites=" + sites +
				", emails=" + emails +
				", skypes=" + skypes +
				", knowledges=" + knowledges +
				'}';
	}
}
