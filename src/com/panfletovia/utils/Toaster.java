package com.panfletovia.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Exibe toasts. Esta classe tem como principal finalidade diminuir o número de
 * caracteres para mostrar um toast. Também serve para verificar quais toasts
 * foram exibidos nos tests.
 * 
 * @author Davi Gabriel da Silva
 * 
 */
public class Toaster {

	// Contexto (activity)
	protected final Context context;

	/**
	 * Constrói o Toaster
	 * 
	 * @param context
	 */
	public Toaster(Context context) {
		this.context = context;
	}

	/**
	 * Exibe uma mensagem rápida (Toast.LENGTH_SHORT)
	 * 
	 * @param message
	 */
	public void fast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Exibe uma mensagem lenta (Toast.LENGTH_LONG)
	 * 
	 * @param message
	 */
	public void slow(String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * Exibe uma mensagem rápida (Toast.LENGTH_SHORT)
	 * 
	 * @param message
	 */
	public void fast(int resourceId) {
		Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Exibe uma mensagem lenta (Toast.LENGTH_LONG)
	 * 
	 * @param message
	 */
	public void slow(int resourceId) {
		Toast.makeText(context, resourceId, Toast.LENGTH_LONG).show();
	}

}
