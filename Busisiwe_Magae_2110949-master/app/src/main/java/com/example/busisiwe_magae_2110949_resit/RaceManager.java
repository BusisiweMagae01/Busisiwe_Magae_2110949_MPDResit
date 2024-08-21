package com.example.busisiwe_magae_2110949_resit;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class RaceManager {
    private static final String TAG = "RaceManager";

    public List<Race> parseRaceSchedule(String xml) {
        List<Race> races = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            Race currentRace = null;
            StringBuilder currentText = new StringBuilder();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("Race".equalsIgnoreCase(parser.getName())) {
                            currentRace = new Race();
                            currentRace.setDate(parser.getAttributeValue(null, "season") + "-" + parser.getAttributeValue(null, "round"));
                        }
                        currentText.setLength(0);
                        break;

                    case XmlPullParser.TEXT:
                        currentText.append(parser.getText());
                        break;

                    case XmlPullParser.END_TAG:
                        if (currentRace != null) {
                            String tagName = parser.getName();
                            String text = currentText.toString().trim();
                            if ("RaceName".equalsIgnoreCase(tagName)) {
                                currentRace.setRaceName(text);
                            } else if ("CircuitName".equalsIgnoreCase(tagName)) {
                                    currentRace.setCircuitName(text);
                            } else if ("Date".equalsIgnoreCase(tagName)) {
                                currentRace.setDate(text);
                            } else if ("Race".equalsIgnoreCase(tagName)) {
                                races.add(currentRace);
                                currentRace = null;
                            }
                        }
                        break;
                }

                try {
                    eventType = parser.next();
                } catch (Exception e) {
                    Log.w(TAG, "Error parsing XML, stopping at current position", e);
                    break;
                }
            }
            Log.d(TAG, "Parsed race schedule: " + races);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing race schedule", e);
        }
        return races;
    }
}


