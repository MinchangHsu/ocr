package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 去除 圖片上 RGB 有符合設定的數值區間
 *
 * 建構子必須傳入:
 *      1.模組編號(module)
 *          1:R
 *          2:G
 *          3:B
 *      2.RGB 元素 (index) 用哪個比較
 *      3.加權數值(+) plus   <-- 可只傳一個數值
 *      4.加權數值(-) minus  <-- 可只傳一個數值  詳情請看建構子
 *
 * 1. 設定要抓取圖片那些座標
 * 2. 計算所有座標 RGB 的所有顏色平均值 並且給予加權數值 ( +- count)
 * 3. 判斷是否有在區間內，進行排除。
 *
 *
 * Created by Caster on 2019/2/15.
 */
public class ExcludeCollectionRGBList extends AbstractFilter {

    int modlue ;
    int index ;
    int plus ;
    int minus ;

    public ExcludeCollectionRGBList(int modlue, int index, int count) {
        this.modlue = modlue;
        this.index = index;
        this.plus = count;
        this.minus = count;
    }

    public ExcludeCollectionRGBList(int modlue, int index, int plus, int minus) {
        this.modlue = modlue;
        this.index = index;
        this.plus = plus;
        this.minus = minus;
    }

    @Override
    public Map<String, int[]> imageFilterRule(ImagePolicy tUtil) {
        init(tUtil);
        Set<Integer> rgbSet = new HashSet<>();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (x >= 100 && originRGBInfoMap.get(x + "," + y)[index] < 100) {
                    int rgb = originRGBInfoMap.get(x + "," + y)[index];
                    if (rgb != whiteColor[index]) {
                        rgbSet.add(rgb);
                    }
                }
            }
        }

        int maxR = Collections.max(rgbSet);
        int minR = Collections.min(rgbSet);

        int middon = (maxR+minR) / 2;

        System.out.print("ExcludeCollectionRGBList:");
        System.out.println("maxR :" + maxR + "   minR:" + minR + "  middon:" + middon + "  區間:" + (middon - 5) + " ~ " + (middon + 5));
        System.out.println("---------------------------------------");

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (x < 74 && cleanRule(middon, originRGBInfoMap.get(x + "," + y))) {
                    pointsRGBInfoMap.put(x + "," + y, whiteColor);
                    tUtil.printSystemLog(false);
                } else {
                    pointsRGBInfoMap.put(x + "," + y, originRGBInfoMap.get(x + "," + y));
                    tUtil.printSystemLog(true, originRGBInfoMap.get(x + "," + y)[index]);
                }
            }
            if (tUtil.checkIsTest()) {
                System.out.println();
            }
        }
        return pointsRGBInfoMap;
    }

    public boolean cleanRule(int middon, int[] rgb) {
        if(((modlue == 1) || (modlue == 0)) && (rgb[0] > (middon - minus) && rgb[0] < (middon - plus))){
            return true;
        }

        if(((modlue == 2) || (modlue == 0)) && (rgb[1] > (middon - minus) && rgb[1] < (middon - plus))){
            return true;
        }

        if(((modlue == 3) || (modlue == 0)) && (rgb[2] > (middon - minus) && rgb[2] < (middon - plus))){
            return true;
        }
        return false;
    }
}
