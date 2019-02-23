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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "RC_STOP")
@JsonIdentityInfo(scope = Stop.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Stop implements Serializable {

	private static final long serialVersionUID = -2354210479615605291L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@ManyToMany(cascade = { CascadeType.REMOVE })
	@JoinTable(name = "RC_STOP_ITEMS", joinColumns = { @JoinColumn(name = "stop_id") }, inverseJoinColumns = {
			@JoinColumn(name = "item_id") })
	private List<Item> items;

}
