import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Classes.Car;

public interface CarService extends Remote {
    void saveCar(Car car) throws RemoteException;
    void removeCar(int renavan) throws RemoteException;
    void editCar(int renavan, Car car) throws RemoteException;
    void buyCar(int renavan) throws RemoteException;    
    List<Car> listCars() throws RemoteException;
    List<Car> searchCars(String name, int renavan) throws RemoteException;
    int showCountCars() throws RemoteException;
}
