package com.org.shbvn.svbsimo.core.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.repository.services.SimoAuthMenuRepositoryService;
import com.org.shbvn.svbsimo.repository.services.SimoFileMasManagerRepositoryService;
import com.org.shbvn.svbsimo.repository.services.SimoRoleRepositoryService;
import com.org.shbvn.svbsimo.repository.services.SimoUserRepositoryService;
import com.org.shbvn.svbsimo.repository.services.SimoUserRoleRepositoryService;
import com.org.shbvn.svbsimo.repository.services.UtilityManagerRepositoryService;
import com.org.shbvn.svbsimo.repository.services.SimoFileDetailHisRepositoryService;
import com.org.shbvn.svbsimo.repository.services.SimoFileHisRepositoryService;

@Service("repositoryManageService")
public class RepositoryManageService {
    
    private UtilityManagerRepositoryService utilityManagerRepositoryService;
    private SimoFileMasManagerRepositoryService simoFileMasManagerRepositoryService;
    private SimoUserRepositoryService simoUserRepositoryService;
    private SimoRoleRepositoryService simoRoleRepositoryService;
    private SimoAuthMenuRepositoryService simoAuthMenuRepositoryService;
    private SimoUserRoleRepositoryService simoUserRoleRepositoryService;
    private SimoFileDetailHisRepositoryService simoFileDetailHisRepositoryService;
    private SimoFileHisRepositoryService simoFileHisRepositoryService;

    public UtilityManagerRepositoryService getUtilityManagerRepositoryService() {
        return utilityManagerRepositoryService;
    }
    @Autowired
    public void setUtilityManagerRepositoryService(
        @Qualifier("utilityManagerRepositoryService") UtilityManagerRepositoryService utilityManagerRepositoryService) {
        this.utilityManagerRepositoryService = utilityManagerRepositoryService;
    }

    public SimoFileMasManagerRepositoryService getSimoFileMasManagerRepositoryService() {
        return simoFileMasManagerRepositoryService;
    }
    @Autowired
    public void setSimoFileMasManagerRepositoryService( 
        @Qualifier("simoFileMasManagerRepositoryService")SimoFileMasManagerRepositoryService simoFileMasManagerRepositoryService) {
        this.simoFileMasManagerRepositoryService = simoFileMasManagerRepositoryService;
    }

    public SimoUserRepositoryService getSimoUserRepositoryService() {
        return simoUserRepositoryService;
    }
    @Autowired
    public void setSimoUserRepositoryService(
        @Qualifier("simoUserRepositoryService") SimoUserRepositoryService simoUserRepositoryService) {
        this.simoUserRepositoryService = simoUserRepositoryService;
    }
    
    public SimoRoleRepositoryService getSimoRoleRepositoryService() {
        return simoRoleRepositoryService;
    }
    @Autowired
    public void setSimoRoleRepositoryService(
        @Qualifier("simoRoleRepositoryService") SimoRoleRepositoryService simoRoleRepositoryService) {
        this.simoRoleRepositoryService = simoRoleRepositoryService;
    }
    
    public SimoAuthMenuRepositoryService getSimoAuthMenuRepositoryService() {
        return simoAuthMenuRepositoryService;
    }
    @Autowired
    public void setSimoAuthMenuRepositoryService(
        @Qualifier("simoAuthMenuRepositoryService") SimoAuthMenuRepositoryService simoAuthMenuRepositoryService) {
        this.simoAuthMenuRepositoryService = simoAuthMenuRepositoryService;
    }
    
    public SimoUserRoleRepositoryService getSimoUserRoleRepositoryService() {
        return simoUserRoleRepositoryService;
    }
    @Autowired
    public void setSimoUserRoleRepositoryService(
        @Qualifier("simoUserRoleRepositoryService") SimoUserRoleRepositoryService simoUserRoleRepositoryService) {
        this.simoUserRoleRepositoryService = simoUserRoleRepositoryService;
    }

    public SimoFileDetailHisRepositoryService getSimoFileDetailHisRepositoryService() {
        return simoFileDetailHisRepositoryService;
    }
    @Autowired
    public void setSimoFileDetailHisRepositoryService(
        @Qualifier("simoFileDetailHisRepositoryService") SimoFileDetailHisRepositoryService simoFileDetailHisRepositoryService) {
        this.simoFileDetailHisRepositoryService = simoFileDetailHisRepositoryService;
    }

    public SimoFileHisRepositoryService getSimoFileHisRepositoryService() {
        return simoFileHisRepositoryService;
    }
    @Autowired
    public void setSimoFileHisRepositoryService(
        @Qualifier("simoFileHisRepositoryService") SimoFileHisRepositoryService simoFileHisRepositoryService) {
        this.simoFileHisRepositoryService = simoFileHisRepositoryService;
    }
}
