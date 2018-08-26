package org.apache.commons.csv;

import org.springframework.core.io.Resource;
import csvdata.builder.ValueType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVUtils {

	public static List<CSVRecord> buildFromFile(Resource resource) throws IOException {
		try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';');
			CSVParser csvParser = new CSVParser(reader, format);
			return csvParser.getRecords();
		}
	}
	public static String[] getHeaders(Resource resource) throws IOException {
		try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			CSVFormat format = CSVFormat.DEFAULT.withDelimiter(';');
			CSVParser csvParser = new CSVParser(reader, format);
			return csvParser.getRecords().get(0).values();
		}
	}

	public static Map<String, Integer> getHeadersMap(Resource resource) throws IOException {
		String[] headers = getHeaders(resource);
		Map<String, Integer> result = new HashMap<>();
		for (int i = 0; i < headers.length; i++) {
			result.put(headers[i], i);
		}
		return  result;
	}
	public static void generateDataTypeMaping(Resource TYPE_MAPPING, Resource HEADER_TEMPLATE, Path OUTPUT) throws IOException {
		String[] headers = getHeaders(HEADER_TEMPLATE);
		CSVRecord typesRecord = buildFromFile(HEADER_TEMPLATE).get(0);
		Map<String, Integer> headerToIndexMap = new HashMap<>();

		for (int i = 0; i < headers.length; i++) {
			headerToIndexMap.put(headers[i], i);
		}

		String field;
		ValueType type;
		for (CSVRecord csvRecord : CSVUtils.buildFromFile(TYPE_MAPPING)) {
			field = csvRecord.get("FIELD");
			try {
				type = ValueType.valueOf(csvRecord.get("TYPE"));
			} catch (IllegalArgumentException e) {
				continue;
			}
			setCSVRecordValue(typesRecord, headerToIndexMap.get(field), type.toString());
		}
		try (
				BufferedWriter writer = Files.newBufferedWriter(OUTPUT);

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';').withEscape(' ').withQuoteMode(QuoteMode.NONE).withTrim())
//						.withHeader(headersArray)
		) {
			csvPrinter.printRecord(headers);
			csvPrinter.printRecord(typesRecord.values());
			csvPrinter.flush();
		}
	}
	public static void setCSVRecordValue(CSVRecord csvRecord, int index, String value){
		csvRecord.values()[index] = value;
	}
}
