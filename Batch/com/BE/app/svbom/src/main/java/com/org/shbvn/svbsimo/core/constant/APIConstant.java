/**
 * 
 */
package com.org.shbvn.svbsimo.core.constant;

import java.math.BigDecimal;


public class APIConstant {

	/** Common Constant */
	public static final String ALL = "all";
	public static final String DOCUMENT = "document";
	public static final String DOCTYPE = "docType";
	public static final String DATA = "data";
	public static final String COUNT = "count";
	
	public static final String RESULT = "result";
	public static final String RESULT_MSG = "resultMSG";
	public static final String FLAG = "flag";

	public static final String _START_DATE_KEY = "_startDt";
	public static final String _REF_NO_KEY = "refNo";
	public static final String _CR_AMT_KEY = "crAmt";
	public static final String _DR_AMT_KEY = "drAmt";
	public static final String _END_DATE_KEY = "_endDt";
	public static final String _TYPE_KEY = "_type";
	public static final String _STATUS_KEY = "status";
	public static final String _START_KEY = "_start";
	public static final String _NUMBER_KEY = "_number";
	public static final String _TXT_SEARCH_KEY = "_txtSearch";
	public static final String _POTIENTIAL_BLANK = "blank";
	public static final String _SORT = "sort";
	public static final String _ORDER = "order";
		
	public static final String USERNAME_KEY = "username";
	public static final String USERPROFILE_KEY = "userProfile";
	public static final String EXPIRY_DATE = "expiry-date";
	public static final String PASSWORD_KEY = "password";
	public static final String TRACKING_LOG_INSTANCE = "trackingLogInstance";

	public static final String FILE = "file";
	public static final String FILE_UPLOAD = "fileUpload";
	
	public static final String SUBMISSION_MODE_KEY = "SubmissionMode";
	public static final String SUBMISSION_LAS_KEY = "SubmissionLAS";
	
	public static final String SUPERGEN_KEY = "super_user";
	public static final String SYSADM = "sysadm";
	
	/** Common Document Constant */
	// Sharing resource file url
	public static final String URL_DIR_FILERESOURCE_SHARING = "url_dir_fileResource_sharing";

	public static final String FILE_TYPE_IMAGE_JPG = ".jpg";
	public static final String FILE_TYPE_IMAGE_PNG = ".png";
	public static final String FILE_TYPE_PDF = ".pdf";
	public static final String FILE_TYPE_JASPER = ".jasper";
	public static final String FILE_TYPE_TXT = ".txt";
	
	public static final String FILE_TYPE_JSON = ".json";
	public static final String FILE_TYPE_XML = ".xml";
	public static final String FILE_TYPE_EXCEL_OLD = ".xls";
	public static final String FILE_TYPE_EXCEL_NEW = ".xlsx";
	
	
	
	/** HTTP Description **/
	public static final String HTTP_REQUEST_BODY_STR = "HttpRequestBody";
	public static final String METHOD_NOT_ALLOWED = "Method is not allowed";
	public static final String UNSUPPORTED_MEDIA_TYPE = "Media type is unsupported";
	public static final String NOT_ACCEPTABLE = "Not Acceptable";

	public static final String GET_METHOD_STR = "GET";
	public static final String POST_METHOD_STR = "POST";
	public static final String PATCH_METHOD_STR = "PATCH";
	public static final String DELETE_METHOD_STR = "DELETE";
	public static final String OPTIONS_METHOD_STR = "OPTIONS";
	public static final String ANONYMOUS_USER = "Anonymous";

	public static final String TEXT_HTML_CHARSET_UTF8_CHARSET_TYPE = "text/html;charset=UTF-8";
	public static final String UTF_8_CHARSET_TYPE = "UTF-8";
	public static final String CONTENT_TYPE_REQUEST_HEADER = "Content-Type";

	public static final String CONTENT_TYPE_IMAGE_PNG = "image/png";
	public static final String CONTENT_TYPE_IMAGE_JPG = "image/jpeg";

	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTEXT_FILTER_PATH = "simo/api/";
	
	public static final String REQUEST_URI_STR = "requestURI";

