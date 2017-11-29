/**
 * 
 */
package com.bbva;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.bbva.sl.ar.android.libmcrypt.exception.LibMCryptException;
import com.bbva.util.PinBlockEncryptionUtil;

/**
 * @author CESAR
 *
 */
public class TestEncripta {

	/**
	 * @param args
	 * @throws LibMCryptException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws LibMCryptException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// TODO Auto-generated method stub
		
		PinBlockEncryptionUtil util = new PinBlockEncryptionUtil();
		
		String PAN = "4381680001262369";  //4919109022546171

		String PIN = "2222";
		
		String pinBlockHEX = util.encryptPinBlock(PAN, PIN);
		
		System.out.println("PINBLOCK: " + pinBlockHEX);
		
		LibMCrypt lib = new LibMCryptAndroid();
	//	String claveHex = "0412AC89ABCDEF67";   //corresponde al PIN :1234
		
     //	String pinBlockHEX = "0412AC89ABCDEF67";   //corresponde al PIN :1234
		
		HostData hostData = lib.encryptHostData(pinBlockHEX, null,"rsa");
		System.out.println(hostData.getHostData());
	}

}
