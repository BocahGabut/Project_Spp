/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Koneksi.db_connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Randsoft
 */
public class Data_Siswa extends javax.swing.JPanel {
private DefaultTableModel model;
String dir;
    /**
     * Creates new form Data_Siswa
     */
    public Data_Siswa() {
        initComponents();
        
        tampil();
    }
 
    public void tampil()
    {
        model = new DefaultTableModel();
        jTable1.setModel(model);
        model.addColumn("Nis Siswa");
        model.addColumn("Nama Siswa");
        model.addColumn("Kelas");
        model.addColumn("Jurusan");
        model.addColumn("Tempat Lahir");
        model.addColumn("Tgl Lahir");
        model.addColumn("Jk");
        model.addColumn("Agama");
        model.addColumn("No Hp");
        model.addColumn("Alamat");
        
        getdata();
    }
    
    private void Delete()
    {
        int i = jTable1.getSelectedRow();
        
        if(i==-1)
        {
            return;
        }
        else
        {
            int pesan = JOptionPane.showConfirmDialog(null, "Anda Yakin Akan Menghapus Data " + model.getValueAt(i, 0),"Konfirmasi",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(pesan==JOptionPane.OK_OPTION)
            {
                try
                {
                Statement stat = (Statement) db_connection.getKoneksi().createStatement();
                String sql = "Delete from siswa where nis = '"+model.getValueAt(i, 0)+"'";
                PreparedStatement p = (PreparedStatement) db_connection.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                JOptionPane.showMessageDialog(null, "Delete Success.......");
                getdata();
                }
                catch(SQLException err)
                {JOptionPane.showMessageDialog(null, err.getMessage());}
            }
        }
    }
    
    private void ExportTable()
    {
        
        try
        {
            FileOutputStream fileOut;
            // Hasil Export
            fileOut = new FileOutputStream("" + jTextField1.getText() + "/" + jTextField2.getText() + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Sheet 0");
            
            // Nama Field
            Row row1 = worksheet.createRow((short)0);
            row1.createCell(0).setCellValue("Nis");
            row1.createCell(1).setCellValue("Nama");
            row1.createCell(2).setCellValue("Kelas");
            row1.createCell(3).setCellValue("Jurusan");
            row1.createCell(4).setCellValue("Tempat Lahir");
            row1.createCell(5).setCellValue("TGL Lahir");
            row1.createCell(6).setCellValue("JK");
            row1.createCell(7).setCellValue("Agama");
            row1.createCell(8).setCellValue("No Hp");
            row1.createCell(9).setCellValue("Alamat");
            
            Row row2 ;
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM siswa order by nis asc";
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
            row2.createCell(7).setCellValue(res.getString(8));
            row2.createCell(8).setCellValue(res.getString(9));
            row2.createCell(9).setCellValue(res.getString(10));
            
        }
        workbook.write(fileOut);
        fileOut.flush();
        fileOut.close();
        res.close();
        JOptionPane.showMessageDialog(null, "Export Success......");
        }catch(SQLException ex){
        System.out.println(ex);
        }
        catch(IOException ioe){
        System.out.println(ioe);
        }
    }
    
    public void getdata()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try
        {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM siswa order by nis asc";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next())
            {
                Object[] obj = new Object[10];
                obj[0] = res.getString("nis");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("kelas");
                obj[3] = res.getString("jurusan");
                obj[4] = res.getString("tmp_lahir");
                obj[5] = res.getString("tgl_lahir");
                obj[6] = res.getString("jk");
                obj[7] = res.getString("agama");
                obj[8] = res.getString("no_hp");
                obj[9] = res.getString("alamat");
                
                model.addRow(obj);
            }
            db_connection.getKoneksi().close();
        }
        catch(SQLException err)
        {JOptionPane.showMessageDialog(null, err.getMessage());}
    }
    
