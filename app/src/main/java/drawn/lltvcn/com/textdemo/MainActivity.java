package drawn.lltvcn.com.textdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.lltvcn.freefont.core.view.ShadeTextView;
import com.lltvcn.freefont.core.layer.SourceLoader;
import com.lltvcn.freefont.core.data.DrawData;
import com.lltvcn.freefont.core.layer.ILayer;
import com.lltvcn.freefont.core.layer.ImgLayer;
import com.lltvcn.freefont.core.data.IndexParam;
import com.lltvcn.freefont.core.data.LayerData;
import com.lltvcn.freefont.core.layer.LayerDataFactory;
import com.lltvcn.freefont.core.layer.PaintHandler;
import com.lltvcn.freefont.core.layer.TxtLayer;
import com.lltvcn.freefont.core.layer.LayerSpan;
import drawn.lltvcn.com.span.ClipTxtSpan;
import drawn.lltvcn.com.span.LineColorSpan;
import drawn.lltvcn.com.span.LineImgSpan;
import drawn.lltvcn.com.span.OffsetTxtSpan;
import drawn.lltvcn.com.span.RandomBgSpan;
import drawn.lltvcn.com.span.SingleTxtBgSpan;
import drawn.lltvcn.com.span.SingleTxtRotateSpan;
import drawn.lltvcn.com.span.ShaderShadowSpan;
import drawn.lltvcn.com.span.ShakeTxtSpan;
import drawn.lltvcn.com.span.StokeTxtSpan;
import drawn.lltvcn.com.util.FileUtil;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    TextView tv;
    ImageView iv;

    private boolean[] selects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shaders = new ArrayList<>();
        Shader.TileMode tile = Shader.TileMode.MIRROR;
        Class z = tile.getClass();
        if(z.isEnum()){
            z.getEnumConstants();
        }
        Typeface typeface = Typeface.createFromAsset(getAssets(),"CIRCBN__.TTF");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            tv.getPaint().setLetterSpacing(2.0f);
//        }


        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                textView.setText("正");
                textView.getPaint().setPathEffect(new CornerPathEffect(getParamProvider().getInt(0,15)));
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                textView.setText("正");
                textView.getPaint().setPathEffect(new DiscretePathEffect(getParamProvider().getInt(0,5),getParamProvider().getInt(1,3)));

            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                textView.setText("正");
                textView.getPaint().setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(),R.drawable.timg_4), Shader.TileMode.REPEAT,Shader.TileMode.REPEAT));
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                textView.setText("执手");
                textView.setTextColor(Color.WHITE);
                textView.getPaint().setShadowLayer(getParamProvider().getInt(0,20),getParamProvider().getFloat(0,0.5f),getParamProvider().getFloat(1,0.5f), Color.argb(255,0,0,0));
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                TextView tv;
                textView.setTextColor(Color.argb(255,242,61,50));
                textView.getPaint().setMaskFilter(null);
                textView.getPaint().setMaskFilter(new BlurMaskFilter(getParamProvider().getInt(0,10), BlurMaskFilter.Blur.NORMAL));
//                textView.setText("轮廓模糊-NORMAL");
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                textView.getPaint().setMaskFilter(null);
                textView.setTextColor(Color.RED);
                textView.setText("荧光");
                textView.getPaint().setMaskFilter(new BlurMaskFilter(getParamProvider().getInt(0,10), BlurMaskFilter.Blur.SOLID));
//                textView.setText("轮廓模糊-SOLID");
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                textView.setTextColor(Color.argb(255,169,95,137));
                textView.getPaint().setMaskFilter(null);
                textView.getPaint().setMaskFilter(new BlurMaskFilter(getParamProvider().getInt(0,10), BlurMaskFilter.Blur.OUTER));
//                textView.setText("轮廓模糊-OUTER");
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                textView.setTextColor(Color.argb(255,255,221,251));
                textView.getPaint().setMaskFilter(null);
                textView.getPaint().setMaskFilter(new BlurMaskFilter(getParamProvider().getInt(0,10), BlurMaskFilter.Blur.INNER));
//                textView.setText("轮廓模糊-INNER");
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                textView.setText("浮雕");
                textView.getPaint().setMaskFilter(new EmbossMaskFilter(new float[]{getParamProvider().getFloat(0,0.5f),getParamProvider().getFloat(1,0.6f),getParamProvider().getFloat(2,0.8f)}
                        ,getParamProvider().getFloat(3,0.8f),getParamProvider().getFloat(4,0.6f),getParamProvider().getInt(0,10)));

            }
        });

        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
