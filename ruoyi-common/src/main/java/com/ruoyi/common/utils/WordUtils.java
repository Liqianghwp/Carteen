//package com.ruoyi.common.utils;
//
//import com.aspose.words.Document;
//import com.aspose.words.License;
//import com.aspose.words.SaveFormat;
//import com.ruoyi.common.utils.sign.Base64;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateExceptionHandler;
//import lombok.SneakyThrows;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * word文档工具类
// * @author lingzhi
// * @date 2022/3/18
// */
//public class WordUtils {
//
//    private static Configuration configuration;
//    static {
//        configuration = new Configuration(Configuration.getVersion());
//        configuration.setDefaultEncoding("UTF-8");
//        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
//        //configuration.setClassForTemplateLoading(WordUtils.class, "/word");
//        //FileUtils.class.getResource("/").getPath()+"word"
//    }
//
//    /**
//     * 根据模板文件,动态创建word文档
//     * @param dataMap
//     * @param templatePath
//     * @param templateFileName
//     * @return
//     * @throws IOException
//     */
//    public static String createDoc(Map<String, String> dataMap, String templatePath, String templateFileName) throws IOException {
//        configuration.setDirectoryForTemplateLoading(new File(templatePath));
//        //加载需要装填的模板
//        Template template  = configuration.getTemplate(templateFileName);
//        try (StringWriter output = new StringWriter()) {
//            template.process(dataMap, output);
//            output.flush();
//            return output.toString();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 加载破解版aspose.words工具包的license
//     */
//    @SneakyThrows
//    private static void getLicense() {
//        try (InputStream is = WordUtils.class.getClassLoader().getResourceAsStream("License.xml")) {
//            License license = new License();
//            license.setLicense(is);
//        }
//    }
//
//    /**
//     * 使用aspose.words工具包转pdf文件
//     * @param wordData
//     * @return
//     */
//    @SneakyThrows
//    public static byte[] wordToPdf(byte[] wordData) {
//        getLicense();
//        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
//            Document doc = new Document(new ByteArrayInputStream(wordData));
//            doc.save(os, SaveFormat.PDF);
//            return os.toByteArray();
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        File file = new File("/Users/lingzhi/Downloads/id_pic2.jpeg");
//        byte[] fileByte = Files.readAllBytes(file.toPath());
//        String imageBase64 = Base64.encode(fileByte);
//        HashMap<String, String> dataMap = new HashMap();
//        dataMap.put("qm", imageBase64);
//        dataMap.put("rq", DateUtils.getDate());
//        dataMap.put("jcd", "北京");
//        dataMap.put("tmh", "AIIII888");
//        dataMap.put("xm", "大宝");
//        dataMap.put("sjh", "18882727273");
//        dataMap.put("ywjlxm", "何丽");
//        dataMap.put("ywjldh", "19928283737");
//        dataMap.put("ssfgs", "加美康联");
//        dataMap.put("xb", "男");
//        dataMap.put("nl", "28");
//        dataMap.put("sfz", "19828283726");
//        dataMap.put("cysj", "2022-03-11");
//        dataMap.put("cybw", "口腔");
//        dataMap.put("sjrsfyazjzs", "否");
//        dataMap.put("qsmazjzs", "无");
//        dataMap.put("sjrsfhyqtjb", "是");
//        dataMap.put("qsmqtjbqk", "轻微过敏性鼻炎");
//
////        String jfree = JfreechartUtils.createBarChartPngBase64Data();
////        dataMap.put("jfree", jfree);
//        String word = createDoc(dataMap, "/Users/lingzhi/diandong/ruoyi/uploadPath/file_template/","template_1_2.xml");
//        byte[] pdfData = wordToPdf(word.getBytes(StandardCharsets.UTF_8));
//        Files.write(Paths.get("/Users/lingzhi/Downloads/zhiqing.pdf"), pdfData);
//        /*File wordFile = new File("/Users/lingzhi/Downloads/test.docx");
//        if (!wordFile.exists()) {
//            wordFile.createNewFile();
//        }
//        FileWriter fileWriter = new FileWriter(wordFile);
//        fileWriter.write(word);
//        fileWriter.flush();
//        fileWriter.close();*/
//
//        //wordToPdf("/Users/lingzhi/Downloads/test.docx", "/Users/lingzhi/Downloads/test.pdf");
//    }
//}
