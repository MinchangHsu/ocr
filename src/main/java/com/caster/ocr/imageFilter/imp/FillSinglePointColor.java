package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;
import com.caster.ocr.policy.shapeSurround.imp.SquareShape;

import java.util.Map;

/**
 * 補色圖上 '孤立' or '連續' or '直線'，針對這種形式進行補色。
 * 建構子必須傳入:
 *      1.模組編號(module)
 *      2.判斷數值(max)
 *      3.層數(step)
 * 使用模組:
 *          0: 全部進行判斷
 *          1: 上下左右
 *          2: 左右
 *          3: 上下
 *          4: [特殊判斷]上下左右 2層 判斷    ※建構子必須傳 層數(step)
 *          5: [特殊判斷] 座標極點 判斷  左上 & 右上 & 左下 & 右下  ※建構子必須傳 層數(step)
 * Created by Caster on 2019/2/13.
 */
public class FillSinglePointColor extends AbstractFilter {


    int module ;
    int max ;
    int step ;
    boolean usedOriginInfoMap = false;


    public FillSinglePointColor(int module, int max) {
        this.module = module;
        this.max = max;
        this.step = 1;
    }

    public FillSinglePointColor(int module, int max , int step) {
        this.module = module;
        this.max = max;
        this.step = step;
    }

    @Override
    public Map<String, int[]> imageFilterRule(ImagePolicy tUtil) {
        init(tUtil);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Map<String, int[]> pointsRGB = tUtil.translateRGBToMap(new SquareShape(pointsRGBInfoMap, originRGBInfoMap, x, y, step, true));

                if (cleanRule(pointsRGB)) {
                    pointsRGBInfoMap.put(x + "," + y, blackColor);
                    tUtil.printSystemLog(true, blackColor[0]);
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
        // [特殊判斷]
        if (module == 1) {
            if (pointsRGBMap.get("L1")[0] == 0
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 255
                    && pointsRGBMap.get("R2")[0] == 255
                    && pointsRGBMap.get("R3")[0] == 255
                    && pointsRGBMap.get("R4")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("L2")[0] == 0
                    && pointsRGBMap.get("L1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 255
                    && pointsRGBMap.get("R2")[0] == 255
                    && pointsRGBMap.get("R3")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("L3")[0] == 0
                    && pointsRGBMap.get("L2")[0] == 255
                    && pointsRGBMap.get("L1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 255
                    && pointsRGBMap.get("R2")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("L4")[0] == 0
                    && pointsRGBMap.get("L3")[0] == 255
                    && pointsRGBMap.get("L2")[0] == 255
                    && pointsRGBMap.get("L1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 0) {
                return true;
            }
        }

        if (module == 2) {
            if (pointsRGBMap.get("L1")[0] == 0
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 255
                    && pointsRGBMap.get("R2")[0] == 255
                    && pointsRGBMap.get("R3")[0] == 255
                    && pointsRGBMap.get("R4")[0] == 255
                    && pointsRGBMap.get("R5")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("L2")[0] == 0
                    && pointsRGBMap.get("L1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 255
                    && pointsRGBMap.get("R2")[0] == 255
                    && pointsRGBMap.get("R3")[0] == 255
                    && pointsRGBMap.get("R4")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("L3")[0] == 0
                    && pointsRGBMap.get("L2")[0] == 255
                    && pointsRGBMap.get("L1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 255
                    && pointsRGBMap.get("R2")[0] == 255
                    && pointsRGBMap.get("R3")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("L4")[0] == 0
                    && pointsRGBMap.get("L3")[0] == 255
                    && pointsRGBMap.get("L2")[0] == 255
                    && pointsRGBMap.get("L1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 255
                    && pointsRGBMap.get("R2")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("L5")[0] == 0
                    && pointsRGBMap.get("L4")[0] == 255
                    && pointsRGBMap.get("L3")[0] == 255
                    && pointsRGBMap.get("L2")[0] == 255
                    && pointsRGBMap.get("L1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("R1")[0] == 0) {
                return true;
            }
        }

        if (module == 3) {
            if (pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("U1")[0] == 0
                    && pointsRGBMap.get("D1")[0] == 0) {
                return true;
            }else if (pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("L1")[0] == 0
                    && pointsRGBMap.get("R1")[0] == 0) {
                return true;
            }
        }

        if (module == 4) {
            if (pointsRGBMap.get("U1")[0] == 0
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 255
                    && pointsRGBMap.get("D2")[0] == 255
                    && pointsRGBMap.get("D3")[0] == 255
                    && pointsRGBMap.get("D4")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("U2")[0] == 0
                    && pointsRGBMap.get("U1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 255
                    && pointsRGBMap.get("D2")[0] == 255
                    && pointsRGBMap.get("D3")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("U3")[0] == 0
                    && pointsRGBMap.get("U2")[0] == 255
                    && pointsRGBMap.get("U1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 255
                    && pointsRGBMap.get("D2")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("U4")[0] == 0
                    && pointsRGBMap.get("U3")[0] == 255
                    && pointsRGBMap.get("U2")[0] == 255
                    && pointsRGBMap.get("U1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 0) {
                return true;
            }
        }



        if (module == 5) {
            if (pointsRGBMap.get("U1")[0] == 0
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 255
                    && pointsRGBMap.get("D2")[0] == 255
                    && pointsRGBMap.get("D3")[0] == 255
                    && pointsRGBMap.get("D4")[0] == 255
                    && pointsRGBMap.get("D5")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("U2")[0] == 0
                    && pointsRGBMap.get("U1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 255
                    && pointsRGBMap.get("D2")[0] == 255
                    && pointsRGBMap.get("D3")[0] == 255
                    && pointsRGBMap.get("D4")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("U3")[0] == 0
                    && pointsRGBMap.get("U2")[0] == 255
                    && pointsRGBMap.get("U1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 255
                    && pointsRGBMap.get("D2")[0] == 255
                    && pointsRGBMap.get("D3")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("U4")[0] == 0
                    && pointsRGBMap.get("U3")[0] == 255
                    && pointsRGBMap.get("U2")[0] == 255
                    && pointsRGBMap.get("U1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 255
                    && pointsRGBMap.get("D2")[0] == 0) {
                return true;
            } else if (pointsRGBMap.get("U5")[0] == 0
                    && pointsRGBMap.get("U4")[0] == 255
                    && pointsRGBMap.get("U3")[0] == 255
                    && pointsRGBMap.get("U2")[0] == 255
                    && pointsRGBMap.get("U1")[0] == 255
                    && pointsRGBMap.get("CORE")[0] == 255
                    && pointsRGBMap.get("D1")[0] == 0) {
                return true;
            }
        }


        return false;
    }
}
