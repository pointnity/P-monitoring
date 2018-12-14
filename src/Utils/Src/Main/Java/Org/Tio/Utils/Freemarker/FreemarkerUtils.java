Package  org . tio . utils . freemarker ;

Import  java.io.File ;
Import  java.io.FileOutputStream ;
Import  java.io.IOException ;
Import  java.io.OutputStreamWriter ;
Import  java.io.StringWriter ;
Import  java.io.Writer ;
Import  java.nio.channels.FileLock ;
Import  java.util.HashMap ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

Import  freemarker.core.ParseException ;
Import  freemarker.template.Configuration ;
Import  freemarker.template.MalformedTemplateNameException ;
Import  freemarker.template.Template ;
Import  freemarker.template.TemplateException ;
Import  freemarker.template.TemplateNotFoundException ;

/**
 * This code is taken without prejudice to the open source protocol: https://gitee.com/sanluan/PublicCMS-preview/blob/master/publiccms-parent/publiccms-common/src/main/java/com/publiccms/common /tools/FreeMarkerUtils.java 
 *  
