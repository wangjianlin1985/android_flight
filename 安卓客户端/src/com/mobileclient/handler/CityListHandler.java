package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.City;
public class CityListHandler extends DefaultHandler {
	private List<City> cityList = null;
	private City city;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (city != null) { 
            String valueString = new String(ch, start, length); 
            if ("cityNo".equals(tempString)) 
            	city.setCityNo(valueString); 
            else if ("cityName".equals(tempString)) 
            	city.setCityName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("City".equals(localName)&&city!=null){
			cityList.add(city);
			city = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		cityList = new ArrayList<City>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("City".equals(localName)) {
            city = new City(); 
        }
        tempString = localName; 
	}

	public List<City> getCityList() {
		return this.cityList;
	}
}
