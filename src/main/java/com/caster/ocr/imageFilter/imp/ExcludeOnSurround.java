package com.caster.ocr.imageFilter.imp;


import com.caster.ocr.policy.ImagePolicy;
import com.caster.ocr.policy.shapeSurround.imp.SquareShape;

import java.util.Map;

/**
 * 辨識周遭 n 層 RGB 元素判斷
 * 取得當前目標 周遭 RGB 元素值
 * 建構子須傳入:
 *      1.層數(step)
 *
 * CORE(0,0)  => 當前目標
 *
 * Ex. 4層範例  表格化表示 KEY 值
 *
 * U(up):上  D(down):下  L(left):左  R(right):右
 *
 * L4U4	L3U4	L2U4	L1U4	 U4 	R1U4	R2U4	R3U4	R4U4
 * L4U3	L3U3	L2U3	L1U3	 U3 	R1U3	R2U3	R3U3	R4U3
 * L4U2	L3U2	L2U2	L1U2	 U2 	R1U2	R2U2	R3U2	R4U2
 * L4U1	L3U1	L2U1	L1U1	 U1 	R1U1	R2U1	R3U1	R4U1
 * L4 	 L3 	 L2 	 L1 	CORE	 R1 	 R2 	 R3 	 R4
 * L4D1	L3D1	L2D1	L1D1	 D1 	R1D1	R2D1	R3D1	R4D1
 * L4D2	L3D2	L2D2	L1D2	 D2 	R1D2	R2D2	R3D2	R4D2
 * L4D3	L3D3	L2D3	L1D3	 D3 	R1D3	R2D3	R3D3	R4D3
 * L4D4	L3D4	L2D4	L1D4	 D4 	R1D4	R2D4	R3D4	R4D4
 *
 * Created by Caster on 2019/2/11.
 */
public class ExcludeOnSurround extends AbstractFilter {
    int step;

    public ExcludeOnSurround(int step) {
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
        boolean core = pointsRGBMap.get("CORE")[0] != 255 ? true : false;
        if (!core) {
            return false;
        }
        boolean checkCoreNeedCut = false;
        for (String pointRGBKey : pointsRGBMap.keySet()) {
            if (pointRGBKey.indexOf(String.valueOf(step)) != -1) {
                if (pointsRGBMap.get(pointRGBKey)[0] == 255)
                    checkCoreNeedCut = true;
                else
                    return false;
            }
        }
        return checkCoreNeedCut;
    }
}
