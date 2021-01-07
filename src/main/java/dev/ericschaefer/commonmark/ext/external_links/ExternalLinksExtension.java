package dev.ericschaefer.commonmark.ext.external_links;

import org.commonmark.Extension;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * commonmark-java extension for adding the attributes target="_blank" and rel="noopener" to links
 *
 * Create via ExternalLinksExtension.create(baseUrl) and add to extension in parser and render build steps.
 */
public class ExternalLinksExtension implements HtmlRenderer.HtmlRendererExtension {
    private final String baseUrl;

    private ExternalLinksExtension(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Create the extension.
     *
     * Example: ExternalLinksExtension.create("https://mydomain.com")
     *
     * @param baseUrl The base URL to use for detecting external links
     * @return the extension
     *
     */
    public static Extension create(String baseUrl) {
        return new ExternalLinksExtension(baseUrl);
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.attributeProviderFactory(context -> ExternalLinksAttributeProvider.create(baseUrl));
    }
}
