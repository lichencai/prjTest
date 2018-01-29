package activiteMQ;

public class MessageSender {
	
	/*
	
	
	// ���ʹ���
    public static final int SEND_NUM = 5;
    // tcp ��ַ
    public static final String BROKER_URL = "tcp://localhost:61616";
    // Ŀ�꣬��ActiveMQ����Ա����̨���� http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "hoo.mq.queue";
    
    *//**
     * <b>function:</b> ������Ϣ
     *//*    
    public static void sendMessage(Session session, MessageProducer producer) throws Exception {
        for (int i = 0; i < SEND_NUM; i++) {
            String message = "������Ϣ��" + (i + 1) + "��";
            TextMessage text = session.createTextMessage(message);
            
            System.out.println(message);
            producer.send(text);
        }
    }
    
    public static void run() throws Exception {
        
        Connection connection = null;
        Session session = null;
        try {
            // �������ӹ���
            ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            // ͨ����������һ������
            connection = factory.createConnection();
            // ��������
            connection.start();
            // ����һ��session�Ự
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // ����һ����Ϣ����
            Destination destination = session.createQueue(DESTINATION);
            // ������Ϣ������
            MessageProducer producer = session.createProducer(destination);
            // ���ó־û�ģʽ
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, producer);
            // �ύ�Ự
            session.commit();
            
        } catch (Exception e) {
            throw e;
        } finally {
            // �ر��ͷ���Դ
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        MessageSender.run();
    }
    
    
    */
}
