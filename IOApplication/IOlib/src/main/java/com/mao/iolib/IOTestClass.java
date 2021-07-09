package com.mao.iolib;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * I/O
 * NIO
 * OKio
 */

public class IOTestClass {

    public static void main(String[] args) {

        //outputStream1();
        //inputStream2();
        //inputStream3();
        //outputStream4();
        //copyFile5();
        //socket6();
        //socket7();
        //NIO8();
        NIO9();
        //OKioTest10();
        //OKioCopyFile11();
    }


    //输出流输出文件
    static void outputStream1() {
        // Java7 之后try 括号创建输入输入输出对象可以自动关闭流
        try(OutputStream outputStream =new FileOutputStream("./IOlib/FileOutput.txt") ) {
            outputStream.write('h');
            outputStream.write('i');

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //文件读取 字符
    static void inputStream2(){

        try(InputStream inputStream =new FileInputStream("./IOlib/FileOutput.txt") ) {
            //字符读取强转
            System.out.println((char)inputStream.read());
            System.out.println((char)inputStream.read());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //文件读取 字节 读取一行
    static void inputStream3(){

        try(InputStream inputStream =new FileInputStream("./IOlib/FileOutput.txt");
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader)) {
            System.out.println(bufferedReader.readLine());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //文件写入 Buffer
    static void outputStream4(){

        try(OutputStream outputStream =new FileOutputStream("./IOlib/FileOutput.txt");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            bufferedOutputStream.write('w');
            bufferedOutputStream.write('w');

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //文件复制
    static void copyFile5(){

        try(InputStream inputStream =new BufferedInputStream(new FileInputStream("./IOlib/FileOutput.txt"));
            OutputStream outputStream =new BufferedOutputStream(new FileOutputStream("./IOlib/new_CopyFileOutput.txt"))){

            byte[] bytes = new byte[1024];
            int read = 0;

            while((read = inputStream.read(bytes))!=-1){
                //输出到新文件
                outputStream.write(bytes,0,read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //客户端请求 socket
    static void socket6(){

        try(Socket socket = new Socket("maoqitian.com",80);
            //获取 socket 输入输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            bufferedWriter.write("GET / HTTP/1.1\n" +
                    "Host: www.example.com\n\n");
            bufferedWriter.flush();
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                System.out.println(message);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //服务端 socket
    static void socket7(){

        try(ServerSocket serverSocket = new ServerSocket(80);
            //阻塞式 等待连接
            Socket socket = serverSocket.accept();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            //返回连接数据

            bufferedWriter.write("HTTP/1.1 200 OK\n" +
                    "Date: Mon, 23 May 2005 22:38:34 GMT\n" +
                    "Content-Type: text/html; charset=UTF-8\n" +
                    "Content-Length: 138\n" +
                    "Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT\n" +
                    "Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)\n" +
                    "ETag: \"3f80f-1b6-3e1cb03b\"\n" +
                    "Accept-Ranges: bytes\n" +
                    "Connection: close\n" +
                    "\n" +
                    "<html>\n" +
                    "  <head>\n" +
                    "    <title>An Example Page</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <p>Hello World, this is a very simple HTML document.</p>\n" +
                    "  </body>\n" +
                    "</html>\n\n");
            bufferedWriter.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //NIO 文件读取 管道可以双向操作 可以读可以写
    static void NIO8(){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("./IOlib/FileOutput.txt","r");
            //获取数据管道
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //将数据读入 ByteBuffer
            channel.read(byteBuffer);
            //移动 ByteBuffer position
            byteBuffer.flip();
            //解码获取byteBuffer中读取的数据
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            //重置 buffer 的 position 和 limit
            byteBuffer.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //socket 服务 NIO
    static void NIO9(){
        try {
            //开启Socket通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //socket 端口
            serverSocketChannel.bind(new InetSocketAddress(80));
            //非阻塞式
            serverSocketChannel.configureBlocking(false);
            //获取一个监听器 观察连接
            Selector selector = Selector.open();
            //注册监听器 接收（变成非阻塞式）
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                selector.select();
                for (SelectionKey key :selector.keys()){
                    //如果为接收key
                    if(key.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while(socketChannel.read(byteBuffer) != -1){
                            //移动 ByteBuffer position
                            byteBuffer.flip();
                            //socket接收到什么消息则输出什么消息
                            socketChannel.write(byteBuffer);
                            //重置 buffer 的 position 和 limit
                            byteBuffer.clear();
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //OKio channel，⽽且是单向的，输⼊源叫 Source，输出⽬标叫 Sink
    //底层也是 基于 原生 IO 的封装
    static void OKioTest10(){
        try (BufferedSource buffer = Okio.buffer(Okio.source(new File("./IOlib/FileOutput.txt")))){
            System.out.println(buffer.readUtf8Line());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // OKio 复制文件
    static void OKioCopyFile11(){

        try (BufferedSource bufferSource = Okio.buffer(Okio.source(new File("./IOlib/FileOutput.txt")));
             BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File("./IOlib/OKioCopyFile1.txt")))){
            bufferedSink.writeAll(bufferSource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}