	/** Exception Constant **/
	public static final String SERVICE_RUNTIME_EXCEPTION_ERROR = "Some error is happened";
	public static final String THE_TOKEN_IS_INVALID_ERROR = "The Token is invalid";
	public static final String THE_TOKEN_IS_INCORRECT_FORMAT_ERROR = "The Token is incorrect format";
	public static final String THE_TOKEN_IS_BLANK_ERROR = "The Token is blank";
	public static final String THE_TOKEN_UNPERMISSION_ERROR = "The Token can not execute this operation";
	public static final String NO_RECORD_FOUND_KEY = "No record found";
	public static final String THE_TOKEN_IS_EXPIRED_ERROR = "The Token is expired";
	public static final String THE_DATA_INCORRECT_FORMAT_ERROR = "The Data is incorrect format";
	public static final String THE_LOAN_INCORRECT_RMK = "Incorrect LoanNo";
	public static final String THE_LOAN_BLANK_RMK = "LoanNo is blank";

	
	
	/** SECURE Constant **/
	public static final String SYSADMIN_STR = "SYSADMIN";
	public static final String ACCESS_TOKEN_KEY = "Authorization";
	//public static final String ACCESS_TOKEN_KEY = "Authorization";
	
	public static final String PUBLIC_KEY = "public_key";
	public static final String AES_KEY = "AES_key";
	public static final String EXECUTION_TIME_KEY = "executionTime";
	public static final String TOKEN_EXPIRY_MINUTES = "Token_expiry_minutes";

	public static final String OMNI_AES_KEY = "OMNI_AES_key";
	/** Other Constant **/
	
	public static final String YES_KEY = "Y";
	public static final String NO_KEY = "N";
	public static final String NULL_KEY = "NULL";
	public static final String BLANK_VALUE = "";
	public static final String EMPTY_KEY = "EMPTY";
	public static final String SUCCESS_KEY = "SUCCESS";
	public static final String SUCCESS_RESPONSE_KEY = "200";
	public static final String ERROR_100_RESPONSE_KEY = "100";
	public static final String ERROR_KEY = "ERROR";
	public static final String COMMA = ",";
	public static final String OMSID = "ID";
	public static String _USERNAME_BATCHJOB = "BatchJob";
	public static String ACCESS_TIME ="access-time";
	
	public static final BigDecimal DEC_ZERO = new BigDecimal("0");
	/** Metadata Constant */
	public static final String _LOOKUP_CODE_KEY = "lookupCode";
	public static final String _LOOKUP_CODE_BANK_VALUE_ = "BANK_CODE";
	public static final String _LOOKUP_CODE_BANK_DISB_VALUE_ = "BANK_CODE_DISB";
	public static final String _LOOKUP_CODE_BANK_REPORT_VALUE_ = "BANK_REPORT_NM";
	public static final String _UPLOADED_FILE_KEY = "UPLOAD_FILE_STATUS";
	public static final String _BANK_STATEMENT_STATUS_KEY = "BANK_STATEMENT_TRX_STATUS";
	
	
	/** File Statement Mas Constant **/
	public static final String UPLOAD_DATE_KEY = "uploadDt";
	public static final String UPLOAD_BANKCODE_KEY = "bankCode";
	public static final String UPLOAD_BANKSTATUS_KEY = "status";
	public static final String UPLOAD_TRXTYPE_KEY = "transactionType";
	public static final String VALUE_DATE_KEY = "valueDt";
	public static final String TRX_DATE_KEY = "trxDt";
	public static final String FILE_NAME = "FILE_NAME";
	public static final String FILE_ID = "fileId";
	
	
	/** nfs config **/
	public static final String nfs_client_host = "nfs_client_host";
	public static final String nfs_client_username = "nfs_client_username";
	public static final String nfs_client_password = "nfs_client_password";
	public static final String nfs_client_remotedirectory = "nfs_client_remotedirectory";
	public static final String nfs_client_remotebackupdirectory = "nfs_client_remotebackupdirectory";
	public static final String DATA_LIMIT_CONDTION_SEARCHING_IN = "search.limit.condition.in";
    public static final String AUTH_MODE = "AUTH";
	/** FTP config **/
	public static final String ftp_server_url = "ftp.server.url";
	public static final String ftp_server_port = "ftp.server.port";
	public static final String ftp_username = "ftp.username";
	public static final String ftp_password = "ftp.password";
	public static final String ftp_remote_directory = "ftp.remote.directory";
	public static final String ftp_local_download_directory = "ftp.local.download.directory";
	public static final String ftp_local_log_directory = "ftp.local.log.directory";


