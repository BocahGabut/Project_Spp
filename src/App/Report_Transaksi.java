/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Koneksi.db_connection;
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


/**
 *
 * @author Randsoft
 */
public class Report_Transaksi extends javax.swing.JPanel {
private DefaultTableModel model;
String queryasc,combobox,combobox2,scholl;
    /**
     * Creates new form Report_Transaksi
     */
    public Report_Transaksi() {
        initComponents();
        
        tampil();
    }

    public void tampil()
    {
        model = new DefaultTableModel();
        jTable1.setModel(model);
        model.addColumn("Nomer Transaksi");
        model.addColumn("Nis Siswa");
        model.addColumn("Nama Siswa");
        model.addColumn("Kelas");
        model.addColumn("Bulan");
        model.addColumn("Bayar");
        model.addColumn("Tgl Bayar");
        
        getdata();
    }
    
    public void getdata()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try
        {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM transaksi";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next())
            {
                Object[] obj = new Object[7];
                obj[0] = res.getString("no_trans");
                obj[1] = res.getString("nis");
                obj[2] = res.getString("nama");
                obj[3] = res.getString("kelas");
                obj[4] = res.getString("bulan");
                obj[5] = res.getString("bayar");
                obj[6] = res.getString("tgl_bayar");
                
                model.addRow(obj);
            }
            
        }
        catch(SQLException err)
        {JOptionPane.showMessageDialog(null, err.getMessage());}
    }
    
    void combo(JComboBox combo1)
    {
        int pilih = combo1.getSelectedIndex();
        switch(pilih)
        {
            case 1:
                combobox = "01";
                break;
            case 2:
                combobox = "02";
                break;
            case 3:
                combobox = "03";
                break;
            case 4:
                combobox = "04";
                break;
            case 5:
                combobox = "05";
                break;
            case 6:
                combobox = "06";
                break;
            case 7:
                combobox = "07";
                break;
            case 8:
                combobox = "08";
                break;
            case 9:
                combobox = "09";
                break;
            case 10:
                combobox = "10";
                break;
            case 11:
                combobox = "11";
                break;
            case 12:
                combobox = "12";
                break;
        }
    }
    
    void combo2(JComboBox combo2)
    {
        int pilih = combo2.getSelectedIndex();
        switch(pilih)
        {
            case 1:
                combobox2 = "01";
                break;
            case 2:
                combobox2 = "02";
                break;
            case 3:
                combobox2 = "03";
                break;
            case 4:
                combobox2 = "04";
                break;
            case 5:
                combobox2 = "05";
                break;
            case 6:
                combobox2 = "06";
                break;
            case 7:
                combobox2 = "07";
                break;
            case 8:
                combobox2 = "08";
                break;
            case 9:
                combobox2 = "09";
                break;
            case 10:
                combobox2 = "10";
                break;
            case 11:
                combobox2 = "11";
                break;
            case 12:
                combobox2 = "12";
                break;
        }
    }
    
    
    public void Between()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        combo(jComboBox1);
        combo2(jComboBox2);
        try
        {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM transaksi where bulan between '"+combobox+"' and '"+combobox2+"' ";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next())
            {
                Object[] obj = new Object[7];
                obj[0] = res.getString("no_trans");
                obj[1] = res.getString("nis");
                obj[2] = res.getString("nama");
                obj[3] = res.getString("kelas");
                obj[4] = res.getString("bulan");
                obj[5] = res.getString("bayar");
                obj[6] = res.getString("tgl_bayar");
                
                model.addRow(obj);
            }
            
        }
        catch(SQLException err)
        {JOptionPane.showMessageDialog(null, err.getMessage());}
    }
    
    public void SortAsc()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        if(jComboBox1.getSelectedItem() != ("-- Silahkan Pilih --") && jComboBox2.getSelectedItem() != ("--Silahkan Pilih --"))
        {
            queryasc = "where bulan between '"+combobox+"' and '"+combobox2+"' order by nama asc";
        }
        else
        {
            queryasc = "order by nama asc";
        }
        
        try
        {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM transaksi" + " " + queryasc;
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next())
            {
                Object[] obj = new Object[7];
                obj[0] = res.getString("no_trans");
                obj[1] = res.getString("nis");
                obj[2] = res.getString("nama");
                obj[3] = res.getString("kelas");
                obj[4] = res.getString("bulan");
                obj[5] = res.getString("bayar");
                obj[6] = res.getString("tgl_bayar");
                
                model.addRow(obj);
            }
            
        }
        catch(SQLException err)
        {JOptionPane.showMessageDialog(null, err.getMessage());}
    }
    
    
    private void ExportTablexls()
    {
        
        try
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");  
            FileOutputStream fileOut;
            // Hasil Export
            int userSelection = fileChooser.showSaveDialog(this);
            
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Sheet 0");
            
            // Nama Field
            Row row1 = worksheet.createRow((short)0);
            row1.createCell(0).setCellValue("No Transaksi");
            row1.createCell(1).setCellValue("Nis");
            row1.createCell(2).setCellValue("Nama");
            row1.createCell(3).setCellValue("Kelas");
            row1.createCell(4).setCellValue("Bulan");
            row1.createCell(5).setCellValue("Bayar");
            row1.createCell(6).setCellValue("Tgl Bayar");
            
            Row row2 ;
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM transaksi";
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
            int a = res.getRow();
            row2 = worksheet.createRow((short)a);
            // Sesuaikan dengan Jumlah Field
            row2.createCell(0).setCellValue(res.getString(1));
            row2.createCell(1).setCellValue(res.getString(2));
            row2.createCell(2).setCellValue(res.getString(3));
            row2.createCell(3).setCellValue(res.getString(4));
            row2.createCell(4).setCellValue(res.getString(5));
            row2.createCell(5).setCellValue(res.getString(6));
            row2.createCell(6).setCellValue(res.getString(7));
            
        }
            
            if (userSelection == JFileChooser.APPROVE_OPTION)
            {
                File fileToSave = fileChooser.getSelectedFile();
                fileOut = new FileOutputStream(fileToSave.getAbsolutePath() + ".xls");
                
                workbook.write(fileOut);
                fileOut.flush();
                fileOut.close();
                res.close();
                JOptionPane.showMessageDialog(null, "Export Success......");
            }
            
        }catch(SQLException ex){
        System.out.println(ex);
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Export Gagal.......");
        }
    }
    
    private void ExportTabledocx()
    {
        Show_Setting();
        
        try
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");  
            FileOutputStream fileOut;
            // Hasil Export
            int userSelection = fileChooser.showSaveDialog(this);
            
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
	    
              XWPFRun paragraphOne= paragraph.createRun();
	     // paragraphOneRunThree.setStrike(true);
	      paragraphOne.setFontSize(36);
	      paragraphOne.setSubscript(VerticalAlign.BASELINE);
	      paragraphOne.setText("Laporan Pembayaran Spp");
             
               XWPFRun paragraphTwo = paragraph.createRun();
	     // paragraphOneRunThree.setStrike(true);
	      paragraphTwo.setFontSize(30);
	      paragraphTwo.setSubscript(VerticalAlign.BASELINE);
              paragraphTwo.addBreak(BreakType.TEXT_WRAPPING);
	      paragraphTwo.setText("" + scholl);
             
             XWPFTable table = document.createTable();
             
              XWPFTableRow tableRowOne = table.getRow(0);
	      tableRowOne.getCell(0).setText("No Transaksi");
	      tableRowOne.addNewTableCell().setText("Nis");
	      tableRowOne.addNewTableCell().setText("Nama");
              tableRowOne.addNewTableCell().setText("Kelas");
	      tableRowOne.addNewTableCell().setText("Bulan");
              tableRowOne.addNewTableCell().setText("Bayar");
              tableRowOne.addNewTableCell().setText("Tgl Bayar");
              
              Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM transaksi";
            ResultSet res = stat.executeQuery(sql);
            while(res.next())
            {
                XWPFTableRow tableRowTwo = table.createRow();
                tableRowTwo.getCell(0).setText(res.getString(1));
                tableRowTwo.getCell(1).setText(res.getString(2));
                tableRowTwo.getCell(2).setText(res.getString(3));
                tableRowTwo.getCell(3).setText(res.getString(4));
                tableRowTwo.getCell(4).setText(res.getString(5));
                tableRowTwo.getCell(5).setText(res.getString(6));
                tableRowTwo.getCell(6).setText(res.getString(7));
            }
            if (userSelection == JFileChooser.APPROVE_OPTION)
            {
                File fileToSave = fileChooser.getSelectedFile();
                fileOut = new FileOutputStream(fileToSave.getAbsolutePath() + ".docx");
                
                document.write(fileOut);
                fileOut.flush();
                fileOut.close();
                res.close();
                JOptionPane.showMessageDialog(null, "Export Success......");
            }
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Export Gagal......");
        }
    }
    
    public void SortDesc()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        if(jComboBox1.getSelectedItem() != ("-- Silahkan Pilih --") && jComboBox2.getSelectedItem() != ("--Silahkan Pilih --"))
        {
            queryasc = "where bulan between '"+combobox+"' and '"+combobox2+"' order by nama desc";
        }
        else
        {
            queryasc = "order by nama desc";
        }
        
        try
        {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM transaksi" + " " + queryasc;
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next())
            {
                Object[] obj = new Object[7];
                obj[0] = res.getString("no_trans");
                obj[1] = res.getString("nis");
                obj[2] = res.getString("nama");
                obj[3] = res.getString("kelas");
                obj[4] = res.getString("bulan");
                obj[5] = res.getString("bayar");
                obj[6] = res.getString("tgl_bayar");
                
                model.addRow(obj);
            }
            
        }
        catch(SQLException err)
        {JOptionPane.showMessageDialog(null, err.getMessage());}
    }
    
    private void Show_Setting()
     {
         Properties p = new Properties();
         
    try 
    {
        InputStream is = new FileInputStream("src/Config/App Config.properties");
        p.load(is);
        
        scholl = ("" + p.getProperty("nama-sekolah"));
    } 
    catch (FileNotFoundException ex) {
        Logger.getLogger(Pengaturan_Menu.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Pengaturan_Menu.class.getName()).log(Level.SEVERE, null, ex);
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

        sort = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        save = new javax.swing.JPopupMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        print = new javax.swing.JPopupMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Alphabetical_Sorting_20px_1.png"))); // NOI18N
        jMenuItem1.setText("Asceding");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        sort.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Alphabetical_Sorting_2_20px_2.png"))); // NOI18N
        jMenuItem2.setText("Desceding");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        sort.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Microsoft_Excel_20px_1.png"))); // NOI18N
        jMenuItem3.setText("Excel");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        save.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Word_20px_1.png"))); // NOI18N
        jMenuItem4.setText("Word");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        save.add(jMenuItem4);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Select_All_20px.png"))); // NOI18N
        jMenuItem6.setText("Print Semua Data");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        print.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Calendar_20px.png"))); // NOI18N
        jMenuItem7.setText("Print Satu Bulan");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        print.add(jMenuItem7);

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setText("Laporan Dari Bulan");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Silahkan Pilih --", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 153));
        jLabel2.setText("--");

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Silahkan Pilih --", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));

        jButton1.setBackground(new java.awt.Color(0, 0, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Generate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Sorting_30px.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Sort_Down_15px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel4MousePressed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Database_Export_30px_1.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Sort_Down_15px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel5MousePressed(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Print_30px.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Sort_Down_15px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Sync_30px.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel11MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        sort.show(this,615,43);
    }//GEN-LAST:event_jPanel2MousePressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        SortAsc();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        SortDesc();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        ExportTablexls();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ExportTabledocx();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jPanel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MousePressed
       save.show(this,685,43);
    }//GEN-LAST:event_jPanel4MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Between();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MousePressed
        getdata();
        jComboBox1.setSelectedItem("-- Silahkan Pilih --");
        jComboBox2.setSelectedItem("-- Silahkan Pilih --");
    }//GEN-LAST:event_jLabel11MousePressed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed

        Show_Setting();
        
        try {
        String path="src/Laporan/report.jasper";
        Map parameter = new HashMap();
        parameter.put("parameter1", scholl);
        JasperPrint print = JasperFillManager.fillReport(path,parameter, db_connection.getKoneksi());
        JasperViewer.viewReport(print, false);
        } catch (Exception ex) 
        {
        JOptionPane.showMessageDialog(null,"Dokumen Tidak Ada"+ex);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        new Bulan().setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jPanel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MousePressed
       print.show(this,765,43);
    }//GEN-LAST:event_jPanel5MousePressed

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
        setColor(jPanel2);
    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        setColor(jPanel4);
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        setColor(jPanel5);
    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseExited
        resetColor(jPanel2);
    }//GEN-LAST:event_jPanel2MouseExited

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        resetColor(jPanel4);
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        resetColor(jPanel5);
    }//GEN-LAST:event_jPanel5MouseExited

    void setColor(JPanel panel)
    {
        panel.setBackground(new Color (230,230,230) );
    }
    
    void resetColor(JPanel panel)
    {
        panel.setBackground(new Color (255,255,255) );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPopupMenu print;
    private javax.swing.JPopupMenu save;
    private javax.swing.JPopupMenu sort;
    // End of variables declaration//GEN-END:variables
}
