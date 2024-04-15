import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import Classes.Car;
import Enums.CarTypes;

public class CarImplement extends UnicastRemoteObject implements CarService {
    private List<Car> cars;

    public CarImplement() throws RemoteException {
        cars = new ArrayList<>();

        // Carros econômicos
        cars.add(new Car(0, "Fiat Novo Uno", 2010, 14000, CarTypes.ECONOMICO));
        cars.add(new Car(1, "Chevrolet Onix", 2010, 50000, CarTypes.ECONOMICO));
        cars.add(new Car(2, "Ford Ka", 2013, 30000, CarTypes.ECONOMICO));
        cars.add(new Car(3, "Hyundai HB20", 2012, 300000, CarTypes.ECONOMICO));

        // Carros intermediários
        cars.add(new Car(4, "Ford Ka Sedan", 2015, 35000, CarTypes.INTERMEDIARIO));
        cars.add(new Car(5, "Chevrolet Onix Plus", 2018, 60000, CarTypes.INTERMEDIARIO));
        cars.add(new Car(6, "Hyundai HB20S", 2017, 55000, CarTypes.INTERMEDIARIO));
        cars.add(new Car(7, "Renault Logan", 2016, 40000, CarTypes.INTERMEDIARIO));

        // Carros executivos
        cars.add(new Car(8, "Toyota Corolla", 2019, 90000, CarTypes.EXECUTIVO));
        cars.add(new Car(9, "Honda Civic", 2020, 85000, CarTypes.EXECUTIVO));
        cars.add(new Car(10, "Chevrolet Cruze", 2019, 80000, CarTypes.EXECUTIVO));
        cars.add(new Car(11, "Audi A3", 2021, 120000, CarTypes.EXECUTIVO));
        
    }

    @Override
    public void saveCar(Car car) throws RemoteException {
        cars.add(car);
    }

    @Override
    public void removeCar(int renavan) throws RemoteException {
        cars.removeIf(car -> car.getRenavan() == (renavan) || car.getNome().isEmpty());
    }

    @Override
    public void editCar(int renavan, Car car) throws RemoteException {
        for (int i = 0; i < cars.size(); i++) {
            Car aux = cars.get(i);
            if (aux.getRenavan() == renavan) {
                cars.set(i, car);
                return;
            }
        }
    }

    @Override
    public List<Car> listCars() throws RemoteException {
        return cars;
    }

    @Override
    public List<Car> searchCars(String name, int renavan) throws RemoteException {
        List<Car> carsByType = new ArrayList<>();
        for (Car car : cars) {
            if (car.getNome().equals(name)) {
                carsByType.add(car);
            } else if (car.getRenavan() == renavan) {
                carsByType.add(car);
            }
        }
        return carsByType;
    }

    @Override
    public int showCountCars() throws RemoteException {
        return cars.size();
    }

    @Override
    public void buyCar(int renavan) throws RemoteException {
        cars.removeIf(car -> car.getRenavan() == (renavan));
    }
    
}
