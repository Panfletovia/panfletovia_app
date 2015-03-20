package com.s2parking.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MainItem implements Parcelable {
	
	private final int imageResource;
	private String description;
	private String subDescription;
	
	public MainItem(Parcel source) {
		imageResource = source.readInt();
		description = source.readString();
		subDescription = source.readString();
	}

	public MainItem(int imageResource, String description, String subDescription) {
		this.imageResource = imageResource;
		this.description = description;
		this.subDescription = subDescription;
	}

	public int getImageResource() {
		return imageResource;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getSubDescription() {
		return subDescription;
	}
	
	@Override
	public int describeContents() { 
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(description);
		dest.writeInt(imageResource);
	}
}
