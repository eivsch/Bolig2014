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
public class BoligsoekerTabellmodell extends AbstractTableModel {

    private final String[] KOLONNENAVN = {"Navn", "Sted", "Pris", "Areal",
        "Soverom", "Boligtype", "Fra Dato"};
    private Object[][] celler;

    //Tegner tabell i konstruktør
    public BoligsoekerTabellmodell() {
        Set s = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().getMengde();
        Iterator<Boligsoeker> iter = s.iterator();
        celler = new Object[s.size()][KOLONNENAVN.length];
        Boligsoeker b;
        for (int rad = 0; rad < s.size(); rad++) {
            if (iter.hasNext()) {
                b = iter.next();
                celler[rad] = b.tilArray();
            }
        }
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
                return Integer.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;
            case 5:
                return String.class;
            default:
                return String.class;
        }
    }  
}
