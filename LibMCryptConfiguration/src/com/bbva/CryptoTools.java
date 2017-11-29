/**
 * 
 */
package com.bbva;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.bbva.sl.ar.android.libmcrypt.exception.LibMCryptException;
import com.bbva.sl.ar.android.libmcrypt.impl.LibMCryptConstants;
import com.bbva.sl.ar.android.libmcrypt.tools.VarTools;

/**
 * @author P019956
 * 
 */
public final class CryptoTools {
	public static KeyPair generateKeyPair() throws LibMCryptException {
		KeyPair keyPairMovil = null;
		try {
			String algorithm = VarTools
					.bytes2String(LibMCryptConstants.PUBPRIVKEY_KEYTYPE);
			KeyPairGenerator keyGenMovil = KeyPairGenerator
					.getInstance(algorithm);
			AlgorithmParameterSpec paramSpec = new RSAKeyGenParameterSpec(2048,
					LibMCryptConstants.PUBPRIVKEY_EXP);
			keyGenMovil.initialize(paramSpec);
			keyPairMovil = keyGenMovil.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new LibMCryptException(201,
					"Error generating public-private key pair");
		} catch (InvalidAlgorithmParameterException e) {
			throw new LibMCryptException(201,
					"Error generating public-private key pair");
		}
		KeyPairGenerator keyGenMovil;
		return keyPairMovil;
	}

	public static byte[] rsaDecrypt(byte[] encrypted, RSAPrivateKey key)
			throws LibMCryptException {
		if (key == null) {
			throw new LibMCryptException(215,
					"Error recovering RSA private key");
		}
		int blockLength = 256;
		if (encrypted.length == blockLength) {
			try {
				String algorithm = VarTools
						.bytes2String(LibMCryptConstants.PUBPRIVKEY_ALGORITHM);
				Cipher cipher = Cipher.getInstance(algorithm);
				cipher.init(2, key);
				return cipher.doFinal(encrypted);
			} catch (NoSuchAlgorithmException e) {
				throw new LibMCryptException(202, "Error deciphering");
			} catch (NoSuchPaddingException e) {
				throw new LibMCryptException(202, "Error deciphering");
			} catch (InvalidKeyException e) {
				throw new LibMCryptException(202, "Error deciphering");
			} catch (IllegalBlockSizeException e) {
				throw new LibMCryptException(202, "Error deciphering");
			} catch (BadPaddingException e) {
				throw new LibMCryptException(202, "Error deciphering");
			}
		}
		throw new LibMCryptException(203,
				"Incorrect length of the encrypted data");
	}

