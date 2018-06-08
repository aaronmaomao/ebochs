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

import com.mwos.ebochs.resource.OSConfig.Source;

public class OSConfigFactory {
	private static Map<IProject, OSConfig> _map = new HashMap<>();

	private OSConfigFactory() {

	}

	public static OSConfig getConfig(IProject project) throws ParserConfigurationException, SAXException, IOException {
		OSConfig config = _map.get(project);
		if (config == null) {
			if (new File(project.getLocationURI().getPath() + "\\OS.xml").exists()) {
				config = parse(project.getFile("OS.xml"));
				if(config!=null)
					_map.put(project, config);
					
			}
		}
		return config;
	}

	private static OSConfig parse(IFile osxml) throws ParserConfigurationException, SAXException, IOException {
		OSConfig config = new OSConfig();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		Document document = dbBuilder.parse(osxml.getLocationURI().getPath());
		//Document document = dbBuilder.parse("D:\\OS\\ebochs\\com.mwos.ebochs\\src\\com\\mwos\\ebochs\\resource\\project\\OSTemp.xml");
		
		Node mapper = document.getFirstChild();
		parse(mapper, config);

		return config;
	}

	private static void parse(Node node, OSConfig osConfig) {
		if (node.getNodeName().equalsIgnoreCase("os")) {
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attr = node.getAttributes().item(i);
				if (attr.getNodeName().equalsIgnoreCase("name")) {
					osConfig.setName(attr.getNodeValue());
				} else if (attr.getNodeName().equalsIgnoreCase("bits")) {
					osConfig.setBits(attr.getNodeValue());
				} else if (attr.getNodeName().equalsIgnoreCase("version")) {
					osConfig.setVersion(attr.getNodeValue());
				}

			}
		} else if (node.getNodeName().equalsIgnoreCase("platform")) {
			Platform platform = new Platform();
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attr = node.getAttributes().item(i);
				if (attr.getNodeName().equalsIgnoreCase("arch")) {
					platform.setArch(attr.getNodeValue());
				}
			}

			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node child = node.getChildNodes().item(i);
				if (child.getNodeName().equalsIgnoreCase("cpu")) {
					for (int j = 0; j < child.getChildNodes().getLength(); j++) {
						Node _child = child.getChildNodes().item(j);
						if (_child.getNodeName().equalsIgnoreCase("#text")) {
							platform.addCpu(_child.getNodeValue());
						}
					}
				}
			}
			osConfig.setPlatform(platform);
		} else if (node.getNodeName().equalsIgnoreCase("image")) {
			Image image = new Image();
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attr = node.getAttributes().item(i);
				if (attr.getNodeName().equalsIgnoreCase("name")) {
					image.setName(attr.getNodeValue());
				} else if (attr.getNodeName().equalsIgnoreCase("size")) {
					image.setSize(attr.getNodeValue());
				} else if (attr.getNodeName().equalsIgnoreCase("driver")) {
					image.setDriver(attr.getNodeValue());
				} else if (attr.getNodeName().equalsIgnoreCase("format")) {
					image.setFormat(attr.getNodeValue());
				}
			}

			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node child = node.getChildNodes().item(i);
				if (child.getNodeName().equalsIgnoreCase("mbr")) {
					for (int j = 0; j < child.getChildNodes().getLength(); j++) {
						Node _child = child.getChildNodes().item(j);
						if (_child.getNodeName().equalsIgnoreCase("#text")) {
							image.setBootloader(_child.getNodeValue());
						}
					}
				}
			}
			osConfig.addImage(image);
		} else if (node.getNodeName().equalsIgnoreCase("source")) {
			Source source = osConfig.new Source();

			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attr = node.getAttributes().item(i);
				if (attr.getNodeName().equalsIgnoreCase("type")) {
					source.setType(attr.getNodeValue());
				} else if (attr.getNodeName().equalsIgnoreCase("name")) {
					source.setName(attr.getNodeValue());
				}
			}

			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node child = node.getChildNodes().item(i);

				if (child.getNodeName().equalsIgnoreCase("code")) {
					Code c = new Code();
					for (int j = 0; j < child.getAttributes().getLength(); j++) {
						Node attr = child.getAttributes().item(j);
						if (attr.getNodeName().equalsIgnoreCase("type")) {
							c.setType(attr.getNodeValue());
						}
					}

					for (int j = 0; j < child.getChildNodes().getLength(); j++) {
						Node _child = child.getChildNodes().item(j);
						if (_child.getNodeName().equalsIgnoreCase("#text")) {
							c.setName(_child.getNodeValue());
						}
					}
					source.addCode(c);
				}
			}

			osConfig.addSource(source);
		}

		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			parse(node.getChildNodes().item(i), osConfig);
		}
	}

//	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//		parse(null);
//	}
}
