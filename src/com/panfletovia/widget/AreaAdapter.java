package com.panfletovia.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.panfletovia.bean.AreaItem;
import com.s2parking.activities.R;

public class AreaAdapter extends ArrayAdapter<AreaItem> {
	
	private List<AreaItem> listItems;
	
	public AreaAdapter(Context context, int resource, List<AreaItem> items) {
		super(context, resource, items);
		this.listItems = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.item_dialog_area, null);
		}

		AreaItem item = listItems.get(position);

		if (item != null) {
			//Nome da área.
			TextView area = (TextView) v.findViewById(R.id.item_dialog_area_nome);
			area.setText(item.getName());
		}

		return v;

	}
}
