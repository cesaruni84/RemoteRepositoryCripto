/**
 * 
 */
package com.bbva;

import java.security.interfaces.RSAPrivateKey;

import com.bbva.sl.ar.android.libmcrypt.exception.LibMCryptException;

/**
 * @author P019956
 * 
 */
public class TestPruebaDesencripta {

	/**
	 * @param args
	 * @throws LibMCryptException
	 */
	public static void main(String[] args) throws LibMCryptException {

		LibMCrypt lib = new LibMCryptAndroid();

		// TODO Auto-generated method stub
		
		//Comenetarios adicionales
		//Comentario adicionales
		KpubData kpd = lib.generateKpub(null);
		
		
		

		
		String datosHOST = "";

		try {
			String decryptedString = lib.decryptHostData(datosHOST);
			System.out.println(decryptedString);
		} catch (LibMCryptException e) {
			int errCode = e.getErrCode();
		}

	}

}
