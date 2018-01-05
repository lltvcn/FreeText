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
import drawn.lltvcn.com.util.FontUtil;
import drawn.lltvcn.com.util.SUtil;

/**
 * Created by zhaolei on 2017/11/21.
 */

public class ReviewActivity extends Activity{
    STextView tv;
    SelectController selectController;
    String[] fileNames;
    String[] fontNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_review);
        fontNames = FontUtil.getFontNames();
        tv = (STextView) findViewById(R.id.tv);
        tv.setText(SUtil.getTxt());
        tv.setLocalSourcePath(FileUtil.getImgDir());
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
        findViewById(R.id.btn_font).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectController.show(fontNames, false, new SelectController.ResultListener() {
                    @Override
                    public void onConfrim(ArrayList<String> result) {
                        if(result!=null&&result.size()>0){
                            tv.setTypeface(FontUtil.getTypeface(result.get(0)));
                        }else{
                            tv.setTypeface(null);
                        }
                    }
                });
            }
        });

    }
}
