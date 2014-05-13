/*
 * Innhold:
 * Sist oppdatert:
 * Programmert av:
 */
package boligformidleren;

import java.util.Iterator;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Eivind
 */
public class BoligTabellmodell extends AbstractTableModel {

    private final String[] KOLONNENAVN = {"Adresse", "Poststed", "Postnummer",
        "Pris", "Areal", "Byggeår", "Avertert fra"};
    private Object[][] celler;

    //Tegner tabell i konstruktør
    public BoligTabellmodell() {
        int maxAntRader = StartVindu.getUtleierVindu().getUtleierMengde().antallBoliger();
        celler = new Object[maxAntRader][KOLONNENAVN.length];
        Iterator<Utleier> utleierIter = StartVindu.getUtleierVindu().getUtleierMengde().getSortertMengde().iterator();
        int radTeller = 0;

        while (utleierIter.hasNext()) {
            Utleier u = utleierIter.next();
            Iterator<Bolig> boligIter = u.getBoligliste().getListe().iterator();
            for (int rad = radTeller; rad < maxAntRader; rad++) {
                if (boligIter.hasNext()) {
                    Bolig b = boligIter.next();
                    celler[rad] = b.tilArray();
                    radTeller++;
                }
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

    public Class getColumnClass(int column) {
        switch (column) {
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
                return Integer.class;
            case 6:
                return Integer.class;
            default:
                return String.class;
        }
    }
}
