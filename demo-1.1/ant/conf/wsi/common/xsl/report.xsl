<?xml version="1.0" encoding="UTF-8"?>

<!--
  Formatting for WS-I Profile Conformance Report generated by the Testing Tools
 
	Copyright (c) 2002-2004 by The Web Services-Interoperability Organization (WS-I) and 
	Certain of its Members. All Rights Reserved.
	
	Notice
	The material contained herein is not a license, either expressly or impliedly, to any 
	intellectual property owned or controlled by any of the authors or developers of this 
	material or WS-I. The material contained herein is provided on an "AS IS" basis and to 
	the maximum extent permitted by applicable law, this material is provided AS IS AND WITH 
	ALL FAULTS, and the authors and developers of this material and WS-I hereby disclaim all 
	other warranties and conditions, either express, implied or statutory, including, but not 
	limited to, any (if any) implied warranties, duties or conditions of  merchantability, 
	of fitness for a particular purpose, of accuracy or completeness of responses, of results, 
	of workmanlike effort, of lack of viruses, and of lack of negligence. ALSO, THERE IS NO 
	WARRANTY OR CONDITION OF TITLE, QUIET ENJOYMENT, QUIET POSSESSION, CORRESPONDENCE TO 
	DESCRIPTION OR NON-INFRINGEMENT WITH REGARD TO THIS MATERIAL.
	
	IN NO EVENT WILL ANY AUTHOR OR DEVELOPER OF THIS MATERIAL OR WS-I BE LIABLE TO ANY OTHER 
	PARTY FOR THE COST OF PROCURING SUBSTITUTE GOODS OR SERVICES, LOST PROFITS, LOSS OF USE, 
	LOSS OF DATA, OR ANY INCIDENTAL, CONSEQUENTIAL, DIRECT, INDIRECT, OR SPECIAL DAMAGES 
	WHETHER UNDER CONTRACT, TORT, WARRANTY, OR OTHERWISE, ARISING IN ANY WAY OUT OF THIS OR 
	ANY OTHER AGREEMENT RELATING TO THIS MATERIAL, WHETHER OR NOT SUCH PARTY HAD ADVANCE 
	NOTICE OF THE POSSIBILITY OF SUCH DAMAGES.
	
	WS-I License Information
	Use of this WS-I Material is governed by the WS-I Test License and other licenses.  Information on these 
	licenses are contained in the README.txt and ReleaseNotes.txt files.  By downloading this file, you agree 
	to the terms of these licenses.   

	How To Provide Feedback
	The Web Services-Interoperability Organization (WS-I) would like to receive input, 
	suggestions and other feedback ("Feedback") on this work from a wide variety of 
	industry participants to improve its quality over time. 
	
	By sending email, or otherwise communicating with WS-I, you (on behalf of yourself if 
	you are an individual, and your company if you are providing Feedback on behalf of the 
	company) will be deemed to have granted to WS-I, the members of WS-I, and other parties 
	that have access to your Feedback, a non-exclusive, non-transferable, worldwide, perpetual, 
	irrevocable, royalty-free license to use, disclose, copy, license, modify, sublicense or 
	otherwise distribute and exploit in any manner whatsoever the Feedback you provide regarding 
	the work. You acknowledge that you have no expectation of confidentiality with respect to 
	any Feedback you provide. You represent and warrant that you have rights to provide this 
	Feedback, and if you are providing Feedback on behalf of a company, you represent and warrant 
	that you have the rights to provide Feedback on behalf of your company. You also acknowledge 
	that WS-I is not required to review, discuss, use, consider or in any way incorporate your 
	Feedback into future versions of its work. If WS-I does incorporate some or all of your 
	Feedback in a future version of the work, it may, but is not obligated to include your name 
	(or, if you are identified as acting on behalf of your company, the name of your company) on 
	a list of contributors to the work. If the foregoing is not acceptable to you and any company 
	on whose behalf you are acting, please do not provide any Feedback.
	
	WS-I members should direct feedback on this document to wsi_testing@lists.ws-i.org; 
    non-members should direct feedback to wsi-tools@ws-i.org. 

  Copyright (c) 2002-2004 IBM Corporation.  All rights reserved.

  @author Peter Brittenham, peterbr@us.ibm.com
  @version 1.0
  
-->

<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:wsi-common="http://www.ws-i.org/testing/2003/03/common/"
	xmlns:wsi-report="http://www.ws-i.org/testing/2004/07/report/"
	xmlns:wsi-profile="http://www.ws-i.org/testing/2003/03/profile/"
	xmlns:wsi-analyzerConfig="http://www.ws-i.org/testing/2004/07/analyzerConfig/"
	xmlns:wsi-monConfig="http://www.ws-i.org/testing/2003/03/monitorConfig/"
	xmlns:wsi-log="http://www.ws-i.org/testing/2003/03/log/">
