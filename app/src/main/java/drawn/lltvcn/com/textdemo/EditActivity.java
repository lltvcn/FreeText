package drawn.lltvcn.com.textdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.lltvcn.freefont.core.view.STextView;
import com.lltvcn.freefont.core.layer.FontStyle;
import drawn.lltvcn.com.textdemo.data.BgData;
import com.lltvcn.freefont.core.data.DrawData;
import drawn.lltvcn.com.textdemo.data.FontData;
import drawn.lltvcn.com.textdemo.data.FontStyleData;
import com.lltvcn.freefont.core.data.IShaderData;
import com.lltvcn.freefont.core.data.LayerData;
import com.lltvcn.freefont.core.data.LineData;
import com.lltvcn.freefont.core.data.ShadeRadiusParam;
import com.lltvcn.freefont.core.data.ShaderBitmapParam;
import com.lltvcn.freefont.core.data.ShaderLinearParam;
import com.lltvcn.freefont.core.data.ShaderSweepParam;
import drawn.lltvcn.com.textdemo.controller.AddLayerViewController;
import drawn.lltvcn.com.textdemo.controller.EditParamViewController;
import drawn.lltvcn.com.textdemo.controller.EditTxtController;
import drawn.lltvcn.com.textdemo.controller.SelectController;
import drawn.lltvcn.com.util.FileUtil;
import drawn.lltvcn.com.util.FontUtil;
import drawn.lltvcn.com.util.ZipUtil;

/**
 * Created by zhaolei on 2017/10/13.
 */

public class EditActivity extends Activity{

    private STextView shadeTextView;
    private CheckBox ckSLine,ckSBm,ckSRadius,ckSSweep;
    private EditTxtController editTxtController;
    private AddLayerViewController addLayerController;
    private EditParamViewController editParamViewController;
    private SelectController selectController;
    private DrawData drawData = new DrawData();
    private String[] fontNames;
    private GridView gv;
    private View btnComLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_general_layer);
        shadeTextView = (STextView) findViewById(R.id.tv);
        btnComLayer = findViewById(R.id.btn_comp_layer);
        shadeTextView.setLocalSourcePath(FileUtil.getImgDir());
        ckSBm = (CheckBox) findViewById(R.id.ck_shader_main_bm);
        ckSLine = (CheckBox) findViewById(R.id.ck_shader_main_linear);
        ckSRadius = (CheckBox) findViewById(R.id.ck_shader_main_radius);
        ckSSweep = (CheckBox) findViewById(R.id.ck_shader_main_sweep);
        drawData.layers = new ArrayList<>();
