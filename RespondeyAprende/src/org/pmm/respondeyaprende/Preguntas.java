package org.pmm.respondeyaprende;

public class Preguntas {
	private String categoria;
	private String pregunta;
	private String[] respuestas;
	private int respuestaCorrecta;
	private int respuestaAyuda;

	public Preguntas(String categoria, String pregunta, String[] respuestas,
			int respuestaCorrecta, int respuestaAyuda) {
		this.categoria = categoria;
		this.pregunta = pregunta;
		this.respuestas = respuestas;
		this.respuestaCorrecta = respuestaCorrecta;
		this.respuestaAyuda = respuestaAyuda;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String[] getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(String[] respuestas) {
		this.respuestas = respuestas;
	}

	public int getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(int respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public int getRespuestaAyuda() {
		return respuestaAyuda;
	}

	public void setRespuestaAyuda(int respuestaAyuda) {
		this.respuestaAyuda = respuestaAyuda;
	}
}
