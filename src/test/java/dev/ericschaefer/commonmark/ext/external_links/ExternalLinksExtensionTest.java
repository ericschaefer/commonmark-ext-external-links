package dev.ericschaefer.commonmark.ext.external_links;

import org.commonmark.Extension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExternalLinksExtensionTest  {
    private Parser parser;
    private HtmlRenderer renderer;

    @BeforeEach
    public void setup() {
        List<Extension> extensions = Collections.singletonList(
                ExternalLinksExtension.create("http://www.example.com")
        );
        parser = Parser.builder()
                .extensions(extensions)
                .build();
        renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
    }

    @Test
    public void relativeUrl() {
        assertRenderedText(
                "relative url: [relative](/some_path)",
                "<p>relative url: <a href=\"/some_path\">relative</a></p>\n"
        );
    }

    @Test
    public void internalAbsoluteUrl() {
        assertRenderedText(
                "internal url: [internal](http://www.example.com/some_path)",
                "<p>internal url: <a href=\"http://www.example.com/some_path\">internal</a></p>\n"
        );
    }

    @Test
    public void externalUrl() {
        assertRenderedText(
                "external url: [external](http://www.google.com/some_path)",
                "<p>external url: <a href=\"http://www.google.com/some_path\" target=\"_blank\" rel=\"noopener\">external</a></p>\n"
        );
    }

    @Test
    public void malformedBaseUrl() {
        assertThrows(IllegalArgumentException.class,
                () -> ExternalLinksExtension.create("abcdef")
        );
    }

    protected void assertRenderedText(String markdown, String expectedHtml) {
        Node node = parser.parse(markdown);
        String renderedHtml = renderer.render(node);

        assertEquals(expectedHtml, renderedHtml);
    }
}
