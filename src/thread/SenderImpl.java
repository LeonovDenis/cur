package thread;

import main.MainApp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import static math.Utils.ByteObjTobyte;
import static math.Utils.byteObjectByte;


public class SenderImpl extends Sender {



    private final byte[] receiveData = new byte[248];
    private DatagramSocket clientSocket = null;
    private byte[] data = null;
    private byte[] resData = null;
    private DatagramPacket sendPacket = null;
    private DatagramPacket receivePacket = null;

    public SenderImpl(InetAddress addr, int port) {
        super(addr,port);
    }

    @Override
    public void run() {

        try {
            System.out.println("Старт потока отправки : " + Thread.currentThread() + "/");
            //Инициализация датаграмм сокета
            clientSocket = new DatagramSocket(Integer.parseInt(MainApp.getAppProps().getProperty("potrApp")));
            //
            while (true) {

                //Запрос команды на отправку
                //Перевод в примитив
                Byte[] command = MainApp.getDetectorComandQueue().take();

                data = ByteObjTobyte( command);

                //Формирование пакета байт
                sendPacket = new DatagramPacket(data, data.length, addr, port);
                //Отправка пакета клиенту
                clientSocket.send(sendPacket);

                //Инициализация датаграм пакета ответного сообщения
                receivePacket = new DatagramPacket(receiveData, receiveData.length);

                try {
                    //Задание предельного времени ожидания ответа
                    clientSocket.setSoTimeout(300);

                    //ожидание ответа
                    clientSocket.receive(receivePacket);
                    //массив байт ответного сообщения

                    byte[] bytes = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());

                    //Переводим в объект
                    //Помещаем в очередь
                    Byte[] byteObjects = byteObjectByte(bytes);
                    MainApp.getDetectorAnswerQueue().put(byteObjects);

                    /**
                     * если время ожидания вышло
                     */
                } catch (SocketTimeoutException e) {
                    System.out.println("Истекло время ожидания, прием данных закончен");

                }

            }

        } catch (Exception e) {
            System.out.println("Ошибка отправляльщика");
            e.printStackTrace();

        } finally {
            if (clientSocket != null) clientSocket.close();
        }

    }
}
