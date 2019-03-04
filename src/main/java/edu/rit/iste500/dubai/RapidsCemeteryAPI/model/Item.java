package edu.rit.iste500.dubai.RapidsCemeteryAPI.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.enums.CategoryEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "RC_ITEM")
// @JsonIdentityInfo(scope = Item.class, generator =
// ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item implements Serializable {

	private static final long serialVersionUID = 8741299117427927402L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY")
	private CategoryEnum category;

	@Column(name = "IMAGE")
	private String image;

	@Column(name = "Media")
	private String media;

	@Column(name = "DOB")
	private String dateOfBirth;

	@Column(name = "DOD")
	private String dateOfDeath;

	@Column(name = "place_of_birth")
	private String placeOfBirth;

	@Column(name = "place_of_death")
	private String placeOfDeath;

	@Column(name = "plot")
	private String plot;

	@Column(name = "veteran_info")
	private String veteranInformation;

	@Column(name = "notes")
	private String notes;

	@ManyToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinTable(name = "RC_ITEM_TAGS", joinColumns = { @JoinColumn(name = "item_id") }, inverseJoinColumns = {
			@JoinColumn(name = "tag_id") })
	private List<Tag> tags;

}
