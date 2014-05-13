/*
 * Innhold:
 * Sist oppdatert:
 * Programmert av:
 */
package boligformidleren;

import java.util.Iterator;
import java.util.Set;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Eivind
 */
public class UtleierTabellmodell extends AbstractTableModel {

    private final String[] KOLONNENAVN = {"Navn", "Firma", "Epost", "Tlf",
        "skjul1", "skjul2"};
    private Object[][] celler;
    // Enkelte kolonner skal kun brukes for å lese inn data og skal dermed skjules
    // for brukeren. Bestemmer her hvilke.
    private final int SKALSKJULES[] = {4, 5};

    //Tegner tabell i konstruktør
    public UtleierTabellmodell() {
        Set s = StartVindu.getUtleierVindu().getUtleierMengde().getSortertMengde();
        Iterator<Utleier> iter = s.iterator();
        celler = new Object[s.size()][KOLONNENAVN.length];
        Utleier u;
        for (int rad = 0; rad < s.size(); rad++) {
            if (iter.hasNext()) {
                u = iter.next();
                celler[rad] = u.tilArray();
            }
        }
    }

    public String getColumnName(int kolonne) {
        return KOLONNENAVN[kolonne];
    }

    public Object getValueAt(int rad, int kolonne) {
        return celler[rad][kolonne];
    }

    public int getColumnCount() {
        return KOLONNENAVN.length;
    }

    public int getRowCount() {
        return celler.length;
    }

    public Class getColumnClass(int kolonne) {
        switch (kolonne) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Integer.class;
            default:
                return String.class;
        }
    }

    public int[] getKolonnerSkalSkjulesIndeks() {
        return SKALSKJULES;
    }

    public String[] getKolonnerSkalSkjules() {
        String[] skalSkjules = new String[SKALSKJULES.length];
        for (int i = 0; i < SKALSKJULES.length; i++) {
            skalSkjules[i] = KOLONNENAVN[SKALSKJULES[i]];
        }
        return skalSkjules;
    }
}
