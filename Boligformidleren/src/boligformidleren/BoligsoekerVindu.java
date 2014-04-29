/*
 * Innhold: Vindu som brukes for registrering, slettning, osv. av boligsøkere
 * Sist oppdatert:
 * Programmert av: Gretar
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BoligsoekerVindu extends JFrame implements ActionListener {

    private JTextField RegPersFornavn, RegPersEtternavn, adresse, epost, tlf, pInfo,
            kravPris, kravAreal, kravRom, kravByggeaar, kravEtasjer;
    private JComboBox kravType;
    private final String[] TYPE = {"Velg", "Enebolig/rekkehus", "Leilighet"};    
    private JCheckBox kravHeis, kravBalkong, kravKjeller;
    private JTextArea output;
    private JButton regBoligsoeker, slettBoligsoeker, visPerson, visPersonInfo, skrivUt;
    private int antRad, antKol, gap;
    private JPanel masterPanel, grid, under;

    private BoligsoekerMengde boligsoekerMengde;

    // konstruktør
    public BoligsoekerVindu() {
        super("Boligsøker");

        boligsoekerMengde = new BoligsoekerMengde();

        antRad = 17;
        antKol = 2;
        gap = 5;

        masterPanel = new JPanel(new BorderLayout());
        grid = new JPanel(new GridLayout(antRad, antKol, gap, gap));
        under = new JPanel(new BorderLayout());
        masterPanel.add(grid, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        this.getContentPane().add(masterPanel);
        setSize(350, 700);

        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // textfields
        grid.add(new JLabel("Fornavn: "));
        RegPersFornavn = new JTextField(10);
        grid.add(RegPersFornavn);

        grid.add(new JLabel("Etternavn: "));
        RegPersEtternavn = new JTextField(10);
        grid.add(RegPersEtternavn);

        grid.add(new JLabel("Adresse: "));
        adresse = new JTextField(10);
        grid.add(adresse);

        grid.add(new JLabel("E-post: "));
        epost = new JTextField(10);
        grid.add(epost);

        grid.add(new JLabel("Telefon: "));
        tlf = new JTextField(10);
        grid.add(tlf);

        grid.add(new JLabel("Personlige opplysninger: "));
        pInfo = new JTextField(10);
        grid.add(pInfo);

        grid.add(new JLabel("Boligtype: "));
        kravType = new JComboBox(TYPE);
        kravType.setSelectedIndex(0);
        grid.add(kravType);

        grid.add(new JLabel("Max. pris: "));
        kravPris = new JTextField(10);
        grid.add(kravPris);

        grid.add(new JLabel("Min. areal: "));
        kravAreal = new JTextField(10);
        grid.add(kravAreal);

        grid.add(new JLabel("Min. rom: "));
        kravRom = new JTextField(10);
        grid.add(kravRom);

        grid.add(new JLabel("Min. byggeår: "));
        kravByggeaar = new JTextField(10);
        grid.add(kravByggeaar);

        grid.add(new JLabel("Antall etasjer: "));
        kravEtasjer = new JTextField(10);
        grid.add(kravEtasjer);

        grid.add(new JLabel("Heis: "));
        kravHeis = new JCheckBox("");
        grid.add(kravHeis);

        grid.add(new JLabel("Balkong: "));
        kravBalkong = new JCheckBox("");
        grid.add(kravBalkong);

        grid.add(new JLabel("Kjeller: "));
        kravKjeller = new JCheckBox("");
        grid.add(kravKjeller);

        // buttons
        regBoligsoeker = new JButton("Register boligsøker");
        regBoligsoeker.addActionListener(this);
        grid.add(regBoligsoeker);

        slettBoligsoeker = new JButton("Slett boligsøker");
        slettBoligsoeker.addActionListener(this);
        grid.add(slettBoligsoeker);

        skrivUt = new JButton("Vis alle boligsøkere");
        skrivUt.addActionListener(this);
        grid.add(skrivUt);

        lesBoligsoekerFraFil();
    }// end constructor

    //registrer boligsøker
    public void regBoligsoeker() {

        String fnavn = RegPersFornavn.getText();
        String enavn = RegPersEtternavn.getText();
        String type = (String)kravType.getSelectedItem();

        if (boligsoekerMengde.finnBoligsoeker(fnavn, enavn) != null) {
            output.setText("Feil - Boligsøker allerede registrert!");
            return;
        }
        
        if( type.equals(TYPE[0]) ){
            output.setText("Feil - du må velge type!");
            return;
        }
            
        Boligsoeker b = new Boligsoeker(fnavn, enavn, adresse.getText(),
                epost.getText(), Integer.parseInt(tlf.getText()), pInfo.getText(),
                type, Integer.parseInt(kravPris.getText()),
                Integer.parseInt(kravAreal.getText()), Integer.parseInt(kravRom.getText()),
                Integer.parseInt(kravByggeaar.getText()), Integer.parseInt(kravEtasjer.getText()),
                kravHeis.isSelected(), kravBalkong.isSelected(), kravKjeller.isSelected());

        boligsoekerMengde.settInn(b);
        output.setText("Boligsøker " + fnavn + " " + enavn + " registrert");
        RegPersFornavn.setText("");
        RegPersEtternavn.setText("");
        adresse.setText("");
        epost.setText("");
        tlf.setText("");
        pInfo.setText("");
        kravType.setSelectedIndex(0);
        kravPris.setText("");
        kravAreal.setText("");
        kravRom.setText("");
        kravByggeaar.setText("");
        kravEtasjer.setText("");
        kravHeis.setSelected(false);
        kravBalkong.setSelected(false);
        kravKjeller.setSelected(false);
    }
    
    public BoligsoekerMengde getBoligsoekerMengde(){
        return boligsoekerMengde;
    }

    public void slettBoligsoeker() {
        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();
        Boligsoeker bs = boligsoekerMengde.finnBoligsoeker(fornavn, etternavn);
        if (bs == null) {
            output.setText("Boligsøker " + fornavn + " " + etternavn + " ble ikke funnet, kontroller skrivefeil.");
            return;
        }
        // try-catch ?
        if (boligsoekerMengde.fjern(bs)) {
            output.setText(fornavn + " " + etternavn + " slettet");
        }
    }

    public void skrivBoligsoekerTilFil() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(
                new FileOutputStream("boligsoekermengde.data"))) {
            utfil.writeObject(boligsoekerMengde.kopierMengdeUsortert());
        } catch (NotSerializableException nse) {
            visFeilmelding(nse);
        } catch (IOException e) {
            visFeilmelding(e);
        }
    }

    public void lesBoligsoekerFraFil() {
        Set<Boligsoeker> innlestBoligsoekere = new TreeSet<>();
        try (ObjectInputStream innfil = new ObjectInputStream(
                new FileInputStream("boligsoekermengde.data"))) {
            innlestBoligsoekere = (TreeSet<Boligsoeker>) innfil.readObject();
            Iterator<Boligsoeker> iter = innlestBoligsoekere.iterator();
            while (iter.hasNext()) {
                boligsoekerMengde.settInn(iter.next());
            }
        } catch (ClassNotFoundException cnfe) {
            visFeilmelding(cnfe);
        } catch (FileNotFoundException fnfe) {
            visFeilmelding(fnfe);
        } catch (IOException e) {
            visFeilmelding(e);
        }
    }

    public void visFeilmelding(StackTraceElement[] ste) {
        JOptionPane.showMessageDialog(this, ste);
    }

    public void visFeilmelding(Object o) {
        JOptionPane.showMessageDialog(this, o);
    }

    public void utskrift() {
        /**
         * Få skrevet ut alt som er registrert til et tekstfelt. (Kall på
         * person- mengde, kontraktliste etc .toString)
         */
        output.setText(boligsoekerMengde.toString() + "\n");
    }

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regBoligsoeker) {
            regBoligsoeker();
        } else if (e.getSource() == skrivUt) {
            utskrift();
        } else if (e.getSource() == slettBoligsoeker) {
            slettBoligsoeker();
        }
    }

}
