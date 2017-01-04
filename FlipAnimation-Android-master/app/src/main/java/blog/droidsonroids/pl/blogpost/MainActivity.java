package blog.droidsonroids.pl.blogpost;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;

public class MainActivity extends AppCompatActivity {

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private GestureDetector mGestureDetector;
    private double mCurrAngle, mPrevAngle;
    private float delta , lastX,lastY;
    private float MOVEMENT_RATE = 1.8f, mAngle;

    private float mRotationDegrees = 0.f;
    private static final float ROTATION_RATIO = 2;
    private boolean moveCheck = false;
    private int duration = 500;
    private  int delaytimer = 170;
    private int reversetimer = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        loadAnimations();
        changeCameraDistance();

//        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
//        mGestureDetector = new GestureDetector(this, customGestureDetector);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        final float xc = mCardFrontLayout.getWidth() / 2;
        final float yc = mCardFrontLayout.getHeight() / 2;

        final float x = event.getX();
        final float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastY = event.getY();
                lastX = event.getX();
                //mCardFrontLayout.clearAnimation();
                //mCurrAngle = getAngle(event.getY(), event.getX());

                mCurrAngle =   Math.toDegrees(Math.atan2(Math.abs(x - lastX), Math.abs(lastY-yc)));
                System.out.println("first :" + mCurrAngle);

                break;
            }
            case MotionEvent.ACTION_MOVE: {

                 moveCheck = true;
                mPrevAngle = mCurrAngle;
                mCurrAngle =   Math.abs(Math.toDegrees(Math.atan2(Math.abs(x - lastX), Math.abs(lastY-yc))));


            if (event.getX() <= lastX) {

//                if (mCurrAngle >= 30)
//                {
//                    swipeleftupAnimation();
//                }
//                else
                swipeleftAnimation();


            }

                else
            {
//                if (mCurrAngle >= 30)
//                {
//                    swiperightUpAnimation();
//                }
//                else
                swiperightAnimation();
            }

                System.out.println("last :" + mCurrAngle);
                break;
            }
            case MotionEvent.ACTION_UP : {
                if (event.getX() <= lastX) {

                    swipeleftupAnimation();
                }


                else
               {

                   swiperightUpAnimation();
//
                }
                mPrevAngle = mCurrAngle = 0;
                break;
            }
        }
        return true;
    }

