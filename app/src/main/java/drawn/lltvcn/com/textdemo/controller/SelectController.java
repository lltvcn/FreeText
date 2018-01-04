package drawn.lltvcn.com.textdemo.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;

import java.util.ArrayList;

import drawn.lltvcn.com.textdemo.R;

/**
 * Created by zhaolei on 2017/10/17.
 */

public class SelectController {

    private ViewGroup parent;
    private View view;
    private RecyclerView rv;
    public String[] data;
    private MyAdapter adapter = new MyAdapter();

    private EditParamViewController.VisiableListener listener;

    public void init(ViewGroup parent, EditParamViewController.VisiableListener listener){
        this.parent = parent;
        this.listener = listener;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_edit_v2,parent,false);
        parent.addView(view);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
//        rv.setLayoutManager(new GridLayoutManager(parent.getContext(),3, LinearLayoutManager.HORIZONTAL,false));
        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> result = new ArrayList<String>();
                for (int i = 0; i < rv.getChildCount(); i++) {
                    CheckBox ck = (CheckBox) rv.getChildAt(i).findViewById(R.id.ck);
                    if(ck.isChecked()){
                        result.add(data[i]);
                    }
                }
                resultLis.onConfrim(result);
                dismiss();
            }
        });
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private View findViewById(int id){
        return view.findViewById(id);
    }

    private class MyAdapter extends RecyclerView.Adapter<Holder>{

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ck,parent,false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            String content = data[position];
            holder.ck.setText(content);
        }

        @Override
        public int getItemCount() {
            return data==null?0:data.length;
        }
    }

    private CheckBox lastCK;
    private boolean isMulti;

    private class Holder extends RecyclerView.ViewHolder{
        private CheckBox ck;

        public Holder(View itemView) {
            super(itemView);
            ck = (CheckBox) itemView.findViewById(R.id.ck);
            ck.setChecked(false);
            ck.setClickable(false);
            ck.setFocusable(false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> str = new ArrayList<String>();
                    str.add(data[rv.getChildAdapterPosition(v)]);
                    resultLis.onConfrim(str);
                    dismiss();
                }
            });
//            ck.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(!isMulti){
//                        if(ck.isChecked()){
//                            if(lastCK!=null&&lastCK!=ck){
//                                lastCK.setChecked(false);
//                            }
//                            lastCK = ck;
//                        }
//                    }
//
//                }
//            });
        }
    }

    public interface ResultListener{
        void onConfrim(ArrayList<String> result);
    }

    private ResultListener resultLis;
    public void show(String[] data,boolean isMulti,ResultListener listener){
        this.resultLis = listener;
        this.data = data;
        this.isMulti = isMulti;
        adapter.notifyDataSetChanged();
        show();
    }

    public void show(){
        view.setVisibility(View.VISIBLE);
        parent.setVisibility(View.VISIBLE);
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1,Animation.RELATIVE_TO_SELF,0);
        ta.setDuration(400);
        view.startAnimation(ta);
        listener.onVisable(true);
    }

    public void dismiss(){
        lastCK = null;
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,1);
        ta.setDuration(400);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                listener.onVisable(false);
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


}
