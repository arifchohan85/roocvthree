package com.egeroo.roocvthree.generic;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



@Service
public class GenericService {
	private static final Logger LOG = Logger.getLogger(GenericService.class);
	private static final String FAILED = "FAILED";
	
	String status = "SUCCESS";
	String errorMessage = "";
	Document document = null;
	String result = "";
	String requestSoap = "";
	String wsdlUri = "";
	Map<String, String> mappingRequest = new HashMap<>();
	GenericResponseDTO dtoResponse = new GenericResponseDTO();
	BufferedReader br = null;
	DocumentBuilder builder = null;
	PostMethod methodPost = null;
	
	/**
	 * Main method of GenericServices which handle communication between REST and SOAP.
	 * 
	 * <p>
	 * See <a href="http://stackoverflow.com/questions/34848491/false-positive-in-sonarqube-squids2583">False Positif in SonarQube</a>
	 * </p>
	 * 
	 * @param key
	 * @param keyParam
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "null" })
	public GenericResponseDTO convertSOAPtoJSON(String key, Map keyParam, String uri, String tenantIdentifier) {
		//GenericMapper genericMapper = new GenericMapperImpl(tenantIdentifier);
		try {
			dtoResponse.setKey(key);
			List<RequestMappingDTO> listMapp = null;//genericMapper.getListData(key);

			if(listMapp.isEmpty()){
				status = FAILED;
				errorMessage = "Request key is not registered.";
				dtoResponse.setStatus(status);
				dtoResponse.setErrorMessage(errorMessage);
				return dtoResponse;
			}
			
			mappingRequest = generateRequestMap(listMapp);
			requestSoap = generateBodySoap(listMapp);
			if(!("").equals(uri) && uri != null){
				wsdlUri = uri;
			}else{
				wsdlUri = generateUri(listMapp);
			}
			requestSoap = generateBodySoap(keyParam, mappingRequest, requestSoap);

			HttpClient client = new HttpClient();
			client.getParams().setParameter("http.useragent", "Web Service Client");
			client.getParams().setParameter(HttpConnectionManagerParams.CONNECTION_TIMEOUT, 60000);

			methodPost = new PostMethod(wsdlUri);
			InputStream inputStream = new ByteArrayInputStream(requestSoap.getBytes(StandardCharsets.UTF_8));
			methodPost.setRequestEntity(new InputStreamRequestEntity(inputStream));
			methodPost.addRequestHeader("Content-Type", "text/xml");
			methodPost.addRequestHeader("SOAPAction", "process");

			int returnCode = client.executeMethod(methodPost);
			if(returnCode == 404){
				status = FAILED;
				errorMessage = "The requested resource is not available.";
				dtoResponse.setStatus(status);
				dtoResponse.setErrorMessage(errorMessage);
				return dtoResponse;
			}
			br = new BufferedReader(new InputStreamReader(methodPost.getResponseBodyAsStream()));
			do {
				result = br.readLine();
			} while (br.readLine() != null);

			br.close();
			methodPost.releaseConnection();
			
			LOG.debug("request : " + requestSoap);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			
			document = builder.parse(new InputSource(new StringReader(result)));

			dtoResponse = generateRestResponse(dtoResponse, document, listMapp);
		} catch (IOException e) {
			LOG.error(e);
		} catch (SAXException e) {
			LOG.error(e);
		} catch (ParserConfigurationException e) {
			LOG.error(e);
		} catch (Exception e) {
			LOG.error(e);
		}

		return dtoResponse;
	}

	/**
	 * Method to generate a Map which contains mapping between REST parameter
	 * and SOAP Request Body.
	 * 
	 * @param listMapp
	 * @return
	 */
	public Map<String, String> generateRequestMap(List<RequestMappingDTO> listMapp) {
		Map<String, String> mapResult = new HashMap<>();

		for (RequestMappingDTO requestMappingDTO : listMapp) {
			if (("REQUEST").equals(requestMappingDTO.getDataType())) {
				mapResult.put(requestMappingDTO.getKeys(), requestMappingDTO.getKeysValue());
			}
		}
		return mapResult;
	}

	/**
	 * Method to get SOAP Body Request which will be used to invoke certain function.
	 * 
	 * @param listMapp
	 * @return
	 */
	public String generateBodySoap(List<RequestMappingDTO> listMapp) {
		for (RequestMappingDTO requestMappingDTO : listMapp) {
			if (("SOAP BODY").equals(requestMappingDTO.getDataType())) {
				return requestMappingDTO.getDataValue();
			}
		}
		return null;
	}

	/**
	 * Method to get URI which will be used as the target of the SOAP's invocation.
	 * 
	 * @param listMapp
	 * @return
	 */
	public String generateUri(List<RequestMappingDTO> listMapp) {
		for (RequestMappingDTO requestMappingDTO : listMapp) {
			if (("URI").equals(requestMappingDTO.getDataType())) {
				return requestMappingDTO.getDataValue();
			}
		}
		return null;
	}

