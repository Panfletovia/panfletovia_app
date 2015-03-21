package com.panfletovia.widget;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.panfletovia.bean.HistoryItem;
import com.s2parking.activities.R;

public class HistoryAdapter extends ArrayAdapter<HistoryItem>{
	
	private List<HistoryItem> listItems;
	private Activity activity;
	
	public HistoryAdapter(Context context, int resource, List<HistoryItem> items) {
		super(context, resource, items);
		this.listItems = items;
		activity = (Activity) context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.history_list_item, null);
		}

		HistoryItem item = listItems.get(position);

		if (item != null) {
			TextView placa = (TextView) v.findViewById(R.id.history_item_placa);
			placa.setText(item.getPlate());
			
			TextView dataInicio = (TextView) v.findViewById(R.id.history_item_data_inicio);
			dataInicio.setText(item.getInitialDate());
			
			TextView dataFim = (TextView) v.findViewById(R.id.history_item_data_fim);
			dataFim.setText(item.getFinalDate());
			//Se o status estiver ativo, então mostra o progress.
//			if (item.getStatus().equals("Ativo")) {
//				ProgressBar progress = (ProgressBar) v.findViewById(R.id.history_item_progress);
//				progress.setVisibility(View.VISIBLE);
//			}
			TextView status = (TextView) v.findViewById(R.id.history_item_status);
			status.setText(item.getStatus());
			
			TextView ticketType = (TextView) v.findViewById(R.id.history_item_ticket_type);
			ticketType.setText(item.getType());
			
			TextView valor = (TextView) v.findViewById(R.id.history_item_valor);
			valor.setText(item.getValue());
		}

		return v;

	}
}
