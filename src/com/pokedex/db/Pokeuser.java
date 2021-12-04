package com.pokedex.db;

public class Pokeuser {
	private int id;
	private String name;
	private int rank;
	private String[] pokemon;
	private String gender;
	private String region;
	private byte age;

	public Pokeuser(int id, int rank, String name, byte age, String gender, String region, String[] pokemon) {
		this.id = id;
		this.name = name;
		this.rank = rank;
		this.pokemon = pokemon;
		this.gender = gender;
		this.region = region;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String[] getPokemon() {
		return pokemon;
	}

	public void setPokemon(String[] pokemon) {
		this.pokemon = pokemon;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

}
