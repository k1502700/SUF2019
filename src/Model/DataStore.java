package Model;

import Model.XMLConverter.Row;
import Model.XMLConverter.Table;
import Model.XMLConverter.XMLConverter;

import java.util.ArrayList;

public class DataStore {

    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> isinList = new ArrayList<>();
    private ArrayList<String> redemptionDateList = new ArrayList<>();
    private ArrayList<String> closeOfBusinessDateList = new ArrayList<>();
    private ArrayList<String> CleanPriceList = new ArrayList<>();
    private ArrayList<String> dirtyPriceList = new ArrayList<>();
    private ArrayList<String> accruedInterestList = new ArrayList<>();
    private ArrayList<String> yieldList = new ArrayList<>();
    private ArrayList<String> modifiedDurationList = new ArrayList<>();

    private ArrayList<Double> couponList = new ArrayList<>();

    private ArrayList<String> valueList = new ArrayList<>();
    private ArrayList<String> irrList = new ArrayList<>();
    private ArrayList<String> durationList = new ArrayList<>();
    private ArrayList<String> resaleList = new ArrayList<>();
    private ArrayList<String> projection1List = new ArrayList<>();
    private ArrayList<String> projection2List = new ArrayList<>();

//
//    ArrayList<String> bondName;
//
//    redemption date
//            first issue date
//
//    dividentdates



    public DataStore(){

        XMLConverter xc = new XMLConverter();
        Table table = xc.getTable();

        for (Row row: table.getRows()){
            nameList.add(row.getA());
            isinList.add(row.getB());
            redemptionDateList.add(row.getC());
            closeOfBusinessDateList.add(row.getD());
            CleanPriceList.add(row.getE());
            dirtyPriceList.add(row.getF());
            accruedInterestList.add(row.getG());
            yieldList.add(row.getH());
            modifiedDurationList.add(row.getI());

            String couponS = row.getA().split(" ")[0];
            Double couponD = 0.0;
            try {

                couponD = Double.valueOf(couponS.replace("%", ""));
                couponList.add(couponD);

            }
            catch (NumberFormatException e){
                couponList.add(0.0);
            }
        }

        System.out.println();
    }




}
