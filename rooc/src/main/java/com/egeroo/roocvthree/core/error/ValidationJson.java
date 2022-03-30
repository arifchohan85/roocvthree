package com.egeroo.roocvthree.core.error;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class ValidationJson {
	
	private static final Gson gson = new Gson();

	  public ValidationJson(){}
	
	public boolean isJSONValidarray(String test) {
	    try {
	        //new JSONObject(test);
	    	new JSONArray(test);
	    } catch (JSONException ex) {
	        // edited, to include @Arthur's comment
	        // e.g. in case JSONArray is valid as well...
//	        try {
//	            new JSONArray(test);
//	        } catch (JSONException ex1) {
//	            return false;
//	        }
	        return false;
	    }
	    return true;
	}
	
	public boolean isJSONValidgson(String jsonInString) {
	      try {
	          gson.fromJson(jsonInString, Object.class);
	          return true;
	      } catch(com.google.gson.JsonSyntaxException ex) { 
	          return false;
	      }
	}
	
	public boolean isJSONValid(String jsonInString ) {
	    try {
	       final ObjectMapper mapper = new ObjectMapper();
	       mapper.readTree(jsonInString);
	       return true;
	    } catch (IOException e) {
	       return false;
	    }
	}
	
	public boolean isJSONValidstandard(String jsonInString) {
	      try {
	    	  JSONObject jsonObjectlocal = new JSONObject(jsonInString);
	          return true;
	      } catch(JSONException ex) { 
	          return false;
	      }
	}
	
	

}
