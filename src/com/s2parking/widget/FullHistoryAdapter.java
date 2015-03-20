package com.s2parking.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.s2parking.activities.R;
import com.s2parking.bean.HistoryItem;
import com.s2parking.utils.Utils;

public class FullHistoryAdapter extends ArrayAdapter<HistoryItem>{
	
	private List<HistoryItem> listItems;
	
	public FullHistoryAdapter(Context context, int resource, List<HistoryItem> items) {
		super(context, resource, items);
		this.listItems = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.full_history_list_item, null);
		}

		HistoryItem item = listItems.get(position);

		if (item != null) {
			//Tipo Ticket
			TextView ticketType= (TextView) v.findViewById(R.id.full_history_item_type);
			ticketType.setText(Utils.humanize(item.getType()));
			//Tipo Irregularidade
			TextView irregularidadeType= (TextView) v.findViewById(R.id.full_history_item_type_irregularity);
			irregularidadeType.setText(item.getReasonIrregularity());
			//Placa
			TextView plate = (TextView) v.findViewById(R.id.full_history_item_plate);
			plate.setText(item.getPlate());
			//Data Inicial
			TextView initialDate = (TextView) v.findViewById(R.id.full_history_item_initial_date);
			initialDate.setText(item.getInitialDate());
			//Data Final
			TextView finalDate = (TextView) v.findViewById(R.id.full_history_item_final_date);
			finalDate.setText(item.getFinalDate());
			//Situação
			TextView situation = (TextView) v.findViewById(R.id.full_history_item_situation);
			situation.setText(Utils.humanize(item.getSituation()));
			//Forma de pagamento
			TextView paymentMethod = (TextView) v.findViewById(R.id.full_history_item_payment_method);
			paymentMethod.setText(item.getPaymentMethod());
			//Valor
			TextView value = (TextView) v.findViewById(R.id.full_history_item_value);
			value.setText(item.getValue());
			//Área
			TextView area = (TextView) v.findViewById(R.id.full_history_item_area);
			area.setText(item.getArea());
		}

		return v;

	}
}
