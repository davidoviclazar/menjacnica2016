package menjacnica;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class Menjacnica implements MenjacnicaInterface {

	private LinkedList<Valuta> kursnaLista = new LinkedList<Valuta>();

	@Override
	public void dodajValutu(Valuta valuta) {
		if (valuta == null)
			throw new RuntimeException("Valuta ne sme biti null");

		if (kursnaLista.contains(valuta))
			throw new RuntimeException("Valuta je vec uneta u kursnu listu");

		kursnaLista.add(valuta);
	}

	@Override
	public void obrisiValutu(Valuta valuta) {
		if (!kursnaLista.contains(valuta))
			throw new RuntimeException("Valuta ne postoji u kursnoj listi");

		kursnaLista.remove(valuta);
	}

	@Override
	public double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos) {
		if (prodaja)
			return iznos * valuta.getProdajni();
		else
			return iznos * valuta.getKupovni();
	}

	@Override
	public LinkedList<Valuta> vratiKursnuListu() {
		return kursnaLista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void ucitajIzFajla(String putanja) {
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(putanja)));

			kursnaLista = (LinkedList<Valuta>) (in.readObject());

			in.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void sacuvajUFajl(String putanja) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(putanja)));

			out.writeObject(kursnaLista);

			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
