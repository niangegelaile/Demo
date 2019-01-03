package com.ange.demo.midea;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Looper;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

public class MVideaRecorder implements SurfaceHolder.Callback {

    private final static String TAG = "MVideaRecorder";
    private SurfaceHolder mSurfaceHolder;
    private Activity activity;
    private SurfaceView mSurfaceView;
    private static MVideaRecorder mVideaRecorder;
    private Camera mCamera = null;//相机
    private Camera.Size mSize = null;//相机的尺寸
    private MediaRecorder mRecorder;//音视频录制类
    private boolean isRecording = false;//标记是否已经在录制
    private int mCameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;//默认后置摄像头
    private static final SparseIntArray orientations = new SparseIntArray();//手机旋转对应的调整角度
    static final int REQUEST_CODE_ASK_CALL_PHONE = 122;
    private LocalServerSocket lss;
    private LocalSocket sender, receiver;

    static {
        orientations.append(Surface.ROTATION_0, 90);
        orientations.append(Surface.ROTATION_90, 0);
        orientations.append(Surface.ROTATION_180, 270);
        orientations.append(Surface.ROTATION_270, 180);
    }

    public static MVideaRecorder getInstance() {
        if (mVideaRecorder == null) {
            synchronized (MVideaRecorder.class) {
                if (mVideaRecorder == null) {
                    mVideaRecorder = new MVideaRecorder();
                }
            }
        }
        return mVideaRecorder;
    }

    private void releaseLocalSocket() {
        try {
            if (sender != null) {
                sender.close();
            }
            if (receiver != null) {
                receiver.close();
            }
            if (lss != null) {
                lss.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sender = null;
        receiver = null;
        lss = null;
    }

    /**
     * 初始化localSocket
     *
     * @return
     */
    private boolean initLocalSocket() {
        boolean ret = true;
        try {
            releaseLocalSocket();

            String serverName = "armAudioServer";
            final int bufSize = 1024;

            lss = new LocalServerSocket(serverName);

            receiver = new LocalSocket();
            receiver.connect(new LocalSocketAddress(serverName));
            receiver.setReceiveBufferSize(bufSize);
            receiver.setSendBufferSize(bufSize);

            sender = lss.accept();
            sender.setReceiveBufferSize(bufSize);
            sender.setSendBufferSize(bufSize);
        } catch (IOException e) {
            ret = false;
        }
        return ret;
    }


    public boolean isRecording() {
        return isRecording;
    }

    /**
     * 初始化
     *
     * @param activity
     * @param surfaceView
     */
    public void init(Activity activity, SurfaceView surfaceView) {
        this.activity = activity;
        this.mSurfaceView = surfaceView;
        initView();
        initCamera();

    }


    /**
     * 释放所有资源
     */
    public void release() {
        releaseLocalSocket();
        releaseMediaRecorder();
        releaseCamera();
        this.mSurfaceView = null;
        this.activity = null;
    }


    private void initView() {
        SurfaceHolder holder = mSurfaceView.getHolder();// 取得holder
        holder.setFormat(PixelFormat.TRANSPARENT);
        holder.setKeepScreenOn(true);
        holder.addCallback(this); // holder加入回调接口
    }


    /**
     * 初始化相机
     */
    public void initCamera() {
        if (Camera.getNumberOfCameras() == 2) {
            mCamera = Camera.open(mCameraFacing);
        } else {
            mCamera = Camera.open();
        }

        CameraSizeComparator sizeComparator = new CameraSizeComparator();
        Camera.Parameters parameters = mCamera.getParameters();

        if (mSize == null) {
            List<Camera.Size> vSizeList = parameters.getSupportedPreviewSizes();
            Collections.sort(vSizeList, sizeComparator);

            for (int num = 0; num < vSizeList.size(); num++) {
                Camera.Size size = vSizeList.get(num);

                if (size.width >= 800 && size.height >= 480) {
                    this.mSize = size;
                    break;
                }
            }
            mSize = vSizeList.get(0);

            List<String> focusModesList = parameters.getSupportedFocusModes();

            //增加对聚焦模式的判断
            if (focusModesList.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            } else if (focusModesList.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }
            mCamera.setParameters(parameters);
        }
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int orientation = orientations.get(rotation);
        mCamera.setDisplayOrientation(orientation);
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.setPreviewCallback(null);
                mCamera.unlock();
                mCamera.release();
            }
        } catch (RuntimeException e) {
        } finally {
            mCamera = null;
        }
    }

    String path;


