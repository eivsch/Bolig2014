/*
 * Innhold: Vindu som brukes for registrering av enebolig/rekkehus
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

public class UtleierVindu extends JFrame implements ActionListener {

    private JTextField RegPersFornavn, RegPersEtternavn, RegPersAdr, RegEpost, RegTlf, RegFirma, BoligKnyttetTil;
    private JTextArea output;
    private JButton regUtleier, slettUtleier, visPerson, visPersonInfo, skrivUt;
    private int antRad, antKol, gap;
    private JPanel masterPanel, grid, under;

    private UtleierMengde utleierMengde;

    // konstruktør
    public UtleierVindu() {
        super("Utleier");

        utleierMengde = new UtleierMengde();

        antRad = 9;
        antKol = 2;
        gap = 5;

        masterPanel = new JPanel(new BorderLayout());
        grid = new JPanel(new GridLayout(antRad, antKol, gap, gap));
        under = new JPanel(new BorderLayout());
        masterPanel.add(grid, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        this.getContentPane().add(masterPanel);
        setSize(300, 500);

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
        RegPersAdr = new JTextField(10);
        grid.add(RegPersAdr);

        grid.add(new JLabel("E-post: "));
        RegEpost = new JTextField(10);
        grid.add(RegEpost);

        grid.add(new JLabel("Telefon: "));
        RegTlf = new JTextField(10);
        grid.add(RegTlf);

        grid.add(new JLabel("Firma: "));
        RegFirma = new JTextField(10);
        grid.add(RegFirma);

        grid.add(new JLabel("Bolig: "));
        BoligKnyttetTil = new JTextField(10);
        grid.add(BoligKnyttetTil);

        // buttons
        regUtleier = new JButton("Register utleier");
        regUtleier.addActionListener(this);
        grid.add(regUtleier);

        slettUtleier = new JButton("Slett utleier");
        slettUtleier.addActionListener(this);
        grid.add(slettUtleier);

        skrivUt = new JButton("Vis alle utleiere");
        skrivUt.addActionListener(this);
        grid.add(skrivUt);

        lesUtleierFraFil();
    }

    public UtleierMengde getUtleierMengde() {
        return utleierMengde;
    }

    //registrer utleier
    public void regUtleier() {

        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();

        if (utleierMengde.finnUtleier(fornavn, etternavn) != null) {
            output.setText("Feil - Utleier allerede registrert!");
            return;
        }

        Utleier u = new Utleier(RegPersFornavn.getText(), RegPersEtternavn.getText(),
                RegPersAdr.getText(), RegEpost.getText(), Integer.parseInt(RegTlf.getText()), RegFirma.getText());

        utleierMengde.settInn(u);
        output.setText("Utleier " + fornavn + " " + etternavn + " registrert");
        blankFelter();
    }

    public void slettUtleier() {
        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();
        Utleier ul = utleierMengde.finnUtleier(fornavn, etternavn);
        if (ul == null) {
            output.setText("Utleier " + fornavn + " " + etternavn + " ble ikke funnet, kontroller skrivefeil.");
            return;
        }
        // try-catch ?
        if (utleierMengde.fjern(ul)) {
            output.setText("Utleier " + fornavn + " " + etternavn + " slettet");
        }
        blankFelter();
    }
        
    public void blankFelter() {
        RegPersFornavn.setText("");
        RegPersEtternavn.setText("");
        RegPersAdr.setText("");
        RegEpost.setText("");
        RegTlf.setText("");
        RegFirma.setText("");
        BoligKnyttetTil.setText("");
    }

    public void utskrift() {
        /**
         * Få skrevet ut alt som er registrert til et tekstfelt. (Kall på
         * person- mengde, kontraktliste etc .toString)
         */
        output.setText(utleierMengde.toString() + "\n");
    }

    public void skrivUtleierTilFil() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(
                new FileOutputStream("utleiermengde.data"))) {
            utfil.writeObject(utleierMengde.kopierMengdeUsortert());
        } catch (NotSerializableException nse) {
            visFeilmelding(nse);
        } catch (IOException e) {
            visFeilmelding(e);
        }
    }

    public void lesUtleierFraFil() {
        Set<Utleier> innlestUtleiere = new TreeSet<>();
        try (ObjectInputStream innfil = new ObjectInputStream(
                new FileInputStream("utleiermengde.data"))) {
            innlestUtleiere = (TreeSet<Utleier>) innfil.readObject();
            Iterator<Utleier> iter = innlestUtleiere.iterator();
            while (iter.hasNext()) {
                utleierMengde.settInn(iter.next());
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

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regUtleier) {
            regUtleier();
        } else if (e.getSource() == skrivUt) {
            utskrift();
        } else if (e.getSource() == slettUtleier) {
            slettUtleier();
        }
    }

}
