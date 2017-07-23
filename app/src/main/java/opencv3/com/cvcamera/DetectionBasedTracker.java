package opencv3.com.cvcamera;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;

public class DetectionBasedTracker
{

    private long mNativeObj = 0;

    public DetectionBasedTracker(String cascadeName) {
        loadCascadeFile(mNativeObj, cascadeName);
    }

    public void detectMultiFace(Mat mFrame, MatOfRect faces){

        nativeDetect(mNativeObj, mFrame.getNativeObjAddr(), faces.getNativeObjAddr());
    }

    public void cutImageROI(Mat mFrame, MatOfRect faces, Mat roi){
        nativeCutImageROI(mNativeObj, mFrame.getNativeObjAddr(), faces.getNativeObjAddr(), roi.getNativeObjAddr());
    }

    private static native void loadCascadeFile(long thiz, String file);
    private static native void nativeDetect(long thiz, long inputImage, long faces);
    private static native void nativeCutImageROI(long thiz, long inputImage, long faces, long roiMat);
}
