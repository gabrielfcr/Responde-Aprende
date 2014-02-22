package org.pmm.respondeyaprende;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GuardarPreguntas extends Activity {

	private Spinner spinner;
	private EditText etPregunta, etRespuestaCorrecta, etRespuesta1,
			etRespuesta2, etRespuesta3;
	private Button btGuardar, btBorrar;

	private ArrayList<String> datos;
	private String categoria, pregunta, respuestaCorrecta, respuesta1,
			respuesta2, respuesta3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guardar_preguntas);

		
		spinner = (Spinner) findViewById(R.id.spinnerCategoria);
		etPregunta = (EditText) findViewById(R.id.edTPregunta);
		etRespuestaCorrecta = (EditText) findViewById(R.id.edTRespuesta);
		etRespuesta1 = (EditText) findViewById(R.id.edTRespuesta1);
		etRespuesta2 = (EditText) findViewById(R.id.edTRespuesta2);
		etRespuesta3 = (EditText) findViewById(R.id.edTRespuesta3);
		btGuardar = (Button) findViewById(R.id.btnGuardar);
		btBorrar = (Button) findViewById(R.id.btnBorrar);

		// BASE DE DATOS
		if (!comprobarBD()) {
			copiarBD();
		}
		datos = leerCategoriaBD();

		// SPINNER
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, datos);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent,
					android.view.View v, int position, long id) {
				Toast.makeText(getApplicationContext(),
						String.valueOf(datos.get(position)), Toast.LENGTH_LONG)
						.show();
				categoria = datos.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		// BOTONES
		btGuardar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pregunta = etPregunta.getText().toString();
				respuestaCorrecta = etRespuestaCorrecta.getText().toString().trim();
				respuesta1 = etRespuesta1.getText().toString().trim();
				respuesta2 = etRespuesta2.getText().toString().trim();
				respuesta3 = etRespuesta3.getText().toString().trim();
				if (pregunta.equals("") || respuestaCorrecta.equals("")
						|| respuesta1.equals("") || respuesta2.equals("")
						|| respuesta3.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Faltan campos por rrellenar", Toast.LENGTH_LONG)
							.show();
				} else {
					insertarDatos(categoria, pregunta, respuestaCorrecta, respuesta1, respuesta2, respuesta3);
					Toast.makeText(getApplicationContext(),
							"Todos los campos estan completos",
							Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});

	}

	// METODOS DE LA ACTIVIDAD

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guardar_preguntas, menu);
		return true;
	}

	// BASE DE DATOS
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

	// Busca las Categorias en la base de datos.
	public ArrayList<String> leerCategoriaBD() {
		ArrayList<String> lista = new ArrayList<String>();
		SQLiteDatabase db = this.openOrCreateDatabase("bdEstudiar.db",
				MODE_PRIVATE, null);
		Cursor c1 = db.rawQuery("select categoria from Preguntas", null);
		while (c1.moveToNext()) {
			if (!lista.contains(c1.getString(0))) {
				lista.add(c1.getString(0));
			}
		}
		c1.close();
		db.close();
		return lista;
	}

	// Metodo para comprobar si esta la base de datos "bdEstudiar.db"
	public boolean comprobarBD() {
		File fichero = getDatabasePath("bdEstudiar.db");
		if (fichero.isFile())
			return true;
		else
			return false;
	}

	// metodo para insertar datos en la base de datos
	public void insertarDatos(String categoria, String pregunta,
			String respuestaCorrecta, String respuesta1, String respuesta2,
			String respuesta3) {
		int numId = 0;
		;
		SQLiteDatabase db = this.openOrCreateDatabase("bdEstudiar.db",
				MODE_PRIVATE, null);
		Cursor c1 = db.rawQuery(
				"select id from Preguntas ORDER BY id DESC LIMIT 1", null);
		while (c1.moveToNext()) {
			numId = c1.getInt(0);
		}
		ContentValues values = new ContentValues();
		numId++;
		values.put("id", numId);
		values.put("categoria", categoria);
		values.put("pregunta", pregunta);
		Random r = new Random();
		int valorDado = r.nextInt(4);
		switch (valorDado) {
		case 0:
			values.put("respuesta1", respuestaCorrecta);
			values.put("respuesta2", respuesta1);
			values.put("respuesta3", respuesta2);
			values.put("respuesta4", respuesta3);
			values.put("respuestacorrecta", 1);
			values.put("ayuda", 4);
			break;
		case 1:
			values.put("respuesta1", respuesta1);
			values.put("respuesta2", respuestaCorrecta);
			values.put("respuesta3", respuesta3);
			values.put("respuesta4", respuesta2);
			values.put("respuestacorrecta", 2);
			values.put("ayuda", 3);
			break;
		case 2:
			values.put("respuesta1", respuesta2);
			values.put("respuesta2", respuesta3);
			values.put("respuesta3", respuestaCorrecta);
			values.put("respuesta4", respuesta1);
			values.put("respuestacorrecta", 3);
			values.put("ayuda", 2);
			break;
		case 4:
			values.put("respuesta1", respuesta2);
			values.put("respuesta2", respuesta3);
			values.put("respuesta3", respuesta1);
			values.put("respuesta4", respuestaCorrecta);
			values.put("respuestacorrecta", 4);
			values.put("ayuda", 1);
			break;
		}
		db.insert("Preguntas", null, values);
		c1.close();
		db.close();
	}

}
