package com.example.first.visualization;

/**
 * Created by wanjialin on 2015/5/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BarChartView extends View {

    //Y轴的刻度数
    public static final int Y_SCALE_NUM = 10;

    // 传递过来的数据
    public String[] XLabel;
    public String[] Data;

    public float xPoint = 18; // (以320宽度的分辨率为准,其他分辨率*系数)
    //public float yPoint = 220; // (以320宽度的分辨率为准,其他分辨率*系数)
    //public float yLength = 200;// (以320宽度的分辨率为准,其他分辨率*系数)
    public float yPoint; // (以320宽度的分辨率为准,其他分辨率*系数)
    public float yLength;// (以320宽度的分辨率为准,其他分辨率*系数)
    public float xLength;// X轴的刻度(屏幕的宽度-左右两边的间隔)

    public float xScale;// X轴的刻度长度
    public float YScale;// y轴的刻度长度

    public int eachYLabel;// Y轴应该显示的值
    public float halfXLabel;// X轴的刻度长度的1/4 用以显示X轴的坐标值

    //private float selectX;// 选中区域要显示的圆点的 x坐标
    private float xyExtra = 10;// XY轴空余的距离
    private float displacement = 4;
    private float textSize = 8;
    private float circleSize = 2;// 圆点的大小
    private float radio;// 不同分辨率的 比率(以320为基数)

    //private String selectData = "";// 选中之后需要显示的数据

    private int screenWidth;
    private int screenHeight;
    //private RectF rectF;
    //private List<PointBean> allpoint = new ArrayList<>();
    private Paint textPaint = new Paint();
    private Paint linePaint = new Paint();
    private Paint dataPaint = new Paint();
    private Paint selectBkgPaint = new Paint();
    private Paint selectCirclePaint = new Paint();
    private Paint selectTextPaint = new Paint();

    public BarChartView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void SetInfo(String[] XTime, String[] AllData,
                        Display display) {

        XLabel = XTime;
        Data = AllData;
        this.screenWidth = display.getWidth();
        this.screenHeight = display.getHeight();
        radio = screenWidth / 320;// (以320的分辨率为基准)
        xPoint = xPoint * radio;// X轴左右两边的间隔
        xLength = screenWidth - xPoint * 2;// X轴的长度 左右两边空余xPoint的刻度
        xyExtra = xyExtra * radio;
        xScale = (xLength - xyExtra) / XLabel.length; // X轴的刻度间隔为长度-最右边空余的距离
        // 除以个数
        halfXLabel = xScale / 2;// X轴刻度的1/4值(X轴刻度值在1/4处显示)

        //yPoint = yPoint * radio;
        //yLength = yLength * radio;
        yPoint = screenHeight - xPoint;
        yLength = screenHeight - xPoint * 2;
        displacement = displacement * radio;// Y轴显示的文字

        YScale = (yLength - xyExtra) / Y_SCALE_NUM;// Y轴的刻度为长度-最上边的距离 除以个数
        eachYLabel = max(Data) / Y_SCALE_NUM;// Y轴的刻度值为(最大数/Y_SCALE_NUM)

        textSize = textSize * radio;
        circleSize = circleSize * radio;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);// 重写onDraw方法

        // 文本的画笔
        //textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setAntiAlias(true);// 去锯齿
        textPaint.setColor(Color.RED);// 颜色
        textPaint.setTextSize(textSize); // 设置轴文字大小

        // 线的画笔
        //linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);// 去锯齿
        //linePaint.setStyle(Paint.Style.STROKE);
        //linePaint.setAntiAlias(true);// 去锯齿
        linePaint.setColor(Color.parseColor("#808b98"));// 颜色

        // 曲线和未选中圆点的画笔
        //dataPaint = new Paint();
        dataPaint.setStyle(Paint.Style.FILL);
        dataPaint.setAntiAlias(true);// 去锯齿
        dataPaint.setColor(Color.parseColor("#37b99c"));// 颜色
        dataPaint.setTextSize(textSize); // 设置轴文字大小
        dataPaint.setStrokeWidth(4);

        // 选中之后 字体的画笔
        //selectTextPaint = new Paint();
        selectTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        selectTextPaint.setAntiAlias(true);// 去锯齿
        selectTextPaint.setColor(Color.WHITE);// 颜色
        selectTextPaint.setTextSize(textSize); // 设置轴文字大小

        // 显示条柱的画笔
        //selectBkgPaint = new Paint();
        selectBkgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        selectBkgPaint.setAntiAlias(true);// 去锯齿
        //selectBkgPaint.setColor(Color.parseColor("#F6F6F6"));// 颜色
        selectBkgPaint.setColor(Color.parseColor("#3366CC"));
        selectBkgPaint.setTextSize(textSize); // 设置轴文字大小

        // 选中之后 显示数据的画笔
        //selectCirclePaint = new Paint();
        selectCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        selectCirclePaint.setAntiAlias(true);// 去锯齿
        selectCirclePaint.setColor(Color.RED);// 颜色
        selectCirclePaint.setTextSize(textSize);

        // 设置Y轴
        canvas.drawLine(xPoint, yPoint - yLength, xPoint, yPoint, linePaint);
        // Y轴的箭头
        canvas.drawLine(xPoint, yPoint - yLength, xPoint - 3, yPoint - yLength + 6, linePaint);
        canvas.drawLine(xPoint, yPoint - yLength, xPoint + 3, yPoint - yLength + 6, linePaint);
        // 设置X轴
        canvas.drawLine(xPoint, yPoint, xPoint + xLength, yPoint, linePaint);
        // X轴的箭头
        canvas.drawLine(xPoint + xLength, yPoint, xPoint + xLength - 6, yPoint - 3, linePaint);
        canvas.drawLine(xPoint + xLength, yPoint, xPoint + xLength - 6, yPoint + 3, linePaint);

        canvas.drawText("id", xPoint + xLength, yPoint + displacement * 3, textPaint); // id
        canvas.drawText("value", xPoint + displacement, yPoint - yLength, textPaint); // value

        // Y轴线
        for (int i = 0; i < Y_SCALE_NUM; i++) {
            // Y轴的每条线,X轴为xPoint至xPoint + xLength Y轴定高度 yPoint - (i + 1) *
            // YScale(根据圆点计算出的值)
            canvas.drawLine(xPoint, yPoint - (i + 1) * YScale, xPoint + xLength, yPoint - (i + 1)
                    * YScale, linePaint); // 刻度
            try {
                // Y轴的刻度值,值为平均分配之后算出来的
                //canvas.drawText(String.valueOf(eachYLabel * (i + 1)), 5, yPoint - (i + 1) * YScale + 5, textPaint); // 文字
                canvas.drawText(String.valueOf(eachYLabel * (i + 1)), xPoint - displacement * 4, yPoint - (i + 1) * YScale + 5, textPaint);
            } catch (Exception e) {
            }

        }
        //canvas.drawText("0", 10, yPoint + displacement * 3, textPaint); // 文字
        canvas.drawText("0", xPoint - displacement * 3, yPoint + displacement * 3, textPaint);


        for (int i = 0; i < XLabel.length; i++) {
            try {
                // X轴的每条刻度线
                /*
                canvas.drawLine(xPoint + (i + 1) * xScale, yPoint, xPoint + (i + 1) * xScale,
                        yPoint - displacement, linePaint);
                */
                // X轴显示的刻度值
                //canvas.drawText(XLabel[i], xPoint + i * xScale + halfXLabel/2, yPoint + displacement * 3, textPaint); // 文字
                canvas.drawText(String.valueOf(i+1), xPoint + i * xScale + halfXLabel/2, yPoint + displacement * 3, textPaint); // id从1开始计

                //显示各个条柱
                canvas.drawRect(xPoint + i * xScale, yPoint - (yLength - xyExtra)*Float.parseFloat(Data[i])/max(Data),
                        xPoint + (i + 1) * xScale, yPoint, selectBkgPaint);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 计算Y坐标
     *
     * @param y0
     * @return
     */
    private float YCoord(String y0) // 计算绘制时的Y坐标，无数据时返回-999
    {
        float y;
        try {
            y = Float.parseFloat(y0);
        } catch (Exception e) {
            return -999; // 出错则返回-999
        }
        try {
            // YScale/eachYLabel为比率 乘以y得到距离圆点的距离
            return (yPoint - YScale * y / eachYLabel);
        } catch (Exception e) {
        }
        return y;
    }

    /**
     * 计算Y轴坐标的最大值
     *
     * @param p
     * @return
     */
    public static int max(String[] p) {
        float max = 0;
        for (int i = 0; i < p.length; i++) {
            if (Float.parseFloat(p[i]) - max > 0) {
                max = Float.parseFloat(p[i]);
            }
        }
        int length;
        int int_max = (int)max;
        if(int_max%20 == 0){
            length = int_max;
        }

        else{
            length = int_max/20+1;
            length = length*20;
        }
        //int length = (int) (max) / 20 + 1;// 为了取整数 比如最大值为39的时候 返回40
        return length;
    }

}
