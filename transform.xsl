<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" />
	<xsl:template match="body">
		<div class="container">
			<xsl:apply-templates />
		</div>
	</xsl:template>
	<xsl:template match="back"></xsl:template>
	<xsl:template match="front"></xsl:template>
	<xsl:template match="aff">
		<p>
			<xsl:apply-templates />
		</p>
	</xsl:template>
	<xsl:template match="pub-date">
		<p>
			Publicado em: \n"
			<xsl:apply-templates />
		</p>
	</xsl:template>
	<xsl:template match="abstract">
		<h3>RESUMO</h3>
		<p>
			<xsl:apply-templates />
		</p>
		<hr></hr>
	</xsl:template>
	<xsl:template match="sec">
		<section>
			<xsl:apply-templates />
		</section>
	</xsl:template>
	<xsl:template match="sec/title">
		<h3>
			<xsl:apply-templates />
		</h3>
	</xsl:template>
	<xsl:template match="sec/sec/title">
		<h4>
			<xsl:apply-templates />
		</h4>
	</xsl:template>
	<xsl:template match="sec/sec/sec/title">
		<h5>
			<xsl:apply-templates />
		</h5>
	</xsl:template>
	<xsl:template match="p">
		<p>
			<xsl:apply-templates />
		</p>
	</xsl:template>
	<xsl:template match="fig"></xsl:template>
	<xsl:template match="xref">
		<i>
			<xsl:apply-templates />
		</i>
	</xsl:template>
</xsl:stylesheet>