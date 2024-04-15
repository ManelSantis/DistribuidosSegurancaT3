import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Classes.Car;
import Classes.User;

public class ServerImplement extends UnicastRemoteObject implements AuthenticateService, CarService {
    private AuthenticateImplement authenticateImplement;
    private CarImplement carImplement;

    public ServerImplement(AuthenticateImplement authenticateImplement, CarImplement carImplement) throws RemoteException {
        this.authenticateImplement = authenticateImplement;
        this.carImplement = carImplement;
    }
    
    public User authenticate(String username, String password) throws RemoteException {
        return authenticateImplement.authenticate(username, password);
    }

    public void saveCar(Car car) throws RemoteException {
        carImplement.saveCar(car);
    }

    public void removeCar(int renavan) throws RemoteException {
        carImplement.removeCar(renavan);
    }

    public void editCar(int renavan, Car car) throws RemoteException {
        carImplement.editCar(renavan, car);
    }

    public void buyCar(int renavan) throws RemoteException {
        carImplement.buyCar(renavan);
    }

    public List<Car> listCars() throws RemoteException {
        return carImplement.listCars();
    }

    public List<Car> searchCars(String name, int renavan) throws RemoteException {
        return carImplement.searchCars(name, renavan);
    }

    public int showCountCars() throws RemoteException {
        return carImplement.showCountCars();
    }

}
