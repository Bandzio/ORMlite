package com.example.orm;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;

public class Adres {

	@DatabaseField(generatedId=true)
	private int id;
	
	@DatabaseField
	private String miasto;
	
	@DatabaseField
	private String ulica;
	
	@DatabaseField
	private String nr;
	
	public Adres() {
		Log.v("Adres", "konstruktor");
	}
	
	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}
	public void setNr(String nr) {
		this.nr = nr;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public String getMiasto() {
		return miasto;
	}
	public String getNr() {
		return nr;
	}
	public String getUlica() {
		return ulica;
	}
	public int getId() {
		return id;
	}
}
