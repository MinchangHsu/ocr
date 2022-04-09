package com.caster.ocr.imageFilter.imp;



import com.caster.ocr.imageFilter.IImageFilter;
import com.caster.ocr.policy.ImagePolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caster on 2019/2/11.
 */
public class AbstractFilter implements IImageFilter {

    Map<String, int[]> originRGBInfoMap, pointsRGBInfoMap;
    int h, w;
    ImagePolicy tUtil;

    public AbstractFilter() {
        originRGBInfoMap = new HashMap<>();
        pointsRGBInfoMap = new HashMap<>();
    }

    public void init(ImagePolicy tUtil) {
        this.originRGBInfoMap = tUtil.getOriginRGBMap();
        pointsRGBInfoMap = new HashMap<>();
        this.tUtil = tUtil;
    }

    @Override
    public void setImageSize(int height, int width) {
        h = height;
        w = width;
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

    public boolean cleanRule(int x, int y) {return false;}

    public static boolean compareRGBArray(int module, int[] target, int[] normal){
        boolean gg = false;

        for(int i = 0; i < 3; i++){
            switch (module){
                case 0:
                    gg = target[i] < normal[i] ? false : true;
                    break;
                case 1:
                    gg = target[i] > normal[i] ? false : true;
                    break;
                case 2:
                    gg = target[i] == normal[i] ? false : true;
                    break;
                case 3:
                    gg = target[i] <= normal[i] ? false : true;
                    break;
                case 4:
                    gg = target[i] >= normal[i] ? false : true;
                    break;
            }
            if(gg){
                return false;
            }
        }
        return true;
    }
}
