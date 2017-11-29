package com.bbva;

import com.bbva.sl.ar.android.libmcrypt.exception.LibMCryptException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager
{
  private Properties properties;

  public PropertiesManager()
    throws LibMCryptException
  {
    this.properties = new Properties();

    InputStream stream = PropertiesManager.class.getResourceAsStream("/res/Configuration.properties");
    try
    {
      this.properties.load(stream);
    } catch (IOException e) {
      throw new LibMCryptException(210, "Error with IO properties file");
    }
  }

  public String getProperty(String key) {
    return this.properties.getProperty(key);
  }

  public void put(String key, String value) {
    this.properties.put(key, value);
  }

  public Properties getProperties() {
    return this.properties;
  }
}
