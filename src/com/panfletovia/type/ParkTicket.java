package com.panfletovia.type;

/**
 * Classe responsável controlar as enums da tabela park_ticket
 * @author desenv03
 *
 */
public class ParkTicket {
	
	public enum Type{
		UTILIZACAO("Utilização"), 
		IRREGULARIDADE("Irregularidade");
		
		private String description;
		
		private Type(String description){
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
			for (Type type : Type.values()){
				if(type.name().equals(name)){
					return type.getDescription();
				}
			}
			return "";
		}
	}// End Enum Type
	
	public enum PaymentType{
		DINHEIRO("Dinheiro"),
		PRE("Pré"),
		POS("Pós"),
		DEBITO_AUTOMATICO("Débito Automático"),
		CPF_CNPJ("Cpf/Cnpj"),
		CARTAO("Cartão");
		
		private String description;
		
		private PaymentType(String description){
			this.description = description;
		}
		public String getDescription(){
			return description;
		}
		/**
		 * Método para retornar a descrição de um pagamento de acordo com o seu tipo
		 * @param name
		 * @return
		 */
		public static String getDescriptionByName(String name){
			for (PaymentType paymentType : PaymentType.values()){
				if(paymentType.name().equals(name)){
					return paymentType.getDescription();
				}
			}
			return "";
		}
	}// End Enum PaymentType
	
	public enum IrregularityType{
		NENHUM("Nenhum"),
		VENCIDO("Vencido"),
		FORA_DA_VAGA("Fora da Vaga"),
		TICKET_INCOMPATIVEL("Ticket Incompatível"),
		SEM_TICKET("Sem Ticket");
		
		private String description;
		
		private IrregularityType(String description){
			this.description = description;
		}
		public String getDescription(){
			return description;
		}
		/**
		 * Método para retornar a descrição do tipo de irregularidade de acordo com o seu nome
		 * @param name
		 * @return
		 */
		public static String getDescriptionByName(String name){
			for (IrregularityType irregularityType : IrregularityType.values()){
				if(irregularityType.name().equals(name)){
					return irregularityType.getDescription();
				}
			}
			return "";
		}
	}// End Enum IrregularityType
}// End Class