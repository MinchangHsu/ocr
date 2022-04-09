package com.caster.ocr.policy;


import com.caster.ocr.policy.shapeSurround.AbsShapeProcess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.caster.ocr.utils.Contents.imageFolder;

/**
 * Created by Caster on 2019/2/15.
 */
public abstract class ShapeSurroundPolicy implements ImagePolicy {
    int h = 0;  // image Height
    int w = 0;  // image Width
    protected boolean isTest = false;  // check is Development step
    Map<String, int[]> originRGBInfoMap = new ConcurrentHashMap<>(); // origin verify image RGB map

    @Override
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

    @Override
    public void deleteVerifyImageFile(List<File> list) {
        for (File file : list) {
            file.delete();
        }
    }

    @Override
    public void printSystemLog(boolean isGood) {
        printSystemLog(isGood, 111);
    }

    @Override
    public void printSystemLog(boolean isGood, int r) {
        if (isTest) {
            if (isGood && r != 255) {
                System.out.print(r + "\t");
            } else {
                System.out.print("-\t");
            }
        }
    }

    @Override
    public void resetOriginRGBMap(Map<String, int[]> pointsRGBInfoMap) {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                originRGBInfoMap.put(x + "," + y, pointsRGBInfoMap.get(x + "," + y));
            }
        }
    }

    @Override
    public File printPicture(Map<String, int[]> printImageMap) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(w, h, 1);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (printImageMap.containsKey(x + "," + y)) {
                    int[] rgbArray = printImageMap.get(x + "," + y);
                    bufferedImage.setRGB(x, y, new Color(rgbArray[0], rgbArray[1], rgbArray[2]).getRGB());
                }
            }
        }

        if (!new File(imageFolder).exists()) {
            new File(imageFolder).mkdirs();
        }

        File cleanFile = new File(imageFolder, "Clean_" + System.currentTimeMillis() + ".png");

        ImageIO.write(bufferedImage, "png", cleanFile);

        return cleanFile;
    }

    @Override
    public boolean checkIsTest() {
        return isTest;
    }

    @Override
    public void setIsTest(boolean isTest) {
        this.isTest = isTest;
    }

    @Override
    public void setImageSize(int height, int width) {
        h = height;
        w = width;
    }

    @Override
    public Map<String, int[]> getOriginRGBMap() {
        return this.originRGBInfoMap;
    }

    @Override
    public void setOriginRGBMap(Map<String, int[]> originRGBInfoMap) {
        this.originRGBInfoMap = originRGBInfoMap;
    }

    public abstract Map<String, int[]> translateRGBToMap(AbsShapeProcess shapeProcess);

    @Override
    public File printFinalPicture(Map<String, int[]> printImageMap, String fileName) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(w, h, 1);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (printImageMap.containsKey(x + "," + y)) {
                    int[] rgbArray = printImageMap.get(x + "," + y);
                    bufferedImage.setRGB(x, y, new Color(rgbArray[0], rgbArray[1], rgbArray[2]).getRGB());
                }
            }
        }

        File cleanFile = new File(imageFolder, fileName + ".png");
        ImageIO.write(bufferedImage, "png", cleanFile);

        return cleanFile;
    }
}
