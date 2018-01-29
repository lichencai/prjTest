package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.PersonEntity;

public interface PersonService extends Remote {
	public List<PersonEntity> GetList() throws RemoteException;
}
