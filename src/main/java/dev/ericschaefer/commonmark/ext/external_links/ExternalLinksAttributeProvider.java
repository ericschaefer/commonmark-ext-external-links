package dev.ericschaefer.commonmark.ext.external_links;

import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

class ExternalLinksAttributeProvider implements AttributeProvider {
    private final URL url;

    private ExternalLinksAttributeProvider(URL url) {
        this.url = url;
    }

    public static ExternalLinksAttributeProvider create(URL url) {
        return new ExternalLinksAttributeProvider(url);
    }

    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
        if (node instanceof Link) {
            Link link = (Link) node;
            String linkDestination = link.getDestination();
            if (linkDestination.startsWith("/")) { // relative URL
                linkDestination = url.getProtocol() + url.getHost() + linkDestination;
            }

            URL linkUrl;
            try {
                linkUrl = new URL(linkDestination);
            } catch (MalformedURLException e) {
                return; // ignore link
            }

            if (isInternal(linkUrl)) {
                return;
            }

            attributes.put("target", "_blank");
            attributes.put("rel", "noopener noreferrer");
        }
    }

    private boolean isInternal(URL linkUrl) {
        return linkUrl.getHost().equals(url.getHost());
    }
}
