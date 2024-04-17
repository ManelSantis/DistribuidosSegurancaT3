package Classes;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;

    public RSA(int bitLength) {
        generateKeys(bitLength);
    }

    public void generateKeys(int bitLength) {
        Random rand = new Random();
        p = BigInteger.probablePrime(bitLength / 2, rand);
        q = BigInteger.probablePrime(bitLength / 2, rand);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537);
        d = e.modInverse(phi);
    }

    public BigInteger cifrarRSA(BigInteger message) {
        return message.modPow(e, n);
    }

    public BigInteger descriptografarRSA(BigInteger encryptedMessage) {
        return encryptedMessage.modPow(d, n);
    }
}
