package drawn.lltvcn.com.util;

import android.content.Context;
import android.graphics.Typeface;

import java.io.IOException;

import drawn.lltvcn.com.textdemo.MainApplication;

/**
 * Created by zhaolei on 2018/1/4.
 */

public class FontUtil {

    private static Context context = MainApplication.getInstance();
    private static String[] assetFonts,fileFonts,result;

    static {
        fileFonts = FileUtil.getFontNames();
        try {
            assetFonts = context.getAssets().list("ttf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int length = 0;
        if(fileFonts!=null){
            length += fileFonts.length;
        }
        if(assetFonts!=null){
            length += assetFonts.length;
        }
        result = new String[length];
        int index = 0;
        if(fileFonts!=null){
            System.arraycopy(fileFonts,0,result,0,fileFonts.length);
            index += fileFonts.length;
        }
        if(assetFonts!=null){
            System.arraycopy(assetFonts,0,result,index,assetFonts.length);
        }
    }

    public static String[] getFontNames(){
        return result;
    }

    public static Typeface getTypeface(String name){
        if(contain(fileFonts,name)){
            return Typeface.createFromFile(FileUtil.getFontPath(name));
        }else if(contain(assetFonts,name)){
            return Typeface.createFromAsset(context.getAssets(),"ttf/"+name);
        }else {
            return null;
        }
    }

    private static boolean contain(String[] strs,String name){
        if(strs==null||strs.length==0)
            return false;
        for (String str :
                strs) {
            if(str.equals(name))
                return true;
        }
        return false;
    }

}
