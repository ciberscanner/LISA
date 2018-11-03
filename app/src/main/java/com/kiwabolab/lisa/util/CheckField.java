package com.kiwabolab.lisa.util;

import android.util.Log;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** Clase para la comprobacion de campos
 *
 * */

public class CheckField {
	// -----------------------------------------------------------------------------------
	//variables
	private String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";
	private String emailPattern2 = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+.+[a-zA-Z]+";

	private String fecha = "[0-9]+[0-9]+[0-9]";

	//----------------------------------------------------------------------------------------------
	//
	public  int isDateValid(String date){
		int resp=0;
		String DATE_FORMAT = "";

		if(date.contains("-")){
			DATE_FORMAT = "dd-MM-yyyy";
			resp=1;
		}else if(date.contains("/")){
			DATE_FORMAT = "dd/MM/yyyy";
			resp=2;
		}else{
			return -9;
		}


		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(date);
		} catch (ParseException e) {
			resp=-1;
		}
		return resp;
	}

	// -----------------------------------------------------------------------------------
	//revisa si un texto tiene espacios
	public boolean whitSpace(String text) {
		if (text.contains(" ")) {
			return true;

		} else {
			return false;
		}
	}

	// -----------------------------------------------------------------------------------
	//retorna la fecha actual
	private String fechaActual() {
		String fecha = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
				.format(Calendar.getInstance().getTime());

		Log.v("Fecha sal:", fecha);
		return fecha;
	}

	// -----------------------------------------------------------------------------------
	//valida si un correo es correcto
	public boolean emailValidateEditText(EditText edit) {
		String email = edit.getText().toString().trim();

		if (email.matches(emailPattern) && email.length() > 0) {
			return true;
		} else if (email.matches(emailPattern2) && email.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// -----------------------------------------------------------------------------------
	//revisa si un editext no sobrepasa el tamaño entregado
	public boolean sizeMaxEditText(EditText edit, int size) {
		if (edit.getText().toString().trim().length() < size) {
			return true;
		} else {
			return false;
		}
	}

	//----------------------------------------------------------------------------------------------
	//revisa si un string no sobrepasa el tamaño ingresado
	public boolean sizeMaxString(String edit, int size) {
		if (edit.length() < size) {
			return true;
		} else {
			return false;
		}
	}

	// -----------------------------------------------------------------------------------
	//revisa si un edittext es mayor al minimo ingresado
	public boolean sizeMinEditText(EditText edit, int size) {
		if (edit.getText().toString().trim().length() > size) {
			return true;
		} else {
			return false;
		}
	}

	//----------------------------------------------------------------------------------------------
	//revisa si un string es mayor al minimo ingresado
	public boolean sizeMinString(String edit, int size) {
		if (edit.length() > size) {
			return true;
		} else {
			return false;
		}
	}
	// -----------------------------------------------------------------------------------
	//verifica si un string es un numero valido
	public boolean isNumeric(String text){
		try{
			float num = Float.parseFloat(text);
			if(num>0){
				return true;
			}
			else {
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

	// -----------------------------------------------------------------------------------
	//verifica que dos editext contengan la misma cadena de caracteres
	public boolean equalPassword(EditText a, EditText b) {
		if (a.getText().toString().trim().equals(b.getText().toString().trim())) {
			return true;
		} else {
			return false;
		}
	}
}
