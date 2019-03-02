package Model.XMLConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Cell")
public class Cell {
    @XStreamAsAttribute
    String content;

    public Cell(String content) {
        this.content = content;
    }
}
