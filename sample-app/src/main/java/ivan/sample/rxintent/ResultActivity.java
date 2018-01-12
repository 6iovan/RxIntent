package ivan.sample.rxintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class ResultActivity extends AppCompatActivity {

    private EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        edt = findViewById(R.id.edt);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("edt", edt.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

}
