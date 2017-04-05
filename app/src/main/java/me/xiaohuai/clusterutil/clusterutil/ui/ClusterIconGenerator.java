package me.xiaohuai.clusterutil.clusterutil.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import me.xiaohuai.clusterutil.R;

/**
 * 自定义聚合图标
 *
 * @author XiaoHuai
 */
public class ClusterIconGenerator {
    Bitmap bmp1;
    private final Context mContext;
    private Paint paint = new Paint(1);
    Rect srcRect;
    float x;
    float y;

    public ClusterIconGenerator(Context paramContext) {
        this.mContext = paramContext;
        //生成自定义图
        this.bmp1 = ((BitmapDrawable) this.mContext.getResources().getDrawable(R.mipmap.park_icon)).getBitmap();
        //绘制时控制文本绘制的范围
        this.srcRect = new Rect(0, 0, this.bmp1.getWidth(), this.bmp1.getHeight());
        this.x = (43 * this.srcRect.width() / 84.0F);
        this.y = (31 * this.srcRect.height() / 127.0F);
        //设置颜色
        this.paint.setColor(mContext.getResources().getColor(android.R.color.white));
        //设置字大小
        this.paint.setTextSize(paramContext.getResources().getDimension(R.dimen.text_size_medium));
        //探测paint文字大小高度
        Paint.FontMetrics localFontMetrics = this.paint.getFontMetrics();
        this.y += (2 + (int) Math.ceil(localFontMetrics.descent - localFontMetrics.top)) / 2;

        Log.e("e", "x：" + x + " y：" + y);
    }

    /**
     * 生成地图上显示的Icon
     *
     * @param paramString 显示的数字
     * @return 图
     */
    public Bitmap makeIcon(String paramString) {
        try {
            Bitmap localBitmap = Bitmap.createBitmap(this.bmp1.getWidth(), this.bmp1.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas localCanvas = new Canvas(localBitmap);
            float f = this.paint.measureText(paramString);
            localCanvas.drawBitmap(this.bmp1, this.srcRect, this.srcRect, null);
            localCanvas.drawText(paramString, this.x - f / 2.0F, this.y, this.paint);
            return localBitmap;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

}