<xsl:import href="common.xsl"/>

<xsl:output method="html" indent="yes"/>
<xsl:preserve-space elements="wsi-report:analyzerFailure"/>
<xsl:strip-space elements="wsi-log:message wsi-log:httpHeaders" />

  <!-- FIX: Reference file on WS-I site when it is available. -->
  <!--  Modif CLARA : on veut des liens vers un fichier HTML sans chemin et pas vers un fichier xml 
<xsl:variable name="tadURL" select="substring-after(/wsi-report:report/wsi-report:analyzer/wsi-analyzerConfig:configuration/wsi-analyzerConfig:testAssertionsFile,'/')"/>
-->

<xsl:variable name="tadURL_tempo">
	<xsl:call-template name="basename">
		<xsl:with-param name="path"><xsl:value-of select="/wsi-report:report/wsi-report:analyzer/wsi-analyzerConfig:configuration/wsi-analyzerConfig:testAssertionsFile"/></xsl:with-param>
	</xsl:call-template>
</xsl:variable>

<xsl:variable name="tadURL" select="concat($tadURL_tempo,'.html')" />

<xsl:template name="basename">
  <xsl:param name="path"/>
  <xsl:choose>
     <xsl:when test="contains($path, '\')">
        <xsl:call-template name="basename">
           <xsl:with-param name="path"><xsl:value-of select="substring-after($path, '\')"/></xsl:with-param>
        </xsl:call-template>
     </xsl:when>
     <xsl:when test="contains($path, '/')">
        <xsl:call-template name="basename">
           <xsl:with-param name="path"><xsl:value-of select="substring-after($path, '/')"/></xsl:with-param>
        </xsl:call-template>
     </xsl:when>
     <xsl:when test="contains($path, '.')">
        <xsl:call-template name="basename">
           <xsl:with-param name="path"><xsl:value-of select="substring-before($path, '.')"/></xsl:with-param>
        </xsl:call-template>
     </xsl:when>
     <xsl:otherwise>
        <xsl:value-of select="$path"/>
     </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- Modif Clara
Le script xsl fourni par le WS-I ne fonctionne pas avec le parseur de Java 5
<xsl:key name="id" match="@id" use="."/>
-->
<xsl:key name="id" match="wsi-report:assertionResult" use="@id"/>
<xsl:key name="assertionID" match="wsi-report:entry/wsi-report:assertionResult" use="@id"/>

<xsl:template match="/">
<html>
	<!-- <link type="text/css" rel="stylesheet" href="http://www.ws-i.org/styles.css" /> -->
	<head>
    <title>WS-I Profile Conformance Report</title>
    <style type="text/css">
       BODY {
        border-top-style : none;
        border-left-style : none;
        border-right-style : none;
        border-bottom-style : none;
        font-family : Arial,sans-serif;
      }
      H1 {
        color : #336699;
      }
      H2 {
        background-color : #336699;
        PADDING-BOTTOM: 1px;
        PADDING-LEFT: 4px;
        PADDING-RIGHT: 4px;
        PADDING-TOP: 1px;
        color : white;
      }
      H3 {
        background-color : #7ca8da;
        padding-left : 6px;
        padding-right : 6px;
        padding-top : 2px;
        padding-bottom : 2px;
      }
      TABLE {
        margin-left : 1em;
        margin-right : 1em;
      }    
      .contents1 {
        margin-left : 2em;
      }
      .contents2 {
        margin-left : 2.2em;
      }
      .data-type {
        margin-left : 1em;
        margin-right : 1em;
      }    
      .data-content {
        margin-left : 1em;
        margin-right : 1em;
      }    
    </style>
	</head>

	<body>
    <a name="top"/>
    <img align="right" src="http://www.ws-i.org/images/WS-I-logo.gif"/>
    <h1>WS-I Profile Conformance Report</h1>
		<xsl:apply-templates />
	</body>
</html>
</xsl:template>

<xsl:template match="wsi-report:description" >
</xsl:template>

