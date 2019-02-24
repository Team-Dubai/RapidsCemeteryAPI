package edu.rit.iste500.dubai.RapidsCemeteryAPI.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "RC_TAG")
@JsonIdentityInfo(scope = Tag.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tag {
	private static final long serialVersionUID = 1549318808833579348L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;
}
