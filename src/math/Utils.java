package math;


import javafx.scene.chart.XYChart;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

public class Utils {
    /**
     * Конвертирование из Byte -> byte
     *
     * @param b Массив Byte
     * @return Массив примитивов byte
     */
    public static byte[] ByteObjTobyte(Byte[] b) {

        byte[] data = new byte[b.length];
        int j = 0;
        for (Byte byt : b)
            data[j++] = byt.byteValue();
        return data;
    }

    /**
     * Конвертирование из byte -> Byte
     *
     * @param b Массив примитивов byte
     * @return Массив Byte
     */
    public static Byte[] byteObjectByte(byte[] b) {

        Byte[] data = new Byte[b.length];
        int i = 0;
        for (byte byt : b) {
            data[i++] = byt;
        }
        return data;
    }

    /**
     * Перевод массива char в объект series
     *
     * @param b массив char
     * @return объект series
     */
    public static XYChart.Series<Number, Number> frameToSeries(char[] b) {

        XYChart.Series<Number, Number> numberSeries = new XYChart.Series<>();
        int i = 0;
        for (char bb : b) {
            numberSeries.getData().add(new XYChart.Data<>(++i, (int) bb));
        }
        return numberSeries;
    }

    /**
     * @param b          Исходный массив Byte видеопосылки
     * @param startFrame начало кадра
     * @param endFrame   конец кадра
     * @return кадр в виде char массива
     */
    public static char[] byteToFrame(Byte[] b, int startFrame, int endFrame) {
        char[] x = new char[288];
        int k = 0;
        for (int i = startFrame; i < endFrame; i += 2) {
            x[k++] = ByteBuffer.wrap(new byte[]{b[i], b[i + 1]}).order(ByteOrder.LITTLE_ENDIAN).getChar();
        }
        return x;
    }



    public static byte[] returnReversArray(byte[] arr) {
        int j = 0;
        byte[] res = new byte[arr.length]; //Создаём временный массивчик
        for (int i = arr.length - 1; i >= 0; i--, j++) {
            res[j] = arr[i];

        }
        return res;
    }

    public static byte[] intToByteChar(int i) {

        byte[] b = new byte[2];
        CharBuffer cBuffer = ByteBuffer.wrap(b).asCharBuffer();
        cBuffer.put((char) i);
        return b;
    }
    public static byte[] intToByteInt(int i) {

        byte[] b = new byte[4];
        IntBuffer cBuffer = ByteBuffer.wrap(b).asIntBuffer();
        cBuffer.put(i);
        return b;
    }
}
