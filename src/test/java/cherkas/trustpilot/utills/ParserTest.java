package cherkas.trustpilot.utills;

import cherkas.trustpilot.exception.ParserException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static cherkas.trustpilot.utils.Parser.getBusinessUnitFromHtml;
import static java.nio.charset.StandardCharsets.UTF_8;

class ParserTest {

    @Test
    @SneakyThrows
    void getBusinessUnitFromHtmlPositiveTest() {

        var businessUnit = getBusinessUnitFromHtml(
                new ClassPathResource("test_html.txt").getContentAsString(UTF_8));

        Assertions.assertEquals(1460, businessUnit.getNumberOfReviews());
        Assertions.assertEquals(4.9, businessUnit.getTrustScore());
    }

    @Test
    void getBusinessUnitFromHtmlNegativeTest() {
        var html = "<script id=\"__NEXT_DATA__\" type=\"application/json\">";
        Assertions.assertThrows(NullPointerException.class, () -> getBusinessUnitFromHtml(""));
        Assertions.assertThrows(ParserException.class, () -> getBusinessUnitFromHtml(html));
    }
}
