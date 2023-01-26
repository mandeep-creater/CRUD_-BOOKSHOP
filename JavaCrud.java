import java.awt.EventQueue;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table_1;

	public void Connect() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root", "");

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("Connection failed!");
			e.printStackTrace();

		}
	}

	public void table_load() {
		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table_1.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 619, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("BOOK SHOP");
		lblNewLabel.setBounds(217, 11, 264, 76);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(10, 95, 320, 208);
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 31, 107, 26);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(11, 96, 107, 26);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 158, 107, 26);
		panel.add(lblNewLabel_1_2);

		txtbname = new JTextField();
		txtbname.setBounds(138, 36, 157, 20);
		panel.add(txtbname);
		txtbname.setColumns(10);

		txtedition = new JTextField();
		txtedition.setBounds(138, 101, 157, 20);
		panel.add(txtedition);
		txtedition.setColumns(10);

		txtprice = new JTextField();
		txtprice.setBounds(138, 160, 157, 20);
		panel.add(txtprice);
		txtprice.setColumns(10);

		table = new JTable();
		table.setBounds(358, 340, 237, -226);
		frame.getContentPane().add(table);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBounds(10, 314, 89, 34);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bname, edition, price;

				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();

				try {

					pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
					// table_load();

					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}

				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnNewButton);

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(126, 314, 89, 34);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(225, 314, 89, 34);
		frame.getContentPane().add(btnClear);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(64, 403, 138, -24);
		frame.getContentPane().add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(20, 359, 306, 73);
		panel_2.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_1_1_1 = new JLabel("Book ID");
		lblNewLabel_1_1_1.setBounds(10, 30, 67, 17);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_2.add(lblNewLabel_1_1_1);

		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {

					String id = txtbid.getText();

					pst = con.prepareStatement("select name,edition,price from book where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();

					if (rs.next() == true) {

						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);

						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);

					} else {
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");

					}

				} catch (SQLException ex) {

				}
			}

		});
		txtbid.setColumns(10);
		txtbid.setBounds(87, 30, 147, 20);
		panel_2.add(txtbid);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bname, edition, price, bid;
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid = txtbid.getText();
				try {
					pst = con.prepareStatement("update book set name= ?,edition=?,price=? where id =?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Update!!!!!");
					table_load();

					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}

				catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnUpdate.setBounds(358, 376, 89, 34);
		frame.getContentPane().add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bid;
				bid = txtbid.getText();
				try {
					pst = con.prepareStatement("delete from book where id =?");
					pst.setString(1, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
					table_load();

					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}

				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(457, 376, 89, 34);
		frame.getContentPane().add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(340, 95, 253, 251);
		frame.getContentPane().add(scrollPane);

		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();

			}
		});
	}
}
