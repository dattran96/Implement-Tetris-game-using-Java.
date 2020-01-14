package tud.ai2.tetris.model;

import tud.ai2.tetris.util.Const;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Die Matrix ist ein Raster, das aus Bloecken und
 * Leerstellen besteht. Eine neu erstellte Matrix ist immer
 * komplett leer und kann durch Ablegen von Steinen gefuellt
 * werden.
 *
 * @author Sebastian Kasten (Melkom)
 * @author Robert Cieslinski
 * @author <Ihr/e Name/n>
 * @version <Ihr Datum>
 */
public class Matrix {
    /** Datenstruktur, die die Bloecke enthaelt. Die Liste
     * enthaelt nur so viele Eintraege wie momentan noetig.*/
    private final List<Block[]> belegung = new ArrayList<>();
    /** Anzahl der Bloecke pro Zeile */
    private final int breite;
    /** Maximale Anzahl der Zeilen */
    private final int hoehe;
    /** Anzahl der bisher geloeschten vollen Zeilen */
    private int volleZeilen = 0;

    /**
     * Erstellt eine neue Matrix mit gegebenen Breite
     * und Hoehe.
     *
     * @param breite Breite der Matrix
     * @param hoehe Hoehe der Matrix
     */
    public Matrix(int breite, int hoehe) {
        this.breite = breite;
        this.hoehe = hoehe;
    }

    /**
     * Gibt die Breite, also die Anzahl der Bloecke
     * pro Zeile zurueck.
     *
     * @return Breite der Matrix
     */
    public int gibBreite() {
        return this.breite;
    }

    /**
     * Gibt die Hoehe, also die Anzahl der Zeilen
     * zurueck.
     *
     * @return Hoehe der Matrix
     */
    public int gibHoehe() {
        return this.hoehe;
    }

    /**
     * Gibt die Anzahl der bisher geloeschten vollen Zeilen zurueck.
     *
     * @return Anzahl der bisher geloeschten vollen Zeilen
     */
    public int gibVolleZeilen() {
        return volleZeilen;
    }

    /**
     * Erweitert die Matrix nach oben mit einer neuen Zeile,
     * sollte noch Platz sein.
     * */
    private void neueZeile() {
        if(this.belegung.size()<this.hoehe) {
            Block[] bloecke = new Block[this.breite];
            this.belegung.add(bloecke);
        }
    }

    /**
     * Versetzt die Matrix in ihren Ausgangszustand.
     */
    public void reset(){
        belegung.clear();
        volleZeilen = 0;
    }

    /**
     *  Setzt den Block in Spalte x und Zeile y auf den uebergebenen
     *  Wert block. Falls block null ist, wird eine Leerstelle erzeugt.
     *
     * @param x gewünschte X-Koordinate
     * @param y gewünschte Y-Koordinate
     * @param block der zu setzende Block
     */
    private void setzeBlock(int x, int y, Block block) {
        if(x<0 || x >= this.breite || y<0 || y >= this.hoehe)
            return;

        // ggf. Matrix nach oben "wachsen" lassen
        while(y>=this.belegung.size())
            neueZeile();

        this.belegung.get(y)[x] = block;
    }

    /**
     *  Loescht den Block an gegebener Stelle.
     *
     * @param x X-Koordinate des zu löschenden Blocks
     * @param y Y-Koordinate des zu löschenden Blocks
     */
    private void loescheBlock(int x, int y) {
        setzeBlock(x,y,null);
    }

