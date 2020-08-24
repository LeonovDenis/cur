package main;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import model.Message;
import java.util.concurrent.TimeUnit;
import static main.MainApp.isVideoStoped;
import static math.Utils.intToByteChar;
import static math.Utils.intToByteInt;

public class Controller {


    static XYChart.Series<Number, Number> series;
    //////////////////////////////////////
    @FXML
    Label kadr;
    @FXML
    Button start;
    @FXML
    Button stop;
    @FXML
    Label counter;
    @FXML
    Button startVideo;
    @FXML
    private TextField header;
    @FXML
    private TextField func;
    @FXML
    private TextField reserv;
    @FXML
    private TextField error;
    @FXML
    private TextField data;
    @FXML
    private TextField ipARM;
    @FXML
    private TextField portARM;
    @FXML
    private TextField portVideo;
    @FXML
    private TextField portApl;
    @FXML
    private TextField sensTemp;
    @FXML
    private TextField VDDA;
    @FXML
    private TextField VDD;
    @FXML
    private TextField reset;
    @FXML
    private TextField uprDesel;
    @FXML
    private TextField UC;
    @FXML
    private TextField VU4;
    @FXML
    private TextField VVA;
    @FXML
    private TextField VR0;
    @FXML
    private TextField TINT;
    @FXML
    private ChoiceBox CCC;
    @FXML
    private ChoiceBox DIR;
    @FXML
    private ChoiceBox mode;
    @FXML
    private TextField freq;
    @FXML
    private TextField sost;
    @FXML
    private CheckBox desel;
    @FXML
    private CheckBox Avu4;
    @FXML
    private CheckBox Auc;
    @FXML
    private CheckBox Avdda;
    @FXML
    private CheckBox Avdd;
    @FXML
    private TextField pause;

    @FXML
    private GridPane gridPane;

    /////////////////////////////////////
    @FXML
    private Label lable1VU4;
    @FXML
    private Label lable1UC;
    @FXML
    private Label lable1VDDA;
    @FXML
    private Label lable1VDD;

    @FXML
    private Label lable2;
    @FXML
    private Label lable3;
    @FXML
    private Label lable4;
    @FXML
    private Label lable5;
    @FXML
    private Label lable6;
    @FXML
    private Label lable7;
    @FXML
    private Label lable8;
    @FXML
    private Label lable9;
    @FXML
    private Label lable10;
    @FXML
    private Label lable11;
    @FXML
    private Label lable12;
    @FXML
    private Label lable13;
    @FXML
    private Label lable14;
    @FXML
    private Label lable15;
    @FXML
    private Label lable16;
    @FXML
    private Label lable17;


    /////////////////////////////////////
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private javafx.scene.chart.AreaChart<Number, Number> AreaChart;

    private MainApp mainApp;

    @FXML
    private void initialize() {

        series = new XYChart.Series<>();

        this.AreaChart.setAnimated(false);
        this.AreaChart.getData().add(series);

        initHandlers();


    }

