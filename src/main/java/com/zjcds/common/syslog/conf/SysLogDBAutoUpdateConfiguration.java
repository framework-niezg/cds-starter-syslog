package com.zjcds.common.syslog.conf;

import com.zjcds.common.db.au.domain.ModuleProperties;
import com.zjcds.common.db.au.domain.ModulePropertiesConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.zjcds.common.db.au.DBAutoUpdateProperties.DBAutoUpdatePropertiesPath;

/**
 * created date：2018-03-27
 * @author niezhegang
 */
@Configuration
@EnableConfigurationProperties(value = SysLogDBAutoUpdateConfiguration.ModuleDBAutoUpdateProperties.class)
public class SysLogDBAutoUpdateConfiguration {
    private final static String ModuleName = "syslog";
    /**
     * syslog模块的数据库升级配置
     * @return
     */
    @Bean
    public ModulePropertiesConfig syslogModulePropertiesConfig(ModuleDBAutoUpdateProperties moduleDBAutoUpdateProperties) {
        ModuleProperties syslogModuleProperties = moduleDBAutoUpdateProperties.getSyslog();
        if(syslogModuleProperties == null){
            syslogModuleProperties = new ModuleProperties();
        }
        //模块名固定
        syslogModuleProperties.setModuleName(ModuleName);
        //版本号也固定不需要配置
        syslogModuleProperties.setCurrentVersion(1);
        if(syslogModuleProperties.getOrder() == null) {
            syslogModuleProperties.setOrder(Integer.MIN_VALUE + 10);
        }
        return ModulePropertiesConfig.newBuilder()
                                        .moduleProperties(syslogModuleProperties)
                                        .build();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = DBAutoUpdatePropertiesPath)
    public static class ModuleDBAutoUpdateProperties {
        @NestedConfigurationProperty
        private ModuleProperties syslog;
    }
}
