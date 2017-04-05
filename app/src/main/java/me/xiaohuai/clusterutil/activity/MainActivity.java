package me.xiaohuai.clusterutil.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import me.xiaohuai.clusterutil.R;
import me.xiaohuai.clusterutil.bean.ItemBean;
import me.xiaohuai.clusterutil.clusterutil.clustering.ClusterManager;

/**
 * 地图核心操作界面
 *
 * @author XiaoHuai
 */
public class MainActivity extends FragmentActivity {
    /**
     * 地图控件
     */
    private MapView mMapView;
    /**
     * 百度地图控制对象
     */
    private BaiduMap mBaiduMap;
    /**
     * 聚合
     */
    private ClusterManager<ItemBean> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        mMapView = (MapView) findViewById(R.id.mapView);
        //获取地图控制
        mBaiduMap = mMapView.getMap();
        /*
         * 初始化聚合管理类（第一个上下文、第二个地图管理对象、第三个Marker管理）
         * 聚合操作其实就是对Marker的处理
         */
        mClusterManager = new ClusterManager<>(this, mBaiduMap);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        mBaiduMap.setOnMarkerClickListener(mClusterManager);
        //设置数据
        setDatas();
    }

    /**
     * 设置数据
     */
    private void setDatas() {
        LatLng latLng1 = new LatLng(39.9186190000, 116.4016030000);
        LatLng latLng2 = new LatLng(39.9169590000, 116.4000220000);
        LatLng latLng3 = new LatLng(39.9173190000, 116.4090590000);
        LatLng latLng4 = new LatLng(39.9157690000, 116.4060050000);
        LatLng latLng5 = new LatLng(39.9150500000, 116.4015850000);
        LatLng latLng6 = new LatLng(39.9262450000, 116.4287530000);
        LatLng latLng7 = new LatLng(39.9218180000, 116.4362270000);
        LatLng latLng8 = new LatLng(39.9311140000, 116.4322020000);
        LatLng latLng9 = new LatLng(39.9355400000, 116.4066190000);
        LatLng latLng10 = new LatLng(39.9150500000, 116.4015850000);
        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(latLng1);
        latLngs.add(latLng2);
        latLngs.add(latLng3);
        latLngs.add(latLng4);
        latLngs.add(latLng5);
        latLngs.add(latLng6);
        latLngs.add(latLng7);
        latLngs.add(latLng8);
        latLngs.add(latLng9);
        latLngs.add(latLng10);

        List<ItemBean> datas = new ArrayList<>();
        for (int i = 0; i < latLngs.size(); i++) {
            final LatLng item = latLngs.get(i);
            int num = i % 2;
            ItemBean itemBean = new ItemBean(MainActivity.this, item, num);
            datas.add(itemBean);
        }

        mClusterManager.addItems(datas);
        mClusterManager.cluster();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
