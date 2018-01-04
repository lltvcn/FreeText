package drawn.lltvcn.com.textdemo.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import drawn.lltvcn.com.textdemo.R;

/**
 * Created by zhaolei on 2017/10/13.
 */

public class EditTxtController {

    private View view;
    private EditText et;
    private ViewGroup parent;


    public void init(ViewGroup parent){
        this.parent = parent;
        this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_edit_txt,parent,false);
        parent.addView(view);
        view.setVisibility(View.GONE);
        et = (EditText) view.findViewById(R.id.et_edit);
        view.findViewById(R.id.btn_et_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener!=null){
                    confirmListener.onConfirm(et.getText().toString());
                }
                dismiss();
            }
        });
        view.findViewById(R.id.btn_et_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnConfirmListener{
        void onConfirm(String content);
    }

    OnConfirmListener confirmListener;
    public void show(OnConfirmListener confirmListener){
        view.setVisibility(View.VISIBLE);
        parent.setVisibility(View.VISIBLE);
        et.setText(null);
        this.confirmListener = confirmListener;
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1,Animation.RELATIVE_TO_SELF,0);
        ta.setDuration(400);
        view.startAnimation(ta);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                et.requestFocus();
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et,InputMethodManager.SHOW_IMPLICIT);
            }
        },400);
    }

    public void dismiss(){
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,-1);
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
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void dismissParent(){
        for (int i = 0; i < parent.getChildCount(); i++) {
            if(parent.getChildAt(i).getVisibility() == View.VISIBLE){
                return;
            }
        }
        parent.setVisibility(View.GONE);
    }
}
