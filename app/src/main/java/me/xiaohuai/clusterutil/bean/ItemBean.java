package me.xiaohuai.clusterutil.bean;

import android.content.Context;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;

import me.xiaohuai.clusterutil.R;
import me.xiaohuai.clusterutil.clusterutil.clustering.ClusterItem;

/**
 * 处理Item数据
 *
 * @author XiaoHuai
 */
public class ItemBean implements ClusterItem {

    private Context mContext;

    private LatLng mLatLng;

    public ItemBean(Context mContext, LatLng mLatLng) {
        this.mContext = mContext;
        this.mLatLng = mLatLng;
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        long cmd = System.currentTimeMillis() % 3;
        if (cmd == 0) {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_marka);
        } else if (cmd == 1) {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_markb);
        }
        return BitmapDescriptorFactory
                .fromResource(R.drawable.icon_gcoding);
    }
}