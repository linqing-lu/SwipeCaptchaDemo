package com.example.lulinqing.captchademo.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class CaptchaActivity extends AppCompatActivity {
    SwipeCaptchaView mSwipeCaptchaView;
    SeekBar mSeekBar;
    private TextView mSeekHintTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);

        mSwipeCaptchaView = (SwipeCaptchaView) findViewById(R.id.swipeCaptchaView);
        mSeekBar = (SeekBar) findViewById(R.id.dragBar);
        mSeekHintTextView = (TextView) findViewById(R.id.seek_bar_hint);

        mSwipeCaptchaView.setOnCaptchaMatchCallback(new SwipeCaptchaView.OnCaptchaMatchCallback() {
            @Override
            public void matchSuccess(SwipeCaptchaView swipeCaptchaView) {
//                    showToast("恭喜你啊 验证成功 可以搞事情了");
                mSeekBar.setVisibility(View.GONE);
                findViewById(R.id.captcha_verify_success).setVisibility(View.VISIBLE);
            }

            @Override
            public void matchFailed(SwipeCaptchaView swipeCaptchaView) {
//                    showToast("你有80%的可能是机器人，现在走还来得及");
                swipeCaptchaView.resetCaptcha();
                mSeekBar.setProgress(0);
                mSeekHintTextView.setVisibility(View.VISIBLE);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSwipeCaptchaView.setCurrentSwipeValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //随便放这里是因为控件
                mSeekBar.setMax(mSwipeCaptchaView.getMaxSwipeValue());
                mSeekHintTextView.setVisibility(View.GONE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("zxt", "onStopTrackingTouch() called with: seekBar = [" + seekBar + "]");
                mSwipeCaptchaView.matchCaptcha();
            }
        });

        findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
