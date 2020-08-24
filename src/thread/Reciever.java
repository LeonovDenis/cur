package thread;

public abstract class Reciever implements Runnable{

    protected final int port;

    public Reciever(int port) {
        this.port = port;
    }
}
