package com.coronacapsule.api.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.coronacapsule.api.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity       
@Getter       
@SuperBuilder
@NoArgsConstructor
public class Users extends JpaBase {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long userId;

	private String socialId;
	private String nickname;
	
	@ToString.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Capsules> capsuleList = new ArrayList<Capsules>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(Role.USER.value()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

}
