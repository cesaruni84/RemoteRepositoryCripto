/**
 * 
 */
package com.bbva;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import javax.crypto.SecretKey;

import com.bbva.sl.ar.android.libmcrypt.exception.LibMCryptException;
import com.bbva.sl.ar.android.libmcrypt.impl.HostDataByte;
import com.bbva.sl.ar.android.libmcrypt.tools.PropertiesManager;
import com.bbva.sl.ar.android.libmcrypt.tools.VarTools;

/**
 * @author P019956
 * 
 */
public class LibMCryptAndroid implements LibMCrypt {
	private RSAPrivateKey priK;
	//private Context context;
	private PropertiesManager pm;

	public LibMCryptAndroid() {
		//this.pm = new PropertiesManager();
		//this.context = ctx;
	}

	public KpubData generateKpub(String dataToKpubCheck)
			throws LibMCryptException {
	//	Log.v("generateKpub", "Starting key pair generation");

		KeyPair keyPair = CryptoTools.generateKeyPair();
		
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		

		String publicK = publicKey.getModulus().toString(16);
		
		String kPubCheck = null;

		KpubData ret = new KpubData(publicK, kPubCheck);

		this.priK = ((RSAPrivateKey) keyPair.getPrivate());

		//Log.v("generateKpub", "key pair generated successfully");

		return ret;
	}

	public String decryptHostData(String hostData) throws LibMCryptException {
	//	Log.v("decryptHostData", "Starting data decryption");

		if (hostData.length() != 1028) {
	//		Log.w("decryptHostData",
		//			"Error code 203: Incorrect length of the encrypted data");
			throw new LibMCryptException(203,
					"Incorrect length of the encrypted data");
		}

		RSAPrivateKey privateKey = this.priK;

		try {
			HostDataByte hdb = new HostDataByte(hostData);

			byte[] protoVersion = VarTools.hexStr2Bytes("01");
			if (!Arrays.equals(hdb.getVersion(), protoVersion)) {
				throw new LibMCryptException(209, "Error with protocol version");
			}

			byte[] aux = CryptoTools.rsaDecrypt(hdb.getSegmento1(), privateKey);

			SecretKey key = CryptoTools.generateKeyDESEDE(aux);

			byte[] datosEncrypt = CryptoTools.rsaDecrypt(hdb.getSegmento2(),
					privateKey);

			byte[] datos = CryptoTools.decrypt(key, datosEncrypt);

	//		Log.v("decryptHostData", "Data decrypted successfully");
			return VarTools.bytes2HexStr(datos);
		} catch (LibMCryptException e) {
	//		Log.w("decryptHostData",
		//			"Error code " + e.getErrCode() + ": " + e.getErrMsg());
			throw new LibMCryptException(e.getErrCode(), e.getErrMsg());
		}
	}



	public HostData encryptHostData(String datos, String infoToDataCheck,
			String keyTag) throws LibMCryptException {
	//	Log.v("encryptHostData", "Starting data encryption");

		if (datos == null) {
	//		Log.w("encryptHostData",
		//			"Error code 211: Incorrect types conversion");
			throw new LibMCryptException(211, "Incorrect types conversion");
		}

		if ((datos.length() > 480) || (datos.length() % 16 != 0)) {
	//		Log.w("encryptHostData", "Error code 213: Incorrect data length");
			throw new LibMCryptException(213, "Incorrect pin size");
		}

		try {
			String keyIndexTag = CryptoTools.getPublicKeyByTag("keyIndex",
					keyTag, this.pm.getProperties());
			String indiceHEX = this.pm.getProperty(keyIndexTag);
			String protocoloHEX = "01";

			byte[] datosByte = VarTools.hexStr2Bytes(datos);

			RSAPublicKey pk = CryptoTools.generatePublicKey(this.pm, keyTag);

			SecretKey secretKey = CryptoTools.generateRandomKeyDESEDE();

			byte[] datosEncriptados = CryptoTools.encrypt(secretKey, datosByte);

			byte[] encryptedDatosByte = CryptoTools.rsaEncrypt(
					datosEncriptados, pk);

			byte[] encryptedKeyByte = CryptoTools.rsaEncrypt(
					Arrays.copyOfRange(secretKey.getEncoded(), 0, 16), pk);

			String encryptedDatosHex = VarTools
					.bytes2HexStr(encryptedDatosByte);
			String encryptedKeyHex = VarTools.bytes2HexStr(encryptedKeyByte);

			String hostData = indiceHEX + protocoloHEX + encryptedKeyHex
					+ encryptedDatosHex;

	//		Log.v("encryptHostData", "Data encrypted successfully");
			return new HostData(hostData, null);
		} catch (Exception e) {
	//		Log.w("encryptHostData",
		//			"Error code " + e.getErrCode() + ": " + e.getErrMsg());
			throw new LibMCryptException(9999, e.getMessage());
		}
	}

	public HostData encryptHostData(String datos, String infoToDataCheck)
			throws LibMCryptException {
		return encryptHostData(datos, infoToDataCheck, "default");
	}

	public String getKeyPrivate() throws LibMCryptException {
		// TODO Auto-generated method stub
		
		String privateK = priK.getModulus().toString(16);
		return privateK;
	}

	public void setPriK(RSAPrivateKey priK) {
		this.priK = priK;
	}
	
	

	
}