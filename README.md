# FreeText
>Android 字体 文字 特效 动画效果库

## freefont库 功能简介：
>1.该库是基于TextView中Spnned等接口扩展，实现的单个文字特效，整段文字的特效，并且拥有动画框架可实现单个文字以及段落文字的动画效果。<br><br>
>2.单个文字特效，整段特效，动画分别是三个框架，可以在框架的基础或思路上扩展各种特效。<br><br>
>3.不仅可以通过代码实现多种文字特效，并且支持把特效转化为持久化的数据，集成库的app只需要加载特效数据就可以显示特效。<br>
>4.文字特效兼容性比较好，基本不影响TextView其他功能，可以任意切换字体，字号，颜色，添加各种spnne<br>

## Demo功能简介：
>1.demo最强大的莫过于创建字体特效功能，通过创建界面，可以手动编辑出各种文字特效。（注：该功能还不够严谨，存在bug甚至崩溃，但不影响生成的特效数据正确性。）<br><br>
>2.demo集成了一部分示例文字特效，可以通过预览界面预览。<br><br>
>3.freeText库目前只通过代码实现了几种动画，可以通过预览动画节目观看效果。（注：时间问题库暂时还不支持数据驱动动画，需要的同学可以自行扩展）。<br><br>

## 以下仅仅是示例，运用动画和特效框架可以得到任何你想要的效果
  ![](https://github.com/lltvcn/FreeText/raw/master/res/res.gif)


## 集成说明
>暂不支持jcenter，直接把freefont库作为model即可。<br>
>具体使用流程：<br>
>>步骤1:由于有些api不支持硬件加速，建议xml文件中要取消硬件加速，如果想用硬件加速，可以通过修改源码解决兼容性问题<br><br>
```<com.lltvcn.freefont.core.view.STextView ```<br>
      ``` android:layerType="software"/>```<br><br>
>>步骤2:代码中只需要调用STextView的setData方法设置特效数据，通过getTAnimation方法获取TAnimation来操作动画。<br><br>
>>注:如果想用代码驱动方式写特效，请参考STextView的实现<br>
