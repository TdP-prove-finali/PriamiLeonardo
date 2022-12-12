package it.polito.tdp.mtg_cards.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import it.polito.tdp.mtg_cards.db.MtgDAO;

public class Model {
	
	private MtgDAO dao;
	private List<Carta> carte;	
	private List<Carta> magie;
	private double forza = 0;
	private double forzaMassima = 0;
	private int numeroCarte = 0;
	private int numeroTerre = 0;
	private int numeroPianure = 0;
	private int numeroIsole = 0;
	private int numeroPaludi = 0;
	private int numeroMontagne = 0;
	private int numeroForeste = 0;
	private int numeroTerreOttimo = 0;
	private int numeroPianureOttimo = 0;
	private int numeroIsoleOttimo = 0;
	private int numeroPaludiOttimo = 0;
	private int numeroMontagneOttimo = 0;
	private int numeroForesteOttimo = 0;
	private int iterazioni = 0;
	
	public Model() {
		dao = new MtgDAO();
	}
	
	private Collection<Carta> ordinaCarte() {
		Collections.sort(carte, new Comparator<Carta>() {
			public int compare(Carta c1, Carta c2) {
				if(c1.getForza()==c2.getForza()) {
					return 0;
				}
				else if (c1.getForza()>c2.getForza()) {
					return -1;
				}
				else return 1;
			}
		});
		return carte;
	}
	
	public List<Carta> elencoCarte(String code){
		carte = dao.getAllCarte(code);
		List<Carta> temp = new ArrayList<Carta>(carte);
		for(Carta c : temp) {
			if(c.getManaValue()==0) {
				carte.remove(c);
			}
		}
		ordinaCarte();
		return carte;
	}
	
	public List<Espansione> espansioni(){
		return dao.getAllEspansioni();
	}
	
	public List<Carta> ottimo(Carta c1, Carta c2){
		forza = 0;
		forzaMassima = 0;
		numeroCarte = 0;
		numeroTerre = 0;
		numeroPianure = 0;
		numeroIsole = 0;
		numeroPaludi = 0;
		numeroMontagne = 0;
		numeroForeste = 0;
		numeroTerreOttimo = 0;
		numeroPianureOttimo = 0;
		numeroIsoleOttimo = 0;
		numeroPaludiOttimo = 0;
		numeroMontagneOttimo = 0;
		numeroForesteOttimo = 0;
		iterazioni = 0;
		magie = new ArrayList<>();
		if(c1 != null) {
			int copie = 0;
			List<Carta> temp = new ArrayList<Carta>(carte);
			for(Carta c : temp) {
				if(c.getNome().equals(c1.getNome())) {
					magie.add(c);
					copie++;
					carte.remove(c);
				}
			}
			forza = forza+c1.getForza()*copie;
			numeroPianure = c1.getTerreW();
			numeroIsole = c1.getTerreU();
			numeroPaludi = c1.getTerreB();
			numeroMontagne = c1.getTerreR();
			numeroForeste = c1.getTerreG();
			numeroTerre = c1.getTerre();
			numeroCarte = numeroTerre+copie;
		}
		if(c2 != null) {
			int copie = 0;
			List<Carta> temp = new ArrayList<Carta>(carte);
			for(Carta c : temp) {
				if(c.getNome().equals(c2.getNome())) {
					magie.add(c);
					copie++;
					carte.remove(c);
				}
			}
			forza = forza+c2.getForza()*copie;
			if(numeroPianure < c2.getTerreW()) numeroPianure = c2.getTerreW();
			if(numeroIsole < c2.getTerreU()) numeroIsole = c2.getTerreU();
			if(numeroPaludi < c2.getTerreB()) numeroPaludi = c2.getTerreB();
			if(numeroMontagne < c2.getTerreR()) numeroMontagne = c2.getTerreR();
			if(numeroForeste < c2.getTerreG()) numeroForeste = c2.getTerreG();
			if(numeroTerre < c2.getTerre()) numeroTerre = c2.getTerre();
			if(numeroTerre <= numeroPianure+numeroIsole+numeroPaludi+numeroMontagne+numeroForeste) {
				numeroCarte = numeroPianure+numeroIsole+numeroPaludi+numeroMontagne+numeroForeste+magie.size();
			}
			else {
				numeroCarte = numeroTerre+magie.size();
			}
			
		}
		cerca(magie, carte, numeroTerre, numeroPianure, numeroIsole, numeroPaludi, numeroMontagne, numeroForeste, forza);
		return magie;
	}
	
