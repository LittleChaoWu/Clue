package com.sfb.linyi;

import android.os.Bundle;
import android.view.View;

import com.sfb.baselib.components.CommonConstant;
import com.sfb.clue.ClueInit;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.clue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(CommonConstant.KEY_PHONE, "13180008900");
                new ClueInit.Builder(MainActivity.this, bundle).create();
            }
        });
    }
}