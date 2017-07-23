# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

#CMAKE_CURRENT_SOURCE_DIR

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11 -fexceptions -frtti")

set(CMAKE_VERBOSE_MAKEFILE on)
set(OpenCV_DIR "/Volumes/SSD120GB/OpenCV-android-sdk/sdk/")
set(CMAKE_VERBOSE_MAKEFILE on)
#set(libs "${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs")


# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.



add_library(libopencv_java3 SHARED IMPORTED )
set_target_properties(libopencv_java3 PROPERTIES
    IMPORTED_LOCATION "${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_java3.so")

add_library(libopencv_core SHARED IMPORTED )
set_target_properties(libopencv_core PROPERTIES
    IMPORTED_LOCATION "${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_core.a")

add_library(libopencv_imgproc SHARED IMPORTED )
set_target_properties(libopencv_imgproc PROPERTIES
    IMPORTED_LOCATION "${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_imgproc.a")

add_library(libopencv_objdetect SHARED IMPORTED )
set_target_properties(libopencv_objdetect PROPERTIES
    IMPORTED_LOCATION "${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_objdetect.a")


add_library( # Sets the name of the library.
             detection_based_tracker

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/DetectionBasedTracker_jni.h
             src/main/cpp/DetectionBasedTracker_jni.cpp )


include_directories(${CMAKE_CURRENT_SOURCE_DIR}/src/main/cpp/include
                    ${CMAKE_CURRENT_SOURCE_DIR}/src/main/cpp)

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( detection_based_tracker android log
                       libopencv_java3 libopencv_core libopencv_imgproc libopencv_objdetect

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )