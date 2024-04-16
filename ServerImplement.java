import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
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

    public void editCar(Car car, int id) throws RemoteException, SQLException{
        carImplement.editCar(car, id);
    }

    public void buyCar(int renavan) throws RemoteException {
        carImplement.buyCar(renavan);
    }

    public Car findCar(int renavan) throws RemoteException,SQLException {
        return carImplement.findCar(renavan);
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
