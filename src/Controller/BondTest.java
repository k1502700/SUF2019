package Controller;

import Model.XMLConverter.DataRoot;
import Model.XMLConverter.IssueTable;
import Model.XMLConverter.Table;
import Model.XMLConverter.XMLConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BondTest {
    Table table;
    DataRoot dataRoot;
    IssueTable issueTable;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyy");
    Date date = new Date();
    Date redDate;
    Date closeOBDate;
    Date issueDate;
    boolean isIndexLinked;
    double coupon;
    double interestRate;

    String isin;

    Bond testBond;

    public BondTest() throws ParseException {
        XMLConverter xc = new XMLConverter();
        table = xc.getTable();
        dataRoot = xc.getDataRoot();
        issueTable = xc.getIssueTable();

        redDate = dateFormat.parse("07-09-2023");
        closeOBDate = dateFormat.parse("1-10-2017");
        issueDate = dateFormat.parse("12-6-2013");
        coupon = 5.0;
        interestRate = coupon / 100;
        isIndexLinked = false;
        isin = "GB00B7Z53659";

        testBond = new Bond(redDate, closeOBDate, coupon, isIndexLinked, isin, dataRoot, issueTable);
    }

    @org.junit.Test
    public void calculateDiscreteValueTest() throws Exception {
        assert testBond.calculateDiscreteValue(date, interestRate) == 88.19718313849938;
    }

    @org.junit.Test
    public void calculateContinuousValueTest() throws Exception {
        assert testBond.calculateContinuousValue(date, interestRate) == 87.78405624522168;
    }

    @org.junit.Test
    public void calculateIRRinterestRateTest() throws Exception {
        assert testBond.calculateIRRinterestRate(date) == 0.652;
    }

    @org.junit.Test
    public void calculateYieldToMaturityTest() throws Exception {
        assert testBond.calculateYieldToMaturity() == 0.20526315789473684;
    }

    @org.junit.Test
    public void calculateTermDifferenceTest() throws Exception {
        assert testBond.calculateTermDifference(issueDate, redDate) == 20;
    }

    @org.junit.Test
    public void calculateTermsRemainingTest() throws Exception {
        assert testBond.calculateTermsRemaining(date) == 7;
    }

    @org.junit.Test
    public void calculateResaleValueTest() throws Exception {
        assert testBond.calculateResaleValue(date) == 89.55117953289292;
    }

    @org.junit.Test
    public void calculateMacDurationTest() throws Exception {
        assert testBond.calculateMacDuration(interestRate, date) == 163.51072937584195;
    }

    @org.junit.Test
    public void calculateDaysToNextPaymentTest() throws Exception {
        assert testBond.calculateDaysToNextPayment(date) == 129;
    }
}