<xsl:template match="wsi-report:report" >
  <table>
  <tr><td>
  <b>Report:</b>
  </td><td>
  <xsl:value-of select="@name" />
  </td></tr>
  <tr><td>
  <b>Timestamp:</b>
	</td><td>
  <xsl:value-of select="@timestamp" />
	</td></tr>
  </table>
  
  <xsl:call-template name="copyright"/>

  <p><xsl:value-of select="wsi-report:description" /></p>
  <hr style="color : black;"/>
  <xsl:apply-templates select="wsi-report:analyzer"/>
  <br/>
  <hr style="color : black;"/>
  <xsl:apply-templates select="wsi-report:summary"/>
  <br/>
  <hr style="color : black;"/>
  <br/>

  <a name="artifacts"><h2>Artifacts</h2></a>
  <ol>
  <xsl:for-each select="wsi-report:artifact">
    <xsl:variable name="linkName1" select="@type"/>
    <li><a href="#artifact-{$linkName1}"><xsl:value-of select="$linkName1"/></a></li>
  </xsl:for-each>
  </ol>
  
  <p/>
  <hr style="color : black;"/>
  <br/>
  
<!-- REMOVE:
  <h2>Assertion Entry List</h2>
  <ol>
  <xsl:for-each select="wsi-report:entry">
    <xsl:sort select="@referencI"/>
    <xsl:choose>
      <xsl:when test="@type='logEntry'">
        <xsl:variable name="linkName1" select="@type"/>
        <xsl:variable name="linkName2"  select="@logEntryID"/>
        <li><a href="#{$linkName1}{$linkName2}"><xsl:value-of select="$linkName1"/><xsl:text> </xsl:text><xsl:value-of select="$linkName2"/></a></li>
      </xsl:when>
      <xsl:otherwise>
        <xsl:variable name="linkName1" select="@type"/>
        <li><a href="#{$linkName1}"><xsl:value-of select="$linkName1"/></a></li>
      </xsl:otherwise>      
    </xsl:choose>
  </xsl:for-each>
  </ol>
  <hr style="color : black;"/>
  <xsl:apply-templates select="wsi-report:entry">
    <xsl:sort select="@logEntryID" data-type="number"/>
  </xsl:apply-templates>
-->
  <xsl:apply-templates select="wsi-report:artifact"/>
  <xsl:apply-templates select="wsi-report:analyzerFailure"/>
  
	<xsl:call-template name="notice"/>
</xsl:template>
  
<xsl:template match="wsi-report:analyzer" >
  <h2>Analyzer Tool Information</h2>
  <table cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <tr bgcolor="#ffffff"><td>
  <b>Version</b>
	</td><td>
  <xsl:value-of select="@version" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Release Date</b>
	</td><td>
  <xsl:value-of select="@releaseDate" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Implementer Name</b>
	</td><td>
  <xsl:value-of select="wsi-report:implementer/@name" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Location</b>
	</td><td>
  <xsl:value-of select="wsi-report:implementer/@location" />
	</td></tr>
  </table>
  <xsl:apply-templates />
</xsl:template>
  
<xsl:template match="wsi-report:environment" >
  <h3>Analyzer Runtime Environment Information</h3>
  <table cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <tr bgcolor="#ffffff"><td>
  <b>Runtime Name</b>
	</td><td>
  <xsl:value-of select="wsi-report:runtime/@name" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Runtime Version</b>
	</td><td>
  <xsl:value-of select="wsi-report:runtime/@version" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Operating System Name</b>
	</td><td>
  <xsl:value-of select="wsi-report:operatingSystem/@name" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Operating System Version</b>
	</td><td>
  <xsl:value-of select="wsi-report:operatingSystem/@version" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>XML Parser Name</b>
	</td><td>
  <xsl:value-of select="wsi-report:xmlParser/@name" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>XML Parser Version</b>
	</td><td>
  <xsl:value-of select="wsi-report:xmlParser/@version" />
	</td></tr>
  </table>
</xsl:template>  

