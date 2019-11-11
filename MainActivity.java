package com.example.rabbitmqimp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class MainActivity extends AppCompatActivity {

    String QUEUE_NAME = "hello";
    ConnectionFactory factory = new ConnectionFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Thread thread = new Thread(new Runnable(){
            public void run() {
                try {
                    System.out.println("TESTING");
                    factory.setHost("155.230.***.***"); // Your ip adress
                    factory.setUsername("***"); // Your username
                    factory.setPassword("***"); // YOur Password
                    Connection connection;
                    Channel channel;
                    connection = factory.newConnection();
                    channel = connection.createChannel();

                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    String message = "Send Manish";
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                    System.out.println(" [x] Sent '" + message + "'");
                    channel.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Connection was refused"+e.getLocalizedMessage());
                }
            }
        });

        thread.start();




    }
}

