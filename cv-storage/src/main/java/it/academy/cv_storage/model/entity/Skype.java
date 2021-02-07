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
@Table(name="skype")
public class Skype implements  Serializable {
	

	private static final long serialVersionUID = 5L;

	@Id
	@GenericGenerator(name = "gen", strategy = "uuid")
	@GeneratedValue(generator = "gen")
	@Column(name="SKYPE_ID")
	private String skypeId;
	
	@Column(name="SKYPE_USER_NAME")
	private String skypeUserName;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
			  CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="CANDIDATE_ID")
	private Candidate candidate;

	public Skype(String skypeUserName) {
		super();
		this.skypeUserName = skypeUserName;
	}

	@Override
	public String toString() {
		return "skypeUserName=" + skypeUserName;
	}
	


}
