package com.sample.soap.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * 
 * @author Evelyn
 *
 */
public class PostOfficeConsumer {
	
public static void main(String[] args) throws SOAPException, IOException {
	
	//SoapUI generated XML
	final String requestSoap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\r\n" + 
			"   <soapenv:Header/>\r\n" + 
			"   <soapenv:Body>\r\n" + 
			"      <tem:CalcPrazo>\r\n" + 
			"         <!--Optional:-->\r\n" + 
			"         <tem:nCdServico>40010</tem:nCdServico>\r\n" + 
			"         <!--Optional:-->\r\n" + 
			"         <tem:sCepOrigem>01207-000</tem:sCepOrigem>\r\n" + 
			"         <!--Optional:-->\r\n" + 
			"         <tem:sCepDestino>01504001</tem:sCepDestino>\r\n" + 
			"      </tem:CalcPrazo>\r\n" + 
			"   </soapenv:Body>\r\n" + 
			"</soapenv:Envelope>"; 
	
	final SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	final SOAPConnection soapConnection = soapConnectionFactory.createConnection();
	
	final String url = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx"; //Web Service URL
	
	final MimeHeaders headers = new MimeHeaders();
	headers.addHeader("Content-Type", "text/xml");
	headers.addHeader("SOAPAction", "http://tempuri.org/CalcPrazo"); //SOAPAction header and your URL. This URL changes according to the Web Service. Some do not have this property.
	
	final MessageFactory messageFactory = MessageFactory.newInstance();
	
	final SOAPMessage msg = messageFactory.createMessage(headers, (new ByteArrayInputStream(requestSoap.getBytes())));
	
	final SOAPMessage soapResponse = soapConnection.call(msg, url);
	final Document requestResponseXml = soapResponse.getSOAPBody().getOwnerDocument();
	System.out.println(passXmlToString(requestResponseXml, 4));
		
	
	}

public static String passXmlToString(Document xml, int spaces) {
	
	try {
		//set up a transformer
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setAttribute("indent-number", new Integer(spaces));
		final Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		 //create string from XML tree
		final StringWriter sw = new StringWriter();
		final StreamResult result = new StreamResult(sw);
		final DOMSource source = new DOMSource(xml);
		transformer.transform(source, result);
		final String xmlString = sw.toString();
		return xmlString;
	}catch (final TransformerException e) {
		e.printStackTrace();
        System.exit(0);
	}
	return null;
}
}
