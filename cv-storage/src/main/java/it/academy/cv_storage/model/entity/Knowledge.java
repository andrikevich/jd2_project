package it.academy.cv_storage.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

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

	@Column(name="KNOWLEDGE_NAME")
	private String knowledgeName;

	
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,
			  CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(
			name = "candidate_knowledge",
			joinColumns = @JoinColumn(name = "KNOWLEDGE_ID"	),
			inverseJoinColumns = @JoinColumn(name = "CANDIDATE_ID"))
	private Set<Candidate> candidates;

	public void addCandidate(Candidate theCandidate) {
		if (candidates == null) {
			candidates = new HashSet<Candidate>();
		}
		candidates.add(theCandidate);
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public Set<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(Set<Candidate> candidates) {
		this.candidates = candidates;
	}

	public String getKnowledgeName() {
		return knowledgeName;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}


	public Knowledge(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	@Override
	public String toString() {
		return "Knowledge{" +
				"knowledgeName='" + knowledgeName + '\'' +
				'}';
	}
}
