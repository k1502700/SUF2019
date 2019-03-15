package View;

import Controller.Bond;
import Model.DataStore;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class GraphScene extends Scene {

    ArrayList<String> bondids;

    public GraphScene(Parent root, ArrayList<String> bondids, DataStore dataStore) {
        super(root);
        this.bondids= bondids;

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(2010);
        xAxis.setUpperBound(2020);
        xAxis.setTickUnit(1);


        for (int i = 0; i <bondids.size() ; i++) {
            Bond b = dataStore.getSpecificBond(Integer.parseInt(bondids.get(i)));

            LinkedHashMap<Integer, Double> hashlist = b.calculateYieldList(new Date());

            XYChart.Series series = new XYChart.Series();
            series.setName(b.toString());

            for (Map.Entry<Integer, Double> entry : hashlist.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                series.getData().add(new XYChart.Data(key, value));
            }
            lineChart.getData().add(series);


        }

        ((Group) this.getRoot()).getChildren().addAll(lineChart);
    }


}
