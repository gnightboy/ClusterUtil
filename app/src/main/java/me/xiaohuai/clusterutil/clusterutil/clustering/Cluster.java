/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package me.xiaohuai.clusterutil.clusterutil.clustering;


import com.baidu.mapapi.model.LatLng;

import java.util.Collection;

/**
 * A collection of ClusterItems that are nearby each other.
 */
public interface Cluster<T extends me.xiaohuai.clusterutil.clusterutil.clustering.ClusterItem> {
    public LatLng getPosition();

    Collection<T> getItems();

    int getSize();
}