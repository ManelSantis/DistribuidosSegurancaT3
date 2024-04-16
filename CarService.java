import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import Classes.Car;

public interface CarService extends Remote {
    void saveCar(Car car) throws RemoteException;
    void removeCar(int renavan) throws RemoteException;
    void editCar(Car car, int id) throws RemoteException,SQLException;
    void buyCar(int renavan) throws RemoteException;    
    Car  findCar(int renavan) throws RemoteException, SQLException;    
    List<Car> listCars() throws RemoteException;
    List<Car> searchCars(String name, int renavan) throws RemoteException;
    int showCountCars() throws RemoteException;
}