 // TODO Aufgabe 5a)
    private void floodfill(int x, int y, int steinIdx, List<Integer> xs, List<Integer> ys) {
        // IHRE IMPLEMENTIERUNG
        //Finde den zugehörigen Stein an x,y.  
        //Lock around neighbor Blocks and check their SteinIndx, if they have the same SteinIndx then update xs,ys.
        //Delete block out of Matrix
    	
        Block check_block = this.gibBlock(x, y);
        if(check_block==null)
        	return;
        int old_steinIdx = steinIdx;
        
        //System.out.println("Before Floodfill");
        if(this.gibBlock(x,y).gibSteinIdx()==old_steinIdx)
        {
            //If the same SteinIndex found, add to xs,ys matrix
            xs.add(new Integer(x));
            ys.add(new Integer(y));
            this.loescheBlock(x, y);
            //recursively call to check Neighbor's Block before go back to original one
            System.out.println("Before Floodfill");
            floodfill(x,y+1,check_block.gibSteinIdx(),xs,ys);
            System.out.println("Flood 1");
            floodfill(x, y-1, check_block.gibSteinIdx(),xs, ys);
            System.out.println("Flood 2");
            floodfill(x+1, y, check_block.gibSteinIdx(),xs, ys);
            System.out.println("Flood 3");
            floodfill(x-1, y, check_block.gibSteinIdx(),xs, ys);
            System.out.println("Flood 4");
        }
    }


    // TODO Aufgabe 5b)
    private int[] boundingBox(List<Integer> xs, List<Integer> ys) {
        // IHRE IMPLEMENTIERUNG
        //Gib die Grenzen züruck: links, rechts, unten, oben
        int links_bound,rechts_bound,unten_bound,oben_bound=0;
        Iterator<Integer> x_iter  = xs.iterator();
        Iterator<Integer> y_iter = ys.iterator();
        //Initialitze value for x, then compare this value to get min and max
        Integer Max_x = x_iter.next();
        Integer Min_x = Max_x;
        while(x_iter.hasNext())
        {
            Integer temp_Compare = x_iter.next();
            if(temp_Compare.compareTo(Max_x)>0)
            {
                Max_x=temp_Compare;
            }
            if(temp_Compare.compareTo(Max_x)<0)
            {
                Min_x=temp_Compare;
            }
        }
        //Simularly initialize value for y, them compare this value to get min and max
        Integer Max_y = y_iter.next();
        Integer Min_y = Max_y;
        while(y_iter.hasNext())
        {
            Integer temp_Compare  = y_iter.next();
            if(temp_Compare.compareTo(Max_y)>0)
            {
                Max_y=temp_Compare;
            }
            if(temp_Compare.compareTo(Min_y)<0)
            {
                Min_y=temp_Compare;
            }
        }
        //Convert Object Integer to value Integer and return.
        int[] bound_result = new int[4];
        bound_result[0] = Min_x.intValue();
        bound_result[1]= Max_x.intValue();
        bound_result[2]=Min_y.intValue();
        bound_result[3]=Max_y.intValue();

        
        return bound_result;
    }

    // TODO Aufgabe 5c)
    private Stein erstelleStein(List<Integer> xs, List<Integer> ys, Color farbe) {
        // IHRE IMPLEMENTIERUNG
        // IHRE IMPLEMENTIERUNG
        //Ruf boundingBox auf, um die Grenzen des Forms zu generieren
  	
        int[] boundary_stein = this.boundingBox(xs, ys);
        //generieren neuen Form
        boolean[][] Form_Stein = new boolean[boundary_stein[3]-boundary_stein[2]+1][boundary_stein[1]-boundary_stein[0]+1];
        
        //System.out.println("x offset:"+ (boundary_stein[1]-boundary_stein[0]+1) + " y offset:"+(boundary_stein[3]-boundary_stein[2]+1));
        //Set origin Coordinator: links,oben
        int orginCoordin_x = boundary_stein[0];
        int orginCoordin_y = boundary_stein[2];
        
        //Get ready to loop over the form       
        Iterator<Integer> xs_Iterator = xs.iterator();
        Iterator<Integer> ys_Iterator = ys.iterator();
        
        //To generate a new Form, we first set all block in the stein to false, then set block that have Coordinate in xs,ys to true
        //1. Set all block to false
        for(int i = 0;i< boundary_stein[3]-boundary_stein[2];i++)
        {
            for(int j=0;j< boundary_stein[1]-boundary_stein[0];j++)
            {
                Form_Stein[i][j]=false;
            }
        }
        
        //2. Set blocks that have Coordinate in xs,ys to true
        while(xs_Iterator.hasNext())
        {
            int xs_true = xs_Iterator.next().intValue();
            int ys_true = ys_Iterator.next().intValue();
            Form_Stein[ys_true-orginCoordin_y][xs_true-orginCoordin_x]=true;

        }
        
        //Create new Stein and retern this Stein
        //Constructor : Stein(Matrix,Form,Unten,Links)
        System.out.println("Form Length:"+Form_Stein[0].length);
        return new Stein(this,new Form(Form_Stein,farbe),orginCoordin_y, orginCoordin_x);
        //return new Stein(this, Const.I);
    }