//                MyImgSpan span = new MyImgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f));
//                SpannableStringBuilder builder = new SpannableStringBuilder();
//                builder.append(textView.getText());
//                textView.setGravity(Gravity.CENTER);
//                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                textView.setText(builder);
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                ShaderShadowSpan span = new ShaderShadowSpan(BitmapFactory.decodeResource(getResources(),R.drawable.shader));
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("阴");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                LineImgSpan span = new LineImgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f),Gravity.TOP,0);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("来哈");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                RandomBgSpan span = new RandomBgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.paint),Gravity.CENTER,0,true);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("荧光");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });

        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                LineImgSpan span = new LineImgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f),Gravity.CENTER,0,true);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("嗨哈");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                LineColorSpan span = new LineColorSpan(Color.parseColor("#a1000000"),false);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("景色");
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                ShakeTxtSpan span = new ShakeTxtSpan();
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("抖\"");
                textView.setGravity(Gravity.CENTER|Gravity.LEFT);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                ShakeTxtSpan span = new ShakeTxtSpan();
                ClipTxtSpan span2 = new ClipTxtSpan(0.5f);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("抖分");
                textView.setGravity(Gravity.CENTER|Gravity.LEFT);
                builder.setSpan(span.addNext(span2).setSizeSpan(span2),0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                float[] positions = new float[]{0.2f,0.1f,0.2f,0.2f,0.4f};
                float[] offsets = new float[]{0,0.03f,0,0.03f,0};
                OffsetTxtSpan span = new OffsetTxtSpan(positions,offsets);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("偏移");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inMutable = true;
                Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.line_bottom,o);
                Canvas c = new Canvas(bm);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                float[] matrix = new float[]
                        {1,0,0,0,0,
                         0,1,0,0,0,
                         0,0,1,0,0,
                         0,0,0,1,0};
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                c.drawPaint(paint);
                LineImgSpan span = new LineImgSpan(bm,Gravity.CENTER);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("下划");
                textView.setGravity(Gravity.CENTER);
//                for (int i = 0; i < builder.length(); i++) {
                    builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                }
//                builder.setSpan(new RelativeSizeSpan(2.0f),1,2,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });

        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                float[] positions = new float[]{0.2f,0.1f,0.2f,0.2f,0.4f};
                float[] offsets = new float[]{0,0.03f,0,0.03f,0};
                OffsetTxtSpan span = new OffsetTxtSpan(positions,offsets);
                ShakeTxtSpan span2  = new ShakeTxtSpan();
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("偏+抖");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span.addNext(span2),0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
            }
        });


        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                StokeTxtSpan span = new StokeTxtSpan(0.1F,Color.WHITE);
//                ClipTxtSpan clipTxtSpan = new ClipTxtSpan(0.4f);
//                MaskFilterSpan filterSpan = new MaskFilterSpan(new BlurMaskFilter(2.0f, BlurMaskFilter.Blur.SOLID));
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("我2");
                textView.setGravity(Gravity.CENTER|Gravity.RIGHT);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                builder.setSpan(filterSpan,0,builder.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(builder);
                textView.setTypeface(Typeface.createFromAsset(getAssets(),"08华康娃娃体W5.TTF"));
//                textView.getPaint().setShadowLayer(2,0.5f,0.5f,Color.GREEN);
//                textView.getPaint().setMaskFilter(new BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID));
                textView.postInvalidate();
                textView.setTextColor(Color.argb(255,76,42,22));
                textView.setBackgroundColor(Color.argb(255,190,146,117));
                textView.setGravity(Gravity.CENTER);
            }
        });


        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                LayerSpan span = new LayerSpan(1.0f,1.0f);
                DrawData data = new DrawData();
                data.layers = new ArrayList<LayerData>();

                LayerData imgLayer = LayerDataFactory.createImgLayer(new String[]{"icon-1"}, IndexParam.Rule.Normal);

                LayerData stokeTxtLayer = LayerDataFactory.createTxtLayerBuilder()
                        .stoke(0.1f, Paint.Join.ROUND)
                        .color("ffffff")
                        .shadow(0.2f,0f,0f,"baffffff")
                        .create();

                LayerData txtLayer = LayerDataFactory.createTxtLayerBuilder()
