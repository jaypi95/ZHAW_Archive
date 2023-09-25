package ch.zhaw.init.its.labs.publickey;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class RSA {
    private static final int DEFAULT_MODULUS_LENGTH = 2048;
    private static final int DEFAULT_P_LENGTH = DEFAULT_MODULUS_LENGTH / 2 - 9;
    private static final int DEFAULT_Q_LENGTH = DEFAULT_MODULUS_LENGTH / 2 + 9;
    private static final BigInteger PUBLIC_EXPONENT = BigInteger.valueOf(65537);
    private final BigInteger n;
    private final BigInteger e;
    private BigInteger d;

    /**
     * Generates a random RSA key pair.
     * <p>
     * This constructor generates a random RSA key pair of unknown, but substantial,
     * modulus length. The public exponent is 65537.
     */
    public RSA() {
        // --------> Your solution here! <--------
        BigInteger p = createRandomPrime(DEFAULT_P_LENGTH);
        BigInteger q = createRandomPrime(DEFAULT_Q_LENGTH);

        this.n = p.multiply(q);
        this.e = PUBLIC_EXPONENT;

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        this.d = gcdExtended(this.e, phi)[1];


        System.out.print("Public key: ");
        System.out.println(this.n + this.e.toString());
        System.out.print("Private key: ");
        System.out.println(this.d.toString() + this.n);
    }

    /**
     * Reads a public key or a key pair from input stream.
     *
     * @param is the input stream to read the public key or key pair from
     * @throws IOException if either the modulus, public exponent, or private
     *                     exponent can not be read
     */
    public RSA(ObjectInputStream is) throws IOException, ClassNotFoundException {
        n = (BigInteger) is.readObject();
        e = (BigInteger) is.readObject();

        try {
            d = (BigInteger) is.readObject();
        } catch (OptionalDataException e) {
            if (!e.eof) {
                throw e;
            }
        }
    }

    public BigInteger[] gcdExtended(BigInteger p, BigInteger q) {
        BigInteger[] val = new BigInteger[3];

        if (q.equals(BigInteger.ZERO)) {
            val[0] = p;
            val[1] = BigInteger.ONE;
            val[2] = BigInteger.ZERO;
        } else {
            BigInteger[] val2 = gcdExtended(q, p.mod(q));
            val[0] = val2[0];
            val[1] = val2[2];
            val[2] = val2[1].subtract(p.divide(q).multiply(val2[2]));
        }

        return val;
    }

    private BigInteger createRandomPrime(int numBits) {
        BigInteger max = BigInteger.ONE.shiftLeft(numBits);
        BigInteger prime;
        do {
            BigInteger integer = new BigInteger(numBits, ThreadLocalRandom.current()); // Pick a random number between 0 and 2 ^ numbits - 1
            prime = integer.nextProbablePrime(); // Pick the next prime. Caution, it can exceed n^numbits, hence the loop
        } while (prime.compareTo(max) > 0);
        return prime;
    }

    /**
     * Encrypts the plain text with the public key.
     *
     * @param plain the plain text to encrypt
     * @return the ciphertext
     * @throws BadMessageException then the plain text is too large or too small
     */
    public BigInteger encrypt(BigInteger plain) throws BadMessageException {
        if (plain.compareTo(n) > 0) {
            throw new BadMessageException("plaintext too large");
        }

        if (plain.compareTo(BigInteger.ZERO) <= 0) {
            throw new BadMessageException("plaintext too small");
        }

        // --------> Your solution here! <--------
        return plain.modPow(e, n);
    }

    /**
     * Decrypts the ciphertext with the private key.
     *
     * @param cipher the ciphertext to decrypt
     * @return decrypted BigInteger
     * @throws BadMessageException If there is no private key or the cipher is too large
     */
    public BigInteger decrypt(BigInteger cipher) throws BadMessageException {
        if (d == null) {
            throw new BadMessageException("don't have private key");
        }

        if (cipher.compareTo(n) > 0) {
            throw new BadMessageException("ciphertext too large");
        }

        if (cipher.compareTo(BigInteger.ZERO) <= 0) {
            throw new BadMessageException("ciphertext too small");
        }

        // --------> Your solution here! <--------
        return cipher.modPow(d, n);
    }

    /**
     * Saves the entire key pair.
     *
     * @param os the output strem to which to save the key pair
     * @throws IOException if saving goes wrong or there is no private key to save
     */
    public void save(ObjectOutputStream os) throws IOException {
        savePublic(os);

        if (d != null) {
            os.writeObject(d);
        } else {
            throw new IOException("don't have private key to save");
        }
    }

    /**
     * Saves only the public part of the key pair.
     *
     * @param os the output stream to which to save the public key
     * @throws IOException if saving goes wrong
     */
    public void savePublic(ObjectOutputStream os) throws IOException {
        if (d == null || n == null) throw new IOException("don't have public key to save");
        os.writeObject(n);
        os.writeObject(e);
    }

    /**
     * Signs a message with the private key.
     *
     * @param message the message to sign
     * @return the signature for this message
     * @throws BadMessageException if something is wrong with this message or there is no private key
     */
    public BigInteger sign(BigInteger message) throws BadMessageException {
        // --------> Your solution here! <--------
        return decrypt(message);
    }

    /**
     * Verifies a signature of a message.
     *
     * @param message   the message whose signature to check
     * @param signature the signature to check
     * @return true iff the signature was made for this message by this key
     * @throws BadMessageException if something is wrong with this message
     */
    public boolean verify(BigInteger message, BigInteger signature) throws BadMessageException {
        // --------> Your solution here! <--------
        return encrypt(signature).equals(message);
    }

    public boolean equals(RSA other) {
        return this.n.equals(other.n) && this.e.equals(other.e) && (this.d == null && other.d == null || this.d.equals(other.d));
    }


    private class OperationNotSupportedError extends RuntimeException {
    }


}