    private void initHandlers() {

        UC.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().getName().equalsIgnoreCase("ENTER")) {
                    UCTiped(Integer.parseUnsignedInt(UC.getText()));
                }

            }
        });

        VR0.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().getName().equalsIgnoreCase("ENTER")) {
                    VR0Tiped(Integer.parseUnsignedInt(VR0.getText()));
                }

            }
        });

        VVA.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().getName().equalsIgnoreCase("ENTER")) {
                    VVATiped(Integer.parseUnsignedInt(VVA.getText()));
                }

            }
        });

        VU4.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().getName().equalsIgnoreCase("ENTER")) {
                    VU4Tiped(Integer.parseUnsignedInt(VU4.getText()));
                }

            }
        });

        TINT.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().getName().equalsIgnoreCase("ENTER")) {
                    TINTTiped(Integer.parseUnsignedInt(TINT.getText()));
                }

            }
        });

        pause.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().getName().equalsIgnoreCase("ENTER")) {
                    pauseTiped(Integer.parseUnsignedInt(pause.getText()));
                }

            }
        });

        freq.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().getName().equalsIgnoreCase("ENTER")) {
                    freqTiped(Integer.parseUnsignedInt(freq.getText()));
                }

            }
        });

        initMode();
        initDIR();
        initCCC();
        initdesel();
        init1uc();
        init1vdd();
        init1vdda();
        init1VU4();

    }
    @FXML
    private void startVideoR(){
    boolean stp=isVideoStoped;

        if(stp==false){startVideo.setText("Остановить видео");
            isVideoStoped=true;}
        else{startVideo.setText("Старт видео");
            isVideoStoped=false;
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void initdesel() {
        desel.setSelected(false);
        byte[] b = new byte[1];
        desel.setOnAction(event -> {
                    if (desel.isSelected()) {
                        b[0] = 0x01;
                    } else {
                        b[0] = 0x00;
                    }
                    new Message((byte) 0x8D, b);
                    System.out.println("deselTiped");
                }
        );
    }

    private void init1VU4() {
        Avu4.setSelected(false);
        byte[] b = new byte[1];
        Avu4.setOnAction(event -> {
                    if (Avu4.isSelected()) {
                        b[0] = 0x01;
                    } else {
                        b[0] = 0x0E;
                    }
                    new Message((byte) 0x81, b);
                    System.out.println("init1VU4Tiped");
                }
        );
    }

    private void init1uc() {
        Auc.setSelected(false);
        byte[] b = new byte[1];
        Auc.setOnAction(event -> {
                    if (Auc.isSelected()) {
                        b[0] = 0x02;
                    } else {
                        b[0] = 0x0D;
                    }
                    new Message((byte) 0x81, b);
                    System.out.println("init1AucTiped");
                }
        );
    }

    private void init1vdda() {
        Avdda.setSelected(false);
        byte[] b = new byte[1];
        Avdda.setOnAction(event -> {
                    if (Avdda.isSelected()) {
                        b[0] = 0x04;
                    } else {
                        b[0] = 0x0B;
                    }
                    new Message((byte) 0x81, b);
                    System.out.println("init1AvddaTiped");
                }
        );
    }

    private void init1vdd() {
        Avdd.setSelected(false);
        byte[] b = new byte[1];
        Avdd.setOnAction(event -> {
                    if (Avdd.isSelected()) {
                        b[0] = 0x08;
                    } else {
                        b[0] = 0x07;
                    }
                    new Message((byte) 0x81, b);
                    System.out.println("init1AvddTiped");
                }
        );
    }

    private void initCCC() {
        ObservableList<?> pass = FXCollections.observableArrayList(0.2, 0.4, 0.6, 0.8, 1.0, 1.2, 1.4, 1.6);
        CCC.setItems(pass);
        CCC.setValue(0.2);
        CCC.setOnAction(event -> {
            double newValue = (double) CCC.getValue();
            byte[] b = new byte[1];
            if (newValue == 0.2) {
                b[0] = 0x00;
            } else if (newValue == 0.4) {
                b[0] = 0x01;
            } else if (newValue == 0.6) {
                b[0] = 0x02;
            } else if (newValue == 0.8) {
                b[0] = 0x03;
            } else if (newValue == 1.0) {
                b[0] = 0x04;
            } else if (newValue == 1.2) {
                b[0] = 0x05;
            } else if (newValue == 1.4) {
                b[0] = 0x06;
            } else if (newValue == 1.6) {
                b[0] = 0x07;
            }
            new Message((byte) 0x85, b);
            System.out.println("CCCTiped");
        });
    }

    private void initDIR() {
        ObservableList<?> pass = FXCollections.observableArrayList("Прямое", "Обратное");
        DIR.setItems(pass);
        DIR.setValue("Прямое");
        DIR.setOnAction(event -> {
            String newValue = (String) DIR.getValue();
            if (newValue != null) {
                byte[] b = new byte[1];
                if (newValue.equalsIgnoreCase("Прямое")) {
                    b[0] = 0x00;
                } else if (newValue.equalsIgnoreCase("Обратное")) {
                    b[0] = 0x01;
                }
                new Message((byte) 0x84, b);
                System.out.println("DIRTiped");
            }
        });
    }

    private void initMode() {
        Separator separator = new Separator();
        ObservableList<?> pass = FXCollections.observableArrayList("0-ВЗН",
                separator,
                "2-Bypass",
                "3-Bypass",
                "6-Bypass",
                "7-Bypass");
        mode.setItems(pass);
        mode.setValue("0-ВЗН");

        mode.setOnAction(event -> {
            String newValue = (String) mode.getValue();
            if (newValue != null) {
                byte[] b = new byte[1];
                if (newValue.equalsIgnoreCase("0-ВЗН")) {
                    b[0] = 0x00;
                } else if (newValue.equalsIgnoreCase("2-Bypass")) {
                    b[0] = 0x02;
                } else if (newValue.equalsIgnoreCase("3-Bypass")) {
                    b[0] = 0x03;
                } else if (newValue.equalsIgnoreCase("6-Bypass")) {
                    b[0] = 0x06;
                } else if (newValue.equalsIgnoreCase("7-Bypass")) {
                    b[0] = 0x07;
                }
                new Message((byte) 0x83, b);
                System.out.println("modeTiped");
            }
        });
    }

    private void freqTiped(int i) {
        if (i < 0) {
            i = 0;
            freq.setText("0");

        }
        new Message((byte) 0x82, intToByteInt(i));
        System.out.println("freqTiped");
    }

    private void pauseTiped(int i) {
        if (i < 0) {
            i = 0;
            pause.setText("0");
        }
        new Message((byte) 0x86, intToByteChar(i));
        System.out.println("pauseTiped");
    }

    private void TINTTiped(int i) {
        if (i < 0) {
            i = 0;
            TINT.setText("0");
        }
        new Message((byte) 0x8A, intToByteInt(i));
        System.out.println("TINTTiped");
    }

    private void VU4Tiped(int i) {

        if (i < 2500) {
            i = 2500;
            VU4.setText("2500");

        } else if (i > 5000) {
            i = 5000;
            VU4.setText("5000");
        }
        int mirror = (int) ((5000 - i) * 0.1021);
        new Message((byte) 0x8A, intToByteChar(mirror));
        System.out.println("VU4Tiped");

    }

    private void VVATiped(int i) {
        if (i < 0) {
            i = 0;
            VVA.setText("0");

        } else if (i > 2500) {
            i = 2500;
            VVA.setText("2500");
        }
        int mirror = (int) (1.6381 * i);
        new Message((byte) 0x89, intToByteChar(mirror));
        System.out.println("VVATiped");

    }

    private void UCTiped(int i) {


        if (i < 2500) {
            i = 2500;
            UC.setText("2500");

        } else if (i > 5000) {
            i = 5000;
            UC.setText("5000");
        }

        int mirror = (int) ((5000 - i) * 0.1021);
        new Message((byte) 0x8B, intToByteChar(mirror));
        System.out.println("UCTiped");
    }


    private void VR0Tiped(int i) {

        if (i < 0) {
            i = 0;
            VR0.setText("0");

        } else if (i > 2500) {
            i = 2500;
            VR0.setText("2500");
        }
        int mirror = (int) (1.6381 * i);
        new Message((byte) 0x88, intToByteChar(mirror));
        System.out.println("VR0Tiped");
    }

    @FXML
    private void resetTiped() {
        new Message((byte) 0x8E, new byte[]{0x01});
        System.out.println("resetTiped");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Message((byte) 0x8E, new byte[]{0x00});
                System.out.println("resetAuto");
            }
        });
        t.start();
    }

    @FXML
    private void handleMode() {

    }

    @FXML
    private void handleStart() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Message((byte) 0x81, new byte[]{0x04});
                    TimeUnit.MILLISECONDS.sleep(500);
                    new Message((byte) 0x81, new byte[]{0x08});
                    TimeUnit.MILLISECONDS.sleep(500);
                    new Message((byte) 0x81, new byte[]{0x02});
                    TimeUnit.MILLISECONDS.sleep(500);
                    new Message((byte) 0x81, new byte[]{0x01});
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        start.setText("Включено");
                        stop.setText("Выключить");
                    }
                });

                System.out.println("Start");
            }
        });
        t.start();
    }

    @FXML
    private void handleStop() {
        new Message((byte) 0x81, new byte[]{0x00});
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                start.setText("Включить");
                stop.setText("Выключено");
            }
        });
        System.out.println("Stop");
    }

    public void getSpeed(){
        long sat = System.nanoTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Long k=0L;
                while (true){
                k=1000_000_000*(MainApp.count*1168)/(System.nanoTime()-sat);
                    Long finalK = k;
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                     MainApp.getController().counter.setText(MainApp.count+" frames// "+finalK +" byte/s");
                    }

                });
            }}
        }).start();
    }

    public static XYChart.Series<Number, Number> getSeries() {
        return series;
    }

    public static void setSeries(XYChart.Series<Number, Number> series) {
        Controller.series = series;
    }

    public Label getLable2() {
        return lable2;
    }

    public Label getLable3() {
        return lable3;
    }

    public Label getLable4() {
        return lable4;
    }

    public Label getLable5() {
        return lable5;
    }

    public Label getLable6() {
        return lable6;
    }

    public Label getLable7() {
        return lable7;
    }

    public Label getLable8() {
        return lable8;
    }

    public Label getLable9() {
        return lable9;
    }

    public Label getLable10() {
        return lable10;
    }

    public Label getLable11() {
        return lable11;
    }

    public Label getLable12() {
        return lable12;
    }

    public Label getLable13() {
        return lable13;
    }

    public Label getLable14() {
        return lable14;
    }

    public Label getLable15() {
        return lable15;
    }

    public Label getLable16() {
        return lable16;
    }

    public Label getLable17() {
        return lable17;
    }

    public Label getLable1VU4() {
        return lable1VU4;
    }

    public Label getLable1UC() {
        return lable1UC;
    }

    public Label getLable1VDDA() {
        return lable1VDDA;
    }

    public Label getLable1VDD() {
        return lable1VDD;
    }
}