//        drawData.indexColors = new IndexParam<>();
//        drawData.indexColors.rule = IndexParam.Rule.Revert.name();
//        drawData.indexColors.datas = new String[5];
//        drawData.indexColors.datas[0] = "ffff00";
//        drawData.indexColors.datas[1] = "ffcc00";
//        drawData.indexColors.datas[2] = "ff9900";
//        drawData.indexColors.datas[3] = "ff3300";
//        drawData.indexColors.datas[4] = "ff0000";

        gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(new MyAdapter());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addLayerController.show(drawData.layers.get(position),layerAdd);
            }
        });

        addLayerController = new AddLayerViewController();
        addLayerController.init((ViewGroup) findViewById(R.id.fl_cotainer));

        selectController = new SelectController();
        selectController.init((ViewGroup) findViewById(R.id.fl_cotainer),new EditParamViewController.VisiableListener() {
            @Override
            public void onVisable(boolean visiable) {
                if(!addLayerController.isShowing()){
                    return;
                }
                if(visiable){
                    addLayerController.dismiss();
                }else{
                    addLayerController.show();
                }
            }
        });

        editParamViewController = new EditParamViewController();
        editParamViewController.init((ViewGroup) findViewById(R.id.fl_cotainer), new EditParamViewController.VisiableListener() {
            @Override
            public void onVisable(boolean visiable) {
                if(!addLayerController.isShowing()){
                    return;
                }
                if(visiable){
                    addLayerController.dismiss();
                }else{
                    addLayerController.show();
                }
            }
        });

        editTxtController = new EditTxtController();
        editTxtController.init((ViewGroup) findViewById(R.id.fl_cotainer));
        fontNames = FontUtil.getFontNames();
    }

    public void clickTxt(View view){
        final TextView tv = (TextView) view;
        EditTxtController.OnConfirmListener listener = null;
        switch (view.getId()){
            case R.id.tv:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        shadeTextView.setText(content);
                    }
                };
                break;
            case R.id.et_txt_size:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        if(!TextUtils.isEmpty(content)){
                            tv.setText(content);
                            shadeTextView.setTextSize(Float.parseFloat(content));
//                            notifyDataChange();
                        }
                    }
                };
                break;
            case R.id.et_txt_color:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        try{
                            tv.setText(content);
                            if(!TextUtils.isEmpty(content)){
                                int color = Color.parseColor("#"+content);
                                shadeTextView.setTextColor(color);
                            }else {
                                shadeTextView.setTextColor(Color.BLACK);
                            }
                            notifyDataChange();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(EditActivity.this,"颜色格式错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                break;
            case R.id.tv_width:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        try{
                            tv.setText(content);
                            if(TextUtils.isEmpty(content)){
                                drawData.width = null;
                            }else {
                                drawData.width = Float.valueOf(content);
                            }
                            notifyDataChange();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                break;
            case R.id.tv_height:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        try{
                            tv.setText(content);
                            if(TextUtils.isEmpty(content)){
                                drawData.height = null;
                            }else {
                                drawData.height = Float.valueOf(content);
                            }
                            notifyDataChange();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                break;
            case R.id.et_txt_font:
                selectController.show(fontNames, false, new SelectController.ResultListener() {
                    @Override
                    public void onConfrim(ArrayList<String> result) {
                        if(result!=null&&result.size()>0){
                            tv.setText(result.get(0));
                            shadeTextView.setTypeface(FontUtil.getTypeface(result.get(0)));
//                            drawData.fontStyle =  result.get(0);
                        }else{
                            tv.setText(null);
//                            drawData.fontStyle =  null;
                            shadeTextView.setTypeface(null);
                        }
                        notifyDataChange();
                    }
                });
                return;
            case R.id.et_font_style:
                FontStyleData fd = new FontStyleData();
                editParamViewController.setData(fd).setCallBack(new EditParamViewController.Call<FontStyleData>() {
                    @Override
                    public void onConfirm(FontStyleData data) {
                        if(data.fontStyle!=null){
                            tv.setText(data.fontStyle.name());
                            drawData.fontStyle = data.fontStyle.name();
                            notifyDataChange();
                        }else{
                            tv.setText(FontStyle.Normal.name());
                            drawData.fontStyle = FontStyle.Normal.name();
                            notifyDataChange();
                        }
                    }
                }).show();
                return;
            case R.id.tv_bg:
                final BgData bg = new BgData();
                bg.bitmap = drawData.bgImg;
                bg.bgColor = drawData.bgColor;
                editParamViewController.setData(bg).setCallBack(new EditParamViewController.Call<BgData>() {
                    @Override
                    public void onConfirm(BgData data) {
                        drawData.bgColor = data.bgColor;
                        drawData.bgImg = data.bitmap;
                        String str = data.bitmap==null?("颜色："+data.bgColor):(data.bitmap+"--过滤颜色："+data.bgColor);
                        tv.setText(str);
                        notifyDataChange();

                    }
                }).show();
                break;
            default:
                listener = addLayerController.onClickTxt(tv);
                break;
        }
        if(listener!=null)
            editTxtController.show(listener);
    }

    public void onCK(View view){
        Object data = null;
        EditParamViewController.Call call = null;
        IShaderData shaderData = null;
        switch (view.getId()){
            case R.id.ck_shader_main_bm:
                shaderData = new ShaderBitmapParam();
                break;
            case R.id.ck_shader_main_linear:
                shaderData = new ShaderLinearParam();
                break;
            case R.id.ck_shader_main_radius:
                shaderData = new ShadeRadiusParam();
                break;
            case R.id.ck_shader_main_sweep:
                shaderData = new ShaderSweepParam();
                break;
            default:
                AddLayerViewController.DataHolder holder = addLayerController.onCK((CheckBox) view);
                if(holder!=null){
                    data = holder.getData();
                    call = holder;
                }
        }
        if(call!=null&&data!=null){
            editParamViewController.setData(data).setCallBack(call).show();
        }else if(shaderData!=null){
            if(!((CheckBox)view).isChecked()){
                drawData.shaderParam = null;
                notifyDataChange();
                return;
            }
            ckSSweep.setChecked(false);
            ckSRadius.setChecked(false);
            ckSBm.setChecked(false);
            ckSLine.setChecked(false);
            editParamViewController.setData(shaderData).setCallBack(new EditParamViewController.Call<IShaderData>() {

                @Override
                public void onConfirm(IShaderData data) {
                    drawData.shaderParam = data.toShaderParam();
                    if(drawData.shaderParam.radiusParam!=null){
                        ckSRadius.setChecked(true);
                    }else if(drawData.shaderParam.sweepParam!=null){
                        ckSSweep.setChecked(true);
                    }else if(drawData.shaderParam.linearParam!=null){
                        ckSLine.setChecked(true);
                    }else if(drawData.shaderParam.bitmapParam!=null){
                        ckSBm.setChecked(true);
                    }
                    notifyDataChange();
                }
            }).show();
        }else {
            notifyDataChange();
        }
    }


    private AddLayerViewController.LayerInterface layerAdd = new AddLayerViewController.LayerInterface() {
        @Override
        public void onAdd(LayerData data) {
            drawData.layers.add(data);
            if(TextUtils.isEmpty(data.name)){
                switch (data.type){
                    case LayerData.TYPE_IMG:
                        data.name = drawData.layers.size()+"层-图";
                        break;
                    case LayerData.TYPE_TXT:
                        data.name = drawData.layers.size()+"层-文";
                        break;
                    case LayerData.TYPE_MULTI:
                        data.name = drawData.layers.size()+"层-多";
                        break;
                }
            }
            if(drawData.layers.size()>1){
                btnComLayer.setEnabled(true);
            }else{
                btnComLayer.setEnabled(false);
            }
            notifyDataChange();
            ((MyAdapter)gv.getAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onCalcel() {
            notifyDataChange();
            ((MyAdapter)gv.getAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onDelete(LayerData data) {
            drawData.layers.remove(data);
            if(drawData.layers.size()>1){
                btnComLayer.setEnabled(true);
            }else{
                btnComLayer.setEnabled(false);
            }
            notifyDataChange();
            ((MyAdapter)gv.getAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onSeparate(LayerData data) {
            drawData.layers.remove(data);
            if(data.layerDatas!=null&&data.layerDatas.size()>0){
                drawData.layers.addAll(data.layerDatas);
            }
            if(drawData.layers.size()>1){
                btnComLayer.setEnabled(true);
            }else{
                btnComLayer.setEnabled(false);
            }
            notifyDataChange();
            ((MyAdapter)gv.getAdapter()).notifyDataSetChanged();
        }
    };

    private void notifyDataChange(){
        shadeTextView.setData(drawData);
//        setData(drawData,shadeTextView,shadeTextView.getText().toString());
    }

    public void clickBtn(View v){
        switch (v.getId()){
            case R.id.btn_add_img_layer:
                addLayerController.showAddImg(layerAdd);
                break;
            case R.id.btn_add_txt_layer:
                addLayerController.showAddTxt(layerAdd);
                break;
            case R.id.btn_comp_layer:
                if(drawData.layers!=null&&drawData.layers.size()>0){
                    String[] layerNames = new String[drawData.layers.size()];
                    for (int i = 0; i < layerNames.length; i++) {
                        layerNames[i] = drawData.layers.get(i).name;
                    }
                    selectController.show(layerNames, true, new SelectController.ResultListener() {
                        @Override
                        public void onConfrim(ArrayList<String> result) {
                            if(result!=null&&result.size()>0){
                                LayerData layerData = new LayerData();
                                layerData.type = LayerData.TYPE_MULTI;
                                layerData.layerDatas = new ArrayList<LayerData>();
                                Iterator<LayerData> i = drawData.layers.iterator();
                                while (i.hasNext()){
                                    LayerData data = i.next();
                                    if(result.contains(data.name)){
                                        i.remove();
                                        layerData.layerDatas.add(data);
                                    }
                                }
                                layerAdd.onAdd(layerData);
                            }
                        }
                    });
//                    drawData.layers.remove(drawData.layers.size()-1);
//                    notifyDataChange();
                }
                break;
            case R.id.btn_general_module:
                editTxtController.show(new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        FontData fontData = generalModule();
                        String json = new GsonBuilder().setPrettyPrinting().create().toJson(fontData.drawData);
                        File dir = new File(FileUtil.getDataDirByName(content));
                        dir.deleteOnExit();
                        try {
                            dir.mkdirs();
                            FileWriter writer = new FileWriter(dir.getAbsolutePath()+File.separator+content+".txt",false);
                            writer.write(json);
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(fontData.dependImgs!=null&&fontData.dependImgs.size()>0){
                            File imgDir = new File(dir.getAbsolutePath()+File.separator+"res");
                            imgDir.mkdir();
                            for (Map.Entry<String,String> entry: fontData.dependImgs.entrySet()) {
                                FileUtil.copyFile(new File(entry.getValue()),new File(imgDir.getAbsolutePath()+File.separator+entry.getKey()));
                            }
                            ZipUtil.compress(imgDir.getAbsolutePath(),dir.getAbsolutePath(),content);
                            FileUtil.deleteDir(imgDir);
                        }
                        Toast.makeText(EditActivity.this,"已保存",Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.btn_add_fore_img:
                if(drawData.foreLayers==null){
                    drawData.foreLayers = new ArrayList<>();
                }
                editParamViewController.setData(new LineData()).setCallBack(new EditParamViewController.Call<LineData>() {

                    @Override
                    public void onConfirm(LineData data) {
                        drawData.foreLayers.add(data);
                        notifyDataChange();
                    }
                }).show();
                break;
            case R.id.btn_add_bg_img:
                if(drawData.backLayers==null){
                    drawData.backLayers = new ArrayList<>();
                }
                editParamViewController.setData(new LineData()).setCallBack(new EditParamViewController.Call<LineData>() {

                    @Override
                    public void onConfirm(LineData data) {
                        drawData.backLayers.add(data);
                        notifyDataChange();
                    }
                }).show();
                break;
        }
    }

    private FontData generalModule() {
        FontData data = new FontData();
        data.drawData = drawData;
        data.dependImgs = new HashMap<>();
//        if(!TextUtils.isEmpty(drawData.font)){
//            data.dependFonts = new HashMap<>();
//            data.dependFonts.put(drawData.font,FileUtil.getFontPath(drawData.font));
//        }
        if(!TextUtils.isEmpty(drawData.bgImg)){
            data.dependImgs.put(drawData.bgImg,FileUtil.getImagePathByName(drawData.bgImg));
        }
        if(drawData.foreLayers!=null){
            for (LineData fl:drawData.foreLayers) {
                if(!TextUtils.isEmpty(fl.bitmap)){
                    data.dependImgs.put(fl.bitmap,FileUtil.getImagePathByName(fl.bitmap));
                }
            }
        }
        if(drawData.backLayers!=null){
            for (LineData fl:drawData.backLayers) {
                if(!TextUtils.isEmpty(fl.bitmap)){
                    data.dependImgs.put(fl.bitmap,FileUtil.getImagePathByName(fl.bitmap));
                }
            }
        }
        if(drawData.shaderParam!=null&&drawData.shaderParam.bitmapParam!=null){
            data.dependImgs.put(drawData.shaderParam.bitmapParam.img,FileUtil.getImagePathByName(drawData.shaderParam.bitmapParam.img));
        }
        if(drawData.layers!=null){
            for (LayerData layer:
                 drawData.layers) {
                if(layer.imgs !=null&& layer.imgs.datas!=null){
                    for (int i = 0; i < layer.imgs.datas.length; i++) {
                        data.dependImgs.put(layer.imgs.datas[i],FileUtil.getImagePathByName(layer.imgs.datas[i]));
                    }
                }
                if(layer.paintParam!=null&&layer.paintParam.shaderParam!=null&&layer.paintParam.shaderParam.bitmapParam!=null){
                    if(!TextUtils.isEmpty(layer.paintParam.shaderParam.bitmapParam.img)){
                        data.dependImgs.put(layer.paintParam.shaderParam.bitmapParam.img,FileUtil.getImagePathByName(layer.paintParam.shaderParam.bitmapParam.img));
                    }
                }
            }
        }
        return data;
    }



    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return drawData.layers==null?0:drawData.layers.size();
        }

        @Override
        public LayerData getItem(int position) {
            return drawData.layers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layer,parent,false);
                holder = new ViewHolder();
                holder.tv = (TextView) convertView.findViewById(R.id.tv_layer);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            LayerData layer = getItem(position);
            if(layer.name!=null){
                holder.tv.setText(layer.name);
            }
//            switch (layer.type){
//                case LayerData.TYPE_TXT:
//                    holder.tv.setText("层"+position+"文字");
//                    break;
//                case LayerData.TYPE_IMG:
//                    holder.tv.setText("层"+position+"图片");
//                    break;
//                case LayerData.TYPE_MULTI:
//                    holder.tv.setText("层"+position+"合成");
//                    break;
//            }
            return convertView;
        }
    }

    private class  ViewHolder{
        TextView tv;
    }



}
