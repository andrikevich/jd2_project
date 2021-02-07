package it.academy.cv_storage.model.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
@Table(name="knowledge")
public class Knowledge implements Serializable{

	private static final long serialVersionUID = 6L;

	@Id
	@GenericGenerator(name = "gen", strategy = "uuid")
	@GeneratedValue(generator = "gen")
	@Column(name="KNOWLEDGE_ID")
	private String knowledgeId;
	
	@ElementCollection
	@Column(name="KNOWN_TECHNOLOGIES")
	@CollectionTable(name="KNOWN_TECHNOLOGIES")
	private Set<String> knownTechnologies;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
			  CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="CANDIDATE_ID")
	private Candidate candidate;

	public Knowledge(Set<String> knownTechnologies) {
		this.knownTechnologies = knownTechnologies;
	}

	@Override
	public String toString() {
		return "knownTechnologies=" + knownTechnologies ;
	}
	


}
