package cn.tycoding.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-23
 */
public class GeneratorDisplay {

    public void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        File configFile = ResourceUtils.getFile("classpath:generator/generatorConfig.xml");
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            GeneratorDisplay display = new GeneratorDisplay();
            display.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
