import java.rmi.Remote;
import java.rmi.RemoteException;

import Classes.User;

public interface AuthenticateService extends Remote {
    User authenticate(String username, String password) throws RemoteException;
}