//    private double getAngle(double x1, double y1) {
//        double x = x1 - (mCardFrontLayout.getWidth() / 2);
//        double y = mCardFrontLayout.getHeight() - y1 - (mCardFrontLayout.getHeight() / 2);
//        switch (getQuadrant(x, y)) {
//            case 1:
//                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
//            case 2:
//                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
//            case 3:
//                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
//            case 4:
//                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
//            default:
//                return 0;
//        }
//    }
//
//    private void animate(double fromDegrees, double toDegrees, long durationMillis) {
//        final RotateAnimation rotate = new RotateAnimation((float) fromDegrees, (float) toDegrees,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        rotate.setDuration(durationMillis);
//        rotate.setFillEnabled(true);
//        rotate.setFillAfter(true);
//        mCardFrontLayout.startAnimation(rotate);
//        System.out.println(mCurrAngle);
//    }
//
//    private int getQuadrant(double x, double y) {
//        if (x >= 0)
//            return y >= 0 ? 1 : 4;
//        else
//            return y >= 0 ? 2 : 3;
//    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
    }

    public void flipCard() {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

//    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//
//            // Swipe left (next)
//            if (e1.getX() > e2.getX()) {
//
//                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout ,
//                        "rotationY", 0f, 90f);
//                imageViewObjectAnimator.setDuration(1000); // miliseconds
//                imageViewObjectAnimator.start();
//
//                ObjectAnimator imageViewObjectAnimator1 = ObjectAnimator.ofFloat(mCardBackLayout ,
//                        "rotationY", -180f, 0f);
//                imageViewObjectAnimator.setDuration(1000); // miliseconds
//                imageViewObjectAnimator.start();
//            }
//
//            // Swipe right (previous)
//            if (e1.getX() < e2.getX()) {
//
//            }
//
//            return super.onFling(e1, e2, velocityX, velocityY);
//        }
//
//
//    }

    private void swipeleftupAnimation()
    {
        if (mCurrAngle >= 30 && moveCheck) {
            moveCheck = false;


            if (!mIsBackVisible) {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "rotationY", (float) mCurrAngle, 180f);
                imageViewObjectAnimator.setDuration(duration);
                imageViewObjectAnimator.start();

                ObjectAnimator imageViewObjectAnimatorAplha = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "alpha", (float) 1.0, (float) 0.0);
                imageViewObjectAnimatorAplha.setDuration(0);
                imageViewObjectAnimatorAplha.setStartDelay(delaytimer);
                imageViewObjectAnimatorAplha.start();

                ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "rotationY", (float) ((-180) + mCurrAngle), 0);
                imageViewObjectAnimatorBack.setDuration(duration);
                imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
                imageViewObjectAnimatorBack.start();

                ObjectAnimator imageViewObjectAnimatorAplhaBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "alpha", (float) 0.0, (float) 1.0);
                imageViewObjectAnimatorAplhaBack.setDuration(0);
                imageViewObjectAnimatorAplhaBack.setStartDelay(delaytimer);
                imageViewObjectAnimatorAplhaBack.start();

                mIsBackVisible = true;
            } else {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "rotationY", (float) ((-180) + mCurrAngle), 0.0f);
                imageViewObjectAnimator.setDuration(duration);
                imageViewObjectAnimator.start();

                ObjectAnimator imageViewObjectAnimatorAplha = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "alpha", (float) 0.0, (float) 1.0);
                imageViewObjectAnimatorAplha.setDuration(0);
                imageViewObjectAnimatorAplha.setStartDelay(delaytimer);
                imageViewObjectAnimatorAplha.start();

                ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "rotationY", (float) mCurrAngle, 180f);
                imageViewObjectAnimatorBack.setDuration(duration);

                imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
                imageViewObjectAnimatorBack.start();

                ObjectAnimator imageViewObjectAnimatorAplhaBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "alpha", (float) 1.0, (float) 0.0);
                imageViewObjectAnimatorAplhaBack.setDuration(0);
                imageViewObjectAnimatorAplhaBack.setStartDelay(delaytimer);
                imageViewObjectAnimatorAplhaBack.start();

                mIsBackVisible = false;
            }

        } else {
            moveCheck = false;
            if (!mIsBackVisible) {

                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "rotationY", (float) mCurrAngle, 0f);
                imageViewObjectAnimator.setDuration(reversetimer);
                imageViewObjectAnimator.start();
            } else {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardBackLayout,
                        "rotationY", (float) mCurrAngle, 0f);
                imageViewObjectAnimator.setDuration(reversetimer);
                imageViewObjectAnimator.start();
            }
        }

    }

    private void swiperightUpAnimation()
    {
        if (mCurrAngle >= 30 && moveCheck) {
            moveCheck = false;


            if (!mIsBackVisible) {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "rotationY", (float) mCurrAngle*(-1), 180f*(-1));
                imageViewObjectAnimator.setDuration(duration);
                imageViewObjectAnimator.start();

                ObjectAnimator imageViewObjectAnimatorAplha = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "alpha", (float) 1.0, (float) 0.0);
                imageViewObjectAnimatorAplha.setDuration(0);
                imageViewObjectAnimatorAplha.setStartDelay(delaytimer);
                imageViewObjectAnimatorAplha.start();

                ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "rotationY", (float) ((-180) + mCurrAngle)*(-1), 0);
                imageViewObjectAnimatorBack.setDuration(duration);
                imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
                imageViewObjectAnimatorBack.start();

                ObjectAnimator imageViewObjectAnimatorAplhaBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "alpha", (float) 0.0, (float) 1.0);
                imageViewObjectAnimatorAplhaBack.setDuration(0);
                imageViewObjectAnimatorAplhaBack.setStartDelay(delaytimer);
                imageViewObjectAnimatorAplhaBack.start();

                mIsBackVisible = true;
            } else {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "rotationY", (float) ((-180) + mCurrAngle)*(-1), 0.0f);
                imageViewObjectAnimator.setDuration(duration);
                imageViewObjectAnimator.start();

                ObjectAnimator imageViewObjectAnimatorAplha = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "alpha", (float) 0.0, (float) 1.0);
                imageViewObjectAnimatorAplha.setDuration(0);
                imageViewObjectAnimatorAplha.setStartDelay(delaytimer);
                imageViewObjectAnimatorAplha.start();

                ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "rotationY", (float) mCurrAngle*(-1), 180f*(-1));
                imageViewObjectAnimatorBack.setDuration(duration);

                imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
                imageViewObjectAnimatorBack.start();

                ObjectAnimator imageViewObjectAnimatorAplhaBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "alpha", (float) 1.0, (float) 0.0);
                imageViewObjectAnimatorAplhaBack.setDuration(0);
                imageViewObjectAnimatorAplhaBack.setStartDelay(delaytimer);
                imageViewObjectAnimatorAplhaBack.start();

                mIsBackVisible = false;
            }

        } else {
            moveCheck = false;
            if (!mIsBackVisible) {

                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "rotationY", (float) mCurrAngle, 0f);
                imageViewObjectAnimator.setDuration(reversetimer);
                imageViewObjectAnimator.start();
            } else {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardBackLayout,
                        "rotationY", (float) mCurrAngle, 0f);
                imageViewObjectAnimator.setDuration(reversetimer);
                imageViewObjectAnimator.start();
            }
        }

    }

