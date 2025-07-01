package com.org.shbvn.svbsimo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.shbvn.svbsimo.core.constant.ResponseOutPut;
import com.org.shbvn.svbsimo.job.SimoBatchJobGetFile;
import com.org.shbvn.svbsimo.job.SimoBatchJobProcessingFile;
import com.org.shbvn.svbsimo.job.SimoBatchJobUploadSVBProcessing;

@RestController
@RequestMapping("/batch-job")
public class BatchJobController {

    @Autowired
    private SimoBatchJobProcessingFile simoBatchJobProcessingFile;
    @Autowired
    private SimoBatchJobGetFile simoBatchJobGetFile;
    @Autowired
    private SimoBatchJobUploadSVBProcessing simoBatchJobUploadSVBProcessing;

    @PostMapping("/process")
    public ResponseEntity<ResponseOutPut> runSimoJob() {
        simoBatchJobProcessingFile.processSimoFile();
        ResponseOutPut response = new ResponseOutPut("OK", // result
                null, // exceptionMessage
                true, // status
                200);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-file")
    public ResponseEntity<ResponseOutPut> runSimoGetFileJob() {
        simoBatchJobGetFile.scanSimoFile();
        ResponseOutPut response = new ResponseOutPut("OK", // result
                null, // exceptionMessage
                true, // status
                200);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload-svb")
    public ResponseEntity<ResponseOutPut> runSimoUploadSVBJob() {
        simoBatchJobUploadSVBProcessing.processSimoUploadSVB();
        ResponseOutPut response = new ResponseOutPut("OK", // result
                null, // exceptionMessage
                true, // status
                200);
        return ResponseEntity.ok(response);
    }

}
