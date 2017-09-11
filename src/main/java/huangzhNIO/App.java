package huangzhNIO;

import org.junit.Test;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Hello world!
 *
 */
public class App 
{

    @Test
    public void test1(){
        System.out.println("TEST01");
    }

    @Test
    public void test2() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("a.txt","rw");
        RandomAccessFile randomAccessFile1 = new RandomAccessFile("a.txt","r");
        FileChannel channel = randomAccessFile.getChannel();
        FileChannel channel1 = randomAccessFile1.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("Test".getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);

        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        byteBuffer.clear();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        channel1.read(byteBuffer);
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array()));
        System.out.println("TEST01");
    }
    @Test
    public void test3() throws CharacterCodingException {
        Charset gbk = Charset.forName("GBK");
        CharsetEncoder charsetEncoder = gbk.newEncoder();
        CharsetDecoder charsetDecoder = gbk.newDecoder();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("测试");
        charBuffer.flip();
        ByteBuffer encode = charsetEncoder.encode(charBuffer);
        CharBuffer charBuffer1 = charsetDecoder.decode(encode);
        System.out.println(charBuffer1.toString());

    }

}
