package com.panfletovia.enums;

public enum AppMenu {
	
	MENU_RECHARGE(-1),
	MENU_PARK(0),
	MENU_HISTORY(1),
	MENU_FIND_SPOT(2),
	MENU_PLATES(3),
	MENU_LOGOUT(4)
	;

	private int id;
	public int getId(){
		return id;
	}

	private AppMenu(int id){
		this.id = id;
	}
}// End Class