    /** Ermittelt den Stein, der mit dem Block in Spalte x und
     * Zeile y zusammenhaengt und loescht ihn aus der Matrix.
     * Gibt ihn dann als Stein-Objekt zurueck. Gibt null zurueck,
     * wenn sich an der Stelle eine Leerstelle befindet.
     *
     * @param x X-Koordinate des Steins
     * @param y Y-Koordinate des Steins
     */
    private Stein nimmStein(int x, int y) {
        // Leerstelle, dann gibt es auch keinen Stein
    	System.out.println("nimm x"+x+" "+y);
        if(gibBlock(x,y)==null) return null;

       // Farbe uebernehmen, Liste der Positionen initialisieren

        final Color farbe = gibBlock(x,y).gibFarbe();
        final List<Integer> xs = new ArrayList<>();
        final List<Integer> ys = new ArrayList<>();

        // Floodfill, um zusammenhaengende Bloecke zu finden
        System.out.println("Check here before FloodFill--------------");
        System.out.println("x: "+x+" y:"+y);
        floodfill(x,y,gibBlock(x,y).gibSteinIdx(),xs,ys);
        Iterator<Integer> loopxs = xs.iterator();
        Iterator<Integer> loopys = ys.iterator();
        if(loopxs.hasNext())
        {
        	System.out.println("Array:"+"xs:"+loopxs.next()+" ys:"+loopys.next());
        }
        System.out.println("Check here after FloodFill----------------");
        return erstelleStein(xs, ys, farbe);
    }


    // TODO Aufgabe 4a)
    private void faerbeVolleZeilen() {
        // IHRE IMPLEMENTIERUNG
    	//Überpruf Vollbarkeit und Schwarz färben
    	////////////////////////Access Zeile of Matrix, check Farbe of each Block in this Zeile
        Iterator<Block[]> iter  = belegung.iterator();
        while(iter.hasNext())
        {
            Block[] temp_zeile= iter.next();
            int BlockInRow_Count = 0;
            boolean Vollbarkeit_check = false;
            for(int i=0;i<this.breite;i++)
            {
                if(temp_zeile[i] != null)
                {
                    BlockInRow_Count++;
                }
            }
            //check Vollbarkeit
            if(BlockInRow_Count==this.breite)
            {
                Vollbarkeit_check=true;
            }
            //If true, alter the color to black, increse volleZeilen by 1
            if(Vollbarkeit_check)
            {
                for(int i=0;i<this.breite;i++)
                 {
                    temp_zeile[i].setFarbe(Color.BLACK);
                 }
                 this.volleZeilen++;
            }
        }
    }


    // TODO Aufgabe 4b)
    private boolean loescheSchwarzeZeilen(){
        // IHRE IMPLEMENTIERUNG
        Iterator<Block[]> iter  = belegung.iterator();
        boolean check_delected = false;
        while(iter.hasNext())
        {
            Block[] temp_zeile = iter.next(); 
            //find black row
            if(temp_zeile[0]==null)
            	continue;
            if(temp_zeile[0].gibFarbe()==Color.BLACK)
            {
                iter.remove(); //remove black row
               check_delected=true;
           }
       }
        return check_delected;
    }

