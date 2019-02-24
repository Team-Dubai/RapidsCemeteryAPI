package edu.rit.iste500.dubai.RapidsCemeteryAPI.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "RC_USER")
@JsonIdentityInfo(scope = User.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 5397548388844790212L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Boolean enabled = true;

	@Column(length = 128)
	private String password;

	@Column(length = 128)
	private String email;

	@Column(length = 64)
	private String salt;

	@Column(nullable = false, length = 30)
	private String username;

	@Column(name = "PASSWORD_RESET", nullable = false)
	private Boolean passwordReset = true;

	@Column(name = "FORGOTTEN_USERNAME", nullable = false)
	private Boolean forgottenUsername = false;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
