package com.s2parking.type;

/**
 * Classe responsável controlar as enums da tabela park_ticket
 * @author desenv03
 *
 */
public class ParkHistorico {
	
	public static final String PARCELABLE_HISTORY = "history";
	
	public enum Status{
		ATIVO("Ativo"),
		CONSUMIDO("Consumido"),
		A_ATIVAR("A ativar"),
		; 
		
		private String description;
		
		private Status(String description){
			this.description = description;
		}
		public String getDescription(){
			return description;
		}
		
		/**
		 * Método para retornar a descrição do tipo de acordo com o seu nome
		 * @param name
		 * @return
		 */
		public static String getDescriptionByName(String name){
			for (Status type : Status.values()){
				if(type.name().equals(name)){
					return type.getDescription();
				}
			}
			return "";
		}
		
		@Override
		public String toString(){
			return description;
		}
	}// End Enum Status
}// End Class