package com.xiaoya.yidiantong.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartydroid.android.starter.kit.app.StarterActivity;
import com.smartydroid.android.starter.kit.utilities.SPUtils;
import com.xiaoya.yidiantong.App;
import com.xiaoya.yidiantong.R;
import com.xiaoya.yidiantong.model.Question;

import org.litepal.crud.DataSupport;

/**
 * User  : guanhuan
 * Date  : 2016/5/3
 */
public class ErrorQuestionActivity extends StarterActivity implements View.OnClickListener {
    private RelativeLayout layoutErrorQes;
    private TextView tvErrorQesSize;
    private CheckBox errorAnswerRightSwitch;
    private CheckBox errorDeleteSwitch;
    private ImageView btnDeleteAll;

    private int errorCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_question);
        new QueryTask().execute();
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.sub_error_qes);
    }

    @Override
    protected void setupViews() {
        assignViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        errorCount = (int) SPUtils.get(this, "error_question_count", 0);
        tvErrorQesSize.setText(String.valueOf("-"));
    }

    private void assignViews() {
        layoutErrorQes = (RelativeLayout) findViewById(R.id.layout_error_qes);
        tvErrorQesSize = (TextView) findViewById(R.id.tv_error_qes_size);
        errorAnswerRightSwitch = (CheckBox) findViewById(R.id.error_answer_right_switch);
        errorDeleteSwitch = (CheckBox) findViewById(R.id.error_delete_switch);
        btnDeleteAll = (ImageView) findViewById(R.id.btn_delete_all);

        layoutErrorQes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_error_qes:
                if (errorCount == 0) {
                    showToastShort(R.string.error_setting_null);
                } else {
                    Intent intent = new Intent(mContext, ZhentiStudyQesPagerActivity.class);
                    intent.putExtra("is_error", true);
                    intent.putExtra("is_practice", true);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    class QueryTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            if (App.getCurrentSubject() == 1) {
                return DataSupport.where("kem = ? and your_small_answer = ?", "1", "0").find(Question.class).size();
            } else {
                return DataSupport.where("kem = ? and your_small_answer = ?", "4", "0").find(Question.class).size();
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            tvErrorQesSize.setText(String.valueOf(integer));
        }
    }
}