	public static final String FILE_STATEMENT_STATUS_UPLOAD = "UPLD";
	public static final String FILE_STATEMENT_STATUS_PROCESS = "PROS";
	public static final String FILE_STATEMENT_STATUS_COMPLETE = "COMP";
	public static final String FILE_STATEMENT_HIS_PROCESS_STATUS = "C";
	public static final String FILE_HIS_DETAIL_STATUS_COMPLETE = "C";
	public static final String FILE_STATEMENT_STATUS_ERROR = "ERRO";
	public static final String FILE_STATEMENT_STATUS_DELETE = "DELE";
	public static final String FILE_STATEMENT_STATUS_REJECT = "REJT";
	public static final String FILE_STATEMENT_STATUS_APPROVE = "APPR";
    public static final String PATH_SCAN_SIMO_FTP_FOLDER = "PATH_SCAN_SIMO_FTP_FOLDER";
	public static final String PATH_SCAN_SIMO_PROCESS_FOLDER = "PATH_SCAN_SIMO_PROCESS_FOLDER";
	public static final String PATH_SCAN_SIMO_DONE_FOLDER = "PATH_SCAN_SIMO_DONE_FOLDER";


	public static final String TEMPLATE_CODE_INST_API1_6_TTTK = "INST_API1_6_TKTT";
	public static final String TEMPLATE_CODE_INST_API1_9_TTTK = "INST_API1_9_TKTT";
	public static final String TEMPLATE_CODE_INST_API1_7_NGGL = "INST_API1_7_NGGL";
	public static final String TEMPLATE_CODE_INST_API1_8_NGGL = "INST_API1_8_NGGL";
	public static final String TEMPLATE_CODE_INST_API1_22_WDR = "INST_API1_22_WDR";

    public static final String LOOKUP_CODE_FILE_TEMPLATE_CODE = "FILE_TEMPLATE_CODE";


	public static final String SVB_ACCESS_TOKEN_TTL = "svb.access.token.ttl";
	public static final String SVB_ACCESS_TOKEN_URL = "svb.access.token.url";
	public static final String SVB_ACCESS_TOKEN_USERNAME = "svb.access.token.username";
	public static final String SVB_ACCESS_TOKEN_PASSWORD = "svb.access.token.password";
	public static final String SVB_CONSUMER_KEY = "svb.access.consumer.key";
	public static final String SVB_CONSUMER_SECRET = "svb.access.consumer.secret";
	public static final String SVB_ACCESS_TOKEN_METHOD = "svb.access.token.method";
	public static final String SVB_MAX_DATA_LIMIT = "svb.data.limit";
    public static final String SVB_UPLOAD_URL = "svb.upload.url";
	public static final String SVB_BASE_URL = "svb.base.url";
	public static final String SVB_UPLOAD_URL_INST_API1_6_TKTT = "svb.upload.url.INST_API1_6_TKTT" ; 
	public static final String SVB_UPLOAD_URL_INST_API1_7_NGGL = "svb.upload.url.INST_API1_7_NGGL" ; 
	public static final String SVB_UPLOAD_URL_INST_API1_8_NGGL = "svb.upload.url.INST_API1_8_NGGL" ; 
	public static final String SVB_UPLOAD_URL_INST_API1_9_TKTT = "svb.upload.url.INST_API1_9_TKTT" ; 
	public static final String SVB_UPLOAD_URL_INST_API1_22_WDR = "svb.upload.url.INST_API1_22_WDR" ;
    public static final String SVB_UPLOAD_METHOD = "svb.upload.method";
	public static final String SVB_SECURITY_WHITELIST = "security.auth.whitelist" ;

}
