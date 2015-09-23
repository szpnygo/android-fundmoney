package neo.smemo.info;

import android.content.Intent;
import android.os.Bundle;

import neo.smemo.info.activity.MainActivity;
import neo.smemo.info.base.BaseActivity;
import neo.smemo.info.util.view.AnnotationView;

@AnnotationView(R.layout.activity_start)
public class StartActivity extends BaseActivity {

    /**
     * 休眠时间
     */
    private static final int TIME = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {

            @Override
            public void run() {
                int start = (int) System.currentTimeMillis();

                Intent intent = new Intent(StartActivity.this,
                        MainActivity.class);
                int end = (int) System.currentTimeMillis();
                if (end - start < TIME) {
                    try {
                        Thread.sleep(TIME - (end - start));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(intent);
                finish();
            }
        }).start();

    }

}
