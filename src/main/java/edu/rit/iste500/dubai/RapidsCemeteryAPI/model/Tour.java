package edu.rit.iste500.dubai.RapidsCemeteryAPI.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "RC_TOUR")
@JsonIdentityInfo(scope = Tour.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tour implements Serializable {

	private static final long serialVersionUID = 8599867063575249334L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "IMAGE")
	private String image;

	@Column(name = "Media")
	private String media;

	@ManyToMany(cascade = { CascadeType.ALL })
	@OrderColumn(name = "index")
	@JoinTable(name = "RC_TOUR_STOPS", joinColumns = { @JoinColumn(name = "tour_id") }, inverseJoinColumns = {
			@JoinColumn(name = "stop_id") })
	private List<Stop> stops;

}
