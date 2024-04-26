package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;
import com.caster.ocr.policy.shapeSurround.imp.SquareShape;

import java.util.Map;

/**
 * 辨識陰影，並將陰影部分去除。
 * 建構子須傳入:
 *      1.層數(step)
 * Created by Caster on 2019/2/11.
 */
public class RemoveShadow extends AbstractFilter {
    int step;

    public RemoveShadow(int step) {
        this.step = step;
    }

    @Override
    public Map<String, int[]> imageFilterRule(ImagePolicy tUtil) {
//        init(tUtil);
//        for (int y = 0; y < h; y++) {
//            for (int x = 0; x < w; x++) {
//                Map<String, int[]> pointsRGB = tUtil.translateRGBToMap(new SquareShape(pointsRGBInfoMap, originRGBInfoMap, x, y, step));
//                if (cleanRule(pointsRGB)) {
//                    pointsRGBInfoMap.put(x + "," + y, whiteColor);
//                    tUtil.printSystemLog(false);
//                } else {
//                    pointsRGBInfoMap.put(x + "," + y, originRGBInfoMap.get(x + "," + y));
//                    tUtil.printSystemLog(true, originRGBInfoMap.get(x + "," + y)[0]);
//                }
//            }
//            if (tUtil.checkIsTest()) {
//                System.out.println();
//            }
//        }
        return pointsRGBInfoMap;
    }

//    public boolean cleanRule(Map<String, int[]> pointsRGBMap) {
//        boolean core = pointsRGBMap.get("CORE")[0] != 255 ? true : false;
//        if (!core) {
//            return false;
//        }
//        boolean checkCoreNeedCut = false;
//        for (String pointRGBKey : pointsRGBMap.keySet()) {
//            if (pointRGBKey.indexOf(String.valueOf(step)) != -1) {
//                if (pointsRGBMap.get(pointRGBKey)[0] == 255)
//                    checkCoreNeedCut = true;
//                else
//                    return false;
//            }
//        }
//        return checkCoreNeedCut;
//    }
}