<xsl:template match="wsi-analyzerConfig:configuration" >
  <h3>Analyzer Configuration Options</h3>
  <table cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <tr bgcolor="#ffffff"><td>
  <b>Verbose</b>
  </td><td>
  <xsl:value-of select="wsi-analyzerConfig:verbose" />
  </td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Type of Results to Show</b>
	</td><td>
  <xsl:value-of select="wsi-analyzerConfig:assertionResults/@type" />
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Show Message Entry</b>
	</td><td>
  <xsl:choose>
    <xsl:when test="wsi-analyzerConfig:assertionResults/@messageEntry">    
      <xsl:value-of select="wsi-analyzerConfig:assertionResults/@messageEntry"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>false</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Show Assertion Description</b>
	</td><td>
  <xsl:choose>
    <xsl:when test="wsi-analyzerConfig:assertionResults/@assertionDescription">    
      <xsl:value-of select="wsi-analyzerConfig:assertionResults/@assertionDescription"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>false</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Show Failure Message</b>
	</td><td>
  <xsl:choose>
    <xsl:when test="wsi-analyzerConfig:assertionResults/@failureMessage">    
      <xsl:value-of select="wsi-analyzerConfig:assertionResults/@failureMessage"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>false</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Show Failure Detail Message</b>
	</td><td>
  <xsl:choose>
    <xsl:when test="wsi-analyzerConfig:assertionResults/@failureDetail">    
      <xsl:value-of select="wsi-analyzerConfig:assertionResults/@failureDetail"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>true</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
	</td></tr>
	<!-- REMOVE:
  <tr bgcolor="#ffffff"><td>
  <b>Show Warning Message</b>
	</td><td>
  <xsl:choose>
    <xsl:when test="wsi-analyzerConfig:assertionResults/@warningMessage">    
      <xsl:value-of select="wsi-analyzerConfig:assertionResults/@warningMessage"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>true</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
	</td></tr>
	-->
  <tr bgcolor="#ffffff"><td>
  <b>Test Assertions Document</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:testAssertionsFile"/>
	</td></tr>
	<xsl:choose>
	<xsl:when test="wsi-analyzerConfig:logFile">
  <tr bgcolor="#ffffff"><td>
  <b>Message Log File</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:logFile"/>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Correlation Type</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:logFile/@correlationType"/>
	</td></tr>
	</xsl:when>
	<xsl:otherwise>
  <tr bgcolor="#ffffff"><td>
  <b>Message Log File</b>
	</td><td>
  [Not specified]
	</td></tr>
	</xsl:otherwise>
	</xsl:choose>
	<xsl:choose>
	<xsl:when test="wsi-analyzerConfig:wsdlReference">
  <tr bgcolor="#ffffff"><td>
  <b>WSDL Element</b>
	</td><td>
  </td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Name</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:wsdlElement"/>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Type</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:wsdlElement/@type"/>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Namespace</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:wsdlElement/@namespace"/>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Parent Element Name</b>
	</td><td>
	  <xsl:choose>
	  <xsl:when test="wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:wsdlElement/@parentElementName">
      <xsl:value-of select="wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:wsdlElement/@parentElementName"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>[Not specified]</xsl:text>
    </xsl:otherwise>
    </xsl:choose>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>WSDL Document</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:wsdlURI"/>
	</td></tr>
  <xsl:if test="wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:serviceLocation">
  <tr bgcolor="#ffffff"><td>
  <b>Service Location</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:serviceLocation"/>
	</td></tr>
	</xsl:if>
	</xsl:when>
	<xsl:when test="wsi-analyzerConfig:uddiReference">
  <tr bgcolor="#ffffff"><td>
  <b>UDDI Key</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:uddiReference/wsi-analyzerConfig:uddiKey"/>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>UDDI Key Type</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:uddiReference/wsi-analyzerConfig:uddiKey/@type"/>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <b>Inquiry URL</b>
	</td><td>
    <xsl:value-of select="wsi-analyzerConfig:uddiReference/wsi-analyzerConfig:inquiryURL"/>
	</td></tr>
	<!-- ADD: Need to add support for wsdlElement and serviceLocation for V1.0 -->
	</xsl:when>
	</xsl:choose>
  </table>
</xsl:template>

<xsl:template match="wsi-report:summary" >
  <h2>Summary</h2>
  <table width="90%" cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <col span="1" width="20%"/>
  <tbody>
  <tr bgcolor="#ffffff"><td>
  <b>Result</b>
  </td>
  <xsl:choose>
    <xsl:when test="@result='failed'">
      <td style="color : {$failedColor};">
      <b><xsl:value-of select="@result" /></b>
      </td>
    </xsl:when>
    <xsl:otherwise>
      <td style="color : {$passedColor};">
      <b><xsl:value-of select="@result" /></b>
      </td>
    </xsl:otherwise>
  </xsl:choose>
  </tr>
  </tbody>
  </table>
  <br/>
  <p>
  <b>Artifact Targets Analyzed:</b>
  The summary result applies to the following artifact targets which were specified in the analyzer 
  configuration file.
  </p>
  <table width="90%" cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <col span="1" width="20%"/>
  <tbody>
  <xsl:if test="//wsi-analyzerConfig:uddiReference">
  <tr bgcolor="#ffffff">
  <td>
  <b>Discovery</b>
  </td>
	<td>
  <xsl:value-of select="//wsi-analyzerConfig:uddiReference/wsi-analyzerConfig:uddiKey/@type"/>
  <xsl:text>=</xsl:text>
  <xsl:value-of select="//wsi-analyzerConfig:uddiReference/wsi-analyzerConfig:uddiKey"/>  
	</td>
	</tr>  
	</xsl:if>
  <xsl:if test="//wsi-analyzerConfig:wsdlReference">
  <tr bgcolor="#ffffff">
  <td>
  <b>Description</b>
  </td>
	<td>
  <xsl:value-of select="//wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:wsdlElement/@type"/>
  <xsl:text>=</xsl:text>
  <xsl:value-of select="//wsi-analyzerConfig:wsdlReference/wsi-analyzerConfig:wsdlElement"/>  
	</td>
	</tr>  
	</xsl:if>
  <xsl:if test="//wsi-analyzerConfig:logFile">
  <tr bgcolor="#ffffff">
  <td>
  <b>Message</b>
  </td>
	<td>
  <xsl:value-of select="//wsi-analyzerConfig:logFile"/>
	</td>
	</tr>  
	</xsl:if>
  </tbody>
  </table>
  
  <xsl:apply-templates />
