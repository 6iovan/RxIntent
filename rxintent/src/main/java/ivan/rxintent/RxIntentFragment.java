package ivan.rxintent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import io.reactivex.Observer;
import ivan.rxintent.internal.Result;


public final class RxIntentFragment extends Fragment {

    public static final String TAG = "RxIntentFragment";

    private Observer observer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RxIntent.REQUEST_CODE.get()) {
            observer.onNext(new Result(requestCode, resultCode, data));
        } else if (requestCode == RxIntent.REQUEST_CODE_CAMERA_VIDEO_URI) {
            observer.onNext(RxIntent.URI);
        } else if (requestCode == RxIntent.REQUEST_CODE_CAMERA_VIDEO_FILE) {
            observer.onNext(RxIntent.FILE);
        } else if (requestCode == RxIntent.REQUEST_CODE_CAMERA_IMAGE_URI) {
            observer.onNext(RxIntent.URI);
        } else if (requestCode == RxIntent.REQUEST_CODE_CAMERA_IMAGE_FILE) {
            observer.onNext(RxIntent.FILE);
        }
        observer.onComplete();
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

}
