package com.org.shbvn.svbsimo.core.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.org.shbvn.svbsimo.core.constant.APIConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurUtils {
    public static Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().serializeNulls().create();
    protected static final Logger logger = LoggerFactory.getLogger(SecurUtils.class);

	public static void main(String[] args) {
		String password = "123456";
		String encrytedPassword = encrytePassword(password);

		System.out.printf("Encryted Password: %s\n", encrytedPassword);
	}

    /**
     * Checks and validates the JWT access token using the provided key.
     * Returns the payload if valid, otherwise returns null.
     *
     * @param access_token JWT token string
     * @param keyStr       Secret key for verification
     * @return Payload string if valid, null otherwise
     */
    public static String checkSecureTheToken(String access_token, String keyStr) {

		logger.info("{} : {}", APIConstant.ACCESS_TOKEN_KEY, access_token);

		if (CommonUtils.isBlank(access_token)) {
			return null;
		}
		// Check the token is valid or not.
		String bodyPayload = "";
		try {
			// OK, we can trust this JWT
			Jws<Claims> jwsClaims = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(keyStr.getBytes())).build().parseSignedClaims(access_token);
			if (jwsClaims == null) {
				return null;
			}
			String[] listStr = access_token.split("\\.");	
			bodyPayload = base64UrlDecode(listStr[1]);
			String signature = listStr[2];
			String compactJws = Jwts.builder().content(bodyPayload).signWith(Keys.hmacShaKeyFor(keyStr.getBytes()))
					.compact();
			if (!CommonUtils.contains(compactJws, signature)) {
				return null;
			}
		} catch (SecurityException e) {
			// don't trust the JWT!
			logger.info(e.getMessage());
			return null;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		logger.info("bodyPayload : {}", bodyPayload);
		if (CommonUtils.isBlank(bodyPayload)) {
			return null;
		}

		return bodyPayload;
	}

    /**
     * Generates a JWT token containing the username and expiry information.
     *
     * @param keyStr   Secret key for signing
     * @param userName Username to include in the token
     * @return Signed JWT token string
     */
    public static String getCryptUser(String keyStr, String userName) {
		try {
			JsonObject json = new JsonObject();
			json.addProperty(APIConstant.USERNAME_KEY, userName);
			json.addProperty(APIConstant.EXPIRY_DATE, APIConstant.YES_KEY);
			json.addProperty(APIConstant.ACCESS_TIME, System.currentTimeMillis());
			
			String compactJws = Jwts.builder().content(gson.toJson(json))
					.signWith(Keys.hmacShaKeyFor(keyStr.trim().getBytes())).compact();
			logger.info("compactJws : {}", compactJws);
			return compactJws;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

    /**
     * Encrypts a password using BCrypt hashing algorithm.
     *
     * @param password Plain text password
     * @return BCrypt hashed password
     */
    public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
    /**
     * Verifies a plain password against a BCrypt hashed password.
     *
     * @param password         Plain text password
     * @param encrytedPassword BCrypt hashed password
     * @return true if matches, false otherwise
     */
	public static boolean decrytePassword(String password, String encrytedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(password, encrytedPassword);
	}
	
    /**
     * Encodes a string to Base64.
     *
     * @param input Input string
     * @return Base64 encoded string
     */
	public static String base64UrlEncode(String input) {
		String result = null;
		byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
		result = new String(encodedBytes);
		return result;
	}

    /**
     * Decodes a Base64 encoded string.
     *
     * @param input Base64 encoded string
     * @return Decoded string
     */
	public static String base64UrlDecode(String input) {
		String result = null;
		byte[] decodedBytes = Base64.getDecoder().decode(input);
		result = new String(decodedBytes);
		return result;
	}

    /**
     * Encrypts plain text using AES-GCM with the provided secret key.
     *
     * @param plainText Plain text to encrypt
     * @param secretKey Secret key in hex string format
     * @return Base64 encoded encrypted string
     */
	public static String encryptPassword(String plainText, String secretKey) {
		try {
			// Get the key bytes (assuming secretKey is a hex string)
			byte[] keyBytes = hexStringToByteArray(secretKey);
			SecretKey key = new SecretKeySpec(keyBytes, "AES");
			
			// Create a nonce (IV)
			byte[] nonce = new byte[12];
			SecureRandom random = new SecureRandom();
			random.nextBytes(nonce);
			
			// Create the cipher instance and initialize it
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			GCMParameterSpec spec = new GCMParameterSpec(128, nonce);
			cipher.init(Cipher.ENCRYPT_MODE, key, spec);
			
			// Encrypt the data
			byte[] encryptedData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
			
			// Combine nonce and encrypted data
			ByteBuffer byteBuffer = ByteBuffer.allocate(nonce.length + encryptedData.length);
			byteBuffer.put(nonce);
			byteBuffer.put(encryptedData);
			
			// Convert to Base64 for storage or transmission
			return Base64.getEncoder().encodeToString(byteBuffer.array());
		} catch (Exception e) {
			logger.error("Error encrypting password", e);
			return null;
		}
	}

    /**
     * Decrypts an AES-GCM encrypted string using the provided secret key.
     *
     * @param encryptedText Base64 encoded encrypted string
     * @param secretKey     Secret key in hex string format
     * @return Decrypted plain text string
     */
	public static String decryptPassword(String encryptedText, String secretKey) {
		try {
			// Get the key bytes (assuming secretKey is a hex string)
			byte[] keyBytes = hexStringToByteArray(secretKey);
			SecretKey key = new SecretKeySpec(keyBytes, "AES");
			
			// Decode the Base64 string
			byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
			
			// Extract nonce and encrypted data
			ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedBytes);
			byte[] nonce = new byte[12];
			byteBuffer.get(nonce);
			
			byte[] encryptedData = new byte[byteBuffer.remaining()];
			byteBuffer.get(encryptedData);
			
			// Create the cipher instance and initialize it
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			GCMParameterSpec spec = new GCMParameterSpec(128, nonce);
			cipher.init(Cipher.DECRYPT_MODE, key, spec);
			
			// Decrypt the data
			byte[] decryptedData = cipher.doFinal(encryptedData);
			
			// Convert to string and return
			return new String(decryptedData, StandardCharsets.UTF_8);
		} catch (Exception e) {
			logger.error("Error decrypting password", e);
			return null;
		}
	}

    /**
     * Converts a hex string to a byte array.
     *
     * @param s Hex string
     * @return Byte array
     */
	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

    /**
     * Generates a random 256-bit (32 bytes) secret key and returns it as a hex string.
     *
     * @return Hex string representation of the secret key
     */
	public static String generateSecretKey() {
		try {
			SecureRandom random = new SecureRandom();
			byte[] keyBytes = new byte[32]; // 256 bits
			random.nextBytes(keyBytes);
			
			// Convert to hex string for storage
			StringBuilder sb = new StringBuilder();
			for (byte b : keyBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("Error generating secret key", e);
			return null;
		}
	}
}
