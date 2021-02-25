import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimerTask;

public class AddressAdvert extends TimerTask
{
    private DatagramSocket socket;
    private byte[] buf;
    private InetAddress mcastAddress;


    AddressAdvert(DatagramSocket socket, InetAddress mcastAddress, int servicePort)
    {
        this.socket = socket;
        String stringAddress = "";
        try
        {
           stringAddress = InetAddress.getLocalHost().toString(); 
        }
        catch (UnknownHostException e)
        {
            System.out.println("Couldn't find host!");
        }
        
        buf = (stringAddress.split("/")[1] + " " + servicePort).getBytes();
        this.mcastAddress = mcastAddress;
    }

    @Override
    public void run() 
    {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, mcastAddress, socket.getPort());
        try
        {
            socket.send(packet);
        }
        catch (IOException e)
        {
            System.out.println("Couldn't send packet through socket!");;
        }
    }
}
