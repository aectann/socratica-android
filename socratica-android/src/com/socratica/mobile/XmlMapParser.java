package com.socratica.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Path;

/**
 * TODO
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 *
 */
public class XmlMapParser implements MapParser {

	private static final String RECT = "rect";
	private static final String POLY = "poly";
	private static final String SHAPE = "shape";

	public ArrayList<Area> parseAreas(Context context, int mapResource) throws XmlPullParserException, IOException {
	  ArrayList<Area> areas = new ArrayList<Area>();
		int id = Integer.MIN_VALUE;
		int target = Integer.MIN_VALUE;
		Area area = null;
		XmlResourceParser parser = context.getResources().getXml(mapResource);
		int next = parser.next();
		while (next != XmlResourceParser.END_DOCUMENT) {
			if (next == XmlResourceParser.START_TAG) {
				String tag = parser.getName();
				if (tag.equals(AREA_TAG_NAME)) {
					id = parser.getAttributeIntValue(null, ATTR_HREF, -1);
					target = parser.getAttributeIntValue(null, ATTR_TARGET, -1);
					if (area == null || area.id != id || area.target != target) {
						area = new Area();
						area.id = id;
						areas.add(area);
						area.path = new Path();
						area.target = target;
					}
					String shape = parser.getAttributeValue(null, SHAPE);
					if(POLY.equalsIgnoreCase(shape)){
						attachPoly(area, parser.getAttributeValue(null, ATTR_COORDS));
					}else if(RECT.equalsIgnoreCase(shape)){
						attachRect(area, parser.getAttributeValue(null, ATTR_COORDS));
					}
				}
			}
			next = parser.next();
		}
		Collections.sort(areas, new Comparator<Area>() {
			public int compare(Area a1, Area a2) {
				if (a1.id > a2.id) {
					return 1;
				} else if (a1.id < a2.id) {
					return -1;
				}
				return 0;
			}
		});
	  return areas;
  }

	private void attachRect(Area area, String coordsString) {
		Path areaPath = area.path;
		//TODO might be not very efficient. Measure..
		String[] coords = coordsString.split(",");
		int x1 = Integer.parseInt(coords[0]);
		int y1 = Integer.parseInt(coords[1]);
		int x2 = Integer.parseInt(coords[2]);
		int y2 = Integer.parseInt(coords[3]);
		areaPath.moveTo(x1, y1);
		areaPath.lineTo(x2, y1);
		areaPath.lineTo(x2, y2);
		areaPath.lineTo(x1, y2);
		areaPath.close();
  }

	private void attachPoly(Area area, String coords) {
		Path areaPath = area.path;
		char[] chars = coords.toCharArray();
		int x = 0;
		int y = 0;
		int commaCount = 0;
		for (char c : chars) {
			if (c == ',') {
				commaCount++;
				if (commaCount == 2) {
					areaPath.moveTo(x, y);
					x = 0;
					y = 0;
				} else if (commaCount % 2 == 0) {
					areaPath.lineTo(x, y);
					x = 0;
					y = 0;
				}
				continue;
			}
			if (commaCount % 2 == 0) {
				x *= 10;
				x += c - '0';
			} else {
				y *= 10;
				y += c - '0';
			}
		}
		areaPath.lineTo(x, y);
		areaPath.close();
	}


}
