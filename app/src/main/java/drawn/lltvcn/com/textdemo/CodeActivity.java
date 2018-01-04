package drawn.lltvcn.com.textdemo;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import java.util.ArrayList;

import com.lltvcn.freefont.core.animation.TAnimation;
import com.lltvcn.freefont.core.animation.TAnimationQueen;
import com.lltvcn.freefont.core.linedrawer.BackgroundDrawer;
import com.lltvcn.freefont.core.linedrawer.Gravity;
import com.lltvcn.freefont.core.linedrawer.LineImgForegroundDrawer;
import com.lltvcn.freefont.core.data.DrawData;
import com.lltvcn.freefont.core.layer.ILayer;
import com.lltvcn.freefont.core.layer.ImgLayer;
import com.lltvcn.freefont.core.data.LayerData;
import com.lltvcn.freefont.core.layer.LayerDataFactory;
import com.lltvcn.freefont.core.layer.PaintHandler;
import com.lltvcn.freefont.core.layer.TxtLayer;
import com.lltvcn.freefont.core.layer.LayerSpan;
import com.lltvcn.freefont.core.util.CU;

/**
 * Created by zhaolei on 2017/9/16.
 */

public class CodeActivity extends Activity{

    TextView tv;
    Paint paint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_code);
        tv = (TextView) findViewById(R.id.tv);
        tv.setBackgroundColor(Color.YELLOW);
        SpannableStringBuilder string = new SpannableStringBuilder();
        string.append("正正正正\n正正正正正\n正正正\n正正正正正\n正正正正正正正正正正正正正正正");
//        string.append("一二三四五六七八九十1234567890abcdefghijklmnABCDEFGHIJKLMNOPQRSTUVWXYZ");
//        string.setSpan(new RelativeSizeSpan(2.0f),0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        string.setSpan(new RelativeSizeSpan(3.0f),2,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        RelativeSizeSpan rs = new RelativeSizeSpan(1.0f);
//        string.setSpan(new RelativeSizeSpan(2.0f),1,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        string.setSpan(new MyImgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f),tv),0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        for (int i = 0; i < string.length(); i++) {
//            string.setSpan(new RelativeSizeSpan(1.0f),0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        BackgroundDrawer drawer = new BackgroundDrawer() {
            @Override
            public void draw(Canvas c, Paint p, float left, int top, float right, int bottom,int baseLine) {
                paint.set(p);
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.FILL);
                c.drawRect(left,top,right,bottom,paint);
            }
        };
//        string.setSpan(new LeadingMarginSpanImpl(),9,13,);
//        string.setSpan(new SubscriptSpan(),1,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        string.setSpan(new RelativeSizeSpan(0.2f),1,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


//        string.setSpan(new LeadingMarginSpanImpl(),0,1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);暂时不支持leadingmargin 和 tab

//        string.setSpan(new RelativeSizeSpan(2.0f),1,7,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        string.setSpan(new RelativeSizeSpan(1.0f),0,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


//        string.setSpan(drawer,2,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        string.setSpan(new ClipTxtSpan(50),0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        string.setSpan(new BackgroundColorSpan(Color.RED),0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        string.setSpan(new LineColorSpan(Color.RED,false),0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        string.setSpan(generalSpan(),0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


//        Drawable drawable = getResources().getDrawable(R.drawable.jjj);
//        drawable.setColorFilter(new PorterDuffColorFilter(Color.argb(100,255,0,0), PorterDuff.Mode.SRC_IN));
//        string.setSpan(new LineImgForegroundDrawer(drawable, 1f, Gravity.CENTER),0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        string.setSpan(new LineImgForegroundDrawer(BitmapFactory.decodeResource(getResources(),R.drawable.line), 0.3f, Gravity.CENTER),0,string.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        string.setSpan(new LineImgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.line), Gravity.BOTTOM,1f,true),3,5,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        }
        tv.setText(string);
