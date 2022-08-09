package com.example.datauniversity.repository;

import com.example.datauniversity.model.University;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
    public static String type = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return type.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel");
    }

    public static List<University> csvToUniversities(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            com.opencsv.CSVParser csvParser = new CSVParserBuilder()
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withSkipLines(1)
                    .withCSVParser(csvParser)
                    .build();
            List<University> universitiesList = new ArrayList<>();
            List<String[]> csvRecords = csvReader.readAll();
            for (String[] csvRecord : csvRecords) {
                List<String> recordList = Arrays.asList(csvRecord);
                University university = new University(
                        csvRecord[1].replace("'", "~"),
                        csvRecord[0].replace("'", "~"),
                        csvRecord[2].replace("'", "~"),
                        csvRecord[3].replace("'", "~"),
                        csvRecord[4].replace("'", "~")
                );
                universitiesList.add(university);
            }
            return universitiesList;
        } catch (Exception e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream universitiesToCSV(List<University> universities) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL).withDelimiter(';');

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            csvPrinter.printRecord(Arrays.asList("State", "Name", "Institution Type", "Phone Number", "Website"));
            for (University university : universities) {
                university.setNameUniversity(university.getNameUniversity().replace("~", "'"));
                university.setState(university.getState().replace("~", "'"));
                university.setInstitutionType(university.getInstitutionType().replace("~", "'"));
                university.setPhoneNumber(university.getPhoneNumber().replace("~", "'"));
                university.setWebsite(university.getWebsite().replace("~", "'"));
                List<String> data = Arrays.asList(
                        university.getState(),
                        university.getNameUniversity(),
                        university.getInstitutionType(),
                        university.getPhoneNumber(),
                        university.getWebsite()
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
