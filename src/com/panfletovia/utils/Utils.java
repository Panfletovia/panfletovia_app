package com.panfletovia.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.panfletovia.annotation.Field;
import com.panfletovia.bean.AreaItem;
import com.panfletovia.enums.Fields;
import com.s2parking.activities.R;


public class Utils {

	private static NotificationManager notificationManager;
	/**
	 * Verifica se o dado endereco MAC (com mascara) eh valido
	 * 
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static boolean isMacAddressValid(String maskedMacAddress) {
		if (maskedMacAddress == null) {
			return false;
		}
		maskedMacAddress = maskedMacAddress.toUpperCase();
		if (maskedMacAddress.length() != "FF.FF.FF.FF.FF.FF".length()) {
			return false;
		}
		String[] parts = maskedMacAddress.split("\\.");
		for (String part : parts) {
			if (!part.matches("^[0-9A-F]+$")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Retorna o id do chip GSM/CDMA TODO: criar mock
	 * 
	 * @return
	 */
	@SuppressLint("NewApi")
	public static final String getDeviceId() {
		try {
			TelephonyManager manager = (TelephonyManager) Application.get()
					.getSystemService(Context.TELEPHONY_SERVICE);
			String id = manager.getDeviceId();
			if (null == id) {
				id = Build.SERIAL;
			}
			return id.toUpperCase();
		} catch (Exception ex) {
			return "";
		}
	}

	@SuppressLint("DefaultLocale")
	public static final String humanize(String enumString) {
		return enumString.substring(0, 1)
				+ enumString.substring(1).toLowerCase(Locale.getDefault())
						.replace("_", " ");
	}

	/**
	 * Formata uma string normal para o padrao enum do banco Ex.:
	 * "Ticket Incompativel" -> "TICKET_INCOMPATIVEL"
	 * 
	 * @param humanString
	 *            String to be converted
	 * @return enum-formatted string
	 */
	public static final String tableize(String humanString) {
		return StringUtils.stripAccents(humanString.toUpperCase(
				Locale.getDefault()).replace(" ", "_"));
	}