    public void caridata()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try
        {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM siswa where nama like '%"+jTextField4.getText()+"%' or nis like '%"+jTextField4.getText()+"%' order by nis asc";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next())
            {
                
                Object[] obj = new Object[10];
                obj[0] = res.getString("nis");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("kelas");
                obj[3] = res.getString("jurusan");
                obj[4] = res.getString("tmp_lahir");
                obj[5] = res.getString("tgl_lahir");
                obj[6] = res.getString("jk");
                obj[7] = res.getString("agama");
                obj[8] = res.getString("no_hp");
                obj[9] = res.getString("alamat");
                
                model.addRow(obj);
            }
            db_connection.getKoneksi().close();
        }
        catch(SQLException err)
        {JOptionPane.showMessageDialog(null, err.getMessage());}
    }
    
    private void Edit()
    {
        int i = jTable1.getSelectedRow();
        
        if(i==-1)
        {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Data");
            return;
        }
        else
        {
            try {
                Edit_Siswa update = new Edit_Siswa();
                Edit_Siswa.nis.setText(""+model.getValueAt(i, 0));
                Edit_Siswa.nama.setText(""+model.getValueAt(i, 1));
                Edit_Siswa.kelas.setText(""+model.getValueAt(i, 2));
                Edit_Siswa.jurusan.setSelectedItem(""+model.getValueAt(i, 3));
                Edit_Siswa.tmp.setText(""+model.getValueAt(i, 4));
                Edit_Siswa.agama.setSelectedItem(""+model.getValueAt(i, 7));
                Edit_Siswa.no_hp.setText(""+model.getValueAt(i, 8));
                Edit_Siswa.alamat.setText(""+model.getValueAt(i, 9));
                
                if(model.getValueAt(i, 6).equals("L"))
                {
                    Edit_Siswa.laki.setSelected(true);
                } 
                else if(model.getValueAt(i, 6).equals("P"))
                {
                    Edit_Siswa.perem.setSelected(true);
                }
                
                Date date = new SimpleDateFormat("MMM d, yyyy").parse((String)model.getValueAt(i, 5));
                Edit_Siswa.tgl_lahir.setDate(date);
                update.setVisible(true);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void ImportExcel()
    {
        try 
        {
            FileInputStream input = new FileInputStream("" + jTextField3.getText());
            POIFSFileSystem fs = new POIFSFileSystem( input );
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for(int i=1; i<=sheet.getLastRowNum(); i++){
                row = sheet.getRow(i);
                String nis = row.getCell(0).getStringCellValue();
                String nama = row.getCell(1).getStringCellValue();
                String kelas = row.getCell(2).getStringCellValue();
                String jurusan = row.getCell(3).getStringCellValue();
                String tmp_lahir = row.getCell(4).getStringCellValue();
                String tgl_lahir = row.getCell(5).getStringCellValue();
                String jk = row.getCell(6).getStringCellValue();
                String agama = row.getCell(7).getStringCellValue();
                String no_hp = row.getCell(8).getStringCellValue();
                String alamat = row.getCell(9).getStringCellValue();
                
                try 
                {
                    Statement stat = (Statement) db_connection.getKoneksi().createStatement();
                    String sql = "INSERT INTO siswa VALUES('"+nis+"','"+nama+"','"+kelas+"','"+jurusan+"','"+tmp_lahir+"',"
                        + "'"+tgl_lahir+"','"+jk+"','"+agama+"','"+no_hp+"','"+alamat+"')";
                    PreparedStatement p = (PreparedStatement) db_connection.getKoneksi().prepareStatement(sql);
                    p.executeUpdate();
                } 
                catch (SQLException ex) {
                    Logger.getLogger(Data_Siswa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            JOptionPane.showMessageDialog(null,"Import Success......");
        } catch (IOException e) {
            e.printStackTrace();
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

        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        export = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        Import = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();

        jPopupMenu2.setBackground(new java.awt.Color(255, 255, 255));

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Edit_Row_20px.png"))); // NOI18N
        jMenuItem1.setText("Edit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Delete_Row_20px.png"))); // NOI18N
        jMenuItem2.setText("Delete");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem2);

        export.setMinimumSize(new java.awt.Dimension(355, 221));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Location");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton5.setBackground(new java.awt.Color(0, 153, 0));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Browse");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Filename");

        jButton6.setBackground(new java.awt.Color(0, 0, 204));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Export");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout exportLayout = new javax.swing.GroupLayout(export.getContentPane());
        export.getContentPane().setLayout(exportLayout);
        exportLayout.setHorizontalGroup(
            exportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        exportLayout.setVerticalGroup(
            exportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Import.setMinimumSize(new java.awt.Dimension(355, 171));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Location");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton8.setBackground(new java.awt.Color(0, 153, 0));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Browse");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(0, 0, 204));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Import");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ImportLayout = new javax.swing.GroupLayout(Import.getContentPane());
        Import.getContentPane().setLayout(ImportLayout);
        ImportLayout.setHorizontalGroup(
            ImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ImportLayout.setVerticalGroup(
            ImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImportLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Add_35px_1.png"))); // NOI18N
        jButton1.setText("Tambah Data");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Refresh_35px.png"))); // NOI18N
        jButton2.setText("Refesh");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 0, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Export_35px.png"))); // NOI18N
        jButton3.setText("Import File (.xls)");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 0, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Download_35px.png"))); // NOI18N
        jButton4.setText("Eksport File (.xls)");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Cari Data");

        jTextField4.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField4CaretUpdate(evt);
            }
        });
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Add_Siswa().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Import.setVisible(true);
        Import.setLocationRelativeTo(null);
        jTextField3.setText(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        export.setVisible(true);
        export.setLocationRelativeTo(null);
        jTextField1.setText(null);
        jTextField2.setText(null);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tampil();
        jTextField4.setText(null);
        JOptionPane.showMessageDialog(null, "Refesh Success....");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Edit();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Delete();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        jPopupMenu2.show(this,evt.getX()-15,evt.getY()+80);
    }//GEN-LAST:event_jTable1MousePressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String choosertitle = null;
        
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        /**System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());**/
        jTextField1.setText("" + chooser.getSelectedFile());
        } else {
        System.out.println("No Selection ");
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        ExportTable();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        
        File file = jfc.getSelectedFile();
        dir = file.getAbsolutePath();
        
        jTextField3.setText(dir);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        ImportExcel();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField4CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField4CaretUpdate
            for(int i =0; i < jTextField4.getText().length()+1;i++)
        {
            if(i >= 1)
            {
                caridata();
            }
            else
            {
                tampil();
            }

        }
    }//GEN-LAST:event_jTextField4CaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Import;
    private javax.swing.JDialog export;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
