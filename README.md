# commonmark-ext-external-links

This is a [commonmark-java](https://github.com/commonmark/commonmark-java) plugin adding target=_blank" and rel="noopener" to external links.

[![Build](https://github.com/ericschaefer/commonmark-ext-external-links/workflows/Build/badge.svg)](https://github.com/ericschaefer/commonmark-ext-external-links/actions?query=workflow%3ABuild)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.ericschaefer/commonmark-ext-external-links)](https://search.maven.org/search?q=g:com.github.ericschaefer)
[![javadoc](https://javadoc.io/badge2/com.github.ericschaefer/commonmark-ext-external-links/javadoc.svg?color=blue)](https://javadoc.io/doc/com.github.ericschaefer/commonmark-ext-external-links)

## Usage

Just add it as an extension as usual providing the base URL of your site for detecting if a link is internal or external:

```Java
List<Extension> extensions = Collections.singletonList(
    ExternalLinksExtension.create("http://www.example.com")
);
Parser parser = Parser.builder()
    .extensions(extensions)
    .build();
HtmlRenderer renderer = HtmlRenderer.builder()
    .extensions(extensions)
    .build();

Node node = parser.parse("external url: [external](http://www.google.com/some_path)");
String html = renderer.render(node);

/*
<p>external url: <a href="http://www.google.com/some_path" target="_blank" rel="noopener">external</a></p>\n
*/

node = parser.parse("internal url: [internal](http://www.example.com/some_path)");
html = renderer.render(node);

/*
<p>internal url: <a href="http://www.example.com/some_path">internal</a></p>\n
*/

node = parser.parse("relative url: [relative](/some_path)");
html = renderer.render(node);

/*
<p>relative url: <a href="/some_path">relative</a></p>\n
*/

```

## Gradle

```Groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.github.ericschaefer:commonmark-ext-external-links:1.0.2'
}

```

## Maven

```XML
<dependency>
    <groupId>com.github.ericschaefer</groupId>
    <artifactId>commonmark-ext-external-links</artifactId>
    <version>1.0.2</version>
</dependency>
```