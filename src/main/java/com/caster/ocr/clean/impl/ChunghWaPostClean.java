package com.caster.ocr.clean.impl;

import com.caster.ocr.imageFilter.IImageFilter;
import com.caster.ocr.imageFilter.imp.*;
import com.caster.ocr.clean.CleanServiceAbs;
import com.caster.ocr.policy.imp.ShapeSurroundPolicyImpl;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 中華郵政驗證碼
 * Created by Caster on 2019/2/14.
 */
@Service("chunghWa")
public class ChunghWaPostClean extends CleanServiceAbs {
    @Override
    public File cleanVerifyImage(File sfile) throws Exception {

        //開發測試請使用 true
        isTest = true;

        // 過濾器引入
        Queue<IImageFilter> filterQueue = new LinkedList<>();
        filterQueue.add(new IncreaseImageBright(1,200,1.1,30));  // 加亮圖片
        filterQueue.add(new CrossJudge(0,255));  // 排除圖上孤立噪點
        filterQueue.add(new CreateImageGray(1)); // 圖片 二階化

        while (!filterQueue.isEmpty()) {
            IImageFilter imageFilter = filterQueue.poll();
            imageFilter.setImageSize(h, w);
            originRGBInfoMap = imageFilter.imageFilterRule(policyImpl);
            policyImpl.resetOriginRGBMap(originRGBInfoMap);
            if (isTest)
                policyImpl.printPicture(originRGBInfoMap);
        }

        return policyImpl.printPicture(originRGBInfoMap);
    }

    @Override
    public void setPolicyImpl() {
        policyImpl = new ShapeSurroundPolicyImpl();
    }

    @Override
    public String getValidCode() {
        return "1234567890";
    }
}
