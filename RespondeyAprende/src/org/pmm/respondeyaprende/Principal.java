package org.pmm.respondeyaprende;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Principal extends Activity {
	private Button btEmpezar, btGuardarPreguntas, btFinalizar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Pedimos al sistema de ventanas de Android que nos quite el nombre de
		// la aplicaci�n y que adem�s oculte la barra de notificaciones.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		btEmpezar = (Button) findViewById(R.id.empezar);
		btGuardarPreguntas = (Button)findViewById(R.id.btCargaListaPregunta);
		btFinalizar = (Button)findViewById(R.id.btSalir);
		btEmpezar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Empezar.class);
				startActivity(i);
			}
		});
		btGuardarPreguntas.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), GuardarPreguntas.class);
				startActivity(i);
			}
		});
		btFinalizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();	
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

}
