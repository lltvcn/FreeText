package com.lltvcn.freefont.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lltvcn.freefont.core.layer.LayerSpan;
import com.lltvcn.freefont.core.layer.SingleWarpSpan;
import com.lltvcn.freefont.core.linedrawer.BackgroundDrawer;
import com.lltvcn.freefont.core.linedrawer.ForegroundDrawer;
import com.lltvcn.freefont.core.linedrawer.LineDrawer;


/**
 * Created by zhaolei on 2017/9/18.
 */

public class ShadeTextView extends TextView{
    private SpannableStringBuilder sb;

    public ShadeTextView(Context context) {
        this(context, null);
    }


    public ShadeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




    @Override
    public void setText(CharSequence text, BufferType type) {
        if(text instanceof SpannableStringBuilder){
//            sb = (SpannableStringBuilder) text;
//            for (int i = 0; i < sb.length(); i++) {
//                sb.setSpan(new RelativeSizeSpan(1.0f),i,i+1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
            sb = (SpannableStringBuilder) text;
            LayerSpan[] spans = sb.getSpans(0,sb.length(),LayerSpan.class);
            if(spans!=null){
                for (int i = 0; i < sb.length(); i++) {
                    spans = sb.getSpans(i,i+1,LayerSpan.class);
                    if(spans!=null&&spans.length>0){
                        sb.setSpan(new SingleWarpSpan(spans[spans.length-1]),i,i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
            remove(sb,LayerSpan.class);
        }else{
            sb = null;
        }
        super.setText(text, type);
//        if(sb!=null){
//            remove(sb,LeadingMarginSpan.class);
//            remove(sb, LeadingMarginSpan.LeadingMarginSpan2.class);
//        }
    }

    private <T> void remove(SpannableStringBuilder sb,Class<T> c){
        T[] spans = sb.getSpans(0,sb.length(), c);
        if(spans!=null){
            for (int i = 0; i < spans.length; i++) {
                sb.removeSpan(spans[i]);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getLayout() != null && sb != null) {
            Paint.FontMetricsInt fontMetricsInt = new Paint.FontMetricsInt();
            getPaint().getFontMetricsInt(fontMetricsInt);
            drawLineRect(fontMetricsInt,canvas,getLayout(),BackgroundDrawer.class);
            super.onDraw(canvas);
            drawLineRect(fontMetricsInt,canvas,getLayout(),ForegroundDrawer.class);
        } else {
            super.onDraw(canvas);
        }
    }

    private <T extends LineDrawer> void drawLineRect(Paint.FontMetricsInt fontMetricsInt,Canvas canvas, Layout layout, Class<T> drawerClass) {
        LineDrawer[] drawers = sb.getSpans(0, sb.length(), drawerClass);
        if (drawers != null) {
            LineDrawer drawer = null;
            int lineStart,lineEnd,spanStar,spanEnd;
            float left,right;
            int lineCount = getLineCount();
            for (int line = 0; line < lineCount; line++) {
                lineStart = layout.getLineStart(line);
                lineEnd = layout.getLineEnd(line);
                for (int j = 0; j < drawers.length; j++) {
                    drawer = drawers[j];
                    spanStar = Math.max(lineStart,sb.getSpanStart(drawer));
                    spanEnd = Math.min(lineEnd,sb.getSpanEnd(drawer));
                    if(spanEnd>spanStar){
                        if(lineStart<spanStar){
                            left = layout.getLineLeft(line)+Layout.getDesiredWidth(sb,lineStart,spanStar,getPaint());
                        }else{
                            left = layout.getLineLeft(line);
                        }
                        if(lineEnd>spanEnd){
                            right = layout.getLineRight(line)-StaticLayout.getDesiredWidth(sb,spanEnd,lineEnd,getPaint());
                        }else {
                            right = layout.getLineRight(line);
                        }
                        left+=getPaddingLeft();
                        right+=getPaddingLeft();
//                        drawer.draw(canvas,getPaint(),left,layout.getLineBaseline(line)+fontMetricsInt.top+getPaddingTop(), right ,layout.getLineBaseline(line)+fontMetricsInt.bottom+getPaddingTop());

                        drawer.draw(canvas,getPaint(),left,layout.getLineTop(line)+getPaddingTop(), right ,layout.getLineBottom(line)+getPaddingTop(),layout.getLineBaseline(line));
                    }
                }
            }
        }
    }

}
