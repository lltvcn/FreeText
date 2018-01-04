package drawn.lltvcn.com.textdemo.controller;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lltvcn.freefont.core.data.BlurParam;
import com.lltvcn.freefont.core.data.ClipParam;
import com.lltvcn.freefont.core.data.IDispatchDraw;
import com.lltvcn.freefont.core.data.IShaderData;
import com.lltvcn.freefont.core.data.IndexParam;
import com.lltvcn.freefont.core.data.LayerData;
import com.lltvcn.freefont.core.data.OffsetParam;
import com.lltvcn.freefont.core.layer.LayerDataFactory;
import com.lltvcn.freefont.core.data.ShadeRadiusParam;
import com.lltvcn.freefont.core.data.ShaderBitmapParam;
import com.lltvcn.freefont.core.data.ShaderLinearParam;
import com.lltvcn.freefont.core.data.ShaderParam;
import com.lltvcn.freefont.core.data.ShaderSweepParam;
import com.lltvcn.freefont.core.data.ShadowParam;
import com.lltvcn.freefont.core.data.StokeParam;
import drawn.lltvcn.com.textdemo.R;

/**
 * Created by zhaolei on 2017/10/13.
 */

public class AddLayerViewController {

//    private LayerDataFactory.LayerBuilder layerBuilder;
    private LayerData layerData;
    private ViewGroup parent;
    private TextView tvColor,tvSize,tvFont,tvOffsetX,tvOffsetY,tvRotate,tvScale,tvImgs,tvLayerName;
    private CheckBox ckBlur,ckShadow,ckShadeLine,ckShadeRadi,ckShadeSwee,ckShadeBM,ckStoke,ckClip,ckOffset,ckColors,ckImgColors,ckImgs;
    private View view,btnOk,btnCancel,btnDelete,btnSeparate,llImg,llTxt;

    public void init(ViewGroup parent){
        this.parent = parent;
        this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_layer,parent,false);
        parent.addView(view);
        view.setVisibility(View.GONE);
        tvColor = (TextView) findViewById(R.id.et_layer_txt_color);
        tvSize = (TextView) findViewById(R.id.et_layer_txt_size);
        tvFont = (TextView) findViewById(R.id.et_layer_font);
        tvLayerName = (TextView) findViewById(R.id.et_layer_name);
        tvOffsetX = (TextView) findViewById(R.id.et_offset_x);
        tvOffsetY = (TextView) findViewById(R.id.et_offset_y);
        tvRotate = (TextView) findViewById(R.id.et_rotate);
        tvScale = (TextView) findViewById(R.id.et_scale);
        ckClip = (CheckBox) findViewById(R.id.ck_clip);
        ckImgColors = (CheckBox) findViewById(R.id.ck_colors);
        ckImgs = (CheckBox) findViewById(R.id.ck_imgs);
        ckColors = (CheckBox) findViewById(R.id.ck_color_index);
        ckOffset = (CheckBox) findViewById(R.id.ck_offset);
        ckBlur = (CheckBox) findViewById(R.id.ck_blur);
        ckStoke = (CheckBox) findViewById(R.id.ck_stoke);
        ckShadow = (CheckBox) findViewById(R.id.ck_shadow);
        ckShadeLine = (CheckBox) findViewById(R.id.ck_shader_lin);
        ckShadeRadi = (CheckBox) findViewById(R.id.ck_shader_rad);
        ckShadeSwee = (CheckBox) findViewById(R.id.ck_shader_swee);
        ckShadeBM = (CheckBox) findViewById(R.id.ck_shader_bm);
        btnOk = findViewById(R.id.btn_layer_ok);
        btnCancel = findViewById(R.id.btn_layer_cancel);
        btnDelete = findViewById(R.id.btn_layer_delete);
        btnSeparate = findViewById(R.id.btn_layer_separate);
        llImg = findViewById(R.id.ll_img);
        llTxt = findViewById(R.id.ll_txt);

