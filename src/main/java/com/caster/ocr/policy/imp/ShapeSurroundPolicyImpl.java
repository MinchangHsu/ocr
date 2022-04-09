package com.caster.ocr.policy.imp;



import com.caster.ocr.policy.ShapeSurroundPolicy;
import com.caster.ocr.policy.shapeSurround.AbsShapeProcess;

import java.util.Map;

/**
 * 處理驗證圖片策略 - 基礎為正方形進行去噪
 * Created by Caster on 2019/2/1.
 */
public class ShapeSurroundPolicyImpl extends ShapeSurroundPolicy {

    /**
     * 取得 RGB 九宮格內容
     * CORE(0,0)   4層範例  表格化表示 KEY 值
     * L4U4	L3U4	L2U4	L1U4	 U4 	R1U4	R2U4	R3U4	R4U4
     * L4U3	L3U3	L2U3	L1U3	 U3 	R1U3	R2U3	R3U3	R4U3
     * L4U2	L3U2	L2U2	L1U2	 U2 	R1U2	R2U2	R3U2	R4U2
     * L4U1	L3U1	L2U1	L1U1	 U1 	R1U1	R2U1	R3U1	R4U1
     * L4 	 L3 	 L2 	 L1 	CORE	 R1 	 R2 	 R3 	 R4
     * L4D1	L3D1	L2D1	L1D1	 D1 	R1D1	R2D1	R3D1	R4D1
     * L4D2	L3D2	L2D2	L1D2	 D2 	R1D2	R2D2	R3D2	R4D2
     * L4D3	L3D3	L2D3	L1D3	 D3 	R1D3	R2D3	R3D3	R4D3
     * L4D4	L3D4	L2D4	L1D4	 D4 	R1D4	R2D4	R3D4	R4D4
     */
    @Override
    public Map<String, int[]> translateRGBToMap(AbsShapeProcess shapeProcess) {
        shapeProcess.setIsTest(isTest);
        return shapeProcess.createShape();
    }

}
