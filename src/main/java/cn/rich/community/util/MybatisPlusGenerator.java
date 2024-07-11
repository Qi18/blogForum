package cn.rich.community.util;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Types;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;


public class MybatisPlusGenerator {
    private static final String REPOSITORY_PREFIX = "cn.rich.community.repositories";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/community?characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong";

    public static void main(String[] args) {
//         需要生成的表名
        String[] tableNames = new String[] {
                "user"
        };
//
//        generateOthers("src/main/java", tableNames);


        FastAutoGenerator.create(DATABASE_URL, "root", "root")
                .globalConfig(builder -> builder
                        .author("rich")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                        .disableOpenDir()
                )
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.TIMESTAMP) {
                        // 自定义类型转换
                        return DbColumnType.DATE;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> builder
                        .parent("cn.rich.community")
                        .entity("entity")
                        .mapper("mapper")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> {
                    for (String tableName : tableNames) builder.addInclude(tableName);
                    builder.entityBuilder().enableFileOverride().enableLombok().enableChainModel().enableTableFieldAnnotation();
                    builder.mapperBuilder().enableFileOverride();
                    builder.serviceBuilder().disableService().disableServiceImpl();
                    builder.controllerBuilder().disable();
                    }
                )
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }

//    public static void generateOthers(String prefixFileName, String[] tableNames) {
//        File file = new File(prefixFileName);
//        String path = file.getAbsolutePath();
//        AutoGenerator mpg = new AutoGenerator(getDataSourceConfig());
//        // 全局配置
//        mpg.global(getGlobalConfig(path));
//        // 包配置
//        mpg.packageInfo(getOtherPackageConfig());
//        // 策略配置
//        mpg.strategy(getStrategyConfig(tableNames));
//        // 配置模板
//        mpg.template(getOtherTemplateConfig());
//        mpg.injection(getOtherInjectConfig());
//        mpg.execute();
//    }

//    // 数据源配置
//    private static DataSourceConfig getDataSourceConfig() {
//        String url
//                = "jdbc:mysql://localhost:3306/community?characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong";
//        String name = "root";
//        String pwd = "root";
//        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(url, name, pwd);
//        return builder.build();
//    }
//
//    // 全局配置
//    private static GlobalConfig getGlobalConfig(String path) {
//        GlobalConfig.Builder builder = new GlobalConfig.Builder();
//        builder.outputDir(path);
//        builder.fileOverride();
//        builder.disableOpenDir();
//        return builder.build();
//    }
//
//    // 包配置
//    private static PackageConfig getOtherPackageConfig() {
//        PackageConfig.Builder builder = new PackageConfig.Builder();
//        builder.parent("");
//        builder.xml(REPOSITORY_PREFIX + ".mapperxml");
//        builder.serviceImpl(REPOSITORY_PREFIX + ".dao");
//
//        builder.entity(REPOSITORY_PREFIX+ ".model");
//        builder.service(REPOSITORY_PREFIX + ".gateway");
//        builder.mapper(REPOSITORY_PREFIX + ".gateway");
//        return builder.build();
//    }
//
//    // 策略配置
//    private static StrategyConfig getStrategyConfig(String[] tableNames) {
//        StrategyConfig.Builder builder = new StrategyConfig.Builder();
//        builder.enableCapitalMode();
//        // 需要生成的表
//        builder.addInclude(tableNames);
//        return builder.build();
//    }
//
//    // 模板配置
//    private static TemplateConfig getOtherTemplateConfig() {
//        TemplateConfig.Builder builder = new TemplateConfig.Builder();
//        builder.disable(TemplateType.CONTROLLER);
//        builder.disable(TemplateType.SERVICE);
//        return builder.build();
//    }
//
//    // 自定义配置
//    private static InjectionConfig getOtherInjectConfig() {
//        InjectionConfig.Builder builder = new Builder();
//        return builder.build();
//    }






}
