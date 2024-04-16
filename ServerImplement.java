import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

import Classes.Car;
import Classes.User;

public class ServerImplement extends UnicastRemoteObject {
    private Firewall firewall;

    public ServerImplement(Firewall firewall) throws RemoteException {
        this.firewall = firewall;
    }
    
    public User authenticate(String username, String password, String userIp) throws RemoteException {
        return firewall.authenticate(username, password, userIp);
    }

    public void saveCar(Car car) throws RemoteException {
        firewall.saveCar(car);
    }

    public void removeCar(int renavan) throws RemoteException {
        firewall.removeCar(renavan);
    }

    public void editCar(Car car, int id) throws RemoteException, SQLException{
        firewall.editCar(car, id);
    }

    public void buyCar(int renavan) throws RemoteException {
        firewall.buyCar(renavan);
    }

    public Car findCar(int renavan) throws RemoteException,SQLException {
        return firewall.findCar(renavan);
    }
    
    public List<Car> listCars() throws RemoteException {
        return firewall.listCars();
    }

    public List<Car> searchCars(String name, int renavan) throws RemoteException {
        return firewall.searchCars(name, renavan);
    }

    public int showCountCars() throws RemoteException {
        return firewall.showCountCars();
    }

}
