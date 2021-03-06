package Model;

import Controller.Bond;
import Model.XMLConverter.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataStore {
    DataRoot dataRoot;
    IssueTable issueTable;


    private ArrayList<Integer> idList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> isinList = new ArrayList<>();
    private ArrayList<Date> redemptionDateList = new ArrayList<>();
    private ArrayList<Date> closeOfBusinessDateList = new ArrayList<>();
    private ArrayList<String> cleanPriceList = new ArrayList<>();
    private ArrayList<String> dirtyPriceList = new ArrayList<>();
    private ArrayList<String> accruedInterestList = new ArrayList<>();
    private ArrayList<String> yieldList = new ArrayList<>();
    private ArrayList<String> modifiedDurationList = new ArrayList<>();
    private ArrayList<Boolean> isIndexLinkedList = new ArrayList<>();

    private ArrayList<Double> couponList = new ArrayList<>();

    private ArrayList<String> discreteValueList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> continuousValueList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> irrList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> durationList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> resaleList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> projection1List = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> projection2List = new ArrayList<>();//todo: needs to be calculated


    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM/dd");

    Date date;
    double givenInterestRate;


    public DataStore(Date date, double givenInterestRate) {

        this.date = date;
        this.givenInterestRate = givenInterestRate;

        XMLConverter xc = new XMLConverter();
        Table table = xc.getTable();
        dataRoot = xc.getDataRoot();
        issueTable = xc.getIssueTable();

        int id = 0;
        int counter = 1;
        int percentage = 10;
        for (Row row : table.getRows()) {
            idList.add(id);
            id++;

            String name = row.getA();
            String isin = row.getB();
            Date redemptionDate = new Date();
            Date closeOfBusinessDate = new Date();
            try {
                redemptionDate = dateFormat.parse(row.getC());
                closeOfBusinessDate = dateFormat.parse(row.getD());
            } catch (ParseException e) {
                System.out.println("unable to parse date when creating data store");
            }

            String cleanPrice = row.getE();
            String dirtyPrice = row.getF();
            String accruedInterest = row.getG();
            String yield = row.getH();
            String modifiedDuration = row.getI();
            boolean isIndexLinked;
            Double coupon;

            nameList.add(name);
            isinList.add(isin);
            redemptionDateList.add(redemptionDate);
            closeOfBusinessDateList.add(closeOfBusinessDate);
            cleanPriceList.add(cleanPrice);
            dirtyPriceList.add(dirtyPrice);
            accruedInterestList.add(accruedInterest);
            yieldList.add(yield);
            modifiedDurationList.add(modifiedDuration);

            String couponS = row.getA().split(" ")[0];
            try {
                coupon = Double.valueOf(couponS.replace("%", ""));
            } catch (NumberFormatException e) {
                coupon = 0.0;
            }
            couponList.add(coupon);

            String indexLinkedString = row.getA().split(" ")[1];
            if (indexLinkedString.contains("Index")) {
                isIndexLinked = true;
            } else {
                isIndexLinked = false;
            }
            isIndexLinkedList.add(isIndexLinked);

            //todo: Create Bond here
            Bond bond = new Bond(redemptionDate, closeOfBusinessDate, coupon, isIndexLinked, isin, dataRoot, issueTable);
            String y1 = Integer.toString(Integer.parseInt(yearFormat.format(date)) + 1);
            String y2 = Integer.toString(Integer.parseInt(yearFormat.format(date)) + 2);
            String tempMd = monthDayFormat.format(date);
            Date bootstrap1 = new Date();
            Date bootstrap2 = new Date();
            try {
                bootstrap1 = dateFormat.parse(tempMd + "/" + y1);
                bootstrap2 = dateFormat.parse(tempMd + "/" + y2);
            }
            catch (ParseException e){
                System.out.println("error handling bootstrap dates");
            }
            discreteValueList.add(Double.toString(bond.calculateDiscreteValue(new Date(), coupon / 100)));
            continuousValueList.add(Double.toString(bond.calculateContinuousValue(new Date(), coupon / 100)));
            irrList.add(Double.toString(bond.calculateIRR(issueTable.getIssueDate(isin))));
            durationList.add(Double.toString(bond.calculateMacDuration(getInterestRateByYear(date), date)));
            resaleList.add(Double.toString(bond.calculateResaleValue(date)));
            projection1List.add(Double.toString(bond.calculateBootstrappedValue(bootstrap1)));
            projection2List.add(Double.toString(bond.calculateBootstrappedValue(bootstrap2)));

            if (counter % 500 == 0) {
                System.out.print(percentage + "% ");
                percentage+=10;
            }
            counter++;
        }
        System.out.println("100%");
    }


    public ArrayList<DisplayBond> getDisplayBonds() {

        ArrayList<DisplayBond> bondList = new ArrayList<>();

        for (int i = 0; i < nameList.size(); i++) {

            //get bond constructor


            //Bond bond = new Bond(redemptionDateList.get(i), closeOfBusinessDateList.get(i), couponList.get(i));
//            Date date = new Date();
//            try {
//                date = dateFormat.parse("03/12/2019");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            String s = Double.toString(dataRoot.getInterestRate(date, isinList.get(i)));
//            System.out.print("");
//            bondList.add(new DisplayBond(nameList.get(i), dateFormat.format(closeOfBusinessDateList.get(i)), dateFormat.format(redemptionDateList.get(i)), "", Integer.toString(bond.calculateTermsPassedToday()), idList.get(i).toString() , Double.toString(bond.calculateDiscreteValue(closeOfBusinessDateList.get(i)))));

            bondList.add(new DisplayBond(idList.get(i).toString(), nameList.get(i), discreteValueList.get(i), continuousValueList.get(i), irrList.get(i), durationList.get(i), resaleList.get(i), projection1List.get(i), projection2List.get(i)));
        }

        return bondList;
    }

    public DisplayBond getSpecificDisplayBond(int id) {
        return new DisplayBond(idList.get(id).toString(), nameList.get(id), discreteValueList.get(id), continuousValueList.get(id), irrList.get(id), durationList.get(id), resaleList.get(id), projection1List.get(id), projection2List.get(id));
    }

    public Bond getSpecificBond(int id) {
        return new Bond(redemptionDateList.get(id), closeOfBusinessDateList.get(id), couponList.get(id), isIndexLinkedList.get(id), isinList.get(id), dataRoot, issueTable);
    }

    public ArrayList<DisplayBond> getRawBonds() {

        ArrayList<DisplayBond> bondList = new ArrayList<>();

        for (int i = 0; i < nameList.size(); i++) {
            bondList.add(new DisplayBond(idList.get(i).toString(), nameList.get(i), redemptionDateList.get(i).toString(), closeOfBusinessDateList.get(i).toString(), cleanPriceList.get(i), accruedInterestList.get(i), couponList.get(i).toString()));
        }

        return bondList;
    }

    public double getInterestRateByYear(Date date) {
        SimpleDateFormat interestRateFormat = new SimpleDateFormat("yyyy-dd-MMM");
        try {
            Date a = interestRateFormat.parse("2006-3-Aug");
            Date b = interestRateFormat.parse("2006-9-Nov");
            Date c = interestRateFormat.parse("2007-11-Jan");
            Date d = interestRateFormat.parse("2007-10-May");
            Date e = interestRateFormat.parse("2007-5-Jul");
            Date f = interestRateFormat.parse("2007-6-Dec");
            Date g = interestRateFormat.parse("2008-7-Feb");
            Date h = interestRateFormat.parse("2008-10-Apr");
            Date i = interestRateFormat.parse("2008-8-Oct");
            Date j = interestRateFormat.parse("2008-6-Nov");
            Date k = interestRateFormat.parse("2008-4-Dec");
            Date l = interestRateFormat.parse("2009-8-Jan");
            Date m = interestRateFormat.parse("2009-5-Feb");
            Date n = interestRateFormat.parse("2009-5-Mar");
            Date o = interestRateFormat.parse("2016-4-Aug");
            Date p = interestRateFormat.parse("2017-2-Nov");
            Date q = interestRateFormat.parse("2018-2-Aug");
            Date r = interestRateFormat.parse("2019-7-Feb");

            if (date.after(r)) {
                return 0.75;
            }
            if (date.after(q)) {
                return 0.75;
            }
            if (date.after(p)) {
                return 0.5;
            }
            if (date.after(o)) {
                return 0.25;
            }
            if (date.after(n)) {
                return 0.5;
            }
            if (date.after(m)) {
                return 1;
            }
            if (date.after(l)) {
                return 1.5;
            }
            if (date.after(k)) {
                return 2;
            }
            if (date.after(j)) {
                return 3;
            }
            if (date.after(i)) {
                return 4.5;
            }
            if (date.after(h)) {
                return 5;
            }
            if (date.after(g)) {
                return 5.25;
            }
            if (date.after(f)) {
                return 5.5;
            }
            if (date.after(e)) {
                return 5.75;
            }
            if (date.after(d)) {
                return 5.5;
            }
            if (date.after(c)) {
                return 5.25;
            }
            if (date.after(b)) {
                return 5;
            }
            if (date.after(a)) {
                return 4.75;
            }
            return 5;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 5;
    }


}
