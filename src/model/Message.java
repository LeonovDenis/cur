package model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import static main.MainApp.getDetectorComandQueue;
import static math.Utils.byteObjectByte;
import static math.Utils.returnReversArray;

public class Message {

    private final byte[] header = new byte[]{(byte) 0xAA, 0x55, 0x55, (byte) 0xAA};
    private final byte func;
    private byte[] data;
    private byte[] mes;

    public Message(byte func) {
        this.func = func;
    }

    public Message(byte func, byte[] data) {
        this(func);
        this.data = data;
        getMes();
    }

    public void getMes() {
        mes = new byte[8 + data.length];
        ByteBuffer order = ByteBuffer.wrap(mes).order(ByteOrder.LITTLE_ENDIAN);
        order.put(header);
        order.put(func);
        order.put(new byte[]{0x00, 0x00, 0x00});

        order.put(returnReversArray(data));
        order.flip();

        try {
            getDetectorComandQueue().put(byteObjectByte(order.array()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
