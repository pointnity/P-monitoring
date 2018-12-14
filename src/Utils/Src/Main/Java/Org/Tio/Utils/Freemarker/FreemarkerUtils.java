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
 * @author tanyaowu 
 *  
 */
Public  class  FreemarkerUtils  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( FreemarkerUtils . class );
	
	Public  static  final  String  DEFAULT_CHARSET  =  "utf-8" ;

	/**
	 * 
	 * @author tanyaowu
	 */
	Public  FreemarkerUtils ()  {
	}

	 /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @throws IOException
     * @throws TemplateException
     */
   Public  static  void  generateFileByFile ( String  templateFilePath ,  String  destFilePath ,  Configuration  configuration ,
            Object  model )  throws  IOException ,  TemplateException  {
        generateFileByFile ( templateFilePath ,  destFilePath ,  configuration ,  model ,  true ,  false );
    }

    /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @param override
     * @throws IOException
     * @throws TemplateException
     */
    Public  static  void  generateFileByFile ( String  templateFilePath ,  String  destFilePath ,  Configuration  configuration ,
            Object  model ,  boolean  override )  throws  IOException ,  TemplateException  {
        generateFileByFile ( templateFilePath ,  destFilePath ,  configuration ,  model ,  override ,  false );
    }

    /**
     * @param templateFilePath
     * @param destFilePath
     * @param configuration
     * @param model
     * @param override
     * @param append
     * @throws ParseException
     * @throws MalformedTemplateNameException
     * @throws IOException
     * @throws TemplateException
     */
    Public  static  void  generateFileByFile ( String  templateFilePath ,  String  destFilePath ,  Configuration  configuration ,
            Object  model ,  boolean  override ,  boolean  append )
            throws  MalformedTemplateNameException ,  a ParseException ,  IOException ,  TemplateException  {
        Template  t  =  configuration . getTemplate ( templateFilePath );
        File  destFile  =  new  File ( destFilePath );
        If  ( override  ||  append  ||  ! destFile . exists ())  {
            File  parent  =  destFile . getParentFile ();
            If  ( null  !=  parent )  {
                Parent . mkdirs ();
            }
            Try  ( FileOutputStream  outputStream  =  new  FileOutputStream ( destFile ,  append );
                    FileLock  fileLock  =  outputStream . getChannel (). tryLock ();)  {
                Writer  out  =  new  OutputStreamWriter ( outputStream ,  DEFAULT_CHARSET );
                T . Process ( Model ,  OUT );
            }
            Log . info ( destFilePath  +  " saved!" );
        }  else  {
            Log . error ( destFilePath  +  " already exists!" );
        }
    }

    /**
     * @param template
     * @param configuration
     * @return render result
     * @throws TemplateException
     * @throws IOException
     */
    Public  static  String  generateStringByFile ( String  template ,  Configuration  configuration )
            throws  IOException ,  TemplateException  {
        Return  generateStringByFile ( template ,  configuration ,  new  HashMap < String ,  Object >());
    }

    /**
     * @param template
     * @param configuration
     * @param model
     * @return render result
     * @throws IOException
