package me.xiaohuai.clusterutil.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 经纬度
     */
    private LatLng mLatLng;
    /**
     * 显示数量
     */
    private int num = 0;

    public ItemBean(Context mContext, LatLng mLatLng, int num) {
        this.mContext = mContext;
        this.mLatLng = mLatLng;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        return BitmapDescriptorFactory.fromView(markerViewDefault(mContext, num));
    }

    /**
     * 默认的角标
     *
     * @param num 界面上显示的数量
     */
    private View markerViewDefault(Context context, int num) {
        View markerView = LayoutInflater.from(context).inflate(R.layout.layout_custom_marker_view_default, null);

        ImageView iv_marker_icon = (ImageView) markerView.findViewById(R.id.iv_marker_icon);
        TextView tv_marker_num = (TextView) markerView.findViewById(R.id.tv_marker_num);

        /*
         * 没有车，显示灰色
         */
        if (num <= 0) {
            tv_marker_num.setText("0");
            iv_marker_icon.setImageResource(R.drawable.parkgray_icon);
        } else {
            tv_marker_num.setText(String.valueOf(num));
            iv_marker_icon.setImageResource(R.drawable.parkblue_icon);
        }
        return markerView;
    }

}