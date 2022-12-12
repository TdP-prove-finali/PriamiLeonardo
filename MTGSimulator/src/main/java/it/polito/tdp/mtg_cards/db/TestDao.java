package it.polito.tdp.mtg_cards.db;

import it.polito.tdp.mtg_cards.model.Espansione;

public class TestDao {

	public static void main(String[] args) {
		MtgDAO dao = new MtgDAO();
		for(Espansione e : dao.getAllEspansioni())
			System.out.println(e);
	}

}
