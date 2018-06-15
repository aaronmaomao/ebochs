package com.mwos.ebochs.resource.config;

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
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.mwos.ebochs.resource.config.entity.CodePart;
import com.mwos.ebochs.resource.config.entity.CodePart.Code;
import com.mwos.ebochs.resource.config.entity.Image;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.resource.config.entity.Platform;
import com.mwos.ebochs.resource.config.entity.ImgFile;

public class OSConfigFactory {
	private static Map<IProject, OSConfig> _map = new HashMap<>();

	private OSConfigFactory() {

	}

	public static OSConfig getConfig(IProject project) throws ParserConfigurationException, SAXException, IOException {
		OSConfig config = _map.get(project);
		if (config == null) {
			return getBuildConfig(project);
		}

		return null;
	}

	public static OSConfig getBuildConfig(IProject project) throws ParserConfigurationException, SAXException, IOException {
		if (new File(project.getLocationURI().getPath() + "\\OS.xml").exists()) {
			OSConfig config = parse(project.getFile("OS.xml"));
			if (config != null) {
				// config.checkNeedBuild(_map.get(project));
				// _map.put(project, config);
				return config;
			}

		}
		return null;
	}

	private static OSConfig parse(IFile osxml) throws ParserConfigurationException, SAXException, IOException {
		OSConfig config = new OSConfig(osxml.getProject());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		Document document = dbBuilder.parse(osxml.getLocationURI().getPath());
		// Document document =
		// dbBuilder.parse("D:\\OS\\ebochs\\com.mwos.ebochs\\src\\com\\mwos\\ebochs\\resource\\project\\OSTemp.xml");

		Node mapper = document.getFirstChild();
		parse(mapper, config);

		return config;
	}

	private static void parse(Node node, OSConfig osConfig) {
		if (node.getNodeName().equalsIgnoreCase("os")) {
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attr = node.getAttributes().item(i);
				if (attr.getNodeName().equalsIgnoreCase("name")) {
					osConfig.setName(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("bits")) {
					osConfig.setBits(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("version")) {
					osConfig.setVersion(attr.getNodeValue().trim());
				}

			}
		} else if (node.getNodeName().equalsIgnoreCase("platform")) {
			Platform platform = new Platform();
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attr = node.getAttributes().item(i);
				if (attr.getNodeName().equalsIgnoreCase("arch")) {
					platform.setArch(attr.getNodeValue().trim());
				}
			}

			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node child = node.getChildNodes().item(i);
				if (child.getNodeName().equalsIgnoreCase("cpu")) {
					for (int j = 0; j < child.getChildNodes().getLength(); j++) {
						Node _child = child.getChildNodes().item(j);
						if (_child.getNodeName().equalsIgnoreCase("#text")) {
							platform.addCpu(_child.getNodeValue().trim());
						}
					}
				}
			}
			osConfig.setPlatform(platform);
		} else if (node.getNodeName().equalsIgnoreCase("image")) {
			Image image = new Image(osConfig);
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attr = node.getAttributes().item(i);
				if (attr.getNodeName().equalsIgnoreCase("name")) {
					image.setName(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("size")) {
					image.setSize(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("device")) {
					image.setDevice(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("format")) {
					image.setFormat(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("mbr")) {
					image.setMbr(attr.getNodeValue().trim());
				}
			}

			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node child = node.getChildNodes().item(i);
				if (child.getNodeName().equalsIgnoreCase("file")) {
					image.addImgFile(getImgFile(child, image));
				}
			}
			osConfig.addImage(image);
		} else if (node.getNodeName().equalsIgnoreCase("codepart")) {
			CodePart codePart = new CodePart(osConfig);
			for (int i = 0; i < node.getAttributes().getLength(); i++) {
				Node attr = node.getAttributes().item(i);
				if (attr.getNodeName().equalsIgnoreCase("type")) {
					codePart.setType(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("obj")) {
					codePart.setObj(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("link")) {
					codePart.setLink(attr.getNodeValue().trim());
				} else if (attr.getNodeName().equalsIgnoreCase("src")) {
					codePart.setSrc(attr.getNodeValue().trim());
				}
			}

			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node child = node.getChildNodes().item(i);

				if (child.getNodeName().equalsIgnoreCase("code")) {
					Code code = codePart.new Code();
					for (int j = 0; j < child.getAttributes().getLength(); j++) {
						Node attr = child.getAttributes().item(j);
						if (attr.getNodeName().equalsIgnoreCase("src")) {
							code.setSrc(attr.getNodeValue().trim());
						}
					}

					// for (int j = 0; j < child.getChildNodes().getLength(); j++) {
					// Node _child = child.getChildNodes().item(j);
					// if (_child.getNodeName().equalsIgnoreCase("#text")) {
					// code.setSrc(_child.getNodeValue());
					// }
					// }
					codePart.addCode(code);
				}
			}

			osConfig.addCodePart(codePart);
		}

		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			parse(node.getChildNodes().item(i), osConfig);
		}
	}

	private static ImgFile getImgFile(Node file, Image img) {
		ImgFile imgFile = new ImgFile(img.getConfig());
		for (int j = 0; j < file.getAttributes().getLength(); j++) {
			Node _attr = file.getAttributes().item(j);
			if (_attr.getNodeName().equalsIgnoreCase("name")) {
				imgFile.setName(_attr.getNodeValue().trim());
			} else if (_attr.getNodeName().equalsIgnoreCase("location")) {
				imgFile.setLocation(_attr.getNodeValue().trim());
			}
		}

		for (int j = 0; j < file.getChildNodes().getLength(); j++) {
			Node sub = file.getChildNodes().item(j);
			if (sub.getNodeName().equalsIgnoreCase("sub")) {
				ImgFile subFile = getImgFile(sub, img);
				imgFile.addSubFile(subFile);
			}
		}
		return imgFile;
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		parse(null);
	}
}
