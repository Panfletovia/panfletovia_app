package com.panfletovia.type;

import java.util.ArrayList;
import java.util.List;

import com.panfletovia.R;
import com.panfletovia.utils.Application;

public enum AppSections {

	INICIO(R.string.app_sections_inicio),
	MEUS_PANFLETOS(R.string.app_sections_meus_panfletos), 
	PREFERENCIAS(R.string.app_sections_preferencias), 
	SAIR(R.string.app_sections_sair)
	;

	private int resourceId;

	private AppSections(int id) {
		resourceId = id;
	}

	/**
	 * Método para retornar uma lista com os menus de acesso
	 * @return
	 */
	public static List<String> getListSections () {
		List<String> listSections = new ArrayList<String>();
		Application application = Application.get();
		for (AppSections appSection : AppSections.values()){
			listSections.add(application.getString(appSection.resourceId));
		}
		return listSections;
	}// End Method 'getListSections'
}// End Class