</xsl:template>

<xsl:template match="wsi-report:artifact" >
  <xsl:variable name="artifactType" select="@type"/>
  <h2><a name="artifact-{$artifactType}">Artifact: <xsl:value-of select="$artifactType"/></a></h2>
	<xsl:if test="wsi-report:artifactReference">
  <p>
  <b>Artifact Reference:</b>
  </p>
  <table cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <tr bgcolor="{$titleRowColor}"><td>
  <b>Timestamp</b>
  </td></tr>
  <tr bgcolor="#ffffff"><td>
  <xsl:value-of select="wsi-report:artifactReference/@timestamp" />
	</td></tr>
	<!-- ADD: Optional monitor comment
  <tr bgcolor="#ffffff"><td>
  <b>Monitor Config Comment</b>
	</td><td>
  <xsl:choose>
    <xsl:when test="wsi-report:log/wsi-monConfig:comment">    
      <xsl:value-of select="wsi-report:log/wsi-monConfig:comment" />
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>[Not available in report]</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
	</td></tr>
	-->
	</table>
	</xsl:if>

  <xsl:choose>
  <xsl:when test="wsi-report:entry">

  <p>
  <b>Assertion Result Summary:</b>
  </p>
  <table cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <tr bgcolor="{$titleRowColor}"><td>
  <b>Assertion ID</b> 
  </td><td>
  <b>Passed</b>
  </td><td>
  <b>Failed</b>
  </td><td>
  <b>Prerequsite Failed</b>
  </td><td>
  <b>Warning</b>
  </td><td>
  <b>Not Applicable</b>
  </td><td>
  <b>Missing Input</b>
  </td></tr>
  <!-- Modif Clara
  <xsl:for-each select="wsi-report:entry/wsi-report:assertionResult/@id[generate-id()=generate-id(key('id', .))]">
  <xsl:sort select="." data-type="text"/>
  -->
  <xsl:for-each select="wsi-report:entry/wsi-report:assertionResult[generate-id(.)=generate-id(key('id', @id)[1])]">
  <xsl:sort select="@id" data-type="text"/>
	<!-- Get all of the summary result counts -->
  <xsl:variable name="passedCount">
  	<xsl:call-template name="getCount">
  <!-- Modif Clara
  on remplace current() par @id dans tout le bloc
  		<xsl:with-param name="assertionID"><xsl:value-of select="current()"/></xsl:with-param>
  -->
  		<xsl:with-param name="assertionID"><xsl:value-of select="@id"/></xsl:with-param>
  		<xsl:with-param name="result"><xsl:value-of select="'passed'"/></xsl:with-param>
  	</xsl:call-template>
  </xsl:variable>
  <xsl:variable name="failedCount">
  	<xsl:call-template name="getCount">
  		<xsl:with-param name="assertionID"><xsl:value-of select="@id"/></xsl:with-param>
  		<xsl:with-param name="result"><xsl:value-of select="'failed'"/></xsl:with-param>
  	</xsl:call-template>
  </xsl:variable>
   <xsl:variable name="prereqFailedCount">
  	<xsl:call-template name="getCount">
  		<xsl:with-param name="assertionID"><xsl:value-of select="@id"/></xsl:with-param>
  		<xsl:with-param name="result"><xsl:value-of select="'prereqFailed'"/></xsl:with-param>
  	</xsl:call-template>
  </xsl:variable>
  <xsl:variable name="warningCount">
  	<xsl:call-template name="getCount">
  		<xsl:with-param name="assertionID"><xsl:value-of select="@id"/></xsl:with-param>
  		<xsl:with-param name="result"><xsl:value-of select="'warning'"/></xsl:with-param>
  	</xsl:call-template>
  </xsl:variable>
  <xsl:variable name="naCount">
  	<xsl:call-template name="getCount">
  		<xsl:with-param name="assertionID"><xsl:value-of select="@id"/></xsl:with-param>
  		<xsl:with-param name="result"><xsl:value-of select="'notApplicable'"/></xsl:with-param>
  	</xsl:call-template>
  </xsl:variable>
  <xsl:variable name="miCount">
  	<xsl:call-template name="getCount">
  		<xsl:with-param name="assertionID"><xsl:value-of select="@id"/></xsl:with-param>
  		<xsl:with-param name="result"><xsl:value-of select="'missingInput'"/></xsl:with-param>
  	</xsl:call-template>
  </xsl:variable>

  <tr bgcolor="#ffffff">
  <td>
  <xsl:choose>
  <xsl:when test="$failedCount > 0">
    <b style="color : {$failedColor};"><xsl:value-of select="@id" /></b>
  </xsl:when>
  <xsl:when test="$prereqFailedCount > 0">
    <b style="color : {$prereqFailedColor}"><xsl:value-of select="@id" /></b>
  </xsl:when>
  <xsl:when test="$warningCount > 0">
    <b style="color : {$warningColor}"><xsl:value-of select="@id" /></b>
  </xsl:when>
  <xsl:when test="$passedCount > 0">
    <b style="color : {$passedColor}"><xsl:value-of select="@id" /></b>
  </xsl:when>
  <xsl:when test="$naCount > 0">
    <b style="color : {$notApplicableColor}"><xsl:value-of select="@id" /></b>
  </xsl:when>
  <xsl:otherwise>
	  <b style="color : {$missingInputColor}"><xsl:value-of select="@id" /></b>
	</xsl:otherwise>
	</xsl:choose>
	<!-- Is this needed?
  [<a href="{$tadURL}{current()}">Description</a>]
  -->
  </td>

  <xsl:choose>
  <xsl:when test="$passedCount > 0">
    <td style="color : {$passedColor};">
    <b><xsl:value-of select="$passedCount" /></b>
    </td>
  </xsl:when>
  <xsl:otherwise>
	  <td>
	  <xsl:value-of select="$passedCount"/>
	  </td>
	</xsl:otherwise>
	</xsl:choose>

  <xsl:choose>
  <xsl:when test="$failedCount > 0">
    <td style="color : {$failedColor};">
    <b><xsl:value-of select="$failedCount" /></b>
    </td>
  </xsl:when>
  <xsl:otherwise>
	  <td>
	  <xsl:value-of select="$failedCount"/>
	  </td>
	</xsl:otherwise>
	</xsl:choose>

  <xsl:choose>
  <xsl:when test="$prereqFailedCount > 0">
    <td style="color : {$prereqFailedColor};">
    <b><xsl:value-of select="$prereqFailedCount" /></b>
    </td>
  </xsl:when>
  <xsl:otherwise>
	  <td>
	  <xsl:value-of select="$prereqFailedCount"/>
	  </td>
	</xsl:otherwise>
	</xsl:choose>

  <xsl:choose>
  <xsl:when test="$warningCount > 0">
    <td style="color : {$warningColor};">
    <b><xsl:value-of select="$warningCount" /></b>
    </td>
  </xsl:when>
  <xsl:otherwise>
	  <td>
	  <xsl:value-of select="$warningCount"/>
	  </td>
	</xsl:otherwise>
	</xsl:choose>

  <xsl:choose>
  <xsl:when test="$naCount > 0">
    <td style="color : {$notApplicableColor};">
    <b><xsl:value-of select="$naCount" /></b>
    </td>
  </xsl:when>
  <xsl:otherwise>
	  <td>
	  <xsl:value-of select="$naCount"/>
	  </td>
	</xsl:otherwise>
	</xsl:choose>


  <xsl:choose>
  <xsl:when test="$miCount > 0">
    <td style="color : {$missingInputColor};">
    <b><xsl:text>X</xsl:text></b>
    </td>
  </xsl:when>
  <xsl:otherwise>
	  <td>
	  </td>
	</xsl:otherwise>
	</xsl:choose>
  </tr>
  </xsl:for-each>
  </table>
  
  <xsl:if test="wsi-report:entry/@type!=''">
  <p>
  <b>Entry List:</b>
  </p>
  <table cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <tr bgcolor="{$titleRowColor}"><td>
  <b>Reference ID</b>
  </td><td>
  <b>Type</b>
  </td></tr>
  <!-- ADD: Set sort data type based on artifact type
  <xsl:choose>
    <xsl:when test="$typeName='message'">
      <xsl:variable name="dataType" select="'number'"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="dataType" select="'text'"/>
    </xsl:otherwise>      
  </xsl:choose>
  -->
  <xsl:for-each select="wsi-report:entry">
    <!--<xsl:sort select="@referenceID" data-type="number"/>-->
    <tr bgcolor="#ffffff"><td>
    <xsl:variable name="referenceID" select="@referenceID"/>
    <xsl:choose>
      <xsl:when test="@referenceID">
        <a href="#entry-{$referenceID}"><xsl:value-of select="$referenceID"/></a>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>[Not specified]</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    </td><td>
    <xsl:value-of select="@type"/>
    </td>
    </tr>
  </xsl:for-each>
  </table>
  </xsl:if>
  
  <p><a href="#artifacts">Return to list of artifacts.</a></p>
  <p/>
	
  <hr style="color : black;"/>
  <xsl:apply-templates select="wsi-report:entry">
    <!--<xsl:sort select="@referenceID" data-type="number"/>-->
  </xsl:apply-templates>
  </xsl:when>
  <xsl:otherwise>
  <p><b>This artifact was not processed by the analyzer.</b></p>
  <p><a href="#artifacts">Return to list of artifacts.</a></p>
  <p/>
  </xsl:otherwise>
  </xsl:choose>
  <hr style="color : black;"/>
