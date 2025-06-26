package com.org.shbvn.svbsimo.core.common;

import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.apache.poi.ss.formula.functions.T;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.org.shbvn.svbsimo.core.model.BankCommonTemplate;
import com.org.shbvn.svbsimo.core.model.DailyCommonTemplate;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;

public class ExcelDTO {
    
    public static Map<String, Entry<String, UnaryOperator<String>>> buildBankTemplateMapping(List<String> excelColumn, Field[] bankStatementProperty){
		/**bankStatementMapping : {excel column name} {[property in Statement],[function to parse value on excel to value in property]}**/
		Map<String, Entry<String, UnaryOperator<String>>> bankStatementMapping = new HashMap<>();
		
		/*all parser to map value from excel, must map exactly with the index of excel column*/
		Map<Integer, UnaryOperator<String>> parser = new HashMap<>();
		
		/*excelColumn, clientCoporateProperty MUST have same size*/
		for (int columnIndex = 0; columnIndex < excelColumn.size(); columnIndex++) {
			String key = excelColumn.get(columnIndex).trim();
			UnaryOperator<String> parserToConsume = Optional.ofNullable(parser.get(columnIndex)).orElse(UnaryOperator.identity());
			System.out.println("key: " + key + ", parser: " + parserToConsume + ", columnIndex: " + columnIndex + "excelColumn.size()" +excelColumn.size());
			bankStatementMapping.put(key, new SimpleEntry<String, UnaryOperator<String>>(bankStatementProperty[columnIndex].getName(), parserToConsume));
		}
		
		return bankStatementMapping;
	}
    public static Map<String, Entry<String, UnaryOperator<String>>> buildDailyTemplateMapping(List<String> excelColumn, Field[] bankStatementProperty){
		/**bankStatementMapping : {excel column name} {[property in Statement],[function to parse value on excel to value in property]}**/
		Map<String, Entry<String, UnaryOperator<String>>> bankStatementMapping = new HashMap<>();
		
		/*all parser to map value from excel, must map exactly with the index of excel column*/
		Map<Integer, UnaryOperator<String>> parser = new HashMap<>();
		
		/*excelColumn, clientCoporateProperty MUST have same size*/
		// for (int columnIndex = 0; columnIndex < excelColumn.size(); columnIndex++) {
		// 	String key = excelColumn.get(columnIndex).trim();
		// 	UnaryOperator<String> parserToConsume = Optional.ofNullable(parser.get(columnIndex)).orElse(UnaryOperator.identity());
		// 	System.out.println("key: " + key + ", parser: " + parserToConsume + ", columnIndex: " + columnIndex + "excelColumn.size()" +excelColumn.size());
		// 	bankStatementMapping.put(key, new SimpleEntry<String, UnaryOperator<String>>(bankStatementProperty[columnIndex].getName(), parserToConsume));
		// }
		for (int columnIndex = 0; columnIndex < excelColumn.size(); columnIndex++) {
		String key = excelColumn.get(columnIndex).trim().toLowerCase();
		UnaryOperator<String> parserToConsume = Optional.ofNullable(parser.get(columnIndex)).orElse(UnaryOperator.identity());

		// Find the field whose name (or @JsonProperty value) matches the column name (case-insensitive)
		String mappedFieldName = null;
		for (Field field : bankStatementProperty) {
			String fieldName = field.getName().toLowerCase();
			String jsonProperty = null;
			if (field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class)) {
				jsonProperty = field.getAnnotation(com.fasterxml.jackson.annotation.JsonProperty.class).value().toLowerCase();
			}
			if (key.equals(fieldName) || (jsonProperty != null && key.equals(jsonProperty))) {
				mappedFieldName = field.getName();
				break;
			}
		}
		if (mappedFieldName != null) {
			bankStatementMapping.put(key, new SimpleEntry<>(mappedFieldName, parserToConsume));
		}
	}
		