    /**
     * Erkennt schwarze Zeilen, loescht diese und initiiert eventuelle
     * Steinbewegungen (erweiterte Physik).
     *
     * @return true <=> mindestens eine Zeile entfernt;
     *         false sonst
     */
    public boolean aufraeumen() {
        boolean aenderung = loescheSchwarzeZeilen();

        // Wenn eine Zeile entfernt wurde...
        if(aenderung) {
            // ...extrahiere betroffene Steine und
            // lasse diese neu auf die Matrix fallen
            for (int i = 0; i < this.belegung.size(); i++)
                for (int j = 0; j < gibBreite(); j++) {
                    Stein stein = nimmStein(j, i);
                    if (stein != null)
                        manifestieren(stein);
                }
            // ggf. erneut Zeilen schwarz färben
            faerbeVolleZeilen();
        }
        return aenderung;
    }

    /**
     * Gibt den Block in Spalte x und Zeile y Zeile zurueck.
     *
     * @param x Spalte, also Position innerhalb der Zeile
     * @param y Index der Zeile
     * @return Block an der Stelle oder <code>null</code>
     * wenn diese Stelle leer ist
     */
    public Block gibBlock(int x, int y) {
        if(y<this.belegung.size() && y>=0 && x>=0 && x<gibBreite())
            return this.belegung.get(y)[x];
        return null;
    }

    // TODO Aufgabe 3a)
    private int gibHoehe(int x, int y) {
    	for(int i = y-1; i >= 0; i--) {
    		if(gibBlock(x,i) != null)
    			return i+1;
    	}
    		
        return 0;
    }


    // TODO Aufgabe 3b)
    public int aufsetzpunkt(Stein stein) {
        // IHRE IMPLEMENTIERUNG
    	int min = this.hoehe, aufSetPunk = 0;
    	for(int i = stein.gibLinks(); i < stein.gibLinks() + stein.gibForm().gibBreite(); i++) {
    		for(int j = stein.gibUnten(); j< stein.gibUnten() + stein.gibForm().gibHoehe(); j++) {
    			if(stein.gibForm().istBelegt(i-stein.gibLinks(), j-stein.gibUnten())) {
    				if((j - gibHoehe(i, j)) < min) {
    					min = j - gibHoehe(i, j);
    					aufSetPunk = stein.gibUnten() - min;
    				}
    				break;
    			}
    		}
    	}
        return aufSetPunk;
    }

    // TODO Aufgabe 3d)
    private boolean manifestieren(Stein stein) {
        // IHRE IMPLEMENTIERUNG
    	int original_aufsetpunkt = this.aufsetzpunkt(stein);
    	for(int i = stein.gibLinks(); i < stein.gibLinks() + stein.gibForm().gibBreite(); i++) {
    		for (int j = stein.gibUnten(); j < stein.gibUnten() + stein.gibForm().gibHoehe(); j++) {
    			if(stein.gibForm().istBelegt(i - stein.gibLinks(), j - stein.gibUnten())) {
    				Block neuBlock = new Block(stein.gibSteinIndex(), stein.gibForm().gibFarbe());
    				this.setzeBlock(i,j-stein.gibUnten()+original_aufsetpunkt , neuBlock);
    				System.out.println("original_aufsetpunkt:"+original_aufsetpunkt+" und temp_aufsetpunkt:"+this.aufsetzpunkt(stein));
    			}
    		}
    	}
    	System.out.println("Hohe:"+this.hoehe+" aufsetpunkt:"+this.aufsetzpunkt(stein)+" Unten:"+stein.gibUnten());
    	if(this.aufsetzpunkt(stein) < this.hoehe -1)
    		return true;
        return false;
    }

    /**
     * Legt den angegebenen Stein auf die Matrix ab und
     * manifestiert diesen somit zu Bloecken auf der
     * Matrix.
     *
     * @param stein Stein, der auf die Matrix faellt
     * @return true <=> Stein erfolgreich abgelegt;
     *         false sonst
     */
    public boolean ablegen(Stein stein) {
        if(!manifestieren(stein)) return false;
        faerbeVolleZeilen();
        return true;
    }

}
