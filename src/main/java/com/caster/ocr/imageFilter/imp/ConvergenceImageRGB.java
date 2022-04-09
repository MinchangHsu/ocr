package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;
import com.caster.ocr.policy.shapeSurround.AbsShapeProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 收斂圖片 RGB 將鄰近RGB 趨於相近顏色
 * 建構子必須傳入:
 *      1.指定形狀的物件(AbsShapeProcess)
 *      2.模組編號(module)
 *          1:全部 RGB 收臉
 *          2:直線 RGB 收斂
 *          3:Y軸極點 RGB 收斂
 *      3.層數(step)
 *
 * Created by Caster on 2019/2/15.
 */
public class ConvergenceImageRGB extends AbstractFilter {

    int step;
    int module;

    AbsShapeProcess shapeProcess ;

    public ConvergenceImageRGB(AbsShapeProcess shapeProcess,int module, int step) {
        this.shapeProcess = shapeProcess;
        this.module = module;
        this.step = step;
    }

    @Override
    public Map<String, int[]> imageFilterRule(ImagePolicy tUtil) {
        init(tUtil);
        tUtil.setIsTest(true);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Map<String, int[]> pointsRGB = tUtil.translateRGBToMap(shapeProcess.init(pointsRGBInfoMap, originRGBInfoMap, x, y, step));
                int[] RGB = cleanRule(pointsRGB);
                pointsRGBInfoMap.put(x + "," + y, RGB);
                tUtil.printSystemLog(true, RGB[0]);
            }
            if (tUtil.checkIsTest()) {
                System.out.println();
            }
        }
        return pointsRGBInfoMap;
    }

    public int[] cleanRule(Map<String, int[]> pointsRGBMap) {
        boolean core = pointsRGBMap.get("CORE")[0] != 255 ? true : false;
        if (!core) {
            return new int[]{255, 255, 255};
        }
        List<int[]> rgbList = new ArrayList<>();

        if(module == 1){
            for (String key : pointsRGBMap.keySet()) {
                int r = pointsRGBMap.get(key)[0];
                int g = pointsRGBMap.get(key)[1];
                int b = pointsRGBMap.get(key)[2];
                if (r != whiteColor[0] && g != whiteColor[1] && b != whiteColor[2]) {
                    rgbList.add(pointsRGBMap.get(key));
                }
            }

            return rgbList.size() != 0 ? translteRGB(rgbList) :pointsRGBMap.get("CORE");
        } else if(module == 2){
            for (String key : pointsRGBMap.keySet()) {
                if(key.equals("U"+step) || key.equals("D"+step) ){
                    int r = pointsRGBMap.get(key)[0];
                    int g = pointsRGBMap.get(key)[1];
                    int b = pointsRGBMap.get(key)[2];
                    if (r != whiteColor[0] && g != whiteColor[1] && b != whiteColor[2]) {
                        rgbList.add(pointsRGBMap.get(key));
                    }
                }
            }
            return rgbList.size() != 0 ? translteRGB(rgbList) :pointsRGBMap.get("CORE");
        } else if(module == 3){
            for (String key : pointsRGBMap.keySet()) {
                if(key.contains("U"+step) || key.contains("D"+step) ){
                    int r = pointsRGBMap.get(key)[0];
                    int g = pointsRGBMap.get(key)[1];
                    int b = pointsRGBMap.get(key)[2];
                    if (r != whiteColor[0] && g != whiteColor[1] && b != whiteColor[2]) {
                        rgbList.add(pointsRGBMap.get(key));
                    }
                }
            }
            return rgbList.size() != 0 ? translteRGB(rgbList) :pointsRGBMap.get("CORE");
        }
        return pointsRGBMap.get("CORE");
    }

    public int[] translteRGB(List<int[]> rgbList){
        int countR = 0;
        int countG = 0;
        int countB = 0;
        for (int[] RGB : rgbList) {
            countR += RGB[0];
            countG += RGB[1];
            countB += RGB[2];
        }
        return new int[]{countR / rgbList.size(), countG / rgbList.size(), countB / rgbList.size()};
    }
}
