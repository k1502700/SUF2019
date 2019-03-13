package Model;

import Model.XMLConverter.DataRoot;
import Model.XMLConverter.IssueTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Bond {

    private boolean isIndexLinked;
    private Date redemptionDate;
    private Date closeOfBusinessDate;
    private double cleanPrice;
    private double interestRate;
    private double coupon;
    private String isin;
    int totalTerms;

    private DataRoot dataRoot;
    private IssueTable issueTable;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM/dd");


    public Bond(Date redemptionDate, Date closeOfBusinessDate, Double coupon, boolean isIndexLinked, String isin, DataRoot dataRoot, IssueTable issueTable) {
        this.redemptionDate = redemptionDate;
        this.closeOfBusinessDate = closeOfBusinessDate;
        this.coupon = coupon;
        interestRate = coupon / 100;
        this.isIndexLinked = isIndexLinked;
        this.isin = isin;
        this.dataRoot = dataRoot;
        this.issueTable = issueTable;
        totalTerms = calculateTermDifference(issueTable.getIssueDate(isin), redemptionDate);

    }

    public double calculateDiscreteValue(Date fromDate) {
        if (isIndexLinked) {
            return calculateDiscreteValueIL(fromDate);
        } else {
            return calculateDiscreteValueNL(fromDate);
        }
    }

    public double calculateContinuousValue(Date fromDate) {
        if (isIndexLinked) {
            return calculateContinuousValueIL(fromDate);
        } else {
            return calculateContinuousValueNL(fromDate);
        }
    }

    public double calculateIRRinterestRate() {
        double value = 0.0;
        int termsRemaining = calculateTermsRemaining(closeOfBusinessDate);


        return 0.0;

    }

    private double calculateDiscreteValueNL(Date fromDate) {

        double value = 0.0;
        int termsRemaining = calculateTermsRemaining(fromDate);
        double initialValue = 100;

        for (int i = 0; i < totalTerms - termsRemaining; i++) {
            value += coupon / 2 / Math.pow((1 + interestRate / 2), i+1);
        }
        value += initialValue / Math.pow((1 + interestRate / 2), totalTerms);
        return value;
    }

    private double calculateContinuousValueNL(Date fromDate) {//todo: not finished
        double value = 0.0;
        int termsRemaining = calculateTermsRemaining(fromDate);
        double initialValue = 100;

        for (int i = 0; i < totalTerms - termsRemaining; i++) {
            value += coupon / 2 / Math.pow(Math.E, (interestRate / 2 * (i + 1)));
        }
        value += initialValue / Math.pow(Math.E, (interestRate / 2 * totalTerms));
        return value;
    }

    private double calculateiFact(Date date) {
        return dataRoot.getInterestRate(date, this.isin) / dataRoot.getInterestRate(closeOfBusinessDate, isin);
    }

    private double calculateDiscreteValueIL(Date fromDate) {
        double iFact = calculateiFact(fromDate);
        int termsRemaining = calculateTermsRemaining(fromDate);
        double initialValue = 100;
        double couponIL = interestRate * 100 * iFact;
        double value = 0.0;

        for (int i = 0; i < totalTerms - termsRemaining; i++) {
            value += couponIL / 2 / Math.pow(1 + (interestRate / 2), i + 1);
        }
        value += initialValue / Math.pow(1 + (interestRate / 2), totalTerms);

        return value;
    }

    private double calculateContinuousValueIL(Date fromDate) {
        double iFact = calculateiFact(fromDate);
        int termsRemaining = calculateTermsRemaining(fromDate) / 2;
        double initialValue = 100;
        double couponIL = interestRate * 100 * iFact;
        double value = 0.0;

        for (int i = 0; i < totalTerms - termsRemaining; i++) {
            value += couponIL / 2 / Math.pow(Math.E, (interestRate / 2) * i + 1);
        }
        value += initialValue / Math.pow(Math.E, (interestRate / 2) * totalTerms);

        return value;
    }
//    public double calculate2Macaulay


    public int calculateTermDifference(Date date1, Date date2) {

        int terms = 0;

        if (!date2.after(date1)) {
            return 0;
        }

        String[] datePartsS = dateFormat.format(date1).split("/");
        String[] redemptionPartsS = dateFormat.format(date2).split("/");

        Integer[] dateParts = new Integer[datePartsS.length];
        Integer[] redemptionParts = new Integer[redemptionPartsS.length];

        for (int i = 0; i < dateParts.length; i++) {
            dateParts[i] = Integer.parseInt(datePartsS[i]);
            redemptionParts[i] = Integer.parseInt(redemptionPartsS[i]);
        }

        String targetYearJan = datePartsS[2];
        String targetYearJune = datePartsS[2];

        Date jan22 = new Date();
        Date jun22 = new Date();
        Date targetDate = new Date();

        try {
            if (date1.after(dateFormat.parse("01/22/" + datePartsS[2]))) {
                targetYearJan = Integer.toString((Integer.parseInt(targetYearJan) + 1));
            }
            if (date1.after(dateFormat.parse("07/22/" + datePartsS[2]))) {
                targetYearJune = Integer.toString((Integer.parseInt(targetYearJune) + 1));
            }

            jan22 = dateFormat.parse("01/22/" + targetYearJan);
            jun22 = dateFormat.parse("07/22/" + targetYearJune);

            targetDate = dateFormat.parse(monthDayFormat.format(date2) + "/" + yearFormat.format(date1));

            if (!targetDate.after(date1)) {
                String targetYear = Integer.toString((Integer.parseInt(yearFormat.format(targetDate)) + 1));
                targetDate = dateFormat.parse(monthDayFormat.format(targetDate) + "/" + targetYear);
            }

        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date year");
        }

        if ((jan22.after(date1) && targetDate.after(jan22)) || (dateFormat.format(jan22).equals(dateFormat.format(targetDate)))) {
            terms++;
        }
        if ((jun22.after(date1) && targetDate.after(jun22)) || (dateFormat.format(jun22).equals(dateFormat.format(targetDate)))) {
            terms++;
        }

        if (date2.after(targetDate)) {//year is not same as redemption year
            int targetYear = Integer.parseInt(yearFormat.format(targetDate));
            int redemptionYear = Integer.parseInt(yearFormat.format(date2));
            terms += (redemptionYear - targetYear) * 2;
        }

        return terms;
    }

    public int calculateTermsRemaining(Date date) {
        return calculateTermDifference(date, redemptionDate);
    }

    public double calculateResaleValue(Date date) {

        double value = calculateContinuousValue(date);

        int daysToNextPayment = calculateDaysToNextPayment(date);

        //todo: write method

        double resalevalue = 1.1;

        return resalevalue;
    }

    public int calculateDaysToNextPayment(Date currentDate) {
        Date nextPaymentDate = calculateNextPayment(currentDate);
        long difference = nextPaymentDate.getTime() - currentDate.getTime();
        return (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }


    public Date calculateNextPayment(Date currentDate) {
        String year = yearFormat.format(currentDate);
        Date jan = new Date();
        Date july = new Date();
        Date nextJan = new Date();
        try {
            jan = dateFormat.parse("01/22/" + year);
            nextJan = dateFormat.parse("01/22/" + Integer.toString(Integer.parseInt(year) + 1));
            july = dateFormat.parse("07/22/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (currentDate.after(july)) {
            return nextJan;
        }
        else if (currentDate.after(jan)) {
            return july;
        }
        else return jan;

    }

    public int calculateTermsPassed(Date date) {

        if (!date.after(closeOfBusinessDate)) {
            return 0;
//            throw new IllegalArgumentException("Date before close of business date!");
        }
        return calculateTermDifference(closeOfBusinessDate, date);
    }

    public int calculateTermsRemainingToday() {
        return calculateTermsRemaining(new Date());
    }

    public int calculateTermsPassedToday() {
        return calculateTermsPassed(new Date());
    }
}



