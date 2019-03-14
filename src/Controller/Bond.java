package Controller;

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

    public double calculateDiscreteValue(Date fromDate, double interestRate) {
        if (isIndexLinked) {
            return calculateDiscreteValueIL(fromDate, interestRate);
        } else {
            return calculateDiscreteValueNL(fromDate, interestRate);
        }
    }

    public double calculateContinuousValue(Date fromDate, double interestRate) {
        if (isIndexLinked) {
            return calculateContinuousValueIL(fromDate, interestRate);
        } else {
            return calculateContinuousValueNL(fromDate, interestRate);
        }
    }

    public double calculateBootstrappedValue(Date date){
        int terms = calculateTermsRemaining(date);
        double sumValue = 0.0;
        for (int i = 1; i < terms; i++){
            sumValue += coupon/Math.pow(1 + calculateYieldToDate(date), i);
        }

        return Math.sqrt((100 + coupon)/(100 - sumValue)) - 1;
    }

    public double calculateIRRinterestRate(Date date) {
        if (coupon == 0) {
            return 0;
        }
        if (date.after(redemptionDate) || calculateTermsRemaining(date) < 1) {
            return 0;
        }
        if (isIndexLinked) {
            return 0;
        }
        double interestRate = 0.0;
        double value = 0.0;

        while (true) {
            value = calculateDiscreteValue(issueTable.getIssueDate(isin), interestRate);
            if (value < 1) {
                return interestRate;
            }

            interestRate += 0.001;
            if (interestRate > 0.01) {
                interestRate += 0.01;
            }
            if (interestRate > 0.1) {
                interestRate += 0.1;
            }
            if (interestRate > 1) {
                interestRate += 0.5;
            }
            if (interestRate > 100) {
                interestRate += 5;
            }
            if (interestRate > 1000) {
                return 1000;
            }
        }
    }

    private double calculateDiscreteValueNL(Date fromDate, double interestRate) {

        double value = 0.0;
        int termsRemaining = calculateTermsRemaining(fromDate);
        double ytm = calculateYieldToMaturity();
        double initialValue = 100;

        for (int i = 0; i < totalTerms - termsRemaining; i++) {
            value += coupon / 2 / Math.pow((1 + interestRate / 2), i + 1);
        }
        value += initialValue / Math.pow((1 + interestRate / 2), totalTerms);
        return value;
    }

    private double calculateContinuousValueNL(Date fromDate, double interestRate) {//todo: not finished
        double value = 0.0;
        int termsRemaining = calculateTermsRemaining(fromDate);
        double ytm = calculateYieldToMaturity();
        double initialValue = 100;

        for (int i = 0; i < totalTerms - termsRemaining; i++) {
            value += interestRate * 100 / 2 / Math.pow(Math.E, (interestRate / 2 * (i + 1)));
        }
        value += initialValue / Math.pow(Math.E, (interestRate / 2 * totalTerms));
        return value;
    }


    private double calculateDiscreteValueIL(Date fromDate, double interestRate) {
        double iFact = calculateiFact(fromDate);
        int termsRemaining = calculateTermsRemaining(fromDate);
        double initialValue = 100;
        double couponIL = interestRate * 100 * iFact;
        double ytm = calculateYieldToMaturity();
        double value = 0.0;

        for (int i = 0; i < totalTerms - termsRemaining; i++) {
            value += couponIL / 2 / Math.pow(1 + (interestRate / 2), i + 1);
        }
        value += initialValue / Math.pow(1 + (interestRate / 2), totalTerms);

        return value;
    }

    private double calculateContinuousValueIL(Date fromDate, double interestRate) {
        double iFact = calculateiFact(fromDate);
        int termsRemaining = calculateTermsRemaining(fromDate) / 2;
        double initialValue = 100;
        double couponIL = interestRate * 100 * iFact;
        double ytm = calculateYieldToMaturity();
        double value = 0.0;

        for (int i = 0; i < totalTerms - termsRemaining; i++) {
            value += couponIL / 2 / Math.pow(Math.E, (interestRate / 2) * i + 1);
        }
        value += initialValue / Math.pow(Math.E, (interestRate / 2) * totalTerms);

        return value;
    }

    private double calculateiFact(Date date) {
        return dataRoot.getInterestRate(date, this.isin) / dataRoot.getInterestRate(closeOfBusinessDate, isin);
    }//    public double calculate2Macaulay

    public double calculateYieldToDate(Date toDate){
        return (coupon + (100 - cleanPrice) / calculateTermDifference(issueTable.getIssueDate(isin), toDate)) / ((100 + cleanPrice) / 2);
    }

    public double calculateYieldToMaturity() {
        return calculateYieldToDate(redemptionDate);
    }

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

        double value = calculateContinuousValue(date, interestRate);

        int daysToNextPayment = calculateDaysToNextPayment(date);


        double resalevalue = value + ((double) daysToNextPayment / 365.0) * coupon;

        return resalevalue;
    }

    public double calculateMacDuration(double interestRate, Date date){
        int currentterm = calculateTermsPassed(date);
        double sum = 0.0;
        int maxterm = calculateTermDifference(issueTable.getIssueDate(isin), redemptionDate);
        double value = calculateDiscreteValue(date, interestRate);
        for (double i = 1.0; i <= currentterm; i+=1.0) {
            double ff = (coupon/2 * i)/Math.pow(1.0+interestRate/2,i) +(((double) maxterm)*100.0)/Math.pow(1.0+interestRate/2, (double) maxterm);
            double result = ff/value;
            sum+=result;
        }

        return sum;
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
        } else if (currentDate.after(jan)) {
            return july;
        } else return jan;

    }

    public int calculateTermsPassed(Date date) {

        if (!date.after(issueTable.getIssueDate(isin))) {
            return 0;
        }
        return calculateTermDifference(issueTable.getIssueDate(isin), date);
    }

    public int calculateTermsRemainingToday() {
        return calculateTermsRemaining(new Date());
    }

    public int calculateTermsPassedToday() {
        return calculateTermsPassed(new Date());
    }
}



