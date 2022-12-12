package it.polito.tdp.mtg_cards.model;

public class Carta {

	private int manaValue;
	private String manaCost;
	private String nome;
	private String rarita;
	private int w;
	private int u;
	private int b;
	private int r;
	private int g;
	private int terre;
	private int terreW;
	private int terreU;
	private int terreB;
	private int terreR;
	private int terreG;
	private double forza;
	private double soglia = 0.8;
	
	private long binomiale(int n, int k) {
		if(k>n-k) {
			k=n-k;
		}
		long b=1;
		for(int i=1, m=n; i<=k; i++, m--) {
			b=b*m/i;
		}
		return b;
	}
	
	private double ipergeometrica(int x, int carteMazzo, int terreMazzo, int cartePescate) {		
		return (double) this.binomiale(terreMazzo, x) * (double) this.binomiale(carteMazzo-terreMazzo, cartePescate-x)/ (double) this.binomiale(carteMazzo, cartePescate);
	}
	
	public int terreRichieste(Carta c) {
		int terreMazzo = c.getManaValue();
		int i = 0;
		double p = 0;
		while(p<soglia) {
			terreMazzo++;
			i = 0;
			p = 1;
			while(i<c.getManaValue()) {
				p = p-this.ipergeometrica(i, 40, terreMazzo, 7+c.getManaValue());
				i++;
			}
		}
		return terreMazzo;
	}
	
	public int coloriRichiesti(Carta c, int col) {
		if(col==0) {
			return 0;
		}
		int coloriMazzo = col;
		int i = 0;
		double p = 0;
		while(p<soglia) {
			coloriMazzo++;
			i = 0;
			p = 1;
			while(i<col) {
				p = p-this.ipergeometrica(i, 40, coloriMazzo, 7+c.getManaValue());
				i++;
			}
		}
		return coloriMazzo;
	}

	public Carta(int manaValue, String manaCost, String nome, String rarita) {
		this.manaValue = manaValue;
		this.manaCost = manaCost;
		this.nome = nome;
		this.rarita = rarita;
		this.w = 0;
		this.u = 0;
		this.b = 0;
		this.r = 0;
		this.g = 0;
		if(this.manaCost != null) {
			for(int i=0;i<this.manaCost.length();i++) {
				if(this.manaCost.charAt(i)=='W') {
					this.w++;
				}
				if(this.manaCost.charAt(i)=='U') {
					this.u++;
				}
				if(this.manaCost.charAt(i)=='B') {
					this.b++;
				}
				if(this.manaCost.charAt(i)=='R') {
					this.r++;
				}
				if(this.manaCost.charAt(i)=='G') {
					this.g++;
				}
			}
		}
		if(this.rarita.equals("common")) {
			forza = this.manaValue;
		}
		else if(this.rarita.equals("uncommon")) {
			forza = this.manaValue*1.5;
		}
		else if(this.rarita.equals("rare")) {
			forza = this.manaValue*2;
		}
		else if(this.rarita.equals("mythic")) {
			forza = this.manaValue*2.5;
		}
		
		this.terre = this.terreRichieste(this);
		this.terreW = this.coloriRichiesti(this, this.w);
		this.terreU = this.coloriRichiesti(this, this.u);
		this.terreB = this.coloriRichiesti(this, this.b);
		this.terreR = this.coloriRichiesti(this, this.r);
		this.terreG = this.coloriRichiesti(this, this.g);
	}

	public int getManaValue() {
		return manaValue;
	}

	public void setManaValue(int manaValue) {
		this.manaValue = manaValue;
	}

	public String getManaCost() {
		return manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRarita() {
		return rarita;
	}

	public void setRarita(String rarita) {
		this.rarita = rarita;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getU() {
		return u;
	}

	public void setU(int u) {
		this.u = u;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public double getForza() {
		return forza;
	}

	public void setForza(double forza) {
		this.forza = forza;
	}

	public int getTerre() {
		return terre;
	}

	public void setTerre(int terre) {
		this.terre = terre;
	}

	public int getTerreW() {
		return terreW;
	}

	public void setTerreW(int terreW) {
		this.terreW = terreW;
	}

	public int getTerreU() {
		return terreU;
	}

	public void setTerreU(int terreU) {
		this.terreU = terreU;
	}

	public int getTerreB() {
		return terreB;
	}

	public void setTerreB(int terreB) {
		this.terreB = terreB;
	}

	public int getTerreR() {
		return terreR;
	}

	public void setTerreR(int terreR) {
		this.terreR = terreR;
	}

	public int getTerreG() {
		return terreG;
	}

	public void setTerreG(int terreG) {
		this.terreG = terreG;
	}

	@Override
	public String toString() {
		return Double.toString(forza) + "   " + nome;
	}
	
}
