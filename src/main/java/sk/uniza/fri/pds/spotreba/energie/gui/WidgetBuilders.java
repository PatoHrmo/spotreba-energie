/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.gui;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.metawidget.inspector.InspectionResultConstants.NAME;
import static org.metawidget.inspector.InspectionResultConstants.TYPE;
import org.metawidget.swing.SwingMetawidget;
import org.metawidget.widgetbuilder.iface.WidgetBuilder;

public class WidgetBuilders {

    public static class DateWidgetBuilder implements WidgetBuilder<Component, SwingMetawidget> {

        @Override
        public Component buildWidget(String elementName, Map<String, String> attributes, SwingMetawidget metawidget) {
            if (Date.class.getName().equalsIgnoreCase(attributes.get(TYPE))) {
                return new JDateChooser();
            }
            return null;
        }

    }

    public static class ImageWidgetBuilder implements WidgetBuilder<Component, SwingMetawidget> {

        @Override
        public Component buildWidget(String elementName, Map<String, String> attributes, SwingMetawidget metawidget) {
            if (BufferedImage.class.getName().equalsIgnoreCase(attributes.get(TYPE))) {
                try {
                    Object obj = metawidget.getToInspect();
                    String name = attributes.get(NAME);
                    Method method = obj.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                    BufferedImage bi = (BufferedImage) method.invoke(obj);
                    ImagePanel panel = new ImagePanel();
                    if (bi != null) {
                        panel.setImage(bi);
                    }
                    return panel;
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(WidgetBuilders.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        }

    }
}