//        tv.post(new Runnable() {
//            @Override
////            public void run() {
////                tv.getPaint().setShader(new LinearGradient(0,0,tv.getMeasuredWidth(),tv.getMeasuredHeight(),Color.YELLOW,Color.RED, Shader.TileMode.REPEAT));
////                tv.getPaint().setShader(new RadialGradient(tv.getWidth()/2f,tv.getHeight()/2f,(float)Math.sqrt(tv.getWidth()*tv.getWidth()+tv.getHeight()*tv.getHeight()),Color.YELLOW,Color.RED, Shader.TileMode.REPEAT));
//                tv.getPaint().setShader(new SweepGradient(tv.getWidth()/2f,tv.getHeight()/2f,Color.YELLOW,Color.RED));
//            }
//        });
    }

    private LayerSpan generalSpan(){
        final LayerSpan span = new LayerSpan(1.0f,1.0f);
        final TAnimationQueen set = new TAnimationQueen(tv);

        int duration = 800;
        final TAnimation animation = new TAnimation.Builder(tv)
                .translate(4,0,0,0)
                .itemDuration(duration)
                .alpha(0,1)
                .valueComputer(new TAnimation.SquenceComputer(300))
//                .valueComputer(TAnimation.REVERSE)
                .create();
        set.addAnimation(animation);
        final TAnimation toAni = new TAnimation.Builder(tv)
                .translate(0,-4,0,0)
                .itemDuration(duration)
                .alpha(1,0)
                .valueComputer(new TAnimation.SquenceComputer(duration))
                .create();
        set.addAnimation(toAni);
//        TRotateAnimation animation = new TRotateAnimation(-10f,10f,tv);
//        animation.setDuration(150);
//        animation.setRepeatMode(ValueAnimator.REVERSE);
//        animation.setRepeatCount(Animation.INFINITE);
        span.setCanvasTransform(set);
        tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                set.start();
            }
        },300);
//        set.setStartDelay(300);
//        set.start();
        DrawData data = new DrawData();
        data.layers = new ArrayList<LayerData>();
//        LayerData imaLayer = LayerDataFactory.createImgLayer(null,new String[]{"baff0000"},null, IndexParam.Rule.Normal);

//        LayerData imgLayer = LayerDataFactory.createImgLayer(new String[]{"icon-1"}, IndexParam.Rule.Normal);

//
        LayerData stokeTxtLayer = LayerDataFactory.createTxtLayerBuilder()
                .stoke(0.1f, Paint.Join.ROUND)
                .color(CU.toString(Color.WHITE))
                .shadow(0.2f,0f,0f,"#baffffff")
                .create();

        LayerData txtLayer = LayerDataFactory.createTxtLayerBuilder()
//                .linearGradient(0,0,1f,1f,Color.WHITE,Color.argb(255,76,42,22), Shader.TileMode.MIRROR)
//                        .offset(0.3f,0.3f)
//                        .rotate(50)
//                        .scale(0.3f)
                .create();

        LayerData txtLayer2 = LayerDataFactory.createTxtLayerBuilder()
//                .linearGradient(0,0,1f,1f,Color.WHITE,Color.argb(255,76,42,22), Shader.TileMode.MIRROR)
//                        .offset(0.3f,0.3f)
                .color("#baffffff")
//                        .rotate(50)
//                        .scale(0.3f)
                .create();

//        data.layers.add(imgLayer);
        data.layers.add(stokeTxtLayer);
        data.layers.add(txtLayer);
//        data.layers.add(txtLayer2);


        if(data.layers!=null&&data.layers.size()>0){
            ILayer layer = null;
            for (LayerData layerData:data.layers) {
                if(layerData.type == LayerData.TYPE_IMG){
                    layer = new ImgLayer(new ImgLayer.DrawableLoader() {
                        @Override
                        public Drawable getDrawable(int index) {
                            return getResources().getDrawable(R.drawable.f);
                        }
                    });
//                    layer = new ImgLayer(new ImgLayer.DrawableLoader() {
//                        @Override
//                        public Drawable getDrawable(int index) {
//                            return new ColorDrawable(Color.RED);
//                        }
//                    });
                }else{
                    layer = new TxtLayer();
//                    float[] positions = new float[]{0.2f,0.1f,0.2f,0.2f,0.4f};
//                    float[] offsets = new float[]{0,0.03f,0,0.03f,0};
//                    layer.setDrawDispatcher(new OffsetDrawer(positions,offsets));
                }
                if(layerData.paintParam!=null){
                    layer.setPaintHandler(new PaintHandler(layerData.paintParam));
//                    layer.setDrawDispatcher(new ClipDrawer(0.3f));
                }
                if(layerData.offsetX>0||layerData.offsetY>0){
                    layer.offset(layerData.offsetX,layerData.offsetY);
                }
                if(layerData.scale>0&&layerData.scale!=1){
                    layer.scale(layerData.scale);
                }
                if(layerData.degree!=0){
                    layer.rotate(layerData.degree);
                }

                span.addLayer(layer);
            }
        }
        return span;
    }


}
