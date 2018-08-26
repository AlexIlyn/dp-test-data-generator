package org.apache.commons.csv;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;

/**
 * CSVRecord Builder At Runtime
 *
 * @author
 */
public class CSVRecordBuider {

	private static List<CSVRecord> getCSVRecordsFromReader(Reader reader) throws IOException {
		CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';');
		CSVParser csvParser = new CSVParser(reader, format);
		return csvParser.getRecords();
	}

	@SuppressWarnings("resource")
	public static List<CSVRecord> buildFromFile(Path filePath) throws IOException {
		try (Reader reader = Files.newBufferedReader(filePath)) {
			return getCSVRecordsFromReader(reader);
		}
	}

	public static CSVRecord buildSingleRecordFromFile(Path filePath) throws IOException {
		return buildFromFile(filePath).get(0);
	}

	@SuppressWarnings("resource")
	public static List<CSVRecord> buildFromFile(String filePath) throws IOException {
		try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
			return getCSVRecordsFromReader(reader);
		}
	}

	@SuppressWarnings("resource")
	public static List<CSVRecord> buildFromFile(Resource resource) throws IOException {
		try (Reader reader = Files.newBufferedReader(Paths.get(resource.getFile().toString()))) {
			return getCSVRecordsFromReader(reader);
		}
	}

	public static CSVRecord buildSingleRecordFromResource(Resource resource) throws IOException {
		return buildFromFile(resource).get(0);
	}
}
