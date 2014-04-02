/*

*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Vindu extends JFrame
{
    private JTextField RegBolAdr,RegBolType,RegAreal,AntRom,ByggAar,Beskrivelse,UtleiePris,AvetertDato,AntEtasjer,Kjeller,TomtStorrelse,Etasje,Heis,Balkong,Andre,MinAreal,MaxAreal,MinPris,MaxPris,RegPersNavn,RegPersAdr,RegEpost,RegTlf,RegLeietaker,RegPris;
    private JTextArea output;
    private JButton regBolig,slettBolig,regPerson,slettPerson,regKontrakt,visBolig,visPerson,visPersonInfo,visBoligInfo,visIntrPers,visKontrakt;
    
    public Vindu()
    {
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
    }
}


