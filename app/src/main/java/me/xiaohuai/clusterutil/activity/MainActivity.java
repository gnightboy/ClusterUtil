package me.xiaohuai.clusterutil.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import me.xiaohuai.clusterutil.R;
import me.xiaohuai.clusterutil.bean.ItemBean;
import me.xiaohuai.clusterutil.clusterutil.clustering.Cluster;
import me.xiaohuai.clusterutil.clusterutil.clustering.ClusterManager;

/**
 * 地图核心操作界面
 *
 * @author XiaoHuai
 */
public class MainActivity extends FragmentActivity implements ClusterManager.OnClusterClickListener<ItemBean>,
        ClusterManager.OnClusterItemClickListener<ItemBean> {
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
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
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

    @Override
    public boolean onClusterClick(Cluster<ItemBean> cluster) {
        //隐藏InfoWindows
        mBaiduMap.hideInfoWindow();
        //InfoWindows第一个条件
        View view = infoWindow("onClusterClick", "数量：" + cluster.getSize());
        //第二个条件
        LatLng latLng = cluster.getPosition();
        //第三个条件
        int y = -getYOffset(R.mipmap.park_icon);
        //显示InfoWindows
        mBaiduMap.showInfoWindow(new InfoWindow(view, latLng, y));
        return false;
    }

    @Override
    public boolean onClusterItemClick(ItemBean item) {
        //隐藏InfoWindows
        mBaiduMap.hideInfoWindow();
        //InfoWindows第一个条件
        View view = infoWindow("onClusterItemClick", "图标内容：" + item.getNum());
        //第二个条件
        LatLng latLng = item.getPosition();
        //第三个条件
        int y = -item.getBitmapDescriptor().getBitmap().getHeight();
        //显示InfoWindows
        mBaiduMap.showInfoWindow(new InfoWindow(view, latLng, y));
        return false;
    }

    /**
     * 获取指定图片的高度
     * （因为每种覆盖物都有各自的图，用图的高度来代表它的偏移量在合适不过了）
     *
     * @param resourcesId 资源ID
     * @return 偏移量高度
     */
    public int getYOffset(int resourcesId) {
        //图片工厂
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //true表示只读图片，不加载到内存中
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resourcesId, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        return height;
    }

    /**
     * 自定义infoWinfow窗口(中间显示距离)
     *
     * @param content 显示的内容
     * @param title   标题
     */
    private View infoWindow(String title, String content) {
        View infoWindow = LayoutInflater.from(this).inflate(R.layout.layout_custom_infowindow_view, null);

        TextView tv_title = (TextView) infoWindow.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) infoWindow.findViewById(R.id.tv_content);
        tv_content.setText(String.valueOf(content));
        tv_title.setText(String.valueOf(title));
        return infoWindow;
    }

}
