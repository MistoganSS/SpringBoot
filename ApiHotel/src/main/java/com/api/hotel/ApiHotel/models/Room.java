package com.api.hotel.ApiHotel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name_theme")
	private String nameTheme;
	
	@Column(name = "type") //OR VIEW
	private RoomType type;
	
	@Column(name = "floor_location", nullable = false)
	private String floorLocation;
	
	@Column(name = "night_price", nullable = false)
	private Double nightPrice;
	
}
