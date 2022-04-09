package com.caster.ocr.policy.shapeSurround;

import java.util.Map;

/**
 * Created by Caster on 2019/2/15.
 */
public abstract class AbsShapeProcess {
    protected Map<String, int[]> pointsRGBInfoMap;
    protected Map<String, int[]> originRGBInfoMap;
    protected int x, y, step ;
    protected boolean isTest = false;  // check is Development step

    public abstract Map<String, int[]> createShape();

    public abstract AbsShapeProcess init(Map<String, int[]> pointsRGBInfoMap, Map<String, int[]> originRGBInfoMap, int x, int y, int step);

    public int[] setRGBToMap(Map<String, int[]> pointsRGBInfoMap, int j, int i) {
        if (pointsRGBInfoMap.containsKey(j + "," + i)) {
            return pointsRGBInfoMap.get(j + "," + i);
        } else {
            if (originRGBInfoMap.containsKey(j + "," + i)) {
                return originRGBInfoMap.get(j + "," + i);
            } else {
                return new int[]{255, 255, 255};
            }
        }
    }

    public void setIsTest(boolean isTest) {
        this.isTest = isTest;
    }
}
