package org.pmm.respondeyaprende;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Empezar extends Activity {
	// Variables primitivas
	private int numeroPregunta = 0, progress = 0;
	
	// Variables de Android
	private ProgressBar progressBar;
	private Button bt1, bt2, bt3, bt4;
	private TextView txtPregunta;
	private ArrayList<Preguntas> listaPreguntas = new ArrayList<Preguntas>();
	private UpdateProgress hilo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Pedimos al sistema de ventanas de Android que nos quite el nombre de
		// la aplicación y que además ocul	te la barra de notificaciones.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empezar);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		txtPregunta = (TextView) findViewById(R.id.tvPregunta);
		bt1 = (Button) findViewById(R.id.btRespuesta1);
		bt2 = (Button) findViewById(R.id.btRespuesta2);
		bt3 = (Button) findViewById(R.id.btRespuesta3);
		bt4 = (Button) findViewById(R.id.btRespuesta4);

		if (!comprobarBD()) {
			copiarBD();
		}

		listaPreguntas = leerBD();
		insertarPregunta();
		hilo = new UpdateProgress();
		hilo.execute();
			
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				respuesta(1);
			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				respuesta(2);
			}
		});
		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				respuesta(3);
			}
		});
		bt4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				respuesta(4);
			}
		});
	}

	// Metodo para comprobar si esta la base de datos "bdEstudiar.db"
	public boolean comprobarBD() {
		File fichero = getDatabasePath("bdEstudiar.db");
		if (fichero.isFile())
			return true;
		else
			return false;
	}

	// Metodo para insertar Preguntas en los botones
	public void insertarPregunta() {
		Preguntas preguntas = listaPreguntas.get(numeroPregunta);
		txtPregunta.setText(preguntas.getPregunta());
		txtPregunta.setTextColor(Color.WHITE);
		bt1.setText(preguntas.getRespuestas()[0]);
		bt2.setText(preguntas.getRespuestas()[1]);
		bt3.setText(preguntas.getRespuestas()[2]);
		bt4.setText(preguntas.getRespuestas()[3]);

		bt1.setEnabled(true);
		bt2.setEnabled(true);
		bt3.setEnabled(true);
		bt4.setEnabled(true);

		bt1.setTextColor(Color.BLACK);
		bt2.setTextColor(Color.BLACK);
		bt3.setTextColor(Color.BLACK);
		bt4.setTextColor(Color.BLACK);
	}

	// Metodo consultar las respuestas
	public void respuesta(int res) {
		Preguntas preguntas = listaPreguntas.get(numeroPregunta);
		int correcta = preguntas.getRespuestaCorrecta();
		Log.d("LISTA DE PREGUNTAS", preguntas.getPregunta());
		Log.d("RESPUESTA CORRECTA", String.valueOf(correcta));
		Log.d("VARIBLE RESPUESTA", String.valueOf(res));
		if (res == correcta) {
			switch (res) {
			case 1:
				bt1.setTextColor(Color.GREEN);
				break;
			case 2:
				bt2.setTextColor(Color.GREEN);
				break;
			case 3:
				bt3.setTextColor(Color.GREEN);
				break;
			case 4:
				bt4.setTextColor(Color.GREEN);
				break;
			}
			onCreateDialog(1).show();
		} else {
			switch (correcta) {
			case 1:
				bt1.setTextColor(Color.GREEN);
				break;
			case 2:
				bt2.setTextColor(Color.GREEN);
				break;
			case 3:
				bt3.setTextColor(Color.GREEN);
				break;
			case 4:
				bt4.setTextColor(Color.GREEN);
				break;
			}
			switch (res) {
			case 1:
				bt1.setTextColor(Color.RED);
				break;
			case 2:
				bt2.setTextColor(Color.RED);
				break;
			case 3:
				bt3.setTextColor(Color.RED);
				break;
			case 4:
				bt4.setTextColor(Color.RED);
				break;
			}
			onCreateDialog(2).show();
		}
		
	}

	// Metodo para mostrar el dialogo
	public Dialog onCreateDialog(int id) {
		AlertDialog dialogo;
		AlertDialog.Builder builder = new AlertDialog.Builder(Empezar.this);
//		hilo.cancel(true);
		hilo.cancel(true);
		switch (id) {
		case 1:
			builder.setTitle("Acertaste");
			builder.setMessage("¿Continuar?");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							numeroPregunta++;
							recorrerLista();
							if(numeroPregunta != listaPreguntas.size()){
								hilo = new UpdateProgress();
								hilo.execute();
							}
						}
					});
			break;
		case 2:
			builder.setTitle("Fallastes");
			builder.setMessage("¿Continuar?");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							numeroPregunta++;
							recorrerLista();
							if(numeroPregunta != listaPreguntas.size()){
								hilo = new UpdateProgress();
								hilo.execute();
							}
						}
					});
			break;
		case 3:
			builder.setTitle("Tiempo agotado");
			builder.setMessage("¿Continuar?");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							numeroPregunta++;
							recorrerLista();
							if(numeroPregunta != listaPreguntas.size()){
								hilo = new UpdateProgress();
								hilo.execute();
							}
						}
					});
			break;
		case 4:
			builder.setTitle("Ha finalizado la partida");
			builder.setMessage("Inserta mas preguntas y demuestra lo que vales.");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			break;

		}
		return dialogo = builder.create();

	}

	// Metodo para reccore la lista de preguntas
	public void recorrerLista() {
		if (numeroPregunta < listaPreguntas.size()) {
			insertarPregunta();
			listaPreguntas.get(numeroPregunta);
		} else {
			onCreateDialog(4).show();
		}
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

	public ArrayList<Preguntas> leerBD() {
		ArrayList<Preguntas> lista = new ArrayList<Preguntas>();
		SQLiteDatabase db = this.openOrCreateDatabase("bdEstudiar.db",
				MODE_PRIVATE, null);
		Cursor c1 = db
				.rawQuery(
						"select categoria, pregunta, respuesta1, respuesta2, respuesta3, respuesta4, respuestacorrecta, ayuda from Preguntas",
						null);
		while (c1.moveToNext()) {
			lista.add(new Preguntas(c1.getString(0), c1.getString(1),
					new String[] { c1.getString(2), c1.getString(3),
							c1.getString(4), c1.getString(5) }, c1.getInt(6),
					c1.getInt(7)));
		}
		c1.close();
		db.close();
		return lista;
	}
	//Hilo secundario para controlar el progressBar
    public class UpdateProgress extends AsyncTask<Void,Integer,Void>{
    	// Tiene que ser interna ya que necesito métodos de la clase Activity (herencia múltiple)
    	
    	@Override
    	protected void onCancelled() {
    		Toast.makeText(Empezar.this, "Tarea cancelada!", Toast.LENGTH_SHORT);
    	}

		@Override
		protected void onPostExecute(Void result) {
			// Una vez que el proceso haya terminado, el botón podrá 
			// ser presionado nuevamente después de cambiar su atributo clickable.
			onCreateDialog(3).show();
			Toast.makeText(Empezar.this, "Tarea terminada!", Toast.LENGTH_SHORT);
			
		}

		@Override
		protected void onPreExecute() {
			// Definimos el progreso de la barra en 0 para empezar.
			progress=0;
			
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// Actualiza la ProgressBar
			// Se invoca una vez después de una llamada a publishProgress(progress)
			progressBar.setProgress(values[0]);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			/*
			 *  En este método definimos la forma en la que estará avanzando la ProgressBar,
			 *  para lo cual nos auxiliamos de la clase SystemClock, y para cada vez que 
			 *  nuestra variable progress aumente se actualice su avance en el UI thread.
			 */
			while(progress<100){
				progress++;
				publishProgress(progress);
				// ahora pasa a realizar el onProgressUpdate
				SystemClock.sleep(100); // duerme el proceso 100 milisegundos
				if(isCancelled()){
					break;
				}
			}
			return null;
		}	
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.empezar, menu);
		return true;
	}

	@Override
	protected void onPause() {
		hilo.cancel(true);
		super.onPause();
	}
	
}