    private android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());

    /**
     * 开始录制
     */
    public boolean startRecord() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (initLocalSocket()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mRecorder == null) {
                                mRecorder = new MediaRecorder(); // 创建MediaRecorder
                            }
                            if (mCamera != null) {
                                mCamera.stopPreview();
                                mCamera.unlock();
                                mRecorder.setCamera(mCamera);
                            }
                            try {
                                // 设置音频采集方式
                                mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                                //设置视频的采集方式
                                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                                //设置文件的输出格式
                                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//aac_adif， aac_adts， output_format_rtp_avp， output_format_mpeg2ts ，webm
                                //设置audio的编码格式
                                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                                //设置video的编码格式
                                mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                                //设置录制的视频编码比特率
                                mRecorder.setVideoEncodingBitRate(1024 * 1024);
                                //设置录制的视频帧率,注意文档的说明:
                                mRecorder.setVideoFrameRate(30);
                                //设置要捕获的视频的宽度和高度
                                mSurfaceHolder.setFixedSize(320, 240);//最高只能设置640x480
                                mRecorder.setVideoSize(320, 240);//最高只能设置640x480
                                //设置记录会话的最大持续时间（毫秒）
                                mRecorder.setMaxDuration(60 * 1000);
                                mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
                                path = activity.getExternalCacheDir().getPath();
                                if (path != null) {
                                    File dir = new File(path + "/videos");
                                    if (!dir.exists()) {
                                        dir.mkdir();
                                    }
                                    path = dir + "/" + System.currentTimeMillis() + ".mp4";

                                    //设置输出文件的路径
                                    mRecorder.setOutputFile(sender.getFileDescriptor());
                                    //准备录制
                                    mRecorder.prepare();
                                    //开始录制
                                    mRecorder.start();
                                    isRecording = true;
                                    startAudioRecording();

                                }
                            } catch (Exception e) {
                                Log.e(TAG, "报错", e);
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }).start();


        return false;
    }

    /**
     * 停止录制
     */
    public void stopRecord() {
        try {
            releaseLocalSocket();
            //停止录制
            mRecorder.stop();
            //重置
            mRecorder.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isRecording = false;
    }

    /**
     * 释放MediaRecorder
     */
    private void releaseMediaRecorder() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = holder;
        if (mCamera == null) {
            return;
        }
        try {
            //设置显示
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
            releaseCamera();
            activity.finish();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // surfaceDestroyed的时候同时对象设置为null
        if (isRecording && mCamera != null) {
            mCamera.lock();
        }
        mSurfaceView = null;
        mSurfaceHolder = null;
        releaseMediaRecorder();
        releaseCamera();
    }

    private void startAudioRecording() {
        new Thread(new AudioCaptureAndSendThread()).start();
    }

    private class AudioCaptureAndSendThread implements Runnable {
        public void run() {
            try {
                sendAmrAudio();
            } catch (Exception e) {
                Log.e(TAG, "sendAmrAudio() 出错");
            }
        }

        private void sendAmrAudio() throws Exception {
//            DatagramSocket udpSocket = new DatagramSocket();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            DataInputStream dataInput = new DataInputStream(receiver.getInputStream());

//            skipAmrHead(dataInput);
//
            final int SEND_FRAME_COUNT_ONE_TIME = 10;// 每次发送10帧的数据，1帧大约32B
//            // AMR格式见博客：http://blog.csdn.net/dinggo/article/details/1966444
//            final int BLOCK_SIZE[] = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0 };

            byte[] sendBuffer = new byte[1024];


            while (isRecording()) {
                int byteCount = 0;
                int bytesWritten = 0;
                while (true) {
                    if ((byteCount = dataInput.read(sendBuffer)) != -1) {
                        fileOutputStream.write(sendBuffer, bytesWritten, byteCount);
                        bytesWritten += byteCount;
                    }
                }
            }
            fileOutputStream.close();
            dataInput.close();
            releaseLocalSocket();
        }

        private void skipAmrHead(DataInputStream dataInput) {
            final byte[] AMR_HEAD = new byte[]{0x23, 0x21, 0x41, 0x4D, 0x52, 0x0A};
            int result = -1;
            int state = 0;
            try {
                while (-1 != (result = dataInput.readByte())) {
                    if (AMR_HEAD[0] == result) {
                        state = (0 == state) ? 1 : 0;
                    } else if (AMR_HEAD[1] == result) {
                        state = (1 == state) ? 2 : 0;
                    } else if (AMR_HEAD[2] == result) {
                        state = (2 == state) ? 3 : 0;
                    } else if (AMR_HEAD[3] == result) {
                        state = (3 == state) ? 4 : 0;
                    } else if (AMR_HEAD[4] == result) {
                        state = (4 == state) ? 5 : 0;
                    } else if (AMR_HEAD[5] == result) {
                        state = (5 == state) ? 6 : 0;
                    }

                    if (6 == state) {
                        break;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "read mdat error...");
            }
        }

        private void readSomeData(byte[] buffer, int offset, int length, DataInputStream dataInput) {
            int numOfRead = -1;
            while (true) {
                try {
                    numOfRead = dataInput.read(buffer, offset, length);
                    if (numOfRead == -1) {
                        Log.d(TAG, "amr...no data get wait for data coming.....");
                        Thread.sleep(100);
                    } else {
                        offset += numOfRead;
                        length -= numOfRead;
                        if (length <= 0) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "amr..error readSomeData");
                    break;
                }
            }
        }

        private void udpSend(DatagramSocket udpSocket, byte[] buffer, int sendLength) {
            try {
                InetAddress ip = InetAddress.getByName(CommonConfig.SERVER_IP_ADDRESS.trim());
                int port = CommonConfig.AUDIO_SERVER_UP_PORT;

                byte[] sendBuffer = new byte[sendLength];
                System.arraycopy(buffer, 0, sendBuffer, 0, sendLength);

                DatagramPacket packet = new DatagramPacket(sendBuffer, sendLength);
                packet.setAddress(ip);
                packet.setPort(port);
                udpSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
