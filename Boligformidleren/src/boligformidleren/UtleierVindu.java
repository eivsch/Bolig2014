/*
 * Innhold: Vindu som brukes for registrering av enebolig/rekkehus
 * Sist oppdatert:
 * Programmert av: Gretar
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UtleierVindu extends JFrame implements ActionListener {

    private JTextField RegPersFornavn, RegPersEtternavn, RegPersAdr, RegEpost, RegTlf, RegFirma, BoligKnyttetTil;
    private JTextArea output;
    private JButton regUtleier, slettUtleier, visPerson, visPersonInfo, skrivUt;
    private int antRad, antKol, gap;
    private JPanel masterPanel, c, under;

    private UtleierMengde utleierMengde;
    
    // konstruktør
    public UtleierVindu(){
        super("Utleier");

        utleierMengde = new UtleierMengde();
        
        antRad = 9;
        antKol = 2;
        gap = 5;

        masterPanel = new JPanel(new BorderLayout());
        c = new JPanel(new GridLayout(antRad, antKol, gap, gap));
        under = new JPanel(new BorderLayout());
        masterPanel.add(c, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        this.getContentPane().add(masterPanel);
        setSize(300, 500);

        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // textfields
        c.add(new JLabel("Fornavn: "));
        RegPersFornavn = new JTextField(10);
        c.add(RegPersFornavn);

        c.add(new JLabel("Etternavn: "));
        RegPersEtternavn = new JTextField(10);
        c.add(RegPersEtternavn);

        c.add(new JLabel("Adresse: "));
        RegPersAdr = new JTextField(10);
        c.add(RegPersAdr);

        c.add(new JLabel("E-post: "));
        RegEpost = new JTextField(10);
        c.add(RegEpost);

        c.add(new JLabel("Telefon: "));
        RegTlf = new JTextField(10);
        c.add(RegTlf);

        c.add(new JLabel("Firma: "));
        RegFirma = new JTextField(10);
        c.add(RegFirma);

        c.add(new JLabel("Bolig: "));
        BoligKnyttetTil = new JTextField(10);
        c.add(BoligKnyttetTil);

        // buttons
        regUtleier = new JButton("Register utleier");
        regUtleier.addActionListener(this);
        c.add(regUtleier);

        slettUtleier = new JButton("Slett utleier");
        slettUtleier.addActionListener(this);
        c.add(slettUtleier);
        
        skrivUt = new JButton("Vis alle utleiere");
        skrivUt.addActionListener(this);
        c.add(skrivUt);
    }
    
    //registrer utleier
    public void regUtleier(){
        
        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();
        
        if( utleierMengde.finnUtleier(fornavn, etternavn) != null ){
            output.setText("Feil - Utleier allerede registrert!");
            return;
        }
        
        Utleier u = new Utleier(RegPersFornavn.getText(),RegPersEtternavn.getText(),
                RegPersAdr.getText(),RegEpost.getText(),Integer.parseInt(RegTlf.getText()),RegFirma.getText());

        utleierMengde.settInn(u);
        output.setText("Utleier " + fornavn + " " + etternavn + " registrert");
        RegPersFornavn.setText("");
        RegPersEtternavn.setText("");
        RegPersAdr.setText("");
        RegEpost.setText("");
        RegTlf.setText("");
        RegFirma.setText("");
        BoligKnyttetTil.setText("");
    }
    
    public boolean slettUtleier(){
       Utleier ul = utleierMengde.finnUtleier(RegPersFornavn.getText(), RegPersEtternavn.getText());
       return (utleierMengde.fjern(ul));
    }

    public void utskrift() {
        /**
         * Få skrevet ut alt som er registrert til et tekstfelt. (Kall på
         * person- mengde, kontraktliste etc .toString)
         */
        output.setText( utleierMengde.toString() + "\n" );
    }
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regUtleier) {
            regUtleier();
        } else if (e.getSource() == skrivUt) {
            utskrift();
        }
    }

}