//                        .linearGradient(0,0,1f,1f,Color.WHITE,Color.argb(255,76,42,22), Shader.TileMode.MIRROR)
                        .bitmapShader(FileUtil.getImagePathByName("f.png"), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
//                        .offset(0.3f,0.3f)
//                        .rotate(50)
//                        .scale(0.5f)
                        .create();

                data.layers.add(imgLayer);
                data.layers.add(stokeTxtLayer);
                data.layers.add(txtLayer);


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
                        }else{
                            layer = new TxtLayer();
                        }
                        if(layerData.paintParam!=null){
                            layer.setPaintHandler(new PaintHandler(layerData.paintParam, new SourceLoader<Bitmap>() {
                                @Override
                                public Bitmap loadByName(String name) {
                                    return BitmapFactory.decodeFile(name);
                                }

                            },null));
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
//
//                ImgLayer imgLayer = new ImgLayer(new ImgLayer.BitmapProvider() {
//                    Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.f);
//                    @Override
//                    public Bitmap getBitmap(int index) {
//                        return img;
//                    }
//                });
////                imgLayer.offset(0.5f,0.5f);
////                imgLayer.scale(0.5f);
////                imgLayer.rotate(45);
//                TxtLayer stokeLayer = new TxtLayer();
//                stokeLayer.setPaintHandler(new ILayer.PaintHandler() {
//                    @Override
//                    public void handlePaint(Paint paint, RectF rectF) {
//                        int color = Color.WHITE;
//                        float relativeWidth = 0.1f;
//                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//                        paint.setStrokeJoin(Paint.Join.ROUND);
//                        paint.setStrokeWidth(paint.getTextSize()*relativeWidth);
//                        paint.setColor(color);
////        paint.setMaskFilter(new BlurMaskFilter(p.getTextSize()*relativeWidth/2F, BlurMaskFilter.Blur.SOLID));
//                        paint.setShadowLayer(paint.getTextSize()*relativeWidth*2,0.5f,0.5f, Color.argb(100,Color.red(color),Color.green(color),Color.blue(color)));
//                    }
//                });
//
//
//                TxtLayer txtLayer = new TxtLayer();
//                txtLayer.setPaintHandler(new ILayer.PaintHandler() {
//                    LinearGradient gradient;
//                    Matrix matrix = new Matrix();
//                    @Override
//                    public void handlePaint(Paint paint, RectF rectF) {
//                        if(gradient==null){
//                            gradient = new LinearGradient(0,0,rectF.width(),rectF.width(),Color.WHITE,Color.argb(255,76,42,22), Shader.TileMode.MIRROR);
//                        }
//                        matrix.reset();
//                        matrix.setTranslate(rectF.left,rectF.top);
//                        gradient.setLocalMatrix(matrix);
//                        paint.setShader(gradient);
//                    }
//                });
//                span.addLayer(imgLayer);
//                span.addLayer(stokeLayer);
//                span.addLayer(txtLayer);
//                LineImgForegroundDrawer drawer = new LineImgForegroundDrawer(BitmapFactory.decodeResource(getResources(),R.drawable.line),0.5f, LineImgDrawer.INNER_TOP);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("无\n敌多么寂寞");
                builder.setSpan(span,0,builder.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(drawer,0,builder.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new RelativeSizeSpan(2f),0,1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(builder);
                textView.setTextColor(Color.argb(255,76,42,22));
//                textView.setTypeface(Typeface.createFromAsset(getAssets(),"08华康娃娃体W5.TTF"));
                textView.setBackgroundColor(Color.argb(255,190,146,117));
                textView.setGravity(Gravity.CENTER);
                textView.postInvalidate();
            }
        });


        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                LayerSpan span = new LayerSpan(1.0f,1.0f);
                ImgLayer imgLayer = new ImgLayer(new ImgLayer.DrawableLoader() {
                    @Override
                    public Drawable getDrawable(int index) {
                        return getResources().getDrawable(R.drawable.f);
                    }
                });
