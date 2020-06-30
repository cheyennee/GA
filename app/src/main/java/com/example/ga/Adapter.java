package com.example.ga;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {

    private List<ParaDetail> mDetails = new ArrayList<>();
    private Context mContext;
    public Adapter(Context context){
        mContext = context;
    }
    public void refresh(List<ParaDetail> details){
        if(details != null && details.size() >= 0) {
            mDetails.clear();
            mDetails.addAll(details);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mDetails == null?0:mDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return mDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext,R.layout.list_item,null);
            holder.para = convertView.findViewById(R.id.para);
            holder.min = convertView.findViewById(R.id.min);
            holder.max = convertView.findViewById(R.id.max);
            holder.radio = convertView.findViewById(R.id.ratio);
            holder.power = convertView.findViewById(R.id.power);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.para.setText(mDetails.get(position).getName());
        holder.min.setText("最小值："+mDetails.get(position).getLbound()+" ");
        holder.max.setText("最大值："+mDetails.get(position).getUbound());
        holder.radio.setText("系数："+mDetails.get(position).getRatio());
        holder.power.setText("次方："+mDetails.get(position).getPower());
        return convertView;
    }
    class ViewHolder{
        TextView para,min,max,radio,power;
    }
}
