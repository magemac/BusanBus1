package com.cos.busanbus;

public interface InitMethod {
    void init();
    void initLr();
    default void initAdapter(){};
    default void initNavigation(){};
    void initData();
}