	public static byte[] rsaEncrypt(byte[] dataByte, RSAPublicKey key)
			throws LibMCryptException {
		try {
			String algorithm = VarTools
					.bytes2String(LibMCryptConstants.PUBPRIVKEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(1, key);
			return cipher.doFinal(dataByte);
		} catch (NoSuchAlgorithmException e) {
			throw new LibMCryptException(204, "Error ciphering RSA");
		} catch (NoSuchPaddingException e) {
			throw new LibMCryptException(204, "Error ciphering RSA");
		} catch (InvalidKeyException e) {
			throw new LibMCryptException(204, "Error ciphering RSA");
		} catch (IllegalBlockSizeException e) {
			throw new LibMCryptException(204, "Error ciphering RSA");
		} catch (BadPaddingException e) {
		}
		throw new LibMCryptException(204, "Error ciphering RSA");
	}

	public static SecretKey generateKeyDESEDE(byte[] keyBytes)
			throws LibMCryptException {
		try {
			byte[] extendedKey = new byte[24];
			System.arraycopy(keyBytes, 0, extendedKey, 0, 16);
			System.arraycopy(keyBytes, 0, extendedKey, 16, 8);

			DESedeKeySpec keySpec = new DESedeKeySpec(extendedKey);
			String algorithm = VarTools
					.bytes2String(LibMCryptConstants.CIPHER_KEYTYPE);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
			return factory.generateSecret(keySpec);
		} catch (InvalidKeyException e) {
			throw new LibMCryptException(207, "Error generating key");
		} catch (NoSuchAlgorithmException e) {
			throw new LibMCryptException(207, "Error generating key");
		} catch (InvalidKeySpecException e) {
		}
		throw new LibMCryptException(207, "Error generating key");
	}

	public static SecretKey generateRandomKeyDESEDE() throws LibMCryptException {
		try {
			SecureRandom random = new SecureRandom();
			byte[] bytes1 = new byte[8];
			random.nextBytes(bytes1);
			byte[] bytes2 = new byte[8];
			random.nextBytes(bytes2);
			byte[] keyBytes = new byte[24];
			System.arraycopy(bytes1, 0, keyBytes, 0, 8);
			System.arraycopy(bytes2, 0, keyBytes, 8, 8);
			System.arraycopy(bytes1, 0, keyBytes, 16, 8);

			DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
			String algorithm = VarTools
					.bytes2String(LibMCryptConstants.CIPHER_KEYTYPE);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
			return factory.generateSecret(keySpec);
		} catch (InvalidKeyException e) {
			throw new LibMCryptException(207, "Error generating key");
		} catch (InvalidKeySpecException e) {
			throw new LibMCryptException(207, "Error generating key");
		} catch (NoSuchAlgorithmException e) {
		}
		throw new LibMCryptException(207, "Error generating key");
	}

	public static byte[] encrypt(SecretKey key, byte[] unencryptedBytes)
			throws LibMCryptException, IllegalStateException, IllegalBlockSizeException, BadPaddingException {
		String algorithm = VarTools
				.bytes2String(LibMCryptConstants.CIPHER_ALGORITHM);
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			byte[] ivBytes = LibMCryptConstants.IV;
			cipher.init(1, key, new IvParameterSpec(ivBytes));
			return cipher.doFinal(unencryptedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new LibMCryptException(206, "Error ciphering");
		} catch (NoSuchPaddingException e) {
			throw new LibMCryptException(206, "Error ciphering");
		} catch (InvalidKeyException e) {
			throw new LibMCryptException(206, "Error ciphering");
		} catch (InvalidAlgorithmParameterException e) {
			throw new LibMCryptException(206, "Error ciphering");
		}
	}

	public static byte[] decrypt(SecretKey key, byte[] encryptedBytes)
			throws LibMCryptException {
		String algorithm = VarTools
				.bytes2String(LibMCryptConstants.CIPHER_ALGORITHM);
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			byte[] ivBytes = LibMCryptConstants.IV;
			cipher.init(2, key, new IvParameterSpec(ivBytes));
			return cipher.doFinal(encryptedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new LibMCryptException(205, "Error deciphering");
		} catch (NoSuchPaddingException e) {
			throw new LibMCryptException(205, "Error deciphering");
		} catch (InvalidKeyException e) {
			throw new LibMCryptException(205, "Error deciphering");
		} catch (InvalidAlgorithmParameterException e) {
			throw new LibMCryptException(205, "Error deciphering");
		} catch (IllegalBlockSizeException e) {
			throw new LibMCryptException(212, "Incorrect block size");
		} catch (BadPaddingException e) {
		}
		throw new LibMCryptException(205, "Error deciphering");
	}

	public static RSAPublicKey generatePublicKey(PropertiesManager pm,
			String keyTag) throws LibMCryptException {
		String keyName = getPublicKeyByTag("publicKey", keyTag,
				pm.getProperties());

		BigInteger modulus = new BigInteger(pm.getProperty(keyName), 16);
		BigInteger pubExponent = LibMCryptConstants.PUBPRIVKEY_EXP;

		RSAPublicKeySpec publicSpec = new RSAPublicKeySpec(modulus, pubExponent);
		try {
			String algorithm = VarTools
					.bytes2String(LibMCryptConstants.PUBPRIVKEY_KEYTYPE);
			KeyFactory factory = KeyFactory.getInstance(algorithm);
			return (RSAPublicKey) factory.generatePublic(publicSpec);
		} catch (NoSuchAlgorithmException ex) {
			throw new LibMCryptException(201,
					"Error generating public-private key pair");
		} catch (InvalidKeySpecException ex) {
		}
		throw new LibMCryptException(201,
				"Error generating public-private key pair");
	}

	public static String getPublicKeyByTag(String pubKeyType, String pubKeyTag,
			Properties prop) throws LibMCryptException {
		if (prop.containsKey(pubKeyType + "." + pubKeyTag)) {
			return pubKeyType + "." + pubKeyTag;
		}
		if ((prop.containsKey(pubKeyType + "." + "default"))
				&& ((pubKeyTag.equals("default")) || (pubKeyTag.equals("")) || (pubKeyTag == null))) {
			return pubKeyType + "." + "default";
		}
		throw new LibMCryptException(214,
				"Key tag not found in properties file");
	}
}
