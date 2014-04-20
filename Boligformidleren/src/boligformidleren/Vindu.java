
/*
 Laget av Sigurd, Eivind.
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Vindu extends JFrame implements ActionListener {

    private JTextField RegBolAdr, RegBolType, RegAreal, AntRom, ByggAar, Beskrivelse, UtleiePris, AvetertDato, AntEtasjer, Kjeller, TomtStorrelse, Etasje, Heis, Balkong, Andre, MinAreal, MaxAreal, MinPris, MaxPris, RegPersFornavn, RegPersEtternavn, RegPersAdr, RegEpost, RegTlf, RegFirma, BoligKnyttetTil, RegPersOpplysning, RegKravBolig, RegKravPris, RegUtleieBolig, RegUtleier, RegLeietaker, RegPris, RegTid;
    private JTextArea output;
    private JButton regBolig, slettBolig, regBoligSoeker, regUtleier, slettPerson, regKontrakt, visBolig, visPerson, visPersonInfo, visBoligInfo, visIntrPers, visKontrakt, skrivUt;
    private UtleierMengde utleierMengde;
    private BoligsoekerMengde boligsoekerMengde;

    public Vindu() {
        //for boliger
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        c.add(new JLabel("Adresse: "));
        RegBolAdr = new JTextField(10);
        c.add(RegBolAdr);

        c.add(new JLabel("Type: "));
        RegBolType = new JTextField(10);
        c.add(RegBolType);

        c.add(new JLabel("Areal: "));
        RegAreal = new JTextField(10);
        c.add(RegAreal);

        c.add(new JLabel("Antall Rom: "));
        AntRom = new JTextField(10);
        c.add(AntRom);

        c.add(new JLabel("Byggeår: "));
        ByggAar = new JTextField(10);
        c.add(ByggAar);

        c.add(new JLabel("Kort beskrivelse: "));
        Beskrivelse = new JTextField(20);
        c.add(Beskrivelse);
/*   Ikke i bruk
        c.add(new JLabel("Utleiepris pr. Måned: "));
        UtleiePris = new JTextField(10);
        c.add(UtleiePris);
*/
        c.add(new JLabel("Dato avetert: "));
        AvetertDato = new JTextField(10);
        c.add(AvetertDato);

        c.add(new JLabel("Antall etasjer: "));
        AntEtasjer = new JTextField(10);
        c.add(AntEtasjer);
/*   Bedre løsning?
        c.add(new JLabel("Kjeller: "));
        Kjeller = new JTextField(10);
        c.add(Kjeller);
*/
        c.add(new JLabel("Størrelse på tomt: "));
        TomtStorrelse = new JTextField(10);
        c.add(TomtStorrelse);

        c.add(new JLabel("Hvilken etasje: "));
        Etasje = new JTextField(10);
        c.add(Etasje);
/*   Bedre løsning?
        c.add(new JLabel("Heis: "));
        Heis = new JTextField(10);
        c.add(Heis);

        c.add(new JLabel("Balkong: "));
        Balkong = new JTextField(10);
        c.add(Balkong);
*/
        c.add(new JLabel("Andre opplysninger: "));
        Andre = new JTextField(10);
        c.add(Andre);
/*   Ikke i bruk
        c.add(new JLabel("Min Areal: "));
        MinAreal = new JTextField(10);
        c.add(MinAreal);

        c.add(new JLabel("Max Areal: "));
        MaxAreal = new JTextField(10);
        c.add(MaxAreal);

        c.add(new JLabel("Min Pris: "));
        MinPris = new JTextField(10);
        c.add(MinPris);

        c.add(new JLabel("Max Pris: "));
        MaxPris = new JTextField(10);
        c.add(MaxPris);
*/
        //for Personer
        c.add(new JLabel("Fornavn: "));
        RegPersFornavn = new JTextField(10);
        c.add(RegPersFornavn);

        c.add(new JLabel("Etternavn: "));
        RegPersEtternavn = new JTextField(10);
        c.add(RegPersEtternavn);
/*   Dobbel? uansett ikke i bruk
        c.add(new JLabel("Adresse: "));
        RegPersAdr = new JTextField(10);
        c.add(RegPersAdr);
*/
        c.add(new JLabel("E-post: "));
        RegEpost = new JTextField(10);
        c.add(RegEpost);

        c.add(new JLabel("Telefon: "));
        RegTlf = new JTextField(10);
        c.add(RegTlf);

        //for utleiere
        c.add(new JLabel("Firma: "));
        RegFirma = new JTextField(10);
        c.add(RegFirma);
