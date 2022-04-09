package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;
import com.caster.ocr.policy.shapeSurround.imp.SquareShape;

import java.util.Map;

/**
 * 將圖片中的文字，進行填補誤判的噪點。
 * 建構子必須傳入:
 *      1.模組編號(module)
 *      2.判斷數值(max)
 *      3.層數(step)
 *
 * 使用模組:
 *          0: 全部進行判斷
 *          1: 上下左右
 *          2: 左右
 *          3: 上下
 *
 *
 * 使用時機: 建議式醬圖片二值化 or 灰階後使用。
 *
 * Created by Caster on 2019/2/14.
 */
public class CramImageRGB extends AbstractFilter {

    int module ;
    int max ;
    int step ;


    public CramImageRGB(int module, int max) {
        this.module = module;
        this.max = max;
        this.step = 1;
    }

    public CramImageRGB(int module,int max , int step) {
        this.module = module;
        this.max = max;
        this.step = step;
    }

    @Override
    public Map<String, int[]> imageFilterRule(ImagePolicy tUtil) {
        init(tUtil);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Map<String, int[]> pointsRGB = tUtil.translateRGBToMap(new SquareShape(pointsRGBInfoMap, originRGBInfoMap, x, y, step));
                if (cleanRule(pointsRGB)) {
                    pointsRGBInfoMap.put(x + "," + y, blackColor);
                    tUtil.printSystemLog(true);
                } else {
                    pointsRGBInfoMap.put(x + "," + y, originRGBInfoMap.get(x + "," + y));
                    tUtil.printSystemLog(true, originRGBInfoMap.get(x + "," + y)[0]);
                }
            }
            if (tUtil.checkIsTest()) {
                System.out.println();
            }
        }
        return pointsRGBInfoMap;
    }

    public boolean cleanRule(Map<String, int[]> pointsRGBMap) {
        // 上下左右
        if ((module == 1  || module == 0) &&
                pointsRGBMap.get("CORE")[0] == 255
                && pointsRGBMap.get("U1")[0] <= max
                && pointsRGBMap.get("D1")[0] <= max
                && pointsRGBMap.get("L1")[0] <= max
                && pointsRGBMap.get("R1")[0] <= max) {
            return true;
        }

        // 左右
        if ((module == 2  || module == 0)
                && pointsRGBMap.get("CORE")[0] == 255
                && pointsRGBMap.get("L1")[0] <= max
                && pointsRGBMap.get("R1")[0] <= max) {
            return true;
        }

        // 上下
        if ((module == 3  || module == 0)
                && pointsRGBMap.get("CORE")[0] == 255
                && pointsRGBMap.get("U1")[0] <= max
                && pointsRGBMap.get("D1")[0] <= max) {
            return true;
        }

        return false;

    }
}
