package Model;

import Model.XMLConverter.Row;
import Model.XMLConverter.Table;
import Model.XMLConverter.XMLConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataStore {

    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> isinList = new ArrayList<>();
    private ArrayList<Date> redemptionDateList = new ArrayList<>();
    private ArrayList<Date> closeOfBusinessDateList = new ArrayList<>();
    private ArrayList<String> CleanPriceList = new ArrayList<>();
    private ArrayList<String> dirtyPriceList = new ArrayList<>();
    private ArrayList<String> accruedInterestList = new ArrayList<>();
    private ArrayList<String> yieldList = new ArrayList<>();
    private ArrayList<String> modifiedDurationList = new ArrayList<>();
    private ArrayList<Boolean> isIndexLinked = new ArrayList<>();

    private ArrayList<Double> couponList = new ArrayList<>();

    private ArrayList<String> valueList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> irrList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> durationList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> resaleList = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> projection1List = new ArrayList<>();//todo: needs to be calculated
    private ArrayList<String> projection2List = new ArrayList<>();//todo: needs to be calculated

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");


    public DataStore(){

        XMLConverter xc = new XMLConverter();
        Table table = xc.getTable();

        for (Row row: table.getRows()){
            nameList.add(row.getA());
            isinList.add(row.getB());
            try {
                redemptionDateList.add(dateFormat.parse(row.getC()));
                closeOfBusinessDateList.add(dateFormat.parse(row.getD()));
            }
            catch (java.text.ParseException e){
                redemptionDateList.add(new Date());
                closeOfBusinessDateList.add(new Date());
            }
            CleanPriceList.add(row.getE());
            dirtyPriceList.add(row.getF());
            accruedInterestList.add(row.getG());
            yieldList.add(row.getH());
            modifiedDurationList.add(row.getI());

            String couponS = row.getA().split(" ")[0];
            try {
                Double couponD = Double.valueOf(couponS.replace("%", ""));
                couponList.add(couponD);
            }
            catch (NumberFormatException e){
                couponList.add(0.0);
            }
            String indexLinkedString = row.getA().split(" ")[1];
            if (indexLinkedString.contains("Index")){
                isIndexLinked.add(true);
            }
            else {
                isIndexLinked.add(false);
            }

            valueList .add("0");//todo: needs to be added
            irrList.add("0");
            durationList.add("0");
            resaleList.add("0");
            projection1List.add("0");
            projection2List.add("0");

        }

        System.out.println();
    }


    public ArrayList<DisplayBond> getDisplayBonds(){

        ArrayList<DisplayBond> bondList = new ArrayList<>();

        for (int i = 0; i < nameList.size(); i++){
            bondList.add(new DisplayBond(nameList.get(i), valueList.get(i), irrList.get(i), durationList.get(i), resaleList.get(i), projection1List.get(i), projection2List.get(i)));
        }

        return bondList;
    }


}
