package com.example.orm;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseORMAdapter extends OrmLiteSqliteOpenHelper {

	private static final String tag = DataBaseORMAdapter.class.getSimpleName();
	private static String DATABASE_NAME = "baza_studenci_orm";
	private static int DATABASE_VERSION = 1;

	Dao<Student, Integer> studentDao = null;
	Dao<Adres, Integer> adresDao = null;
	Dao<Phone, Integer> phoneDao = null;

	public DataBaseORMAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.v(tag, "DataBaseORMAdapter()");
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		Log.v(tag, "onCreate");
		try {
			TableUtils.createTable(connectionSource, Student.class);
			TableUtils.createTable(connectionSource, Adres.class);
			TableUtils.createTable(connectionSource, Phone.class);
		} catch (SQLException e) {
			Log.v("ERROR", "onCreate");
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		Log.v(tag, "onUpgrade");
	}

	public Dao<Student, Integer> getSimpleDao() {
		Log.v(tag, "getSimpleDao");

		try {
			if (studentDao == null)
				studentDao = getDao(Student.class);
		} catch (SQLException e) {
			Log.v("ERROR", "getSimpleDao");
			e.printStackTrace();
		}
		return studentDao;
	}

	public Dao<Adres, Integer> getAdresDao() {
		Log.v(tag, "getAdresDao");

		try {
			if (adresDao == null) {
				adresDao = getDao(Adres.class);
			}
		} catch (SQLException e) {
			Log.v("Error", "getAdresDao");
			e.printStackTrace();
		}

		return adresDao;
	}

	public Dao<Phone, Integer> getPhoneDao() {

		try {
			if (phoneDao == null)
				phoneDao = getDao(Phone.class);
		} catch (SQLException e) {
			Log.v("Error", "getPhoneDao");
			e.printStackTrace();
		}
		return phoneDao;
	}
}
