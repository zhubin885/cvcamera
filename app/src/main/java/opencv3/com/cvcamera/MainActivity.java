package opencv3.com.cvcamera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends Activity {


    private ImageView imgv;

    private ImageView imgv2;
    private ImageView imgv3;
    private ImageView imgv4;

    // Used to load the 'native-lib' library on application startup.
    static {
        if(!OpenCVLoader.initDebug()){
            //TODO:
        }

        System.loadLibrary("opencv_java3");
        System.loadLibrary("detection_based_tracker");
    }

    private DetectionBasedTracker detector;

    private Mat mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        imgv = (ImageView) findViewById(R.id.imageView);
        imgv2 = (ImageView) findViewById(R.id.imageView2);
        imgv3 = (ImageView) findViewById(R.id.imageView3);
        imgv4 = (ImageView) findViewById(R.id.imageView4);


        initCascadeEng();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detect();
            }
        });

    }

    public void initCascadeEng(){

        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
        File mCascadeFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/haarcascade_frontalface_alt_tree.xml");


        detector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath());


    }

    Mat matROI = null;

    public void detect(){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.test);

        mat = new Mat();
        matROI = new Mat();
        Mat mask = new Mat();

        Utils.bitmapToMat(bmp, mat);
        imgv.setImageBitmap(bmp);


        MatOfRect faces = new MatOfRect();

        detector.detectMultiFace(mat, faces); //调用人脸检测功能.

        Rect[] rects = faces.toArray();

//        Mat matROI = null; //new Mat(mat, rects[0]);

        mat.copyTo(matROI, mask);

        int i=0;
        for(Rect r: rects){
            Imgproc.rectangle(matROI, r.tl(), r.br(), new Scalar(0, 255, 0, 0), 10); //画矩形框
//            matROI = new Mat(mat, rects[0]);

            Mat dstMat = new Mat();
            matROI.copyTo(dstMat);
            dstMat = dstMat.submat(r);

            Bitmap bmp2 = Bitmap.createBitmap(dstMat.cols(), dstMat.rows(), Bitmap.Config.RGB_565);
            Utils.matToBitmap(dstMat, bmp2);

            switch(i){
                case 0:
                    imgv2.setImageBitmap(bmp2);
                    break;
                case 1:
                    imgv3.setImageBitmap(bmp2);
                    break;
                case 2:
                    imgv4.setImageBitmap(bmp2);
                    break;
            }



            i++;
        }


        Rect rec = new Rect(0, 0, 500, 50);


        mat.release();
    }
    Mat submat = null;
}
