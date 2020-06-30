package com.example.ga;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ObjFunActivity extends AppCompatActivity {

    private TextView objFun;
    private Button add,next;
    private ListView lv;
    private List<ParaDetail> paraDetails = new ArrayList<>();
    private Adapter adapter = new Adapter(this);
    private EditText name,min,max,ratio,power;
    private StringBuilder builder = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj_fun);
        initView();
        initListener();
    }
    private void initView(){
        objFun = findViewById(R.id.tv_obj_fun);
        add = findViewById(R.id.bt_add_para);
        next = findViewById(R.id.bt_next);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);
    }
    private void initListener(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paraDetails.size() == 0 || paraDetails == null){
                    Toast.makeText(ObjFunActivity.this,"GA经不起调戏",Toast.LENGTH_SHORT).show();
                    return ;
                }
                Intent intent = new Intent(ObjFunActivity.this,ParaActivity.class);
                Constant.details.addAll(paraDetails);
                Constant.NVARS = paraDetails.size();
                startActivity(intent);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View alertView = View.inflate(ObjFunActivity.this,R.layout.alert_layout,null);
                initAlertView(alertView);
                AlertDialog dialog = new AlertDialog.Builder(ObjFunActivity.this).create();
                dialog.setTitle("设置参数");
                dialog.setView(alertView);
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!check())
                            return ;
                        ParaDetail one = new ParaDetail();
                        one.setName(name.getText().toString().trim());
                        one.setLbound(Integer.parseInt(min.getText().toString()));
                        one.setUbound(Integer.parseInt(max.getText().toString()));
                        one.setRatio(Integer.parseInt(ratio.getText().toString()));
                        one.setPower(Integer.parseInt(ratio.getText().toString()));
                        paraDetails.add(one);
                        adapter.refresh(paraDetails);
                        String temp;
                        if(one.getRatio()<0){
                            temp = "("+one.getRatio()+")*("+one.getName()+")^"+one.getPower();
                        }else{
                            temp = "+" + one.getRatio()+"*("+one.getName()+")^"+one.getPower();
                        }
                        builder.append(temp);
                        objFun.setText(builder);
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
    }
    private void initAlertView(View view){
        name = view.findViewById(R.id.et_name);
        max = view.findViewById(R.id.et_max);
        min = view.findViewById(R.id.et_min);
        ratio = view.findViewById(R.id.et_radio);
        power = view.findViewById(R.id.et_power);
    }
    private boolean check(){
        if(max.getText().toString().contains(".")||min.getText().toString().contains(".")||ratio.getText().toString().contains(".")||power.getText().toString().contains(".")){
            Toast.makeText(ObjFunActivity.this,"GA经不起调戏QAQ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(min.getText())||TextUtils.isEmpty(max.getText())||TextUtils.isEmpty(ratio.getText())||TextUtils.isEmpty(power.getText())){
            Toast.makeText(ObjFunActivity.this,"GA经不起调戏QAQ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Integer.parseInt(min.getText().toString()) >= Integer.parseInt(max.getText().toString())){
            Toast.makeText(ObjFunActivity.this,"GA经不起调戏QAQ",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
