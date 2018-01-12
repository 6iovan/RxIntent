package ivan.rxintent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import ivan.rxintent.internal.FileHelper;
import ivan.rxintent.internal.ObservableIntent;
import ivan.rxintent.internal.Request;
import ivan.rxintent.internal.Result;

public final class RxIntent {
    static final int REQUEST_CODE_CAMERA_VIDEO_URI = 66;
    static final int REQUEST_CODE_CAMERA_IMAGE_URI = 67;
    static final int REQUEST_CODE_CAMERA_VIDEO_FILE = 68;
    static final int REQUEST_CODE_CAMERA_IMAGE_FILE = 69;

    volatile static AtomicInteger REQUEST_CODE = new AtomicInteger(70);
    volatile static File FILE;
    volatile static Uri URI;

    public static Builder with(Activity activity) {
        return new Builder(activity);
    }

    public static Observable<Result> startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        return new ObservableIntent<>(new Request(activity, intent, REQUEST_CODE.incrementAndGet(), null));
    }

    public static Observable<Result> startActivity(Activity activity, Intent intent) {
        return new ObservableIntent<>(new Request(activity, intent, REQUEST_CODE.incrementAndGet(), null));
    }

    public static Observable<Result> startActivity(
            Activity activity, Intent intent, int requestCode) {
        return new ObservableIntent<>(new Request(activity, intent, requestCode, null));
    }

    public static Observable<Result> startActivity(
            Activity activity, Intent intent, int requestCode, @Nullable Bundle options) {
        return new ObservableIntent<>(new Request(activity, intent, requestCode, options));
    }

    private static <R> Observable<R> startActivityR(
            Activity activity, Intent intent, int requestCode, @Nullable Bundle options) {
        return new ObservableIntent<>(new Request(activity, intent, requestCode, options));
    }


    public static final class Builder {
        private final Activity activity;
        private Intent intent;
        private int requestCode;
        private Bundle options;

        private Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder intent(Intent intent) {
            this.intent = intent;
            return this;
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder options(@Nullable Bundle options) {
            this.options = options;
            return this;
        }

        public Observable<Result> startActivity() {
            ObjectHelper.requireNonNull(activity, "activity is null");
            ObjectHelper.requireNonNull(intent, "intent is null");
            return RxIntent.startActivity(activity, intent, REQUEST_CODE.incrementAndGet(), options);
        }

        public Video video() {
            return new Video(activity);
        }

        public Image image() {
            return new Image(activity);
        }
    }


    private static abstract class Camera {
        final Activity activity;
        final String filePath;

        private Camera(Activity activity) {
            this.activity = activity;
            filePath = activity.getExternalCacheDir().getAbsolutePath() + "/DCIM";
            FileHelper.mkdirs(filePath);
        }

        abstract Intent getIntent();

        public abstract Observable<Uri> uri();

        public abstract Observable<File> file();

        <T> Observable<T> startCamera(int requestCode) {
            Intent intent = getIntent();
            ObjectHelper.requireNonNull(activity, "activity is null");
            ObjectHelper.requireNonNull(intent, "intent is null");
            return RxIntent.startActivityR(activity, intent, requestCode, null);
        }
    }


    public static final class Video extends Camera {
        private int durationLimit = 60;
        private float videoQuality = 1.0f; //0.1~1.0

        private Video(Activity activity) {
            super(activity);
        }

        public Video setDurationLimit(int durationLimit) {
            this.durationLimit = durationLimit;
            return this;
        }

        public Video setVideoQuality(float videoQuality) {
            this.videoQuality = videoQuality;
            return this;
        }

        @Override
        Intent getIntent() {
            FILE = new File(filePath, "VID_" + FileHelper.generateFileName() + ".mp4");
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationLimit);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, videoQuality);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                URI = FileProvider.getUriForFile(activity, "ivan.rxintent.provider", FILE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                URI = Uri.fromFile(FILE);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, URI);
            return intent;
        }

        @Override
        public Observable<Uri> uri() {
            return startCamera(RxIntent.REQUEST_CODE_CAMERA_VIDEO_URI);
        }

        @Override
        public Observable<File> file() {
            return startCamera(RxIntent.REQUEST_CODE_CAMERA_VIDEO_FILE);
        }
    }


    public static final class Image extends Camera {
        private Image(Activity activity) {
            super(activity);
        }

        @Override
        Intent getIntent() {
            FILE = new File(filePath, "IMG_" + FileHelper.generateFileName() + ".jpg");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                URI = FileProvider.getUriForFile(activity, "ivan.rxintent.provider", FILE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                URI = Uri.fromFile(FILE);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, URI);
            return intent;
        }

        @Override
        public Observable<Uri> uri() {
            return startCamera(RxIntent.REQUEST_CODE_CAMERA_IMAGE_URI);
        }

        @Override
        public Observable<File> file() {
            return startCamera(RxIntent.REQUEST_CODE_CAMERA_IMAGE_FILE);
        }
    }

}
