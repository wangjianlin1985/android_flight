package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Flight;
public class FlightListHandler extends DefaultHandler {
	private List<Flight> flightList = null;
	private Flight flight;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (flight != null) { 
            String valueString = new String(ch, start, length); 
            if ("flightId".equals(tempString)) 
            	flight.setFlightId(new Integer(valueString).intValue());
            else if ("flightNo".equals(tempString)) 
            	flight.setFlightNo(valueString); 
            else if ("comparyObj".equals(tempString)) 
            	flight.setComparyObj(new Integer(valueString).intValue());
            else if ("startCity".equals(tempString)) 
            	flight.setStartCity(valueString); 
            else if ("arriveCity".equals(tempString)) 
            	flight.setArriveCity(valueString); 
            else if ("flightDate".equals(tempString)) 
            	flight.setFlightDate(Timestamp.valueOf(valueString));
            else if ("flyTime".equals(tempString)) 
            	flight.setFlyTime(valueString); 
            else if ("waitFloor".equals(tempString)) 
            	flight.setWaitFloor(valueString); 
            else if ("meetFloor".equals(tempString)) 
            	flight.setMeetFloor(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Flight".equals(localName)&&flight!=null){
			flightList.add(flight);
			flight = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		flightList = new ArrayList<Flight>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Flight".equals(localName)) {
            flight = new Flight(); 
        }
        tempString = localName; 
	}

	public List<Flight> getFlightList() {
		return this.flightList;
	}
}
