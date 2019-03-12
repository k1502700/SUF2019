package Model.XMLConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Issue")
public class Issue {
    @XStreamAsAttribute @XStreamAlias("isin")
    public String isin;
    @XStreamAsAttribute @XStreamAlias("issueDate")
    public String issueDate;

}
