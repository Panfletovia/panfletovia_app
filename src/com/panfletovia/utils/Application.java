package com.panfletovia.utils;

import com.panfletovia.base.ConfigurationManager;


public class Application extends android.app.Application {

	private static Application instance = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		ConfigurationManager.initialize(this);
		instance = this;
	}

	/**
	 * Retorna a instancia da aplicacao
	 * @return
	 */
	public static Application get() {
		return instance;
	}
}
