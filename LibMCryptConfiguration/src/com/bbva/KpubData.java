package com.bbva;

public class KpubData {
	private String KPubValue;
	private String KPubCheck;

	public KpubData(String KPubValue, String KPubCheck) {
		this.KPubValue = KPubValue;
		this.KPubCheck = KPubCheck;
	}

	public String getKPubValue() {
		return this.KPubValue;
	}

	public String getKPubCheck() {
		return this.KPubCheck;
	}

	public void setKPubValue(String kPubValue) {
		this.KPubValue = kPubValue;
	}

	public void setKPubCheck(String kPubCheck) {
		this.KPubCheck = kPubCheck;
	}
}