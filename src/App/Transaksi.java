/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Koneksi.db_connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.sun.glass.events.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Randsoft
 */
public class Transaksi extends javax.swing.JPanel {
private DefaultTableModel model;
String tgl,scholl,jam,bulan,conv;
    /**
     * Creates new form Transaksi
     */
    public Transaksi() {
        initComponents();
        
        tampil();
        no_trans();
        setJam();
    }

    public void tampil()
    {
        model = new DefaultTableModel();
        jTable1.setModel(model);
        model.addColumn("Nis Siswa");
        model.addColumn("Nama Siswa");
        model.addColumn("Kelas");
        
        getdata();
    }
    
    public void getdata()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try
        {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT nis,nama,kelas,jurusan FROM siswa order by kelas asc";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next())
            {
                Object[] obj = new Object[4];
                obj[0] = res.getString("nis");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("kelas");
                
                model.addRow(obj);
            }
            
        }
        catch(SQLException err)
        {JOptionPane.showMessageDialog(null, err.getMessage());}
    }
    
    private void Find_Data()
    {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try
        {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql= "SELECT * FROM siswa where nis like '%"+jTextField1.getText()+"%' or"
                    + " nama like '%"+jTextField1.getText()+"%' or kelas like '%"+jTextField1.getText()+"%' order by kelas asc";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next())
            {
                Object[] obj = new Object[4];
                obj[0] = res.getString("nis");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("kelas");
                
                model.addRow(obj);
            }
            
        }
        catch(SQLException err)
        {JOptionPane.showMessageDialog(null, err.getMessage());}
    }

    private void New()
    {
        no_trans();
        nis.setText(null);
        nama.setText(null);
        kelas.setText(null);
        bulan1.setSelectedItem("-- Silahkan Pilih --");
        bayar.setText(null);
        jButton2.setEnabled(true);
        jButton3.setEnabled(false);
        jTable1.setEnabled(true);
        jButton4.setEnabled(false);
    }
    
    private void no_trans()
    {
        try {
            Statement stat = (Statement) db_connection.getKoneksi().createStatement();
            String sql="select * from transaksi order by no_trans desc";
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                String nofak = res.getString("no_trans").substring(3);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               no_trans.setText("SBP" + Nol + AN);
            } else {
               no_trans.setText("SBP0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
    }

    private void save_transaksi()
    {
        combo(bulan1);
        
        try 
            {
                Statement stat = (Statement) db_connection.getKoneksi().createStatement();
                String sql = "Insert Into transaksi values ('"+no_trans.getText()+"','"+nis.getText()+"','"+nama.getText()+"','"+kelas.getText()+"',"
                        + "'"+bulan+"','"+bayar.getText()+"','"+tgl+"')";
                PreparedStatement p = (PreparedStatement) db_connection.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                JOptionPane.showMessageDialog(null, "Save Succes......");
                jButton2.setEnabled(false);
                jTable1.setEnabled(false);
                jButton4.setEnabled(true);
                jButton3.setEnabled(true);
            } 
                catch (SQLException ex) 
            {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
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
    
    private void Convert(JTextField field)
    {
        double harga = Integer.parseInt(field.getText());

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        conv = kursIndonesia.format(harga);
    }
    
    public void setJam(){
        ActionListener taskPerformer = new ActionListener() {

        public void actionPerformed(ActionEvent evt) {
        String nol_jam = "", nol_menit = "",nol_detik = "";

        java.util.Date dateTime = new java.util.Date();
        int nilai_jam = dateTime.getHours();
        int nilai_menit = dateTime.getMinutes();
        int nilai_detik = dateTime.getSeconds();

        if(nilai_jam <= 9) nol_jam= "0";
        if(nilai_menit <= 9) nol_menit= "0";
        if(nilai_detik <= 9) nol_detik= "0";

        String waktu = nol_jam + Integer.toString(nilai_jam);
        String menit = nol_menit + Integer.toString(nilai_menit);
        String detik = nol_detik + Integer.toString(nilai_detik);
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd MMMM YYYY");
        
        jam = (waktu+":"+menit);
        tgl = (s.format(date));

        }
        
    };
        new Timer(1000, taskPerformer).start();
    }
    
    void combo(JComboBox combo1)
    {
        int pilih = combo1.getSelectedIndex();
        switch(pilih)
        {
            case 1:
                bulan = "01";
                break;
            case 2:
                bulan = "02";
                break;
            case 3:
                bulan = "03";
                break;
            case 4:
                bulan = "04";
                break;
            case 5:
                bulan = "05";
                break;
            case 6:
                bulan = "06";
                break;
            case 7:
                bulan = "07";
                break;
            case 8:
                bulan = "08";
                break;
            case 9:
                bulan = "09";
                break;
            case 10:
                bulan = "10";
                break;
            case 11:
                bulan = "11";
                break;
            case 12:
                bulan = "12";
                break;
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

        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        no_trans = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nis = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        bayar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        bulan1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        kelas = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Cari Data Siswa");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField1CaretUpdate(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("No.Trans");

        no_trans.setEditable(false);
        no_trans.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Nis");

        nis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Nama");

        nama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255), 2));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setText("Bayar");

        bayar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton2.setBackground(new java.awt.Color(0, 0, 153));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Save_25px.png"))); // NOI18N
        jButton2.setText("SAVE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 153));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_New_File_25px.png"))); // NOI18N
        jButton3.setText("New");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 0, 153));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_Print_25px.png"))); // NOI18N
        jButton4.setText("Print");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Bulan");

        bulan1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bulan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Silahkan Pilih --", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        bulan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bulan1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5))
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bulan1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bayar))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(bulan1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("Kelas");

        kelas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nis, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                                    .addComponent(no_trans)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(65, 65, 65))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(67, 67, 67)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nama, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                                    .addComponent(kelas))))
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(no_trans, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(nis, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int i = jTable1.getSelectedRow();
        
        if(i==-1)
        {
            JOptionPane.showMessageDialog(null, "Silahkan Pilih Data");
            return;
        }
        else
        {
            nis.setText(""+model.getValueAt(i, 0));
            nama.setText(""+model.getValueAt(i, 1));
            kelas.setText(""+model.getValueAt(i, 2));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField1CaretUpdate
        for(int i =0; i < jTextField1.getText().length()+1;i++)
        {
            if(i >= 1)
            {
                Find_Data();
            }
            else
            {
                tampil();
            }

        }
    }//GEN-LAST:event_jTextField1CaretUpdate

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        save_transaksi();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        Show_Setting();
        Convert(bayar);
        
        Print_Preview prin = new Print_Preview();
        
        Print_Preview.jTextArea1.setText("\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + " ================================================\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "              Bukti Pembayaran Spp              \n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "            " + scholl + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + " ================================================\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "    Nis   : "+nis.getText()+"\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "    Nama  : "+nama.getText()+"\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "    Kelas : "+kelas.getText()+"\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "    Bulan : "+bulan1.getSelectedItem()+"\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "    Bayar : " + conv);
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "    " + tgl + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "    " + jam + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "				         TTD\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "\n");
        Print_Preview.jTextArea1.setText(Print_Preview.jTextArea1.getText() + "				      Bendahara\n");
        
        prin.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        New();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void bulan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bulan1ActionPerformed

    }//GEN-LAST:event_bulan1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bayar;
    private javax.swing.JComboBox<String> bulan1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField kelas;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField nis;
    private javax.swing.JTextField no_trans;
    // End of variables declaration//GEN-END:variables
}
