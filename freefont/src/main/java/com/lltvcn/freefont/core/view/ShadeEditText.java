package com.lltvcn.freefont.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.lltvcn.freefont.core.layer.LayerSpan;
import com.lltvcn.freefont.core.layer.SingleWarpSpan;
import com.lltvcn.freefont.core.linedrawer.BackgroundDrawer;
import com.lltvcn.freefont.core.linedrawer.ForegroundDrawer;
import com.lltvcn.freefont.core.linedrawer.LineDrawer;

/**
 * Created by zhaolei on 2017/10/12.
 */

public class ShadeEditText extends EditText{
    private SpannableStringBuilder sb;


    public ShadeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Log.i("jjjjjjjj","setText"+text);
        if(text instanceof SpannableStringBuilder){
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
            drawLineRect(canvas,getLayout(),BackgroundDrawer.class);
            super.onDraw(canvas);
            drawLineRect(canvas,getLayout(),ForegroundDrawer.class);
        } else {
            super.onDraw(canvas);
        }
    }

    private <T extends LineDrawer> void drawLineRect(Canvas canvas, Layout layout, Class<T> drawerClass) {
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
                            right = layout.getLineRight(line)- StaticLayout.getDesiredWidth(sb,spanEnd,lineEnd,getPaint());
                        }else {
                            right = layout.getLineRight(line);
                        }
                        left+=getPaddingLeft();
                        right+=getPaddingLeft();

                        drawer.draw(canvas,getPaint(),left,layout.getLineTop(line)+getPaddingTop(), right ,layout.getLineBottom(line)+getPaddingTop(),layout.getLineBaseline(line));
                    }
                }
            }
        }
    }
}
