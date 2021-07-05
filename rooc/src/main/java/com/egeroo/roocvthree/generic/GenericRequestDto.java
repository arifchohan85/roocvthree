package com.egeroo.roocvthree.generic;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.springframework.lang.NonNull;

/**
 * @author vebbi
 *
 */
@XmlRootElement(name = "requestDTO")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"key", "params"})
public class GenericRequestDto implements Serializable,Comparable<GenericRequestDto>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private String uri;
	private Map<String, Object> params;
	
	@NonNull
	@XmlElement(name = "key")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@XmlElement(name = "uri")
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	@NonNull
	@XmlElement(name="params")
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
//	@Override
//	public int compareTo(final GenericRequestDto o) {
//	    return ComparisonChain.start()
//	            .compare(this.key, o.key)
//	            .result();
//	      }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericRequestDto other = (GenericRequestDto) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(GenericRequestDto o) {
		// TODO Auto-generated method stub
		return 0;
	}		
	
	
}