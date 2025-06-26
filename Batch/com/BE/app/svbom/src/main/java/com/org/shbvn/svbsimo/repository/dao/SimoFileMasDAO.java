/**
 * 
 */
package com.org.shbvn.svbsimo.repository.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.shbvn.svbsimo.repository.entities.SimoFileMas;


@Repository
public interface SimoFileMasDAO extends JpaRepository<SimoFileMas, Long> {

	/* @Query("SELECT new com.shinhan.recon.core.model.BankStatementFile( mas.uploadDt as uploadDt, bank.bankCode as bankCode, bank.bankName as bankName, mas.fileName as fileName, "
			+ "mas.openBalance as openBalance, mas.closeBalance as closeBalance, "
			+ "mas.processStatus as processStatus, mas.fileStatus as fileStatus, mas.totalCount as totalCount, "
			+ "mas.successCount as successCount, mas.fileValidCount as fileValidCount, mas.remark as remark, "
			+ "bank.isBankYn as isBank, bank.isRepaymentYn as isRepayment, bank.addInf as fileCnt)"
			+ "FROM TOmsStmtFileMas mas, TBankCommon bank "
			+ "WHERE mas.uploadDt = :uploadDt and mas.bankCode = bank.bankAccNuber "
			+ "and mas.fileName like bank.bankCode || '%' ")
	public List<BankStatementFile> getListStmtFileMasByDate(@Param("uploadDt") Date uploadDt); */
	
	
}
