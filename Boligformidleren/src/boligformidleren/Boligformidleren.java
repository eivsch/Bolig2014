/*
 * INNHOLD:
 * Main-metode.
 *
 * Sist oppdatert: 13.05.2014, 11:32.
 * Programmert av: Eivind
 */

package boligformidleren;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klassen skal kjøre og avslutte programmet, i tilegg til å skrive data til fil
 * ved lukking.
 */
public class Boligformidleren {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final StartVindu vindu = new StartVindu();
        vindu.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        vindu.skrivTilFil();
                        System.exit(0);
                    }
                });
    }
    
}
