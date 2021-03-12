package com.coronacapsule.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.coronacapsule.api.dto.CapsuleDto;
import com.coronacapsule.api.dto.MarbleColorResultSet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity       
@Getter       
@SuperBuilder 
public class Capsules extends JpaBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long capsuleId;
	
	@ManyToOne(fetch =FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
    @JsonBackReference
	private Users user;
	
	private String capsuleName;
	private int allowedMarbleCount;
	
	@ToString.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy = "capsule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Marbles> marbleList = new ArrayList<Marbles>();

	public void changeCapsuleName(String capsuleName) {
		this.capsuleName = capsuleName;
	}

	public CapsuleDto convertToCapsuleDto() {
		
		return CapsuleDto.builder()
				.capsuleId(capsuleId)
				.capsuleName(capsuleName)
				.allowedMarbleCount(allowedMarbleCount)
				.build();
		
	}
	
	public CapsuleDto convertToCapsuleDto(List<MarbleColorResultSet> marbleColorCountDto) {
		
		return CapsuleDto.builder()
				.capsuleId(capsuleId)
				.capsuleName(capsuleName)
				.allowedMarbleCount(allowedMarbleCount)
				.marbleColorCount(marbleColorCountDto)
				.build();
		
	}
	
	
}