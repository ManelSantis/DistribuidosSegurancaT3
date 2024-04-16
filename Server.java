import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Server {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            Conexao con = new Conexao();
            AuthenticateImplement authenticateImplement = new AuthenticateImplement();
            CarImplement carImplement = new CarImplement();
            Firewall firewall = new Firewall(authenticateImplement, carImplement);
            ServerImplement server = new ServerImplement(firewall);

            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Firewall", server);

            System.err.println("Servico de autenticacao pronto.");
            System.err.println("Servico da loja de carros pronto.");
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