	public static final String md5(final String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void initializeFields(Activity activity)
			throws IllegalAccessException {
		initializeFields(activity, activity.getClass());
	}

	/**
	 * Metodo utilizado para iniciar todos os components que estiverem com a
	 * annotation "field".
	 * 
	 * @param activity
	 * @throws IllegalAccessException
	 */
	public static void initializeFields(Activity activity,
			Class<? extends Activity> activityClass)
			throws IllegalAccessException {
		for (java.lang.reflect.Field campo : activityClass.getDeclaredFields()) {
			campo.setAccessible(true);
			if (campo.isAnnotationPresent(Field.class)
					&& campo.getAnnotation(Field.class).id() > -1) {
				Integer id = campo.getAnnotation(Field.class).id();
				if (campo.getType().equals(Button.class)) {
					campo.set(activity, (Button) activity.findViewById(id));
				} else if (campo.getType().equals(RadioButton.class)) {
					campo.set(activity, (RadioButton) activity.findViewById(id));
				} else if (campo.getType().equals(EditText.class)) {
					EditText edt = (EditText) activity.findViewById(id);
					campo.set(activity, edt);
				} else if (campo.getType().equals(Spinner.class)) {
					Spinner spi = (Spinner) activity.findViewById(id);
					campo.set(activity, spi);
				} else if (campo.getType().equals(TextView.class)) {
					campo.set(activity, (TextView) activity.findViewById(id));
				} else if (campo.getType().equals(ListView.class)) {
					campo.set(activity, (ListView) activity.findViewById(id));
				} else if (campo.getType().equals(RadioGroup.class)) {
					campo.set(activity, (RadioGroup) activity.findViewById(id));
				} else if (campo.getType().equals(TabHost.class)) {
					TabHost tbh = (TabHost) activity.findViewById(id);
					campo.set(activity, tbh);
				} else if (campo.getType().equals(ProgressBar.class)) {
					campo.set(activity, (ProgressBar) activity.findViewById(id));
				} else if (campo.getType().equals(ImageButton.class)) {
					ImageButton imb = (ImageButton) activity.findViewById(id);
					campo.set(activity, imb);
				} else if (campo.getType().equals(ImageView.class)) {
					campo.set(activity, (ImageView) activity.findViewById(id));
				} else if (campo.getType().equals(GridView.class)) {
					campo.set(activity, (GridView) activity.findViewById(id));
				} else if (campo.getType().equals(SurfaceView.class)) {
					campo.set(activity, (SurfaceView) activity.findViewById(id));
				} else if (campo.getType().equals(TimePicker.class)) {
					final TimePicker timePicker = ((TimePicker) activity
							.findViewById(id));
					// TimePicker com formato 24 horas.
					timePicker.setIs24HourView(true);
					campo.set(activity, timePicker);
				} else if (campo.getType().equals(CheckBox.class)) {
					CheckBox che = (CheckBox) activity.findViewById(id);
					campo.set(activity, che);
					che.setOnEditorActionListener(new TextView.OnEditorActionListener() {

						public boolean onEditorAction(TextView tv, int id,
								KeyEvent ke) {
							if (ke.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
								TextView nextField = (TextView) tv
										.focusSearch(View.FOCUS_DOWN);
								if (nextField != null) {
									nextField.requestFocus();
								}
								return true;
							}
							return false;
						}
					});
				}
			}
		}
	}

	public static boolean validateRequiredFields(Activity activity)
			throws Exception {
		return validateRequiredFields(activity, activity.getClass());
	}

	/**
	 * Metodo utilizado para validar os campos que estiverem com a annotation
	 * required true.
	 * 
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	public static boolean validateRequiredFields(Activity activity,
			Class<? extends Activity> activityClass) throws Exception {
		boolean isValido = true;
		EditText edit = null;
		for (java.lang.reflect.Field campo : activityClass.getDeclaredFields()) {
			campo.setAccessible(true);
			if (campo.isAnnotationPresent(Field.class)
					&& campo.getAnnotation(Field.class).required()) {
				if (campo.getType().equals(EditText.class)) {
					edit = (EditText) campo.get(activity);
					if (edit.getText() == null
							|| edit.getText().toString().trim()
									.equalsIgnoreCase("")) {
						isValido = false;
					}

				} else if (campo.getType().equals(Spinner.class)) {
					Spinner spinner = (Spinner) campo.get(activity);
					if (spinner.getSelectedItem() == null
							|| spinner.getSelectedItem().toString().trim()
									.equalsIgnoreCase("")) {
						isValido = false;
					}
				}
			}
			if (!isValido) {
				// Se o id da string for 0, nao foi configurada mensagem de
				// validacao
				int stringId = campo.getAnnotation(Field.class).message();
				if (stringId != 0) {
					edit.setError(activity.getString(stringId));
					edit.clearFocus();
					edit.requestFocus();
					// ((BaseActivity)activity).getToaster().slow(stringId);
				}
				break;
			}
		}
		return isValido;
	}

	@SuppressLint("SimpleDateFormat")
	public static String elapsedTime(String startTime) throws ParseException {
		// Cria um objeto date com a data informada
		Date start = Formatter.get().getDatabaseFormat().parse(startTime);
		// Calcula a diferenca entre o horario informado e "agora"
		long differenceInMilissecons = new Date().getTime() - start.getTime();
		// Converte a diferenca para minutos
		long differenceInMinutes = differenceInMilissecons / 1000 / 60;

		int totalHours = 0;
		int totalMinutes = 0;
		// Se o tempo eh inferior a 1 hora
		if (differenceInMinutes / 60 < 1) {
			totalMinutes = (int) differenceInMinutes;
		} else { // Se for superior a uma hora
			// Calcula a quantidade de horas
			totalHours = (int) Math.floor(differenceInMinutes / 60);
			// Se houver minutos
			if (totalMinutes % 60 > 0) {
				// Converte o valor fracionario para minutos
				totalMinutes = (int) (totalHours - differenceInMinutes) / 60;
			}
		}

		// Monta a string conforme os parâmetros
		StringBuilder elapsedTime = new StringBuilder();
		elapsedTime.append("A ");
		// Mais de uma hora
		if (totalHours > 0 && totalMinutes > 0) {
			elapsedTime.append(String.valueOf(totalHours));
			elapsedTime.append(" horas e ");
			elapsedTime.append(String.valueOf(totalMinutes));
			elapsedTime.append(" minuto(s)");
		} else {
			// Menos de um minuto
			if (totalHours == 0 && totalMinutes == 0) {
				elapsedTime.append("alguns segundos");
			}
			// Hora cheia
			if (totalHours > 0 && totalMinutes == 0) {
				elapsedTime.append(String.valueOf(totalHours));
				elapsedTime.append(" hora(s)");
			}
			// Menos de uma hora
			if (totalHours == 0 && totalMinutes > 0) {
				elapsedTime.append(String.valueOf(totalMinutes));
				elapsedTime.append(" minuto(s)");
			}
		}

		return elapsedTime.toString();
	}

	/**
	 * Verifica se a placa foi digitada no formato brasileiro (ABC-1234).
	 * 
	 * Valida as letras, os numeros e o hifen mesmo a placa sendo formatada
	 * corretamente atraves do TextWatcher insertPlate.
	 */
	public static boolean validateBrazilianPlateFormat(final EditText editText) {
		// Se a placa possui oito digitos (ABC-1234)
		if (editText.length() == 8) {
			// Obtem a string da placa digitada
			String placa = editText.getText().toString();
			// Variavel que armazena cada caractere da string para validacao
			char character;

			for (int count = 0; count <= 7; count++) {
				character = placa.charAt(count);
				// Se o caractere a ser validado encontra-se na posicao de uma
				// das três letras da placa
				if (count <= 2) {
					// Se o caractere nao eh uma letra
					if (!Character.isLetter(character)) {
						return false;
					}
					// Se o caractere a ser validado encontra-se na posicao do
					// hifen
				} else if (count == 3) {
					// Se o caracter nao for o hifen
					if (character != '-') {
						return false;
					}

					// Se o caractere a ser validado encontra-se posicao de um
					// dos quatro numeros da placa
				} else {
					// Se o carater nao for um numero
					if (!Character.isDigit(character)) {
						return false;
					}
				}
			}
			// Se a placa digitada estah no padrao brasileiro
			return true;

			// Se a placa nao possui oito digitos
		} else {
			return false;
		}

	}

	public static String getNowDateTime() {
		return parseTime("%d/%m/%Y %T");
	}

	public static String getNowDate() {
		return parseTime("%d/%m/%Y");
	}

	public static String getNowTime() {
		return parseTime("%T");
	}

	private static String parseTime(String format) {
		Time now = new Time();
		now.setToNow();
		return now.format(format);
	}

	/**
	 * Verifica se ha uma conexao de internet disponivel
	 * 
	 * @return
	 */
	public static boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) Application.get()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	@SuppressLint("NewApi")
	public static String removeSpecialCharacters(String text) {
		if (null == text) {
			return text;
		}
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll(
				"[^\\p{ASCII}]", "");
	}

