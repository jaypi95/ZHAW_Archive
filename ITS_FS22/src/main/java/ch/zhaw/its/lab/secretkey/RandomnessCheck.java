package ch.zhaw.its.lab.secretkey;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class RandomnessCheck {

    public static final String KALGORITHM = "AES";
    public static final String CALGORITHM = KALGORITHM + "/CBC/PKCS5Padding";

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeyException {
        if(args.length != 1) {
            System.err.println("Please give the following arguments \n" +
                    "1: Path to your encrypted file");
        } else {
            byte[] characterArray = readFile(args[0]);
            //Generate keys and try to decrypt

            // Try to decrypt the file
            List<String> decryptedFile = findKey(characterArray);

            //System.out.println("File: " + decryptedFile.get(0));
            System.out.println("Key: \n" + decryptedFile.get(1));
        }

    }

    private static long calcTimestamp(byte[] timeAsByteArr){
        long timestamp = 0;
      for(int i = 0; i < timeAsByteArr.length; i++){
          if(timeAsByteArr[i] == 0x00){
              break;
          }
          timestamp += ((long) timeAsByteArr[i] & 0xffL) << (8 * i);
      }
      return timestamp;
    }

    private static byte[] timestampToArray(long timestamp){
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance(CALGORITHM);
        } catch (Exception e){
            System.err.println("Oh no :(");
        }

        byte[] key = new byte[cipher.getBlockSize()];
        for(int i = 0; i < key.length; i++){
            key[i] = (byte) (timestamp & 0xff);
            timestamp >>= 8;
        }
        return key;
    }

    private static List<String> findKey(byte[] encryptedFile){
        List<String> returnValue = new ArrayList<>();

        //Extract the first 6Bytes (Bytes 7 to 15 are 0 anyway)
        byte[] extractedIV = new byte[16];
        System.arraycopy(encryptedFile, 0, extractedIV, 0, 16);

        //Convert the IV to the timestamp in milliseconds
        long timestamp = calcTimestamp(extractedIV);

        //Generate a 6 Byte long key out of the IV timestamp.
        //IV gets generated after the key, so we count down from there
        for(long i = timestamp; i > 0; i--) {
            //convert timestamp back to array
            byte[] timestampInBytes = timestampToArray(i);

            //decrypt
            byte[] decryptedFile = new byte[0];
            try {
                decryptedFile = decrypt(timestampInBytes, encryptedFile);

            //check if output file is plaintext
            // Get frequency of characters to calculate randomness
            Map<Byte, Integer> frequencyMap = countCharacters(decryptedFile);
            // Calculate randomness
            double HF = calcHF(frequencyMap, decryptedFile);
            //If randomness is low enough, mark as plaintext
            boolean plaintext = HF <= 5;
            if (plaintext) {
                returnValue.add(Arrays.toString(decryptedFile));
                returnValue.add(String.valueOf(i));

                //Write file
                OutputStream os = new FileOutputStream("./decrypt_result");
                os.write(decryptedFile);
                os.close();
                break;
            }
            } catch (Exception ignored) {
            }
        }
        return returnValue;
    }

    private static double calcHF(Map<Byte, Integer> frequencyMap, byte[] characterArray) {
        double fileLength = characterArray.length;
        double sumOfHF = 0;
        for (Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
            double value = entry.getValue();
            double itemFrequency = value / fileLength;
            sumOfHF += (itemFrequency * (Math.log(itemFrequency) / Math.log(2)));
        }

        sumOfHF = sumOfHF * (-1);
        return sumOfHF;

    }

    private static Map<Byte, Integer> countCharacters(byte[] characterArray) {
        Map<Byte, Integer> frequencyMap = new HashMap<>();

        for (byte b : characterArray) {
            if (frequencyMap.containsKey(b)) {
                int count = frequencyMap.get(b);
                frequencyMap.put(b, count + 1);
            } else {
                frequencyMap.put(b, 1);
            }

        }
        return frequencyMap;
    }

    private static byte[] readFile(String filepath) {
        byte[] result = null;
        Path path = Paths.get(filepath);
        try {
            result = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void crypt(InputStream is, OutputStream os, Cipher cipher) throws IOException, BadPaddingException, IllegalBlockSizeException {
        boolean more = true;
        byte[] input = new byte[cipher.getBlockSize()];

        while (more) {
            int inBytes = is.read(input);

            if (inBytes > 0) {
                os.write(cipher.update(input, 0, inBytes));
            } else {
                more = false;
            }
        }
        os.write(cipher.doFinal());
    }

    public static InputStream convertByteArrToInputStream(byte[] encryptedFile){
        InputStream stream = new ByteArrayInputStream(encryptedFile);
        return stream;
    }
    public static byte[] decrypt(byte[] rawKey, byte[] text) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKey key = new SecretKeySpec(rawKey, 0, rawKey.length, KALGORITHM);
        Cipher cipher = Cipher.getInstance(CALGORITHM);

        try(InputStream is = new ByteArrayInputStream(text);
        ByteArrayOutputStream os = new ByteArrayOutputStream()){
            IvParameterSpec ivParameterSpec = readIv(is, cipher);

            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            crypt(is, os, cipher);

            return os.toByteArray();
        }

    }

    public static IvParameterSpec readIv(InputStream is, Cipher cipher) throws IOException {
        byte[] rawIv = new byte[cipher.getBlockSize()];
        int inBytes = rawIv.length;

        if (inBytes != cipher.getBlockSize()) {
            throw new IOException("can't read IV from file");
        }

        return new IvParameterSpec(rawIv);
    }



}
