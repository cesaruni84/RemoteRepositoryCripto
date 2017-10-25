/**
 * 
 */
package com.bbva;

import com.bbva.sl.ar.android.libmcrypt.exception.LibMCryptException;

/**
 * @author P019956
 *
 */
public abstract interface LibMCrypt
{
  public abstract KpubData generateKpub(String paramString)
    throws LibMCryptException;

  public abstract String decryptHostData(String paramString)
    throws LibMCryptException;

  public abstract HostData encryptHostData(String paramString1, String paramString2)
    throws LibMCryptException;

  public abstract HostData encryptHostData(String paramString1, String paramString2, String paramString3)
    throws LibMCryptException;
  
  public abstract String getKeyPrivate()
		    throws LibMCryptException;
}