		private void cerca(List<Carta> parziale, List<Carta> carte, int terre, int pianure, int isole, int paludi, int montagne, int foreste, double f) {
			if(parziale.size()+Math.max(numeroTerre, numeroPianure+numeroIsole+numeroPaludi+numeroMontagne+numeroForeste) >= 40) {
				if(forza > forzaMassima && parziale.size()+Math.max(numeroTerre, numeroPianure+numeroIsole+numeroPaludi+numeroMontagne+numeroForeste) == 40) {
					magie = new ArrayList<Carta>(parziale);
					forzaMassima = forza;
					numeroTerreOttimo = numeroTerre;
					numeroPianureOttimo = numeroPianure;
					numeroIsoleOttimo = numeroIsole;
					numeroPaludiOttimo = numeroPaludi;
					numeroMontagneOttimo = numeroMontagne;
					numeroForesteOttimo = numeroForeste;
					iterazioni = 0;
				}
				numeroTerre = terre;
				numeroPianure = pianure;
				numeroIsole = isole;
				numeroPaludi = paludi;
				numeroMontagne = montagne;
				numeroForeste = foreste;
				forza = f;
				return;
			}
			for(Carta c : carte) {
					parziale.add(c);
					double forzaUltima = forza;
					int numeroTerreUltima = numeroTerre;
					int numeroPianureUltima = numeroPianure;
					int numeroIsoleUltima = numeroIsole;
					int numeroPaludiUltima = numeroPaludi;
					int numeroMontagneUltima = numeroMontagne;
					int numeroForesteUltima = numeroForeste;
					forza = forza+c.getForza();
					if(numeroPianure < c.getTerreW()) numeroPianure = c.getTerreW();
					if(numeroIsole < c.getTerreU()) numeroIsole = c.getTerreU();
					if(numeroPaludi < c.getTerreB()) numeroPaludi = c.getTerreB();
					if(numeroMontagne < c.getTerreR()) numeroMontagne = c.getTerreR();
					if(numeroForeste < c.getTerreG()) numeroForeste = c.getTerreG();
					if(numeroTerre < c.getTerre()) numeroTerre = c.getTerre();
					if(numeroTerre <= numeroPianure+numeroIsole+numeroPaludi+numeroMontagne+numeroForeste) {
						numeroCarte = numeroPianure+numeroIsole+numeroPaludi+numeroMontagne+numeroForeste+magie.size();
					}
					else {
						numeroCarte = numeroTerre+magie.size();
					}
					
					List<Carta> cartemp = new ArrayList<Carta>(carte);
					cartemp.remove(c);
					iterazioni++;
					if(iterazioni>10000000) return;
					cerca(parziale, cartemp, terre, pianure, isole, paludi, montagne, foreste, f);
					parziale.remove(parziale.size()-1);
					forza = forzaUltima;
					numeroTerre = numeroTerreUltima;
					numeroPianure = numeroPianureUltima;
					numeroIsole = numeroIsoleUltima;
					numeroPaludi = numeroPaludiUltima;
					numeroMontagne = numeroMontagneUltima;
					numeroForeste = numeroForesteUltima;
			}
		}

		public List<Carta> getMagie() {
			return magie;
		}

		public void setMagie(List<Carta> magie) {
			this.magie = magie;
		}

		public double getForzaMassima() {
			return forzaMassima;
		}

		public void setForzaMassima(double forzaMassima) {
			this.forzaMassima = forzaMassima;
		}

		public int getNumeroTerreOttimo() {
			return numeroTerreOttimo;
		}

		public void setNumeroTerreOttimo(int numeroTerreOttimo) {
			this.numeroTerreOttimo = numeroTerreOttimo;
		}

		public int getNumeroPianureOttimo() {
			return numeroPianureOttimo;
		}

		public void setNumeroPianureOttimo(int numeroPianureOttimo) {
			this.numeroPianureOttimo = numeroPianureOttimo;
		}

		public int getNumeroIsoleOttimo() {
			return numeroIsoleOttimo;
		}

		public void setNumeroIsoleOttimo(int numeroIsoleOttimo) {
			this.numeroIsoleOttimo = numeroIsoleOttimo;
		}

		public int getNumeroPaludiOttimo() {
			return numeroPaludiOttimo;
		}

		public void setNumeroPaludiOttimo(int numeroPaludiOttimo) {
			this.numeroPaludiOttimo = numeroPaludiOttimo;
		}

		public int getNumeroMontagneOttimo() {
			return numeroMontagneOttimo;
		}

		public void setNumeroMontagneOttimo(int numeroMontagneOttimo) {
			this.numeroMontagneOttimo = numeroMontagneOttimo;
		}

		public int getNumeroForesteOttimo() {
			return numeroForesteOttimo;
		}

		public void setNumeroForesteOttimo(int numeroForesteOttimo) {
			this.numeroForesteOttimo = numeroForesteOttimo;
		}
	
}


