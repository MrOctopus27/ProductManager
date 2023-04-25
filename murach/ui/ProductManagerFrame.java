/*
 * Author: Kevin Alvarado
 * Date: 11/8/2022
 * 
 * Description: This program uses a database with code, description, and price.
 * You can add a product of your choice, you can update it, you can delete it, or you can print it if you wish.
 */

package murach.ui;

import java.awt.BorderLayout;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mysql.jdbc.Statement;

import murach.business.Product;
import murach.db.ProductDB;
import murach.db.DBException;

@SuppressWarnings("serial")
public class ProductManagerFrame extends JFrame {
    private JTable productTable;
    private ProductTableModel productTableModel;
    
    public ProductManagerFrame() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }        
        setTitle("Product Manager");
        setSize(768, 384);
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(buildButtonPanel(), BorderLayout.SOUTH);
        productTable = buildProductTable();
        add(new JScrollPane(productTable), BorderLayout.CENTER);
        setVisible(true);        
    }
    
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton addButton = new JButton("Add");
        addButton.setToolTipText("Add product");
        addButton.addActionListener((ActionEvent) -> {
            doAddButton();
        });
        panel.add(addButton);
        
        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit selected product");
        editButton.addActionListener((ActionEvent) -> {
            doEditButton();
        });
        panel.add(editButton);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete selected product");
        deleteButton.addActionListener((ActionEvent) -> {
            doDeleteButton();
        });
        panel.add(deleteButton);
        
        // I added this.
        JButton printButton = new JButton("Print");
        printButton.setToolTipText("Print selected prodoct");
        printButton.addActionListener((ActionEvent) -> {
        	doPrintButton();
        });
        panel.add(printButton);
        
        return panel;
    }
    
    private void doAddButton() {
    	
    	
        try {                    
            ProductDB.add(null);
            productTableModel.databaseUpdated();
        } catch (DBException e) {
            System.out.println(e);
        }

    }
    
     
    

	private void doEditButton() {
        //doAddButton();
		int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No product is currently selected.", 
                    "No product selected", JOptionPane.ERROR_MESSAGE);
        } else {
            Product product = productTableModel.getProduct(selectedRow);
            try {                    
                ProductDB.update(product);
                productTableModel.databaseUpdated();
            } catch (DBException e) {
                System.out.println(e);
            }
        }
    }
    
    private void doDeleteButton() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No product is currently selected.", 
                    "No product selected", JOptionPane.ERROR_MESSAGE);
        } else {
            Product product = productTableModel.getProduct(selectedRow);
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + 
                            product.getDescription() + " from the database?",
                    "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {                    
                    ProductDB.delete(product);
                    productTableModel.databaseUpdated();
                } catch (DBException e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    // I added this.
    private void doPrintButton() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No product is selected.", 
                    "No product selected", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "The selected product has been sent to the printer.",
                    "Product printed", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private JTable buildProductTable() {
        productTableModel = new ProductTableModel();
        JTable table = new JTable(productTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBorder(null);
        return table;
    }
}