package com.vtb.vladislav.spring.data.lesson8.homework;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.vtb.vladislav.spring.data.lesson8.homework.exceptions.ResourceNotFoundException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleManagementApp {
    private static final String PROCESSING_ORDERS_QUEUE = "processingOrdersQueue";
    private static final String EXCHANGE_FOR_READY_ORDERS = "readyOrdersExchanger";

    // каждый раз, когда в очередь попадает новый заказ администратору предлагается
    // ввести команду для обработки заказа (такой вариант кажется мне не совсем корректным)
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        List<Long> orders = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println(" [*] Ожидание новых заказов");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" Получен заказ №" + message);
            orders.add(Long.parseLong(message));

            System.out.println("Список заказов: ");
            for (Long order : orders) {
                System.out.print(order + " ");
            }
            System.out.println();

            while (orders.size() != 0) {
                System.out.println("Введите команду (exit - для выхода): ");
                String query = sc.nextLine();
                if (query.equals("exit")) {
                    break;
                }
                if (query.startsWith("/готово")) {
                    Long orderId = Long.parseLong(query.split(" ")[1]);
                    if (orders.contains(orderId)) {
                        orders.remove(orderId);
                        System.out.println("Заказ №" + orderId + " был успешно подготовлен");
                        channel.basicPublish(EXCHANGE_FOR_READY_ORDERS, "", null, orderId.toString().getBytes());
                    } else {
                        throw new ResourceNotFoundException("Такого заказа не существует!");
                    }
                } else {
                    System.out.println("Команда не распознана! Пожалуйста, повторите попытку");
                }
            }
        };

        channel.basicConsume(PROCESSING_ORDERS_QUEUE, true, deliverCallback, consumerTag -> {
        });
    }
}
