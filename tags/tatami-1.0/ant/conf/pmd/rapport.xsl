<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<HTML>
			<HEAD>
				<META http-equiv="Content-Type"
					content="text/html; charset=iso-8859-1"/>
				<style>
					<xsl:text disable-output-escaping="yes"> <![CDATA[  
BODY {
	FONT-SIZE: 8pt; FONT-FAMILY: Verdana, Geneva, Arial, Helvetica, sans-serif
}
EM {
	FONT-WEIGHT: bold; FONT-STYLE: italic
}
A {
	FONT-SIZE: 8pt; FONT-FAMILY: Verdana, Geneva, Arial, Helvetica, sans-serif
}
TD {
	FONT-SIZE: 8pt; FONT-FAMILY: Verdana, Geneva, Arial, Helvetica, sans-serif
}
P {
	FONT-SIZE: 8pt; FONT-FAMILY: Verdana, Geneva, Arial, Helvetica, sans-serif; TEXT-ALIGN: justify
}
LI {
	FONT-SIZE: 8pt; FONT-FAMILY: Verdana, Geneva, Arial, Helvetica, sans-serif; TEXT-ALIGN: justify
}
A:visited {
	COLOR: #5f4ed0
}
A:link {
	COLOR: #5f4ed0
}
A:hover {
	COLOR: #8d81e1
}
H3 {
	FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #8d81e1; FONT-STYLE: normal; FONT-FAMILY: Verdana, Geneva, Arial, Helvetica, sans-serif
}
H4 {
	PADDING-LEFT: 10px; FONT-WEIGHT: bold; FONT-SIZE: 9pt; COLOR: #8d81e1; FONT-FAMILY: Verdana, Geneva, Arial, Helvetica, sans-serif
; font-style: italic
}
]]> </xsl:text>
				</style>
			</HEAD>
			<BODY text="#000000" bgColor="#ffffff">
				<table width="100%" border="0" cellspacing="0" cellpadding="5">
					<tr>
						<td valign="middle" bgcolor="#000000">
							<h2 align="center">
								<font color="#FFFFFF">
									<font
										face="Geneva, Arial, Helvetica, san-serif"
										color="#F3F3F3">
										<i>Eclipse</i>
									</font>
								</font>
							</h2>
						</td>
						<td valign="middle" bgcolor="#000000">
							<h2 align="center">
								<font color="#FFFFFF">
									<font
										face="Geneva, Arial, Helvetica, san-serif"
										color="#F3F3F3">Rapport de vérification
										des règles de développement</font>
								</font>
							</h2>
						</td>
						<td valign="middle" align="center">
							<a
								href="http://intranoo.francetelecom.fr/sites/CLARA.htm"
								>
								<h2 align="center">
									<b>
										<i>
											<font color="#333366">Clar@</font>
										</i>
									</b>
								</h2>
							</a>
						</td>
					</tr>
				</table>
				<p/>
				<!--			<table border="1" cellspacing="0" cellpadding="0"
					bordercolor="#333366" align="center">
					<tr>
						<td>
							<table border="0" cellpadding="5" align="center"
								cellspacing="0" >
								<tr>
									<td bgcolor="#333366">
										<a name="sommaire">
										<font color="#CCCCCC" size="+1">
											<b>
												<font color="#FFFFFF">Sommaire</font>
											</b>
										</font></a>
									</td>
								</tr>
								<tr><td>-->
				<li>
					<a href="#resume">Résumé de la vérification</a>
				</li>
				<li>
					<a href="#listeerreurs">Liste des erreurs trouvées</a>
				</li>
				<li>
					<a href="#detailerreurs">Détail des erreurs trouvées</a>
				</li>
				<li>
					<a href="#listefichiers">Liste des erreurs par fichier</a>
				</li>
				<li>
					<a href="#detail">Détail des erreurs par fichier</a>
				</li>
				<!--
									</td></tr></table></td></tr></table>-->
				<p>
				</p>
				<xsl:element name="a">
					<xsl:attribute name = "name">
						<xsl:text>resume</xsl:text>
					</xsl:attribute>
					<h3>Résumé de la vérification</h3>
				</xsl:element>
				<p/>
<!--				<p>Nombre de fichiers vérifiés : <xsl:number level="any"
					value="count(descendant::file)"/> </p>-->
				<p>Nombre de fichiers avec erreur : <xsl:number level="any"
					value="count(descendant::file[violation])" /> </p>
				<p>Nombre total d'erreurs : <xsl:number level="any"
					value="count(descendant::violation)" /> </p>
