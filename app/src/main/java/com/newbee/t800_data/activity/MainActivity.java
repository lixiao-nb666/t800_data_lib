package com.newbee.t800_data.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.newbee.t800_data.R;
import com.newbee.t800_lib.manager.T800SendManager;
import com.newbee.t800_lib.type.T800BitmapQualityType;
import com.newbee.t800_lib.util.BleByteUtil;
import com.newbee.t800_lib.util.T800SendUtil;

public class MainActivity extends AppCompatActivity {
    private T800SendManager.Listen t800SendManagerListen=new T800SendManager.Listen() {
        @Override
        public void nowSendCmd(byte[] bytes) {
            String ss=BleByteUtil.parseByte2HexStr(bytes);
            tv.setText(ss);
        }

        @Override
        public void sendImage(Bitmap bitmap, T800BitmapQualityType qualityType) {
            tv.setText("处理图片:"+(bitmap==null)+"处理图片质量"+(qualityType==null));
        }
    };
    private TextView tv;
    private Button cmdBT,imageBT;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null==v){
                return;
            }
            switch (v.getId()){
                case R.id.bt_cmd:
                    T800SendUtil.sendTime();
                    break;
                case R.id.bt_image:
                    T800SendUtil.sendImage(null,null);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv);
        cmdBT=findViewById(R.id.bt_cmd);
        imageBT=findViewById(R.id.bt_image);
        cmdBT.setOnClickListener(onClickListener);
        imageBT.setOnClickListener(onClickListener);
        T800SendManager.getInstance().setListen(t800SendManagerListen);

    }
}
