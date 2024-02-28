package cherkas.trustpilot.utils;

import cherkas.trustpilot.domain.props.BusinessUnit;
import cherkas.trustpilot.domain.props.PropsResponse;
import cherkas.trustpilot.exception.ParserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

public class Parser {

    private static final String NEXT_DATA_QUERY = "#__NEXT_DATA__";

    public static BusinessUnit getBusinessUnitFromHtml(String html) {
        try {
            Document doc = Jsoup.parse(html);
            String parsedJson = Objects.requireNonNull(doc.select(NEXT_DATA_QUERY).first()).html();
            return parseJsonToProps(parsedJson)
                    .getProps()
                    .getPageProps()
                    .getBusinessUnit();
        } catch (IOException e) {
            throw new ParserException("Html data parsing error: " + e.getMessage());
        }
    }

    private static PropsResponse parseJsonToProps(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, PropsResponse.class);
    }
}
