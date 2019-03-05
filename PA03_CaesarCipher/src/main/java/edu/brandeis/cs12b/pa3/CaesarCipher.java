package edu.brandeis.cs12b.pa3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * @author Name: Vitor Mouzinho 
 * Due FEB 10,2018 
 * PA03: Caesar Cipher COSI 12B - Pito Salas 
 * DESCRIPTION: Program 
 * encodes and decodes using caesar
 * cipher method Program also performs brute force/dictionary attack to
 * crack cipher
 */
public class CaesarCipher {

	HashSet<String> dictionary = new HashSet<String>();

	/**
	 * Constructor reads in file to dictionary
	 **/
	public CaesarCipher() {
		File dict = new File("dictionary/google10000.txt");
		Scanner readFile;
		try {
			readFile = new Scanner(dict);
			while (readFile.hasNextLine()) {
				dictionary.add(readFile.nextLine());
			}
			readFile.close();
		} catch (FileNotFoundException s) {
			System.out.println("File does Not Exist Please Try Again: ");
		}
	}

	/**
	 * This is method to encode a string with a n shift
	 * 
	 * @param s the string to be encoded
	 * @param n the shift digit
	 * @return String the string encoded
	 */
	public String encode(String s, int n) {
		return mutateString(s, n);
	}

	/**
	 * This is method to decode a string with an n shift
	 * 
	 * @param s the string to be decoded
	 * @param n the shift digit
	 * @return String the string decoded
	 */
	public String decode(String s, int n) {
		return mutateString(s, -n);
	}

	/**
	 * This is method to decode a without knowing the shift (if possible)
	 * 
	 * @param s the string to be decoded
	 * @return String the string decoded
	 */
	public String decode(String s) {
		System.out.println("LOG: BRUTE FORCE WILL NOW START");
		if (s == null) {
			return null;
		}
		int[] postiveShifts = createArray();
		System.out.println("LOG: BRUTE FORCE Fininished");
		return bruteForceAttack(postiveShifts, s);
	}

	/**
	 * @param String
	 * @param int n finds correct shifts of a string
	 ***/
	public String mutateString(String s, int n) {
		if (s == null) {
			return null;
		}
		String result = "";
		for (char i : s.toCharArray()) {
			int newAscii = 0;
			if (Character.isUpperCase(i)) {
				newAscii = checkIfInRange(i, n, 26, 90, 65, 26);
			} else if (Character.isLowerCase(i)) {
				newAscii = checkIfInRange(i, n, 26, 122, 97, 26);
			} else if (Character.isDigit(i)) {
				newAscii = checkIfInRange(i, n, 10, 57, 48, 10);
			} else {
				newAscii = (int) i;
			}
			i = (char) newAscii;
			result += (i);
		}
		return result;
	}

	/**
	 * @param newAscci of ASCII value
	 * @param high     limit of the range of ASCII Value
	 * @param Starting value of the range
	 * @param addOrSub decides whether to be positive or negative shift This method
	 *                 finds out if it is a negative or positive shift its helper
	 *                 method for cipherAndDecipher
	 * @return an int newAscii which represents if its negative or positive shift
	 */
	public int checkIfInRange(char i, int shift, int mod, int maxRange, int lowRange, int addOrSub) {
		int newAscii = (((int) (i + shift) - lowRange) % mod + lowRange);
		if (newAscii < lowRange) {
			newAscii += addOrSub;
		} else if (newAscii > maxRange) {
			newAscii -= addOrSub;
		}
		return newAscii;
	}

	/**
	 * @param int correctWords, how many words
	 * @return returns percentage
	 **/
	public float calculatePercentage(int correctWords, int howManyWords) {
		int correct = correctWords;
		int outOfhowMany = howManyWords;
		return (float) Math.floor(correct / outOfhowMany * 100);
	}

	/**
	 * @param takes an array of either postive or negative shifts
	 * @param takes a string that you are trying to break
	 * @return returns the string decoded if found, if not return null
	 **/
	public String bruteForceAttack(int[] shiftValues, String s) {
		int index = 0;
		float max = 0;
		int indexMax = 0;
		for (int shift : shiftValues) {
			String mutatedString = mutateString(s, -1 * shift);
			float percentage = dictornaryFrequency(mutatedString);
			if (percentage >= 90) {
				return mutatedString;
			}
			if (percentage > max) {
				max = percentage;
				indexMax = index;
			}
			index++;
		}
		System.out.println("Log: Brute force finished 90% of the words were not found in Dictonary");
		System.out.println("Log: CLOSET DECRYPTION WAS: " + mutateString(s, -1 * indexMax));
		return null;
	}

	/**
	 * @param s takes in a string
	 * @return what percentage of the string is in dictionary
	 **/
	public float dictornaryFrequency(String s) {
		int correctWords = 0;
		String[] wordsInString = s.split("[ ]");
		for (String word : wordsInString) {
			if (dictionary.contains(word.toLowerCase())) {
				correctWords++;
			}
		}
		return calculatePercentage(correctWords, wordsInString.length);
	}

	/**
	 * Creates int array with possible shifts
	 **/
	public int[] createArray() {
		int[] array = new int[27];
		for (int i = 0; i <= 26; i++) {
			array[i] = i;
		}
		return array;
	}
}
