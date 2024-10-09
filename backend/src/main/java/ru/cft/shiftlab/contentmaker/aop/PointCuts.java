package ru.cft.shiftlab.contentmaker.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    @Pointcut("execution(* ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService.changeStoryByUser(..))")
    public void cut() {

    }
}
