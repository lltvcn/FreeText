package drawn.lltvcn.com.util;

import java.util.Random;

/**
 * Created by zhaolei on 2018/1/5.
 */

public class SUtil {

    private static Random random = new Random(500);
    private static String[] strs;

    static {
        strs = new String[]{
          "人生若只如初见，何事秋风悲画扇。",
                "我来不及认真地年轻，待明白过来时，只能选择认真地老去。",
                "人生本来如此：喜欢的事自然可以坚持，不喜欢的怎么也长久不了。",
                "愿时光如玉，别有温润，滚滚凡尘，浮生尽欢",
                "今天会很残酷,明天会更残酷"
        };
    }

    public static String getTxt(){
        return strs[random.nextInt(strs.length)];
    }
}
