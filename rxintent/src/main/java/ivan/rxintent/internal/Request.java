package ivan.rxintent.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public final class Request {
    private Activity activity;
    private Intent intent;
    private int requestCode;
    private Bundle options;

    public Request(Activity activity, Intent intent, int requestCode, Bundle options) {
        this.activity = activity;
        this.intent = intent;
        this.requestCode = requestCode;
        this.options = options;
    }

    public Activity getActivity() {
        return activity;
    }

    public Intent getIntent() {
        return intent;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public Bundle getOptions() {
        return options;
    }

}