<!--				<p>Nombre moyen d'erreurs par fichier : <xsl:number level="any"
					value="count(descendant::violation) div count(descendant::file)"
					/> </p>-->
				<hr align="left" width="95%" size="1"/>
				<xsl:element name="a">
					<xsl:attribute name = "name">
						<xsl:text>listeerreurs</xsl:text>
					</xsl:attribute>
					<h3>Liste des erreurs trouvées</h3>
				</xsl:element>
				<p/>
				<table width="90%" border="1" cellspacing="0" cellpadding="0"
					bordercolor="#333366" align="center">
					<tr>
									<td bgcolor="#333366">
										<font color="#CCCCCC" size="+1">
											<b>
												<font color="#FFFFFF">Liste des
													messages d'erreur</font>
											</b>
										</font>
									</td>
								</tr>
								<tr>
						<td>
							<table border="0" cellpadding="5" align="left"
								cellspacing="0" >
								<tr>
									<td>
										<center>
											<font color="#333366">Message
												d'erreur</font>
										</center>
									</td>
									<td>
										<center>
											<font color="#333366">Nb.
												d'occurence</font>
										</center>
									</td>
								</tr>
								<xsl:apply-templates
									select="//violation[not(@rule=following::violation/attribute::rule)]"
									mode="comptagegeneral"/>
							</table>
						</td>
					</tr>
				</table>
				<p style="text-align:right" align="right">
					<xsl:element name="a"> <xsl:attribute name = "href"> 
						<xsl:text>#</xsl:text> </xsl:attribute> <xsl:text
						disable-output-escaping="yes">&lt;--</xsl:text> Sommaire</xsl:element>
				</p>
				<p/>
				<hr align="left" width="95%" size="1"/>
				<A name="detailerreurs">
					<h3>Détail des erreurs trouvées</h3>
				</A>
				<xsl:apply-templates
					select="//violation[not(@rule=following::violation/attribute::rule)]"
					mode="listefichiersparregle"/>
				<hr align="left" width="95%" size="1"/>
				<xsl:element name="a">
					<xsl:attribute name = "name">
						<xsl:text>listefichiers</xsl:text>
					</xsl:attribute>
					<h3>Liste des erreurs par fichier</h3>
				</xsl:element>
				<p/>
				<table width="90%" border="1" cellspacing="0" cellpadding="0"
					bordercolor="#333366" align="center">
								<tr>
									<td bgcolor="#333366">
										<font color="#CCCCCC" size="+1">
											<b>
												<font color="#FFFFFF">Liste des
													fichiers</font>
											</b>
										</font>
									</td>
								</tr>
					<tr>
						<td>
							<table border="0" cellpadding="5" align="left"
								cellspacing="0" >
								<tr>
									<td>
										<xsl:apply-templates
											mode="listefichiers"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<p style="text-align:right" align="right">
					<xsl:element name="a"> <xsl:attribute name = "href"> 
						<xsl:text>#</xsl:text> </xsl:attribute> <xsl:text
						disable-output-escaping="yes">&lt;--</xsl:text> Sommaire</xsl:element>
				</p>
				<p/>
				<xsl:apply-templates mode="comptage"/>
				<p/>
				<hr align="left" width="95%" size="1"/>
				<xsl:element name="a">
					<xsl:attribute name = "name">
						<xsl:text>detail</xsl:text>
					</xsl:attribute>
					<h3>Détail des erreurs par fichier</h3>
				</xsl:element>
				<p/>
				<xsl:apply-templates/>
			</BODY>
		</HTML>
	</xsl:template>
	<xsl:template match="file[violation]">
		<table width="90%" border="1" cellspacing="0" cellpadding="0" bordercolor="#333366"
			align="center">
						<tr bgcolor="#333366">
							<td>
								<table width="100%">
									<tr>
										<td>
											<xsl:element name="a">
												<xsl:attribute name = "name">
													<xsl:text>detail</xsl:text>
													<xsl:value-of select="@name"
														/>
												</xsl:attribute>
												<font size="+1">
													<b>
														<font color="#FFFFFF">
															Fichier 
															<xsl:value-of
															select="@name"/></font>
													</b>
												</font>
											</xsl:element>
										</td>
										<td align="right">
											<xsl:element name="a">
												<xsl:attribute name = "href">
													<xsl:text>#</xsl:text>
													<xsl:value-of select="@name"
														/>
												</xsl:attribute>
												<font color="#FFFFFF">voir
													résumé du fichier</font>
											</xsl:element>
										</td>
									</tr>
								</table>
							</td>
						</tr>
			<tr>
				<td>
					<table border="0" cellpadding="5" align="left"
						cellspacing="0" >
						<tr>
							<td>
								<center>
									<font color="#333366">N° ligne</font>
								</center>
							</td>
							<td>
								<center>
									<font color="#333366">Message d'erreur</font>
								</center>
							</td>
						</tr>
						<xsl:apply-templates select="violation"/>
					</table>
				</td>
			</tr>
		</table>
		<p style="text-align:right" align="right">
			<a href="#listefichiers"><xsl:text disable-output-escaping="yes">&lt;
				--</xsl:text> liste des fichier</a>
		</p>
		<p/>
	</xsl:template>
	<xsl:template match="violation">
		<tr>
			<td>
				<xsl:value-of select="@line"/>
			</td>
			<td>
				<xsl:value-of select="text()"/>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="file[violation]" mode="listefichiers">
		<p>
			<xsl:element name="a">
				<xsl:attribute name = "href">
					<xsl:text>#</xsl:text>
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:value-of select="@name"/>
			</xsl:element>
		</p>
	</xsl:template>
	<xsl:template match="file[violation]" mode="comptage">
		<table width="90%" border="1" cellspacing="0" cellpadding="0" bordercolor="#333366"
			align="center">
						<tr bgcolor="#333366">
							<td>
								<xsl:element name="a">
									<xsl:attribute name = "name">
										<xsl:value-of select="@name"/>
									</xsl:attribute>
									<font size="+1">
										<b>
											<font color="#FFFFFF">Fichier 
												<xsl:value-of select="@name"/></font>
										</b>
									</font>
								</xsl:element>
							</td>
							<td align="right">
								<xsl:element name="a">
									<xsl:attribute name = "href">
										<xsl:text>#detail</xsl:text>
										<xsl:value-of select="@name"/>
									</xsl:attribute>
									<font color="#FFFFFF">voir détails</font>
								</xsl:element>
							</td>
						</tr>
			<tr>
				<td colspan="2">
					<table border="0" cellpadding="5" align="left"
						cellspacing="0" >
						<tr>
							<td>
								<center>
									<font color="#333366">Message d'erreur</font>
								</center>
							</td>
							<td>
								<center>
									<font color="#333366">Nb. d'occurence</font>
								</center>
							</td>
						</tr>
						<xsl:apply-templates
							select="violation[not(@rule=following-sibling::violation/attribute::rule)]"
							mode="comptage"/>
					</table>
				</td>
			</tr>
		</table>
		<p style="text-align:right" align="right">
			<a href="#listefichiers"> <xsl:text disable-output-escaping="yes">&lt;
				--</xsl:text> liste des fichier</a>
		</p>
		<p/>
	</xsl:template>
	<xsl:template match="violation" mode="comptage">
		<tr>
			<td>
				<xsl:value-of select="text()"/>
			</td>
			<td>
				<xsl:variable name="message" select="@rule"/>
				<xsl:value-of
					select="count(preceding-sibling::violation[@rule=$message])+1"
					/>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="violation" mode="comptagegeneral">
		<tr>
			<td>
				<xsl:element name="a">
					<xsl:attribute name = "href">
						<xsl:text>#</xsl:text>
						<xsl:value-of select="@rule" />
					</xsl:attribute>
					<xsl:value-of select="text()"/>
				</xsl:element>
			</td>
			<td>
				<xsl:variable name="message" select="@rule"/>
				<xsl:value-of
					select="count(preceding::violation[@rule=$message])+1" />
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="violation" mode="listefichiersparregle">
		<table width="90%" border="1" cellspacing="0" cellpadding="0" bordercolor="#333366"
			align="center">
						<tr bgcolor="#333366">
							<td>
								<xsl:element name="a">
									<xsl:attribute name = "name">
										<xsl:value-of
											select="@rule"
											/>
									</xsl:attribute>
									<font size="+1">
										<b>
											<font color="#FFFFFF">
												<xsl:value-of select="text()" />
											</font>
										</b>
									</font>
								</xsl:element>
							</td>
							<td align="right">
								<a href="#listeerreurs">
									<font color="#FFFFFF">listes des erreurs</font>
								</a>
							</td>
						</tr>
			<tr>
				<td colspan="2">
					<table border="0" cellpadding="5" align="left"
						cellspacing="0" >
						<tr>
							<td>
								<center>
									<font color="#333366">Fichier</font>
								</center>
							</td>
							<td>
								<center>
									<font color="#333366">Nb. d'occurence</font>
								</center>
							</td>
						</tr>
						<xsl:variable name="message" select="@rule"/>
						<xsl:apply-templates
							select="//file[violation[@rule=$message]]"
							mode="listefichiersparregle">
							<xsl:with-param name="message" select="@rule">
							</xsl:with-param>
						</xsl:apply-templates>
					</table>
				</td>
			</tr>
		</table>
		<p style="text-align:right" align="right">
			<a href="#listeerreurs"><xsl:text disable-output-escaping="yes">&lt;
				--</xsl:text> liste des erreurs</a>
		</p>
		<p/>
	</xsl:template>
	<xsl:template match="file" mode="listefichiersparregle">
		<xsl:param name="message">
		</xsl:param>
		<tr>
			<td>
				<xsl:element name="a">
					<xsl:attribute name = "href">
						<xsl:text>#</xsl:text>
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:value-of select="@name"/>
				</xsl:element>
			</td>
			<td>
				<xsl:value-of select="count(violation[@rule=$message])"/>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>
