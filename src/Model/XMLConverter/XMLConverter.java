package Model.XMLConverter;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class XMLConverter {
    Table table;
    DataRoot dataRoot;
    IssueTable issueTable;
    String path;

    public XMLConverter(){
        convertInterestRates();
        convertBonds();
        convertIssueDates();
    }

    public void convertInterestRates(){
        Class<?>[] classes = new Class[] { DataRoot.class, Sheet.class};
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classes);

        xStream.processAnnotations(DataRoot.class);
        xStream.processAnnotations(Sheet.class);

        URL url = getClass().getResource("IndexRatioData.xml");
        String input = "";
        String urlS = url.toString();
        urlS = urlS.replace("file:/", "");

        try {
            input = readFile(urlS, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataRoot = (DataRoot) xStream.fromXML(input);

    }

    public void convertIssueDates(){

        Class<?>[] classes = new Class[] { IssueTable.class, Issue.class};
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classes);

        xStream.processAnnotations(IssueTable.class);
        xStream.processAnnotations(Issue.class);

        URL url = getClass().getResource("GiltsInIssueTruncated.xml");
        String input = "";
        String urlS = url.toString();
        urlS = urlS.replace("file:/", "");

        try {
            input = readFile(urlS, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        issueTable = (IssueTable) xStream.fromXML(input);

    }


    public void convertBonds(){

        Class<?>[] classes = new Class[] { Table.class, Row.class, Cell.class};
        XStream xStream = new XStream();
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classes);

        xStream.processAnnotations(Table.class);
        xStream.processAnnotations(Row.class);
        xStream.processAnnotations(Cell.class);

        URL url = getClass().getResource("20190302GiltReferencePrices.xml");
        String input = "";
        String urlS = url.toString();
        urlS = urlS.replace("file:/", "");

        try {
            input = readFile(urlS, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        table = (Table) xStream.fromXML(input);



//        Cell cell1 = new Cell("1");
//        Cell cell2 = new Cell("2");
//        Cell cell3 = new Cell("3");
//
//        Row row1 = new Row();
//        row1.addCell(cell1);
//        row1.addCell(cell2);
//        row1.addCell(cell3);
//        Row row2 = new Row();
//        row2.addCell(cell1);
//        row2.addCell(cell2);
//        row2.addCell(cell3);
//        Row row3 = new Row();
//        row3.addCell(cell1);
//        row3.addCell(cell2);
//        row3.addCell(cell3);
//
//        Table table1 = new Table();
//        table1.addRow(row1);
//        table1.addRow(row2);
//        table1.addRow(row3);
//
//        System.out.println(xStream.toXML(table1));




    }


    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public Table getTable() {
        return table;
    }

    public DataRoot getDataRoot() {
        return dataRoot;
    }

    public IssueTable getIssueTable() {
        return issueTable;
    }
}
