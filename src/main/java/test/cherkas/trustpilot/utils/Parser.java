package test.cherkas.trustpilot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import test.cherkas.trustpilot.domain.props.BusinessUnit;
import test.cherkas.trustpilot.domain.props.PropsResponse;

import java.util.Objects;

@Component
public class Parser {

    public BusinessUnit getBusinessUnitFromHtml(String html) {
        try {
            return parseJsonToProps(
                    parseRespHtmlToPropsJson(html)
            )
                    .getProps()
                    .getPageProps()
                    .getBusinessUnit();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public PropsResponse parseJsonToProps(String json) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, PropsResponse.class);
    }

    public String parseRespHtmlToPropsJson(String input) {
        var doc = Jsoup.parse(input);
        return Objects.requireNonNull(doc.select("#__NEXT_DATA__").first()).html();
    }
}
