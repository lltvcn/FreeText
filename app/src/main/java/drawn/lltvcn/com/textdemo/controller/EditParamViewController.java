package drawn.lltvcn.com.textdemo.controller;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;

import com.lltvcn.freefont.core.annotation.Description;
import com.lltvcn.freefont.core.annotation.Font;
import com.lltvcn.freefont.core.annotation.Img;
import drawn.lltvcn.com.textdemo.R;
import drawn.lltvcn.com.util.FileUtil;
import drawn.lltvcn.com.util.FontUtil;

/**
 * Created by zhaolei on 2017/10/13.
 */

public class EditParamViewController {

    private View view;
    private ViewGroup parent;
    private RecyclerView recyclerView;
    private Object data;
    private Field[] fields;
    private VisiableListener visiableListener;

    public EditParamViewController init(ViewGroup viewGroup, VisiableListener listener){
        parent = viewGroup;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_edit_v2,parent,false);
        parent.addView(view);
        visiableListener = listener;
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        view.setVisibility(View.GONE);
        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(v);
            }
        });
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel(v);
            }
        });
        return this;
    }

    public interface VisiableListener{
        void onVisable(boolean visiable);
    }

    private View findViewById(int id){
        return view.findViewById(id);
    }

    public interface Call<T>{
        void onConfirm(T data);
    }

    public EditParamViewController setData(Object data){
        this.data = data;
        fields = data.getClass().getFields();
        recyclerView.getAdapter().notifyDataSetChanged();
        return this;
    }

    private Call call;
    public EditParamViewController setCallBack(Call call){
        this.call = call;
        return this;
    }


    public void confirm(View view){
        for (Field field:
             fields) {
//            try {
//                if(field.get(data)==null){
//                    Description des = field.getAnnotation(Description.class);
//                    Toast.makeText(view.getContext(),des.name()+"没填",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
        }
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(call!=null){
                    call.onConfirm(data);
                }
            }
        },400);
        dismiss();
    }

    public void cancel(View view){
        dismiss();
    }

    public void show(){
        view.setVisibility(View.VISIBLE);
        parent.setVisibility(View.VISIBLE);
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1,Animation.RELATIVE_TO_SELF,0);
        ta.setDuration(400);
        view.startAnimation(ta);
        visiableListener.onVisable(true);
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
                visiableListener.onVisable(false);
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


    private class MyAdapter extends RecyclerView.Adapter<BaseViewHolder>{
        private static final int TYPE_ARRAY = 2;
        private static final int TYPE_STRING_ARRAY = 1;
        private static final int TYPE_NORMAL = 0;
        private static final int TYPE_UNKNOW = -1;


        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType){
                case TYPE_ARRAY:
                    return new ArrayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_array,parent,false));
                case TYPE_STRING_ARRAY:
                    return new StringArrayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enum,parent,false));
                case TYPE_NORMAL:
                    return  new NormalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal,parent,false));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            holder.bindData(fields[position]);
        }

        @Override
        public int getItemCount() {
            return fields==null?0:fields.length;
        }

        @Override
        public int getItemViewType(int position) {
//            Data data = dataProvider.get(position);
            Field field = fields[position];
            return getDataType(field);
        }

        private int getDataType(Field field){
            Class type = field.getType();
            if(type.isArray()||type.isAssignableFrom(AbstractList.class)){
                return TYPE_ARRAY;
            }else{
                type = getShowType(field);
                if(type.isEnum()||type.isAssignableFrom(Img.class)||type.isAssignableFrom(Font.class)){
                    return TYPE_STRING_ARRAY;
                }else {
                    return TYPE_NORMAL;
                }
            }


        }
    }

    private Class getShowType(Field field){
        Class type = null;
        Description description = field.getAnnotation(Description.class);
        if(description!=null&&description.cls()!=Void.class){
            type = description.cls();
        }else{
            type = field.getType();
        }
        return type;
    }

    private class NormalViewHolder extends BaseViewHolder{
        private EditText et;

        public NormalViewHolder(View itemView) {
            super(itemView);
            et = (EditText) findViewById(R.id.et);
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    setString(field,s.toString());
                }
            });
        }

        @Override
        public void onBind(Field field) {
            super.onBind(field);
            Class c = getShowType(field);
            if(c.isAssignableFrom(float.class)||c.isAssignableFrom(double.class)||c.isAssignableFrom(Float.class)||c.isAssignableFrom(Double.class)){
                et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
            }else if(c.isAssignableFrom(int.class)||c.isAssignableFrom(Integer.class)||c.isAssignableFrom(Long.class)||c.isAssignableFrom(long.class)){
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            }else {
                et.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            et.setText(null);
        }
    }

    private class StringArrayViewHolder extends BaseViewHolder{

        ViewGroup viewContainer;
        private String[] strings;

        public StringArrayViewHolder(View itemView) {
            super(itemView);
            viewContainer = (ViewGroup) findViewById(R.id.ll);
        }

        @Override
        public void onBind(Field field) {
            strings = getStrings(field);
            viewContainer.removeAllViews();
            if(strings!=null){
                for (String str:strings) {
                    addView(str);
                }
            }
        }

        private void addView(String str) {
            CheckBox tv = new CheckBox(viewContainer.getContext());
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText(str);
            viewContainer.addView(tv);
            if(obj!=null&&obj.equals(getString(str))){
                tv.setChecked(true);
            }
            tv.setOnCheckedChangeListener(lis);
        }

        private CheckBox lastCK;

        private CompoundButton.OnCheckedChangeListener lis = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(lastCK!=null){
                        lastCK.setChecked(false);
                    }
                    lastCK = (CheckBox) buttonView;
                    setString(lastCK.getText().toString());
                }else {
                    lastCK = null;
                    try {
                        field.set(data,null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        private String[] getStrings(Field field){
            Class c = getShowType(field);
            if(c.isEnum()){
                Enum[] enums = (Enum[]) getShowType(field).getEnumConstants();
                String[] names = new String[enums.length];
                for (int i = 0; i < enums.length; i++) {
                    names[i] = enums[i].name();
                }
                return names;
            }else if(c.isAssignableFrom(Img.class)){
                String[] names = FileUtil.getImgNames();
                return names;
            }else if(c.isAssignableFrom(Font.class)){
                String[] names = FontUtil.getFontNames();
                return names;
            }
            return  null;
        }

        public String getString(String content){
//            if(TextUtils.isEmpty(content)){
                return content;
//            }
//            Class c = getShowType(field);
//            if(c.isAssignableFrom(Img.class)){
//                return FileUtil.getMd5ByName(content);
//            }else{
//                return content;
//            }
        }

        public void setString(String content){
            try {
                Class type = field.getType();
                if(type.isEnum()){
                    int index = 0;
                    for (int i = 0; i < strings.length; i++) {
                        if(strings[i].equals(content)){
                            index = i;
                            break;
                        }
                    }
                    field.set(data, type.getEnumConstants()[index]);
                }else if(type.isAssignableFrom(String.class)){
                    field.set(data,content);
//                    Class c = getShowType(field);
//                    if(c.isAssignableFrom(Img.class)){
//                        field.set(data,FileUtil.getMd5ByName(content));
//                    }else {
//                        field.set(data,content);
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Adapter{

    }




    private class ArrayViewHolder extends BaseViewHolder{
        private EditText et;
        private ViewGroup viewContainer;
        private ArrayList<String> strings = new ArrayList<>();

        public ArrayViewHolder(View itemView) {
            super(itemView);
            et = (EditText) findViewById(R.id.et);
            findViewById(R.id.btn_add).setOnClickListener(this);
            findViewById(R.id.btn_delete).setOnClickListener(this);
            viewContainer = (ViewGroup) findViewById(R.id.ll_container);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_add:
                    if(et.length()>0){
                        strings.add(et.getText().toString());
                        setString(strings);
                        addView(et.getText().toString());
                        recyclerView.getLayoutManager().requestLayout();
                    }
                    break;
                case R.id.btn_delete:
//                    data.data.remove(data.data.size()-1);
                    if(strings.size()>0){
                        strings.remove(strings.size()-1);
                        viewContainer.removeViewAt(viewContainer.getChildCount()-1);
                        setString(strings);
                        recyclerView.getLayoutManager().requestLayout();
                    }
                    break;
            }
        }


        @Override
        public void onBind(Field field) {
            super.onBind(field);
            strings.clear();
            viewContainer.removeAllViews();
            getString();
            for (int i = 0; i < strings.size(); i++) {
                addView(strings.get(i));
            }
            recyclerView.getLayoutManager().requestLayout();
        }

        private void addView(String content){
            TextView tv = new TextView(viewContainer.getContext());
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText(content);
            viewContainer.addView(tv);
        }

        public void getString(){
            Object o = null;
            try {
                o = field.get(data);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(o == null){
                return;
            }
            try {
                Class type = field.getType();
                if(type.isArray()){
                    Class cType = type.getComponentType();
                    if(cType.isAssignableFrom(float.class)){
                        float[] res = (float[]) field.get(data);
                        for (int i = 0; i < res.length; i++) {
                            strings.add(String.valueOf(res[i]));
                        }
                    }else if(cType.isAssignableFrom(int.class)){
                        int[] res = (int[]) field.get(data);
                        for (int i = 0; i < res.length; i++) {
                            strings.add(String.valueOf(res[i]));
                        }
                    }else if(cType.isAssignableFrom(String.class)){
                        String[] res = (String[]) field.get(data);
                        for (int i = 0; i < res.length; i++) {
                            strings.add(res[i]);
                        }
                    }
                }else if(type.isAssignableFrom(ArrayList.class)){
                    // TODO: 2017/10/17 目前还没用到arraylist
//                    Type gType = field.getGenericType();
//                    if(ParameterizedType.class.isAssignableFrom(gType.getClass())){
//                        Class realCalss = (Class) ((ParameterizedType) gType).getActualTypeArguments()[0];
//                        if(realCalss.isAssignableFrom(float.class)){
//                           ArrayList<Float> list = new ArrayList<>()
//                            for (int i = 0; i < contents.size(); i++) {
//                                res[i] = Float.valueOf(contents.get(i));
//                            }
//                            field.set(data,res);
//                        }else if(realCalss.isAssignableFrom(int.class)){
//                            int[] res = new int[contents.size()];
//                            for (int i = 0; i < contents.size(); i++) {
//                                res[i] = Integer.valueOf(contents.get(i));
//                            }
//                            field.set(data,res);
//                        }else if(realCalss.isAssignableFrom(String.class)){
//                            String[] res = new String [contents.size()];
//                            for (int i = 0; i < contents.size(); i++) {
//                                res[i] = contents.get(i);
//                            }
//                            field.set(data,res);
//                        }
//                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void setString(ArrayList<String> contents){
            try {
                Class type = getShowType(field);
                if(type.isArray()){
                    Class cType = type.getComponentType();
                    if(cType.isAssignableFrom(float.class)){
                        float[] res = new float[contents.size()];
                        for (int i = 0; i < contents.size(); i++) {
                            res[i] = Float.valueOf(contents.get(i));
                        }
                        field.set(data,res);
                    }else if(cType.isAssignableFrom(int.class)){
                        int[] res = new int[contents.size()];
                        for (int i = 0; i < contents.size(); i++) {
                            res[i] = Integer.valueOf(contents.get(i));
                        }
                        field.set(data,res);
                    }else if(cType.isAssignableFrom(String.class)){
                        String[] res = new String [contents.size()];
                        for (int i = 0; i < contents.size(); i++) {
                            res[i] = contents.get(i);
                        }
                        field.set(data,res);
                    }
                }else if(type.isAssignableFrom(ArrayList.class)){
                    // TODO: 2017/10/17 目前还没用到arraylist
//                    Type gType = field.getGenericType();
//                    if(ParameterizedType.class.isAssignableFrom(gType.getClass())){
//                        Class realCalss = (Class) ((ParameterizedType) gType).getActualTypeArguments()[0];
//                        if(realCalss.isAssignableFrom(float.class)){
//                           ArrayList<Float> list = new ArrayList<>()
//                            for (int i = 0; i < contents.size(); i++) {
//                                res[i] = Float.valueOf(contents.get(i));
//                            }
//                            field.set(data,res);
//                        }else if(realCalss.isAssignableFrom(int.class)){
//                            int[] res = new int[contents.size()];
//                            for (int i = 0; i < contents.size(); i++) {
//                                res[i] = Integer.valueOf(contents.get(i));
//                            }
//                            field.set(data,res);
//                        }else if(realCalss.isAssignableFrom(String.class)){
//                            String[] res = new String [contents.size()];
//                            for (int i = 0; i < contents.size(); i++) {
//                                res[i] = contents.get(i);
//                            }
//                            field.set(data,res);
//                        }
//                    }
                }else if(type.isAssignableFrom(Color.class)){
                    int[] res = new int[contents.size()];
                    for (int i = 0; i < contents.size(); i++) {
                        res[i] = Color.parseColor("#"+contents.get(i));
                    }
                    field.set(data,res);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    private class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv;
        protected Field field;
        protected Object obj;

        public BaseViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) findViewById(R.id.tv);
        }

        public View findViewById(int id){
            return itemView.findViewById(id);
        }

        public final void bindData(Field field){
            tv.setVisibility(View.VISIBLE);
            Description description = field.getAnnotation(Description.class);
            if(description != null){
                tv.setText(description.name());
            }else{
                tv.setText(field.getName());
            }
            this.field = field;
            try {
                obj = field.get(data);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            onBind(field);
        }

        public void onBind(Field field){

        }

        @Override
        public void onClick(View v) {

        }
    }

    private void setString(Field field,String string){
        Class type = getShowType(field);
        try {
            if(type.isAssignableFrom(float.class)||type.isAssignableFrom(Float.class)){
                if(!TextUtils.isEmpty(string))
                    field.setFloat(data,Float.parseFloat(string));
                else{
                    if(type.isAssignableFrom(Float.class)){
                        field.set(data,null);
                    }else {
                        field.setFloat(data,0);
                    }
                }

            }else if(type.isAssignableFrom(int.class)||type.isAssignableFrom(Integer.class)){
                if(!TextUtils.isEmpty(string)){
                    field.setInt(data,Integer.parseInt(string));
                }else{
                    if(type.isAssignableFrom(Integer.class)){
                        field.set(data,null);
                    }else{
                        field.setInt(data,0);
                    }
                }

            }else if(type.isAssignableFrom(String.class)){
                field.set(data,string);
            }else if(type.isAssignableFrom(Color.class)){
                if(!TextUtils.isEmpty(string))
                    field.set(data,Color.parseColor("#"+string));
                else
                    field.set(data,null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