</xsl:template>

<xsl:template name="getCount">
	<xsl:param name="assertionID"/>
	<xsl:param name="result"/>
	<xsl:value-of select="count(key('assertionID', $assertionID)[@result = $result])"/>
</xsl:template>

<xsl:template match="wsi-report:entry" >
  <h3>
  <xsl:choose>
  <xsl:when test="@referenceID">
    <xsl:variable name="linkName" select="@referenceID"/>
    <a name="entry-{$linkName}">Entry: <xsl:value-of select="$linkName"/></a>
  </xsl:when>
  <xsl:otherwise>
    <xsl:variable name="linkName" select="@type"/>
    <a name="entry-{$linkName}">Entry: <xsl:value-of select="$linkName"/></a>
  </xsl:otherwise>
  </xsl:choose>
  </h3>

  <xsl:if test="wsi-report:entry/@type!=''">
  <table cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <tr bgcolor="{$titleRowColor}"><td>
  <b>Reference ID</b>
	</td><td>
  <b>Type</b>
	</td></tr>
  <tr bgcolor="#ffffff"><td>
  <xsl:value-of select="@referenceID" />
	</td><td>
  <xsl:value-of select="@type" />
	</td></tr>
  </table>
	</xsl:if>

  <p/>
  <xsl:choose>
  <xsl:when test="wsi-report:assertionResult">
	  <xsl:apply-templates />
	</xsl:when>
	<xsl:otherwise>
		<b>NOTE: There were no test assertion results for this entry.</b>
	</xsl:otherwise>
	</xsl:choose>
  <p><a href="#artifacts">Return to list of artifacts.</a></p>
  <p/>
