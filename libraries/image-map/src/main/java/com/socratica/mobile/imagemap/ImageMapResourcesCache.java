package com.socratica.mobile.imagemap;

import android.content.Context;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * TODO
 * 
 * @author aectann@gmail.com (Konstantin Burov)
 * 
 */
public interface ImageMapResourcesCache {

  Path[] getAreaPaths(Context context, Integer xmlResourceId);

  int getDataId(Context context, Integer xmlResourceId, Integer pathIndex);

  int getAreaId(Context context, Integer xmlResourceId, Integer dataId,
                Integer target);

  int getAreaId(Context context, Integer xmlResourceId, Integer dataId);

  ArrayList<Integer> getAreaGroups(Context context, Integer xmlResourceId,
                                   Integer dataId);
}
