package com.socratica.mobile.imagemap;

import android.content.Context;
import android.graphics.Path;
import android.util.SparseArray;

import com.socratica.mobile.imagemap.MapParser.Area;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 * 
 */
public class SimpleResourceCache implements ImageMapResourcesCache {

  private SparseArray<Object> dataIds;
  private SparseArray<Object> paths;
  private MapParser mapParser;
  private SparseArray<SparseArray<ArrayList<Integer>>> areaGroups;

  public SimpleResourceCache(MapParser mapParser) {
    dataIds = new SparseArray<Object>();
    paths = new SparseArray<Object>();
    areaGroups = new SparseArray<SparseArray<ArrayList<Integer>>>();
    this.mapParser = mapParser;
  }

  @Override
  public synchronized Path[] getAreaPaths(Context context, Integer mapResourceId) {
    if (dataIds.indexOfKey(mapResourceId) > -1) {
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
    if (dataIds.indexOfKey(mapResource) < 0) {
      ArrayList<Area> areas = mapParser.parseAreas(context, mapResource);
      int size = areas.size();
      int[][] areaIds = new int[size][2];
      Path[] areaPaths = new Path[size];
      SparseArray<ArrayList<Integer>> groupsByData = new SparseArray<ArrayList<Integer>>();
      int i = 0;
      for (Area a : areas) {
        areaIds[i][0] = a.id;
        areaIds[i][1] = a.target;
        areaPaths[i] = a.path;
        if (groupsByData.indexOfKey(a.id) < 0) {
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
  public int getDataId(Context context, Integer mapResourceId, Integer pathIndex) {
    if (dataIds.indexOfKey(mapResourceId) < 0) {
      try {
        init(context, mapResourceId);
      } catch (Exception e) {
        throw new IllegalStateException("Failed to init image map areas", e);
      }
    }
    return ((int[][]) dataIds.get(mapResourceId))[pathIndex][0];
  }

  @Override
  public int getAreaId(Context context, Integer mapResourceId, Integer dataId,
      Integer target) {
    if (dataIds.indexOfKey(mapResourceId) < 0) {
      try {
        init(context, mapResourceId);
      } catch (Exception e) {
        throw new IllegalStateException("Failed to init image map areas", e);
      }
    }
    int[][] ids = (int[][]) this.dataIds.get(mapResourceId);
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
      Integer mapResourceId, Integer dataId) {
    if (areaGroups.indexOfKey(mapResourceId) < 0) {
      try {
        init(context, mapResourceId);
      } catch (Exception e) {
        throw new IllegalStateException("Failed to init image map areas", e);
      }
    }
    return areaGroups.get(mapResourceId).get(dataId);
  }
}
