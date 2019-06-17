package com.zjcds.common.syslog.util;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.loader.DelegatingLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.zjcds.common.syslog.domain.LogEvent;
import com.zjcds.common.syslog.exception.TemplateParseException;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created date：2018-03-03
 * @author niezhegang
 */
public class TemplateUtils {
    private static PebbleEngine pebbleEngine;
    static {
        List<Loader<?>> loaders = new ArrayList<>();
        loaders.add(new StringLoader());
        pebbleEngine = new PebbleEngine
                .Builder()
                .loader(new DelegatingLoader(loaders)).build();
    }

    public static String evaluateTemplate(String templateName,Map<String,Object> evaluateContext) {
        try {
            PebbleTemplate pebbleTemplate = pebbleEngine.getTemplate(templateName);
            StringWriter sw = new StringWriter();
            pebbleTemplate.evaluate(sw,evaluateContext);
            return sw.toString();
        } catch (Exception e) {
            throw new TemplateParseException("模板解析出错！",e);
        }
    }

    public static class StringLoader implements Loader<String> {

        @Override
        public Reader getReader(String cacheKey) throws LoaderException {
            LogEvent logEvent = LogRegisterUtils.getLogEvent(cacheKey);
            return new StringReader(logEvent.getTemplateText());
        }

        @Override
        public void setCharset(String charset) {

        }

        @Override
        public void setPrefix(String prefix) {

        }

        @Override
        public void setSuffix(String suffix) {

        }

        @Override
        public String resolveRelativePath(String relativePath, String anchorPath) {
            return null;
        }

        @Override
        public String createCacheKey(String templateName) {
            return templateName;
        }
    }
}