//                imgLayer.offset(0.5f,0.5f);
//                imgLayer.scale(0.5f);
//                imgLayer.rotate(45);
                TxtLayer stokeLayer = new TxtLayer();
                stokeLayer.setPaintHandler(new ILayer.IPaintHandler() {
                    @Override
                    public void handlePaint(int index,Paint paint, RectF rectF) {
                        int color = Color.WHITE;
                        float relativeWidth = 0.1f;
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint.setStrokeJoin(Paint.Join.ROUND);
                        paint.setStrokeWidth(paint.getTextSize()*relativeWidth);
                        paint.setColor(color);
//        paint.setMaskFilter(new BlurMaskFilter(p.getTextSize()*relativeWidth/2F, BlurMaskFilter.Blur.SOLID));
                        paint.setShadowLayer(paint.getTextSize()*relativeWidth*2,0.5f,0.5f, Color.argb(100,Color.red(color),Color.green(color),Color.blue(color)));
                    }
                });


                TxtLayer txtLayer = new TxtLayer();
                txtLayer.setPaintHandler(new ILayer.IPaintHandler() {
                    LinearGradient gradient;
                    Matrix matrix = new Matrix();
                    @Override
                    public void handlePaint(int index,Paint paint, RectF rectF) {
                        if(gradient==null){
                            gradient = new LinearGradient(0,0,rectF.width(),rectF.width(),Color.WHITE,Color.argb(255,76,42,22), Shader.TileMode.MIRROR);
                        }
                        matrix.reset();
                        matrix.setTranslate(rectF.left,rectF.top);
                        gradient.setLocalMatrix(matrix);
                        paint.setShader(gradient);
                    }
                });
                span.addLayer(imgLayer);
                span.addLayer(stokeLayer);
                span.addLayer(txtLayer);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("我23");
                builder.setSpan(span,0,builder.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(builder);
                textView.setTextColor(Color.argb(255,76,42,22));
                textView.setTypeface(Typeface.createFromAsset(getAssets(),"08华康娃娃体W5.TTF"));
                textView.setBackgroundColor(Color.argb(255,190,146,117));
                textView.setGravity(Gravity.CENTER);
                textView.postInvalidate();
            }
        });

        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                //渐变
                int location[] = new int[2];
                textView.getLocationInWindow(location);
                textView.setText("国\n国");
                textView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                //需要一个矩形区域，并且从canvas左上角开始计算
                LinearGradient gradient = new LinearGradient(0,textView.getMeasuredHeight(),textView.getMeasuredWidth(),0,new int[]{Color.BLACK,Color.WHITE,Color.YELLOW},new float[]{0.1f,0.8f,0.1f}, Shader.TileMode.MIRROR);
//              LinearGradient gradient = new LinearGradient(0,textView.getTextSize(),textView.getTextSize(),0,Color.BLACK,Color.YELLOW, Shader.TileMode.REPEAT);
                textView.getPaint().setShader(gradient);
            }
        });

        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                ClipTxtSpan span = new ClipTxtSpan(50);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("要\n美");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
                textView.postInvalidate();
            }
        });

        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                SingleTxtRotateSpan span = new SingleTxtRotateSpan(20);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("旋转");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
                textView.postInvalidate();
            }
        });
        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                SingleTxtBgSpan span = new SingleTxtBgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f),1);
                span.setHorSpace(20);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("单字背");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
                textView.postInvalidate();
            }
        });



        shaders.add(new TxtShader2Ext() {
            @Override
            public void onShade(TextView textView) {
                SingleTxtRotateSpan span = new SingleTxtRotateSpan(20);
//                SingleTxtBgSpan span2 = new SingleTxtBgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f),1);
                span.setHorSpace(20);
//                span2.setHorSpace(20);
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("背旋叠");
                textView.setGravity(Gravity.CENTER);
                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                textView.setText(builder);
                textView.postInvalidate();
            }
        });

        selects = new boolean[shaders.size()];

        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        gridView = (GridView) findViewById(R.id.gridview);
//        tv.setText("字体哈哈哈哈哈");
        tv.setTypeface(typeface,Typeface.ITALIC);
//        LineImgSpan span = new LineImgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f),Gravity.CENTER,0,true);
//        RelativeSizeSpan span = new RelativeSizeSpan(2.0f);
//        tv.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.e("jsisisj","jjj",new Throwable());
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                SpannableStringBuilder builder = new SpannableStringBuilder(s);
////                builder.setSpan(new StokeTxt(0.1f,Color.WHITE),0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
////                tv.setText(builder);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("ABCDEFGHIJKLMNOPQ");
        tv.setGravity(Gravity.CENTER);
        builder.setSpan(new ChangeWhatcher(),0,builder.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);


