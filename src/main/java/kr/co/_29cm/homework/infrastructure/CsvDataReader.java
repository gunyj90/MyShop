package kr.co._29cm.homework.infrastructure;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import kr.co._29cm.homework.service.DataReader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvDataReader implements DataReader {

    private static final String FILE_NAME = "/*.csv";

    private final ResourceLoader resourceLoader;

    public <T> List<T> read(Parseable<T> parseable) {
        List<T> resultData = new ArrayList<>();

        try (CSVReader reader = createReader()) {
            String[] words;
            while ((words = reader.readNext()) != null) {
                resultData.add(parseable.parse(words));
            }
        } catch (Exception e) {
            throw new RuntimeException("failed to read data", e);
        }
        return resultData;
    }

    private CSVReader createReader() throws IOException {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(false)
                .build();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(resourceLoader);
        InputStream inputStream = resolver.getResources(DIRECTORY + FILE_NAME)[0].getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        return new CSVReaderBuilder(inputStreamReader)
                .withSkipLines(1)
                .withCSVParser(csvParser)
                .build();
    }
}
