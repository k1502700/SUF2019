package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bond {

    private boolean isIndexLinked;
    private Date redemptionDate;
    private Date closeOfBusinessDate;
    private double cleanPrice;
    private double interestRate;
    private double coupon;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM/dd");


    public Bond(Date redemptionDate, Date closeOfBusinessDate, Double coupon) {
        this.redemptionDate = redemptionDate;
        this.closeOfBusinessDate = closeOfBusinessDate;
        this.coupon = coupon;
        interestRate = coupon / 100;
    }


    public double calculateDiscreteValue(Date fromDate) {

        double value = 0.0;
        int termsRemaining = calculateTermsRemaining(fromDate);
        double initialValue = 100;

        for (int i = 0; i < termsRemaining; i++) {
            value += coupon/2 / Math.pow((1 + interestRate/2), termsRemaining);
        }
        value += initialValue / Math.pow((1 + interestRate/2), termsRemaining);
        return value;
    }

    public double calculateContinuousValue(Date fromDate) {//todo: not finished
        double value = 0.0;
        int termsRemaining = calculateTermsRemaining(fromDate);
        double initialValue = 100;

        for (int i = 0; i < termsRemaining; i++){
            value += coupon/2 / Math.pow(Math.E, (interestRate/2 * termsRemaining));
        }
        value += initialValue / Math.pow(Math.E, (interestRate/2 * termsRemaining));
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



