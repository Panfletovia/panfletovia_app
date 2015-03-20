package com.s2parking.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

/**
 * Classe responsável pela formatação de moedas, datas e números
 * 
 * Singleton
 */
public class Formatter {

	private NumberFormat currency;
	private NumberFormat number;
	private DateFormat dateTime;
	private DateFormat date;
	private DateFormat time;
	private BigDecimal oneHundred = new BigDecimal(100);

	private Formatter(Locale locale) {
		currency = NumberFormat.getCurrencyInstance(locale);
		number = NumberFormat.getInstance(locale);
		dateTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
		date = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		time = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
	}
	
	public DateFormat getDatabaseFormat() {
		return databaseFormatDatetime;
	}

	public static final Formatter get() {
		return get(Locale.getDefault());
	}

	public static final Formatter get(Locale locale) {
		if (instance == null) {
			instance = new Formatter(locale);
		}
		return instance;
	}

	private static Formatter instance;
	@SuppressLint("SimpleDateFormat")
	private DateFormat databaseFormatDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat databaseFormatDate = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Formata uma data/hora
	 * 
	 * @param databaseDateTime
	 * @return
	 */
	public String dateTime(String databaseDateTime) {
		try {
			return dateTime.format(databaseFormatDatetime.parse(databaseDateTime));
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Cria um objeto date de acordo com a string passado de parâmetro.
	 * 
	 * @param databaseDateTime
	 * @return
	 */
	public Date parseDate(String databaseDateTime) {
		try {
			return databaseFormatDatetime.parse(databaseDateTime);
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Formata uma data/hora de banco em data para o usuário
	 * 
	 * @param databaseDateTime
	 * @return
	 */
	public String dateFromSQLDatetime(String databaseDateTime) {
		try {
			return dateTime.format(databaseFormatDatetime.parse(databaseDateTime));
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Formata uma data de banco em data para o usuário
	 * 
	 * @param databaseDateTime
	 * @return
	 */
	public String dateFromSQLDate(String databaseDate) {
		try {
			return date.format(databaseFormatDate.parse(databaseDate));
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Formata uma data de banco em data para o usuário
	 * 
	 * @param databaseDateTime
	 * @return
	 */
	public String dateDayMonth(String databaseDate) {
		try {
			String dayMonth =  date.format(databaseFormatDate.parse(databaseDate));
			return dayMonth.substring(0, 5);
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Formata uma data/hora em apenas hora
	 * 
	 * @param databaseDateTime
	 * @return
	 */
	public String time(String databaseDateTime) {
		try {
			return time.format(databaseFormatDatetime.parse(databaseDateTime));
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Formata um inteiro
	 * 
	 * @param number
	 * @return
	 */
	public String number(long num) {
		return number.format(num);
	}

	/**
	 * Formata moeda
	 * 
	 * @param cents
	 *            O valor em centavos
	 * @return
	 */
	public String currency(long cents) {
		BigDecimal decimal = new BigDecimal(cents);
		decimal = decimal.divide(oneHundred);
		return currency.format(decimal);
	}
	
	/**	
	 * Formata para Bytes (B)
	 */
	public String bytes(long bytes) {
		return number(bytes) + "B";		
	}
}
