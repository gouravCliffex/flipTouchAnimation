# flipTouchAnimation
Flip from one view to next on touch:
Steps:
Create an xml file with two framelayout one for front and second for back.

<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="blog.droidsonroids.pl.blogpost.MainActivity"
    >
    
    <FrameLayout
        android:id="@+id/card_back"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:alpha="0">
        <include layout="@layout/card_back" />
    </FrameLayout>

    <FrameLayout
        android:id="@+id/card_front"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center">
        <include layout="@layout/card_front" />
    </FrameLayout>
    
</FrameLayout>

Then intilize both framelayout on your main activty.


overide onTouchEvent method in your main activity.

switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastY = event.getY();
                lastX = event.getX();
                mCurrAngle =   Math.toDegrees(Math.atan2(Math.abs(x - lastX), Math.abs(lastY-yc)));
                System.out.println("first :" + mCurrAngle);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                 moveCheck = true;
                mPrevAngle = mCurrAngle;
                mCurrAngle =   Math.abs(Math.toDegrees(Math.atan2(Math.abs(x - lastX), Math.abs(lastY-yc))));


            if (event.getX() <= lastX) 
                swipeleftAnimation();
            }

              else
            {       
                swiperightAnimation();
            }

           
            }
            case MotionEvent.ACTION_UP : {
                if (event.getX() <= lastX) {
                    swipeleftupAnimation();
                }
                else
               {
                   swiperightUpAnimation();

                }
                mPrevAngle = mCurrAngle = 0;
                break;
            }
        }
        return true;
        }
