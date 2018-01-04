package com.lltvcn.freefont.core.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.view.animation.Animation;
import android.widget.TextView;

import com.lltvcn.freefont.core.animation.A;
import com.lltvcn.freefont.core.animation.Animation2IA;
import com.lltvcn.freefont.core.animation.BaseAnimation2IA;
import com.lltvcn.freefont.core.animation.ICanvasTransform;
import com.lltvcn.freefont.core.animation.TA;
import com.lltvcn.freefont.core.animation.TAnimation;
import com.lltvcn.freefont.core.animation.TAnimationQueen;
import com.lltvcn.freefont.core.data.AniData;
import com.lltvcn.freefont.core.data.DrawData;
import com.lltvcn.freefont.core.data.IndexParam;
import com.lltvcn.freefont.core.data.LayerData;
import com.lltvcn.freefont.core.data.LineData;
import com.lltvcn.freefont.core.layer.ClipDrawer;
import com.lltvcn.freefont.core.layer.FontStyle;
import com.lltvcn.freefont.core.layer.ILayer;
import com.lltvcn.freefont.core.layer.ImgLayer;
import com.lltvcn.freefont.core.layer.LayerSpan;
import com.lltvcn.freefont.core.layer.MultiLayer;
import com.lltvcn.freefont.core.layer.OffsetDrawer;
import com.lltvcn.freefont.core.layer.PaintHandler;
import com.lltvcn.freefont.core.layer.SourceLoader;
import com.lltvcn.freefont.core.layer.TxtLayer;
import com.lltvcn.freefont.core.linedrawer.Gravity;
import com.lltvcn.freefont.core.linedrawer.LineImgBackgroundDrawer;
import com.lltvcn.freefont.core.linedrawer.LineImgForegroundDrawer;
import com.lltvcn.freefont.core.util.CU;

import java.io.File;

/**
 * Created by zhaolei on 2017/10/18.
 */

public class STextView extends ShadeTextView {
    private CharSequence outTxt;
    private String sourcePath;
    private TA ta;


    public STextView(Context context) {
        this(context,null);
    }


