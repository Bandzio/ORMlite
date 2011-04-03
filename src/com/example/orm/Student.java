package com.example.orm;

import java.util.Collection;

import android.util.Log;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
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

	//Klasa student ma kolekcje adresow. 
	// Adres musi miec pole odwolujace sie do konkretnego studenta. 
	@ForeignCollectionField
	private Collection<Adres> adres;
	
	public Student() {
//		Log.v("Student", "konstruktor");
	}
	
	public Student(String name, String surname, String semestr){
		this.name = name;
		this.surname = surname;
		this.semestr = semestr;
		 
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

	public void setAdres(Collection<Adres> adres) {
		this.adres = adres;
	}
	public Collection<Adres> getAdres() {
		return adres;
	}
	
	public int getId() {
		return id;
	}
}
