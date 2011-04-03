package com.example.orm;

import java.io.Serializable;
import java.util.Collection;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

public class Adres implements Serializable {

	@DatabaseField(generatedId=true)
	private int id;

	@DatabaseField(foreign=true, columnName="student_id")
	private Student student;
	
	@DatabaseField
	private String miasto;
	
	@DatabaseField
	private String ulica;
	
	@DatabaseField
	private String nr;
	
	@ForeignCollectionField
	private Collection<Phone> phone;
	
	
	public Adres() {
//		Log.v("Adres", "konstruktor");
	}
	
	public Adres(Student s, String miasto, String ulica , String nr){
		this.student = s;
		this.miasto = miasto;
		this.ulica = ulica;
		this.nr = nr;
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

	public Collection<Phone> getPhone() {
		return phone;
	}
	
	public void setPhone(Collection<Phone> phone) {
		this.phone = phone;
	}
	
}
