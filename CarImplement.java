import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import Classes.Car;


public class CarImplement extends UnicastRemoteObject implements CarService {
    private List<Car> cars;

    public CarImplement() throws RemoteException {
        cars = new ArrayList<>();

        // Carros econômicos
        saveCar((new Car(0, "Fiat Novo Uno", 2010, 14000, "Economico")));
        saveCar((new Car(1, "Chevrolet Onix", 2010, 50000, "Economico")));
        saveCar((new Car(2, "Ford Ka", 2013, 30000, "Economico")));
        saveCar((new Car(3, "Hyundai HB20", 2012, 300000, "Economico")));

        // Carros intermediários
        saveCar((new Car(4, "Ford Ka Sedan", 2015, 35000, "Intermediario")));
        saveCar((new Car(5, "Chevrolet Onix Plus", 2018, 60000, "Intermediario")));
        saveCar((new Car(6, "Hyundai HB20S", 2017, 55000, "Intermediario")));
        saveCar((new Car(7, "Renault Logan", 2016, 40000, "Intermediario")));

        // Carros executivos
        saveCar((new Car(8, "Toyota Corolla", 2019, 90000, "Executivo")));
        saveCar((new Car(9, "Honda Civic", 2020, 85000, "Executivo")));
        saveCar((new Car(10, "Chevrolet Cruze", 2019, 80000, "Executivo")));
        saveCar((new Car(11, "Audi A3", 2021, 120000, "Executivo")));

    }

    @Override
    public void saveCar(Car car) throws RemoteException {
        try {
            Connection connection = Conexao.getConnection();
            String sql = "insert into veiculos (renavan, nome, anofabricacao, preco, tipo) values (?, ?, ?,?,?);";
            PreparedStatement ptst;
            ptst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ptst.setInt(1, car.getRenavan());
            ptst.setString(2, car.getNome());
            ptst.setInt(3, car.getAnoFabricacao());
            ptst.setDouble(4, car.getPreco());
            ptst.setString(5, car.getType());
            ptst.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void removeCar(int renavan) throws RemoteException {
        String sql = "delete from veiculos where renavan = ?";
        PreparedStatement ptst;
        try {
            Connection connection = Conexao.getConnection();
            ptst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ptst.setLong(1, renavan);
            ptst.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void editCar(Car car,int id) throws RemoteException, SQLException {
        String sql = "update veiculos set renavan = ?, nome = ?, anofabricacao = ?, preco = ?, tipo= ? WHERE renavan = ?" ;
        PreparedStatement ptst;
        Connection connection = Conexao.getConnection();
        ptst = connection.prepareStatement(sql);
        ptst.setLong(1, car.getRenavan());
        ptst.setString(2, car.getNome());
        ptst.setInt(3, car.getAnoFabricacao());
        ptst.setDouble(4, car.getPreco());
        ptst.setString(5, car.getType());
        ptst.setLong(6, id);
        ptst.executeUpdate();
    }

    @Override
    public List<Car> listCars() throws RemoteException {
        List<Car> carros = new ArrayList<>();
        PreparedStatement ptst;
        String sql = "SELECT * FROM veiculos";
        try {
            Connection connection = Conexao.getConnection();
            ptst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ptst.executeQuery();
            while (rs.next()){
                Car car = new Car(
                    rs.getInt("renavan"),
                    rs.getString("nome"),
                    rs.getInt("anofabricacao"),
                    rs.getDouble("preco"),
                    rs.getString("tipo"));
                    carros.add(car);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return carros;
    }

    @override
    public Car findCar(int renavan) throws RemoteException, SQLException {
        String sql = "select * from veiculos where renavan = ?";
        PreparedStatement ptst;
        ResultSet rs;
        Connection connection = Conexao.getConnection();
        ptst = connection.prepareStatement(sql);
        ptst.setLong(1, renavan);
        rs = ptst.executeQuery();
        Car car = new Car();
        try {
            if (rs.next()) {
                car = new Car(rs.getInt("renavan"), rs.getString("nome"), rs.getInt("anofabricacao"),
                        rs.getDouble("preco"), rs.getString("tipo"));
                System.out.println(car.getPreco());
                return car;

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return car;
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
