package Model;

import Model.XMLConverter.Row;
import Model.XMLConverter.Table;
import Model.XMLConverter.XMLConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataStore {

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

    private ArrayList<String> disvalueList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> contvalueList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> irrList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> durationList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> resaleList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> projection1List = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> projection2List = new ArrayList<>();//todo: needs to be calculated

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");


    public DataStore() {

        XMLConverter xc = new XMLConverter();
        Table table = xc.getTable();

        int id = 0;
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
            Bond bond = new Bond(redemptionDate, closeOfBusinessDate, coupon);

            disvalueList.add(Double.toString(bond.calculateDiscreteValue(new Date())));
            contvalueList.add("0");
            irrList.add("0");//todo: needs to be added
            durationList.add("0");
            resaleList.add("0");
            projection1List.add("0");
            projection2List.add("0");
        }
    }


    public ArrayList<DisplayBond> getDisplayBonds() {

        ArrayList<DisplayBond> bondList = new ArrayList<>();

        for (int i = 0; i < nameList.size(); i++) {
//            Bond bond = new Bond(redemptionDateList.get(i), closeOfBusinessDateList.get(i), couponList.get(i));
//
//            bondList.add(new DisplayBond(nameList.get(i), dateFormat.format(closeOfBusinessDateList.get(i)), dateFormat.format(redemptionDateList.get(i)), Integer.toString(bond.calculateTermsRemainingToday()), Integer.toString(bond.calculateTermsPassedToday()), idList.get(i).toString() , Double.toString(bond.calculateDiscreteValue(closeOfBusinessDateList.get(i)))));

            bondList.add(new DisplayBond(idList.get(i).toString(), nameList.get(i), disvalueList.get(i), contvalueList.get(i), irrList.get(i), durationList.get(i), resaleList.get(i), projection1List.get(i), projection2List.get(i)));
        }

        return bondList;
    }
    
    public DisplayBond getSpecificDisplayBond(int id){
        return new DisplayBond(idList.get(id).toString() , nameList.get(id), disvalueList.get(id), contvalueList.get(id), irrList.get(id), durationList.get(id), resaleList.get(id), projection1List.get(id), projection2List.get(id));
    }


}
