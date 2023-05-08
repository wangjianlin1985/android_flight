package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Dot;
public class DotListHandler extends DefaultHandler {
	private List<Dot> dotList = null;
	private Dot dot;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (dot != null) { 
            String valueString = new String(ch, start, length); 
            if ("dotId".equals(tempString)) 
            	dot.setDotId(new Integer(valueString).intValue());
            else if ("companyObj".equals(tempString)) 
            	dot.setCompanyObj(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	dot.setTitle(valueString); 
            else if ("cityObj".equals(tempString)) 
            	dot.setCityObj(valueString); 
            else if ("telephone".equals(tempString)) 
            	dot.setTelephone(valueString); 
            else if ("fax".equals(tempString)) 
            	dot.setFax(valueString); 
            else if ("address".equals(tempString)) 
            	dot.setAddress(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Dot".equals(localName)&&dot!=null){
			dotList.add(dot);
			dot = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		dotList = new ArrayList<Dot>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Dot".equals(localName)) {
            dot = new Dot(); 
        }
        tempString = localName; 
	}

	public List<Dot> getDotList() {
		return this.dotList;
	}
}
