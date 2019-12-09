package pcbe.jms;

import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Editor implements MessageListener {
    private Session session;
    private final String destinationName = "topic/news";
    private int read;

    public static void main(String[] args) throws Exception {
        NewsDB.init();
        new Editor().init();
    }

    public void init() throws Exception {
        Context ic = null;
        ConnectionFactory cf = null;
        Connection connection = null;
        try {
            ic = getInitialContext();
            cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
            Topic topic = (Topic) ic.lookup(destinationName);

            connection = cf.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer subscriber = session.createConsumer(topic);
            subscriber.setMessageListener(this);

            connection.start();
            sendMessage(topic, Events.ADDED.name());

            Scanner keyIn = new Scanner(System.in);

            System.out.print("JMS Server listening. Type a Key + CR to exit\n");
            keyIn.next();
        } finally {
            if (ic != null) {
                try {
                    ic.close();
                } catch (Exception e) {
                    throw e;
                }
            }

            // ALWAYS close your connection in a finally block to avoid leaks.
            // Closing connection also takes care of closing its related objects e.g. sessions.
            closeConnection(connection);
        }
    }

    private void sendMessage(Topic topic, String message) throws Exception {
        MessageProducer publisher = session.createProducer(topic);
        TextMessage msg = session.createTextMessage(message);
        publisher.send(msg);
    }

    private void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (JMSException jmse) {
            System.out.println("Could not close connection " + con + " exception was " + jmse);
        }
    }

    public static Context getInitialContext() throws javax.naming.NamingException {

        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, " org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        return new InitialContext(p);
    }

    @Override
    public void onMessage(Message m) {
        System.out.println(m);
        TextMessage text = (TextMessage) m;
        try {
            if (text.getText().equals(Events.READ.name())) {
                System.out.println("Reader got read event " + read++);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
