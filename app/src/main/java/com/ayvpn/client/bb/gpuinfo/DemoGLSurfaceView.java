package com.ayvpn.client.bb.gpuinfo;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by shidu on 17/9/25.
 */

public class DemoGLSurfaceView extends GLSurfaceView {

    DemoRenderer mRenderer;

    public DemoGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(1);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        mRenderer = new DemoRenderer();
        setRenderer(mRenderer);
    }
}
