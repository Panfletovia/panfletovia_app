package com.panfletovia.activities;

import android.app.Activity;
import android.os.Bundle;

import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Toaster;

public class SuperActivity extends Activity {

	protected ConfigurationManager configuration;
	protected Toaster toaster;
	protected Dialogs dialogs;

	public ConfigurationManager getConfigurationManager() {
		if (configuration == null) {
			configuration = ConfigurationManager.get();
		}
		return configuration;
		
	}

	public Toaster getToaster() {
		if (toaster == null) {
			toaster = new Toaster(this);
		}
		return toaster;
	}

	public Dialogs getDialogs() {
		if (dialogs == null) {
			dialogs = new Dialogs(this);
		}
		return dialogs;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.configuration = getConfigurationManager();
		this.toaster = getToaster();
		this.dialogs = getDialogs();
	}
}