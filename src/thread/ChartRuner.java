package thread;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import main.Controller;
import main.MainApp;


import static main.MainApp.isVideoStoped;

public class ChartRuner implements Runnable {

    @Override
    public void run() {
        while (true) {

            XYChart.Series<Number, Number> take = new XYChart.Series<>();
            try {

                take = MainApp.getSeriesQueue().take();
                XYChart.Series<Number, Number> finalTake = take;

                if(isVideoStoped){Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Controller.getSeries().getData().clear();
                        Controller.getSeries().getData().addAll(finalTake.getData());
                    }
                });}

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
