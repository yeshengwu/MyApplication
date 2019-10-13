package com.example;

/**
 * Created by shidu on 17/11/9.
 */

public interface ICompose {
    public void onCompose(String url);

    public interface IData {
        public void onData(String url);
    }
}
