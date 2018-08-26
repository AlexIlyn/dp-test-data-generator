package csvdata.builder;

import static csvdata.builder.RandomValueUtils.getRandomValueByValueType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import csvdata.builder.enums.CaseSubType;
import csvdata.builder.enums.CaseType;

public class CSV_TestDataBuilder {
	
	private Path OUTPUT;
	//private Resource OUTPUT_RES;

	private Map<ValueType, List<Integer>> valueTypeListMap;
	private Map<String, ValueType> typeMapping;
	private Map<String, Integer> headersMap;
	private String[] headersArray;
	private Map<String, String> defaultValues;
	private Resource TYPE_MAPPING_RES;

	private final List<String[]> records;

	public List<String[]> getRecords() {
		return this.records;
	}

	private CSV_TestDataBuilder(String caseType, String caseSubType, Path OUTPUT) throws IOException {
		TYPE_MAPPING_RES = new ClassPathResource(String.format("%s\\%s\\TYPE_MAPPING.txt", caseType, caseSubType));
		String tempPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
		if(Files.isDirectory(Paths.get(tempPath))){
			this.OUTPUT = Paths.get(tempPath + OUTPUT);
		} else {
			int lastSlashIndex = tempPath.lastIndexOf('\\');
			this.OUTPUT = Paths.get(tempPath.substring(0, lastSlashIndex + 1) + OUTPUT);
		}
		typeMapping = initColumnDataTypesMap(TYPE_MAPPING_RES);
		records = new ArrayList<>();

	}

	public CSV_TestDataBuilder(String caseType, String caseSubType, String OUTPUT) throws IOException {
		this(caseType, caseSubType, Paths.get(OUTPUT));
	}

	private CSV_TestDataBuilder(String caseType, String caseSubType) throws IOException {
		this(caseType, caseSubType, Paths.get(UUID.randomUUID().toString() + ".txt"));
	}

	public CSV_TestDataBuilder(CaseType caseType, CaseSubType caseSubType, Path OUTPUT) throws IOException {
		this(caseType.name(), caseSubType.name(), OUTPUT);
	}

	public CSV_TestDataBuilder(CaseType caseType, CaseSubType caseSubType) throws IOException {
		this(caseType.name(), caseSubType.name());
	}

	public CSV_TestDataBuilder(CaseType caseType, CaseSubType caseSubType, String OUTPUT) throws IOException {
		this(caseType.name(), caseSubType.name(), OUTPUT);
	}

	public CSV_TestDataBuilder buildRandomRecords(int records) {

		for (int i = 0; i < records; i++) {
			buildRandomRecord();
		}
		return this;
	}

	public CSV_TestDataBuilder buildRandomRecord() {
		String[] result_array = new String[headersMap.size()];
		for (String header : headersArray) {
			if (typeMapping.get(header) != null) {
				result_array[headersMap.get(header)] = getRandomValueByValueType(typeMapping.get(header));
			} else if (defaultValues.containsKey(header)){
				result_array[headersMap.get(header)] = defaultValues.get(header);
			}
		}
		records.add(result_array);
		return this;
	}

	public CSV_TestDataBuilder buildEmptyRecord() {
		String[] result_array = new String[headersMap.size()];
		for (String header : headersArray) {
			if (typeMapping.get(header) != null) {
				result_array[headersMap.get(header)] = "";
			}
		}
		records.add(result_array);
		return this;
	}

	public CSV_TestDataBuilder setAllRecordsFieldsWithType(ValueType valueType, String value) {
		if (valueTypeListMap.get(valueType) == null) return this;
		for (int index : valueTypeListMap.get(valueType)) {
			for (String[] record : records) {
				record[index] = value;
			}
		}
		return this;
	}

	public CSV_TestDataBuilder setLastRecordFieldsWithType(ValueType fieldType, String value) {
		if (valueTypeListMap.get(fieldType) == null) return this;
		for (int index : valueTypeListMap.get(fieldType)) {
			records.get(records.size() - 1)[index] = value;
		}
		return this;
	}

