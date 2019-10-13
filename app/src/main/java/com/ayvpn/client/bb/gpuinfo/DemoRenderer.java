package com.ayvpn.client.bb.gpuinfo;

import android.opengl.EGLConfig;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by shidu on 17/9/25.
 *
 * http://blog.csdn.net/typename/article/details/6735671
 */

public class DemoRenderer implements android.opengl.GLSurfaceView.Renderer {

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//渲染器
        Log.i("SystemInfo", "GL_RENDERER:::::" + gl.glGetString(GL10.GL_RENDERER));
//供应商
        Log.i("SystemInfo", "GL_VENDOR::::: " + gl.glGetString(GL10.GL_VENDOR));
//版本
        Log.i("SystemInfo", "GL_VERSION::::: " + gl.glGetString(GL10.GL_VERSION));
        Log.i("SystemInfo", "GL_EXTENSIONS::::: " + gl.glGetString(GL10.GL_EXTENSIONS));
    }

    public void onDrawFrame(GL10 arg0) {
// TODO Auto-generated method stub
    }

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {

    }

    public void onSurfaceChanged(GL10 gl, int arg1, int arg2) {
// TODO Auto-generated method stub
        //渲染器
        Log.i("SystemInfo", "GL_RENDERER:::::" + gl.glGetString(GL10.GL_RENDERER));
//供应商
        Log.i("SystemInfo", "GL_VENDOR::::: " + gl.glGetString(GL10.GL_VENDOR));
//版本
        Log.i("SystemInfo", "GL_VERSION::::: " + gl.glGetString(GL10.GL_VERSION));
        Log.i("SystemInfo", "GL_EXTENSIONS::::: " + gl.glGetString(GL10.GL_EXTENSIONS));
    }

}