//    private  void swiperightUpAnimation()
//    {
//        if (mCurrAngle >= 30 && moveCheck) {
//            moveCheck = false;
//
//
//            if (!mIsBackVisible) {
//                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardBackLayout,
//                        "rotationY", (float) (180 - mCurrAngle), 0f);
//                imageViewObjectAnimator.setDuration(duration);
//                imageViewObjectAnimator.start();
//
//                ObjectAnimator imageViewObjectAnimatorAplha = ObjectAnimator.ofFloat(mCardFrontLayout,
//                        "alpha", (float) 1.0, (float) 0.0);
//                imageViewObjectAnimatorAplha.setDuration(0);
//                imageViewObjectAnimatorAplha.setStartDelay(delaytimer);
//                imageViewObjectAnimatorAplha.start();
//
//                ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardFrontLayout,
//                        "rotationY", (float) ( mCurrAngle * (-1)), -180f);
//                imageViewObjectAnimatorBack.setDuration(duration);
//                imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
//                imageViewObjectAnimatorBack.start();
//
//                ObjectAnimator imageViewObjectAnimatorAplhaBack = ObjectAnimator.ofFloat(mCardBackLayout,
//                        "alpha", (float) 0.0, (float) 1.0);
//                imageViewObjectAnimatorAplhaBack.setDuration(0);
//                imageViewObjectAnimatorAplhaBack.setStartDelay(delaytimer);
//                imageViewObjectAnimatorAplhaBack.start();
//
//                mIsBackVisible = true;
//            } else {
//                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardBackLayout,
//                        "rotationY", (float) (mCurrAngle * (-1)), -180f);
//                imageViewObjectAnimator.setDuration(duration);
//                imageViewObjectAnimator.start();
//
//                ObjectAnimator imageViewObjectAnimatorAplha = ObjectAnimator.ofFloat(mCardFrontLayout,
//                        "alpha", (float) 0.0, (float) 1.0);
//                imageViewObjectAnimatorAplha.setDuration(0);
//                imageViewObjectAnimatorAplha.setStartDelay(delaytimer);
//                imageViewObjectAnimatorAplha.start();
//
//                ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardFrontLayout,
//                        "rotationY", (float) (180- mCurrAngle), 0f);
//                imageViewObjectAnimatorBack.setDuration(duration);
//
//                imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
//                imageViewObjectAnimatorBack.start();
//
//                ObjectAnimator imageViewObjectAnimatorAplhaBack = ObjectAnimator.ofFloat(mCardBackLayout,
//                        "alpha", (float) 1.0, (float) 0.0);
//                imageViewObjectAnimatorAplhaBack.setDuration(0);
//                imageViewObjectAnimatorAplhaBack.setStartDelay(delaytimer);
//                imageViewObjectAnimatorAplhaBack.start();
//
//                mIsBackVisible = false;
//            }
//
//        } else {
//            moveCheck = false;
//            if (!mIsBackVisible) {
//
//
//
//                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
//                        "rotationY", (float) mCurrAngle*(-1), 0f);
//                imageViewObjectAnimator.setDuration(reversetimer);
//                imageViewObjectAnimator.start();
//            } else {
//                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardBackLayout,
//                        "rotationY", (float) mCurrAngle*(-1), 0f);
//                imageViewObjectAnimator.setDuration(reversetimer);
//                imageViewObjectAnimator.start();
//            }
//        }
//    }

    private void swipeleftAnimation()
    {

            if (!mIsBackVisible) {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "rotationY", (float) mPrevAngle, (float) mCurrAngle);
                imageViewObjectAnimator.setDuration(0);
                imageViewObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
                imageViewObjectAnimator.start();

                ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "rotationY", (float) ((-180) + mPrevAngle), (float) ((-180) + mCurrAngle));
                imageViewObjectAnimatorBack.setDuration(0);

                imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
                imageViewObjectAnimatorBack.start();
            } else {
                ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                        "rotationY", (float) ((-180) + mPrevAngle), (float) ((-180) + mCurrAngle));
                imageViewObjectAnimator.setDuration(0);
                imageViewObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
                imageViewObjectAnimator.start();

                ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
                        "rotationY", (float) mPrevAngle, (float) mCurrAngle);
                imageViewObjectAnimatorBack.setDuration(0);

                imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
                imageViewObjectAnimatorBack.start();

            }


        }

    private void swiperightAnimation()
    {

        if (!mIsBackVisible) {
            ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                    "rotationY", (float) mPrevAngle * (-1), (float) mCurrAngle*(-1));
            imageViewObjectAnimator.setDuration(0);
            imageViewObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
            imageViewObjectAnimator.start();

            ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
                    "rotationY", (float) ((-180) + mPrevAngle)* (-1), (float) ((-180) + mCurrAngle) * (-1));
            imageViewObjectAnimatorBack.setDuration(0);

            imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
            imageViewObjectAnimatorBack.start();
        } else {
            ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
                    "rotationY", (float) ((-180) + mPrevAngle)* (-1), (float) ((-180) + mCurrAngle)* (-1));
            imageViewObjectAnimator.setDuration(0);
            imageViewObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
            imageViewObjectAnimator.start();

            ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
                    "rotationY", (float) mPrevAngle* (-1), (float) mCurrAngle* (-1));
            imageViewObjectAnimatorBack.setDuration(0);

            imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
            imageViewObjectAnimatorBack.start();

        }


    }

