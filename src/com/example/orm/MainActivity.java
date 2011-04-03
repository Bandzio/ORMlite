package com.example.orm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager.SqliteOpenHelperFactory;
import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectIterator;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.query.In;

public class MainActivity extends OrmLiteBaseActivity<DataBaseORMAdapter> {

	private static final String tag = MainActivity.class.getSimpleName();
	private Button mDodaj, mUsun, mGetAll;
	private EditText mImie, mNazwisko, mSemestr, mUlica, mMiasto, mNrDomu;

	// factory for helper
	static {
		OpenHelperManager.setOpenHelperFactory(new SqliteOpenHelperFactory() {

			@Override
			public OrmLiteSqliteOpenHelper getHelper(Context context) {
				Log.v("static", "getHelper");
				return new DataBaseORMAdapter(context);
			}
		});
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mImie = (EditText) findViewById(R.id.editImie);
		mNazwisko = (EditText) findViewById(R.id.editNazwisko);
		mSemestr = (EditText) findViewById(R.id.editSemestr);
		mUlica = (EditText) findViewById(R.id.editulica);
		mMiasto = (EditText) findViewById(R.id.editmiasto);
		mNrDomu = (EditText) findViewById(R.id.editnrDomu);

		mDodaj = (Button) findViewById(R.id.btnSave);
		mDodaj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String imie = mImie.getText().toString();
				String nazwisko = mNazwisko.getText().toString();
				String semestr = mSemestr.getText().toString();
				String miasto = mMiasto.getText().toString();
				String ulica = mUlica.getText().toString();
				String nrdomu = mNrDomu.getText().toString();
				// dodajDoBazy(imie, nazwisko, semestr, miasto, ulica, nrdomu);
				try {
					dodajDoBazyCollection();
				} catch (SQLException e) {
					Log.v("EEEERRRROOOORRR", "");
					e.printStackTrace();
				}

			}
		});

		mUsun = (Button) findViewById(R.id.btnDelete);
		mUsun.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				usun();
			}
		});

		mGetAll = (Button) findViewById(R.id.btnGetAll);
		mGetAll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// getAll();
				try {
					czytajZBazy();
				} catch (SQLException e) {
					Log.v("ERROR", "EROR:");
					e.printStackTrace();
				}
			}
		});
	}

	protected void czytajZBazy() throws SQLException {
		/*
		 * wyswietl numery telefonu dla studenta MArcin w Rybniku
		 * 
		 * tel 111111 i 222222
		 */

		Log.v("czytam z bazy", "");

		Dao<Student, Integer> simpleDao = getHelper().getSimpleDao();
		Dao<Adres, Integer> adresDao = getHelper().getAdresDao();
		Dao<Phone, Integer> phoneDao = getHelper().getPhoneDao();

		// wypisz studenta o imieniu MArcin

		/**
		 * QueryBuilder<Account, String> qb = accountDao.queryBuilder(); ...
		 * custom query builder methods SelectIterator<Account> iterator =
		 * partialDao.iterator(qb.prepare()); try { while (iterator.hasNext()) {
		 * Account account = iterator.next(); ... } } finish { iterator.close();
		 * }
		 */

//		QueryBuilder<Student, Integer> qbStudent = simpleDao.queryBuilder();;
//		Where<Student, Integer> where = qbStudent.where();
//		where.eq("name", "Marcin");
//		PreparedQuery<Student> prepare = qbStudent.prepare();
//		CloseableIterator<Student> iterator = simpleDao.iterator(prepare);
//		while(iterator.hasNext()){
//			Log.v("inside ", "loop");
//			Student next = iterator.next();
//			Log.v("sudent", ""+next.getId()+" "+next.getName()+" "+next.getSurname());
//		}
//		iterator.close();
		
		
		/**
		 * wyswietl adresy dla studenta Marcin
		 */
		QueryBuilder<Student, Integer> qbStudent = simpleDao.queryBuilder();;
		Where<Student, Integer> where = qbStudent.where();
		where.eq("name", "Marcin");
		PreparedQuery<Student> prepare = qbStudent.prepare();
		CloseableIterator<Student> iterator = simpleDao.iterator(prepare);
		while(iterator.hasNext()){
			Log.v("inside ", "loop");
			Student next = iterator.next();
			Log.v("sudent", ""+next.getId()+" "+next.getName()+" "+next.getSurname());
			
			//wypisz adresy 

			QueryBuilder<Adres, Integer> adresQB = adresDao.queryBuilder();
			Where<Adres, Integer> where2 = adresQB.where();
			
			where2.eq("student_id", next.getId());
			
			PreparedQuery<Adres> preparedAdres = adresQB.prepare();
			CloseableIterator<Adres> iterator2 = adresDao.iterator(preparedAdres);
			
			while(iterator2.hasNext()){
				Log.v("iterator po adresie", "!!");
				Adres nextAdres = iterator2.next();
				Log.v("Adres", ""+nextAdres.getId()+" "+nextAdres.getMiasto());
				
				
				//wypisz telefony 
				
				QueryBuilder<Phone, Integer> queryPhone = phoneDao.queryBuilder();
				Where<Phone, Integer> where3 = queryPhone.where();
				where3.eq("adres_id", nextAdres.getId());
				
				
				PreparedQuery<Phone> preparedPhone = queryPhone.prepare();
				CloseableIterator<Phone> iterator3 = phoneDao.iterator(preparedPhone);
				
				while(iterator3.hasNext()){
					Log.v("inside phone iterator", "");
					Phone next2 = iterator3.next();
					Log.v("phone", ""+next2.getId()+" "+next2.getNumber());
				}
				iterator3.close();
			}
			
			iterator2.close();
		}
		iterator.close();
	
	}
	

	/*
	 * Metoda dodaje do bazy przykladowe koleckje
	 */
	protected void dodajDoBazyCollection() throws SQLException {
		Dao<Student, Integer> simpleDao = getHelper().getSimpleDao();
		Dao<Adres, Integer> adresDao = getHelper().getAdresDao();
		Dao<Phone, Integer> phoneDao = getHelper().getPhoneDao();
		/*
		 * Student Marcin - Marcin Banaszynski
		 */
		Student studentMarcin = new Student("Marcin", "Banaszynski", "X");
		simpleDao.create(studentMarcin);
		/*
		 * Nowy obcy student
		 */
		Student studentXXX = new Student("XXX", "YUYYY", "1");
		simpleDao.create(studentXXX);

		// tworzymy dwa rozne adresy
		Adres adresRybnik = new Adres(studentMarcin, "Rybnik",
				"Kusocisnkiegpo", "8");
		Adres adresGliwice = new Adres(studentMarcin, "Gliwice", "Arkonska",
				"4/4");
		Adres adresXXX = new Adres(studentXXX, "W-wa", "Nieznana", "32");

		adresDao.create(adresRybnik);
		adresDao.create(adresGliwice);
		adresDao.create(adresXXX);

		Phone phoneRybnik1 = new Phone(adresRybnik, "111111");
		Phone phoneRybnik2 = new Phone(adresRybnik, "222222");
		Phone phoneXXX1 = new Phone(adresXXX, "234234324");

		phoneDao.create(phoneRybnik1);
		phoneDao.create(phoneRybnik2);
		phoneDao.create(phoneXXX1);

		/*
		 * check is it ok - student MArcin ma miec 2 adresy, rybnik i gliwice
		 */
		Student queryForStudentMarcin = simpleDao.queryForId(studentMarcin
				.getId());
		Collection<Adres> adres = queryForStudentMarcin.getAdres();
		Log.v("adres size", "" + adres.size());
		Log.v("przed foreach", "!!!!!");

		CloseableIterator<Student> iterator = simpleDao.iterator();
		Student next = iterator.next();
		Log.v("student", "" + next.getName());
		Adres queryForId = adresDao.queryForId(next.getId());
		Log.v("adres", queryForId.getMiasto());
		Phone queryForId2 = phoneDao.queryForId(queryForId.getId());
		Log.v("phone", "" + queryForId2.getNumber());

		//
		// CloseableIterator<Adres> iterator = (CloseableIterator<Adres>)
		// adres.iterator();
		//	
		// while(iterator.hasNext()){
		// Log.v("iteraotr has next", "");
		// Adres next = iterator.next();
		// Log.v("iterator miasto", next.getMiasto());
		// }
		// 		
		//
		// for (Adres a : adres) {
		// Log.v("w for each", "!!!");
		// int id = a.getId();
		// String miasto = a.getMiasto();
		// String ulica = a.getUlica();
		// String nr = a.getNr();
		// Log.v("queryForStudentMarcin.getName()","id :"+id+" "+
		// miasto+" "+ulica+" "+nr+"\n");
		//			
		//			
		// Collection<Phone> phone = a.getPhone();
		// Log.v("phone ", "phones :"+phone.size());
		// for (Phone p : phone) {
		// int id2 = p.getId();
		// String number = p.getNumber();
		// Log.v(""+id2, number);
		// }
		// }
	}

	protected void usun() {
		Log.v(tag, "usun");
		Dao<Student, Integer> simpleDao = getHelper().getSimpleDao();
		try {

			List<Student> datas = simpleDao.queryForAll();
			simpleDao.delete(datas);

		} catch (SQLException e) {
			Log.v("ERROR", "Delete");
			e.printStackTrace();
		}
	}

	protected void getAll() {
		Log.v(tag, "getAll");
		Dao<Student, Integer> daoStudent = getHelper().getSimpleDao();
		Dao<Adres, Integer> adresDao = getHelper().getAdresDao();
		try {
			List<Student> all = daoStudent.queryForAll();
			Log.v("size ", "" + all.size());
			for (Student s : all) {
				StringBuilder sb = new StringBuilder();
				sb.append("id =").append(s.getId()).append(" imie=").append(
						s.getName()).append(" nazwisko =").append(
						s.getSurname()).append(" sem. =")
						.append(s.getSemestr()).append("\n");
				Log.v("student", sb.toString());

				// adresDao.refresh(s.getAdres());
				//				
				// String miasto = s.getAdres().getMiasto();
				// String ulica = s.getAdres().getUlica();
				// String nr = s.getAdres().getNr();
				// //int id = s.getAdres().getId();
				// int id = 999;
				// StringBuilder adres = new StringBuilder();
				// adres.append("id=").append(id).append(" miasto =").append(
				// miasto).append(" ulic =").append(ulica).append(" nr=")
				// .append(nr).append("\n");
				// Log.v("Adres", adres.toString());
			}

		} catch (SQLException e) {
			Log.v("ERROR", "getALL");
			e.printStackTrace();
		}
	}

	protected void dodajDoBazy(String name, String surname, String semestr,
			String miasto, String ulica, String nrdomu) {
		Log.v(tag, "dodajDoBazy");
		try {
			Dao<Adres, Integer> adresDao = getHelper().getAdresDao();

			Adres adres = new Adres();
			adres.setMiasto(miasto);
			adres.setUlica(ulica);
			adres.setNr(nrdomu);

			adresDao.create(adres);

			Adres adres2 = new Adres();
			adres2.setMiasto(miasto + "xxx");
			adres2.setUlica(ulica + "xxx");
			adres2.setNr(nrdomu + "xxx");
			adresDao.create(adres2);

			Dao<Student, Integer> daoStudent = getHelper().getSimpleDao();
			Student student = new Student();
			student.setName(name);
			student.setSurname(surname);
			student.setSemestr(semestr);

			Collection<Adres> adresses = new ArrayList<Adres>();
			adresses.add(adres);
			adresses.add(adres2);

			student.setAdres(adresses);

			daoStudent.create(student);
		} catch (SQLException e) {
			Log.v("ERROR", "dodajDoBazy");
			e.printStackTrace();
		}
	}
}