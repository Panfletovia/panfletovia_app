package com.panfletovia.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryItem implements Parcelable {
	
	private String plate;
	private String situation;
	private String value;
	private String createIn;
	private String paidIn;
	private String type;
	private String initialDate;
	private String finalDate;
	private String area;
	private String periods;
	private String spot;
	private String notificationDueDate;
	private String reasonIrregularity;
	private String paymentMethod;
	private String interrupted;
	private String status;
	private int ticketId;
	
	public HistoryItem() {

	}
	
	public HistoryItem(Parcel parcel) {
		plate = parcel.readString();
		situation = parcel.readString();
		value = parcel.readString();
		createIn = parcel.readString();
		paidIn = parcel.readString();
		type = parcel.readString();
		initialDate = parcel.readString();
		finalDate = parcel.readString();
		area = parcel.readString();
		periods = parcel.readString();
		spot = parcel.readString();
		notificationDueDate = parcel.readString();
		reasonIrregularity = parcel.readString();
		paymentMethod = parcel.readString();
		interrupted = parcel.readString();
		status = parcel.readString();
		ticketId = parcel.readInt();
	}
	
	public String getPlate() {
		return plate;
	}
	
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	public String getSituation() {
		return situation;
	}
	
	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getCreateIn() {
		return createIn;
	}
	
	public void setCreateIn(String createIn) {
		this.createIn = createIn;
	}
	
	public String getPaidIn() {
		return paidIn;
	}
	
	public void setPaidIn(String paidIn) {
		this.paidIn = paidIn;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getInitialDate() {
		return initialDate;
	}
	
	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}
	
	public String getFinalDate() {
		return finalDate;
	}
	
	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getPeriods() {
		return periods;
	}
	
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	
	public String getSpot() {
		return spot;
	}
	
	public void setSpot(String spot) {
		this.spot = spot;
	}
	
	public String getNotificationDueDate() {
		return notificationDueDate;
	}
	
	public void setNotificationDueDate(String notificationDueDate) {
		this.notificationDueDate = notificationDueDate;
	}
	
	public String getReasonIrregularity() {
		return reasonIrregularity;
	}
	
	public void setReasonIrregularity(String reasonIrregularity) {
		this.reasonIrregularity = reasonIrregularity;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public String getInterrupted() {
		return interrupted;
	}
	
	public void setInterrupted(String interrupted) {
		this.interrupted = interrupted;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}	
	
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	public int getTicketId() {
		return ticketId;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(plate);
		dest.writeString(situation);
		dest.writeString(value);
		dest.writeString(createIn);
		dest.writeString(paidIn);
		dest.writeString(type);
		dest.writeString(initialDate);
		dest.writeString(finalDate);
		dest.writeString(area);
		dest.writeString(periods);
		dest.writeString(spot);
		dest.writeString(notificationDueDate);
		dest.writeString(reasonIrregularity);
		dest.writeString(paymentMethod);
		dest.writeString(interrupted);
		dest.writeString(status);
		dest.writeInt(ticketId);
	}
	
	public static final Creator<HistoryItem> CREATOR = new Parcelable.Creator<HistoryItem>() {

		@Override
		public HistoryItem createFromParcel(Parcel source) {
			return new HistoryItem(source);
		}

		@Override
		public HistoryItem[] newArray(int size) {
			return new HistoryItem[size];
		}
	};
}
