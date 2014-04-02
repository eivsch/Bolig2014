/*
 * Innhold:
 * Sist oppdatert:
 * Programmert av: Eivind
 */
package boligformidleren;
import java.util.*;
import java.text.*;
import javax.swing.JOptionPane;
import java.io.Serializable;

/**
 *
 * @author Eivind
 */
public class Personsammenlikner implements Comparator<Person>, Serializable{

    private String rekkefølge = "<\0<0<1<2<3<4<5<6<7<8<9"
            + "<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j"
            + "<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t"
            + "<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,æ<Ø,ø<Å=AA,å=aa;AA,aa";

    private RuleBasedCollator kollator;

    public Personsammenlikner() {
        try {
            kollator = new RuleBasedCollator(rekkefølge);
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(null,
                    "Feil i rekkefølgestring for kollator!");
            System.exit(0);
        }

    }

    public int compare(Person p1, Person p2) {
        String n1 = p1.getEtternavn();
        String n2 = p2.getEtternavn();
        String f1 = p1.getFornavn();
        String f2 = p2.getFornavn();
        int d = kollator.compare(n1, n2);
        if (d != 0) {
            return d;
        } else {
            return kollator.compare(f1, f2);
        }
    }
}
