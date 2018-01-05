package drawn.lltvcn.com.textdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lltvcn.freefont.core.animation.A;
import com.lltvcn.freefont.core.data.AniData;
import com.lltvcn.freefont.core.data.DrawData;
import com.lltvcn.freefont.core.view.STextView;

import java.util.ArrayList;

import drawn.lltvcn.com.util.FileUtil;
import drawn.lltvcn.com.util.FontUtil;
import drawn.lltvcn.com.util.SUtil;

/**
 * Created by zhaolei on 2018/1/5.
 */

public class DemoActivity extends Activity{

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        datas = new ArrayList<>();
        Data d1 = new Data();
        d1.title = "组合动画";
        d1.data = FileUtil.getDrawData("文字颜色顺序变化");
        d1.font = "15华康海报体.ttf";
        d1.data.aniType = A.BOTTOM_IN_SCALE_UP_OUT;
        datas.add(d1);

        d1 = new Data();
        d1.title = "淡入淡出动画";
        d1.data = FileUtil.getDrawData("文字颜色顺序变化");
        d1.font = "15华康海报体.ttf";
        d1.data.aniType = A.SINGLE_RIGHT_FADE_INF_LEFT_FADE_OUT;
        datas.add(d1);

        d1 = new Data();
        d1.title = "单字旋转动画";
        d1.data = FileUtil.getDrawData("文字颜色顺序变化");
        d1.data.aniType = A.SINGLE_ROTATE;
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        d1 = new Data();
        d1.title = "整体旋转动画";
        d1.data = FileUtil.getDrawData("文字颜色顺序变化");
        d1.data.aniType = A.ROTATE_REPEAT;
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        d1 = new Data();
        d1.title = "上下移动动画";
        d1.data = FileUtil.getDrawData("文字颜色顺序变化");
        d1.data.aniType = A.SINGLE_UP_DOWN;
        d1.font = "15华康海报体.ttf";
        datas.add(d1);


        d1 = new Data();
        d1.title = "下划线+发光";
        d1.data = FileUtil.getDrawData("下划线+发光");
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        d1 = new Data();
        d1.title = "中划线";
        d1.data = FileUtil.getDrawData("中划线");
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        d1 = new Data();
        d1.title = "抖音效果2";
        d1.data = FileUtil.getDrawData("抖音2");
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        d1 = new Data();
        d1.title = "缩放效果";
        d1.data = FileUtil.getDrawData("缩放效果");
        d1.font = "15华康海报体.ttf";
        datas.add(d1);


        d1 = new Data();
        d1.title = "每个文字叠加图片";
        d1.data = FileUtil.getDrawData("单个字叠加图片");
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        d1 = new Data();
        d1.title = "每个文字渐变";
        d1.data = FileUtil.getDrawData("单个文字渐变");
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        d1 = new Data();
        d1.title = "每个文字加背景图";
        d1.data = FileUtil.getDrawData("背景图");
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        d1 = new Data();
        d1.title = "切割文字";
        d1.data = FileUtil.getDrawData("切割文字");
        d1.font = "15华康海报体.ttf";
        datas.add(d1);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyAdapter());

    }




    private class Data{
        String title;
        String font;
        DrawData data;
    }

    private ArrayList<Data> datas;

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datas==null?0:datas.size();
        }

        @Override
        public Data getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo,parent,false);
                holder = new ViewHolder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvContent = (STextView) convertView.findViewById(R.id.tv_content);
                holder.tvContent.setLocalSourcePath(FileUtil.getImgDir());
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            Data data = getItem(position);
            holder.tvTitle.setText(data.title);
            holder.tvTitle.setVisibility(View.GONE);
            holder.tvContent.setText(data.title);
            holder.tvContent.setTypeface(FontUtil.getTypeface(data.font));
            if(holder.tvContent.getTAnimation()!=null){
                holder.tvContent.getTAnimation().stop();
            }
            holder.tvContent.setData(data.data);
            if(holder.tvContent.getTAnimation()!=null){
                holder.tvContent.getTAnimation().start();
            }
            return convertView;
        }
    }

    private class ViewHolder{
        TextView tvTitle;
        STextView tvContent;
    }
}
