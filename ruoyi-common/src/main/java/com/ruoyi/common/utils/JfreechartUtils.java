package com.ruoyi.common.utils;

import com.ruoyi.common.utils.sign.Base64;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Jfreechart生成图表工具类
 * @author lingzhi
 * @date 2022/3/19
 */
public class JfreechartUtils {
    public static byte[] createBarChartPngByteData() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(40, "", "语文");
        dataset.addValue(50, "", "数学");
        dataset.addValue(60, "", "化学");
        dataset.addValue(70, "", "物理");
        dataset.addValue(80, "", "生物");

        JFreeChart chart = ChartFactory.createBarChart("学生对教师授课满意度", // 图表标题
                "课程名", // 目录轴的显示标签
                "百分比", // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                false, // 是否显示图例(对于简单的柱状图必须是false)
                false, // 是否生成工具
                false // 是否生成URL链接
        );
        //3. 设置整个柱状图的颜色和文字（char对象的设置是针对整个图形的设置）
        chart.setBackgroundPaint(ChartColor.WHITE); // 设置总的背景颜色

        //1. 图形标题文字设置
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("宋体",Font.BOLD,20));

        //2. 图形X轴坐标文字的设置
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        axis.setLabelFont(new Font("宋体",Font.BOLD,22));  //设置X轴坐标上标题的文字
        axis.setTickLabelFont(new Font("宋体",Font.BOLD,15));  //设置X轴坐标上的文字

        //2. 图形Y轴坐标文字的设置
        ValueAxis valueAxis = plot.getRangeAxis();
        valueAxis.setLabelFont(new Font("宋体",Font.BOLD,15));  //设置Y轴坐标上标题的文字
        valueAxis.setTickLabelFont(new Font("sans-serif",Font.BOLD,12));//设置Y轴坐标上的文
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(output, chart, 800, 500);
            output.flush();
            byte[] imageData = output.toByteArray();
            return imageData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createBarChartPngBase64Data() {
        return Base64.encode(createBarChartPngByteData());
    }

    public static void main(String[] args) throws IOException {
        Files.write(Paths.get("/Users/lingzhi/Downloads/jfree.png"),createBarChartPngByteData());
    }
}
