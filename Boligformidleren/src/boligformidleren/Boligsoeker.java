/*
 * INNHOLD: Klassen Boligsoeker er subklasse til Person og skal representere en boligsøker.
 * Sist oppdatert: 05.05.2014 kl.11:50
 * Programmert av: Eivind, Gretar
 */
package boligformidleren;

import java.util.Date;

public class Boligsoeker extends Person implements Comparable<Boligsoeker> {

    /*
     * Hvis boligsøker ikke har noen krav om type,
     * så bruker kun felles feltene.
     * Hvis boligsøker har krav om enebolig, så viser
     * vi enebolig-feltene i tillegg til felles feltene
     */
    // Krav til bolig
    private int pris, areal, soverom, byggeaar, maxAntEtasjer, maxEtasje, tomtestorrelse;
    private String pInfo, type;
    private Date dato;
    private boolean heis, balkong, kjeller, leterEtterBolig = true;

    // konstruktør
    public Boligsoeker(String fornavn, String etternavn, String gateadresse, int postnr, String poststed, String epost, int tlfnr, String pInfo,
            String type, int areal, int soverom, int byggeaar, int pris, Date dato,
            int maxAntEtasjer, int tomtestorrelse, boolean kjeller,
            int maxEtasje, boolean heis, boolean balkong) {

        super(fornavn, etternavn, gateadresse, postnr, poststed, epost, tlfnr);
        this.pInfo = pInfo;

        // felles krav
        this.areal = areal;
        this.byggeaar = byggeaar;
        this.dato = dato;
        this.pris = pris;
        this.soverom = soverom;
        this.type = type;

        // enebolig krav
        this.maxAntEtasjer = maxAntEtasjer;
        this.tomtestorrelse = tomtestorrelse;
        this.kjeller = kjeller;

        // leilighet krav
        this.maxEtasje = maxEtasje;
        this.balkong = balkong;
        this.heis = heis;
    }

    // get metoder
    public String getPersInfo() {
        return pInfo;
    }

    public int getAreal() {
        return areal;
    }

    public int getByggeaar() {
        return byggeaar;
    }

    public Date getDato() {
        return dato;
    }

    public int getPris() {
        return pris;
    }

    public int getSoverom() {
        return soverom;
    }

    public String getType() {
        return type;
    }

    public int getMaxAntEtasjer() {
        return maxAntEtasjer;
    }

    public int getTomtestorrelse() {
        return tomtestorrelse;
    }

    public boolean getKjeller() {
        return kjeller;
    }

    public int getMaxEtasje() {
        return maxEtasje;
    }

    public boolean getBalkong() {
        return balkong;
    }

    public boolean getHeis() {
        return heis;
    }

    public boolean getLeterEtterBolig() {
        return leterEtterBolig;
    }

    // set metoder
    public void setPersInfo(String p) {
        pInfo = p;
    }

    public void setAreal(int a) {
        areal = a;
    }

    public void setByggeaar(int b) {
        byggeaar = b;
    }

    public void setDato(Date d) {
        dato = d;
    }

    public void setPris(int p) {
        pris = p;
    }

    public void setSoverom(int s) {
        soverom = s;
    }

    public void setType(String t) {
        type = t;
    }

    public void setMaxAntEtasjer(int m) {
        maxAntEtasjer = m;
    }

    public void setTomtestorrelse(int t) {
        tomtestorrelse = t;
    }

    public void setKjeller(boolean k) {
        kjeller = k;
    }

    public void setMaxEtasje(int m) {
        maxEtasje = m;
    }

    public void setBalkong(boolean b) {
        balkong = b;
    }

    public void setHeis(boolean h) {
        heis = h;
    }

    // sjekker om paramteren bolig passer til boligsøkerens krav
    public boolean passerTilBolig(Bolig b) {
        if (b == null) {
            return false;
        }

        if (!b.getLedig()) {
            return false;
        }

        boolean passer = true;

        if (areal != 0 && areal > b.getAreal()) {
            passer = false;
        }
        if (byggeaar != 0 && byggeaar > b.getByggeaar()) {
            passer = false;
        }
        if (dato != null && dato.after(b.getDato())) {
            passer = false;
        }
        if (pris != 0 && pris < b.getPris()) {
            passer = false;
        }
        if (soverom != 0 && soverom > b.getSoverom()) {
            passer = false;
        }
        if (!type.equals("Ingen krav") && !type.equals(b.getType())) {
            passer = false;
        }

        if (b instanceof Enebolig) {
            Enebolig e = (Enebolig) b;

            if (maxAntEtasjer != 0 && maxAntEtasjer < e.getAntEtasjer()) {
                passer = false;
            }
            if (tomtestorrelse != 0 && tomtestorrelse > e.getTomtAreal()) {
                passer = false;
            }
            if (kjeller && !e.getKjeller()) {
                passer = false;
            }
        }

        if (b instanceof Leilighet) {
            Leilighet l = (Leilighet) b;

            if (maxEtasje != 0 && maxEtasje < l.getEtasje()) {
                passer = false;
            }
            if (balkong && !l.getBalkong()) {
                passer = false;
            }
            if (heis && !l.getHeis()) {
                passer = false;
            }
        }

        return passer;
    }
    
    // Setter boligsøker til "leter etter bolig".
    public void leterEtterBolig(){
        leterEtterBolig = true;
    }
    // Setter boligsøker til "leter ikke etter bolig".
    public void leterIkkeEtterBolig() {
        leterEtterBolig = false;
    }

    // Lager en array som skal tilsvare en rad i en boligsøkertabell.
    public Object[] tilRad() {
        Object[] rad = {super.getEtternavn() + ", " + super.getFornavn(),
            super.getPoststed(), new Integer(pris), new Integer(areal), new Integer(soverom),
            type, dato == null ? "Ikke oppgitt" : StartVindu.ENKELDATOFORMAT.format(dato),
            super.getFornavn(), super.getEtternavn()};
        return rad;
    }

    /**
     * Enkel compareTo-metode. Kun for å kunne lagre Utleiere i en TreeSet som
     * ikke tar imot en comparator i kontruktøren, slik at lagring til fil
     * muliggjøres.
     */
    public int compareTo(Boligsoeker bs) {
        return bs.getEtternavn().compareTo(this.getEtternavn());
    }

    // toString-metode
    public String toString() {
        String s = super.toString() + "\nPersonlige opplysninger: " + pInfo
                + "\nStatus: " + (leterEtterBolig ? "Leter etter bolig" : "Leter ikke etter bolig")
                + "\nKrav: "
                + "\nBoligype: " + type
                + "\nMin. areal: " + areal
                + "\nMin. sovesrom: " + soverom
                + "\nMin. byggeår: " + byggeaar
                + "\nMax. leiepris: " + pris
                + "\nMax. antall etasjer: " + maxAntEtasjer
                + "\nMin. tomtestørrelse: " + tomtestorrelse
                + "\nKjeller: " + (kjeller ? "ja" : "nei")
                + "\nMax. etasje: " + maxEtasje
                + "\nHeis: " + (heis ? "ja" : "nei")
                + "\nBalkong: " + (balkong ? "ja" : "nei")
                + "\nDato: " + (dato == null ? "Ikke oppgitt" : StartVindu.ENKELDATOFORMAT.format(dato));
        return s;
    }
}
