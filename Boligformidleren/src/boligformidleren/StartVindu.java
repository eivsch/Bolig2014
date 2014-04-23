/*
 * Innhold: Vindu som åpnes når programmet er kjørt
 * Sist oppdatert:
 * Programmert av: Gretar
 */

package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartVindu extends JFrame implements ActionListener{
    
    private final String vinduer[] = {"Utleier", "Boligsøker", "Enebolig/rekkehus", "Leilighet", "Kontrakt", "Matche"};
    private JButton buttons[];
    private UtleierVindu utleierVindu;
    private int antKnapper, antRad, antKol, gap;
    // opprette evt. andre vinduer
    
    public StartVindu(){
        super( "Boligformidleren" );
        utleierVindu = new UtleierVindu();
        antKnapper = vinduer.length;
        antRad = (int)( Math.sqrt(antKnapper) );
        antKol = antKnapper - antRad;
        gap = 5;
        Container c = getContentPane();
        c.setLayout(new GridLayout(antRad, antKol, gap, gap));
        buttons = new JButton[ antKnapper ];
        
        for( int i = 0; i < antKnapper; i++){
            buttons[i] = new JButton( vinduer[i] );
            buttons[i].addActionListener(this);
            c.add( buttons[i] );
        }
        
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]){
            utleierVindu.setVisible(true);
        }
    }
    
}
