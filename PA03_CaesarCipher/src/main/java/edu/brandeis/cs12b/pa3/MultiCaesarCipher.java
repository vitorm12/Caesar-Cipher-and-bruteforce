package edu.brandeis.cs12b.pa3;

import java.io.FileNotFoundException;

class MultiCaesarCipher{
	/**
	 * This is method to encode a string with multiple shift digits
	 * @param s the string to be encoded
	 * @param shifts the set of multiple shift digits
	 * @return String the string encoded
	 * @throws FileNotFoundException 
	 */
	public static String multiEncode(String s, int[] shifts) throws FileNotFoundException{
		CaesarCipher c = new CaesarCipher();
		String ss = s;
		boolean isShiftsEvenOrOdd = (shifts.length % 2 == 0);
		for(int shift : shifts) {
			if(shift % 2 == 0 && isShiftsEvenOrOdd) {
				ss = c.encode(ss, shift);
			}else if(!isShiftsEvenOrOdd){
				ss = c.encode(ss, shift);
			}
		}
		return ss;
	} 
	
	/**
	 * This is method to decode a string with multiple shift digits
	 * @param s the string to be decoded
	 * @param shifts the set of multiple shift digits
	 * @return String the string decoded
	 */
	public static String multiDecode(String s, int[] shifts){
		return null;	
	}
}