//    private void swiperightAnimation()
//    {
//        if (!mIsBackVisible) {
//            ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardBackLayout,
//                    "rotationY", (float) (180 - mPrevAngle), (float) (180 - mCurrAngle));
//            imageViewObjectAnimator.setDuration(0);
//            imageViewObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
//            imageViewObjectAnimator.start();
//
//            ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardFrontLayout,
//                    "rotationY", (float) mPrevAngle*(-1), (float) mCurrAngle * (-1));
//            imageViewObjectAnimatorBack.setDuration(0);
//
//            imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
//            imageViewObjectAnimatorBack.start();
//        } else {
//            ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(mCardFrontLayout,
//                    "rotationY", (float) (180 - mPrevAngle), (float) (180 - mCurrAngle));
//            imageViewObjectAnimator.setDuration(0);
//            imageViewObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
//            imageViewObjectAnimator.start();
//
//            ObjectAnimator imageViewObjectAnimatorBack = ObjectAnimator.ofFloat(mCardBackLayout,
//                    "rotationY", (float)  mPrevAngle*(-1), (float) mCurrAngle * (-1));
//            imageViewObjectAnimatorBack.setDuration(0);
//
//            imageViewObjectAnimatorBack.setInterpolator(new AccelerateDecelerateInterpolator());// miliseconds
//            imageViewObjectAnimatorBack.start();
//
//        }
//
//
//    }
}
