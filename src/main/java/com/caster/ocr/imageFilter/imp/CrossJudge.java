package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;
import com.caster.ocr.policy.shapeSurround.imp.SquareShape;

import java.util.Map;

/**
 * 排除圖上 '孤立' or '連續' or '直線' 噪點，針對這種噪點進行排除。
 * 排除圖上孤立噪點
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
 *          4: [特殊判斷]上下左右 2層 判斷    ※建構子必須傳 層數(step)
 *          5: [特殊判斷] 座標極點 判斷  左上 & 右上 & 左下 & 右下  ※建構子必須傳 層數(step)
 *
 * Created by Caster on 2019/2/13.
 */
public class CrossJudge extends AbstractFilter {


    int module ;
    int max ;
    int step ;


    public CrossJudge(int module, int max) {
        this.module = module;
        this.max = max;
        this.step = 1;
    }

    public CrossJudge(int module,int max , int step) {
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

    public boolean cleanRule(Map<String, int[]> pointsRGBMap) {
        // 上下左右
        if ((module == 1  || module == 0) &&
                pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] >= max
                && pointsRGBMap.get("D1")[0] >= max
                && pointsRGBMap.get("L1")[0] >= max
                && pointsRGBMap.get("R1")[0] >= max) {
            return true;
        }

        // 左右
        if ((module == 2  || module == 0)
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("L1")[0] >= max
                && pointsRGBMap.get("R1")[0] >= max) {
            return true;
        }

        // 上下
        if ((module == 3  || module == 0)
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] >= max
                && pointsRGBMap.get("D1")[0] >= max) {
            return true;
        }

        // [特殊判斷]上下左右 2層 判斷
        if ((module == 4 )
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] >= max
                && pointsRGBMap.get("U2")[0] >= max
                && pointsRGBMap.get("D1")[0] >= max
                && pointsRGBMap.get("D2")[0] >= max
                && pointsRGBMap.get("L1")[0] >= max
                && pointsRGBMap.get("L2")[0] >= max
                && pointsRGBMap.get("R1")[0] >= max
                && pointsRGBMap.get("R2")[0] >= max) {
            return true;
        }

        // [特殊判斷] 座標極點 判斷  左上 & 右上 & 左下 & 右下
        if ((module == 5 )
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("L" + step + "U" +step )[0] >= max
                && pointsRGBMap.get("R" + step + "U" +step)[0] >= max
                && pointsRGBMap.get("L" + step + "D" +step)[0] >= max
                && pointsRGBMap.get("R" + step + "D" +step)[0] >= max) {
            return true;
        }

        // [特殊判斷] 座標極點 判斷  直線3色
        if ((module == 6 )
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] < max
                && pointsRGBMap.get("D1")[0] < max
                && (pointsRGBMap.get("L1")[0] < max || pointsRGBMap.get("L1")[0] == max)
                && (pointsRGBMap.get("R1")[0] < max || pointsRGBMap.get("R1")[0] == max)
                && pointsRGBMap.get("U2")[0] == max
                && pointsRGBMap.get("D2")[0] == max) {
            return true;
        }

        // [特殊判斷] 座標極點 判斷  直線3色
        if ((module == 7 )
                && pointsRGBMap.get("CORE")[0] < 255
                && pointsRGBMap.get("U1")[0] < max
                && pointsRGBMap.get("D1")[0] < max
                && (pointsRGBMap.get("L1")[0] < max || pointsRGBMap.get("L1")[0] == max)
                && (pointsRGBMap.get("R1")[0] < max || pointsRGBMap.get("R1")[0] == max)
                && pointsRGBMap.get("U2")[0] <= max && pointsRGBMap.get("D2")[0] <= max
                && pointsRGBMap.get("U3")[0] == max && pointsRGBMap.get("D3")[0] == max) {
            return true;
        }

        return false;
    }
}
