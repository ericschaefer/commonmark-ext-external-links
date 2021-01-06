package dev.ericschaefer.commonmark.ext.external_links;

import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

class ExternalLinksAttributeProvider implements AttributeProvider {
    private final String baseUrl;

    private ExternalLinksAttributeProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static ExternalLinksAttributeProvider create(String baseUrl) {
        return new ExternalLinksAttributeProvider(baseUrl);
    }

    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
        if (node instanceof Link) {
            Link link = (Link) node;
            String linkDestination = link.getDestination();
            if (linkDestination.startsWith("/") || linkDestination.toLowerCase().startsWith(baseUrl)) {
                return; // internal URL (relative or absolute)
            }

            attributes.put("target", "_blank");
            attributes.put("rel", "noopener");
        }
    }
}
