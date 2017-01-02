/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.gui;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.util.Date;
import java.util.Map;
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
}