/*   Ikke i bruk
        c.add(new JLabel("Bolig: "));
        BoligKnyttetTil = new JTextField(10);
        c.add(BoligKnyttetTil);

        //for boligsøkere
        c.add(new JLabel("Personlige Opplysninger: "));
        RegPersOpplysning = new JTextField(10);
        c.add(RegPersOpplysning);

        c.add(new JLabel("Krav til boligen: "));
        RegKravBolig = new JTextField(10);
        c.add(RegKravBolig);

        c.add(new JLabel("Krav til utleiepris: "));
        RegKravPris = new JTextField(10);
        c.add(RegKravPris);
*/
        //for kontrakter
        c.add(new JLabel("Utleieboligen"));
        RegUtleieBolig = new JTextField(10);
        c.add(RegUtleieBolig);

        c.add(new JLabel("Utleier: "));
        RegUtleier = new JTextField(10);
        c.add(RegUtleier);

        c.add(new JLabel("Leietaker: "));
        RegLeietaker = new JTextField(10);
        c.add(RegLeietaker);

        c.add(new JLabel("Leiepris pr Måned: "));
        RegPris = new JTextField(10);
        c.add(RegPris);

        c.add(new JLabel("Tidsrom: "));
        RegTid = new JTextField(10);
        c.add(RegTid);

        utleierMengde = new UtleierMengde();
        boligsoekerMengde = new BoligsoekerMengde();
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Buttons
        regBoligSoeker = new JButton("Registrer Boligsøker");
        regBoligSoeker.addActionListener(this);
        c.add(regBoligSoeker);
        
        regUtleier = new JButton("Registrer Utleier");
        regUtleier.addActionListener(this);
        c.add(regUtleier);

        skrivUt = new JButton("Skriv ut");
        skrivUt.addActionListener(this);
        c.add(skrivUt);

        regBolig = new JButton("Registrer Bolig");
        regBolig.addActionListener(this);
        c.add(regBolig);

        output = new JTextArea(10, 20);
        c.add(output);
        output.setText("testdsadsa");
    }

    public void regBoligsoeker() {
        //registrer boligsøker
        Boligsoeker bs = new Boligsoeker(RegPersFornavn.getText(), RegPersEtternavn.getText(),
                RegBolAdr.getText(), RegEpost.getText(), Integer.parseInt(RegTlf.getText()), RegBolType.getText(),
                Integer.parseInt(RegPris.getText()), Integer.parseInt(RegAreal.getText()),
                Integer.parseInt(AntRom.getText()), Integer.parseInt(ByggAar.getText()),
                Integer.parseInt(AntEtasjer.getText()), true, false, true);
        boligsoekerMengde.settInn(bs);
    }
    
    public void regUtleier(){
        Utleier u = new Utleier(RegPersFornavn.getText(),RegPersEtternavn.getText(),
                RegBolAdr.getText(),RegEpost.getText(),Integer.parseInt(RegTlf.getText()),RegFirma.getText());
        utleierMengde.settInn(u);
    }

    public void regKontrakt() {
        // Samme som regPerson, men for kontrakt. Altså opprette kontrakt, bruke settInn - metode i Kontraktliste.
    }

    public void regBolig() {
        Bolig b = new Leilighet(RegBolAdr.getText(), RegBolType.getText(), Beskrivelse.getText(), AvetertDato.getText(),
                Integer.parseInt(RegAreal.getText()), Integer.parseInt(AntRom.getText()), Integer.parseInt(ByggAar.getText()),
                Integer.parseInt(RegPris.getText()), Integer.parseInt(Etasje.getText()), true, true);
        /**
         * Henter etternavn og fornavn på personen som boligen skal registreres
         * på, bruker finnPerson-metoden i Personmengde for å hente ut selve
         * personobjektet. Sender med det og boligobjektet til regBolig-metoden
         * i Personmengde
         */
        Utleier ul = utleierMengde.finnUtleier(RegPersFornavn.getText(), RegPersEtternavn.getText());

        boolean a = utleierMengde.regBolig(ul, b);
        if (a)
            output.append("true");
        output.append("test");
    }

    public void utskrift() {
        /**
         * Få skrevet ut alt som er registrert til et tekstfelt. (Kall på
         * person- mengde, kontraktliste etc sin toString)
         */
        output.setText(boligsoekerMengde.toString() + "\n" + utleierMengde.toString());
    }

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regBoligSoeker) {
            regBoligsoeker();
        } else if (e.getSource() == skrivUt) {
            utskrift();
        } else if (e.getSource() == regBolig) {
            regBolig();
        } else if(e.getSource()== regUtleier){
            regUtleier();
        }
    }
}
