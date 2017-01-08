/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.gui.utils;

import com.toedter.calendar.JDateChooser;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.AutoBinding;
import org.metawidget.swing.SwingMetawidget;
import org.metawidget.swing.widgetbuilder.OverriddenWidgetBuilder;
import org.metawidget.swing.widgetbuilder.ReadOnlyWidgetBuilder;
import org.metawidget.swing.widgetbuilder.SwingWidgetBuilder;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessor;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessorConfig;
import org.metawidget.widgetbuilder.composite.CompositeWidgetBuilder;
import org.metawidget.widgetbuilder.composite.CompositeWidgetBuilderConfig;
import org.metawidget.widgetprocessor.iface.WidgetProcessor;
import sk.uniza.fri.pds.spotreba.energie.gui.DbTab;

import sk.uniza.fri.pds.spotreba.energie.gui.FilePanel;

import sk.uniza.fri.pds.spotreba.energie.gui.ImagePanel;

public class MetawidgetUtils {

    public static void setCommonSettings(SwingMetawidget metawidget) {
        metawidget.setWidgetBuilder(new CompositeWidgetBuilder<>(new CompositeWidgetBuilderConfig()
                .setWidgetBuilders(

                        new OverriddenWidgetBuilder(),
                        new ReadOnlyWidgetBuilder(),
                        new WidgetBuilders.DateWidgetBuilder(),
                        new WidgetBuilders.ImageWidgetBuilder(),
                        new WidgetBuilders.EnumWidgetBuilder(),
                        new WidgetBuilders.FileWidgetBuilder(),
                        new SwingWidgetBuilder()

                )));
        metawidget.addWidgetProcessor(new BeansBindingProcessor(
                new BeansBindingProcessorConfig()
                        .setUpdateStrategy(AutoBinding.UpdateStrategy.READ_WRITE)));

        metawidget.addWidgetProcessor(new WidgetProcessor<JComponent, SwingMetawidget>() {
            @Override
            public JComponent processWidget(JComponent w, String string, Map<String, String> map, SwingMetawidget m) {
                if (w instanceof ImagePanel) {
                    ((ImagePanel) w).addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent e) {
                            if ("image".equals(e.getPropertyName())) {
                                try {
                                    Object o;
                                    o = m.getToInspect();
                                    final String name = map.get("name");
                                    Method method = o.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), BufferedImage.class);
                                    method.invoke(o, e.getNewValue());
                                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                    Logger.getLogger(DbTab.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });

                } else if (w instanceof FilePanel) {
                    ((FilePanel) w).addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent e) {
                            if ("file".equals(e.getPropertyName())) {
                                try {
                                    Object o;
                                    o = m.getToInspect();
                                    final String name = map.get("name");
                                    Method method = o.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), File.class);
                                    method.invoke(o, e.getNewValue());
                                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                    Logger.getLogger(DbTab.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });

                } else if (w instanceof JDateChooser) {
                    ((JDateChooser) w).getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent e) {
                            if ("date".equals(e.getPropertyName())) {
                                try {
                                    Object o;
                                    o = m.getToInspect();
                                    final String name = map.get("name");
                                    Method method = o.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), Date.class);
                                    method.invoke(o, e.getNewValue());
                                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                    Logger.getLogger(DbTab.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });
                }
                return w;
            }
        });
    }
}
