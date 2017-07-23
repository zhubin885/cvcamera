#include <DetectionBasedTracker_jni.h>
#include <opencv2/core.hpp>
#include <opencv2/opencv.hpp>
#include <opencv2/objdetect.hpp>

#include <string>
#include <vector>

#include <android/log.h>

#define LOG_TAG "FaceDetection/DetectionBasedTracker"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

using namespace std;
using namespace cv;

inline void vector_Rect_to_Mat(vector<Rect>& v_rect, Mat& mat)
{
    mat = Mat(v_rect, true);
}

class CascadeDetector{
private:


public:
    CascadeClassifier cascade;
    void detectFace(Mat frame, std::vector<Rect>&);



};

void CascadeDetector::detectFace(Mat frame, std::vector<Rect>& faces) {
    Mat frame_gray;
    cvtColor(frame, frame, COLOR_RGB2GRAY);


    this->cascade.detectMultiScale(frame, faces, 1.1, 3, 0|CASCADE_SCALE_IMAGE, Size(30,30));


}

CascadeDetector dectetor;

JNIEXPORT void JNICALL Java_opencv3_com_cvcamera_DetectionBasedTracker_loadCascadeFile
(JNIEnv * jenv, jclass, jlong thiz, jstring jfile)
{
    LOGD("Java_opencv3_com_cvcamera_DetectionBasedTracker_loadCascadeFile");
    const char* file = jenv->GetStringUTFChars(jfile, JNI_FALSE);
    dectetor.cascade.load(file);


    LOGD("Java_opencv3_com_cvcamera_DetectionBasedTracker_loadCascadeFile END");
}


JNIEXPORT void JNICALL Java_opencv3_com_cvcamera_DetectionBasedTracker_nativeDetect
(JNIEnv * jenv, jclass, jlong thiz, jlong imageGray, jlong jfaces)
{
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeDetect");

    Mat rgbFrame = *((Mat*)imageGray);

    std::vector<Rect> faces;

    dectetor.detectFace(rgbFrame, faces);
    *((Mat*)jfaces) = Mat(faces, true);


    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeDetect END");
}

JNIEXPORT void JNICALL Java_opencv3_com_cvcamera_DetectionBasedTracker_nativeCutImageROI
        (JNIEnv * jenv, jclass, jlong thiz, jlong imgFrame, jlong jfaces, jlong jroi){

    Mat rgbFrame = *((Mat*)imgFrame);
    Mat roiImg = *((Mat*)jroi);

    std::vector<Rect> faces;
    dectetor.detectFace(rgbFrame, faces);

    for(Rect r : faces){
        rgbFrame(r).copyTo(roiImg);
    }


}
