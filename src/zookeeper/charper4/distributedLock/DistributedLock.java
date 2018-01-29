package zookeeper.charper4.distributedLock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class DistributedLock implements Lock, Watcher{

	private ZooKeeper zk;
    private String root = "/locks";//��
    /**
     * ������Դ�ı�־
     */
    private String lockName;
    private String waitNode;//�ȴ�ǰһ����
    private String myZnode;//��ǰ��
    private CountDownLatch latch;//������
    private int sessionTimeout = 30000;
    private List<Exception> exception = new ArrayList<Exception>();
    /**
     * �����ֲ�ʽ��,ʹ��ǰ��ȷ��config���õ�zookeeper�������
     * @param config 127.0.0.1:2181
     * @param lockName ������Դ��־,lockName�в��ܰ�������lock
     */
    public DistributedLock(String config, String lockName){
        this.lockName = lockName;
        // ����һ���������������
         try {
            zk = new ZooKeeper(config, sessionTimeout, this);
            Stat stat = zk.exists(root, false);
            if(stat == null){
                // �������ڵ�
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            exception.add(e);
        } catch (KeeperException e) {
            exception.add(e);
        } catch (InterruptedException e) {
            exception.add(e);
        }
    }
    
	@Override
	public void process(WatchedEvent event) {
		if(this.latch != null) { 
            this.latch.countDown(); 
        }
	}

	@Override
	public void lock() {
		if(exception.size() > 0){
            throw new LockException(exception.get(0));
        }
        try {
            if(this.tryLock()){
                System.out.println("Thread " + Thread.currentThread().getId() + " " + myZnode + " get lock true");
                return;
            }
            else{
                waitForLock(waitNode, sessionTimeout);//�ȴ���
            }
        } catch (KeeperException e) {
            throw new LockException(e);
        } catch (InterruptedException e) {
            throw new LockException(e);
        }
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		this.lock();
		
	}

	@Override
	public boolean tryLock() {
		try {
            String splitStr = "_lock_";
            if(lockName.contains(splitStr))
                throw new LockException("lockName can not contains \\u000B");
            //������ʱ�ӽڵ�
            myZnode = zk.create(root + "/" + lockName + splitStr, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(myZnode + " is created ");
            //ȡ�������ӽڵ�
            List<String> subNodes = zk.getChildren(root, false);
            //ȡ������lockName����
            List<String> lockObjNodes = new ArrayList<String>();
            for (String node : subNodes) {
                String _node = node.split(splitStr)[0];
                if(_node.equals(lockName)){
                    lockObjNodes.add(node);
                }
            }
            Collections.sort(lockObjNodes);
            System.out.println(myZnode + "==" + lockObjNodes.get(0));
            if(myZnode.equals(root+"/"+lockObjNodes.get(0))){
                //�������С�Ľڵ�,���ʾȡ����
                return true;
            }
            //���������С�Ľڵ㣬�ҵ����Լ�С1�Ľڵ�
            String subMyZnode = myZnode.substring(myZnode.lastIndexOf("/") + 1);
            waitNode = lockObjNodes.get(Collections.binarySearch(lockObjNodes, subMyZnode) - 1);
        } catch (KeeperException e) {
            throw new LockException(e);
        } catch (InterruptedException e) {
            throw new LockException(e);
        }
        return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		try {
            if(this.tryLock()){
                return true;
            }
            return waitForLock(waitNode,time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}

	private boolean waitForLock(String lower, long waitTime) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(root + "/" + lower,true);
        //�жϱ��Լ�Сһ�����Ľڵ��Ƿ����,���������������ȴ���,ͬʱע�����
        if(stat != null){
            System.out.println("Thread " + Thread.currentThread().getId() + " waiting for " + root + "/" + lower);
            this.latch = new CountDownLatch(1);
            this.latch.await(waitTime, TimeUnit.MILLISECONDS);
            this.latch = null;
        }
        return true;
    }
	
	
	@Override
	public void unlock() {
		try {
            System.out.println("unlock " + myZnode);
            zk.delete(myZnode, -1);
            myZnode = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
	}

	@Override
	public Condition newCondition() {
		return null;
	}
	
	public class LockException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public LockException(String e){
            super(e);
        }
        public LockException(Exception e){
            super(e);
        }
    }
}
