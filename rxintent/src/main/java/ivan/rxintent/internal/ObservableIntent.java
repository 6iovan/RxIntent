package ivan.rxintent.internal;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.Observer;
import ivan.rxintent.RxIntentFragment;

public final class ObservableIntent<R> extends Observable<R> {

    private final Request value;

    public ObservableIntent(final Request value) {
        this.value = value;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        IntentEmitter<Request, R> e = new IntentEmitter<>(value, observer);
        e.emit();
    }

    private static final class IntentEmitter<T extends Request, R> {
        private final Observer<? super R> observer;
        private final Activity activity;
        private final Intent intent;
        private final int requestCode;
        private final Bundle options;

        private IntentEmitter(final T value, final Observer<? super R> observer) {
            this.observer = observer;
            this.activity = value.getActivity();
            this.intent = value.getIntent();
            this.requestCode = value.getRequestCode();
            this.options = value.getOptions();
        }

        private void emit() {
            RxIntentFragment fragment = generateFragment(activity);
            fragment.setObserver(observer);
            activity.startActivityFromFragment(fragment, intent, requestCode, options);
        }

        private RxIntentFragment generateFragment(Activity activity) {
            RxIntentFragment fragment = findFragment(activity);
            boolean isNewInstance = fragment == null;
            if (isNewInstance) {
                fragment = new RxIntentFragment();
                FragmentManager fragmentManager = activity.getFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .add(fragment, RxIntentFragment.TAG)
                        .commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
            return fragment;
        }

        private RxIntentFragment findFragment(Activity activity) {
            return (RxIntentFragment) activity.getFragmentManager().findFragmentByTag(RxIntentFragment.TAG);
        }
    }

}
