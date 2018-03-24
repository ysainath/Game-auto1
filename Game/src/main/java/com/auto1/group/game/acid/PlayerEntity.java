/**
 * 
 */
package com.auto1.group.game.acid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author yelsa03
 *
 */
@Entity
@Table(name = "PLAYER")
@EntityListeners(AuditingEntityListener.class)
public class PlayerEntity implements Serializable {

	private static final long serialVersionUID = -5464488187546407770L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(name = "PLAYER_NAME", nullable = false)
	private String name;

	@NotNull
	@Size(max = 100)
	@Column(name = "GAME_NAME", nullable = false)
	private String gameName;

	@Column(name = "HEALTH")
	private Integer health;

	@Column(name = "HEALTH_BOTTLES")
	private Integer healthBottles;

	@Column(name = "CURRENT_LEVEL")
	private Integer currentLevel;

	@Column(name = "SCORE")
	private Integer score;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@CreatedDate
	private Long createdDate;

	@Column(name = "MODIFIED_DATE")
	@LastModifiedDate
	private Long modifiedDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "playerEntity")
	private List<GameZoneEntity> gameZones = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "playerEntity")
	private List<ItemEntity> items = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}

	public Integer getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public List<GameZoneEntity> getGameZones() {
		return gameZones;
	}

	public void setGameZones(List<GameZoneEntity> gameZones) {
		this.gameZones = gameZones;
	}

	public List<ItemEntity> getItems() {
		return items;
	}

	public void setItems(List<ItemEntity> items) {
		this.items = items;
	}

	public void addItemEntity(String itemName, Integer count, PlayerEntity playerEntity) {
		if (this.items == null) {
			items = new ArrayList<>();
		}
		ItemEntity entity = new ItemEntity();
		entity.setItemName(itemName);
		entity.setItemCount(count);
		entity.setPlayerEntity(playerEntity);
		this.items.add(entity);
	}

	public Integer getHealthBottles() {
		return healthBottles;
	}

	public void setHealthBottles(Integer healthBottles) {
		this.healthBottles = healthBottles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addZoneEntity(String zoneName, Integer visitedCount, PlayerEntity playerEntity) {
		if (this.gameZones == null) {
			gameZones = new ArrayList<>();
		}
		GameZoneEntity entity = new GameZoneEntity();
		entity.setZoneName(zoneName);
		entity.setVisitedCount(visitedCount);
		entity.setPlayerEntity(playerEntity);
		this.gameZones.add(entity);

	}

}
