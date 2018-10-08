package com.yrtech.facedetect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import entity.FaceRecognitionResult;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @BindView(R.id.activity_main_detect_one_bt)
    Button activityMainDetectOneBt;
    @BindView(R.id.activity_main_detect_two_bt)
    Button activityMainDetectTwoBt;
    @BindView(R.id.activity_main_detect_three_bt)
    Button activityMainDetectThreeBt;
    @BindView(R.id.activity_main_detect_result_one_tv)
    TextView activityMainDetectResultOneTv;
    @BindView(R.id.activity_main_detect_result_two_tv)
    TextView activityMainDetectResultTwoTv;
    @BindView(R.id.activity_main_detect_result_three_tv)
    TextView activityMainDetectResultThreeTv;
    @BindView(R.id.activity_main_detect_one_iv)
    ImageView activityMainDetectOneIv;
    @BindView(R.id.activity_main_detect_two_iv)
    ImageView activityMainDetectTwoIv;
    @BindView(R.id.activity_main_detect_three_iv)
    ImageView activityMainDetectThreeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activityMainDetectOneIv.setBackgroundResource(R.drawable.wuyanzu);
        activityMainDetectTwoIv.setBackgroundResource(R.drawable.heying);
        activityMainDetectThreeIv.setBackgroundResource(R.drawable.heying2);
    }

    @OnClick({R.id.activity_main_detect_one_bt, R.id.activity_main_detect_two_bt, R.id.activity_main_detect_three_bt})
    public void onViewClicked(View view) {
        Bitmap bitmap;
        FaceRecognitionResult recognitionResult;
        String str = "";
        switch (view.getId()) {
            case R.id.activity_main_detect_one_bt:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wuyanzu);
                recognitionResult = faceRecognition(bitmap);
                str = "检测到人脸数为：" + recognitionResult.getFace_num() + ",位置为(分别是[left, top, width, height])：";
                for (Integer integer : recognitionResult.getFace_rect()) {
                    str += integer + ",";
                }
                activityMainDetectResultOneTv.setText(str);
                break;
            case R.id.activity_main_detect_two_bt:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heying);
                recognitionResult = faceRecognition(bitmap);
                str = "检测到人脸数为：" + recognitionResult.getFace_num() + ",位置为(分别是[left, top, width, height])：";
                for (Integer integer : recognitionResult.getFace_rect()) {
                    str += integer + ",";
                }
                activityMainDetectResultTwoTv.setText(str);
                break;
            case R.id.activity_main_detect_three_bt:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heying2);
                recognitionResult = faceRecognition(bitmap);
                str = "检测到人脸数为：" + recognitionResult.getFace_num() + ",位置为(分别是[left, top, width, height])：";
                for (Integer integer : recognitionResult.getFace_rect()) {
                    str += integer + ",";
                }
                activityMainDetectResultThreeTv.setText(str);
                break;
        }
    }

    /**
     * 人脸识别，调用的阿里云人脸识别接口
     *
     * @param bitmap 需要识别的照片
     */

    public FaceRecognitionResult faceRecognition(Bitmap bitmap) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        String url = "https://dtplus-cn-shanghai.data.aliyuncs.com/face/detect";
        String ak_id = "aaaaaaaaaaaaaaaaaaaaa";                           // 替换成自己的
        String ak_secret = "bbbbbbbbbbbbbbbbbbbbbb"; // 替换成自己的

        Body bd = new Body();
        bd.setType(1);
        bd.setImage_url("http://pic4.nipic.com/20090926/2121777_021618818098_2.jpg");
        bd.setContent(base64ToNoHeaderBase64(bitmapToBase64(bitmap)));
        String body = bd.ToString();
        try {
            result = FaceMatchHelper.sendPost(url, body, ak_id, ak_secret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        FaceRecognitionResult faceRecognitionResult = gson.fromJson(result, FaceRecognitionResult.class);
        return faceRecognitionResult;
    }

    /**
     * 将Bitmap转换成Base64字符串
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        //转换来的base64码需要加前缀，必须是NO_WRAP参数，表示没有空格。
        return "data:image/jpeg;base64," + Base64.encodeToString(bytes, Base64.NO_WRAP);
        //转换来的base64码不需要需要加前缀，必须是NO_WRAP参数，表示没有空格。
        //return "data:image/jpeg;base64," + Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    /**
     * 将base64的头去掉
     *
     * @param base64
     * @return
     */
    public static String base64ToNoHeaderBase64(String base64) {
        return base64.replace("data:image/jpeg;base64,", "");
    }
}
