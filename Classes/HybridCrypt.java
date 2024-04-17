package Classes;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HybridCrypt {
    private RSA chaveRsa;

    public HybridCrypt () throws NoSuchAlgorithmException {
        this.chaveRsa = new RSA(1024);
    }

    public void mensagem(String mensagem) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey chaveAES = keyGen.generateKey();

        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, chaveAES);

        byte[] mensagemCifradaAES = aesCipher.doFinal(mensagem.getBytes());

        BigInteger aesChaveBigInt = new BigInteger(chaveAES.getEncoded());
        BigInteger cifrarChaveAes = chaveRsa.cifrarRSA(aesChaveBigInt);

        String mensagemCifradaString = Base64.getEncoder().encodeToString(mensagemCifradaAES);
        String chaveAesString = cifrarChaveAes.toString();

        System.out.println("Mensagem criptografada em RSA: " + mensagemCifradaString);
        System.out.println("Chave AES criptografada em RSA: " + chaveAesString);

        BigInteger decriptografarChaveAes = chaveRsa.descriptografarRSA(cifrarChaveAes);
        byte[] decriptAESChave = decriptografarChaveAes.toByteArray();

        SecretKey originalAESChave = new SecretKeySpec(decriptAESChave, 0, decriptAESChave.length, "AES");

        aesCipher.init(Cipher.DECRYPT_MODE, originalAESChave);
        byte[] decryptedData = aesCipher.doFinal(Base64.getDecoder().decode(mensagemCifradaString));
        String mensagemDecifrada = new String(decryptedData);
        System.out.println("Mensagem descriptografada: " + mensagemDecifrada);
    }
}
