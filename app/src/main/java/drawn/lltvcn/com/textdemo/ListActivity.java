package drawn.lltvcn.com.textdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by zhaolei on 2017/10/20.
 */

public class ListActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);
    }


    public void toEdit(View view){
        startActivity(new Intent(this,EditActivity.class));
    }

    public void toReview(View view){
        startActivity(new Intent(this,ReviewActivity.class));
    }

    public void toReviewAni(View view){
        startActivity(new Intent(this,ReviewAniActivity.class));

    }

    public void toCode(View view){
        startActivity(new Intent(this,CodeActivity.class));
    }

}
