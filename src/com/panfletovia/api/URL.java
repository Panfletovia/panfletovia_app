package com.panfletovia.api;

public class URL {
	
	public interface CONTROLLER {
		public static final String AUTHORIZATION = "authorization";
		
		
		public static final String PLATES_MOBILE = "plates_mobile";
		public static final String HISTORY_MOBILE = "history_mobile";
		public static final String PERIOD_PURCHASE_MOBILE = "period_purchase_mobile";
		public static final String SPOTS_MOBILE = "spots_mobile";
		public static final String TICKETS_MOBILE = "tickets_mobile";
	}
	
	public interface ACTION {
		public static final String REMOVE = "remove";
		public static final String DELETE = "delete";
	}
}
