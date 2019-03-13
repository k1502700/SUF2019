package Model.XMLConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@XStreamAlias("dataroot")
public class DataRoot {
    @XStreamImplicit
    ArrayList<Sheet> sheets = new ArrayList<>();

    public DataRoot(){

    }

    public double getInterestRate(Date date, String bondName){
        SimpleDateFormat mmmDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        for (Sheet sheet : sheets){

            if (sheet.date.equals(mmmDateFormat.format(date))){

                try {
                    Field field = Sheet.class.getField(bondName);
                    double ir = Double.parseDouble((String) field.get(sheet));
                    return ir;
                }
                catch (Exception e){
//                    System.out.println("Invalid bond name when getting interestrate");
                    return 0.0;
                }


            }



        }

        System.out.println("Interest rate date not in db");




        return 0.0;
    }

}
