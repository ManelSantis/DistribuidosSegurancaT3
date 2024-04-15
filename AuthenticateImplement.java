import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import Classes.PasswordHash;
import Classes.User;
import Enums.UserTypes;

public class AuthenticateImplement extends UnicastRemoteObject implements AuthenticateService {
    private Map<String, User> users;
    private Map<String, Integer> loginAttempts;
    private Map<String, Long> blockedUsers;

    private static final int MAX_ATTEMPTS = 3;
    private static final long BLOCK_DURATION = 300_000;

    public AuthenticateImplement() throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException {
        users = new HashMap<>();
        loginAttempts = new HashMap<>();
        blockedUsers = new HashMap<>();

        users.put("cliente1", new User("cliente1", "senha1", UserTypes.CLIENT));
        users.put("funcionario1", new User("funcionario1", "senha2", UserTypes.EMPLOYEE));
        users.put("cliente2", new User("cliente2", "senha3", UserTypes.CLIENT));
    }

    @Override
    public User authenticate(String username, String password) throws RemoteException {

        if (blockedUsers.containsKey(username)) {
            long lockedTime = blockedUsers.get(username);
            long currentTime = System.currentTimeMillis();

            if (currentTime - lockedTime >= BLOCK_DURATION) {
                blockedUsers.remove(username);
                loginAttempts.remove(username);
            } else {
                System.out.println("Conta bloqueada. Tente novamente mais tarde.");
                return null;
            }
        }

        User user = users.get(username);

        if (user != null) {
            try {

                String inputHash = PasswordHash.hashPassword(password, user.getPasswordSalt());
                
                if (user.getPasswordHash().equals(inputHash)) {
                    loginAttempts.remove(username);
                    return user;
                } else {
                    int attempts = loginAttempts.getOrDefault(username, 0) + 1;
                    loginAttempts.put(username, attempts);
                    if (attempts >= MAX_ATTEMPTS) {
                        blockedUsers.put(username, System.currentTimeMillis());
                        System.out.println("Conta bloqueada devido a tentativas de login falhas.");
                        return null;
                    } else {
                        System.out.println("Senha incorreta. Tentativas restantes: " + (MAX_ATTEMPTS - attempts));
                        return null;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
