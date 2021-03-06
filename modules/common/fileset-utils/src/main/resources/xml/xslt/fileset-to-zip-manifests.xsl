<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:d="http://www.daisy.org/ns/pipeline/data"
                xmlns:c="http://www.w3.org/ns/xproc-step"
                xmlns:pf="http://www.daisy.org/ns/pipeline/functions">
	
	<!--
		Convert a d:fileset, representing a set of zipped files in one or more zip files, to a
		series of c:zip-manifest documents, one for each zip file, which can be passed to px:zip
		steps (http://exproc.org/proposed/steps/other.html#zip) on the "manifest" port to create the
		zip files. Each of the c:zip-manifest documents have a @href attribute which can be passed
		as the value of the "href" option. The hrefs of the input d:fileset must be of the form
		`<path/to/zip/file>!/<path/within/zip>`. The paths may be relative (to @xml:base) or
		absolute. Files that are present on disk but not in memory must have a @original-href
		attribute. Files that are present in memory must not have a @original-href attribute.
	-->
	
	<xsl:import href="http://www.daisy.org/pipeline/modules/file-utils/library.xsl"/>
	
	<xsl:output method="xml" encoding="UTF-8" indent="yes" name="zip-manifest"/>
	
	<xsl:variable name="fileset.zip" select="collection()[1]"/>
	
	<xsl:template match="/">
		<xsl:for-each select="distinct-values(//d:file/substring-before(resolve-uri(@href,base-uri(.)),'!/'))">
			<xsl:variable name="href" select="."/>
			<xsl:result-document href="manifest_{position()}.xml" format="zip-manifest">
				<xsl:element name="c:zip-manifest">
					<xsl:attribute name="xml:base" select="base-uri($fileset.zip/*)"/>
					<xsl:attribute name="href" select="$href"/>
					<xsl:apply-templates select="$fileset.zip//d:file[substring-before(resolve-uri(@href,base-uri(.)),'!/')=$href]"/>
				</xsl:element>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template match="d:file">
		<xsl:element name="c:entry">
			<xsl:variable name="target" select="resolve-uri(@href, base-uri(.))"/>
			<xsl:attribute name="name" select="pf:unescape-uri(substring-after($target,'!/'))"/>
			<xsl:attribute name="href" select="(@original-href,@href)[1]"/>
			<!--
				serialization attributes
				
				Note that px:zip ignores these when the c:entry does not point to a document in
				memory (i.e. when @original-href exists).
			-->
			<xsl:sequence select="@byte-order-mark        | @escape-uri-attributes | @normalization-form   |
			                      @cdata-section-elements | @include-content-type  | @omit-xml-declaration |
			                      @doctype-public         | @indent                | @standalone           |
			                      @doctype-system         | @media-type            | @undeclare-prefixes   |
			                      @encoding               | @method                | @version"/>
			<!--
			    attributes specific for px:zip
			-->
			<xsl:sequence select="@compression-method | @compression-level"/>
		</xsl:element>
	</xsl:template>
	
</xsl:stylesheet>