	public CSV_TestDataBuilder setAllRecordsFieldsToRandom(ValueType fieldType, ValueType randomValueType) {
		if (valueTypeListMap.get(fieldType) == null) return this;
		for (int index : valueTypeListMap.get(fieldType)) {
			for (String[] record : records) {
				record[index] = getRandomValueByValueType(randomValueType);
			}
		}
		return this;
	}

	public CSV_TestDataBuilder setLastRecordFieldsToRandom(ValueType fieldType, ValueType randomValueType) {
		if (valueTypeListMap.get(fieldType) == null) return this;
		for (int index : valueTypeListMap.get(fieldType)) {
			records.get(records.size() - 1)[index] = getRandomValueByValueType(randomValueType);
		}
		return this;
	}

	public CSV_TestDataBuilder randomizeLastRecordField(String field) {
		if (headersMap.get(field) == null) return this;
		records.get(records.size() - 1)[headersMap.get(field)] = getRandomValueByValueType(typeMapping.get(field));
		return this;
	}

	public CSV_TestDataBuilder setAllRecordsFieldValue(String field, String value) {
		if (headersMap.get(field) == null) return this;
		for (String[] record : records) {
			record[headersMap.get(field)] = value;
		}
		return this;
	}

	public CSV_TestDataBuilder setAllRecordsFieldRandomValue(String field, ValueType valueType) {
		if (headersMap.get(field) == null) return this;
		for (String[] record : records) {
			record[headersMap.get(field)] = getRandomValueByValueType(valueType);
		}
		return this;
	}

	public CSV_TestDataBuilder setLastRecordFieldValue(String field, String value) {
		if (headersMap.get(field) == null) return this;
		records.get(records.size() - 1)[headersMap.get(field)] = value;
		return this;
	}

	public CSV_TestDataBuilder copyLastRecord() {
		records.add(records.get(records.size() - 1).clone());
		return this;
	}

	public Path generate() throws IOException {
		try (
				BufferedWriter writer = Files.newBufferedWriter(OUTPUT);
				//CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';').withEscape(' ').withQuoteMode(QuoteMode.NONE).withTrim())
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';').withCommentMarker('~').withTrim()
						.withHeader(headersArray)
				)
		) {
			//csvPrinter.printRecord(headersArray);
			for (String[] record : records) {
				csvPrinter.printRecord(record);
			}
			csvPrinter.flush();
		}
		return OUTPUT;
	}

	public CSV_TestDataBuilder destroy() throws IOException {
		File file = new File(OUTPUT.toString());
		if (file.exists()) file.delete();
		records.clear();
		return this;
	}

	public CSV_TestDataBuilder cleanRecords() throws IOException {
		records.clear();
		return this;
	}

	private Map<String, ValueType> initColumnDataTypesMap(Resource resource) throws IOException {
		valueTypeListMap = new HashMap<>();
		headersArray = CSVUtils.getHeaders(resource);
		headersMap = CSVUtils.getHeadersMap(resource);
		defaultValues = new HashMap<>();
		Map<String, ValueType> columnDataTypesMap = new HashMap<>();
		CSVRecord csvRecord = CSVUtils.buildFromFile(resource).get(0);
		String field;
		ValueType type;
		for (int i = 0; i < headersArray.length; i++) {
			field = headersArray[i];
			try {
				type = ValueType.valueOf(csvRecord.get(field));
			} catch (IllegalArgumentException e) {
				defaultValues.put(field, csvRecord.get(field));
				continue;
			}

			columnDataTypesMap.put(field, type);
			valueTypeListMap.computeIfAbsent(type, k -> new ArrayList<>());
			valueTypeListMap.get(type).add(i);
		}
		return columnDataTypesMap;
	}
}
