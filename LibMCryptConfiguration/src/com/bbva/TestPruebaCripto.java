package com.bbva;

import java.io.UnsupportedEncodingException;

import com.bbva.sl.ar.android.libmcrypt.exception.LibMCryptException;
import javax.xml.bind.DatatypeConverter;

public class TestPruebaCripto {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

		LibMCrypt lib = new LibMCryptAndroid();

		try {
			KpubData kpd = lib.generateKpub(null);
			System.out.println("Llave Publica: " + kpd.getKPubValue());
			System.out.println("Llave Privada: " + lib.getKeyPrivate());
			
		    byte[] myBytes = kpd.getKPubValue().getBytes("UTF-8");
			System.out.println("Llave Publica HEX: " + DatatypeConverter.printHexBinary(myBytes));
			
			byte[] myBytes1 =lib.getKeyPrivate().getBytes("UTF-8");
			System.out.println("Llave Privada HEX: " + DatatypeConverter.printHexBinary(myBytes1));
			
			
		} catch (LibMCryptException e) {
			int errCode = e.getErrCode();
			System.out.println("Código de error: " + errCode);
		}

	}

}
