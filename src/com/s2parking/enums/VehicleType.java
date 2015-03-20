package com.s2parking.enums;

public enum VehicleType {
	
	CARRO, MOTO;
	
	@Override
	public String toString(){
		return this.name();
	}

}
