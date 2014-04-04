
/*
Laget av Sigurd, s198597
*/
package boligformidleren;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Vindu extends JFrame implements ActionListener
{
    private JTextField RegBolAdr,RegBolType,RegAreal,AntRom,ByggAar,Beskrivelse,UtleiePris,AvetertDato,AntEtasjer,Kjeller,TomtStorrelse,Etasje,Heis,Balkong,Andre,MinAreal,MaxAreal,MinPris,MaxPris,RegPersNavn,RegPersAdr,RegEpost,RegTlf,RegFirma,BoligKnyttetTil,RegPersOpplysning,RegKravBolig,RegKravPris,RegUtleieBolig,RegUtleier,RegLeietaker,RegPris,RegTid;
    private JTextArea output;
    private JButton regBolig,slettBolig,regPerson,slettPerson,regKontrakt,visBolig,visPerson,visPersonInfo,visBoligInfo,visIntrPers,visKontrakt;
    
    public Vindu()
    {
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
        
        c.add(new JLabel("Utleiepris pr. Måned: "));
        UtleiePris = new JTextField(10);
        c.add(UtleiePris);
        
        c.add(new JLabel("Dato avetert: "));
        AvetertDato = new JTextField(10);
        c.add(AvetertDato);
        
        c.add(new JLabel("Antall etasjer: "));
        AntEtasjer = new JTextField(10);
        c.add(AntEtasjer);
        
        c.add(new JLabel("Kjeller: "));
        Kjeller = new JTextField(10);
        c.add(Kjeller);
        
        c.add(new JLabel("Størrelse på tomt: "));
        TomtStorrelse = new JTextField(10);
        c.add(TomtStorrelse);
        
        c.add(new JLabel("Hvilken etasje: "));
        Etasje = new JTextField(10);
        c.add(Etasje);
        
        c.add(new JLabel("Heis: "));
        Heis = new JTextField(10);
        c.add(Heis);
        
        c.add(new JLabel("Balkong: "));
        Balkong = new JTextField(10);
        c.add(Balkong);
        
        c.add(new JLabel("Andre opplysninger: "));
        Andre = new JTextField(10);
        c.add(Andre);
        
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
        
        //for Personer
        c.add(new JLabel("Navn: "));
        RegPersNavn = new JTextField(10);
        c.add(RegPersNavn);
        
        c.add(new JLabel("Adresse: "));
        RegPersAdr = new JTextField(10);
        c.add(RegPersAdr);
        
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
        
        setSize(400, 400);
        setVisible(true);
    }
    
    public void regPerson(){
        /**
         * Henter nødvendig informasjon fra datafelt, oppretter med det et
         * personobjekt og legger det inn i personmengden. Bruk Personmengde sin
         * settInn-metode.
         */
    }
    public void regKontrakt(){
        // Samme som regPerson
    }
    public void regBolig(){
        // kommer senere
    }
    public void utskrift(){
        /**
         * Få skrevet ut alt som er registrert til et tekstfelt. (Kall på person-
         * mengde, kontraktliste etc sin toString)
         */
    }
    public void actionPerformed(ActionEvent e){
    }
}


