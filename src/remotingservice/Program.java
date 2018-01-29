package remotingservice;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import service.PersonService;
import serviceImpl.PersonServiceImpl;

public class Program {
	public static void main(String[] args) {
		try {
			PersonService personService = new PersonServiceImpl();
			//注册通讯端口
			LocateRegistry.createRegistry(6600);
			//注册通讯路径
			Naming.rebind("rmi://127.0.0.1:6600/PersonService", personService);
			System.out.println("Service Start!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
