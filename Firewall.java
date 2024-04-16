import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import Classes.Car;
import Classes.User;

public class Firewall {
    private AuthenticateImplement authenticateImplement;
    private CarImplement carImplement;

    public Firewall(AuthenticateImplement authenticateImplement, CarImplement carImplement) {
        this.authenticateImplement = authenticateImplement;
        this.carImplement = carImplement;
    }

    public User authenticate(String username, String password, String clientIp) throws RemoteException {
        if (!isIpAllowed(clientIp)) {
            System.out.println("Endereço IP não permitido: " + clientIp);
            return null;
        }

        User authenticatedUser = authenticateImplement.authenticate(username, password);

        if (authenticatedUser != null) {
            System.out.println("Autenticação bem-sucedida para o usuário: " + authenticatedUser.getUsername());
            proceedToStore(authenticatedUser);
        } else {
            System.out.println("Autenticação falhou para o usuário: " + username);
        }

        return authenticatedUser;
    }

    private boolean isIpAllowed(String clientIp) {
        int clientIpInteger = ipToInteger(clientIp);
    
        int startRange = ipToInteger("100.1.1.0");
        int endRange = ipToInteger("100.1.255.255");
    
        return clientIpInteger >= startRange && clientIpInteger <= endRange;
    }

    private int ipToInteger(String ip) {
        String[] parts = ip.split("\\.");
        int ipInteger = 0;
        for (int i = 0; i < 4; i++) {
            ipInteger = (ipInteger << 8) + Integer.parseInt(parts[i]);
        }
        return ipInteger;
    }

    private void proceedToStore(User authenticatedUser) {
        System.out.println("O usuário autenticado pode acessar o sistema da loja.");
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
