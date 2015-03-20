package com.s2parking.utils;

import java.text.NumberFormat;
import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mask {
	
	public static String unMask(String s) {
		return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", "");
	}

	/**
	 *  Adiciona máscara de valor monetário no EditText passado de parâmetro
	 * 
	 * @param editText
	 * @return
	 */
	@SuppressLint("NewApi")
	public static TextWatcher money(final EditText editText) {

		TextWatcher tw = new TextWatcher() {
			private String current = "";

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!s.toString().equals(current)) {
					editText.removeTextChangedListener(this);

					String cleanString = s.toString().replaceAll("\\D", "");

					double parsed = cleanString.isEmpty() ? 0 : Double.parseDouble(cleanString);
					String formated = NumberFormat.getCurrencyInstance().format((parsed / 100));

					current = formated;
					editText.setText(formated);
					editText.setSelection(formated.length());

					editText.addTextChangedListener(this);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		};
		editText.addTextChangedListener(tw);
		return tw;
	}

	/**
	 * Permite adicionar uma máscara em um EditText.
	 * 
	 * @param mask
	 * @param ediTxt
	 * @return
	 */
	public static TextWatcher insert(final String mask, final EditText ediTxt) {
		// Retorna um objeto text watcher
		return new TextWatcher() {
			// Inicializa variaveis do método
			boolean isUpdating;
			String old = "";

			// Listener on change
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Remove a máscara da string
				String str = Mask.unMask(s.toString());
				// Inicializa a máscara
				String mascara = "";
				// Se for uma edição
				if (isUpdating) {
					// Guarda a string anterior
					old = str;
					isUpdating = false;
					return;
				}
				int i = 0;
				// Percorre cada caractere da máscara
				for (char m : mask.toCharArray()) {
					// Se o caractere não for # e a string atual for maior que a antiga
					if (m != '#' && str.length() > old.length()) {
						mascara += m;
						continue;
					}
					try {
						mascara += str.charAt(i);
					} catch (Exception e) {
						break;
					}
					i++;
				}
				isUpdating = true;
				ediTxt.setText(mascara);
				ediTxt.setSelection(mascara.length());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		};
	}
	
	/**
	 * Adiciona uma máscara de telefone ao input
	 * 
	 * @param mask
	 * @param ediTxt
	 * @return
	 */
	public static TextWatcher phoneMask(final EditText ediTxt) {
		
		return new TextWatcher() {
			
			boolean isUpdating;
			String old = "";

			// Listener on change
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Remove a máscara da string
				String str = Mask.unMask(s.toString());
				// Inicializa a variável que terá o texto formatado
				String textoFormatado = "";
				// Se for uma edição
				if (isUpdating) {
					// Guarda a string anterior
					old = str;
					isUpdating = false;
					return;
				}
				int i = 0;
				String mask = "";
				// Ajusta a máscara conforme o número de caracteres
				if (str.length() < 11 ) {
					mask = "(##)####-####"; 
				} else {
					mask = "(##)#####-####";
				}
				// Percorre cada caractere da máscara
				for (char m : mask.toCharArray()) {
					// Se o caractere não for # e a string atual for maior que a antiga
					if (m != '#' && str.length() != old.length()) {
						textoFormatado += m;
						continue;
					}
					if (i != str.length()) {
						textoFormatado += str.charAt(i);
					} else {
						break;
					}
					i++;
				}
				isUpdating = true;
				
				ediTxt.setText(textoFormatado);
				ediTxt.setSelection(textoFormatado.length());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		};
	}

	/**
	 * Adiciona uma máscara de placa no formato brasileiro.
	 * 
	 * @param mask
	 * @param ediTxt
	 * @return
	 */
	public static TextWatcher insertPlate(final EditText editText) {
		// Inicia a activity com o teclado no modo para inserção de letras
		editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		
		return new TextWatcher() {
			// Armazena o caracter deletado
			char deletedChar;
			// Informa se há ou não o caractere hífen no EditText
			boolean hyphen = false;
			//Informa a quantidade de letras que há no EditText
			int totalLetters = 0;
			//Informa a quantidade de números que há no EditText
			int totalNumbers = 0;
			// Armazena a posição no EditText do último caractere inserido ou removido
			int start = 0;
			// Indica se algum caractere foi removido do EditText
			boolean before = false;
			// Indica se algum caractere foi inserido no EditText	
			boolean count = false;
			// Se o conteúdo de um EditText associado a um TextWatcher for alterado por um dos métodos do TextWatcher (através do método setText, por exemplo), os métodos do TextWatcher serão executados novamente. A flag isUpdating é usada para evitar o processamento de alterações que não foram feitas pelo usuário.
			boolean isUpdating = false;			

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				// Se a mudança no EditText foi efetuada pelo usuário, e não pelo método afterTextChange
				if (!isUpdating) {
					// Obtém a localização no EditText do caractere inserido ou removido				
					this.start = start;		

					// Obtém a indicação de que a alteração no EditText foi de inserção ou remoção de um caractere.
					// O correto seria testar "se before é igual a 1 e count é igual a 0", mas devido a possibilidade de se usar o aplicativo SwiftKey, o teste para verificar se um caractere foi apagado foi alterado para "se before é maior ou igual a 1 e count é igual a 0". Isso porque quando se usa esse aplicativo e mantém-se pressionado o botão backspace os métodos do TextWatcher são executados somente após a deleção de todos os números e do hífen, resultando em um valor para before igual ao total de caracteres deletados. Já com o teclado da Samsung, mesmo segurando-se o botão backspace, os métodos do TextWatcher são executados após a deleção de cada caractere.
					
					// Se um caractere foi apagado do EditText
					if (before == 1 && count == 0) { 
						this.before = true;
						this.count = false;
						
					// Se um caractere foi inserido no EditText
					} else if (before == 0 && count == 1) {
						this.before = false;
						this.count = true;
						
					// Se a placa foi recebida como extra de uma activity	
					} else if (start == 0 && before == 0 && count == 8) {
							totalLetters = 3;
							totalNumbers = 4;
							hyphen = true;
							isUpdating = true;	
							editText.setSelection(8); // Reposiciona o cursor
							editText.setInputType(InputType.TYPE_CLASS_NUMBER); // Altera o teclado para inserção de números                                                                                               
						
					// Se o usuário colou uma sequência de caracteres da área de transferência (nada será feito)	
					} else {						
						this.before = false;
						this.count = false;
					}
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// Se um caractere foi deletado
				if (count == 1) {
					deletedChar = s.charAt(start);
				}
			}

			/**
			 * Valida a máscara da placa após cada alteração no EditText.
			 * 
			 * Obs.: a validação foi implementada originalmente no método onTextChanged e foi refatorada no método afterTextChanged, conforme recomendação da documentação do objeto TextWatcher.
			 */
			public void afterTextChanged(Editable s) {	
				// Obtem a string da placa digitada até o momento
				StringBuilder plate = new StringBuilder(s.toString().toUpperCase());
				// Obtém o tamanho da string da placa digitada até o momento				
				int sLength = plate.length();
				 
				// Se o EditText foi alterado pelo método afterTextChanged e não pelo usuário
				if(isUpdating) {
					isUpdating = false;
					return;	// Retorna sem processar a alteração				
				}
				
				// Remove a mensagem de erro do EditText, se houver
				editText.setError(null);
				
				// Se o último caractere restante foi apagado ou se o EditText foi atualizado para "" (string sem caracteres) por outro método (a desmarcação do checkbox Placa Estrangeira, por exemplo)  
				if (sLength == 0) {
					hyphen = false; // Informa que o hífen não está presente no EditText
					totalLetters = 0;
					totalNumbers = 0;
					editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS); // Altera o teclado para inserção de letras
					
				// Se um caractere foi inserido
				} else if (count) {
					// Obtém o caractere digitado pelo usuário
					char lastChar = plate.charAt(start);
									
					// Se o usuário digitou algum caractere na posição de uma das três letras da placa
					if (start <= 2) {
						
						// Se o caractere digitado for uma letra						
						if (Character.isLetter(lastChar)) {
							
							//Se já haviam 3 letras no EditText
							if (totalLetters == 3) {								
								plate.deleteCharAt(3); // Apaga a última das três letras que existiam no EditText
								isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
								editText.setTextKeepState(plate); // Atualiza o EditText com a nova letra
								
								// Se a letra foi inserida antes do hífen
								if (start == 2) {
									editText.setInputType(InputType.TYPE_CLASS_NUMBER); // Altera o teclado para inserção de númeroslooooooool
									editText.setSelection(4); // Reposiciona o cursor depois do hífen
									
								} else {
									editText.setSelection(start + 1); // Reposiciona o cursor para depois da letra inserida
								}
										

							// Se não haviam 3 letras no EditText
							} else {
								
								// Se há números e apenas uma letra no EditText, o usuário pode estar inserindo uma letra após um número (por exemplo, E1A234, onde A foi a letra inserida)
								if (totalNumbers != 0) {									
									if (totalLetters == 1) {
										
										// Se o caractere anterior a letra é um número
										if (Character.isDigit(plate.charAt(start - 1))) {
											
											// Reposiciona o número para depois da letra digitada
											char number = plate.charAt(start - 1);
											plate.deleteCharAt(start - 1);											
											plate.insert(start, number);

											isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada	
											editText.setTextKeepState(plate);
											
											editText.setSelection(2); // Reposiciona o cursor
										}
									}
									
								} else {
									// As duas linhas a seguir são necessárias apenas se o teclado utilizado for o SwiftKey (o InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS não é respeitado por esse aplicativo)
									isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada	
									editText.setTextKeepState(plate);								
								}
								
								//Registra a inserção de mais uma letra
								totalLetters++;	
							}
							
							// Se agora há 3 letras no EditText
							if (totalLetters == 3 && !hyphen) {
								plate.insert(3, String.valueOf('-')); // Insere o hífen na StrinfBuilder
								hyphen = true; // Informa que o hífen está presente no EditText										
								isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada	
								editText.setTextKeepState(plate); // Insere o hífen no EditText
									
								editText.setInputType(InputType.TYPE_CLASS_NUMBER); // Altera o teclado para inserção de números
								editText.setSelection(4); // Reposiciona o cursor												
							}
							
						// Se o caracter digitado não for uma letra	
						} else {
							plate.deleteCharAt(start); // Deleta da StringBuilder o caractere digitado							
							isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
							editText.setTextKeepState(plate); // Apaga o caractere digitado do EditText
							editText.setSelection(start); // Reposiciona o cursor							
														
							editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS); // Altera o teclado para inserção de letras
							editText.setError("Os três primeiros caracteres devem ser letras"); // Exibe mensagem de erro
							
						}
					
					// Se o usuário digitou algum caractere na posição ocupada pelo hífen	
					} else if (start == 3) {
						
						// Se houver hífen no EditText
						if (hyphen) {
							plate.deleteCharAt(start); // Deleta o caractere digitado da StringBuilder
							isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
							editText.setTextKeepState(plate); // Apaga o caractere digitado do EditText
							editText.setSelection(4); // Reposiciona o cursor							
						
						// Se não houver hífen no EditText e o caractere digitado for uma letra
						} else if (Character.isLetter(lastChar)) {
							
							// Se há números no EditText, o usuário pode estar inserindo uma letra após um número (por exemplo, E1A234, onde A foi a letra inserida)
							if (totalNumbers != 0) {
								if (totalLetters == 1) {
									
									// Reposiciona a letra para depois da primeira letra existente
									char letter = plate.charAt(start);
									plate.deleteCharAt(start);										
									plate.insert(1, letter);	
									isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada										
									editText.setTextKeepState(plate); // Atualiza o EditText
									editText.setSelection(2); // Reposiciona o cursor
									
									editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS); // Altera o teclado para inserção de letras
									
								// Se esta sendo digitada a terceira letra da placa
								} else if (totalLetters == 2) {
									
									// Se o caractere anterior a letra é um número
									if (Character.isDigit(plate.charAt(start - 1))) {
										
										// Reposiciona o número para depois da letra digitada
										char number = plate.charAt(start - 1);
										plate.deleteCharAt(start - 1);										
										plate.insert(start, number);
										
										plate.insert(3, String.valueOf('-')); // Insere o hífen na StrinfBuilder
										hyphen = true; // Informa que o hífen está presente no EditText		
										
										isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada										
										editText.setTextKeepState(plate); // Atualiza o EditText
									}
									
									editText.setInputType(InputType.TYPE_CLASS_NUMBER); // Altera o teclado para inserção de numeros
								}
								
								totalLetters++;
							}												
							
						// Se não houver hífen no EditText e ja houverem quatro números no EditText						
						} else if (totalNumbers == 4) {
							plate.deleteCharAt(sLength - 1); //Apaga o último dos quatro números que já estavam no EditiText
							isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
							editText.setTextKeepState(plate); // Apaga o último número do EditText								
							
						} else {
							totalNumbers++;
						}						
					
					// Se o usuário digitou algum caractere na posição de um dos quatro números da placa	
					} else if (start >= 4 && start <= 7) {

						// Se o caractere digitado for uma letra						
						if (Character.isLetter(lastChar)) {						
							plate.deleteCharAt(start); // Deleta o caractere digitado da StringBuilder
							editText.setError("Os quatro últimos caracteres devem ser números"); // Exibe mensagem de erro
							isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
							editText.setTextKeepState(plate); // Apaga o caractere digitado do EditText
							editText.setInputType(InputType.TYPE_CLASS_NUMBER); // Altera o teclado para inserção de números
							editText.setSelection(start); // Reposiciona o cursor							

						// Se ja houverem quatro números no EditText	
						} else if (totalNumbers == 4) {
							plate.deleteCharAt(sLength - 1); //Apaga o último dos quatro números que já estavam no EditiText
							isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
							editText.setTextKeepState(plate); // Apaga o último número do EditText								
							
						} else {
							totalNumbers++;
						}
						
					// Se o EditText já possui 8 caracteres (###-####) e o cursor está após o último caractere
					// (O nono caractere irá ocupar o oitavo índice da string que armazena o valor do EditText)
					} else if (sLength == 9 && start == 8) {
						plate.deleteCharAt(start); // Deleta o caractere digitado da StringBuilder
						isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
						editText.setTextKeepState(plate); // Apaga o número digitado do EditText
					}
					
				// Se um caractere foi apagado	
				} else if (before) {
					// Se o caracter apagado era uma letra
					if (Character.isLetter(deletedChar)) {
						totalLetters--;
						editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS); // Altera o teclado para inserção de letras
						
						if (hyphen) {
							hyphen = false;
							plate.deleteCharAt(totalLetters); // Deleta o hífen
							isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
							editText.setTextKeepState(plate); // Apaga do EditText a letra anterior ao hífen
						}
					
					// Se o caracter apagado era um número	
					} else if (Character.isDigit(deletedChar)) {
						totalNumbers--;
					
					// Se o caractere apagado era o hífen	
					} else if (deletedChar == '-') {
						hyphen = false;
						plate.deleteCharAt(start - 1); // Deleta a letra anterior ao hífen			
						totalLetters--;
						editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS); // Altera o teclado para inserção de letras						
						isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada
						editText.setTextKeepState(plate); // Apaga do EditText a letra anterior ao hífen 
						editText.setSelection(2); // Reposiciona o cursor						
					}
				} 
			}
		};
	}

	/**
	 * Adiciona uma máscara de placa no formato estrangeiro (só permite a inserção de letras, números e um caractere hífen).
	 *  
	 * @param mask
	 * @param ediTxt
	 * @return
	 */
	public static TextWatcher insertForeignPlate(final EditText editText) {
		// Inicia a activity com o teclado no modo para inserção de letras
		editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

		return new TextWatcher() {
			// Informa se há ou não o caractere hífen no EditText
			boolean hyphen = false;
			// Armazena a posição no EditText do último caractere inserido ou removido
			int start = 0;
			// Indica se algum caractere foi inserido no EditText
			boolean count = false;
			// Se o conteúdo de um EditText associado a um TextWatcher for alterado por um dos métodos do TextWatcher (através do método setText, por exemplo), os métodos do TextWatcher serão executados novamente. A flag isUpdating é usada para evitar o processamento de alterações que não foram feitas pelo usuário.
			boolean isUpdating = false;

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Se a mudança no EditText foi efetuada pelo usuário, e não pelo método afterTextChange
				if (!isUpdating) {
					// Obtém a localização no EditText do caractere inserido ou removido
					this.start = start;

					// Obtém a indicação de que a alteração no EditText foi de inserção ou remoção de um caractere. O correto seria testar "se before é igual a 1 e count é igual a 0", mas devido a possibilidade de se usar o aplicativo SwiftKey, o teste para verificar se um caractere foi apagado foi alterado para "se before é maior ou igual a 1 e count é igual a 0". Isso porque quando se usa esse aplicativo e mantém-se pressionado o botão backspace os métodos do TextWatcher são executados somente após a deleção de todos os números e do hífen, resultando em um valor para before igual ao total de caracteres deletados. Já com o teclado da Samsung, mesmo segurando-se o botão backspace, os métodos do TextWatcher são executados após a deleção de cada caractere.

					// Se um caractere foi apagado do EditText
					if (before >= 1 && count == 0) {						
						this.count = false;
						// Se um caractere foi inserido no EditText
					} else if (before == 0 && count == 1) {						
						this.count = true;
					}
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// Se um caracter foi removido
				if (count == 1 && after == 0) {
					// Se o caractere removido foi o hífen
					if (s.charAt(start) == '-') {
						hyphen = false; // Informa que o hífen não está presente no EditText
					}
				}
			}

			/**
			 * Valida a máscara da placa após cada alteração no EditText.
			 */
			public void afterTextChanged(Editable s) {
				// Obtem a string da placa digitada até o momento
				StringBuilder plate = new StringBuilder(s.toString().toUpperCase());
				// Obtém o tamanho da string da placa digitada até o momento
				int sLength = plate.length();

				// Se o EditText foi alterado pelo método afterTextChanged e não pelo usuário
				if (isUpdating) {
					isUpdating = false;
					return; // Retorna sem processar a alteração
				}

				// Remove a mensagem de erro do EditText, se houver
				editText.setError(null);

				// Se o último caractere restante foi apagado ou se o EditText foi atualizado para "" (string sem caracteres) por outro método (a desmarcação do checkbox Placa Estrangeira, por exemplo)
				if (sLength == 0) {
					editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS); // Altera o teclado para inserção de letras

				// Se um caractere foi digitado
				} else if (count) {
					// Obtém o caractere digitado pelo usuário
					char lastChar = plate.charAt(start);

					// Se o caractere digitado não for uma letra ou um número
					if (!Character.isLetter(lastChar) && !Character.isDigit(lastChar)) {
						// Se o caractere digitado não for o hífen
						if (lastChar != '-') {
							plate.deleteCharAt(start); // Deleta o caractere digitado da StringBuilder
							editText.setSelection(start); // Reposiciona o cursor
							editText.setError("Caracter inválido!"); // Exibe mensagem de erro					
							
						// Se o caractere digitado é um hífen e já há um hífen no EditText	
						} else if (hyphen) {
							plate.deleteCharAt(start); // Deleta o caractere digitado da StringBuilder
							editText.setSelection(start); // Reposiciona o cursor
							editText.setError("Apenas um caracter hífen é permitido!"); // Exibe mensagem de erro							
							
						// Se o caractere digitado é um hífen e não há um hífen no EditText	
						} else {
							hyphen = true; // Informa que o hífen está presente no EditText
						}
					}
					
					// As duas linhas a seguir são necessárias apenas se o teclado utilizado for o SwiftKey (o InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS não é respeitado por esse aplicativo)
					isUpdating = true; // Evita que a alteração no EditText efetuada na próxima linha seja processada					
					editText.setTextKeepState(plate); // Atualiza o EditText
					
				}
			}
		};
	}
}
