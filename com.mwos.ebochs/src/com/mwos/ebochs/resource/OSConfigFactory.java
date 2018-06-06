package com.mwos.ebochs.resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OSConfigFactory {
	private static Map<IProject, OSConfig> _map = new HashMap<>();

	private OSConfigFactory() {

	}

	public static OSConfig getConfig(IProject project) throws ParserConfigurationException, SAXException, IOException {
		OSConfig config = _map.get(project);
		if (config == null) {
			if (new File(project.getLocationURI().toString() + "\\OS.xml").exists()) {
				parse(project.getFile("OS.xml"));
			}
		}
		return config;
	}

	private static OSConfig parse(IFile osxml) throws ParserConfigurationException, SAXException, IOException {
		OSConfig config = new OSConfig();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		Document document = dbBuilder.parse("D:\\OS\\ebochs\\com.mwos.ebochs\\src\\com\\mwos\\ebochs\\resource\\project\\OSTemp.xml");

		Node mapper = document.getFirstChild();
		parse(mapper, null);
//		NodeList nodes = mapper.getChildNodes();
//		for(int i=0;i<nodes.getLength();i++) {
//			Node node = nodes.item(i);
//			System.out.println(node.getAttributes());
//		}
//		for (int i = 0; i < mapper.getChildNodes().getLength(); i++) {
//			Node node = mapper.getChildNodes().item(i);
//			String s = node.getNodeName();
//			if (s.toLowerCase().equals("#comment")) {
//				System.out.println("这是注释内容: " + node.getTextContent());
//			} else if (s.toLowerCase().equals("#text")) {
//				System.out.println("呐，这是标签之外的文本: " + node.getNodeName());
//			} else if ("node".equals(s)) {
//				// 获取元素属性的值
//				String column = node.getAttributes().getNamedItem("column").getNodeValue();
//				String field = node.getAttributes().getNamedItem("property").getNodeValue();
//			} else {
//				// 其他的就不要了
//			}
//		}

		return config;
	}
	
	private static void parse(Node node,OSConfig osConfig) {
		System.out.println(node.getNodeName());
		NamedNodeMap map = node.getAttributes();
		if(map!=null)
		for(int i=0;i<map.getLength();i++) {
			System.out.println(map.item(i).getNodeName()+" "+map.item(i).getNodeValue());
		}
		
		NodeList nodes = node.getChildNodes();
		if(nodes!=null)
		for(int i=0;i<nodes.getLength();i++) {
			if(!nodes.item(i).getNodeName().equalsIgnoreCase("#comment")&&!nodes.item(i).getNodeName().equalsIgnoreCase("#comment"))
			parse(nodes.item(i),osConfig);
		}
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		parse(null);
	}
}
