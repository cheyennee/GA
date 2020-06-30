package com.example.ga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

//创建一个产生随机数的线程，每产生一个数据发送一个message，handler接受message后更新折线图
public class MainActivity extends AppCompatActivity {

    private static final int TAG = 1;//message的what标识
    private LineChart mLineChart;
    private List<Data> mDatas = new ArrayList<>();
    private List<Entry> mEntries = new ArrayList<>();
    private Thread mThread;
    private Handler mHandler;
    private GA ga = new GA();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initThread();
        mThread.start();
    }
    private void initView(){
        mLineChart = findViewById(R.id.line_chart);
    }
    private void initData(){
        ga.generation = 0;
        ga.initialize();
        ga.evaluate();
        ga.keep_the_best();
        LineDataSet dataSet = new LineDataSet(mEntries,"number");
        dataSet.setColor(Color.YELLOW);
        LineData lineData = new LineData(dataSet);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
        YAxis rightYAxis = mLineChart.getAxisRight();
        rightYAxis.setEnabled(false);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.fitScreen();

    }
    private void initThread(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == TAG){
                    updateChart(msg);
                }
            }
        };
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(ga.generation<Constant.MAXGENS) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ga.generation++;
                    ga.select();
                    ga.crossover();
                    ga.mutate();
                    ga.evaluate();
                    ga.elitist();
                    int temp = 0;
                    for(int i = 0;i<Constant.NVARS;i++){
                        temp += Constant.details.get(i).getRatio()*Math.pow(ga.population[Constant.POPSIZE].gene[i],Constant.details.get(i).getPower());
                    }
                    Data data = new Data(ga.generation,temp);
                    mDatas.add(data);
                    Message message = Message.obtain();
                    message.arg1 = temp;
                    message.arg2 = ga.generation;
                    message.what=TAG;
                    mHandler.sendMessage(message);
                }
            }
        });
    }
    private void updateChart(Message msg){
        mEntries.add(new Entry(msg.arg2,msg.arg1));
        LineDataSet dataSet = new LineDataSet(mEntries,"number");
        LineData lineData = new LineData(dataSet);
        mLineChart.setData(lineData);
        mLineChart.invalidate();
    }
    private int endIndex(int i){
        int index = 0;
        while (i!=0){
            i = i/10;
            index += 1;
        }
        return index;
    }
    private void updateTxt(Message msg){
        StringBuilder mStringBuilder = new StringBuilder();
        int mEndIndex = 1;
        mStringBuilder.replace(7,7 + mEndIndex, msg.arg1 + "");//将原来的数字替换
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(mStringBuilder);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.5f);
        mEndIndex = endIndex(msg.arg1);//新的y值的位数
        spannableStringBuilder.setSpan(foregroundColorSpan,7,7 + mEndIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(relativeSizeSpan,7,7 + mEndIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
