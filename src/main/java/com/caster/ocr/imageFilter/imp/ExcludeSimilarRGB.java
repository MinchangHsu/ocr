package com.caster.ocr.imageFilter.imp;

import com.caster.ocr.policy.ImagePolicy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 排除相近顏色
 * 抓取噪點 RGB 顏色區間
 * 收集其RGB最大最小極值。
 * 進行判斷
 * 建構子須傳入
 * <p>
 * <p>
 * Created by Caster on 2019/3/4.
 */
public class ExcludeSimilarRGB extends AbstractFilter {

    int rMax;
    int rMin;
    int gMax;
    int gMin;
    int bMax;
    int bMin;

    @Override
    public Map<String, int[]> imageFilterRule(ImagePolicy tUtil) {
        init(tUtil);
        collectionRGBData();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (cleanRule(x, y)) {
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

    public void collectionRGBData() {
        Set<Integer> RSet = new HashSet<>();
        Set<Integer> GSet = new HashSet<>();
        Set<Integer> BSet = new HashSet<>();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (x <= 5 || x >= 80) {
                    if (originRGBInfoMap.get(x + "," + y)[0] == 255) {
                        break;
                    }
                    if (excludLargeRGB(originRGBInfoMap.get(x + "," + y))) {
                        break;
                    }
                    RSet.add(originRGBInfoMap.get(x + "," + y)[0]);
                    GSet.add(originRGBInfoMap.get(x + "," + y)[1]);
                    BSet.add(originRGBInfoMap.get(x + "," + y)[2]);
                }
            }
        }

        rMax = Collections.max(RSet);
        rMin = Collections.min(RSet);
        gMax = Collections.max(GSet);
        gMin = Collections.min(GSet);
        bMax = Collections.max(BSet);
        bMin = Collections.min(BSet);

        System.out.println("  rMin:" + rMin + "  rMax:" + rMax +  "  gMin:" + gMin + "  gMax:" + gMax + "  bMin:" + bMin + "  bMax:" + bMax );

    }

    public boolean cleanRule(int x, int y) {

        if (x <= 5 || x >= 80) {
            return false;
        }

        int r = originRGBInfoMap.get(x + "," + y)[0];
        int g = originRGBInfoMap.get(x + "," + y)[1];
        int b = originRGBInfoMap.get(x + "," + y)[2];

        if (r >= rMin && r <= rMax &&
                g >= gMin && g <= gMax &&
                b >= bMin && b <= bMax) {
            return true;
        } else {
            return false;
        }
    }

    public boolean excludLargeRGB(int[] RGB) {
        int r = RGB[0];
        int g = RGB[1];
        int b = RGB[2];

        if ((r + g + b) > 300) {
            return true;
        } else {
            return false;
        }
    }
}
