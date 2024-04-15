import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import Classes.Car;
import Classes.User;
import Enums.CarTypes;
import Enums.UserTypes;

public class Client {

  static Scanner sc = new Scanner(System.in);

  public static void main(String[] args)
    throws RemoteException, NotBoundException {
    Registry registry = LocateRegistry.getRegistry("0");
    AuthenticateService authService = (AuthenticateService) registry.lookup(
      "AuthenticateService"
    );
    User userLogin = null;
    boolean login = false;

    while (!login) {
      System.out.print("Usuario: ");
      String user = sc.nextLine();
      System.out.print("Senha: ");
      String password = sc.nextLine();
      userLogin = authService.authenticate(user, password);
      if (userLogin != null) {
        System.out.println(
          "Bem vindo(a) " + userLogin.getUsername() + " em que podemos ajudar?"
        );
        login = true;
      } else {
        System.err.println("Usuario ou senha incorretos. Tente novamente.");
      }
    }

    CarService carService = (CarService) registry.lookup("CarService");
    int value = 50;
    while (value != 0) {
      if (userLogin.getUserType().equals(UserTypes.CLIENT)) {
        MenuCliente();
        value = sc.nextInt();
        sc.nextLine();
        SwitchClient(value, carService);
      } else if (userLogin.getUserType().equals(UserTypes.EMPLOYEE)) {
        MenuFuncionario();
        value = sc.nextInt();
        sc.nextLine();
        SwitchEmployee(value, carService);
      }
    }
  }

  public static void MenuCliente() {
    System.out.println("--------- Loja de Carros ---------");
    System.out.println("1- Listar Carros");
    System.out.println("2- Consultar Carros");
    System.out.println("3- Exibir Quantidade de Carros");
    System.out.println("4- Comprar Carro");
    System.out.println("0- Sair");
    System.out.println("----------------------------------");
  }

  public static void MenuFuncionario() {
    System.out.println("--------- Loja de Carros ---------");
    System.out.println("1- Listar Carros");
    System.out.println("2- Consultar Carros");
    System.out.println("3- Exibir Quantidade de Carros");
    System.out.println("4- Comprar Carro");
    System.out.println("5- Adicionar Carro");
    System.out.println("6- Apagar Carro");
    System.out.println("7- Alterar Atributos de Carros");
    System.out.println("0- Sair");
    System.out.println("----------------------------------");
  }

  public static void SwitchClient(int value, CarService carService)
    throws RemoteException {
    List<Car> cars;
    switch (value) {
      case 1:
        cars = carService.listCars();
        System.out.println("--------- Lista de Carros --------");
        for (Car car : cars) {
          System.out.println("----------------------------------");
          System.out.println(car);
          System.out.println("----------------------------------");
        }
        break;
      case 2:
        String name = "";
        int renavan = 0;
        System.out.println("Como deseja pesquisar o carro?");
        System.out.println("1- Nome");
        System.err.println("2- Renavan");
        int aux = sc.nextInt();
        sc.nextLine();
        if (aux == 1) {
          System.err.print("Digite o nome: ");
          name = sc.nextLine();
        } else {
          System.out.print("Digite o Renavan: ");
          renavan = sc.nextInt();
          sc.nextLine();
        }
        cars = carService.searchCars(name, renavan);
        System.out.println("----------- Resultado ------------");
        for (Car car : cars) {
          System.out.println("----------------------------------");
          System.out.println(car);
          System.out.println("----------------------------------");
        }
        break;
      case 3:
        System.out.println(
          "Existem " + carService.showCountCars() + " carros no sistema."
        );
        break;
      case 4:
        System.out.println("Digite o renavan do carro que deseja comprar: ");
        int renavanAux = sc.nextInt();
        sc.nextLine();
        if (carService.searchCars("", renavanAux).size() < 1) {
          while (
            carService.searchCars("", renavanAux).size() < 1 &&
            renavanAux != 404
          ) {
            System.out.print(
              "Renavan nao encontrado no sistema. Por favor, digite novamente ou cancele a compra (404): "
            );
            renavanAux = sc.nextInt();
            sc.nextLine();
          }
        }

        if (renavanAux == 404) {
          break;
        }
        Car carCompra = carService.searchCars("", renavanAux).get(0);
        System.out.println("Carro Encontrado");
        System.out.println("----------------------------------");
        System.out.println(carCompra);
        System.out.println("----------------------------------");
        System.out.println("Deseja concluir a compra? \n 1- Sim | 2. Não");
        int comprar = sc.nextInt();
        if (comprar == 1) {
          carService.buyCar(renavanAux);
          System.out.println("Carro Comprado");
          System.out.println("----------------------------------");
          System.out.println(carCompra);
          System.out.println("----------------------------------");
        } else {
          System.out.println("Compra cancelada.");
        }
        break;
      default:
        break;
    }
  }

