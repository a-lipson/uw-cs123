package me.alipson.extracredit;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

import java.io.*;
import java.util.*;

// Represents a classical cipher that is able to encrypt a plaintext into a ciphertext, and
// decrypt a ciphertext into a plaintext. Also capable of encrypting and decrypting entire files

public abstract class Cipher {
    // The minimum character able to be encrypted/decrypted by any cipher
    // (we encourage you to change this value when testing!)
    public static final int MIN_CHAR = (int) (' ');

    // The maximum character able to be encrypted/decrypted by any cipher
    // (we encourage you to change this value when testing!)
    public static final int MAX_CHAR = (int) ('}');

    // The total number of characters able to be encrypted/decrypted by any cipher
    // (aka. the encodable range)
    public static final int TOTAL_CHARS = MAX_CHAR - MIN_CHAR + 1;

    // checks whether an inputted char falls within the encodable range,
    // returns true if valid and false if not.
    public static boolean isValidChar(char c) {
        return MIN_CHAR <= c && c <= MAX_CHAR;
    }

    // gives the int position of an inputted char in the endodable range.
    public static int getCharIndex(char c) {
        return (int) c - MIN_CHAR;
    }

    // creates and gives a list of the encodable chars in cipher
    public static List<Character> getEncodableChars() { // would prefer to provide an iterable here
        List<Character> chars = new ArrayList<>();
        for (int c = MIN_CHAR; c <= MAX_CHAR; c++) {
            chars.add((char) c);
        }
        return chars;
    }

    // Behavior: Applies this Cipher's encryption scheme to the file with the
    // given 'fileName', creating a new file to store the results.
    // Exceptions: Throws a FileNotFoundException if a file with the provided
    // 'fileName'
    // doesn't exist
    // Returns: None
    // Parameters: 'fileName' - The name of the file to be encrypted
    public void encryptFile(String fileName) throws FileNotFoundException {
        fileHelper(fileName, true, "-encrypted");
    }

    // Behavior: Applies the inverse of this Cipher's encryption scheme to the file
    // with the
    // given 'fileName' (reversing a single round of encryption if previously
    // applied)
    // creating a new file to store the results.
    // Exceptions: Throws a FileNotFoundException if a file with the provided
    // 'fileName'
    // doesn't exist
    // Returns: None
    // Parameters: 'fileName' - The name of the file to be decrypted
    public void decryptFile(String fileName) throws FileNotFoundException {
        fileHelper(fileName, false, "-decrypted");
    }

    // Behavior: Reads from an input file with 'fileName', either encrypting or
    // decrypting
    // depending on 'encrypt', printing the results to a new file with 'suffix'
    // appended to the input file's name
    // Exceptions: Throws a FileNotFoundException if a file with the provided
    // 'fileName'
    // doesn't exist
    // Returns: None
    // Parameters: 'fileName' - the name of the file to be encrypted / decrypted
    // 'encrypt' - whether or not encryption should occur
    // 'suffix' - appended to the fileName when creating the output file
    private void fileHelper(String fileName, boolean encrypt, String suffix)
            throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        String out = fileName.split("\\.txt")[0] + suffix + ".txt";
        PrintStream ps = new PrintStream(out);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            ps.println(encrypt ? encrypt(line) : decrypt(line));
        }
        ps.close();
    }

    // Behavior: Applies this Cipher's encryption scheme to 'input', returning the
    // result
    // Exceptions: None
    // Returns: The result of applying this Cipher's encryption scheme to `input`
    // Parameters: 'input' - the string to be encrypted
    public abstract String encrypt(String input);

    // Behavior: Applies this inverse of this Cipher's encryption scheme to 'input'
    // (reversing
    // a single round of encryption if previously applied), returning the result
    // Exceptions: None
    // Returns: The result of applying the inverse of this Cipher's encryption
    // scheme to `input`
    // Parameters: 'input' - the string to be decrypted
    public abstract String decrypt(String input);
}