	/**
	 * Cria o hash da senha
	 * 
	 * @param password
	 * @param cpfCnpj
	 * @return token
	 */
	public static String hashPassword(String password, String cpfCnpj) {
		String passToken = password + cpfCnpj;
		for (int i = 0; i < 100; i++) {
			passToken = Utils.md5(passToken).toLowerCase(Locale.getDefault());
		}

		return passToken;
	}

	/**
	 * Validação de erros retornados pela API.
	 * 
	 * @param activity
	 * @param response
	 * @throws JSONException
	 */
	public static void validationErros(Activity activity, JSONObject response)
			throws JSONException {
		// Verifica se ocorreu o erro de nsu.
		// Se ocorreu salva nas preferências o nsu de retorno.
		if (response != null) {
			// Se existe codigo_erro.
			if (response.has("codigo_erro")) {
				int codigoErro = response.getInt("codigo_erro");
				if (codigoErro == 60) {
					ConfigurationManager.get().set("nsu",
							response.get("nsu").toString());
				}
				Toast.makeText(activity, response.get("name").toString(),
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(activity, response.toString(), Toast.LENGTH_LONG)
						.show();
			}
		} else {
			Toast.makeText(
					activity,
					"Ocorreu um erro na comunicação com o servidor!!! Por favor, tente novamente.",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Converte um Map<String, Object> para um List<NameValuePair>.
	 * 
	 * Utilizado pelo APIRequest.
	 * 
	 * @param params
	 * @return
	 */
	public static List<NameValuePair> mapToNameValuePairList(JSONObject params)
			throws JSONException {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<String> i = params.keys();
		while (i.hasNext()) {
			String key = i.next();
			list.add(new BasicNameValuePair(key, params.getString(key)));
		}
		return list;
	}

	/**
	 * Retorna um List<AreaItem> das áreas.
	 */
	public static List<AreaItem> getAreas() {
		final List<AreaItem> list = new ArrayList<AreaItem>();
		try {
			JSONArray arrayAreas = new JSONArray(ConfigurationManager.get().getString(Fields.AREAS));
			for (int i = 0; i < arrayAreas.length(); i++) {
				JSONObject objArea = arrayAreas.getJSONObject(i);
				JSONObject objItem = objArea.getJSONObject(Fields.JSON_AREA);
				list.add(new AreaItem(objItem.getInt(Fields.ID), "  " + objItem.getString(Fields.NOME)));
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public static Map<String, String> getHashMapResource(Context c, int hashMapResId) {
		Map<String, String> map = null;
		XmlResourceParser parser = c.getResources().getXml(hashMapResId);

		String key = null, value = null;

		try {
			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					Log.d("utils", "Start document");
				} else if (eventType == XmlPullParser.START_TAG) {
					if (parser.getName().equals("map")) {
						boolean isLinked = parser.getAttributeBooleanValue(null, "linked", false);

						map = isLinked ? new LinkedHashMap<String, String>() : new HashMap<String, String>();
					} else if (parser.getName().equals("entry")) {
						key = parser.getAttributeValue(null, "key");

						if (null == key) {
							parser.close();
							return null;
						}
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (parser.getName().equals("entry")) {
						map.put(key, value);
						key = null;
						value = null;
					}
				} else if (eventType == XmlPullParser.TEXT) {
					if (null != key) {
						value = parser.getText();
					}
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return map;
	}
	
	/**
	 * Método para criar uma notificação na barra de status 
	 */
	@SuppressLint("NewApi")
	public static void sendNotification(Context context, Class<?> startClass, String title, String description, int icon, int idTicket) {
		// Busca instância do objeto que controla as notificações do aparelho
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// Intent do click da notificação
		Intent startActivityIntent = new Intent(context, startClass);
		PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, startActivityIntent, 0);
		// Cria a notificação 
		Notification.Builder builder = new Notification.Builder(context);
		builder.setSmallIcon(icon);
		builder.setContentTitle(title);
		builder.setContentText(description);
		builder.setLights(Color.RED, 3000, 3000);
		builder.setVibrate(new long[] { 1000 ,1000 ,1000 ,1000 ,1000});
		builder.setContentIntent(pendingIntent);
		notificationManager.notify(idTicket, builder.getNotification());

	}// End Method 'sendNotification'
	
	
	/**
	 * Método para cancelar a notificação na barra de status
	 * @param id - Ticket Id
	 */
	public static void cancelNotification(int id) {
		notificationManager.cancel(id);
	}// End Method 'cancelNotification'
	
	/**
	 * Seta o título da activity.
	 * @param activity
	 * @param titleId
	 */
	public static void setTitle(Activity activity, int titleId) {
		final TextView textView = (TextView) activity.findViewById(R.id.title);
		textView.setText(activity.getString(titleId));
	}
	
}// End Class