package thread;

import main.MainApp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static math.Utils.byteObjectByte;

public class RecieverImpl extends Reciever {

    byte[] receiveData = new byte[1168];
    DatagramSocket serverSocket = null;
    DatagramPacket receivePacket = null;


    public RecieverImpl(int port) {
        super(port);
    }

    @Override
    public void run() {

        try {
            //Открытие датаграмм сокета
            serverSocket = new DatagramSocket(port);
            System.out.println("Запуск серверного потока: " + Thread.currentThread() + "/");

            /**
             * Слушаем канал пока не будет команды на стоп
             */
            while (true) {
                //Инициализация датаграм пакета
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                //получаем пакет
                serverSocket.receive(receivePacket);
                // data = receivePacket.getData();

                //Переводим в объект

                Byte[] bytes = byteObjectByte(receiveData);
                //Помещаем в очередь
                MainApp.getFrameQueue().put(bytes);

            }
        } catch (Exception e) {
            System.out.println("Ошибка в получателе");
            e.printStackTrace();

        } finally {
            //Закрытие сокета
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Сервер сокет закрыт.");
            }
        }

    }


}
