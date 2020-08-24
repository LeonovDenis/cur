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
        int numZero = 0;
        int anInt = 0;
        String string = "";
        if (error != 0) {
            string = "Error(0" + error + ")";

        } else {

            if (data.length == 2) {
                numZero = 8;
                anInt = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getChar();
            } else if (data.length == 4) {
                numZero = 32;
                anInt = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getInt();

            } else if (data.length == 1) {
                numZero = 4;
                anInt = data[0];
            }
            string = String.valueOf(anInt);
        }
        switch (funktion) {
            case 0x01:
                String s1 = "", s2 = "", string1 = "";

                if (error == 0) {
                    string1 = Integer.toBinaryString(anInt);

                    if (string1.length() > 5) {
                        string1 = string1.substring(string1.length() - 4);
                    } else {
                        do {
                            string1 = "0" + string1;
                        } while (string1.length() < 4);
                    }
                    s1 = "Подано";
                    s2 = "Отключено";
                } else {
                    string1 = "1111";
                    s1 = s2 = string;
                }
                if (string1.charAt(0) == 49) {
                    MainApp.getController().getLable1VDD().setText(s1);
                } else if (string1.charAt(0) == 48) {
                    MainApp.getController().getLable1VDD().setText(s2);
                }
                if (string1.charAt(1) == 49) {
                    MainApp.getController().getLable1VDDA().setText(s1);
                } else if (string1.charAt(1) == 48) {
                    MainApp.getController().getLable1VDDA().setText(s2);
                }
                if (string1.charAt(2) == 49) {
                    MainApp.getController().getLable1UC().setText(s1);
                } else if (string1.charAt(2) == 48) {
                    MainApp.getController().getLable1UC().setText(s2);
                }
                if (string1.charAt(3) == 49) {
                    MainApp.getController().getLable1VU4().setText(s1);
                } else if (string1.charAt(3) == 48) {
                    MainApp.getController().getLable1VU4().setText(s2);
                }
                break;
            case 0x02:
                MainApp.getController().getLable2().setText(string);
                break;
            case 0x03:
                String string2 = "Error";
                if (error == 0) {
                    if (string.equalsIgnoreCase("0")) {
                        string2 = "0-ВЗН";
                    } else if (string.equalsIgnoreCase("2")) {
                        string2 = "2-Bypass";
                    } else if (string.equalsIgnoreCase("3")) {
                        string2 = "3-Bypass";
                    } else if (string.equalsIgnoreCase("4")) {
                        string2 = "4-Bypass";
                    } else if (string.equalsIgnoreCase("6")) {
                        string2 = "6-Bypass";
                    } else if (string.equalsIgnoreCase("7")) {
                        string2 = "7-Bypass";
                    }
                    MainApp.getController().getLable3().setText(string2);
                } else {
                    MainApp.getController().getLable3().setText(string);
                }
                break;
            case 0x04:
                String string4 = "Error";
                if (error == 0) {
                    if (string.equalsIgnoreCase("0")) {
                        string4 = "Прямое";
                    } else if (string.equalsIgnoreCase("1")) {
                        string4 = "Обратное";
                    }
                    MainApp.getController().getLable4().setText(string4);
                } else {
                    MainApp.getController().getLable4().setText(string);
                }

                break;
            case 0x05:
                String string3 = "Error";
                if (error == 0) {
                    if (string.equalsIgnoreCase("0")) {
                        string3 = "0.2";
                    } else if (string.equalsIgnoreCase("1")) {
                        string3 = "0.4";
                    } else if (string.equalsIgnoreCase("2")) {
                        string3 = "0.6";
                    } else if (string.equalsIgnoreCase("3")) {
                        string3 = "0.8";
                    } else if (string.equalsIgnoreCase("4")) {
                        string3 = "1.0";
                    } else if (string.equalsIgnoreCase("5")) {
                        string3 = "1.2";
                    } else if (string.equalsIgnoreCase("6")) {
                        string3 = "1.4";
                    } else if (string.equalsIgnoreCase("7")) {
                        string3 = "1.6";
                    }
                    MainApp.getController().getLable5().setText(string3);
                } else {
                    MainApp.getController().getLable5().setText(string);
                }
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
                String string5 = "Error";
                if (error == 0) {
                    if(string.equalsIgnoreCase("0")){
                        string5="Выключено" ;
                    }else if(string.equalsIgnoreCase("1")){
                        string5="Включено" ;
                    }
                    MainApp.getController().getLable12().setText(string5);
                } else {
                    MainApp.getController().getLable12().setText(string);
                }
                break;
            case 0x0D:
                //MainApp.getController().getLable13().setText(string);
                break;
            case 0x0E:
                if (error == 0) {
                    MainApp.getController().getLable14().setText(string.equals("1") ? "Идет сброс" : "Ок");
                } else {
                    MainApp.getController().getLable14().setText(string);
                }

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
