package thread;

import javafx.application.Platform;
import main.MainApp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

public class StatusCheckerResiever implements Runnable {


    @Override
    public void run() {
        while (true) {
            try {
                Byte[] take = MainApp.getDetectorAnswerQueue().take();
                int length = take.length;
                byte funktion = take[4];
                byte error = take[7];
                byte[] data = new byte[0];
                if (length > 8) {
                    data = new byte[length - 8];
                    int i = 0;
                    for (int j = 8; j < length; j++) {
                        data[i++] = take[j];
                    }
                }
                byte[] finalData = data;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setItInFrame(funktion, error, finalData);
                    }
                });

                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setItInFrame(byte funktion, byte error, byte[] data) {
        int anInt = 0;
        String string = "";
        if (error != 0) {
            string = "Error(" + error + ")";
        } else {

            if (data.length == 2) {
                anInt = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getChar();
            } else if (data.length == 4) {
                anInt = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getInt();

            } else if (data.length == 1) {
                anInt = data[0];
            }
            string = String.valueOf(anInt);
        }
        switch (funktion) {
            case 0x01:
                String string1 = Integer.toBinaryString(anInt);
                if (string1.charAt(0) == 1) {
                    MainApp.getController().getLable1VDD().setText("Подано");
                } else if (string1.charAt(0) == 0) {
                    MainApp.getController().getLable1VDD().setText("Отключено");
                } else if (string1.charAt(1) == 1) {
                    MainApp.getController().getLable1VDDA().setText("Подано");
                } else if (string1.charAt(1) == 0) {
                    MainApp.getController().getLable1VDDA().setText("Отключено");
                } else if (string1.charAt(2) == 1) {
                    MainApp.getController().getLable1UC().setText("Подано");
                } else if (string1.charAt(2) == 0) {
                    MainApp.getController().getLable1UC().setText("Отключено");
                } else if (string1.charAt(3) == 1) {
                    MainApp.getController().getLable1VU4().setText("Подано");
                } else if (string1.charAt(3) == 0) {
                    MainApp.getController().getLable1VU4().setText("Отключено");
                }
                break;
            case 0x02:
                MainApp.getController().getLable2().setText(string);
                break;
            case 0x03:
                MainApp.getController().getLable3().setText(string);
                break;
            case 0x04:
                MainApp.getController().getLable4().setText(string);
                break;
            case 0x05:
                MainApp.getController().getLable5().setText(string);
                break;
            case 0x06:
                MainApp.getController().getLable6().setText(string);
                break;
            case 0x07:
                MainApp.getController().getLable7().setText(string);
                break;
            case 0x08:
                MainApp.getController().getLable8().setText(string);
                break;
            case 0x09:
                MainApp.getController().getLable9().setText(string);
                break;
            case 0x0A:
                MainApp.getController().getLable10().setText(string);
                break;
            case 0x0B:
                MainApp.getController().getLable11().setText(string);
                break;
            case 0x0C:
                MainApp.getController().getLable12().setText(string);
                break;
            case 0x0D:
                //MainApp.getController().getLable13().setText(string);
                break;
            case 0x0E:
                MainApp.getController().getLable14().setText(string.equals("1") ? "Идет сброс" : "Ок");
                break;
            case 0x0F:
                MainApp.getController().getLable15().setText(string);
                break;
            case 0x10:
                MainApp.getController().getLable16().setText(string);
                break;
            case 0x11:
                MainApp.getController().getLable17().setText(string);
                break;

        }

    }
}
