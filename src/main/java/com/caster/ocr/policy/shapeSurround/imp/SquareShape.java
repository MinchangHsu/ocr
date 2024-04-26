package com.caster.ocr.policy.shapeSurround.imp;


import com.caster.ocr.policy.shapeSurround.AbsShapeProcess;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 產生 正方形形狀的九宮格 Map
 *
 * Created by Caster on 2019/2/15.
 */
public class SquareShape extends AbsShapeProcess {

    boolean usedOriginInfoMap = false;
    public SquareShape() {
    }

    public SquareShape(Map<String, int[]> pointsRGBInfoMap, Map<String, int[]> originRGBInfoMap, int x, int y, int step) {
        this.pointsRGBInfoMap = pointsRGBInfoMap;
        this.originRGBInfoMap = originRGBInfoMap;
        this.x = x;
        this.y = y;
        this.step = step;
    }

    public SquareShape(Map<String, int[]> pointsRGBInfoMap, Map<String, int[]> originRGBInfoMap, int x, int y, int step, boolean usedOriginInfoMap) {
        this.pointsRGBInfoMap = pointsRGBInfoMap;
        this.originRGBInfoMap = originRGBInfoMap;
        this.x = x;
        this.y = y;
        this.step = step;
        this.usedOriginInfoMap = usedOriginInfoMap;
    }

    @Override
    public AbsShapeProcess init(Map<String, int[]> pointsRGBInfoMap, Map<String, int[]> originRGBInfoMap, int x, int y, int step){
        return new SquareShape(pointsRGBInfoMap,originRGBInfoMap,x,y,step);
    }

    @Override
    public Map<String, int[]> createShape() {
        Map<String, int[]> pointMap = new LinkedHashMap<>();
        for (int i = -step; i < (step + 1); i++) {
            for (int j = -step; j < (step + 1); j++) {

                int RGB[] = setRGBToMap(usedOriginInfoMap ? originRGBInfoMap : pointsRGBInfoMap, (j + x), (i + y));

                // 都是以图片的角度 并非 数学角度 判断象限
                if (i == 0 && j == 0) {  // 中心點
                    pointMap.put("CORE", RGB);
                } else if (j == 0 && i < 0) { // Y軸 -
                    pointMap.put("U" + Math.abs(i), RGB);
                } else if (j == 0 && i > 0) {  // Y軸 +
                    pointMap.put("D" + Math.abs(i), RGB);
                } else if (j < 0 && i == 0) {  // x軸 -
                    pointMap.put("L" + Math.abs(j), RGB);
                } else if (j > 0 && i == 0) {  // x軸 +
                    pointMap.put("R" + Math.abs(j), RGB);
                } else if (j > 0 && i < 0) { // 一象限
                    pointMap.put("R" + Math.abs(j) + "U" + Math.abs(i), RGB);
                } else if (j < 0 && i < 0) { // 二象限
                    pointMap.put("L" + Math.abs(j) + "U" + Math.abs(i), RGB);
                } else if (j < 0 && i > 0) { // 三象限
                    pointMap.put("L" + Math.abs(j) + "D" + Math.abs(i), RGB);
                } else if (j > 0 && i > 0) { // 四象限
                    pointMap.put("R" + Math.abs(j) + "D" + Math.abs(i), RGB);
                }
            }
        }

        if (isTest) {
            if (x == 0 && y == 0) {
                int printIndex = 1;
                for (String key : pointMap.keySet()) {

                    key = key.length() != 4 ? " " + key + " " : key;

                    if ((printIndex % ((step * 2) + 1)) == 0) {
                        System.out.println(key + "\t");
                    } else {
                        System.out.print(key + "\t");
                    }
                    printIndex++;
                }
            }
        }
        return pointMap;
    }
}
