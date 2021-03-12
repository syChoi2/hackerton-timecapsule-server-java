package com.coronacapsule.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.coronacapsule.api.dto.MarbleDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity       
@Getter       
@SuperBuilder 
@NoArgsConstructor
public class Marbles extends JpaBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long marbleId;
	
	@ManyToOne(fetch =FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="capsule_id")
    @JsonBackReference
	private Capsules capsule;

	private String content;
	private String marbleColor;
	private boolean wishChecked;
	
	public void setCapsule(Capsules capsule) {
		this.capsule = capsule;
		capsule.getMarbleList().add(this);
		
	}

	public MarbleDto convertToDto() {
		return MarbleDto.builder()
			.marbleId(marbleId)
			.content(content)
			.marbleColor(marbleColor)
			.wishChecked(wishChecked)
			.build();
	}

	public void checkWish() {
		this.wishChecked = true;
	}
	
	
	
}
