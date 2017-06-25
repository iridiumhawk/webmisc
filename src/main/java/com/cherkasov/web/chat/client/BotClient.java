package com.cherkasov.web.chat.client;


import com.javarush.test.level30.lesson15.big01.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hawk on 21.07.2016.
 */
public class BotClient extends Client {

    private static int botsCounter = 0;

    public class BotSocketThread extends SocketThread {

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            String name = "";
            String mess = "";
            String resultMess = "";


            if (message.indexOf(':') >= 0) {
                name = message.split(": ")[0];
                mess = message.split(": ")[1].toLowerCase();


                SimpleDateFormat dateFormat = null;
                Calendar calendar = Calendar.getInstance();

                switch (mess) {

                    case "дата": //отправить сообщение содержащее текущую дату в формате "d.MM.YYYY";
                        dateFormat = new SimpleDateFormat("d.MM.YYYY");
                        resultMess = dateFormat.format(calendar.getTime());
                        break;
                    case "день"://– в формате"d";.
                        dateFormat = new SimpleDateFormat("d");
                        resultMess = dateFormat.format(calendar.getTime());
                        break;
                    case "месяц"://- "MMMM";
                        dateFormat = new SimpleDateFormat("MMMM");
                        resultMess = dateFormat.format(calendar.getTime());
                        break;
                    case "год":// "YYYY";
                        dateFormat = new SimpleDateFormat("YYYY");
                        resultMess = dateFormat.format(calendar.getTime());
                        break;
                    case "время"://- "H:mm:ss";
                        dateFormat = new SimpleDateFormat("H:mm:ss");
                        resultMess = dateFormat.format(calendar.getTime());
                        break;
                    case "час"://- "H";
                        dateFormat = new SimpleDateFormat("H");
                        resultMess = dateFormat.format(calendar.getTime());
                        break;
                    case "минуты":// - "m";
                        dateFormat = new SimpleDateFormat("m");
                        resultMess = dateFormat.format(calendar.getTime());
                        break;
                    case "секунды":// - "s".
                        dateFormat = new SimpleDateFormat("s");
                        resultMess = dateFormat.format(calendar.getTime());
                        break;

                    default:
                        return;
                }

                sendTextMessage("Информация для " + name + ": " + resultMess );
            }
        }
    }


    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSentTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        if (botsCounter == 99) {
            botsCounter = 0;
        }

        return "date_bot_" + botsCounter++;
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }

}
/*Сегодня будем реализовывать класс BotSocketThread, вернее переопределять некоторые
его методы, весь основной функционал он уже унаследовал от SocketThread.
19.1.	Переопредели метод clientMainLoop():
19.1.1.	С помощью метода sendTextMessage() отправь сообщение с текстом
"Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды."
19.1.2.	Вызови реализацию clientMainLoop() родительского класса.

19.2.	Переопредели метод processIncomingMessage(String message). Он должен
следующим образом обрабатывать входящие сообщения:
19.2.1.	Вывести в консоль текст полученного сообщения message.
19.2.2.	Получить из message имя отправителя и текст сообщения. Они разделены ": ".
19.2.3.	Отправить ответ в зависимости от текста принятого сообщения. Если текст
сообщения:
"дата" – отправить сообщение содержащее текущую дату в формате "d.MM.YYYY";
"день" – в формате"d";
"месяц" - "MMMM";
"год" - "YYYY";
"время" - "H:mm:ss";
"час" - "H";
"минуты" - "m";
"секунды" - "s".
Указанный выше формат используй для создания объекта SimpleDateFormat. Для
получения текущей даты необходимо использовать класс Calendar и метод
getTime().

Ответ должен содержать имя клиента, который прислал запрос и ожидает ответ,
например, если Боб отправил запрос "время", мы должны отправить ответ
"Информация для Боб: 12:30:47".
Наш бот готов. Запусти сервер, запусти бота, обычного клиента и убедись, что все работает правильно.
Помни, что message бывают разных типов и не всегда содержат ":"*/


/*18.3.3.	getUserName(), метод должен генерировать новое имя бота, например:
date_bot_XX, где XX – любое число от 0 до 99. Этот метод должен возвращать
каждый раз новое значение, на случай, если на сервере захотят
зарегистрироваться несколько ботов, у них должны быть разные имена.
18.4.	Добавь метод main. Он должен создавать новый объект BotClient и вызывать у
него метод run().*/