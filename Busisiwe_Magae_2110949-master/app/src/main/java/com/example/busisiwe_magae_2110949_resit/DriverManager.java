package com.example.busisiwe_magae_2110949_resit;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class DriverManager {
    private static final String TAG = "DriverManager";

    public List<Driver> parseDriverStandings(String xml) {
        List<Driver> drivers = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            Driver currentDriver = null;
            boolean insideDriver = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("DriverStanding".equalsIgnoreCase(tagName)) {
                            currentDriver = new Driver();
                            currentDriver.setPoints(parser.getAttributeValue(null, "points"));
                        } else if ("Driver".equalsIgnoreCase(tagName)) {
                            insideDriver = true;
                        } else if (insideDriver) {
                            if ("GivenName".equalsIgnoreCase(tagName)) {
                                currentDriver.setName(parser.nextText());
                            } else if ("FamilyName".equalsIgnoreCase(tagName)) {
                                currentDriver.setName(currentDriver.getName() + " " + parser.nextText());
                            }
                        } else if ("Constructor".equalsIgnoreCase(tagName)) {
                            insideDriver = false;
                        } else if ("Name".equalsIgnoreCase(tagName) && !insideDriver) {
                            currentDriver.setTeam(parser.nextText());
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("DriverStanding".equalsIgnoreCase(tagName) && currentDriver != null) {
                            drivers.add(currentDriver);
                            currentDriver = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            Log.d(TAG, "Parsed driver standings: " + drivers);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing driver standings", e);
            return null;
        }
        return drivers;
    }
}

