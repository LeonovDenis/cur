package thread;

import main.MainApp;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import static math.Utils.byteObjectByte;

public class StatusCheckerSender implements Runnable {

    private final ByteBuffer buff = ByteBuffer.allocate(8);
    byte[] header = {(byte) 0xAA, (byte) 0x55, (byte) 0x55, (byte) 0xAA};
    byte[] zero = {(byte) 0x00, (byte) 0x00, (byte) 0x00};

    @Override
    public void run() {
        while (true) {
            for (int i = 1; i < 18; i++) {
                //заголовок сообщения
                buff.put(header);
                //функция
                buff.put((byte) i);
                //резерв+ошибка
                buff.put(zero);
                buff.flip();
                try {
                    MainApp.getDetectorComandQueue().put(byteObjectByte(buff.array()));
                    buff.rewind();
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
