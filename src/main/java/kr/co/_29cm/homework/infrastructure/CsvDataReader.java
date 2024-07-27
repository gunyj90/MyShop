package kr.co._29cm.homework.infrastructure;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import kr.co._29cm.homework.service.DataReader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
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
        Resource[] resources = resolver.getResources(DIRECTORY + FILE_NAME);

        return new CSVReaderBuilder(resources.length > 0 ? new FileReader(resources[0].getFile()) : null)
                .withSkipLines(1)
                .withCSVParser(csvParser)
                .build();
    }
}
