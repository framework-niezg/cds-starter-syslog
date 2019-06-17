package com.zjcds.common.syslog.annotation;

import com.zjcds.common.syslog.domain.SysLogAspect;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author luokp on 2018/8/7.
 */
public class SysLogRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition("sysLogAspect", BeanDefinitionBuilder.rootBeanDefinition(SysLogAspect.class).getBeanDefinition());
    }

}
