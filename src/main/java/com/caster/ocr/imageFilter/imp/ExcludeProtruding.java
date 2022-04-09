package com.caster.ocr.imageFilter.imp;

import com.caster.ocr.policy.ImagePolicy;
import com.caster.ocr.policy.shapeSurround.imp.SquareShape;

import java.util.Map;

/**
 * 排除文字上突出的噪點，針對這種噪點進行排除。
 * 建構子必須傳入:
 *      1.九宮格判斷層數(step)。
 *      2.模組編號(module)。
 *
 * 使用時機:
 * Ex.  以英文字母 'L' 做示範
 *
 *                       ＊＊＊
 *                       ＊＊＊
 *                       ＊＊＊
 * 多出來這點就是噪點 ->  ※＊＊＊
 *                       ＊＊＊
 *                       ＊＊＊
 *                       ＊＊＊
 *                       ＊＊＊＊＊＊＊＊＊＊＊＊
 *                       ＊＊＊＊＊＊＊＊＊＊＊＊
 *
 * 噪點出現分別為  文字的   上 下 左 右
 * 使用模組:
 *          0: 全部進行判斷
 *          1: 上
 *          2: 下
 *          3: 左
 *          4: 右
 *
 * Created by Caster on 2019/2/12.
 */
public class ExcludeProtruding extends AbstractFilter {
    int step ;
    int module ;

    public ExcludeProtruding(int module) {
        this.step = 1;
        this.module = module;

    }

    public ExcludeProtruding(int step ,int module) {
        this.step = step;
        this.module = module;

    }

    @Override
    public Map<String, int[]> imageFilterRule(ImagePolicy tUtil) {
        init(tUtil);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Map<String, int[]> pointsRGB = tUtil.translateRGBToMap(new SquareShape(pointsRGBInfoMap, originRGBInfoMap, x, y, step));
                if (cleanRule(pointsRGB)) {
                    pointsRGBInfoMap.put(x + "," + y, whiteColor);
                    tUtil.printSystemLog(false);
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

    public boolean cleanRule(Map<String, int[]> pointsRGBMap){
        if ((module == 1  || module == 0) &&
                pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] == 255
                && pointsRGBMap.get("D1")[0] < 255
                && pointsRGBMap.get("L1")[0] == 255
                && pointsRGBMap.get("R1")[0] == 255) {
            return true;
        }
        if ((module == 2  || module == 0)
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] < 255
                && pointsRGBMap.get("D1")[0] == 255
                && pointsRGBMap.get("L1")[0] == 255
                && pointsRGBMap.get("R1")[0] == 255) {
            return true;
        }
        if ((module == 3  || module == 0)
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] == 255
                && pointsRGBMap.get("D1")[0] == 255
                && pointsRGBMap.get("L1")[0] == 255
                && pointsRGBMap.get("R1")[0] < 255) {
            return true;
        }

        if ((module == 4  || module == 0)
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] == 255
                && pointsRGBMap.get("D1")[0] == 255
                && pointsRGBMap.get("L1")[0] < 255
                && pointsRGBMap.get("R1")[0] == 255) {
            return true;
        }

        return false;
    }
}
