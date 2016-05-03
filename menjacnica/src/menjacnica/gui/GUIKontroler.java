package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	private static MenjacnicaGUI menjacnicaGUI;
	// klasa na logickom nivou
	protected static Menjacnica menjacnica;

	private static DodajKursGUI dodajKursGUI;
	private static IzvrsiZamenuGUI izvrsiZamenuGUI;
	private static ObrisiKursGUI obrisiKursGUI;

	private static Valuta valuta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// valuta = new Valuta();
					menjacnica = new Menjacnica();
					menjacnicaGUI = new MenjacnicaGUI();
					menjacnicaGUI.setLocationRelativeTo(null);
					menjacnicaGUI.setVisible(true);
					// izvrsiZamenuGUI = new IzvrsiZamenuGUI();
					// obrisiKursGUI = new ObrisiKursGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * MenjacnicaGUI
	 */

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGUI.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak", JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void prikaziAboutProzor() {
		JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), "Autor: Bojan Tomic, Verzija 1.0",
				"O programu Menjacnica", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaGUI.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), e1.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnicaGUI.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				osveziGlavniProzor();
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), e1.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static LinkedList<Valuta> vratiKursnuListu() {
		return menjacnica.vratiKursnuListu();
	}

	public static void prikaziDodajKursGUI() {
		dodajKursGUI = new DodajKursGUI();
		dodajKursGUI.setLocationRelativeTo(menjacnicaGUI.getContentPane());
		dodajKursGUI.setVisible(true);
	}

	public static void prikaziObrisiKursGUI(JTable table) {
		MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());

		if (table.getSelectedRow() != -1) {
			GUIKontroler.valuta = model.vratiValutu(table.getSelectedRow());
			obrisiKursGUI = new ObrisiKursGUI();
			obrisiKursGUI.setLocationRelativeTo(menjacnicaGUI.getContentPane());
			obrisiKursGUI.setVisible(true);

		} else {
			GUIKontroler.porukaGreskeBiranjeRedaZaBrisanje();
		}
	}

	public static void prikaziIzvrsiZamenuGUI(JTable table) {
		MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());

		if (table.getSelectedRow() != -1) {
			GUIKontroler.valuta = model.vratiValutu(table.getSelectedRow());
			izvrsiZamenuGUI = new IzvrsiZamenuGUI();
			izvrsiZamenuGUI.setLocationRelativeTo(menjacnicaGUI.getContentPane());
			izvrsiZamenuGUI.setVisible(true);

		} else {
			GUIKontroler.porukaGreskeBiranjeRedaZaZamenu();
		}
	}

	/*
	 * DodajKursGUI
	 */

	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, String prodajniKurs, String kupovniKurs,
			String srednjiKurs) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra((Integer) (sifra));
			valuta.setProdajni(Double.parseDouble(prodajniKurs));
			valuta.setKupovni(Double.parseDouble(kupovniKurs));
			valuta.setSrednji(Double.parseDouble(srednjiKurs));

			// Dodavanje valute u kursnu listu
			GUIKontroler.menjacnica.dodajValutu(valuta);

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(dodajKursGUI.getContentPane(), e1.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void osveziGlavniProzor() {
		// Osvezavanje glavnog prozora
		menjacnicaGUI.prikaziSveValute();
	}
	/*
	 * IzvrsiZamenuGUI
	 */

	public static double izvrsiZamenu(boolean isSelected, String iznos) {
		try {
			return GUIKontroler.menjacnica.izvrsiTransakciju(valuta, isSelected, Double.parseDouble(iznos));

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(izvrsiZamenuGUI.getContentPane(), e1.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
		return Double.MIN_VALUE;
	}

	public static Valuta vratiValutu() {
		return GUIKontroler.valuta;
	}

	public static void porukaGreskeBiranjeRedaZaZamenu() {
		JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), "Izaberite kurs za zamenu!", "Greska",
				JOptionPane.ERROR_MESSAGE);
	}

	/*
	 * ObrisiKursGUI
	 */
	public static void obrisiValutu() {
		try {
			GUIKontroler.menjacnica.obrisiValutu(valuta);

			menjacnicaGUI.prikaziSveValute();
			obrisiKursGUI.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(obrisiKursGUI.getContentPane(), e1.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void porukaGreskeBiranjeRedaZaBrisanje() {
		JOptionPane.showMessageDialog(menjacnicaGUI.getContentPane(), "Izaberite kurs za brisanje!", "Greska",
				JOptionPane.ERROR_MESSAGE);
	}

}
