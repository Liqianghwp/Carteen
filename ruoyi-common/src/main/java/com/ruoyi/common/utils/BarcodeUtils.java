package com.ruoyi.common.utils;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * 条形码工具类
 * @author lingzhi
 * @date 2022/3/18
 */
public class BarcodeUtils {
    private static final int IMAGE_WIDTH = 300;
    private static final int IMAGE_HEIGHT = 50;

    /**
     * 针对条形码进行解析
     *
     * @param imgPath 图片路径
     * @return
     */
    public static String decodeBar(String imgPath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imgPath));
            return decodeBar(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 针对条形码进行解析
     *
     * @param imageData 图片二进制内容
     * @return
     */
    public static String decodeBar(byte[] imageData) {
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(imageData);
            BufferedImage image = ImageIO.read(input);
            return decodeBar(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析条形码内容
     * @param image
     * @return
     */
    private static String decodeBar(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = null;
        try {
            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建条形码,保存图片
     * @param contents 内容
     * @param imgPath 图片路径
     */
    public static void encodeBar(String contents, String imgPath) {
        // 条形码的最小宽度
        int codeWidth = 98;
        codeWidth = Math.max(codeWidth, IMAGE_WIDTH);
        int codeHeight = 40;
        codeHeight = Math.max(codeHeight, IMAGE_HEIGHT);
        BufferedImage image = encodeBar(contents, codeWidth, codeHeight);
        try {
            ImageIO.write(image, "png", new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建条形码
     * @param contents 内容
     * @return
     */
    public static BufferedImage encodeBar(String contents) {
        // 条形码的最小宽度
        int codeWidth = 98;
        codeWidth = Math.max(codeWidth, IMAGE_WIDTH);
        int codeHeight = 40;
        codeHeight = Math.max(codeHeight, IMAGE_HEIGHT);
        return encodeBar(contents, codeWidth, codeHeight);
    }

    /**
     * 创建条形码
     * @param contents 内容
     * @param width 宽度
     * @param height 高度
     * @return 图片io对象
     */
    public static BufferedImage encodeBar(String contents, int width, int height) {
        // 条形码的最小宽度
        int codeWidth = 98;
        codeWidth = Math.max(codeWidth, width);
        try {
            //生成默认zxing条形码对象
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.CODE_39, codeWidth, height, null);
            //去除zxing自动添加的白边
            bitMatrix = deleteWhiteBorder(bitMatrix);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 去除白边
     * @param bitMatrix
     * @return
     */
    private static BitMatrix deleteWhiteBorder(BitMatrix bitMatrix) {
        int[] rec = bitMatrix.getEnclosingRectangle();
        int resWidth = rec[2];
        int resHeight = rec[3];

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (bitMatrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    /**
     * 把带logo的二维码下面加上文字
     * @author fxbin
     * @param image  条形码图片
     * @param words  文字
     * @return 返回BufferedImage
     */
    private static BufferedImage insertWords(BufferedImage image, String words){
        // 新的图片，把带logo的二维码下面加上文字
        if (StringUtils.isNotEmpty(words)) {

            BufferedImage outImage = new BufferedImage(image.getWidth()+10, image.getHeight() + 30, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = outImage.createGraphics();

            // 抗锯齿
            setGraphics2D(g2d);
            // 设置白色
            setColorWhite(g2d);

            // 画条形码到新的面板
            g2d.drawImage(image, 5, 5, image.getWidth(), image.getHeight(), null);
            // 画文字到新的面板
            Color color=new Color(0, 0, 0);
            g2d.setColor(color);
            // 字体、字型、字号
            g2d.setFont(new Font("Arial", Font.PLAIN, 18));
            //文字长度
            int strWidth = g2d.getFontMetrics().stringWidth(words);
            //总长度减去文字长度的一半  （居中显示）
            int wordStartX=(image.getWidth() - strWidth) / 2 + 5;
            //height + (outImage.getHeight() - height) / 2 + 12
            int wordStartY=image.getHeight()+25;

            // 画文字
            g2d.drawString(words, wordStartX, wordStartY);
            g2d.dispose();
            outImage.flush();
            return outImage;
        }
        return null;
    }

    /**
     * 设置 Graphics2D 属性  （抗锯齿）
     * @param g2d  Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private static void setGraphics2D(Graphics2D g2d){
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(s);
    }

    /**
     * 设置背景为白色
     * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private static void setColorWhite(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        //填充整个屏幕
        g2d.fillRect(0,0,600,600);
        //设置笔刷
        g2d.setColor(Color.BLACK);
    }

    /**
     * 创建条形码,插入内容显示
     * @param words
     * @return
     */
    public static BufferedImage encodeBarWithWords(String words) {
        BufferedImage image = encodeBar(words);
        return insertWords(image, words);
    }

    public static void main(String[] args) {
        BufferedImage image = encodeBarWithWords("JMB00003");
        try {
            ImageIO.write(image, "png", new File("/Users/lingzhi/Downloads/barcode1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
