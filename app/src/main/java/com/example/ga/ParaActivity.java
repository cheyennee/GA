package com.example.ga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ParaActivity extends AppCompatActivity {

    private EditText popsize,maxgenes,pxover,pmutation;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para);
        initView();
        initListener();
    }
    private void initView(){
        popsize = findViewById(R.id.et_popsize);
        maxgenes = findViewById(R.id.et_maxgenes);
        pxover = findViewById(R.id.et_pxover);
        pmutation = findViewById(R.id.et_pmutation);
        next = findViewById(R.id.bt_next);
    }
    private void initListener(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(popsize.getText()) ||TextUtils.isEmpty(maxgenes.getText())||TextUtils.isEmpty(pxover.getText())||TextUtils.isEmpty(pmutation.getText())){
                    Toast.makeText(ParaActivity.this,"GA经不起调戏",Toast.LENGTH_SHORT).show();
                    return ;
                }
                Constant.POPSIZE = Integer.parseInt(popsize.getText().toString());
                Constant.MAXGENS = Integer.parseInt(maxgenes.getText().toString());
                Constant.PXOVER = Double.parseDouble(pxover.getText().toString());
                Constant.PMUTATION = Double.parseDouble(pmutation.getText().toString());
                Intent intent = new Intent(ParaActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
