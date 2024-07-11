package me.alipson.multiswapcipher;

// Represents a classical cipher that is able to encrypt a plaintext into a ciphertext, and
// decrypt a ciphertext into a plaintext. Also capable of encrypting and decrypting entire files

public interface Cipher {
    // The minimum character able to be encrypted/decrypted by any cipher
    public static final int MIN_CHAR = (int) ('A');

    // The maximum character able to be encrypted/decrypted by any cipher
    public static final int MAX_CHAR = (int) ('Z');

    // The total number of characters able to be encrypted/decrypted by any cipher
    // (aka. the encodable range)
    public static final int TOTAL_CHARS = MAX_CHAR - MIN_CHAR + 1;

    // Behavior: Applies this Cipher's encryption scheme to 'input', returning the
    // result
    // Exceptions: None
    // Returns: The result of applying this Cipher's encryption scheme to `input`
    // Parameters: 'input' - the string to be encrypted
    public String encrypt(String input);

    // Behavior: Applies this inverse of this Cipher's encryption scheme to 'input'
    // (reversing
    // a single round of encryption if previously applied), returning the result
    // Exceptions: None
    // Returns: The result of applying the inverse of this Cipher's encryption
    // scheme to `input`
    // Parameters: 'input' - the string to be decrypted
    public String decrypt(String input);
}
