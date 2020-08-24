package thread;

import java.net.InetAddress;

public abstract class Sender implements Runnable {

    protected final InetAddress addr;
    protected final int port;

    Sender(InetAddress addr, int port) {
        this.addr = addr;
        this.port = port;
    }

}
