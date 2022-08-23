package git;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class TestGit {

    @ValueSource(strings = {"Введение", "Основы Git", "Ветвление в Git"})
    @ParameterizedTest(name = "Книга гита содержит тему {0}")
    void headersBookGit(String testData) {
        open("https://git-scm.com/book/ru/v2");
        $$("ol.book-toc").shouldHave(CollectionCondition.texts(testData));
    }

    @CsvSource(value = {
            "1. , Введение",
            "2. , Основы Git",
            "3. , Ветвление в Git",
    })

    @ParameterizedTest(name = "Номер главы \"{0}\" для запроса: \"{1}\"")
    void numberHeaderBookGit(String number, String header) {
        open("https://git-scm.com/book/ru/v2");
        $x("//li[@class='chapter']//h2[text()=" + number + "]/a").shouldHave(text(header));


    }

    static Stream<Arguments> dataLangHeaderBook() {
        return Stream.of(
                Arguments.of(Lang.de, List.of("Erste Schritte", "Git Grundlagen", "Git Branching", "Git auf dem Server","Verteiltes Git",
                        "GitHub", "Git Tools", "Git einrichten", "Git und andere Systeme", "Git Interna", "Anhang A: Git in anderen",
                        "Anhang B: Git in Ihre Anwendungen", "Anhang C: Git Kommandos")),
                Arguments.of(Lang.en, List.of("Getting Started", "Git Basics", "Git Branching", "Git on the Server", "Distributed Git",
                        "GitHub", "Git Tools", "Customizing Git", "Git and Other Systems", "Git Internals", "Appendix A: Git in Other",
                        "Appendix B: Embedding Git in your", "Appendix C: Git Commands"))
        );
    }

    @MethodSource("dataLangHeaderBook")
    @ParameterizedTest(name = "Для lang {0} отображается заголовки {1}")
    void langHeaderBook(Lang lang, List<String> expectedHeader) {
        String url = "https://git-scm.com/book/" + lang + "/v2";
        open(url);
        $$("h2 a").shouldHave(CollectionCondition.texts(expectedHeader));

    }
}

