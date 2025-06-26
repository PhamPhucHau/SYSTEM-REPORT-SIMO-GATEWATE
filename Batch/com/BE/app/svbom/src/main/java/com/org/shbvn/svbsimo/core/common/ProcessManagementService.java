package com.org.shbvn.svbsimo.core.common;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.service.AdminApiService;
import com.org.shbvn.svbsimo.service.AuthService;
import com.org.shbvn.svbsimo.service.BankTemplateAccountInfo16Process;
import com.org.shbvn.svbsimo.service.SVBApiService;

@Service("processManagerService")
public class ProcessManagementService {
    
    private AuthService authService;
    private AdminApiService adminApiService;
    private BankTemplateAccountInfo16Process bankTemplateAccountInfo16Process;
    private SVBApiService svbApiService;

    public AuthService getAuthService() {
        return authService;
    }
    @Autowired
    public void setAuthService(@Qualifier("authService") AuthService authService) {
        this.authService = authService;
    }

    public AdminApiService getAdminApiService() {
        return adminApiService;
    }

    @Autowired
    public void setAdminApiService(@Qualifier("adminApiService") AdminApiService adminApiService) {
        this.adminApiService = adminApiService;
    }

    public BankTemplateAccountInfo16Process getBankTemplateAccountInfo16Process() {
        return bankTemplateAccountInfo16Process;
    }

    @Autowired
    public void setBankTemplateAccountInfo16Process(@Qualifier("bankTemplateAccountInfo16Process") BankTemplateAccountInfo16Process bankTemplateAccountInfo16Process) {
        this.bankTemplateAccountInfo16Process = bankTemplateAccountInfo16Process;
    }

    public SVBApiService getSVBApiService() {
        return svbApiService;
    }

    @Autowired
    public void setSVBApiService(@Qualifier("svbApiService") SVBApiService svbApiService) {
        this.svbApiService = svbApiService;
    }

}
