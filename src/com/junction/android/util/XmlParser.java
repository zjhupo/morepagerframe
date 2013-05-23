package com.junction.android.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * @Description ����xml����
 * @author ¥�ܷ�
 * @time 2012-06-19
 * @modify author �޸���
 * @modify time �޸�ʱ��
 * @modify Description �޸���������
 */
public class XmlParser extends DefaultHandler{
	
	private String retcode = null;
    private String preTag = null;//�����Ǽ�¼����ʱ����һ���ڵ����� 
    private static String parseName = null ;
    /**
	 * @Description ��xml��ȡ��Ҫ������
	 * @param str
	 *            xml�ַ���
	 * @param myParseName
	 *            ��Ҫ������������         
	 * @return String ����ֵ
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

        preTag = localName;//�����ڽ����Ľڵ����Ƹ���preTag 
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
