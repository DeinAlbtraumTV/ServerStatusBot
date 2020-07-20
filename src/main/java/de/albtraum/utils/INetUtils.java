package de.albtraum.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class INetUtils {

    public static long ping (String host) throws IOException {
        int port = 80;
        long timeToRespond = 0;

        InetAddress inetAddress = InetAddress.getByName(host);

        if(inetAddress.isReachable(5000)) {

            InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);

            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(true);

            Date start = new Date();
            if (sc.connect(socketAddress)) {
                Date stop = new Date();
                timeToRespond = (stop.getTime() - start.getTime());
            }

            sc.close();
        } else {
            return 9999L;
        }
        return timeToRespond;
    }
}
