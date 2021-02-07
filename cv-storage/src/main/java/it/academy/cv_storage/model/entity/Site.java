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
@Table(name="site")
public class Site implements Serializable{
	
	
	private static final long serialVersionUID = 4L;

	@Id
	@GenericGenerator(name = "gen", strategy = "uuid")
	@GeneratedValue(generator = "gen")
	@Column(name="SITE_ID")
	private String siteId;
	
	@Column(name="SITE_URL")
	private String siteUrl;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
						  CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="CANDIDATE_ID")
	private Candidate candidate;

	public Site(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	@Override
	public String toString() {
		return "siteUrl=" + siteUrl;
	}
	
	
	

}
