package com.org.shbvn.svbsimo.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraceNoGenerator {
    protected static final Logger logger = LoggerFactory.getLogger(NFsUtils.class);
        /**
     * Sinh maYeuCau dạng yyyyMMddHHmmss + random 6 ký tự hex.
     * @return mã yêu cầu unique.
     */
    public static String generateTraceNo() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        logger.info("Generated Trace No: " + timestamp + randomPart);
        return timestamp + randomPart;
    }
    public static String getKyBaoCao()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1); // Giảm 1 tháng

        String kyBaoCao = new SimpleDateFormat("MM/yyyy").format(cal.getTime());
        logger.info("Generated kyBaoCao: " + kyBaoCao);
        return kyBaoCao;
    }
}   
