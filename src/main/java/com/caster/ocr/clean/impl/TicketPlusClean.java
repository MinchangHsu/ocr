package com.caster.ocr.clean.impl;

import com.caster.ocr.clean.CleanServiceAbs;
import com.caster.ocr.imageFilter.IImageFilter;
import com.caster.ocr.imageFilter.imp.*;
import com.caster.ocr.policy.imp.ShapeSurroundPolicyImpl;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 中華郵政驗證碼
 * Created by Caster on 2019/2/14.
 */
@Service("ticketPlus")
public class TicketPlusClean extends CleanServiceAbs {
    @Override
    public File cleanVerifyImage(File sfile) throws Exception {

        //開發測試請使用 true
        isTest = true;

        // 過濾器引入
        Queue<IImageFilter> filterQueue = new LinkedList<>();
        filterQueue.add(new ExcludeRGBSinglePoleValue("R", ">", 220));
        filterQueue.add(new CreateImageGray());
        filterQueue.add(new FillSinglePointColor(1, 255, 4));
        filterQueue.add(new FillSinglePointColor(2, 255, 5));
        filterQueue.add(new FillSinglePointColor(3, 255, 1));
        filterQueue.add(new FillSinglePointColor(4, 255, 4));
        filterQueue.add(new FillSinglePointColor(5, 255, 5));
        filterQueue.add(new CrossJudge(0, 255));


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
        policyImpl.setIsTest(Boolean.TRUE);
    }

    @Override
    public String getValidCode() {
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
}
