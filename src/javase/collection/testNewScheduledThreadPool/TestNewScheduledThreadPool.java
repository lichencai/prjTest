package javase.collection.testNewScheduledThreadPool;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * �ӳٶ���ʱ�俪ʼ����ִ������
 * �������������̣߳����ܲ�ͬ��ʱ�䵥λ
 */
public class TestNewScheduledThreadPool {
	public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        //  ������ִ��ʱ��С�ڼ��ʱ�������£���������ʼʱ��Ϊ׼��ÿ��ָ��ʱ��ִ��һ�Σ���������ִ��ʱ��Ӱ�졣
        scheduleAtFixedRate(service,1000);
        //  ��ִ������ʱ����ڼ��ʱ�䣬�˷����������¿���һ���µ��������ִ�У����ǵȴ�ԭ������ִ����ɣ����Ͽ�����һ���������ִ�С�
        //  ��ʱ��ִ�м��ʱ���Ѿ������ҡ�
        scheduleAtFixedRate(service,6000);
        //  ��ִ������С���ӳ�ʱ��ʱ����һ������ִ��֮���ӳ�ָ��ʱ�䣬Ȼ��ʼִ�еڶ�������
        scheduleWithFixedDelay(service,1000);
        //  ��ִ����������ӳ�ʱ��ʱ����һ������ִ��֮���ӳ�ָ��ʱ�䣬Ȼ��ʼִ�еڶ�������
        scheduleWithFixedDelay(service,6000);
    }
	/**
	 * ������ִ������
	 */
    private static void scheduleAtFixedRate(ScheduledExecutorService service, final int sleepTime){
        
    	service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long start = new Date().getTime();
                System.out.println("scheduleAtFixedRate ��ʼִ��ʱ��:" +
                        DateFormat.getTimeInstance().format(new Date()));
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long end = new Date().getTime();
                System.out.println("scheduleAtFixedRate ִ�л���ʱ��=" + (end -start)/1000 + "m");
                System.out.println("scheduleAtFixedRate ִ�����ʱ�䣺"
                        + DateFormat.getTimeInstance().format(new Date()));
                System.out.println("======================================");
            }
        },1000, 5000, TimeUnit.MILLISECONDS);
    	
    }
    /**
     * ������ִ����ɺ��ӳ�һ��ʱ���ٴ�ִ������
     */
    private static void scheduleWithFixedDelay(ScheduledExecutorService service,final int sleepTime){
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                long start = new Date().getTime();
                System.out.println("scheduleWithFixedDelay ��ʼִ��ʱ��:" +
                        DateFormat.getTimeInstance().format(new Date()));
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long end = new Date().getTime();
                System.out.println("scheduleWithFixedDelayִ�л���ʱ��=" + (end -start)/1000 + "m");
                System.out.println("scheduleWithFixedDelayִ�����ʱ�䣺"
                        + DateFormat.getTimeInstance().format(new Date()));
                System.out.println("======================================");
            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);
    }
}
