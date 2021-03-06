package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import thread.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.*;


public class MainApp extends Application {

    private static BlockingDeque<Byte[]> detectorComandQueue = new LinkedBlockingDeque<>(50);
    private static BlockingDeque<Byte[]> detectorAnswerQueue = new LinkedBlockingDeque(50);
    private static BlockingDeque<Byte[]> achtComandQueue = new LinkedBlockingDeque(50);
    private static BlockingDeque<Byte[]> frameQueue = new LinkedBlockingDeque(100);
    private static BlockingDeque<XYChart.Series<Number, Number>> seriesQueue = new LinkedBlockingDeque(100);
    public static boolean isVideoStoped = false;
    private static Properties appProps = new Properties();
    private Stage primaryStage;
    private Parent root;
    private static Controller controller;
    private byte[] matrix;
    private ExecutorService executor;
    public static Long count=0L;
    public static int stat=0;

    public static void main(String[] args) {
        launch(args);
    }

    public static BlockingDeque<Byte[]> getDetectorComandQueue() {
        return detectorComandQueue;
    }

    public static void setDetectorComandQueue(BlockingDeque<Byte[]> detectorComandQueue) {
        MainApp.detectorComandQueue = detectorComandQueue;
    }

    public static BlockingDeque<Byte[]> getAchtComandQueue() {
        return achtComandQueue;
    }

    public static void setAchtComandQueue(BlockingDeque<Byte[]> achtComandQueue) {
        MainApp.achtComandQueue = achtComandQueue;
    }

    public static BlockingDeque<Byte[]> getFrameQueue() {
        return frameQueue;
    }

    public static void setFrameQueue(BlockingDeque<Byte[]> frameQueue) {
        MainApp.frameQueue = frameQueue;
    }

    public static BlockingDeque<XYChart.Series<Number, Number>> getSeriesQueue() {
        return seriesQueue;
    }

    public static void setSeriesQueue(BlockingDeque<XYChart.Series<Number, Number>> seriesQueue) {
        MainApp.seriesQueue = seriesQueue;
    }

    public static BlockingDeque<Byte[]> getDetectorAnswerQueue() {
        return detectorAnswerQueue;
    }

    public static void setDetectorAnswerQueue(BlockingDeque<Byte[]> detectorAnswerQueue) {
        MainApp.detectorAnswerQueue = detectorAnswerQueue;
    }

    public static boolean isIsVideoStoped() {
        return isVideoStoped;
    }

    public static void setIsVideoStoped(boolean isVideoStoped) {
        MainApp.isVideoStoped = isVideoStoped;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Test App");

        initRootLayout();
        initProps();
        initThreads();
        initOnClose();

    }

    /**
     * Загрузка настроек
     */
    private void initProps() {
        InputStream resourceAsStream =MainApp.class.getResourceAsStream("/properties/res.properties");
        //InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("properties" + System.getProperties().getProperty("file.separator") + "res.properties");
        // String rootPath = Thread.currentThread().getContextClassLoader().getResource("properties").getPath();
       // String appConfigPath = rootPath + System.getProperties().getProperty("file.separator") + "res.properties";
        try {
            appProps.load(resourceAsStream);
        } catch (IOException e) {
            System.out.println("Не загрузились настройки");
            e.printStackTrace();
        }

    }

    /**
     * Запуск фоновых потоков для работы с портами
     */
    private void initThreads() {
        //Инициализация исполнителя потоков
       // executor = Executors.newWorkStealingPool();
        executor = Executors.newFixedThreadPool(10);
        //Старт потока обмена сообщениями с детектором
        try {
            Sender k = new SenderImpl(InetAddress.getByName(appProps.getProperty("ipArm")), Integer.valueOf(appProps.getProperty("portArm")));
            executor.submit(k);
        } catch (UnknownHostException e) {
            System.out.println("Не правильный ipARM");
            e.printStackTrace();
        }
        //Старт потока приёма видео
        Reciever r = new RecieverImpl(Integer.valueOf(appProps.getProperty("portVideo")));
        executor.submit(r);
        //Старт обработчика видео посылок
        VideoParser v = new VideoParser();
        executor.submit(v);
        //Старт отрисовщика графиков
        ChartRuner c =new ChartRuner();
        executor.submit(c);
        //Старт отправляльщика ответов детектора
        StatusCheckerSender tt=new StatusCheckerSender();
        executor.submit(tt);
        //Старт получальщика ответов детектора
        StatusCheckerResiever t=new StatusCheckerResiever();
        executor.submit(t);

    }

    /**
     * Загрузка корневой панели окна.
     * Инициализация контроллера.
     */
    private void initRootLayout() {
        try {

            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("mainPage.fxml"));
            root = loader.load();


            getMatrix(loader);


            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

            controller = loader.getController();
            //controller.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Инициализация параметров закрытия окна
     */
    private void initOnClose() {

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {

                Platform.exit();
                System.exit(0);
            }
        });

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public static Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public byte[] getMatrix() {
        return matrix;
    }

    public void setMatrix(byte[] matrix) {
        this.matrix = matrix;
    }

    public static Properties getAppProps() {
        return appProps;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }



    private void getMatrix(FXMLLoader loader) {
        ObservableMap<String, Object> namespace = loader.getNamespace();
        AnchorPane matrix = (AnchorPane) namespace.get("matrix");


        GridPane root = new GridPane();
        root.setPadding(new Insets(5));
        root.setHgap(1);
        root.setVgap(1);
        root.setMinSize(900, 120);
        root.setPrefSize(900, 120);
        root.setMaxSize(1189, 120);
        // never size the gridpane larger than its preferred size:

        root.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        root.setGridLinesVisible(true);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 144; j++) {

                if (((j % 2 == 0) && i > 4) || ((j % 2 == 1) && i < 4)) {
                    Button label = new Button(" ");
                    label.setContextMenu(new ContextMenu(new MenuItem("" + j + 1)));

                    label.setStyle("-fx-font: 3 arial; -fx-base: #17c355;");

                    label.setAlignment(Pos.BOTTOM_RIGHT);
                    root.add(label, j, i, 2, 1);
                }
            }
        }
        matrix.getChildren().add(root);
    }

}
