package com.example.orm;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*
 * prosta struktura danych , ktora chcemy persystowac do db
 */
@DatabaseTable
public class Student {

	@DatabaseField(generatedId=true)
	private int id;

	@DatabaseField
	private String name;
	
	@DatabaseField
	private String surname;
	
	@DatabaseField
	private String semestr;

	@DatabaseField(foreign=true,canBeNull=false)
	private Adres adres;
	
	public Student() {
		Log.v("Student", "konstruktor");
	}
	

	public void setName(String name) {
		this.name = name;
	}
	public void setSemestr(String semestr) {
		this.semestr = semestr;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return name;
	}
	public String getSemestr() {
		return semestr;
	}
	public String getSurname() {
		return surname;
	}
	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	public Adres getAdres() {
		return adres;
	}
	public int getId() {
		return id;
	}
}
