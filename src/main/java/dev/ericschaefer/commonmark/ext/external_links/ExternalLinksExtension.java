package dev.ericschaefer.commonmark.ext.external_links;

import org.commonmark.Extension;
import org.commonmark.renderer.html.HtmlRenderer;

public class ExternalLinksExtension implements HtmlRenderer.HtmlRendererExtension {
    private final String baseUrl;

    private ExternalLinksExtension(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static Extension create(String baseUrl) {
        return new ExternalLinksExtension(baseUrl);
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.attributeProviderFactory(context -> ExternalLinksAttributeProvider.create(baseUrl));
    }
}