    public STextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        outTxt = text;
        if(drawData!=null){
            SpannableStringBuilder builder = buildString(drawData,outTxt);
            super.setText(builder, type);
        }else {
            super.setText(text,type);
        }
    }


    private DrawData drawData;

    public void setData(DrawData data){
        this.drawData = data;
        if(ta!=null){
            ta.stop();
            clearAnimation();
            ta = null;
        }
        notifyDataChange();
    }


    public void notifyDataChange(){
        handleData(drawData);
        if(outTxt!=null&&outTxt.length()>0){
            setText(outTxt);
        }
        postInvalidate();
    }

    private void handleData(DrawData drawData){
        if(drawData==null)
            return;
//        if(drawData.color !=null)
//            setTextColor(drawData.color);
//        if(drawData.size !=null)
//            setTextSize(TypedValue.COMPLEX_UNIT_PX,drawData.size);
        if(drawData.shaderParam!=null){
            final LayerData.PaintParam param = new LayerData.PaintParam();
            param.shaderParam = drawData.shaderParam;
            post(new Runnable() {
                @Override
                public void run() {
                    new PaintHandler(param,bitmapLoader,fontLoader).handlePaint(0,getPaint(),new RectF(0,0,getWidth(),getHeight()));
                }
            });
        }else{
            getPaint().setShader(null);
        }
//        Typeface typeface = null;
//        Log.i("jjiis","hasBitmap"+drawData.bgImg);
//        if(!TextUtils.isEmpty(drawData.font)){
//            Log.i("jjiis","font"+drawData.font);
//            typeface = fontLoader.loadByName(drawData.font);
//        }
        if(!TextUtils.isEmpty(drawData.fontStyle)){
            setTypeface(getTypeface(), FontStyle.valueOf(drawData.fontStyle).ordinal());
        }else{
            setTypeface(getTypeface());
        }
//        else {
//            setTypeface(typeface);
//        }
//        tv.setTypeface(Typeface.createFromAsset(getAssets(),"08华康娃娃体W5.TTF"));
        if(!TextUtils.isEmpty(drawData.bgImg)){
            Log.i("jjiis","hasBitmap"+drawData.bgImg);
            Drawable drawable = drawableLoader.loadByName(drawData.bgImg);
            if(!TextUtils.isEmpty(drawData.bgColor)){
                CU.filterDrawable(drawable,CU.toInt(drawData.bgColor));
            }
            setBackgroundDrawable(drawable);
        }else if(!TextUtils.isEmpty(drawData.bgColor)){
            setBackgroundColor(CU.toInt(drawData.bgColor));
        }else {
            setBackgroundDrawable(null);
        }
        setGravity(android.view.Gravity.CENTER);
    }

    private SpannableStringBuilder buildString(final DrawData drawData, CharSequence content){
        float width = drawData.width==null?0:drawData.width;
        float height = drawData.height==null?1.0f:drawData.height;
        LayerSpan span = new LayerSpan(height,width);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);

        if(drawData.aniType!=null){
            ta = A.createTAByType(drawData.aniType,this);
            if(ta != null){
                if(ta instanceof BaseAnimation2IA){
                    span.setCanvasTransform((ICanvasTransform) ta.getValue());
                }else if(ta instanceof Animation2IA){
                    setAnimation((Animation) ta.getValue());
                }
            }
        }

        if(drawData.layers!=null&&drawData.layers.size()>0){
            ILayer layer = null;
            for (LayerData layerData:drawData.layers) {
                layer = createLayer(layerData);
                span.addLayer(layer);
            }
            builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if(drawData.foreLayers!=null&&drawData.foreLayers.size()>0){
            for (LineData lineData:drawData.foreLayers) {
                Drawable drawable = null;
                if(!TextUtils.isEmpty(lineData.bitmap)){
                    drawable = drawableLoader.loadByName(lineData.bitmap);
                    if(lineData.color != null ){
                        CU.filterDrawable(drawable,lineData.color);
                    }
                }else if(lineData.color != null){
                    drawable = new ColorDrawable(lineData.color);
                }
                if(drawable!=null){
                    LineImgForegroundDrawer drawer = new LineImgForegroundDrawer(drawable,lineData.rh, Gravity.valueOf(lineData.gravity));
                    builder.setSpan(drawer,0,builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        if(drawData.backLayers!=null&&drawData.backLayers.size()>0){
            for (LineData lineData:drawData.backLayers) {
                Drawable drawable = null;
                if(!TextUtils.isEmpty(lineData.bitmap)){
                    drawable = drawableLoader.loadByName(lineData.bitmap);
                    if(lineData.color!=null){
                        CU.filterDrawable(drawable,lineData.color);
                    }
                }else if(lineData.color!=null){
                    drawable = new ColorDrawable(lineData.color);
                }
                if(drawable!=null){
                    LineImgBackgroundDrawer drawer = new LineImgBackgroundDrawer(drawable,lineData.rh, Gravity.valueOf(lineData.gravity));
                    builder.setSpan(drawer,0,builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
//        super.setText(builder);
        return builder;
    }



    private ILayer createLayer(LayerData layerData) {
        ILayer layer = null;
        if(layerData.type == LayerData.TYPE_IMG){
            layer = new ImgLayer(new DrawableLoaderImpl(layerData,drawableLoader));
        }else if(layerData.type == LayerData.TYPE_TXT){
            layer = new TxtLayer();
        }else if(layerData.type == LayerData.TYPE_MULTI){
            MultiLayer mlayer = new MultiLayer();
            if(layerData.layerDatas!=null&&layerData.layerDatas.size()>0){
                for (int i = 0; i < layerData.layerDatas.size(); i++) {
                    mlayer.add(createLayer(layerData.layerDatas.get(i)));
                }
            }
            layer = mlayer;
        }
        if(layerData.drawParam!=null){
            if(layerData.drawParam.clipParam!=null){
                layer.setDrawDispatcher(new ClipDrawer(layerData.drawParam.clipParam.span));
            }else if(layerData.drawParam.offsetParam!=null){
                layer.setDrawDispatcher(new OffsetDrawer(layerData.drawParam.offsetParam.positions,layerData.drawParam.offsetParam.offsets));
            }
        }
        if(layerData.paintParam!=null){
            layer.setPaintHandler(new PaintHandler(layerData.paintParam,bitmapLoader,fontLoader));
        }
        if(layerData.offsetX!=0||layerData.offsetY!=0){
            layer.offset(layerData.offsetX,layerData.offsetY);
        }
        if(layerData.scale>0&&layerData.scale!=1){
            layer.scale(layerData.scale);
        }
        if(layerData.degree!=0){
            layer.rotate(layerData.degree);
        }
        return layer;
    }

    public void setLocalSourcePath(String path){
        sourcePath = path.endsWith(File.separator)?path:(path+File.separator);
        notifyDataChange();
    }

    private LruCache<String,Drawable> drawableLruCache = new LruCache<>(10);

    private SourceLoader<Drawable> drawableLoader = new SourceLoader<Drawable>() {
        @Override
        public Drawable loadByName(String name) {
            Drawable drawable = drawableLruCache.get(name);
            if(drawable==null){
                drawable = Drawable.createFromPath(getLocalFileName(name));
                if(drawable!=null)
                    drawableLruCache.put(name,drawable);
            }
            return drawable;
        }

        public String getLocalFileName(String name) {
            return TextUtils.isEmpty(sourcePath)?null:(sourcePath+name);
        }
    };

    private SourceLoader<Bitmap> bitmapLoader = new SourceLoader<Bitmap>() {
        @Override
        public Bitmap loadByName(String name) {
            Drawable drawable = drawableLoader.loadByName(name);
            if(drawable!=null && drawable instanceof BitmapDrawable){
                return ((BitmapDrawable) drawable).getBitmap();
            }else {
                return null;
            }
        }

    };

    private SourceLoader<Typeface> fontLoader = new SourceLoader<Typeface>() {
        @Override
        public Typeface loadByName(String name) {
            //当前版本先不支持多种字体
            return null;
//            String file = getLocalFileName(name);
//            if(!TextUtils.isEmpty(file)&&new File(file).exists()){
//                Log.i("jsi",file);
//                return Typeface.createFromFile(file);
//            }else{
//                return null;
//            }
        }

    };

    private class DrawableLoaderImpl implements ImgLayer.DrawableLoader{
        private IndexParam<String> colors;
        private IndexParam<String> imgs;
        private SourceLoader<Drawable> bitmapSourceLoader;

        public DrawableLoaderImpl(LayerData data,SourceLoader<Drawable> sourceLoader){
            colors = data.colors;
            imgs = data.imgs;
            bitmapSourceLoader = sourceLoader;
        }

        @Override
        public Drawable getDrawable(int index) {
            Drawable drawable = null;
            if(imgs!=null&&imgs.available()){
                drawable = bitmapSourceLoader.loadByName(imgs.getDataByIndex(index));
                if(colors!=null&&colors.available()){
                    CU.filterDrawable(drawable,CU.toInt(colors.getDataByIndex(index)));
                }
            }else {
                if(colors!=null&&colors.available()){
                    drawable = new ColorDrawable(CU.toInt(colors.getDataByIndex(index)));
                }
            }
            return drawable;
        }
    }



    public TA getTAnimation(){
        return ta;
    }




}