        btnOk.setOnClickListener(clickListener);
        btnCancel.setOnClickListener(clickListener);
        btnDelete.setOnClickListener(clickListener);
        btnSeparate.setOnClickListener(clickListener);

    }
    private boolean isShowing;
    private void preparePaint(){
        if(layerData.paintParam==null){
            layerData.paintParam = new LayerData.PaintParam();
        }
    }

    public boolean isShowing(){
        return isShowing;
    }

    private void prepareShader(){
        preparePaint();
        if(layerData.paintParam.shaderParam == null){
            layerData.paintParam.shaderParam = new ShaderParam();
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_layer_ok:
                    if(layerInterface!=null){
                        layerInterface.onAdd(layerData);
                    }
                    break;
                case R.id.btn_layer_cancel:
                    if(layerInterface!=null){
                        layerInterface.onCalcel();
                    }
                    break;
                case R.id.btn_layer_separate:
                    if(layerInterface!=null){
                        layerInterface.onSeparate(layerData);
                    }
                    break;
                case R.id.btn_layer_delete:
                    if(layerInterface!=null){
                        layerInterface.onDelete(layerData);
                    }
                    break;
            }
            dismiss();
            reset();
        }
    };

    public DataHolder onCK(CheckBox ck){
        if(ck.isChecked()){
            IShaderData shaderData = null;
            IDispatchDraw dispatchDraw = null;
            IndexParam<String> indexParam = null;
            switch (ck.getId()){
                case R.id.ck_blur:
                    DataHolder blur = new DataHolder(new BlurParam());
                    blur.cl = new CL<BlurParam>() {

                        @Override
                        public void onConfirm(BlurParam data) {
                            preparePaint();
                            layerData.paintParam.blurParam = data;
                        }
                    };
                    return blur;
                case R.id.ck_shadow:
                    DataHolder dataProvider = new DataHolder(new ShadowParam());
                    dataProvider.cl = new CL<ShadowParam>() {

                        @Override
                        public void onConfirm(ShadowParam data) {
                            preparePaint();
                            layerData.paintParam.shadowParam = data;
//                            layerBuilder.shadow(data.radius,data.x,data.y, data.color);
                        }
                    };
                    return dataProvider;
                case R.id.ck_stoke:
                    DataHolder stoke = new DataHolder(new StokeParam());
                    stoke.cl = new CL<StokeParam>() {

                        @Override
                        public void onConfirm(StokeParam data) {
                            preparePaint();
                            layerData.paintParam.stokeParam = data;
//                            layerBuilder.stoke(data.width, Paint.Join.valueOf(data.join));
                        }
                    };
                    return stoke;
                case R.id.ck_shader_bm:
                    shaderData = new ShaderBitmapParam();
                    break;
                case R.id.ck_shader_lin:
                    shaderData = new ShaderLinearParam();
                    break;
                case R.id.ck_shader_rad:
                    shaderData = new ShadeRadiusParam();
                    break;
                case R.id.ck_shader_swee:
                    shaderData = new ShaderSweepParam();
                    break;
                case R.id.ck_clip:
                    dispatchDraw = new ClipParam();
                    break;
                case R.id.ck_offset:
                    dispatchDraw = new OffsetParam();
                    break;
                case R.id.ck_color_index:
                    DataHolder colorIndex = new DataHolder(new IndexParam<>());
                    reSetView();
                    colorIndex.cl = new CL<IndexParam>() {

                        @Override
                        public void onConfirm(IndexParam data) {
                            preparePaint();
                            if(data!=null&&data.available()){
                                layerData.paintParam.colors = data;
                            }else {
                                layerData.paintParam.colors = null;
                            }
                        }
                    };
                    return colorIndex;
                case R.id.ck_colors:
                    DataHolder imgColors = new DataHolder(new IndexParam<>());
                    reSetView();
                    imgColors.cl = new CL<IndexParam>() {

                        @Override
                        public void onConfirm(IndexParam data) {
                            if(data!=null&&data.available()){
                                layerData.colors = data;
                            }else {
                                layerData.colors = null;
                            }
                        }
                    };
                    return imgColors;
                case R.id.ck_imgs:
                    DataHolder imgs = new DataHolder(new IndexParam<>());
                    reSetView();
                    imgs.cl = new CL<IndexParam>() {

                        @Override
                        public void onConfirm(IndexParam data) {
                            if(data!=null&&data.available()){
                                layerData.imgs = data;
                            }else {
                                layerData.imgs = null;
                            }
                        }
                    };
                    return imgs;
            }
            if(shaderData!=null){
                DataHolder shader = new DataHolder(shaderData);
                reSetView();
                shader.cl = new CL<IShaderData>() {

                    @Override
                    public void onConfirm(IShaderData data) {
                        prepareShader();
                        layerData.paintParam.shaderParam = data.toShaderParam();
                    }
                };
                return shader;
            }else if(dispatchDraw!=null){
                DataHolder shader = new DataHolder(dispatchDraw);
                reSetView();
                shader.cl = new CL<IDispatchDraw>() {

                    @Override
                    public void onConfirm(IDispatchDraw data) {
                        layerData.drawParam = data.toDispatchDrawParam();
                    }
                };
                return shader;
            }
        }else {
            switch (ck.getId()){
                case R.id.ck_blur:
                    preparePaint();
                    layerData.paintParam.blurParam = null;
                    break;
                case R.id.ck_shadow:
                    preparePaint();
                    layerData.paintParam.shadowParam = null;
                    break;
                case R.id.ck_stoke:
                    preparePaint();
                    layerData.paintParam.stokeParam = null;
                    break;
                case R.id.ck_shader_bm:
                case R.id.ck_shader_lin:
                case R.id.ck_shader_rad:
                case R.id.ck_shader_swee:
                    preparePaint();
                    layerData.paintParam.shaderParam = null;
                    break;
                case R.id.ck_clip:
                case R.id.ck_offset:
                    layerData.drawParam = null;
                    break;
                case R.id.ck_color_index:
                    preparePaint();
                    layerData.paintParam.colors = null;
                    break;
                case R.id.ck_colors:
                    layerData.colors = null;
                    break;
                case R.id.ck_imgs:
                    layerData.imgs = null;
                    break;
            }
            reSetView();
            setData();
        }
        return null;
    }

    public EditTxtController.OnConfirmListener onClickTxt(final TextView view){
        EditTxtController.OnConfirmListener listener = null;
        switch (view.getId()){
            case R.id.et_layer_txt_color:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        view.setText(content);
                        if(!TextUtils.isEmpty(content)){
                            preparePaint();
                            layerData.paintParam.color = content;
                        }
                    }
                };
                break;
            case R.id.et_layer_txt_size:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        view.setText(content);
                        if(!TextUtils.isEmpty(content)){
                            preparePaint();
                            layerData.paintParam.relativeSize = Float.parseFloat(content);
                        }
                    }
                };
                break;
            case R.id.et_offset_x:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        view.setText(content);
                        if(!TextUtils.isEmpty(content)){
                            layerData.offsetX = Float.parseFloat(content);
                        }
                    }
                };
                break;
            case R.id.et_offset_y:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        view.setText(content);
                        if(!TextUtils.isEmpty(content)){
                            layerData.offsetY = Float.parseFloat(content);
                        }
                    }
                };
                break;
            case R.id.et_rotate:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        view.setText(content);
                        if(!TextUtils.isEmpty(content)){
                            layerData.degree = Float.parseFloat(content);
                        }
                    }
                };
                break;
            case R.id.et_scale:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        view.setText(content);
                        if(!TextUtils.isEmpty(content)){
                            layerData.scale = Float.parseFloat(content);
                        }

                    }
                };
                break;
            case R.id.et_layer_name:
                listener = new EditTxtController.OnConfirmListener() {
                    @Override
                    public void onConfirm(String content) {
                        view.setText(content);
                        if(!TextUtils.isEmpty(content)){
                            layerData.name = content;
                        }

                    }
                };
                break;
            case R.id.et_layer_txt_font_style:
