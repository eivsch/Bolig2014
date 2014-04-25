/*
 * INNHOLD:
 * Driverklasse for programmet (main-metode).
 *
 * Sist oppdatert: 27.03.2014, 11:32.
 * Programmert av: Eivind
 */

package boligformidleren;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *Klassen har til hensikt å kjøre programmet.
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
                        vindu.skrivUtleierTilFil();
                        System.exit(0);
                    }
                });
    }
    
}
