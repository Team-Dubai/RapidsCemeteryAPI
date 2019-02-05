package edu.rit.iste500.dubai.RapidsCemeteryAPI.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.enums.CategoryEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "RC_ITEM")
@JsonIdentityInfo(scope = Item.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

}
