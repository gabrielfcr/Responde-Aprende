package org.pmm.respondeyaprende;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Empezar extends Activity {
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empezar);
		progressBar = (ProgressBar)findViewById(R.id.progressBar1);
		if (!comprobarBD()) {
			copiarBD();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.empezar, menu);
		return true;
	}
	// Metodo para comprobar si esta la base de datos "bdEstudiar.db"
	public boolean comprobarBD() {
		File fichero = getDatabasePath("bdEstudiar.db");
		if (fichero.isFile())
			return true;
		else
			return false;
	}
	// Metodo para copiar la base de datos de raw al dispositivo interno
	public void copiarBD() {
		SQLiteDatabase db = this.openOrCreateDatabase("bdEstudiar.db",
				MODE_PRIVATE, null);
		InputStream in = getResources().openRawResource(R.raw.estudiar);
		try {
			FileOutputStream out = new FileOutputStream(db.getPath());
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			Toast.makeText(
					getApplicationContext(),
					"No se puede copiar la base de datos al dispositivo interno",
					Toast.LENGTH_LONG).show();
		}
		db.close();

	}
	public class Hilo extends AsyncTask<Void, Integer, Void>{
		
		

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}

}