		return bankStatementMapping;
	}
	@SuppressWarnings("hiding")
	public static <T extends BankCommonTemplate> List<T> mapListExcelDataToListBankTemplate
					(JsonArray jsonArrayDocument, Map<String, Entry<String, UnaryOperator<String>>> bankStatementMapping, Supplier<T> supplier){
		List<T> statements = new ArrayList<>();
		for(JsonElement element: jsonArrayDocument) {
			boolean isContinue = mapOneExcelDataToOneBankTemplate(statements, element, bankStatementMapping, supplier);
			//stop scanning excel file if one item don't add into List
			if(!isContinue) {
				break;
			}
		}
		return statements;
	}
	@SuppressWarnings("hiding")
	public static <T extends DailyCommonTemplate> List<T> mapListCSVDataToListBankTemplate
					(JsonArray jsonArrayDocument, Map<String, Entry<String, UnaryOperator<String>>> bankStatementMapping, Supplier<T> supplier){
		List<T> statements = new ArrayList<>();
		// T statement = supplier.get();
		// statement
		// statements.add(); 
		for(JsonElement element: jsonArrayDocument) {
			boolean isContinue = mapOneCSVDataToOneBankTemplate(statements, element, bankStatementMapping, supplier);
			//stop scanning excel file if one item don't add into List
			if(!isContinue) {
				break;
			}
		}
		return statements;
	}
    @SuppressWarnings("hiding")
	private static <T extends BankCommonTemplate> Boolean mapOneExcelDataToOneBankTemplate
				(List<T> statements, JsonElement element, Map<String, Entry<String, UnaryOperator<String>>> bankStatementMapping, Supplier<T> supplier) {
		T statement = supplier.get();
		for(Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()){
			String excelColumnName = entry.getKey().trim();
			System.out.println("excelColumnName: " + excelColumnName);
			
			String excelColumnValue = entry.getValue().getAsString();
			System.out.println("excelColumnValue: " + excelColumnValue);
			if(!bankStatementMapping.containsKey(excelColumnName)) {
				continue;
			}
			
			Entry<String, UnaryOperator<String>> mapper = bankStatementMapping.get(excelColumnName);
			//get value to add into property, and call parser to parse that value
			String valueToAdd = mapper.getValue().apply(excelColumnValue);
			System.out.println("valueToAdd: " + valueToAdd);
			//get property of BankStatement
			String field = mapper.getKey();
			System.out.println("field: " + field);
			if(valueToAdd != null && !CommonUtils.setPropertyIntoObject(statement, valueToAdd, field)) {
				//statement.setError("Configuration error"); TODO
				System.out.println("Error setting property: " + field + " with value: " + valueToAdd);
			}
		}
		
		if(!CommonUtils.isBlank(statement.getCif())) {
			statements.add(statement);
			return true;
		}
		return false;
	}
    @SuppressWarnings("hiding")
	private static <T extends DailyCommonTemplate> Boolean mapOneCSVDataToOneBankTemplate
				(List<T> statements, JsonElement element, Map<String, Entry<String, UnaryOperator<String>>> bankStatementMapping, Supplier<T> supplier) {
		T statement = supplier.get();
		for(Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()){
			String excelColumnName = entry.getKey().trim();
			System.out.println("excelColumnName: " + excelColumnName);
			
			String excelColumnValue = entry.getValue().getAsString();
			System.out.println("excelColumnValue: " + excelColumnValue);
			if(!bankStatementMapping.containsKey(excelColumnName.toLowerCase())) {
				continue;
			}
			
			Entry<String, UnaryOperator<String>> mapper = bankStatementMapping.get(excelColumnName.toLowerCase());
			//get value to add into property, and call parser to parse that value
			String valueToAdd = mapper.getValue().apply(excelColumnValue);
			System.out.println("valueToAdd: " + valueToAdd);
			//get property of BankStatement
			String field = mapper.getKey();
			System.out.println("field: " + field);
			if(valueToAdd != null && !CommonUtils.setPropertyIntoObject(statement, valueToAdd, field)) {
				//statement.setError("Configuration error"); TODO
				System.out.println("Error setting property: " + field + " with value: " + valueToAdd);
			}
		}
		
		return false;
	}
@SuppressWarnings("hiding")
public static <T extends DailyCommonTemplate, E> T mapCSVDataToSingleDailyTemplateObject(
    JsonArray jsonArrayDocument,
    Map<String, Entry<String, UnaryOperator<String>>> childMapping,
    Supplier<T> parentSupplier,
    Supplier<E> childSupplier,
    BiConsumer<T, List<E>> setChildListMethod
) {
    T parentObject = parentSupplier.get();
    List<E> childList = new ArrayList<>();

    for (JsonElement element : jsonArrayDocument) {
        E childObject = childSupplier.get();

        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            String columnName = entry.getKey().trim().toLowerCase();
            String columnValue = entry.getValue().getAsString();

            if (!childMapping.containsKey(columnName)) {
                continue;
            }

            Entry<String, UnaryOperator<String>> mapper = childMapping.get(columnName);
            String valueToAdd = mapper.getValue().apply(columnValue);
            String fieldName = mapper.getKey();

            CommonUtils.setPropertyIntoObject(childObject, valueToAdd, fieldName);
        }

        childList.add(childObject);
    }

    setChildListMethod.accept(parentObject, childList);
    return parentObject;
}



}
