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
import ivan.rxintent.internal.FileUtils;
import ivan.rxintent.internal.IntentUtils;
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

    public static Observable<Result> open(Activity activity, Intent intent) {
        return new ObservableIntent<>(new Request(activity, intent, REQUEST_CODE.incrementAndGet(), null));
    }

    public static Observable<Result> open(
            Activity activity, Intent intent, int requestCode) {
        return new ObservableIntent<>(new Request(activity, intent, requestCode, null));
    }

    public static Observable<Result> open(
            Activity activity, Intent intent, int requestCode, @Nullable Bundle options) {
        return new ObservableIntent<>(new Request(activity, intent, requestCode, options));
    }

    private static <R> Observable<R> startActivity(
            Activity activity, Intent intent, int requestCode, @Nullable Bundle options) {
        return new ObservableIntent<>(new Request(activity, intent, requestCode, options));
    }

    public static Observable<Result> open(final Activity activity, final Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        return open(activity, intent);
    }

    public static Observable<Result> open(final Activity activity, final String packageName, final String className) {
        Intent intent = IntentUtils.getComponentIntent(packageName, className);
        return open(activity, intent);
    }

    public static Observable<Result> open(final Activity activity, final String packageName, final String className, final boolean isNewTask) {
        Intent intent = IntentUtils.getComponentIntent(packageName, className, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> open(final Activity activity, final String packageName, final String className, final Bundle bundle) {
        Intent intent = IntentUtils.getComponentIntent(packageName, className, bundle);
        return open(activity, intent);
    }

    public static Observable<Result> open(final Activity activity, final String packageName, final String className, final Bundle bundle, final boolean isNewTask) {
        Intent intent = IntentUtils.getComponentIntent(packageName, className, bundle, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openApp(final Activity activity, final String packageName) {
        Intent intent = IntentUtils.getLaunchAppIntent(activity, packageName);
        return open(activity, intent);
    }

    public static Observable<Result> openApp(final Activity activity, final String packageName, final boolean isNewTask) {
        Intent intent = IntentUtils.getLaunchAppIntent(activity, packageName, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openInstall(final Activity activity, final String filePath, final String authority) {
        Intent intent = IntentUtils.getInstallAppIntent(activity, filePath, authority);
        return open(activity, intent);
    }

    public static Observable<Result> openInstall(final Activity activity, final File file, final String authority) {
        Intent intent = IntentUtils.getInstallAppIntent(activity, file, authority);
        return open(activity, intent);
    }

    public static Observable<Result> openInstall(final Activity activity, final File file, final String authority, final boolean isNewTask) {
        Intent intent = IntentUtils.getInstallAppIntent(activity, file, authority, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openUninstall(final Activity activity, final String packageName) {
        Intent intent = IntentUtils.getUninstallAppIntent(packageName);
        return open(activity, intent);
    }

    public static Observable<Result> openUninstall(final Activity activity, final String packageName, final boolean isNewTask) {
        Intent intent = IntentUtils.getUninstallAppIntent(packageName, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openAppSetting(final Activity activity, final String packageName) {
        Intent intent = IntentUtils.getAppDetailsSettingsIntent(packageName);
        return open(activity, intent);
    }

    public static Observable<Result> openAppSetting(final Activity activity, final String packageName, final boolean isNewTask) {
        Intent intent = IntentUtils.getAppDetailsSettingsIntent(packageName, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openShareText(final Activity activity, final String text) {
        Intent intent = IntentUtils.getShareTextIntent(text);
        return open(activity, intent);
    }

    public static Observable<Result> openShareText(final Activity activity, final String text, final boolean isNewTask) {
        Intent intent = IntentUtils.getShareTextIntent(text, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openShareImage(final Activity activity, final String text, final String imagePath) {
        Intent intent = IntentUtils.getShareImageIntent(text, imagePath);
        return open(activity, intent);
    }

    public static Observable<Result> openShareImage(final Activity activity, final String text, final String imagePath, final boolean isNewTask) {
        Intent intent = IntentUtils.getShareImageIntent(text, imagePath, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openShareImage(final Activity activity, final String text, final File image) {
        Intent intent = IntentUtils.getShareImageIntent(text, image);
        return open(activity, intent);
    }

    public static Observable<Result> openShareImage(final Activity activity, final String text, final File image, final boolean isNewTask) {
        Intent intent = IntentUtils.getShareImageIntent(text, image, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openShareImage(final Activity activity, final String text, final Uri uri) {
        Intent intent = IntentUtils.getShareImageIntent(text, uri);
        return open(activity, intent);
    }

    public static Observable<Result> openShareImage(final Activity activity, final String text, final Uri uri, final boolean isNewTask) {
        Intent intent = IntentUtils.getShareImageIntent(text, uri, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openShutdown(final Activity activity) {
        Intent intent = IntentUtils.getShutdownIntent();
        return open(activity, intent);
    }

    public static Observable<Result> openShutdown(final Activity activity, final boolean isNewTask) {
        Intent intent = IntentUtils.getShutdownIntent(isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openDial(final Activity activity, final String phoneNumber) {
        Intent intent = IntentUtils.getDialIntent(phoneNumber);
        return open(activity, intent);
    }

    public static Observable<Result> openDial(final Activity activity, final String phoneNumber, final boolean isNewTask) {
        Intent intent = IntentUtils.getDialIntent(phoneNumber, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openCall(final Activity activity, final String phoneNumber) {
        Intent intent = IntentUtils.getCallIntent(phoneNumber);
        return open(activity, intent);
    }

    public static Observable<Result> openCall(final Activity activity, final String phoneNumber, final boolean isNewTask) {
        Intent intent = IntentUtils.getCallIntent(phoneNumber, isNewTask);
        return open(activity, intent);
    }

    public static Observable<Result> openSendSms(final Activity activity, final String phoneNumber, final String content) {
        Intent intent = IntentUtils.getSendSmsIntent(phoneNumber, content);
        return open(activity, intent);
    }

    public static Observable<Result> openSendSms(final Activity activity, final String phoneNumber, final String content, final boolean isNewTask) {
        Intent intent = IntentUtils.getSendSmsIntent(phoneNumber, content, isNewTask);
        return open(activity, intent);
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

        public Observable<Result> open() {
            ObjectHelper.requireNonNull(activity, "activity is null");
            ObjectHelper.requireNonNull(intent, "intent is null");
            return RxIntent.open(activity, intent, REQUEST_CODE.incrementAndGet(), options);
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
            FileUtils.mkdirs(filePath);
        }

        abstract Intent getIntent();

        public abstract Observable<Uri> uri();

        public abstract Observable<File> file();

        <T> Observable<T> openCamera(int requestCode) {
            Intent intent = getIntent();
            ObjectHelper.requireNonNull(activity, "activity is null");
            ObjectHelper.requireNonNull(intent, "intent is null");
            return RxIntent.startActivity(activity, intent, requestCode, null);
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
            FILE = new File(filePath, "VID_" + FileUtils.generateFileName() + ".mp4");
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
            return openCamera(RxIntent.REQUEST_CODE_CAMERA_VIDEO_URI);
        }

        @Override
        public Observable<File> file() {
            return openCamera(RxIntent.REQUEST_CODE_CAMERA_VIDEO_FILE);
        }
    }


    public static final class Image extends Camera {
        private Image(Activity activity) {
            super(activity);
        }

        @Override
        Intent getIntent() {
            FILE = new File(filePath, "IMG_" + FileUtils.generateFileName() + ".jpg");
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
            return openCamera(RxIntent.REQUEST_CODE_CAMERA_IMAGE_URI);
        }

        @Override
        public Observable<File> file() {
            return openCamera(RxIntent.REQUEST_CODE_CAMERA_IMAGE_FILE);
        }
    }

}
