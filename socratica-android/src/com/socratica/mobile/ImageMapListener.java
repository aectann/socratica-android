package com.socratica.mobile;

/**
 * @author aectann@gmail.com (Konstantin Burov)
 */
public interface ImageMapListener {

  /**
   * Invoked when some area on the map was tapped.
   * 
   * @param areaId
   */
  void onAreaClicked(int areaId);
}
