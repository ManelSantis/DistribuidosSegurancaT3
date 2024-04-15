package Classes;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import Enums.UserTypes;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String passwordHash;
    private byte[] passwordSalt;
    private UserTypes userType;

    public User(String username, String password, UserTypes userType) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.username = username;
        this.userType = userType;
        // Gere o salt e o hash da senha
        this.passwordSalt = PasswordHash.generateSalt();
        this.passwordHash = PasswordHash.hashPassword(password, this.passwordSalt);
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public byte[] getPasswordSalt() {
        return passwordSalt;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userType=" + userType +
                '}';
    }
}
