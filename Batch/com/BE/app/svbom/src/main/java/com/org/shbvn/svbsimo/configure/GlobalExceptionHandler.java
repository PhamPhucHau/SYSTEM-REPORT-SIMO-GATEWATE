/**
 * 
 */
package com.org.shbvn.svbsimo.configure;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.constant.ResponseOutPut;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.ServiceInvalidAgurmentException;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.exception.UnauthorizedException;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;

import jakarta.persistence.PersistenceException;

@EnableWebMvc
@ControllerAdvice()
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private Environment env;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();

	// 400
	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
		logger.error("ops!", ex);
		ResponseOutPut output = new ResponseOutPut("", ex.getLocalizedMessage(), false, status.value());
		return handleExceptionInternal(ex, output, headers, status, request);
	}

	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
		logger.error("ops!", ex);
		final String error = "Message Not Readable is missing";
		ResponseOutPut output = new ResponseOutPut("", error, false, status.value());
		return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
	}

	/* @Override
	@ResponseBody
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.error("ops!", ex);
		ResponseOutPut output = new ResponseOutPut("", ex.getLocalizedMessage(), false, status.value());
		return handleExceptionInternal(ex, output, headers, status, request);
	} */

	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatusCode status, final WebRequest request) {
		logger.error("ops!", ex);
		//
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		ResponseOutPut output = new ResponseOutPut("", error, false, status.value());
		return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
	}

	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
		logger.error("ops!", ex);
		//
		final String error = ex.getRequestPartName() + " part is missing";
		ResponseOutPut output = new ResponseOutPut("", error, false, status.value());
		return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
	}

	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatusCode status,
			final WebRequest request) {
		logger.error("ops!", ex);
		//
		final String error = ex.getParameterName() + " parameter is missing";

		ResponseOutPut output = new ResponseOutPut("", error, false, status.value());
		return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
	}

	// 404
	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		ResponseOutPut output = new ResponseOutPut("", error, false, status.value());
		return new ResponseEntity<Object>(output, HttpStatus.NOT_FOUND);
	}

	// 415
	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
		logger.error("ops!", ex);
		ResponseOutPut output = new ResponseOutPut("", APIConstant.UNSUPPORTED_MEDIA_TYPE, false, status.value());
		return new ResponseEntity<Object>(output, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	// 405
	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatusCode status,
			final WebRequest request) {
		logger.error("ops!", ex);
		ResponseOutPut output = new ResponseOutPut("", APIConstant.UNSUPPORTED_MEDIA_TYPE, false, status.value());
		return new ResponseEntity<Object>(output, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	// 406
	@Override
	@ResponseBody
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		logger.error("ops!", ex);
		ResponseOutPut output = new ResponseOutPut("", APIConstant.NOT_ACCEPTABLE, false, status.value());
		return new ResponseEntity<Object>(output, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler({ MultipartException.class })
	@ResponseBody
	public ResponseEntity<Object> handleMultipartException(MultipartException e) {
		logger.error("ops!", e);
		String errorMessage = env.getProperty("MSG_120");// e.getRootCause().getMessage();
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	@ResponseBody
	public ResponseEntity<Object> handleOracleSQLException(DataIntegrityViolationException e) {
		logger.error("ops!", e);
		String errorMessage = env.getProperty("MSG_002");// e.getRootCause().getMessage();
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(output, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ PersistenceException.class })
	@ResponseBody
	public ResponseEntity<Object> handleOracleSQLException(PersistenceException e) {
		logger.error("ops!", e);
		String errorMessage = env.getProperty("MSG_002");
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<Object>(output, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ UnexpectedRollbackException.class })
	@ResponseBody
	public ResponseEntity<Object> handleUnexpectedRollbackException(UnexpectedRollbackException e) {
		logger.error("ops!", e);
		String errorMessage = env.getProperty("MSG_002");
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<Object>(output, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseBody
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
		logger.error("ops!", e);
		String errorMessage = env.getProperty("MSG_002");
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<Object>(output, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ IndexOutOfBoundsException.class })
	@ResponseBody
	public ResponseEntity<Object> handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
		logger.error("ops!", e);
		String errorMessage = env.getProperty("MSG_002");
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<Object>(output, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ NullPointerException.class })
	@ResponseBody
	public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
		logger.error("ops!", e);
		String errorMessage = env.getProperty("MSG_002");
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<Object>(output, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ NumberFormatException.class })
	@ResponseBody
	public ResponseEntity<Object> handleNullPointerException(NumberFormatException e) {
		logger.error("ops!", e);
		String errorMessage = env.getProperty("MSG_002");
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<Object>(output, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ BaseException.class })
	@ResponseBody
	public ResponseEntity<Object> handleOmnichannelException(BaseException e) {
		logger.error("ops!", e);
		if (e instanceof ServiceRuntimeException) {
			return buildErrorResponseOmni((ServiceRuntimeException) e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (e instanceof ServiceInvalidAgurmentException) {
			return buildErrorInvalidAgurmentResponseOmni((ServiceInvalidAgurmentException) e,
					HttpStatus.BAD_REQUEST);
		}
		if (e instanceof UnauthorizedException) {
			return buildErrorResponseOmni((UnauthorizedException) e, HttpStatus.UNAUTHORIZED);
		}

		return buildErrorResponseOmni(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
 
	private ResponseEntity<Object> buildErrorInvalidAgurmentResponseOmni(ServiceInvalidAgurmentException ex,
			HttpStatus status) {
		ResponseOutPut output;
		List<?> listItemError = ex.getListItemError();
		if (listItemError == null || listItemError.isEmpty() || listItemError.size() == 1) {
			String message = (listItemError == null || listItemError.isEmpty()) == true ? ex.getMessage()
					: gson.fromJson(gson.toJson(listItemError.get(0)), HashMap.class).get("ERROR_MESSAGE").toString();
			HashMap<String, Object> mapError = buildErrorInvalidAgurmentResponseOmniSingleItem(message);
			String messageError = "";
			if (mapError.isEmpty()) {
				output = new ResponseOutPut(mapError.isEmpty() ? "" : mapError, ex.getMessage(), false, status.value());
				return new ResponseEntity<Object>(output, status);
			}
			for (Entry<String, Object> entry : mapError.entrySet()) {
				if (entry == null || entry.getValue() == null
						|| APIConstant.NULL_KEY.equalsIgnoreCase(entry.getValue().toString()))
					continue;

				messageError = CommonUtils.isBlank(messageError) ? entry.getValue() + ";"
						: messageError + entry.getValue() + ";";
			}
			output = new ResponseOutPut(mapError.isEmpty() ? "" : mapError, messageError, false, status.value());
			return new ResponseEntity<Object>(output, status);
		}
		ArrayList<HashMap<String, Object>> listErrorRs = new ArrayList<>();
		for (int j = 0; j < listItemError.size(); j++) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = gson.fromJson(gson.toJson(listItemError.get(j)), HashMap.class);

			if (map.get("ERROR_MESSAGE") != null && !CommonUtils.isBlank(map.get("ERROR_MESSAGE").toString())) {
				HashMap<String, Object> mapAttributeError = buildErrorInvalidAgurmentResponseOmniSingleItem(
						map.get("ERROR_MESSAGE").toString());

				DecimalFormat df = new DecimalFormat("###.#");
				Object number;
				if ((map.get("numberNo") == null || CommonUtils.isBlank(map.get("numberNo").toString())) == true) {
					number = (j + 1);
				} else {
					try {
						number = df.format(Float.parseFloat(map.get("numberNo").toString()));
					} catch (Exception e) {
						number = map.get("numberNo").toString();
					}
				}
				mapAttributeError.put("numberNo", number);
				listErrorRs.add(mapAttributeError);
			}
		}
		JsonArray dataStr = gson.fromJson(gson.toJson(listErrorRs), JsonArray.class);
		JsonObject json = new JsonObject();
		json.add("data", dataStr);
		output = new ResponseOutPut(json, "Invalid Agurment", false, status.value());
		return new ResponseEntity<Object>(gson.toJson(output), status);
	}

	private HashMap<String, Object> buildErrorInvalidAgurmentResponseOmniSingleItem(String message) {
		HashMap<String, Object> map = new HashMap<>();
		String errorMessage = "";

		if (CommonUtils.contains(message, ";")) {
			String[] errors = CommonUtils.split(message, ";");
			logger.info(errors.toString());
			for (String error : errors) {
				if (CommonUtils.isBlank(error)) {
					continue;
				}
				logger.info(error);
				String keyWord = "";
				String messageStr = "";
				if (CommonUtils.contains(error, ",")) {
					String[] splitError = CommonUtils.split(error, ",");
					keyWord = splitError[0].toString().trim();
					messageStr = splitError[1].toString().trim();
				} else {
					messageStr = error;
				}
				errorMessage = errorMessage + ";" + messageStr;
				if (!CommonUtils.isBlank(keyWord)) {
					if (map.containsKey(keyWord)) {
						String value = map.get(keyWord).toString();
						messageStr = value + ";" + messageStr;
						map.remove(keyWord);
					}
					map.put(keyWord, messageStr);
				}
			}
		} else {
			return new HashMap<>();
		}

		return map;
	} 

	private ResponseEntity<Object> buildErrorResponseOmni(Throwable throwable, HttpStatus status) {
		String errorMessage = throwable.getMessage();
		ResponseOutPut output = new ResponseOutPut("", errorMessage, false, status.value());

		return new ResponseEntity<Object>(output, status);
	}
}
