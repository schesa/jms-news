package pcbe.jms;

import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;

public class TopicConsumer implements MessageListener {

    public static void main(String[] args) throws Exception {
        new TopicConsumer().init();
    }

    public void init() throws Exception {
        String destinationName = "topic/news";

        Context ic = null;
        ConnectionFactory cf = null;
        Connection connection = null;

        try {
            ic = getInitialContext();

            cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
            Topic topic = (Topic) ic.lookup(destinationName);

            connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer subscriber = session.createConsumer(topic);

            subscriber.setMessageListener(this);
            connection.start();

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

    @Override
    public void onMessage(Message message) {
        TextMessage text = (TextMessage) message;
        String strMessage = null;
        try {
            strMessage = text.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("Message received: " + strMessage);
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
        return new javax.naming.InitialContext(p);
    }
}
