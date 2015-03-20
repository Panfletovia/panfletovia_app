package com.s2parking.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PlateItem implements Parcelable {
	private int id;
	private String type;
	private String plate;	
	
	public PlateItem(int id, String type, String plate) {
		this.id = id;
		this.type = type;
		this.plate = plate;
	}
	
	public PlateItem(Parcel parcel) {
		id = parcel.readInt();
		type = parcel.readString();
		plate = parcel.readString();
	}
	
	public int getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}	
	
	public String getPlate() {
		return plate;
	}
			
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(type);		
		dest.writeString(plate);		
	}
	
	public static final Creator<PlateItem> CREATOR = new Parcelable.Creator<PlateItem>() {

		@Override
		public PlateItem createFromParcel(Parcel source) {
			return new PlateItem(source);
		}

		@Override
		public PlateItem[] newArray(int size) {
			return new PlateItem[size];
		}
	};
}
