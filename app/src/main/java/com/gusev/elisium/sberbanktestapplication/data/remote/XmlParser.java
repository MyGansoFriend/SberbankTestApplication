package com.gusev.elisium.sberbanktestapplication.data.remote;

import com.gusev.elisium.sberbanktestapplication.data.model.BaseXmlModel;
import com.gusev.elisium.sberbanktestapplication.data.model.Channel;
import com.gusev.elisium.sberbanktestapplication.data.model.Hab;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class XmlParser {
    private static final String SEARCHING_ITEM = "item";

    static List<BaseXmlModel> parse(String xml) {
        List<BaseXmlModel> list = new ArrayList<>();

        DocumentBuilder documentBuilder;
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document document = documentBuilder.parse(is);

            Node root = document.getDocumentElement();

            NodeList items = root.getChildNodes().item(1).getChildNodes();

            Channel channel = new Channel();
            list.add(channel);
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);

                if (item.getNodeType() != Node.TEXT_NODE)
                    if (item.getNodeName().equals(SEARCHING_ITEM)) {
                        NodeList fields = item.getChildNodes();
                        list.add(getFields(fields));
                    } else {
                        setValueField(item, channel);
                    }
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static Hab getFields(NodeList fields) throws IllegalAccessException {
        Hab hab = new Hab();
        for (int j = 0; j < fields.getLength(); j++) {
            Node field = fields.item(j);

            if (field.getNodeType() != Node.TEXT_NODE) {

                if (field.hasChildNodes()) {
                    if(field.getNodeName().equals("category")){
                        hab.addCategory(field.getChildNodes().item(0).getNodeValue());
                    }
                    setValueField(field, hab);
                }

            }
        }
        hab.updateDescription();
        return hab;
    }

    private static void setValueField(Node field, BaseXmlModel model) throws IllegalAccessException {
        StringBuilder value = new StringBuilder();
        Field modelField = model.findField(field.getNodeName(), model);
        if (modelField != null) {
            for (int k = 0; k < field.getChildNodes().getLength(); k++) {
                value.append(field.getChildNodes()
                        .item(k).getNodeValue());
            }
            modelField.set(model, value.toString());
        }
    }
}