</xsl:template>

<xsl:template match="wsi-log:messageEntry">
  <h4>Message Entry:</h4>
    <table width="90%" cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
    <tr bgcolor="#ffffff"><td>
    <b>Type</b>
	  </td><td>
    <xsl:value-of select="@type" />
  	</td></tr>
    <tr bgcolor="#ffffff"><td>
    <b>Conversation ID</b>
	  </td><td>
    <xsl:value-of select="@conversationID" />
  	</td></tr>
    <tr bgcolor="#ffffff"><td>
    <b>Sender Host and Port</b>
	  </td><td>
    <xsl:value-of select="wsi-log:senderHostAndPort" />
  	</td></tr>
    <tr bgcolor="#ffffff"><td>
    <b>Receiver Host and Port</b>
	  </td><td>
    <xsl:value-of select="wsi-log:receiverHostAndPort" />
  	</td></tr>
    <tr bgcolor="#ffffff"><td>
    <b>HTTP Headers</b>
	  </td><td>
    <pre><xsl:value-of select="wsi-log:httpHeaders" /></pre>
  	</td></tr>
    <tr bgcolor="#ffffff"><td>
    <b>Message</b>
	  </td><td>
    <xsl:variable name="messageContent" select="wsi-log:messageContent"/>
    <xsl:choose>
    <xsl:when test="contains($messageContent, '&#xD;')">
    <pre><xsl:value-of select="wsi-log:messageContent" /></pre>
    </xsl:when>
    <xsl:when test="contains($messageContent, '&#xA;')">
    <pre><xsl:value-of select="wsi-log:messageContent" /></pre>
    </xsl:when>
    <xsl:otherwise>
    <text><xsl:value-of select="wsi-log:messageContent" /></text>
    </xsl:otherwise>
    </xsl:choose>
  	</td></tr>
    </table>
