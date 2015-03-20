package com.s2parking.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.s2parking.annotation.Field;
import com.s2parking.bean.HistoryItem;
import com.s2parking.controller.HistoryController;
import com.s2parking.enums.Constants;
import com.s2parking.type.ParkHistorico;
import com.s2parking.utils.ConfigurationManager;
import com.s2parking.utils.Dialogs;
import com.s2parking.utils.Utils;

public class HistoryActivity extends Activity implements OnItemClickListener {
	
	@Field(id = R.id.history_list)
	private ListView list;
	HistoryController historyController;
	private Dialogs dialogs;
	private ConfigurationManager configuration = ConfigurationManager.get();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		try {
			Utils.initializeFields(this);
			Utils.setTitle(this, R.string.menu_title_history);
			dialogs = new Dialogs(this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		historyController = new HistoryController(this, list, dialogs);
		historyController.getHistory(list);
		list.setOnItemClickListener(this);
		changeCity();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		final HistoryItem historyItem = (HistoryItem) adapter.getItemAtPosition(position);
		Intent intent = new Intent(this, FullHistoryActivity.class);
		intent.putExtra(ParkHistorico.PARCELABLE_HISTORY, historyItem);
		startActivity(intent);
		finish();
	}
	
	/*
	 * Método Responsavél por trocar o imagem para o logo da operação escolhida
	 * e também alterar o nome da cidade
	 */
	public void changeCity() {
		ImageView img = (ImageView) findViewById(R.id.menu_header_logo);
		int logo = Integer.valueOf(configuration.getInt(Constants.PREFERENCES_LOCAL_LOGO));
		img.setImageResource(logo);
		
		TextView city = (TextView) findViewById(R.id.title_city);
		String city_preferences = String.valueOf(configuration.getString(Constants.PREFERENCES_LOCAL_TITLE)); 
		city.setText(city_preferences);
	}// End Method 'changeCity'
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}