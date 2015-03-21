package com.panfletovia.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.panfletovia.bean.SpinnerItem;
import com.s2parking.activities.R;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem>{
	
	private List<SpinnerItem> listItems;
	private TextView id;
	private TextView description;
	
	public SpinnerAdapter(Context context, int resource, List<SpinnerItem> items) {
		super(context, resource, items);
		this.listItems = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return customSpinner(convertView, position);
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return customSpinner(convertView, position);
	}
	
	/**
	 * Passa os valores para os textviews.
	 * @param view
	 * @param position
	 * @return
	 */
	private View customSpinner(View view, int position) {
		if (view == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			view = vi.inflate(R.layout.spinner_item, null);
		}

		SpinnerItem item = listItems.get(position);

		if (item != null) {
			//Description
			description = (TextView) view.findViewById(R.id.spinner_item_description);
			description.setText(item.getDescription());
		}

		return view;
	}
}
