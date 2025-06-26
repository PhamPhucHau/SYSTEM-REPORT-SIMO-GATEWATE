package com.org.shbvn.svbsimo.core.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import com.org.shbvn.svbsimo.repository.services.impl.GenericCacheService;

public abstract class AbstractService extends AbstractRepository {

    private ProcessManagementService processManagerService;

    private GenericCacheService genericCacheService;

    public ProcessManagementService getProcessManagerService() {
        return processManagerService;
    }

    @Autowired
    public void setProcessManagerService(@Lazy @Qualifier("processManagerService") ProcessManagementService processManagerService) {
        this.processManagerService = processManagerService;
    }
    
    public GenericCacheService getGenericCacheService() {
        return genericCacheService;
    }

    @Autowired
    public void setGenericCacheService(@Lazy @Qualifier("genericCacheService") GenericCacheService genericCacheService) {
        this.genericCacheService = genericCacheService;
    }

}
