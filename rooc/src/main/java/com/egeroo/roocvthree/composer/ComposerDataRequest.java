package com.egeroo.roocvthree.composer;

import java.io.Serializable;

public class ComposerDataRequest  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] customChannel;
	private boolean useCustomChannel;
	private String[] responseText;
	private String variation;
	
	
	public String[] getCustomChannel() {
		return customChannel;
	}
	public void setCustomChannel(String[] customChannel) {
		this.customChannel = customChannel;
	}
	public boolean getUseCustomChannel() {
		return useCustomChannel;
	}
	public void setUseCustomChannel(boolean useCustomChannel) {
		this.useCustomChannel = useCustomChannel;
	}
	public String[] getResponseText() {
		return responseText;
	}
	public void setResponseText(String[] responseText) {
		this.responseText = responseText;
	}
	public String getVariation() {
		return variation;
	}
	public void setVariation(String variation) {
		this.variation = variation;
	}
	
	
	

}
