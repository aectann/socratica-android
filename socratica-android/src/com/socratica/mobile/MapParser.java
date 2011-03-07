package com.socratica.mobile;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.graphics.Path;

/**
 * TODO
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 *
 */
public interface MapParser {

	ArrayList<Area> parseAreas(Context context, int mapResource) throws XmlPullParserException, IOException;
	
	static final String ATTR_HREF = "href";
	static final String ATTR_COORDS = "coords";
	static final String AREA_TAG_NAME = "area";
	static final String ATTR_TARGET = "target";
	
	static class Area {
		public Path path;
		public String idStr;
		public int id;
		public int area;
		public int target;
	}
}
