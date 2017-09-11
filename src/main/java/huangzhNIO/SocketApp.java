package huangzhNIO;

import org.junit.Test;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Will on 2017/9/11.
 */
public class SocketApp {
    @Test
    public void ServerSocket() throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket = serverSocket.bind(new InetSocketAddress(9110));
        serverSocket.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        while(selector.select() > 0){
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey =iterator.next();
                if(selectionKey.isAcceptable()){
                    System.out.println("acceptable");
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    if(accept.isConnected()){
                        System.out.println("aaaa");
                        accept.register(selector,SelectionKey.OP_READ);
                    }
                }
                /*客户端用注册连接的socket
                if(selectionKey.isConnectable()){
                    System.out.println("connectable");
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ);
                }*/
                if(selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int b;
                    while((b = channel.read(buffer))!=-1){
                        buffer.flip();
                        System.out.println(new String(buffer.array()));
                        buffer.clear();
                    }
                }
                iterator.remove();
            }
        }
    }
    @Test
    public void ClientSocket() throws IOException {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9110));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Test".getBytes());
        buffer.flip();
        socket.write(buffer);
        socket.close();
    }
}
