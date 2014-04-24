/*
 * Innhold: Vindu som brukes for registrering av enebolig/rekkehus
 * Sist oppdatert:
 * Programmert av: Gretar
 */

package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoligsoekerVindu extends JFrame implements ActionListener{
    
    private JTextField fornavn, etternavn, adresse, epost, tlf, pInfo, kravType, 
                        kravPris, kravAreal, kravRom, kravByggeaar, kravEtasjer;
    private JCheckBox kravHeis, kravBalkong, kravKjeller;
    private JTextArea output;
    private JButton regBoligsoeker, slettBoligsoeker, visPerson, visPersonInfo, skrivUt;
    private int antRad, antKol, gap;
    private JPanel masterPanel, c, under;
    
    private BoligsoekerMengde boligsoekerMengde;
    
    // konstruktør
    public BoligsoekerVindu(){
        super("Boligsøker");
        
        boligsoekerMengde = new BoligsoekerMengde();
        
        antRad = 17;
        antKol = 2;
        gap = 5;
        
        masterPanel = new JPanel( new BorderLayout() );
        c = new JPanel( new GridLayout(antRad, antKol, gap, gap) );
        under = new JPanel( new BorderLayout() );
        masterPanel.add(c, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        this.getContentPane().add(masterPanel);
        setSize(350, 700);
        
        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);
        
        // textfields
        c.add(new JLabel("Fornavn: "));
        fornavn = new JTextField(10);
        c.add(fornavn);

        c.add(new JLabel("Etternavn: "));
        etternavn = new JTextField(10);
        c.add(etternavn);
        
        c.add(new JLabel("Adresse: "));
        adresse = new JTextField(10);
        c.add(adresse);
        
        c.add(new JLabel("E-post: "));
        epost = new JTextField(10);
        c.add(epost);

        c.add(new JLabel("Telefon: "));
        tlf = new JTextField(10);
        c.add(tlf);

        c.add(new JLabel("Personlige opplysninger: "));
        pInfo = new JTextField(10);
        c.add(pInfo);
        
        c.add(new JLabel("Boligtype: "));
        kravType = new JTextField(10);
        c.add(kravType);
        
        c.add(new JLabel("Max pris: "));
        kravPris = new JTextField(10);
        c.add(kravPris);
        
        c.add(new JLabel("Min areal: "));
        kravAreal = new JTextField(10);
        c.add(kravAreal);
        
        c.add(new JLabel("Min rom: "));
        kravRom = new JTextField(10);
        c.add(kravRom);
        
        c.add(new JLabel("Min byggeår: "));
        kravByggeaar = new JTextField(10);
        c.add(kravByggeaar);
        
        c.add(new JLabel("Antall etasjer: "));
        kravEtasjer = new JTextField(10);
        c.add(kravEtasjer);
        
        c.add(new JLabel("Heis: "));
        kravHeis = new JCheckBox("");
        c.add(kravHeis);
        
        c.add(new JLabel("Balkong: "));
        kravBalkong = new JCheckBox("");
        c.add(kravBalkong);
        
        c.add(new JLabel("Kjeller: "));
        kravKjeller = new JCheckBox("");
        c.add(kravKjeller);
        
        // buttons
        regBoligsoeker = new JButton("Register boligsøker");
        regBoligsoeker.addActionListener(this);
        c.add(regBoligsoeker);
        
        slettBoligsoeker = new JButton("Slett boligsøker");
        slettBoligsoeker.addActionListener(this);
        c.add(slettBoligsoeker);
        
        skrivUt = new JButton("Vis alle boligsøkere");
        skrivUt.addActionListener(this);
        c.add(skrivUt);
    }
    
    //registrer boligsøker
    public void regBoligsoeker(){
        
        String fnavn = fornavn.getText();
        String enavn = etternavn.getText();
        
        if( boligsoekerMengde.finnBoligsoeker(fnavn, enavn) != null ){
            output.setText("Feil - Boligsøker allerede registrert!");
            return;
        }
        
        Boligsoeker b = new Boligsoeker(fnavn, enavn, adresse.getText(),
                                        epost.getText(),Integer.parseInt(tlf.getText()),pInfo.getText(),
                                        kravType.getText(), Integer.parseInt(kravPris.getText()), 
                                        Integer.parseInt(kravAreal.getText()), Integer.parseInt(kravRom.getText()),
                                        Integer.parseInt(kravByggeaar.getText()), Integer.parseInt(kravEtasjer.getText()),
                                        kravHeis.isSelected(), kravBalkong.isSelected(), kravKjeller.isSelected());
        
        boligsoekerMengde.settInn(b);
        output.setText("Boligsøker " + fnavn + " " + enavn + " registrert");
        fornavn.setText("");
        etternavn.setText("");
        adresse.setText("");
        epost.setText("");
        tlf.setText("");
        pInfo.setText("");
        kravType.setText("");
        kravPris.setText("");
        kravAreal.setText("");
        kravRom.setText("");
        kravByggeaar.setText("");
        kravEtasjer.setText("");
        kravHeis.setSelected(false);
        kravBalkong.setSelected(false);
        kravKjeller.setSelected(false);
    }
    
    // her kommer slettBoligsoeker-metode
    
    public void utskrift() {
        /**
         * Få skrevet ut alt som er registrert til et tekstfelt. (Kall på
         * person- mengde, kontraktliste etc .toString)
         */
        output.setText( boligsoekerMengde.toString() + "\n" );
    }
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== regBoligsoeker){
            regBoligsoeker();
        }
        else if (e.getSource() == skrivUt) {
            utskrift();
        }
    }
    
}
