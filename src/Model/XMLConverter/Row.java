package Model.XMLConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Row")
public class Row {
    @XStreamAsAttribute @XStreamAlias("A")
    String a;
    @XStreamAsAttribute @XStreamAlias("B")
    String b;
    @XStreamAsAttribute @XStreamAlias("C")
    String c;
    @XStreamAsAttribute @XStreamAlias("D")
    String d;
    @XStreamAsAttribute @XStreamAlias("E")
    String e;
    @XStreamAsAttribute @XStreamAlias("F")
    String f;
    @XStreamAsAttribute @XStreamAlias("G")
    String g;
    @XStreamAsAttribute @XStreamAlias("H")
    String h;
    @XStreamAsAttribute @XStreamAlias("I")
    String i;

//    @XStreamImplicit
//    ArrayList<Cell> cells = new ArrayList<>();

    public Row(){

    }

//    public void addCell(Cell cell){
//        cells.add(cell);
//    }


    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getE() {
        return e;
    }

    public String getF() {
        return f;
    }

    public String getG() {
        return g;
    }

    public String getH() {
        return h;
    }

    public String getI() {
        return i;
    }
}
