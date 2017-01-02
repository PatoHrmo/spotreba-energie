/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.gui;

import com.toedter.calendar.JDateChooser;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.metawidget.swing.SwingMetawidget;
import org.metawidget.swing.widgetbuilder.OverriddenWidgetBuilder;
import org.metawidget.swing.widgetbuilder.ReadOnlyWidgetBuilder;
import org.metawidget.swing.widgetbuilder.SwingWidgetBuilder;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessor;
import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessorConfig;
import org.metawidget.widgetbuilder.composite.CompositeWidgetBuilder;
import org.metawidget.widgetbuilder.composite.CompositeWidgetBuilderConfig;
import org.metawidget.widgetprocessor.iface.WidgetProcessor;
import sk.uniza.fri.pds.spotreba.energie.service.SeService;

/**
 *
 * @author Coder
 */
public class DbTab extends javax.swing.JPanel {

    private BeanTableModel tableModel;
    private Class clazz;

    private SeService service;

    /**
     * Creates new form DbTab
     */
    public DbTab() {
        initComponents();
    }

    public DbTab(Class clazz, SeService service) {
        this.clazz = clazz;
        this.service = service;
        tableModel = new BeanTableModel(clazz);
        this.tableModel.sortColumnNames();
        setName(clazz.getSimpleName());

        initComponents();

        try {
            metawidget.setWidgetBuilder(new CompositeWidgetBuilder<>(new CompositeWidgetBuilderConfig()
                    .setWidgetBuilders(
                            new OverriddenWidgetBuilder(), new ReadOnlyWidgetBuilder(),
                            new WidgetBuilders.DateWidgetBuilder(), new WidgetBuilders.ImageWidgetBuilder(), new SwingWidgetBuilder()
                    )));
            metawidget.addWidgetProcessor(new BeansBindingProcessor(
                    new BeansBindingProcessorConfig()
                            .setUpdateStrategy(UpdateStrategy.READ_WRITE)));

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

            metawidget.setToInspect(clazz.newInstance());

            jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    if (jTable.getSelectedRow() > -1) {
                        Object row = ((BeanTableModel) jTable.getModel()).getRow(jTable.getSelectedRow());
                        metawidget.setToInspect(row);
                    }
                }
            });
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DbTab.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        table = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        bot = new javax.swing.JPanel();
        metawidget = new org.metawidget.swing.SwingMetawidget();
        buttons = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jTable.setModel(tableModel);
        table.setViewportView(jTable);

        add(table, java.awt.BorderLayout.CENTER);

        bot.setLayout(new java.awt.BorderLayout());
        bot.add(metawidget, java.awt.BorderLayout.PAGE_START);

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        buttons.add(newButton);

        loadButton.setText("Load table");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });
        buttons.add(loadButton);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        buttons.add(saveButton);

        bot.add(buttons, java.awt.BorderLayout.LINE_END);

        add(bot, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (service != null) {
            if (jTable.getSelectedRow() > -1) {
                update();
            } else {
                create();
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        if (service != null) {
            load();
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    private void load() {
        new SwingWorker<List, RuntimeException>() {
            @Override
            protected List doInBackground() throws Exception {
                try {
                    return service.findAll();
                } catch (RuntimeException e) {
                    publish(e);
                    return null;
                }
            }

            @Override
            protected void done() {
                try {
                    if (get() != null) {
                        tableModel = new BeanTableModel(clazz, get());
                        tableModel.sortColumnNames();
                        jTable.setModel(tableModel);
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(DbTab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected void process(List<RuntimeException> chunks) {
                showException("Chyba", chunks.get(0));
            }

        }.execute();
    }

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        try {
            metawidget.setToInspect(clazz.newInstance());
            jTable.clearSelection();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DbTab.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_newButtonActionPerformed

    private void create() {
        new SwingWorker<Boolean, RuntimeException>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    service.create(metawidget.getToInspect());
                    return true;
                } catch (RuntimeException e) {
                    publish(e);
                }
                return false;
            }

            @Override
            protected void done() {
                // a co teraz?
            }

            @Override
            protected void process(List<RuntimeException> chunks) {
                showException("Chyba", chunks.get(0));
            }
        }.execute();
    }

    private void update() {
        new SwingWorker<Boolean, RuntimeException>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    service.update(metawidget.getToInspect());
                    return true;
                } catch (RuntimeException e) {
                    publish(e);
                }
                return false;
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        tableModel.fireTableRowsUpdated(jTable.getSelectedRow(), jTable.getSelectedRow());
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(DbTab.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            protected void process(List<RuntimeException> chunks) {
                showException("Chyba", chunks.get(0));
            }
        }.execute();
    }

    private void showException(String message, Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), message, JOptionPane.ERROR_MESSAGE);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bot;
    private javax.swing.JPanel buttons;
    private javax.swing.JTable jTable;
    private javax.swing.JButton loadButton;
    private org.metawidget.swing.SwingMetawidget metawidget;
    private javax.swing.JButton newButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JScrollPane table;
    // End of variables declaration//GEN-END:variables
}
