package com.panfletovia.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.panfletovia.bean.MainItem;
import com.s2parking.activities.R;

public class MainAdapter extends ArrayAdapter<MainItem> {
	
	private List<MainItem> listItems;
	
	public MainAdapter(Context context, int resource, List<MainItem> items) {
		super(context, resource, items);
		this.listItems = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.main_list_item, null);
		}

		MainItem item = listItems.get(position);

		if (item != null) {
			ImageView image = (ImageView) v.findViewById(R.id.main_item_image);
			image.setImageResource(item.getImageResource());
			
			TextView description = (TextView) v.findViewById(R.id.main_item_description);
			description.setText(item.getDescription());
			
			TextView subDescription = (TextView) v.findViewById(R.id.main_item_sub_description);
			subDescription.setText(item.getSubDescription());
		}

		return v;

	}
}
