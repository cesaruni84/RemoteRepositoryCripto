/**
 * 
 */
package com.bbva;

/**
 * @author P019956
 *
 */
public class HostData
{
  public String hostData;
  public String dataCheck;

  public HostData(String hostData, String dataCheck)
  {
    this.hostData = hostData;
    this.dataCheck = dataCheck;
  }

  public String getHostData() {
    return this.hostData;
  }

  public String getDataCheck() {
    return this.dataCheck;
  }

  public void setHostData(String hostData) {
    this.hostData = hostData;
  }

  public void setDataCheck(String dataCheck) {
    this.dataCheck = dataCheck;
  }
}