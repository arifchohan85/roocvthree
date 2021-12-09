package com.egeroo.roocvthree.intent.detail;

import java.io.Serializable;

public class AudioResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String audioSource;
	private boolean isAutoPlay;
	private String loop;
	private String audioSourceUrl;
	private String audioSourceFile;
	
	public String getAudioSource() {
		return audioSource;
	}
	public void setAudioSource(String audioSource) {
		this.audioSource = audioSource;
	}
	public boolean isAutoPlay() {
		return isAutoPlay;
	}
	public void setAutoPlay(boolean isAutoPlay) {
		this.isAutoPlay = isAutoPlay;
	}
	public String getLoop() {
		return loop;
	}
	public void setLoop(String loop) {
		this.loop = loop;
	}
	public String getAudioSourceUrl() {
		return audioSourceUrl;
	}
	public void setAudioSourceUrl(String audioSourceUrl) {
		this.audioSourceUrl = audioSourceUrl;
	}
	public String getAudioSourceFile() {
		return audioSourceFile;
	}
	public void setAudioSourceFile(String audioSourceFile) {
		this.audioSourceFile = audioSourceFile;
	}
	
}
