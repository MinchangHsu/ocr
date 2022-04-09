package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * 先计算图片中，RGB 个别颜色数量，留下最多的颜色，移除其他颜色。
 *
 * 建構子必須傳入:
 *      1.模組編號(module)
 *          1: 留下最多的颜色
 *          2: 留下最少的颜色
 *      2.留下多少颜色(count)
 *
 *
 * Created by Caster on 2019/4/9.
 */
public class ExcludeCountCollectionRGBList extends AbstractFilter {

    int modlue ;
    int count ;
    Map<String,Integer> countRGBMap = new HashMap<>();

    public ExcludeCountCollectionRGBList(int modlue, int count) {
        this.modlue = modlue;
        this.count = count;
    }

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
            if (tUtil.checkIsTest())
                System.out.println();
        }
        return pointsRGBInfoMap;
    }


    public void collectionRGBData() {

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int r = originRGBInfoMap.get(x + "," + y)[0];
                int g = originRGBInfoMap.get(x + "," + y)[1];
                int b = originRGBInfoMap.get(x + "," + y)[2];
                String mapKey = r + "," + g + "," + b;

                if(r != 255 && g != 255 && b != 255){
                    if(countRGBMap.containsKey(mapKey)){
                        countRGBMap.put(mapKey,(countRGBMap.get(mapKey)+1));
                    }else{
                        countRGBMap.put(mapKey,1);
                    }
                }
            }
        }

        countRGBMap.entrySet().removeIf(entry -> entry.getValue() < 50);

    }

    public boolean cleanRule(int x, int y) {

        int r = originRGBInfoMap.get(x + "," + y)[0];
        int g = originRGBInfoMap.get(x + "," + y)[1];
        int b = originRGBInfoMap.get(x + "," + y)[2];

        String mapKey = r + "," + g + "," + b;

        if (!countRGBMap.containsKey(mapKey)) {
            return true;
        } else {
            return false;
        }
    }
}
