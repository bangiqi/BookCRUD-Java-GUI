/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookcrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ramdani-pc
 */
public class BookUiDemo extends javax.swing.JFrame {
    
    
    String[] columnNames = {
        "ID", 
        "Judul", 
        "ISBN", 
        "Penerbit", 
        "Tgl Penerbit",
        "Kategori"
    };
    
    Koneksi con ;
    
    private String _txtIdBook;
    private String _txtTitleBook;
    private String _txtISBN;
    private String _txtPenerbitBook;
    private String _txtTglTerbitBook;
    private String _txtKategoriBook;
    
    int hitung;
    int getLastID;
    String getLastIdString;
    
    /**
     * Creates new form BookUiDemo
     */
    public BookUiDemo() {
        initComponents();
        
        
        con = new Koneksi();
        con.getKoneksi();
        
        setEnableText(false);
        setEnableButton(false);
        readBook();
        
    }
    
    public void readBook(){
        
        
	DefaultTableModel model = new DefaultTableModel();
	model.setColumnIdentifiers(columnNames);
        
        tblBookDetail.setModel(model);
        
        try {    
			
			String sql = "SELECT * FROM book_detail ORDER BY id DESC";
			
			Statement statement = con.cc.createStatement();
			ResultSet result = statement.executeQuery(sql);
                        
                        //result.last();
                        //hitung = result.getRow();
                        //result.beforeFirst();
                        
                        //System.out.println("hitung"+hitung);
                       
			
			int count = 0;
                        
                        //looping data			
			while (result.next()){
                            
				String id = result.getString(1);
				String judul = result.getString("judul");
				String isbn = result.getString("isbn");
				String penerbit = result.getString("penerbit");
                                String tglPenerbit = result.getString("release_date");
				String kategori = result.getString("kategori");
				
				//String output = "User #%d: %s - %s - %s - %s";
                                
                                model.addRow(new Object[]{
                                    id, 
                                    judul, 
                                    isbn ,
                                    penerbit, 
                                    tglPenerbit,
                                    kategori
                                });
                                
				//System.out.println(String.format(output, ++count, name, pass, fullname, email));
			}
                        
                        // untuk mendapatkan last ID
                        result.afterLast();
                        while (result.previous()) {
                          getLastIdString = result.getString("id");       
                        }
                        
                        hitung = Integer.parseInt(getLastIdString);
                        
                        System.out.println("get last id : "+getLastIdString);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
       
       
    }
    
    public void deleteBook(){
        try {
			
		String sql = "DELETE FROM book_detail WHERE id=?";
			
                PreparedStatement statement = con.cc.prepareStatement(sql);
                statement.setString(1, txtIdBook.getText());
                
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, 
                    "Apakah anda yakin akan menghapus data dengan id buku = "
                            +txtIdBook.getText() +"?",
                    "Warning",
                    dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(this, 
                            "Data berhasil dihapus");
                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Detail buku berhasil di hapus!");
                        readBook();
                    }
                
                }else{
                    JOptionPane.showMessageDialog(this, "Data gagal dihapus");
                }
                
                
                            
                txtIdBook.requestFocus();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
                        
		}
    }
    
    private void saveBook(){
        
        _txtIdBook          = this.txtIdBook.getText();
        _txtTitleBook       = this.txtTitleBook.getText();
        _txtISBN            = this.txtISBN.getText();
        _txtPenerbitBook    = (String) this.cmbPenerbit.getSelectedItem();
        _txtTglTerbitBook   = this.txtTglTerbit.getText();
        _txtKategoriBook    = (String) this.cmbKategori.getSelectedItem();
        
        
        try {
            
            String sql = "INSERT INTO book_detail values (?,?,?,?,?,?)";
            PreparedStatement p = con.cc.prepareStatement(sql);
            p.setString(1, _txtIdBook);
            p.setString(2, _txtTitleBook);
            p.setString(3, _txtISBN);
            p.setString(4, _txtPenerbitBook);
            p.setString(5, _txtTglTerbitBook);
            p.setString(6, _txtKategoriBook);
            
            int rowsInserted = p.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Data Buku berhasil ditambahkan!");
                System.out.println("Data Buku berhasil ditambahkan!");
            }

            readBook();         
            
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Error :"+e);
        }
        
    }
    
    private void updateBook(){
        
        _txtIdBook          = this.txtIdBook.getText();
        getLastID           = Integer.parseInt(_txtIdBook);
        _txtTitleBook       = this.txtTitleBook.getText();
        _txtISBN            = this.txtISBN.getText();
        _txtPenerbitBook    = (String) this.cmbPenerbit.getSelectedItem();
        _txtTglTerbitBook   = this.txtTglTerbit.getText();
        _txtKategoriBook    = (String) this.cmbKategori.getSelectedItem();        
        
        try {
            
            String sql = "UPDATE book_detail SET "
                    + "judul=?,"
                    + "isbn=?,"
                    + "penerbit=?,"
                    + "release_date=?,"
                    + "kategori=?"
                    + "WHERE id=?";
            
            PreparedStatement p = con.cc.prepareStatement(sql);
			
            p.setString(1, _txtTitleBook);
            p.setString(2, _txtISBN);
            p.setString(3, _txtPenerbitBook);
            p.setString(4, _txtTglTerbitBook);
            p.setString(5, _txtKategoriBook);
            p.setInt(6, getLastID);
            System.out.println("anu"+p.toString());
            
            int rowsUpdated = p.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Data Buku berhasil diperbaharui!");
                System.out.println("Data Buku berhasil diperbaharui!");
            }

            readBook();           
            
            
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Error :"+e);
            
        }
        
    }
    
    private void setTabel() throws ParseException{       
        int row = tblBookDetail.getSelectedRow();
        txtIdBook.setText((String)tblBookDetail.getValueAt(row, 0));
        txtTitleBook.setText((String)tblBookDetail.getValueAt(row, 1));
        txtISBN.setText((String)tblBookDetail.getValueAt(row, 2));
        cmbPenerbit.setSelectedItem((String)tblBookDetail.getValueAt(row, 3));
        txtTglTerbit.setText((String)tblBookDetail.getValueAt(row, 4));
        cmbKategori.setSelectedItem((String)tblBookDetail.getValueAt(row, 5));      
    }
    
    private void clearText(){
        
        txtTitleBook.setText("");
        txtISBN.setText("");       
        txtTglTerbit.setText("");
        
    }
    
    private void setEnableText(boolean status){
        txtIdBook.setEnabled(status);
        txtTitleBook.setEnabled(status);
        txtISBN.setEnabled(status);       
        txtTglTerbit.setEnabled(status);        
    }
    private void setEnableButton(boolean status){
        btnDeleteBook.setEnabled(status);
        btnSaveBook.setEnabled(status);
        btnUpdateBook.setEnabled(status);       
    }
    
    private void setEnableButtonSecond(boolean status){
        btnDeleteBook.setEnabled(status);
        btnUpdateBook.setEnabled(status);      
    }
    
    private void addNewBook(){
        
        //System.out.println("hitung2"+hitung);
        //String getId = txtIdBook.getText();
        
        //int getIdInteger = Integer.parseInt(getId);
        
        setEnableText(true);
        setEnableButton(true);
        
        int plusOne = hitung+1;
        String getIdLast = String.valueOf(plusOne);        
        txtIdBook.setText(getIdLast);
        
        setEnableButtonSecond(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblBookDetail = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIdBook = new javax.swing.JTextField();
        txtTitleBook = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCreateBook = new javax.swing.JButton();
        btnSaveBook = new javax.swing.JButton();
        btnDeleteBook = new javax.swing.JButton();
        btnUpdateBook = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTglTerbit = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbKategori = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        cmbPenerbit = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form Book Library");

        tblBookDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBookDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBookDetailMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblBookDetailMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblBookDetail);

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        jLabel1.setText("Book Library");

        jLabel2.setText("ID");

        jLabel3.setText(":");

        jLabel4.setText(":");

        jLabel5.setText("JUDUL");

        btnCreateBook.setText("New Book");
        btnCreateBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateBookActionPerformed(evt);
            }
        });

        btnSaveBook.setText("Save Book");
        btnSaveBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveBookMouseClicked(evt);
            }
        });
        btnSaveBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveBookActionPerformed(evt);
            }
        });

        btnDeleteBook.setText("Delete Book");
        btnDeleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBookActionPerformed(evt);
            }
        });

        btnUpdateBook.setText("Update Book");
        btnUpdateBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBookActionPerformed(evt);
            }
        });

        jLabel6.setText("ISBN");

        jLabel7.setText(":");

        jLabel8.setText("PENERBIT");

        jLabel9.setText(":");

        jLabel10.setText("TGL TERBIT");

        jLabel11.setText(":");

        jLabel12.setText("KATEGORI");

        jLabel13.setText(":");

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "WEB PROGRAMMING", "JARINGAN", "TEKNIK ELEKTRO", "BASIS DATA", "RPL", "FRAMEWORK" }));

        jLabel14.setText("*Format : yyyy-dd-mm");

        cmbPenerbit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "INFORMATIKA", "ELEX KOMPUTINDO", "ANDI PUBLISHER" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(18, 18, 18)
                                    .addComponent(cmbPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtIdBook, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTitleBook, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(312, 312, 312)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(311, 311, 311))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel14)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTglTerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCreateBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveBook, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDeleteBook, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateBook, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIdBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTitleBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(cmbPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel10))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(txtTglTerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(0, 32, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCreateBook)
                            .addComponent(btnDeleteBook))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSaveBook)
                            .addComponent(btnUpdateBook))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveBookActionPerformed
        // TODO add your handling code here:
        
        saveBook();
        setEnableButtonSecond(true);
    }//GEN-LAST:event_btnSaveBookActionPerformed

    private void btnDeleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBookActionPerformed
        // TODO add your handling code here:
        deleteBook();
        
    }//GEN-LAST:event_btnDeleteBookActionPerformed

    private void btnUpdateBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBookActionPerformed
        // TODO add your handling code here:
        updateBook();
    }//GEN-LAST:event_btnUpdateBookActionPerformed

    private void tblBookDetailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBookDetailMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBookDetailMouseEntered

    private void tblBookDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBookDetailMouseClicked
        // TODO add your handling code here:
        
        try {
            // TODO add your handling code here:
            setTabel();
            setEnableText(true);
            setEnableButtonSecond(true);
        } catch (ParseException ex) {
            Logger.getLogger(BookUiDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tblBookDetailMouseClicked

    private void btnCreateBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateBookActionPerformed
        // TODO add your handling code here:
        clearText();
        addNewBook();
    }//GEN-LAST:event_btnCreateBookActionPerformed

    private void btnSaveBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveBookMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveBookMouseClicked

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
            java.util.logging.Logger.getLogger(BookUiDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookUiDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookUiDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookUiDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookUiDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateBook;
    private javax.swing.JButton btnDeleteBook;
    private javax.swing.JButton btnSaveBook;
    private javax.swing.JButton btnUpdateBook;
    private javax.swing.JComboBox cmbKategori;
    private javax.swing.JComboBox cmbPenerbit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBookDetail;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtIdBook;
    private javax.swing.JTextField txtTglTerbit;
    private javax.swing.JTextField txtTitleBook;
    // End of variables declaration//GEN-END:variables
}