</xsl:template>

<xsl:template match="wsi-report:assertionResult" >
  <xsl:variable name="linkName" select="@id"/>
  <!-- FIX: Reference file on WS-I site when it is available. -->
	<a name="#{$linkName}"></a><h4>Assertion: <a href="{$tadURL}#{$linkName}">
	<xsl:value-of select="$linkName"/>
	</a></h4>
  <table width="90%" cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <col span="1" width="20%"/>
  <tbody>
  <tr bgcolor="#ffffff"><td>
  <b>Result</b>
  </td>
  <xsl:choose>
    <xsl:when test="@result='failed'">
      <td style="color : {$failedColor};">
      <b><xsl:value-of select="@result" /></b>
      </td>
    </xsl:when>    
    <xsl:when test="@result='prereqFailed'">
      <td style="color : {$prereqFailedColor};">
      <b><xsl:value-of select="@result" /></b>
      </td>
    </xsl:when>
    <xsl:when test="@result='passed'">
      <td style="color : {$passedColor};">
      <b><xsl:value-of select="@result" /></b>
      </td>
    </xsl:when>
    <xsl:when test="@result='warning'">
      <td style="color : {$warningColor};">
      <b><xsl:value-of select="@result" /></b>
      </td>
    </xsl:when>
    <xsl:when test="@result='notApplicable'">
      <td style="color : {$notApplicableColor};">
      <b><xsl:value-of select="@result" /></b>
      </td>
    </xsl:when>
    <xsl:otherwise>
      <td style="color : {$missingInputColor};">
      <b><xsl:value-of select="@result" /></b>
      </td>
    </xsl:otherwise>
  </xsl:choose>
  </tr>
  <xsl:if test="wsi-report:assertionDescription">
    <tr bgcolor="#ffffff"><td>
    <b>Assertion Description</b>
    </td><td>
    <xsl:value-of select="wsi-report:assertionDescription" />
    </td></tr>
  </xsl:if>
  <xsl:if test="wsi-report:failureMessage">
    <tr bgcolor="#ffffff"><td>
    <b>Failure Message</b>
    </td><td>
    <xsl:value-of select="wsi-report:failureMessage" />
    </td></tr>
  </xsl:if>
  <xsl:for-each select="wsi-report:failureDetail">
    <tr bgcolor="#ffffff"><td>
    <b>Failure Detail Message</b>
    </td><td>

    <xsl:variable name="singleFailureDetail" select="current()"/>
    <xsl:choose>
    <xsl:when test="contains($singleFailureDetail, '&#xD;')">
    <pre><xsl:value-of select="$singleFailureDetail" /></pre>
    </xsl:when>
    <xsl:when test="contains($singleFailureDetail, '&#xA;')">
    <pre><xsl:value-of select="$singleFailureDetail" /></pre>
    </xsl:when>
    <xsl:otherwise>
    <text><xsl:value-of select="$singleFailureDetail" /></text>
    </xsl:otherwise>
    </xsl:choose>


    </td></tr>
  </xsl:for-each>
  <xsl:if test="wsi-report:prereqFailedList">
    <tr bgcolor="#ffffff">
    <td>
		<b>Prereq Failed List</b>
    </td>
    <td style="color : {$prereqFailedColor};">
		<xsl:for-each select="wsi-report:prereqFailedList/wsi-report:testAssertionID">
			<b>
			<text>
				<xsl:value-of select="." />
			</text>
			</b>
		</xsl:for-each>
    </td></tr>
  </xsl:if>
  <xsl:for-each select="wsi-report:additionalEntryType">
    <tr bgcolor="#ffffff"><td>
    <b>Additional Entry Type</b>
    </td><td>
      <xsl:value-of select="@type" />
  	</td></tr>
  </xsl:for-each>
  </tbody>
  </table>
  <p/>
</xsl:template>

<xsl:template match="wsi-report:analyzerFailure" >
  <hr style="color : black;"/>
  <h2 style="background-color: red;">Analyzer Failure</h2>
  
  <xsl:if test="wsi-report:failureDetail">
  <table width="90%" cellpadding="4" bgcolor="#000000" cellspacing="1" valign="top">
  <col span="1" width="20%"/>
  <tbody>
    <xsl:for-each select="wsi-report:failureDetail">
    <tr bgcolor="#ffffff"><td>
    <b>Failure Detail Message</b>
    </td><td>
    <pre>
    <xsl:value-of select="current()" />
    </pre>
    </td></tr>
  </xsl:for-each>
  </tbody>
  </table>
  </xsl:if>
  <hr style="color : black;"/>
</xsl:template>

</xsl:stylesheet>

