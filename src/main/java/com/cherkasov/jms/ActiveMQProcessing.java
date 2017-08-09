package com.cherkasov.jms;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import javax.jms.MessageProducer;
import lombok.extern.log4j.Log4j2;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.logging.log4j.message.FormattedMessage;

@Log4j2
public class ActiveMQProcessing implements AutoCloseable {

  private static String DEF_QUEUE = "test.in";

  private final ActiveMQConnectionFactory connectionFactory;
  private Connection connection = null;
  private Session session = null;
  //  private Queue<String> _messagesQueue;
  private boolean activeState = true;

  /**
   * Конструктор используется в случае, когда брокер не требует авторизации.
   */
  public ActiveMQProcessing(String url) {
    this(url, null, null);
  }

  /**
   * Конструктор используется в случае, когда брокер требует авторизацию.
   */
  public ActiveMQProcessing(String url, String user, String password) {
    if (user != null && !user.isEmpty() && password != null) {
      connectionFactory = new ActiveMQConnectionFactory(url, user, password);
    } else {
      connectionFactory = new ActiveMQConnectionFactory(url);
    }

//    _messagesQueue = new PriorityBlockingQueue<String>();
  }

  /**
   * Инициализация producer-а.
   */
  private MessageProducer init() throws JMSException {
    connection = connectionFactory.createConnection();
    connection.start();
    //todo check
    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Destination dest = session.createQueue(DEF_QUEUE);
    return session.createProducer(dest);
  }

  /**
   * Метод цикла отправки сообщений в брокер.
   */
  public void send(String line) {

    /*          saveXmlAttachment("тело запроса", xmlDoc);
            log.debug(" . http-method='{}' BODY:[{}]", method, xmlDoc);*/
    try {
      log.debug("Init producer...");
      MessageProducer producer = init();
      log.debug("Producer successfully initialized");

      try {
        if (line != null) {
          Message msg = session.createTextMessage(line);
          saveXmlAttachment("тело запроса", line);

          msg.setObjectProperty("Created", (new Date()).toString());
          producer.send(msg);
          System.out.println("Message " + msg.getJMSMessageID() + " was sent");
        }

      } catch (JMSException e) {
        log.error(new FormattedMessage("Ошибка отправки сообщений в очередь {}", DEF_QUEUE), e);
//        e.printStackTrace();
        session.close();
        connection.close();
//        producer = init(); // trying to reconnect
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void close() {
//    activeState = false;
    if (connection != null) {
      try {
        connection.close();
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    String url = "tcp://localhost:61616"; // url коннектора брокера
    try (ActiveMQProcessing producer = new ActiveMQProcessing(url);)
//        JmsConsumer consumer = new JmsConsumer(url, "test.in"))
    {
      producer.init();
//      consumer.init();

      BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
      String line;
      while (!(line = rdr.readLine())
          .equalsIgnoreCase("stop")) // для выхода нужно набрать в консоли stop
      {
        producer.send(line);
      }
      System.out.println("Bye!");
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

}
