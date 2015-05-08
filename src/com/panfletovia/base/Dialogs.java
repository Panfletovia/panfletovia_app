package com.panfletovia.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class Dialogs {

	private Context context;
	private ProgressDialog progress;
	private View view;
	private AlertDialog.Builder alert;
	private AlertDialog alertDialog;

	public Dialogs(Context context) {
		this.context = context;
	}

	/**
	 * Mostra uma dialog de confirmação
	 * 
	 * @param title
	 *            Título da dialog
	 * @param message
	 *            Mensagem
	 * @param onPositive
	 *            Ação positiva (caso o usuário clique em Ok)
	 */
	public void confirm(String title, String message, DialogInterface.OnClickListener onPositive) {
		confirm(title, message, onPositive, null);
	}

	/**
	 * Mostra uma dialog de confirmação
	 * 
	 * @param title
	 *            Título da dialog
	 * @param message
	 *            Mensagem
	 * @param onPositive
	 *            Ação positiva (caso o usuário clique em Ok)
	 * @param onNegative
	 *            Ação negativa (caso o usuário clique em Cancelar)
	 */
	public void confirm(String title, String message, DialogInterface.OnClickListener onPositive, DialogInterface.OnClickListener onNegative) {
		alert = new AlertDialog.Builder(context);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setIcon(android.R.drawable.ic_dialog_alert);
		alert.setPositiveButton(android.R.string.yes, onPositive);
		alert.setNegativeButton(android.R.string.no, onNegative);
		alert.show();
	}

	/**
	 * Mostra um alert com o botão de OK apenas, obrigando o usuário a clicar
	 * para continuar
	 * 
	 * @param title
	 *            - Título da dialog
	 * @param message
	 *            - Mensagem
	 * @param onOK
	 *            - Ação positiva (caso o usuário clique em Ok)
	 */
	public void alert(String title, String message, DialogInterface.OnClickListener onOK) {
		alert = new AlertDialog.Builder(context);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setCancelable(false);
		alert.setPositiveButton(android.R.string.ok, onOK);
		alert.show();
	}

	/**
	 * Cria um AlertDialog personalizado.
	 * 
	 * @param layout
	 * @param title
	 */
	public View createDialog(final Activity activity, int layout, String title) {
		View view = activity.getLayoutInflater().inflate(layout, null);
		alert = new AlertDialog.Builder(activity);
		alert.setView(view);
		alert.setInverseBackgroundForced(true);
		alert.setTitle(title);
		alert.create();
		alertDialog = alert.show();
		return view;
	}

	/**
	 * Cria um AlertDialog personalizado com opções OK/CANCELAR Seta o campo
	 * view com a view utilizada no dialog
	 * 
	 * @param layout
	 * @param title
	 * @param onOK
	 * @param onCancel
	 */
	public void createDialog(final Activity activity, int layout, String title, DialogInterface.OnClickListener onOK, DialogInterface.OnClickListener onCancel) {
		view = activity.getLayoutInflater().inflate(layout, null);
		alert = new AlertDialog.Builder(activity);
		alert.setInverseBackgroundForced(true);
		alert.setView(view);
		alert.setTitle(title);
		alert.setPositiveButton(android.R.string.ok, onOK);
		alert.setNegativeButton(android.R.string.cancel, onCancel);
		alert.create();
		alert.show();
	}

	/**
	 * Abre um ProgressDialog please_wait.
	 * 
	 * @param context
	 */
	public void openProgressDialog() {
		progress = new ProgressDialog(context);
		progress.setIndeterminate(true);
//		progress.setMessage(context.getResources().getString(R.string.please_wait));
		progress.setCancelable(false);
		progress.show();
	}

	/**
	 * Fecha o ProgressDialog.
	 */
	public void closeProgressDialog() {
		if (progress != null) {
			progress.dismiss();
			progress = null;
		}	
	}

	/**
	 * Retorna a view
	 * 
	 * @return
	 */
	public View getView() {
		return view;
	}
	
	/**
	 * Retorna o AlerDialog.
	 * @return
	 */
	public AlertDialog.Builder getAlertDialogBuilder() {
		return alert;
	}
	
	public AlertDialog getAlertDialog() {
		return alertDialog;
	}
	
	public ProgressDialog getProgressDialog() {
		return progress;
	}
}
