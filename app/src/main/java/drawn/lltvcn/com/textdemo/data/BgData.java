package drawn.lltvcn.com.textdemo.data;

import com.lltvcn.freefont.core.annotation.Description;
import com.lltvcn.freefont.core.annotation.Img;

/**
 * Created by zhaolei on 2017/10/18.
 */

public class BgData {

    @Description(name = "背景图",cls = Img.class)
    public String bitmap;

    @Description(name = "背景色")
    public String bgColor;
}
