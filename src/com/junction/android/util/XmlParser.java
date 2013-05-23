package com.junction.android.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * @Description 解析xml数据
 * @author 楼周峰
 * @time 2012-06-19
 * @modify author 修改人
 * @modify time 修改时间
 * @modify Description 修改内容描述
 */
public class XmlParser extends DefaultHandler{
	
	private String retcode = null;
    private String preTag = null;//作用是记录解析时的上一个节点名称 
    private static String parseName = null ;
    /**
	 * @Description 从xml获取想要的数据
	 * @param str
	 *            xml字符串
	 * @param myParseName
	 *            需要解析的数据名         
	 * @return String 数据值
	 */
	public static String parse(String str,String myParseName) throws Exception {
		parseName = myParseName ;
		InputStream   inputStream   =   new   ByteArrayInputStream(str.getBytes());
		SAXParserFactory factory = SAXParserFactory.newInstance(); 
        SAXParser parser = factory.newSAXParser(); 
        XmlParser handler = new XmlParser(); 
        parser.parse(inputStream, handler); 
        return handler.getRetCode(); 
	}

    public String getRetCode(){ 
        return retcode; 
    } 
    
    @Override 
    public void startDocument() throws SAXException { 
    	retcode = "";
    } 
 
    @Override 
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException { 

        preTag = localName;//将正在解析的节点名称赋给preTag 
    } 
 
    @Override 
    public void endElement(String uri, String localName, String qName) 
            throws SAXException { 

        preTag = null;
    } 
 
    @Override 
    public void characters(char[] ch, int start, int length) throws SAXException { 
        if(preTag!=null){ 
            String content = new String(ch,start,length); 
            if(parseName.equals(preTag) ){ 
            	retcode = content ;
            }
        } 
    } 
}
