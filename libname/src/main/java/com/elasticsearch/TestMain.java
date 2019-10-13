package com.elasticsearch;

/**
 * Created by shidu on 17/11/28.
 * https://github.com/elastic/elasticsearch
 */

public class TestMain {
    public static void main(String[] args) {
        RandomBasedUUIDGenerator generator = new RandomBasedUUIDGenerator();
        System.out.println("RandomBasedUUIDGenerator generator = "+generator.getBase64UUID());
        TimeBasedUUIDGenerator generator1 = new TimeBasedUUIDGenerator();
        System.out.println("TimeBasedUUIDGenerator generator = "+generator1.getBase64UUID());
    }
}
