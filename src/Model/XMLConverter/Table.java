package Model.XMLConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;

@XStreamAlias("Table")
public class Table {
    @XStreamImplicit
    ArrayList<Row> rows = new ArrayList<>();

    public Table(){

    }

    public void addRow(Row row){
        rows.add(row);
    }

    public ArrayList<Row> getRows() {
        return rows;
    }
}
