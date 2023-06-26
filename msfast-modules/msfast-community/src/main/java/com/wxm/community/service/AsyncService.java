package com.wxm.community.service;

public interface AsyncService {

    void sendMatchMessage(Integer otherUserId, Integer selfUserId);
}
