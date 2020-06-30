package com.example.ga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MaxOrMinActivity extends AppCompatActivity {

    private RadioGroup rg;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_or_min);
        initView();
        initListener();
    }
    private void initView(){
        rg = findViewById(R.id.rg_maxormin);
        bt = findViewById(R.id.bt_next);
    }
    private void initListener(){
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_max:
                        Constant.isMax = true;
                        break;
                    case R.id.rb_min:
                        Constant.isMax = false;
                        break;
                }
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaxOrMinActivity.this,ObjFunActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