//                listener = new EditTxtController.OnConfirmListener() {
//                    @Override
//                    public void onConfirm(String content) {
//                        view.setText(content);
//                        if(!TextUtils.isEmpty(content)){
//                            stokeWidth = Float.parseFloat(content);
//                            // TODO: 2017/10/16 这里临时默认给值了
//                            preparePaint();
//                            layerData.paintParam.stokeParam = new StokeParam();
//                            layerData.paintParam.stokeParam.width = stokeWidth;
//                            layerData.paintParam.stokeParam.join = Paint.Join.ROUND.name();
////                            layerBuilder.stoke(stokeWidth, Paint.Join.ROUND);
////                            if(!TextUtils.isEmpty(stokeJoin))
////                                // TODO: 2017/10/16 这里l
////                                layerBuilder.stoke(stokeWidth, Paint.Join.ROUND);
//                        }
//                    }
//                };
                break;

        }
        return listener;
    }


    public interface LayerInterface{
        void onAdd(LayerData data);
        void onCalcel();
        void onDelete(LayerData data);
        void onSeparate(LayerData data);
    }

    private LayerInterface layerInterface;
    private boolean isNew;

    public void showAddImg(LayerInterface layerInterface){
        isNew = true;
        show(LayerDataFactory.createImgLayerBuilder(null, IndexParam.Rule.Normal).create(),layerInterface);
    }

    public void showAddTxt(LayerInterface layerInterface){
        isNew = true;
        show(LayerDataFactory.createTxtLayerBuilder().create(),layerInterface);
    }


    public void show(LayerData data,LayerInterface layerInterface){
        this.layerInterface = layerInterface;
        this.layerData = data;
        switch (data.type){
            case LayerData.TYPE_IMG:
                llTxt.setVisibility(View.GONE);
                llImg.setVisibility(View.VISIBLE);
                break;
            case LayerData.TYPE_TXT:
                llTxt.setVisibility(View.VISIBLE);
                llImg.setVisibility(View.GONE);
                break;
            case LayerData.TYPE_MULTI:
                llTxt.setVisibility(View.GONE);
                llImg.setVisibility(View.GONE);
                break;
        }
        if(isNew){
            btnOk.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            btnSeparate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }else{
            if(data.type == LayerData.TYPE_MULTI){
                btnSeparate.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);
                btnOk.setVisibility(View.GONE);
            }else{
                btnSeparate.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                btnOk.setVisibility(View.GONE);
            }
        }
        show();
        isShowing = true;
        reSetView();
        setData();
    }

    public void show(){
        view.setVisibility(View.VISIBLE);
        parent.setVisibility(View.VISIBLE);
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1,Animation.RELATIVE_TO_SELF,0);
        ta.setDuration(400);
        view.startAnimation(ta);
    }

    public void dismiss(){
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,1);
        ta.setDuration(400);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                dismissParent();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(ta);
    }

    private void dismissParent(){
        for (int i = 0; i < parent.getChildCount(); i++) {
            if(parent.getChildAt(i).getVisibility() == View.VISIBLE){
                return;
            }
        }
        parent.setVisibility(View.GONE);
    }

    private void reset(){
        isNew = false;
        isShowing = false;
        reSetView();
    }


    private void reSetView() {
      reSetView((ViewGroup) view);
    }

    private void reSetView(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if(view instanceof CheckBox){
                ((CheckBox) view).setChecked(false);
            }else if(view instanceof Button){

            }else if(view instanceof TextView){
                ((TextView)view).setText(null);
            }else if(view instanceof ViewGroup){
                reSetView((ViewGroup) view);
            }
        }
    }

    private void setData(){
        tvLayerName.setText(layerData.name);
        if(layerData.scale>0){
            tvScale.setText(String.valueOf(layerData.scale));
        }else {
            tvScale.setText(null);
        }
        if(layerData.degree>0){
            tvRotate.setText(String.valueOf(layerData.degree));
        }else {
            tvRotate.setText(null);
        }
        if(layerData.offsetY>0){
            tvOffsetY.setText(String.valueOf(layerData.offsetY));
        }else {
            tvOffsetY.setText(null);
        }
        if(layerData.offsetX>0){
            tvOffsetX.setText(String.valueOf(layerData.offsetX));
        }else {
            tvOffsetX.setText(null);
        }
        if(layerData.imgs!=null&&layerData.imgs.available()){
            ckImgs.setChecked(true);
        }else{
            ckImgs.setChecked(false);
        }
        if(layerData.colors!=null&&layerData.colors.available()){
            ckImgColors.setChecked(true);
        }else {
            ckImgColors.setChecked(false);
        }
        if(layerData.drawParam!=null){
            if(layerData.drawParam.clipParam!=null){
                ckClip.setChecked(true);
            }else {
                ckClip.setChecked(false);
            }
            if(layerData.drawParam.offsetParam!=null){
                ckOffset.setChecked(true);
            }else {
                ckOffset.setChecked(false);
            }
        }
        if(layerData.paintParam!=null){
            tvColor.setText(layerData.paintParam.color==null?"":layerData.paintParam.color.toString());
            tvFont.setText(layerData.paintParam.font);
            tvSize.setText(layerData.paintParam.relativeSize==null?"":layerData.paintParam.relativeSize.toString());
            if(layerData.paintParam.colors!=null){
                ckColors.setChecked(true);
            }else {
                ckColors.setChecked(false);
            }
            if(layerData.paintParam.shaderParam!=null){
                if(layerData.paintParam.shaderParam.bitmapParam!=null){
                    ckShadeBM.setChecked(true);
                }
                if(layerData.paintParam.shaderParam.radiusParam!=null){
                    ckShadeRadi.setChecked(true);
                }
                if(layerData.paintParam.shaderParam.linearParam!=null){
                    ckShadeLine.setChecked(true);
                }
                if(layerData.paintParam.shaderParam.sweepParam!=null){
                    ckShadeSwee.setChecked(true);
                }
            }
            if(layerData.paintParam.shadowParam!=null){
                ckShadow.setChecked(true);
            }
            if(layerData.paintParam.blurParam!=null){
                ckBlur.setChecked(true);
            }
            if (layerData.paintParam.stokeParam!=null){
                ckStoke.setChecked(true);
            }
        }
    }

    private View findViewById(int id){
        return view.findViewById(id);
    }



    public class DataHolder<T> implements EditParamViewController.Call<T> {

        private CL<T> cl;
        private T data;

        DataHolder(T data){
            this.data = data;
        }

        public T getData(){
            return data;
        }

        @Override
        public void onConfirm(T data) {
            if(data==null) return;
            if(cl!=null){
                cl.onConfirm(data);
            }
            setData();
        }

    }
    private interface CL<T>{
        public void onConfirm(T data);
    }
}
