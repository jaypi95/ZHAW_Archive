package ch.zhaw.init.its.labs.publickey;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PublicKeyLab {

    private static final String keypairFilename = "src/ch/zhaw/init/its/labs/publickey/ch/zhaw/init/its/labs/publickey/test.txt";

    public static void main(String[] args) throws Exception {
        PublicKeyLab lab = new PublicKeyLab();

        lab.exercise1();


        BigInteger encodedText = encodeText(getTextFromCommandline());
        exercise3Encrypt(encodedText);
        System.out.printf("Decrypted text: %s%n", exercise3Decrypt(keypairFilename + "_encrypted", keypairFilename));

        BigInteger encodeForSignature = encodeText(getTextFromCommandline());
        lab.exercise9GenerateSignature(encodeForSignature);
        lab.exercise9VerifySignature(encodeForSignature, keypairFilename + "_signature.txt", keypairFilename);
        exercise10GenerateSignature(encodeForSignature);
        exercise10Verify(encodeForSignature, keypairFilename + "_signature_modified.txt", keypairFilename);
    }

    private static void exercise3Encrypt(BigInteger encodedText) throws IOException, ClassNotFoundException, BadMessageException {
        banner("Exercise 3 (encryption)");

        generateKeypairIfNotExists();
        RSA publicKey = new RSA(readFromFile(keypairFilename));


        BigInteger encrypted = publicKey.encrypt(encodedText);

        writeToFile(encrypted, keypairFilename + "_encrypted");
    }

    private static String exercise3Decrypt(String cipherFilepath, String keypairFilename) {
        try {
            ObjectInputStream ois = readFromFile(cipherFilepath);
            BigInteger cipher = (BigInteger) ois.readObject();

            ObjectInputStream keyPairOis = readFromFile(keypairFilename);

            BigInteger decryptedAsHex = new RSA(keyPairOis).decrypt(cipher);

            return new String(decryptedAsHex.toByteArray(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void exercise10GenerateSignature(BigInteger encodedMessage) throws Exception {

        banner("Exercise 10 (signature generation 2: electric boogaloo)");

        generateKeypairIfNotExists();
        RSA publicKey = new RSA(readFromFile(keypairFilename));

        BigInteger signature = publicKey.sign(encodedMessage);
        writeToFile(signature, keypairFilename + "_signature_modified.txt");

        // --------> Your solution here! <--------
    }

    private static void exercise10Verify(BigInteger encodedString, String signatureFilepath, String keypairFilepath) {
        boolean ok = false;

        banner("Exercise 10 (what if file is modified?)");

        try (ObjectInputStream key = new ObjectInputStream(new FileInputStream(keypairFilepath))) {
            RSA keypair = new RSA(key);

            // --------> Your solution here! <--------
            ObjectInputStream signatureOis = readFromFile(signatureFilepath);
            BigInteger signature = (BigInteger) signatureOis.readObject();

            signature = signature.subtract(BigInteger.ONE);

            ok = keypair.verify(encodedString, signature);

        } catch (FileNotFoundException e) {
            System.err.println("Can't find keypair file \"" + keypairFilename + "\"");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (BadMessageException e) {
            throw new RuntimeException(e);
        }

        if (ok) {
            System.out.println("Signature verified successfully");
        } else {
            System.out.println("Signature did not verify successfully");
        }

    }

    private static void generateKeypairIfNotExists() throws IOException {
        // Generate keypair if none exists
        File f = new File(keypairFilename);
        if (!f.canRead()) {
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
                RSA rsa = new RSA();
                rsa.save(os);

            }
        }
    }

    private static void writeToFile(BigInteger file, String filepath) {
        try (FileOutputStream fos = new FileOutputStream(filepath); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ObjectInputStream readFromFile(String filepath) {
        ObjectInputStream result = null;

        System.out.println("File path: " + filepath);
        String path = System.getProperty("user.dir") + "/" + filepath;

        if (Files.exists(Paths.get(path))) {
            try {
                result = new ObjectInputStream(new FileInputStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("File does not exist");
        }
        if (result == null) {
            throw new IllegalArgumentException("File does not exist");
        }

        return result;
    }

    private static String getTextFromCommandline() throws IOException {
        System.out.println("Please enter a short string and press enter: ");
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

        return buff.readLine();
    }

    private static BigInteger encodeText(String plaintext) {
        return new BigInteger(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    private static void banner(String string) {
        System.out.println();
        System.out.println(string);
        for (int i = 0; i < string.length(); i++) {
            System.out.print('=');
        }
        System.out.println();
        System.out.println();
    }

    private void exercise1() {
        final int[] workFactorsBits = {128, 256, 384, 512};

        banner("Exercise 1");
        for (int wfBits : workFactorsBits) {
            int keyLength = findRSAKeyLengthForWorkFactorInBits(wfBits);
            System.out.format("%4d bits work factor: %6d bits RSA exponent\n", wfBits, keyLength);
        }

    }

    private void exercise9GenerateSignature(BigInteger encodedMessage) throws BadMessageException, IOException, ClassNotFoundException {

        banner("Exercise 9 (signature generation)");

        generateKeypairIfNotExists();
        RSA publicKey = new RSA(readFromFile(keypairFilename));

        BigInteger signature = publicKey.sign(encodedMessage);
        writeToFile(signature, keypairFilename + "_signature.txt");

        // --------> Your solution here! <--------
    }

    private void exercise9VerifySignature(BigInteger encodedString, String signatureFilepath, String keypairFilepath) throws BadMessageException {
        boolean ok = false;

        banner("Exercise 9 (signature verification)");

        try (ObjectInputStream key = new ObjectInputStream(new FileInputStream(keypairFilepath))) {
            RSA keypair = new RSA(key);

            // --------> Your solution here! <--------
            ObjectInputStream signatureOis = readFromFile(signatureFilepath);
            BigInteger signature = (BigInteger) signatureOis.readObject();

            ok = keypair.verify(encodedString, signature);

        } catch (FileNotFoundException e) {
            System.err.println("Can't find keypair file \"" + keypairFilename + "\"");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (ok) {
            System.out.println("Signature verified successfully");
        } else {
            System.out.println("Signature did not verify successfully");
        }


    }

    private int findRSAKeyLengthForWorkFactorInBits(int wfBits) {

        int b = 1;
        double W = 0;
        // --------> Your solution here! <--------
        while (W <= Math.pow(2, wfBits)) {
            b++;
            W = Math.exp(1.92 * (Math.pow(b, 1 / 3.0) * Math.pow(Math.log(b), 2 / 3.0)));
        }
        return b;
    }
}
