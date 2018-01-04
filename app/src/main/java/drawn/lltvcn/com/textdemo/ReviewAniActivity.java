package drawn.lltvcn.com.textdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lltvcn.freefont.core.animation.A;
import com.lltvcn.freefont.core.data.DrawData;
import com.lltvcn.freefont.core.view.STextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import drawn.lltvcn.com.textdemo.controller.EditParamViewController;
import drawn.lltvcn.com.textdemo.controller.SelectController;
import drawn.lltvcn.com.util.FileUtil;

/**
 * Created by zhaolei on 2017/11/21.
 */

public class ReviewAniActivity extends Activity{
    STextView tv;
    SelectController selectController;
    String[] fileNames,aniNames;
    DrawData drawData;
    int[] aniTypes;

    {
        aniNames = new String[]{"出现1","出现2(单字旋转)","出现4(大->小)","出现7(淡入淡出)","基础2(单字上下抖动)","基础3(所有字旋转)","基础4(单字缩放)","基础1(X)"};
        aniTypes = new int[]{A.BOTTOM_IN_SCALE_UP_OUT,A.SINGLE_ROTATE,A.SCALE_SHOW,A.SINGLE_RIGHT_FADE_INF_LEFT_FADE_OUT,A.SINGLE_UP_DOWN,A.ROTATE_REPEAT,A.SINGLE_SCALE,A.SINGLE_X};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_review_ani);
        tv = (STextView) findViewById(R.id.tv);
        tv.setText("测试字体");
        selectController = new SelectController();
        selectController.init((ViewGroup) findViewById(R.id.fl_cotainer), new EditParamViewController.VisiableListener() {
            @Override
            public void onVisable(boolean visiable) {
            }
        });
        File file = new File(FileUtil.getDataDir());
        fileNames = file.list();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectController.show(fileNames, false, new SelectController.ResultListener() {
                    @Override
                    public void onConfrim(ArrayList<String> result) {
                        String name = result.get(0);
                        String dir = FileUtil.getDataDirByName(name);
                        try {
                            FileReader reader = new FileReader(dir+File.separator+name+".txt");
                            DrawData data = new Gson().fromJson(reader,DrawData.class);
                            drawData = data;
                            reader.close();
                            tv.setData(data);
                            if(tv.getTAnimation()!=null){
                                tv.getTAnimation().start();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        findViewById(R.id.btn_ani).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectController.show(aniNames, false, new SelectController.ResultListener() {
                    @Override
                    public void onConfrim(ArrayList<String> result) {
                        for (int i = 0; i < aniNames.length; i++) {
                            if(aniNames[i].equals(result.get(0))){
                                if(drawData!=null){
                                    drawData.aniType = aniTypes[i];
                                    if(tv.getTAnimation()!=null){
                                        tv.getTAnimation().stop();
                                    }
                                    tv.setData(drawData);
                                    if(tv.getTAnimation()!=null){
                                        tv.getTAnimation().start();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });

    }
}