//        ImageSpan
//        SingleTxtRotateSpan span = new SingleTxtRotateSpan(20);



//        tv.postDelayed(new Runnable() {
//            @Override
//            public void run() {

//                LineImgSpan span = new LineImgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f),Gravity.CENTER,0,true);
////        RelativeSizeSpan span = new RelativeSizeSpan(2.0f);
//                SpannableStringBuilder builder = new SpannableStringBuilder();
//                builder.append(tv.getText());
//                tv.setGravity(Gravity.CENTER);
//                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                tv.setText(builder);
//                ClipTxtSpan span = new ClipTxtSpan(50);
//                SpannableStringBuilder builder = new SpannableStringBuilder();
//                builder.append(tv.getText().toString());
//                tv.setGravity(Gravity.CENTER);
//                tv.setBackgroundColor(Color.argb(100,0,0,0));
//                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                tv.setText(builder);
//                tv.invalidate();
//                LineImgSpan span = new LineImgSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f),Gravity.CENTER,0,true);
////        RelativeSizeSpan span = new RelativeSizeSpan(2.0f);
//                SpannableStringBuilder builder = new SpannableStringBuilder();
//                builder.append(tv.getText());
//                tv.setGravity(Gravity.CENTER);
//                builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                tv.setText(builder);
//            }
//        },2000);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                TxtShader2Ext shader2 = shaders.get(position);
            }
        });
        gridView.setAdapter(new MyAdapter());
        ImageSpan imageSpan = new ImageSpan(BitmapFactory.decodeResource(getResources(),R.drawable.f));

//        gridView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this,"行数："+tv.getLayout().getLineCount(),Toast.LENGTH_SHORT).show();
//            }
//        },2000);

//        MarkTxtSpan span = new MarkTxtSpan(BitmapFactory.decodeResource(getResources(),R.drawable.paint3),Gravity.CENTER,0,true);
//        SpannableStringBuilder builder = new SpannableStringBuilder();
//        builder.append("字体哈哈哈哈哈");
//        tv.setGravity(Gravity.CENTER);
//        builder.setSpan(span,0,builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        tv.setText(builder);
////                LinearGradient gradient = new LinearGradient(0,textView.getTextSize(),textView.getTextSize(),0,Color.BLACK,Color.YELLOW, Shader.TileMode.REPEAT);
//        tv.measure(View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.EXACTLY));
//        LinearGradient gradient = new LinearGradient(0,10,10,0,new int[]{Color.BLACK,Color.WHITE,Color.YELLOW},new float[]{0.1f,0.7f,1f}, Shader.TileMode.REPEAT);
//        tv.getPaint().setShader(gradient);
//        iv.setImageResource(R.color.colorAccent);


    }

    private ArrayList<TxtShader2Ext> shaders;

    private class MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return shaders==null?0:shaders.size();
        }

        @Override
        public TxtShader2 getItem(int position) {
            return shaders.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ShadeTextView textView = null;
            if(convertView==null){

                textView = new ShadeTextView(parent.getContext());
                textView.setTextSize(30);
                textView.getPaint().setFakeBoldText(true);
                textView.setGravity(Gravity.CENTER);
                textView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
                textView.setTextColor(Color.BLACK);
                textView.setText("正");
                textView.setBackgroundColor(Color.TRANSPARENT);
                convertView = textView;
//                textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (parent.getWidth()/4)));
                textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            }else{
                textView = (ShadeTextView) convertView;
            }
            TxtShader2 shader = getItem(position);
            shader.onShade(textView);
//            textView.setTxtShader(shader);
//            textView.setText(shader.getShaderName());
            return convertView;
        }
    }



    private class ChangeWhatcher implements SpanWatcher,TextWatcher{

        @Override
        public void onSpanAdded(Spannable text, Object what, int start, int end) {
            Log.i("ChangeWhatcher","onSpanAdded");
        }

        @Override
        public void onSpanRemoved(Spannable text, Object what, int start, int end) {
            Log.i("ChangeWhatcher","onSpanRemoved");
        }

        @Override
        public void onSpanChanged(Spannable text, Object what, int ostart, int oend, int nstart, int nend) {
            Log.i("ChangeWhatcher","onSpanChanged");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.i("ChangeWhatcher","beforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i("ChangeWhatcher","onTextChanged");
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.i("ChangeWhatcher","afterTextChanged");
        }
    }


}