  public static void SwitchEmployee(int value, CarService carService)
    throws RemoteException {
    List<Car> cars;
    switch (value) {
      case 1:
        cars = carService.listCars();
        System.out.println("--------- Lista de Carros --------");
        for (Car car : cars) {
          System.out.println("----------------------------------");
          System.out.println(car);
          System.out.println("----------------------------------");
        }
        break;
      case 2:
        String name = "";
        int renavan = 0;
        System.out.println("Como deseja pesquisar o carro?");
        System.out.println("1- Nome");
        System.err.println("2- Renavan");
        int aux = sc.nextInt();
        sc.nextLine();
        if (aux == 1) {
          System.err.print("Digite o nome: ");
          name = sc.nextLine();
        } else {
          System.out.print("Digite o Renavan: ");
          renavan = sc.nextInt();
          sc.nextLine();
        }
        cars = carService.searchCars(name, renavan);
        System.out.println("----------- Resultado ------------");
        for (Car car : cars) {
          System.out.println("----------------------------------");
          System.out.println(car);
          System.out.println("----------------------------------");
        }
        break;
      case 3:
        System.out.println(
          "Existem " + carService.showCountCars() + " carros no sistema."
        );
        break;
      case 4:
        System.out.println("Digite o renavan do carro que deseja comprar: ");
        int renavanAux = sc.nextInt();
        sc.nextLine();
        if (carService.searchCars("", renavanAux).size() < 1) {
          while (
            carService.searchCars("", renavanAux).size() < 1 &&
            renavanAux != 404
          ) {
            System.out.print(
              "Renavan nao encontrado no sistema. Por favor, digite novamente ou cancele a compra (404): "
            );
            renavanAux = sc.nextInt();
            sc.nextLine();
          }
        }

        if (renavanAux == 404) {
          break;
        }
        Car carCompra = carService.searchCars("", renavanAux).get(0);
        System.out.println("Carro Encontrado");
        System.out.println("----------------------------------");
        System.out.println(carCompra);
        System.out.println("----------------------------------");
        System.out.println("Deseja concluir a compra? \n 1- Sim | 2. Nao");
        int comprar = sc.nextInt();
        if (comprar == 1) {
          carService.buyCar(renavanAux);
          System.out.println("Carro Comprado");
          System.out.println("----------------------------------");
          System.out.println(carCompra);
          System.out.println("----------------------------------");
        } else {
          System.out.println("Compra cancelada.");
        }
        break;
      case 5:
        System.out.print("Digite o Renavan do carro: ");
        int renavanAdd = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o nome do carro: ");
        String nameAdd = sc.nextLine();
        System.out.print("Digite o ano de fabricacao do carro: ");
        int anoAdd = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o preco do carro: ");
        double precoAdd = sc.nextDouble();
        sc.nextLine();
        System.out.println(
          "Escolha o tipo do carro: \n 1- Economico | 2- Intermediario | 3- Executivo"
        );
        int tipoAdd = sc.nextInt();
        sc.nextLine();

        Car addCar = null;
        if (tipoAdd == 1) {
          addCar =
            new Car(renavanAdd, nameAdd, anoAdd, precoAdd, CarTypes.ECONOMICO);
        } else if (tipoAdd == 2) {
          addCar =
            new Car(
              renavanAdd,
              nameAdd,
              anoAdd,
              precoAdd,
              CarTypes.INTERMEDIARIO
            );
        } else {
          addCar =
            new Car(renavanAdd, nameAdd, anoAdd, precoAdd, CarTypes.EXECUTIVO);
        }

        carService.saveCar(addCar);

        System.out.println("Carro adicionado");
        System.out.println(addCar);
        break;
      case 6:
        System.out.println("Digite o renavan do carro que deseja deletar: ");
        int renavanDeletar = sc.nextInt();
        sc.nextLine();
        if (carService.searchCars("", renavanDeletar).size() < 1) {
          while (
            carService.searchCars("", renavanDeletar).size() < 1 &&
            renavanDeletar != 404
          ) {
            System.out.print(
              "Renavan nao encontrado no sistema. Por favor, digite novamente ou cancele a remocao (404): "
            );
            renavanDeletar = sc.nextInt();
            sc.nextLine();
          }
        }

        if (renavanDeletar == 404) {
          break;
        }
        Car carDeletar = carService.searchCars("", renavanDeletar).get(0);
        System.out.println("Carro Encontrado");
        System.out.println("----------------------------------");
        System.out.println(carDeletar);
        System.out.println("----------------------------------");
        System.out.println("Deseja concluir a remocao? \n 1- Sim | 2. Nao");
        int deletar = sc.nextInt();
        if (deletar == 1) {
          carService.removeCar(renavanDeletar);
          System.out.println("Carro Removido");
          System.out.println("----------------------------------");
          System.out.println(carDeletar);
          System.out.println("----------------------------------");
        } else {
          System.out.println("Remocao cancelada.");
        }
        break;
      case 7:
        System.out.print("Digite o renavan do carro que deseja editar: ");
        int renavanEditar = sc.nextInt();
        sc.nextLine();
        Car carEdit = carService.searchCars("", renavanEditar).get(0);
        Car oldCarEdit = carService.searchCars("", renavanEditar).get(0);
        System.out.println("Renavan atual: " + carEdit.getRenavan());
        System.out.print("Novo: ");
        carEdit.setRenavan(sc.nextInt());
        sc.nextLine();
        System.out.println("Nome atual: " + carEdit.getNome());
        System.out.print("Novo: ");
        carEdit.setNome(sc.nextLine());
        System.out.println("Ano atual: " + carEdit.getAnoFabricacao());
        System.out.print("Novo: ");
        carEdit.setAnoFabricacao(sc.nextInt());
        sc.nextLine();
        System.out.println("Preco atual: " + carEdit.getPreco());
        System.out.print("Novo: ");
        carEdit.setPreco(sc.nextDouble());
        sc.nextLine();
        System.out.println("Tipo atual: " + carEdit.getType());
        System.out.println("1- Economico | 2- Intermediario | 3- Executivo");
        System.out.print("Novo: ");
        int tipoEditar = sc.nextInt();
        sc.nextLine();

        if (tipoEditar == 1) {
            carEdit.setType(CarTypes.ECONOMICO);
        } else if (tipoEditar == 2) {
            carEdit.setType(CarTypes.INTERMEDIARIO);
        } else {
            carEdit.setType(CarTypes.EXECUTIVO);
        }

        System.out.println("Deseja realmente alterar esses valores?");
        System.out.println("Renagan: " + oldCarEdit.getRenavan() + " -> " + carEdit.getRenavan());
        System.out.println("Nome: " + oldCarEdit.getNome() + " -> " + carEdit.getNome());        
        System.out.println("Ano: " + oldCarEdit.getAnoFabricacao() + " -> " + carEdit.getAnoFabricacao());        
        System.out.println("Preco: " + oldCarEdit.getPreco() + " -> " + carEdit.getPreco());        
        System.out.println("Tipo: " + oldCarEdit.getType() + " -> " + carEdit.getType());        
        System.out.println("1- Sim | 2- Nao");
        int concluirEdicao = sc.nextInt();
        sc.nextLine();

        if (concluirEdicao == 1){
            carService.editCar(renavanEditar, carEdit);
            System.out.println("Carro Editado.");
        } else {
            System.out.println("Edição cancelada.");
        }
        
        break;
      default:
        break;
    }
  }
}
