package ivan.sample.rxintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import ivan.rxintent.RxIntent;

public class ExampleActivity extends AppCompatActivity {

    private TextView tv;
    private VideoView vv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        tv = findViewById(R.id.tv);
        vv = findViewById(R.id.vv);
        iv = findViewById(R.id.iv);
    }

    public void example1(View view) {
        RxIntent.startActivity(this, ResultActivity.class)
                .subscribe(result -> {
                    Intent data = result.getData();
                    String edt = data.getStringExtra("edt");
                    tv.setText(edt);
                });
    }

    public void example2(View view) {
        RxIntent.with(this)
                .intent(new Intent(this, ResultActivity.class))
                .startActivity()
                .subscribe(result -> {
                    Intent data = result.getData();
                    String edt = data.getStringExtra("edt");
                    tv.setText(edt);
                });
    }

    public void example3(View view) {
        RxIntent.with(this)
                .video()
                .setDurationLimit(10)
                .setVideoQuality(1)
                .file()
                .subscribe(file -> {
                    vv.setVideoPath(file.getPath());
                    vv.start();
                });
    }

    public void example4(View view) {
        RxIntent.with(this)
                .image()
                .uri()
                .subscribe(uri -> iv.setImageURI(uri));
    }

}
