package it.polito.tdp.mtg_cards.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.mtg_cards.model.Espansione;
import it.polito.tdp.mtg_cards.model.Carta;

public class MtgDAO {

	public List<Espansione> getAllEspansioni() {

		final String sql = "SELECT code,name FROM mtg_cards.sets where isOnlineOnly=0 and isPartialPreview=0 and booster is not null";
		List<Espansione> espansioni = new ArrayList<Espansione>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Espansione esp = new Espansione(rs.getString("code"), rs.getString("name"));
				espansioni.add(esp);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return espansioni;
	}

	public List<Carta> getAllCarte(String sc) {
		final String sql = "((SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'common' order by rand() limit 11)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'uncommon' order by rand() limit 3)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and (rarity='rare' or rarity='mythic') order by rand() limit 1)" +
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'common' order by rand() limit 11)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'uncommon' order by rand() limit 3)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and (rarity='rare' or rarity='mythic') order by rand() limit 1)" + 
				"union all" +
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'common' order by rand() limit 11)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'uncommon' order by rand() limit 3)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and (rarity='rare' or rarity='mythic') order by rand() limit 1)" +
				"union all" +
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'common' order by rand() limit 11)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'uncommon' order by rand() limit 3)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and (rarity='rare' or rarity='mythic') order by rand() limit 1)" +
				"union all" +
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'common' order by rand() limit 11)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'uncommon' order by rand() limit 3)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and (rarity='rare' or rarity='mythic') order by rand() limit 1)" +
				"union all" +
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'common' order by rand() limit 11)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and rarity = 'uncommon' order by rand() limit 3)" + 
				"union all" + 
				"(SELECT name,rarity,manaCost,manaValue,setCode FROM mtg_cards.cards where setCode = ? and (rarity='rare' or rarity='mythic') order by rand() limit 1)) order by name";

		List<Carta> carte = new ArrayList<Carta>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			for(int i=1;i<=18;i++) {
				st.setString(i, sc);
			}
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Carta c = new Carta(rs.getInt("manaValue"), rs.getString("manaCost"), rs.getString("name"),
						rs.getString("rarity"));
				carte.add(c);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return carte;
	}

}
