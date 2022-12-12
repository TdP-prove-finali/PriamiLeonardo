package it.polito.tdp.mtg_cards.model;

public class Espansione {
	
	private String code;
	private String name;

	public Espansione(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return code+" "+name;
	}

}