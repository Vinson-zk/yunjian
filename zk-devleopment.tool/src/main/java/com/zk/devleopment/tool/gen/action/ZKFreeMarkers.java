/** 
* Copyright (c) 2004-2020 ZK-Vinson Technologies, Inc.
* address: 
* All rights reserved. 
* 
* This software is the confidential and proprietary information of 
* ZK-Vinson Technologies, Inc. ("Confidential Information"). You shall not 
* disclose such Confidential Information and shall use it only in 
* accordance with the terms of the license agreement you entered into 
* with ZK-Vinson. 
*
* @Title: ZKFreeMarkers.java 
* @author Vinson 
* @Package com.zk.code.generate.gen.utils 
* @Description: TODO(simple description this file what to do.) 
* @date Mar 17, 2020 10:11:44 PM 
* @version V1.0 
*/
package com.zk.devleopment.tool.gen.action;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModel;
import freemarker.template.Version;

/** 
* @ClassName: ZKFreeMarkers 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKFreeMarkers {

    @SuppressWarnings("deprecation")
    public static String renderString(String templateString, Map<String, Object> model) throws Exception {
        try {
            StringWriter result = new StringWriter();
            Template t = new Template("name", new StringReader(templateString), new Configuration());

            BeansWrapper wrapper = new BeansWrapper(new Version(2, 3, 27));
            TemplateModel statics = wrapper.getStaticModels();
            model.put("statics", statics);

            t.process(model, result);
            return result.toString();
        }
        catch(Exception e) {
            throw e;
        }
    }

    public static String renderTemplate(Template template, Object model) throws Exception {
        try {
            StringWriter result = new StringWriter();
            template.process(model, result);
            return result.toString();
        }
        catch(Exception e) {
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
    public static Configuration buildConfiguration(String directory) throws IOException {

        Configuration cfg = new Configuration();
        Resource path = new DefaultResourceLoader().getResource(directory);
        cfg.setDirectoryForTemplateLoading(path.getFile());
        return cfg;
    }

}
