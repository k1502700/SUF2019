package Model.XMLConverter;

import Model.Model;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;

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
}
