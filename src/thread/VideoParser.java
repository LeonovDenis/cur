package thread;

import javafx.scene.chart.XYChart;
import main.MainApp;

import static math.Utils.byteToFrame;
import static math.Utils.frameToSeries;

public class VideoParser implements Runnable {


    @Override
    public void run() {
        System.out.println("Старт потока парсинга видео : " + Thread.currentThread() + "/");

        Byte[] take = new Byte[0];
        while (true) {
            try {
                take = MainApp.getFrameQueue().take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (take[0] == -1 && take[1] == 63 &&
                    take[584] == -1 && take[585] == 63 &&
                    take.length == 1168) {
                if (MainApp.count == 0) {
                    MainApp.getController().getSpeed();
                }
                MainApp.count++;
                // читаем первый кадр посылки и записываем в очередь series
                consumeFrame(take, 8, 584);
                // читаем второй кадр посылки и записываем в очередь series
                consumeFrame(take, 592, 1168);
            } else {
                System.err.println("Попалась битая посылка");

            }
        }

    }

    private void consumeFrame(Byte[] take, int startFrame, int endFrame) {
        // читаем кадр посылки
        char[] chars = byteToFrame(take, startFrame, endFrame);
        //преобразуем в объект series
        XYChart.Series<Number, Number> numberSeries = frameToSeries(chars);
        try {
            MainApp.getSeriesQueue().put(numberSeries);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
