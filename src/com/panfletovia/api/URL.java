package com.panfletovia.api;

public class URL {
	
	public static final String BASE = "http://192.168.1.4/panfletovia/api";
	
	public interface CONTROLLER {
		public static final String AUTHORIZATION = "authorization";
		public static final String CLIENT = "client";
	}
	
	public interface ACTION {
		public static final String REMOVE = "remove";
		public static final String DELETE = "delete";
	}
}
