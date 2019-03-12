package Model.XMLConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@XStreamAlias("IssueTable")
public class IssueTable {
    @XStreamImplicit
    ArrayList<Issue> issues = new ArrayList<>();

    public IssueTable(ArrayList<Issue> sheets) {

    }

    public Date getIssueDate(String isin){
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        for (Issue issue : issues){
            try {
                if (isin.equals(issue.isin)) {
                    date = dateformat.parse(issue.issueDate);
                }
            }
            catch (ParseException e){
                System.out.println("invalid issue date");
            }
        }
        return date;
    }

}
