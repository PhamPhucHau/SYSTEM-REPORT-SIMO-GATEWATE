package com.org.shbvn.svbsimo.core.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.model.DailyCommonTemplate;

public class CommonUtils {

    @Autowired
	private transient static Environment env;
    protected static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    
    public static Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();

	private static RestTemplate restTemplate;


	public static boolean isNullOrEmpty(CharSequence str) {

		if(str == null) return true;

		if(str.length() == 0) return true;

		if(str.equals("")) return true;

		return false;
	}

	public static boolean isNullOrEmpty(Collection<Object> coll) {

		if(coll == null) return true;

		if(coll.size() == 0) return true;

		return false;
	}

	public static boolean isNullOrEmpty(String[] colls) {

		if(colls == null) return true;

		if(colls.length == 0) return true;

		return false;
	}

	public static String toJson(final Object object) throws ServiceRuntimeException {
		try {
			return gson.toJson(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}

	public static Object toPojo(final String jsonString, final Class<?> clazz) throws ServiceRuntimeException {
		try {
			return gson.fromJson(jsonString, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}
	
	public static <T> List<T> toListPojo(final String jsonArray, final Class<T> clazz) throws ServiceRuntimeException {
		try {
			return gson.fromJson(jsonArray, TypeToken.getParameterized(List.class, clazz).getType());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}

	/**
	 * Should use #clonePojo()(Meaningful and easier to understand) instead of this one
	 * @param json
	 * @param clazz
	 * @return Object
	 * @throws ServiceRuntimeException
	 */
	public static Object toPojo(final Object json, final Class<?> clazz) throws ServiceRuntimeException {
		try {
			return gson.fromJson(toJson(json), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}
	
	/**
	 * This util use json to do a deep clone of an object
	 * With other object different than entity(prevent hibernate automatic persist that instante?) 
	 * or just want an shallow clone should implement interface Cloneable instead of using this one
	 * @param objectToClone
	 * @return the cloned object
	 * @throws ServiceRuntimeException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clonePojo(final T objectToClone) throws ServiceRuntimeException {
		try {
			return (T) gson.fromJson(toJson(objectToClone), objectToClone.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_004"), e.getCause());
		}
	}

    /**
	 * This function help set a value into a field of an object
	 * Support multiple level on an Object (separate by an ".")
	 * Important note:
	 * 	- if a field is a List if will add value inside that List if the type is match
	 *  - it also check the super class, mean if the class extend another class it is automatically cared
	 *  - swallow exception so best practice is check the boolean response
	 * @param object
	 * @param fieldName
	 * @param fieldValue
	 * @return boolean
	 */
	public static <T,V> Boolean setPropertyIntoObject(T object, V fieldValue, String fieldName) {
		return setPropertyIntoObjectHandler(object, fieldValue, fieldName.split("\\."));
	}
	
	
	private static <T,V> Boolean setPropertyIntoObjectHandler(T object, V fieldValue, String... fieldName) {
		if(object == null || fieldValue == null || isNullOrEmpty(fieldName)) {
			return false;
		}
		if(fieldName.length == 1) {
			return setPropertyIntoObjectHandler(object, fieldValue, fieldName[0]);
		} else {
			try {
				Field field = getField(object, fieldName[0]);
				System.out.println("field: " + field);
				if(field == null) return false;
				Object subObject = Optional.ofNullable(field.get(object)).orElse(Class.forName(field.getType().getName()).getDeclaredConstructor().newInstance());
				System.out.println("subObject: " + subObject.toString());
				boolean isSucess = setPropertyIntoObjectHandler(subObject, fieldValue, Arrays.copyOfRange(fieldName, 1, fieldName.length));
				System.out.println("isSucess: " + isSucess + ", fieldName: " + Arrays.toString(fieldName) + ":"+object.toString());
				if (isSucess) {
					field.set(object, subObject);
				}
				return isSucess;
			} catch (Exception e) {
				logger.error("Error setting property into object: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T,V> Boolean setPropertyIntoObjectHandler(T object, V fieldValue, String fieldName) {
		if(object == null || fieldValue == null || isBlank(fieldName)) {
			return false;
		}
		try {
			Field field = getField(object, fieldName);
			if (field == null)
				return false;

			if (field.getType().equals(fieldValue.getClass())) {
				field.set(object, fieldValue);
				return true;
			}
			boolean islistWithSameType = field.getType().equals(List.class)
					&& ((Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0])
							.equals(fieldValue.getClass());
			if (islistWithSameType) {
				List<V> currentValue = Optional.ofNullable((List<V>) field.get(object)).orElse(new ArrayList<>());
				currentValue.add(fieldValue);
				field.set(object, currentValue);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static <T> Field getField(T object, String fieldName) {
		Class clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		String message = String.format("Property: \"%s\" not available on %s", fieldName, object.getClass().getName());
		logger.error(message);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <T> List<T> joinList(final List<? extends T>...lists){
		if(lists.length == 1) {
			return (List<T>) Optional.ofNullable(lists[0]).orElse(new ArrayList<>());
		}
		final ArrayList<T> result = new ArrayList<>();
		result.addAll(Optional.ofNullable(lists[0]).orElse(new ArrayList<>()));
		result.addAll(joinList(Arrays.copyOfRange(lists, 1, lists.length)));
		return result;
	}

    public static boolean isNumber(String strNum) {
		if (strNum == null) {
	        return false; 
	    }
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	    return pattern.matcher(strNum).matches();
	}
	public static boolean isCurrencyAmt(String strNum) {
		if (strNum == null) {
			return false; 
		}
		Pattern pattern = Pattern.compile("^[\\$|-]?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$");
		return pattern.matcher(strNum).matches();
	}

    public static boolean isBlank(String input){
        return input == null || input.length() == 0;
    }

	public static boolean contains(String sourceStr, String regex) {
		return sourceStr.contains(regex);
	}

	public static String[] split(String sourceStr, String regex) {
		return sourceStr.split(regex);
	}

	public static Collection<File> getFilesInFolder(String dirFolder) {
		List<File> result = new ArrayList<>();
		String[] types = {"xlsx", "xls", "dat", "csv", "TXT"}; // Add new file types 
		
		File directory = new File(dirFolder);
		if (!directory.exists() || !directory.isDirectory()) {
			logger.info("Directory not found: " + dirFolder);
			return result;
		}
		
		findFiles(directory, types, true, result);
		return result;
	}

	public static void moveFileToDirectory(File srcFile, File desFile) throws ServiceRuntimeException {
		try {
			// Create destination directory if it doesn't exist
			if (!desFile.exists()) {
				desFile.mkdirs();
			}
			// Move the file to the destination directory
			File destFile = new File(desFile, srcFile.getName());
			java.nio.file.Files.move(
				srcFile.toPath(), 
				destFile.toPath(), 
				java.nio.file.StandardCopyOption.REPLACE_EXISTING
			);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_002"), e.getCause());
		}
	}
	
	private static void findFiles(File directory, String[] extensions, boolean recursive, List<File> result) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory() && recursive) {
					findFiles(file, extensions, recursive, result);
				} else if (file.isFile()) {
					String name = file.getName().toLowerCase();
					for (String extension : extensions) {
						if (name.endsWith("." + extension.toLowerCase())) {
							result.add(file);
							break;
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Invokes a REST service using RestTemplate
	 * 
	 * @param <T> The expected return type
	 * @param contextUrl The URL of the REST service
	 * @param httpMethodStr The HTTP method (GET, POST, PUT, DELETE, etc.)
	 * @param token The authentication token (can be null or empty)
	 * @param body The request body object (will be converted to JSON)
	 * @param responseType The class of the expected response
	 * @return The response object of type T, or null if the request fails
	 * @throws ServiceRuntimeException If there's an error processing the request
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invokeRestTemplateService(String contextUrl, String httpMethodStr
			, String token, Map<String, String> customHeaders, Object body, Class<T> responseType, boolean isFormEncoded) throws ServiceRuntimeException{
		
		logger.info("***********Start invokeRestTemplateService Raw**********");
		HttpHeaders httpHeaders = new HttpHeaders();
		//Set user profile for HTTPHeader
		if(!isBlank(token)) {
			httpHeaders.set(APIConstant.ACCESS_TOKEN_KEY, "Bearer "+ token);
		}
		if(isFormEncoded == true)
		{
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		}
		else
		{
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		}
// Set dynamic custom headers
		if (customHeaders != null) {
			for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
				if (!isBlank(entry.getKey()) && !isBlank(entry.getValue())) {
					httpHeaders.set(entry.getKey(), entry.getValue());
				}
			}
		}
		T rs = null;
		
		HttpMethod httpMethod = HttpMethod.valueOf(httpMethodStr.toUpperCase());
		
		ResponseEntity<T> response = null;
		HttpStatusCode statusCode = null;
		long startTime = System.currentTimeMillis();
		logger.info("URL : " + contextUrl);
		logger.info("Token : " + token);
		logger.info("Method: " + httpMethodStr);
		logger.info("Header: " + httpHeaders.toString());
		logger.info("DataSet : " + toJson(body));
		
		// successful (2xx)
		try{
			if(restTemplate == null){
				restTemplate = new RestTemplate(new org.springframework.http.client.SimpleClientHttpRequestFactory());
			}
			        HttpEntity<?> request;
        if (isFormEncoded && body instanceof Map) {
            LinkedMultiValueMap <String, String> form = new LinkedMultiValueMap<>();
            ((Map<String, String>) body).forEach(form::add);
            request = new HttpEntity<>(form, httpHeaders);
        } else {
            request = new HttpEntity<>(body, httpHeaders);
        }
			response = restTemplate.exchange(contextUrl, httpMethod, request, responseType);

			rs = response.getBody();
			statusCode = response.getStatusCode();
		}
		// client error (4xx)
		catch(HttpClientErrorException e){
			statusCode = e.getStatusCode();
			logger.info("HTTP Status Code:" + statusCode);
			logger.info("Response Body:" + e.getResponseBodyAsString());
			rs = (T) toPojo(e.getResponseBodyAsString(), responseType);
		}
		// server error (5xx)
		catch(HttpServerErrorException e){
			statusCode = e.getStatusCode();
			logger.info("HTTP Status Code:" + statusCode);
			logger.info("Response Body:" + e.getResponseBodyAsString());
			rs = (T) toPojo(e.getResponseBodyAsString(), responseType);
		}
		//Bad request or connection timeout
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		finally {
			long endTime = System.currentTimeMillis();
			long time = endTime - startTime;
			logger.info("Response : " + rs);
			logger.info("Time Duration: " + time);
			logger.info("***********End invokeRestTemplateService Raw**********");
		}
		return rs;
	}
	/**
	 * Invokes a RESTful API endpoint using Spring's RestTemplate.
	 * This utility method supports dynamic HTTP methods (GET, POST, etc.), optional token-based
	 * authentication, custom headers, and automatic JSON (de)serialization of request/response bodies.
	 *
	 * <p>Usage scenarios:
	 * <ul>
	 *   <li>Calling REST APIs that accept JSON payloads</li>
	 *   <li>Injecting Bearer tokens or custom headers for secured endpoints</li>
	 *   <li>Receiving strongly typed response objects</li>
	 * </ul>
	 *
	 * @param <T>           The type of the response body expected from the API
	 * @param contextUrl    The full URL of the target REST API
	 * @param httpMethodStr The HTTP method to use (e.g., "GET", "POST", "PUT", "DELETE")
	 * @param token         Optional bearer token for authentication; if provided, it will be added as an "Authorization" header
	 * @param customHeaders Optional map of custom headers to include in the request
	 * @param body          The request payload to be sent (serialized as JSON), can be null for GET/DELETE
	 * @param responseType  The class of the expected response type
	 * @return              The response body converted into the specified type {@code T}; or {@code null} if the request fails
	 * @throws ServiceRuntimeException if a serialization or HTTP error occurs during the process
	 */

	@SuppressWarnings("unchecked")
	public static <T> T invokeTokenRequestService(
        String tokenUrl,
        String consumerKey,
        String consumerSecret,
        String username,
        String password,
        Class<T> responseType


		
) throws ServiceRuntimeException {
    try {
        logger.info("=== Start Token Request ===");

        // 1. Tạo RestTemplate nếu cần
        if (restTemplate == null) {
            restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        }

        // 2. Header: Basic Authorization
        String authString = consumerKey + ":" + consumerSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedAuth);

        // 3. Body dạng form
        String bodyString = "grant_type=password&username=" + username + "&password=" + password;
        HttpEntity<String> request = new HttpEntity<>(bodyString, headers);

        // 4. Gọi API
        ResponseEntity<T> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, responseType);

        logger.info("=== Token Response: " + response.getBody());
        return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
        logger.error("Token request failed: " + e.getResponseBodyAsString());
        throw new ServiceRuntimeException("Token API Error", e);
    } catch (Exception ex) {
        logger.error("Token request exception", ex);
        throw new ServiceRuntimeException("Unknown error while getting token", ex);
    }
}

	/**
	 * Gets all fields declared in the class hierarchy of the given object
	 * This includes fields from all superclasses
	 * 
	 * @param object The object to get fields from
	 * @return List of all fields in the class hierarchy
	 */
	public static List<Field> getAllDeclaredFields(Object object) {
		List<Field> fields = new ArrayList<>();
		Class<?> clazz = object.getClass();
		
		// Traverse the class hierarchy
		while (clazz != null && clazz != Object.class) {
			// Add all declared fields from current class
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			
			// Move up to the superclass
			clazz = clazz.getSuperclass();
		}
		
		// Make all fields accessible
		for (Field field : fields) {
			field.setAccessible(true);
		}
		
		return fields;
	}

	/**
	 * Gets all declared fields for a given class and its superclasses
	 * 
	 * @param clazz The class to get fields from
	 * @return List of all fields in the class hierarchy
	 */
	public static List<Field> getAllDeclaredFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<>();
		List<Class<?>> classHierarchy = new ArrayList<>();

		// Build class hierarchy from top (superclass) to bottom (clazz)
		while (clazz != null && clazz != Object.class) {
			classHierarchy.add(0, clazz); // insert at the beginning
			clazz = clazz.getSuperclass();
		}

		// Add fields from superclass first
		for (Class<?> c : classHierarchy) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}

		// Make all fields accessible
		for (Field field : fields) {
			field.setAccessible(true);
		}

		return fields;
	}
	/**
 * This method maps Java field names to their corresponding @JsonProperty values if present.
 * If no @JsonProperty is defined, the field name is returned.
 *
 * @param clazz The class to inspect
 * @return A map of Java field name to JSON property name
 */
public static <T extends DailyCommonTemplate> String getJsonPropertyMappings(T bankStatement) throws ServiceRuntimeException {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Optional: format đẹp
        return objectMapper.writeValueAsString(bankStatement);
    } catch (Exception e) {
        throw new ServiceRuntimeException("Failed to serialize with JsonProperty", e);
    }
}
}
