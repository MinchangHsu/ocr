package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;

import java.util.Map;

/**
 * 將圖片二值化
 * 原圖上有顏色地方，全部變成黑色，白色部分不變。
 * 建構子須傳入:
 *      1.模組編號(module)
 *          1: 二值化
 *          2: 灰階化 (未完成)
 *          3:
 *
 * Created by Caster on 2019/2/14.
 */
public class CreateImageGray extends AbstractFilter {

    int module ;

    public CreateImageGray() {
        this.module = 1;
    }

    public CreateImageGray(int module) {
        this.module = module;
    }

    @Override
    public Map<String, int[]> imageFilterRule(ImagePolicy tUtil) {
        init(tUtil);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (cleanRule(x, y)) {
                    pointsRGBInfoMap.put(x + "," + y, whiteColor);
                    tUtil.printSystemLog(false);
                } else {
                    pointsRGBInfoMap.put(x + "," + y, blackColor);
                    tUtil.printSystemLog(true);
                }
            }
            if (tUtil.checkIsTest()) {
                System.out.println();
            }
        }
        return pointsRGBInfoMap;
    }

    public boolean cleanRule(int x, int y) {

        int r = originRGBInfoMap.get(x + "," + y)[0];
        int g = originRGBInfoMap.get(x + "," + y)[1];
        int b = originRGBInfoMap.get(x + "," + y)[2];

        if(module == 1){
            if(r == 255 && g == 255 && b == 255){
                return true;
            }else{
                return false;
            }
        }

        return false;
    }
}
