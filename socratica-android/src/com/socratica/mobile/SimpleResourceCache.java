package com.socratica.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.graphics.Path;

import com.socratica.mobile.MapParser.Area;

/**
 * TODO
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 * 
 */
public class SimpleResourceCache implements ImageMapResourcesCache {

	private HashMap<Integer, Object> dataIds;
	private HashMap<Integer, Object> paths;
	private MapParser mapParser;
	private HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> areaGroups;
	
	public SimpleResourceCache(MapParser mapParser) {
		dataIds = new HashMap<Integer, Object>();
		paths = new HashMap<Integer, Object>();
		areaGroups = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		this.mapParser = mapParser;
	}

	@Override
	public synchronized Path[] getAreaPaths(Context context, Integer mapResourceId) {
		if (dataIds.containsKey(mapResourceId)) {
			return (Path[]) paths.get(mapResourceId);
		}
		try {
			init(context, mapResourceId);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to init image map areas", e);
		}
		notify();
		return (Path[]) paths.get(mapResourceId);
	}

	public synchronized void init(Context context, int mapResource)
			throws XmlPullParserException, IOException {
	  if (!dataIds.containsKey(mapResource)) {
  		ArrayList<Area> areas = mapParser.parseAreas(context, mapResource);
  		int size = areas.size();
  		int[][] areaIds = new int[size][2];
  		Path[] areaPaths = new Path[size];
  		HashMap<Integer, ArrayList<Integer>> groupsByData = new HashMap<Integer, ArrayList<Integer>>();
  		int i = 0;
  		for (Area a : areas) {
  			areaIds[i][0] = a.id;
  			areaIds[i][1] = a.target;
  			areaPaths[i] = a.path;
  			if(!groupsByData.containsKey(a.id)){
  				groupsByData.put(a.id, new ArrayList<Integer>());
  			}
  			groupsByData.get(a.id).add(a.target);
  			i++;
  		}
  		dataIds.put(mapResource, areaIds);
  		paths.put(mapResource, areaPaths);
  		areaGroups.put(mapResource, groupsByData);
	  }
	}

	@Override
	public int getDataId(Context context, Integer xmlResourceId, Integer pathIndex) {
		if(!dataIds.containsKey(xmlResourceId)){
			try {
				init(context, xmlResourceId);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ((int[][]) dataIds.get(xmlResourceId))[pathIndex][0];
	}

	@Override
	public int getAreaId(Context context, Integer xmlResourceId, Integer dataId, Integer target) {
		if(!dataIds.containsKey(xmlResourceId)){
			try {
				init(context, xmlResourceId);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int[][] ids = (int[][]) this.dataIds.get(xmlResourceId);
		for (int i = 0; i < ids.length; i++) {
			if (dataId == ids[i][0] && (target == -1 || target == ids[i][1])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getAreaId(Context context, Integer mapResource, Integer id) {
		return getAreaId(context, mapResource, id, -1);
	}

	@Override
	public ArrayList<Integer> getAreaGroups(Context context,
			Integer xmlResourceId, Integer dataId) {
		if(!areaGroups.containsKey(xmlResourceId)){
			try {
				init(context, xmlResourceId);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return areaGroups.get(xmlResourceId).get(dataId);
	}
}
