package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bond {

    private boolean isIndexLinked;
    private Date redemptionDate;
    private Date closeOfBusinessDate;
    private double cleanPrice;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM/dd");


    public Bond(Date redemptionDate){
        this.redemptionDate = redemptionDate;
    }







    public double calculateDiscreteValue(){



        return 0.0;
    }

    public double calculateContinousValue(){



        return 0.0;
    }


    public int calculateTermsRemaining(Date date){

        int terms = 0;

        if (date.after(redemptionDate)){
            System.out.println("redemption date before today");
            return 0;
//            throw new IllegalArgumentException("Date after redemption date!");
        }

        String[] datePartsS = dateFormat.format(date).split("/");
        String[] redemptionPartsS = dateFormat.format(redemptionDate).split("/");

        Integer[] dateParts = new Integer[datePartsS.length];
        Integer[] redemptionParts = new Integer[redemptionPartsS.length];

        for (int i = 0; i < dateParts.length; i++){
            dateParts[i] = Integer.parseInt(datePartsS[i]);
            redemptionParts[i] = Integer.parseInt(redemptionPartsS[i]);
        }



        String targetYearJan = datePartsS[2];
        String targetYearJune = datePartsS[2];

        Date jan22 = new Date();
        Date jun22 = new Date();
        Date targetDate = new Date();
        try {
            if (date.after(dateFormat.parse("01/22/" + datePartsS[2]))){
                targetYearJan = Integer.toString((Integer.parseInt(targetYearJan) + 1));
            }
            if (date.after(dateFormat.parse("07/22/" + datePartsS[2]))){
                targetYearJune = Integer.toString((Integer.parseInt(targetYearJune) + 1));
            }

            jan22 = dateFormat.parse("01/22/" + targetYearJan);
            jun22 = dateFormat.parse("07/22/" + targetYearJune);

            targetDate = dateFormat.parse(monthDayFormat.format(redemptionDate) + "/" + yearFormat.format(date));

            if (!targetDate.after(date)){
                String targetYear = Integer.toString((Integer.parseInt(yearFormat.format(targetDate)) + 1));
                targetDate = dateFormat.parse(monthDayFormat.format(targetDate) + "/" + targetYear);
            }

        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date year");
        }

        if ((jan22.after(date) && targetDate.after(jan22)) || (dateFormat.format(jan22).equals(dateFormat.format(targetDate)))){
            terms++;
        }
        if ((jun22.after(date) && targetDate.after(jun22)) || (dateFormat.format(jun22).equals(dateFormat.format(targetDate)))){
            terms++;
        }

        if (redemptionDate.after(targetDate)){//year is not same as redemption year
            int targetYear = Integer.parseInt(yearFormat.format(targetDate));
            int redemptionYear = Integer.parseInt(yearFormat.format(redemptionDate));
            terms += (redemptionYear - targetYear) * 2;
        }

        System.out.print("");
        return terms;
    }

    public int calculateTermsPassed(Date date){

        if (closeOfBusinessDate.after(date)){
            throw new IllegalArgumentException("Date before close of business date!");
        }


        return 1;
    }

    public int calculateTermsRemainingToday(){

        return calculateTermsRemaining(new Date());
    }

    public int calculateTermsPassedToday(){

        return calculateTermsPassed(new Date());
    }

//    public boolean datebefore

}



