/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.uniza.fri.pds.spotreba.energie.gui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.metawidget.swing.SwingMetawidget;
import sk.uniza.fri.pds.spotreba.energie.domain.KrokSpotreby;
import sk.uniza.fri.pds.spotreba.energie.domain.StatistikaServisov;
import sk.uniza.fri.pds.spotreba.energie.domain.StatistikaTypuKategorie;
import sk.uniza.fri.pds.spotreba.energie.domain.ZvysenieSpotreby;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.DbClass;
import sk.uniza.fri.pds.spotreba.energie.gui.utils.MetawidgetUtils;
import sk.uniza.fri.pds.spotreba.energie.service.SeHistoriaService;
import sk.uniza.fri.pds.spotreba.energie.service.SeServisService;
import sk.uniza.fri.pds.spotreba.energie.service.util.IncreasedSpendingStatisticParams;
import sk.uniza.fri.pds.spotreba.energie.service.util.SpendingStatisticsParameters;
import sk.uniza.fri.pds.spotreba.energie.service.util.StatistikaTypuKategorieParams;

/**
 *
 * @author Coder
 */
public class MainGui extends javax.swing.JFrame {

    /**
     * Creates new form MainGui
     */
    public MainGui() {
        initComponents();
        setLocationRelativeTo(null);
        for (DbClass cl : DbClass.values()) {
            tabs.add(new DbTab(cl.clazz, cl.service));
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

        tabs = new javax.swing.JTabbedPane();
        menuBar = new javax.swing.JMenuBar();
        spendingMenu = new javax.swing.JMenu();
        showSpendingStatistics = new javax.swing.JMenuItem();
        increasedSpending = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        typeAndCatMenuItem = new javax.swing.JMenuItem();
        servisMenu = new javax.swing.JMenu();
        servisStatsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabs.setMinimumSize(new java.awt.Dimension(600, 900));
        tabs.setPreferredSize(new java.awt.Dimension(600, 900));
        getContentPane().add(tabs, java.awt.BorderLayout.CENTER);

        spendingMenu.setText("Spotreba");

        showSpendingStatistics.setText("Vývoj spotreby");
        showSpendingStatistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showSpendingStatisticsActionPerformed(evt);
            }
        });
        spendingMenu.add(showSpendingStatistics);

        increasedSpending.setText("Zvýšená spotreba o aspoň 20%");
        increasedSpending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                increasedSpendingActionPerformed(evt);
            }
        });
        spendingMenu.add(increasedSpending);

        jMenuItem1.setText("Zvýšená spotreba o aspoň 50% v poslednom roku");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        spendingMenu.add(jMenuItem1);

        jMenuItem2.setText("Spotreba v poslednom roku menšia ako 10% priemeru");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        spendingMenu.add(jMenuItem2);

        typeAndCatMenuItem.setText("Štatistika podľa typu a kategórie");
        typeAndCatMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeAndCatMenuItemActionPerformed(evt);
            }
        });
        spendingMenu.add(typeAndCatMenuItem);

        menuBar.add(spendingMenu);

        servisMenu.setText("Servisy");

        servisStatsMenuItem.setText("Štatistika");
        servisStatsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servisStatsMenuItemActionPerformed(evt);
            }
        });
        servisMenu.add(servisStatsMenuItem);

        menuBar.add(servisMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showSpendingStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showSpendingStatisticsActionPerformed
        final SpendingStatisticsParameters params = new SpendingStatisticsParameters();
        int option = showUniversalInputDialog(params, "Vývoj spotreby");
        if (option == JOptionPane.OK_OPTION) {
            List<KrokSpotreby> spendingStatistics = SeHistoriaService.getInstance().getSpendingStatistics(params);
            final TimeSeries series = new TimeSeries("");

            final String title = "Vývoj spotreby";
            for (KrokSpotreby krok : spendingStatistics) {
                series.add(new Month(krok.getDatumOd()), krok.getSpotreba());
            }
            final IntervalXYDataset dataset = (IntervalXYDataset) new TimeSeriesCollection(series);
            JFreeChart chart = ChartFactory.createXYBarChart(
                    title, // title
                    "", // x-axis label
                    true, // date axis?
                    "", // y-axis label
                    dataset, // data
                    PlotOrientation.VERTICAL, // orientation
                    false, // create legend?
                    true, // generate tooltips?
                    false // generate URLs?
            );

            // Set date axis style
            DateAxis axis = (DateAxis) ((XYPlot) chart.getPlot()).getDomainAxis();
            axis.setDateFormatOverride(new SimpleDateFormat("yyyy"));
            DateFormat formatter = new SimpleDateFormat("yyyy");
            DateTickUnit unit = new DateTickUnit(DateTickUnitType.YEAR, 1, formatter);
            axis.setTickUnit(unit);

            JOptionPane.showMessageDialog(null, new ChartPanel(chart));
        }
    }//GEN-LAST:event_showSpendingStatisticsActionPerformed

    private void increasedSpendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_increasedSpendingActionPerformed
        final IncreasedSpendingStatisticParams params = new IncreasedSpendingStatisticParams();
        int option = showUniversalInputDialog(params, "Zvýšená spotreba");
        if (option == JOptionPane.OK_OPTION) {
            List<ZvysenieSpotreby> spendingStatistics = SeHistoriaService.getInstance().getIncreasedSpendingStatistics(params, 1.2);

            JScrollPane jScrollPane = new JScrollPane();
            final BeanTableModel beanTableModel = new BeanTableModel(ZvysenieSpotreby.class, spendingStatistics);
            beanTableModel.sortColumnNames();
            JTable jTable = new JTable(beanTableModel);
            jScrollPane.getViewport().add(jTable);
            JOptionPane.showMessageDialog(null, jScrollPane);
        }
    }//GEN-LAST:event_increasedSpendingActionPerformed

    private void servisStatsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servisStatsMenuItemActionPerformed
        List<StatistikaServisov> spendingStatistics = SeServisService.getInstance().getServiceStatistics();

        JScrollPane jScrollPane = new JScrollPane();
        Dimension dimension = new Dimension(1200, 400);
        jScrollPane.setSize(dimension);
        jScrollPane.setPreferredSize(dimension);
        final BeanTableModel beanTableModel = new BeanTableModel(StatistikaServisov.class, spendingStatistics);
        beanTableModel.sortColumnNames();
        JTable jTable = new JTable(beanTableModel);
        jScrollPane.getViewport().add(jTable);
        JOptionPane.showMessageDialog(null, jScrollPane);
    }//GEN-LAST:event_servisStatsMenuItemActionPerformed

    private void typeAndCatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeAndCatMenuItemActionPerformed
        final StatistikaTypuKategorieParams params = new StatistikaTypuKategorieParams();
        int option = showUniversalInputDialog(params, "Štatistika podľa typu a kategórie");
        if (option == JOptionPane.OK_OPTION) {
            List<StatistikaTypuKategorie> spendingStatistics = SeHistoriaService.getInstance().getTypeAndCategoryStatistics(params);

            JScrollPane jScrollPane = new JScrollPane();
            Dimension dimension = new Dimension(1200, 400);
            jScrollPane.setSize(dimension);
            jScrollPane.setPreferredSize(dimension);
            final BeanTableModel beanTableModel = new BeanTableModel(StatistikaTypuKategorie.class, spendingStatistics);
            beanTableModel.sortColumnNames();
            JTable jTable = new JTable(beanTableModel);
            jScrollPane.getViewport().add(jTable);
            JOptionPane.showMessageDialog(null, jScrollPane);
        }
    }//GEN-LAST:event_typeAndCatMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        showLastYearChange(1.5);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        showLastYearChange(0.9);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void showLastYearChange(double factor) throws HeadlessException {
        final IncreasedSpendingStatisticParams params = new IncreasedSpendingStatisticParams();
        params.setDatumDo(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        params.setDatumOd(result);

        List<ZvysenieSpotreby> spendingStatistics = SeHistoriaService.getInstance().getIncreasedSpendingStatistics(params, factor);

        JScrollPane jScrollPane = new JScrollPane();
        Dimension dimension = new Dimension(1200, 400);
        jScrollPane.setSize(dimension);
        jScrollPane.setPreferredSize(dimension);
        final BeanTableModel beanTableModel = new BeanTableModel(ZvysenieSpotreby.class, spendingStatistics);
        beanTableModel.sortColumnNames();
        JTable jTable = new JTable(beanTableModel);
        jScrollPane.getViewport().add(jTable);
        JOptionPane.showMessageDialog(null, jScrollPane);
    }

    private int showUniversalInputDialog(final Object params, String title) throws HeadlessException {
        SwingMetawidget metawidget = new SwingMetawidget();
        MetawidgetUtils.setCommonSettings(metawidget);
        metawidget.setToInspect(params);
        Object[] message = {
            metawidget
        };
        int option = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
        return option;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem increasedSpending;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu servisMenu;
    private javax.swing.JMenuItem servisStatsMenuItem;
    private javax.swing.JMenuItem showSpendingStatistics;
    private javax.swing.JMenu spendingMenu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JMenuItem typeAndCatMenuItem;
    // End of variables declaration//GEN-END:variables
}
