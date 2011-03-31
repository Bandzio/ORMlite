package com.example.orm;

import java.sql.SQLException;
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
import com.j256.ormlite.dao.Dao;

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
				dodajDoBazy(imie, nazwisko, semestr, miasto, ulica, nrdomu);
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
				getAll();
			}
		});
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

				adresDao.refresh(s.getAdres());
				
				String miasto = s.getAdres().getMiasto();
				String ulica = s.getAdres().getUlica();
				String nr = s.getAdres().getNr();
				int id = s.getAdres().getId();

				StringBuilder adres = new StringBuilder();
				adres.append("id=").append(id).append(" miasto =").append(
						miasto).append(" ulic =").append(ulica).append(" nr=")
						.append(nr).append("\n");
				Log.v("Adres", adres.toString());
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

			Dao<Student, Integer> daoStudent = getHelper().getSimpleDao();
			Student student = new Student();
			student.setName(name);
			student.setSurname(surname);
			student.setSemestr(semestr);

			student.setAdres(adres);

			daoStudent.create(student);
		} catch (SQLException e) {
			Log.v("ERROR", "dodajDoBazy");
			e.printStackTrace();
		}
	}
}