package cherkas.trustpilot.utills;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static cherkas.trustpilot.utils.Parser.getBusinessUnitFromHtml;

class ParserTest {

    @Test
    @SneakyThrows
    void getBusinessUnitFromHtmlPositiveTest() {
        var file = new FileInputStream("src/test/resources/test_html.txt");
        var html = IOUtils.toString(file, StandardCharsets.UTF_8);

        var businessUnit = getBusinessUnitFromHtml(html);

        Assertions.assertEquals(1460, businessUnit.getNumberOfReviews());
        Assertions.assertEquals(4.9, businessUnit.getTrustScore());
    }

    @Test
    void getBusinessUnitFromHtmlNegativeTest() {
        var html = "<script id=\"__NEXT_DATA__\" type=\"application/json\">";
        Assertions.assertThrows(NullPointerException.class, () -> getBusinessUnitFromHtml(""));
        Assertions.assertThrows(RuntimeException.class, () -> getBusinessUnitFromHtml(html));
    }
}
