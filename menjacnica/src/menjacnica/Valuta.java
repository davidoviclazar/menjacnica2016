package menjacnica;

import java.io.Serializable;

public class Valuta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sifra;
	private String skraceniNaziv;
	private String naziv;
	private double kupovni, srednji, prodajni;

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public String getSkraceniNaziv() {
		return skraceniNaziv;
	}

	public void setSkraceniNaziv(String skraceniNaziv) {
		this.skraceniNaziv = skraceniNaziv;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getKupovni() {
		return kupovni;
	}

	public void setKupovni(double kupovni) {
		this.kupovni = kupovni;
	}

	public double getSrednji() {
		return srednji;
	}

	public void setSrednji(double srednji) {
		this.srednji = srednji;
	}

	public double getProdajni() {
		return prodajni;
	}

	public void setProdajni(double prodajni) {
		this.prodajni = prodajni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sifra;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Valuta other = (Valuta) obj;
		if (sifra != other.sifra)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[sifra=" + sifra + ", skraceniNaziv=" + skraceniNaziv + ", kupovni=" + kupovni + ", srednji=" + srednji
				+ ", prodajni=" + prodajni + "]";
	}

}
