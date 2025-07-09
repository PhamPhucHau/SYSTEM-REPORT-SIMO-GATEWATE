package com.org.shbvn.svbsimo.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.repository.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AuthApiController extends BaseController{
    
    public AuthApiController() {
        super();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/v1/auth/generateToken", 
        produces = {"application/json;charset=utf-8", "application/xml;charset=utf-8"}, 
        method=RequestMethod.POST)
    public ResponseEntity<Object>  generateToken(@RequestBody String document) throws BaseException {

        Map<String, Object> inputParams = new HashMap<>();
        inputParams = (Map<String, Object>) CommonUtils.toPojo(document, Map.class);

        UserInfo user = getProcessManagerService().getAuthService().generateUserToken(inputParams);


        return triggerSuccessOutPut(user, String.class, "MSG_006");
    }

    @RequestMapping(value="/v1/auth/generateHashingValue", 
        produces = {"application/json;charset=utf-8", "application/xml;charset=utf-8"}, 
        method=RequestMethod.GET)
    public ResponseEntity<Object>  generateHashingValue(@RequestParam(name = "sourceStr", required = true) String sourceStr) throws BaseException {
        String result = getProcessManagerService().getAuthService().generateHashingValue(sourceStr);
        return triggerSuccessOutPut(result, String.class, "MSG_006");
    }
// Register new User 
@RequestMapping(
    value = "/v1/auth/registerUser",
    produces = {"application/json;charset=utf-8", "application/xml;charset=utf-8"},
    method = RequestMethod.POST
)
public ResponseEntity<Object> registerUser(@RequestBody String document) throws BaseException {
        // Parse JSON body thành Map
        @SuppressWarnings("unchecked")
        Map<String, Object> inputParams = (Map<String, Object>) CommonUtils.toPojo(document, Map.class);

        // Gọi service tạo user mới
        UserInfo user = getProcessManagerService().getAuthService().createUserProfile(inputParams);

        // Trả về kết quả thành công (thường chứa token, id, v.v.)
        return triggerSuccessOutPut(user, UserInfo.class, "MSG_006"); 
}
}