	/**
	 * Method to generate the SOAP Body Request which will be used as the request parameter
	 * when invoking SOAP's target.
	 * 
	 * @param keyParam
	 * @param mappingRequest
	 * @param requestSoap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "cast" })
	public String generateBodySoap(Map keyParam, Map<String, String> mappingRequest, String requestSoap) {
		String resultBody = requestSoap;
		for (Entry<String, String> keyMap : mappingRequest.entrySet()) {
			resultBody = StringUtils.replace(resultBody, ">" + keyMap.getValue() + "<", (String) (">" + keyParam.get(keyMap.getKey()) + "<"));
		}
		return resultBody;
	}

	/**
	 * Method to convert SOAP's Response Body to GenericResponseDto.
	 * 
	 * @param document
	 * @param listMapp
	 * @return
	 */
	public GenericResponseDTO generateRestResponse(GenericResponseDTO dtoResponse, Document document, List<RequestMappingDTO> listMapp) {
		Map<String, Object> mappedField = new HashMap<>();
		List<Map<String, Object>> mappedResult = new ArrayList<>();
		
		String bodyName = "";
		List<String> childNames = new ArrayList<>();
		for (RequestMappingDTO requestMappingDTO : listMapp) {
			if (("RESPONSE").equals(requestMappingDTO.getDataType())) {
				if (requestMappingDTO.getParentId() == 0) {
					bodyName = requestMappingDTO.getKeys();
				}
				if (requestMappingDTO.getTagLevel() == 2) {
					childNames.add(requestMappingDTO.getKeys());
				}
			}
		}

		Element rootNode = document.getDocumentElement();
		NodeList firstNodeList = rootNode.getChildNodes();
		for (int i = 0; i < firstNodeList.getLength(); i++) {
			Node node = firstNodeList.item(i);
			if (StringUtils.containsIgnoreCase(node.getNodeName(), "env:Body")) {
				NodeList bodyNodeList = node.getChildNodes();
				for (int j = 0; j < bodyNodeList.getLength(); j++) {
					Node bodyNode = bodyNodeList.item(j);
					if (StringUtils.containsIgnoreCase(bodyNode.getNodeName(), "env:Fault")){
						NodeList paramNodeList = bodyNode.getChildNodes();
						for (int k = 0; k < paramNodeList.getLength(); k++) {
							Node outputNode = paramNodeList.item(k);
							if(("faultstring").equalsIgnoreCase(outputNode.getNodeName())){
								errorMessage = outputNode.getTextContent();
								status = FAILED;
							}
						}
						break;
					}
					if (StringUtils.containsIgnoreCase(bodyNode.getNodeName(), bodyName)) {
						LOG.debug("List " + bodyNode.getNodeName());
						NodeList paramNodeList = bodyNode.getChildNodes();
						for (int k = 0; k < paramNodeList.getLength(); k++) {
							Node outputNode = paramNodeList.item(k);
							for (String childName : childNames) {
								if (StringUtils.containsIgnoreCase(outputNode.getNodeName(), childName)) {
									LOG.debug("================================");
									LOG.debug(k + " - " + outputNode.getNodeName());
									NodeList dataNodeList = outputNode.getChildNodes();
									Map<String, Object> mappedData = new HashMap<>();
									if (dataNodeList.getLength() > 1) {
										for (int l = 0; l < dataNodeList.getLength(); l++) {
											Node dataNode = dataNodeList.item(l);
											LOG.debug(cutDoublePeriod(dataNode.getNodeName()) + " : " + dataNode.getTextContent());
											if (mappedField.get(dataNode.getNodeName()) == null) {
												mappedField.put(cutDoublePeriod(dataNode.getNodeName()), cutDoublePeriod(dataNode.getNodeName()));
											}
											mappedData.put(cutDoublePeriod(dataNode.getNodeName()), dataNode.getTextContent());
										}
									} else {
										mappedField.put(cutDoublePeriod(outputNode.getNodeName()), cutDoublePeriod(outputNode.getNodeName()));
										mappedData.put(cutDoublePeriod(outputNode.getNodeName()), outputNode.getTextContent());
									}
									mappedResult.add(mappedData);
								}
							}
						}
					}
				}
			}
		}

		dtoResponse.setListField(mappedField);
		dtoResponse.setListData(mappedResult);
		dtoResponse.setStatus(status);
		dtoResponse.setErrorMessage(errorMessage);
		return dtoResponse;
	}

	/**
	 * Method to substring SOAP tags.
	 * 
	 * @param input
	 * @return
	 */
	public String cutDoublePeriod(String input) {
		int index = input.indexOf(":") + 1;
		return input.substring(index);
	}
	
}
