import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JCheckBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.event.WindowFocusListener;

public class OtelPanel extends JFrame {

	private JPanel contentPane;
	private JTextField jtxt_musteri_email;
	private JTextField jtxt_musteri_adres;
	private JTextField jtxt_oda_fiyat;
	private JTextField jtxt_oda_tip;
	private JTextField jtxt_oda_no;
	private JTextField jtxt_toplam;
	private JTable admin_tablo_rezervasyon;
	private JTable admin_tablo_oda;
	private JTable admin_tablo_musteri;
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OtelPanel frame = new OtelPanel();
					
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	VeritabaniFonksiyonlar fonk = new VeritabaniFonksiyonlar();

	private JTextField jtxt_musteri_ad;
	private JTable yemek_tablo;
	private JTextField jtxt_yemek;
	private JTextField jtxt_yemek_ucret;
	private JTextField gizli_oda_id;
	private JTextField gizli_musteri_id;
	private JTextField id_field;
	
	//rezervasyon listeleme fonksiyonu
	
public void rezervasyonListele(String otel_id) {
		try {
			
			String otelid = otel_id;
			
		
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM reservation WHERE hotel_id="+otelid;
			ResultSet rs = st.executeQuery(query);
			DefaultTableModel tm = (DefaultTableModel)admin_tablo_rezervasyon.getModel();
			tm.setRowCount(0);
		
			
			while (rs.next()) {
					
				    String o[] = {rs.getString("reservation_id"),rs.getString("customer_name"), rs.getString("customer_email"),
				    		rs.getString("customer_adress"),rs.getString("hotel_name"), rs.getString("date_in"),
				    		rs.getString("date_out"),rs.getString("room_no"),rs.getString("room_type"),rs.getString("room_price"),
				    				rs.getString("meal_selection"),rs.getString("meal_price"), rs.getString("total_price")};
				    
			
				    tm.addRow(o);
				    
			/*	    
			otelin id'sini oda tablosundaki hotel id k�sm�ndan al�yordum burada. Sonras�nda rezervasyon listesinden se�ip
			d�zenlemeye kalk�nca oda  tablosunda her hangi bir �ey se�ili olmad���nda otel id sini alamay�p hata veriyordu.
			onun yerine �imdi otel ad� k�sm�nda yazan id den al�yor. ��ler kar��m�yor. 

			if(otelid.equals(idSelected)){ 
					tm.addRow(o);
				    	
			} */
				   
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	//veritaban�ndan m��terileri al�p tabloya aktaran fonksiyon
	public void musteriListele(String otelid) {
		try {
			String otel_id = otelid;
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM customer WHERE hotel_id="+otel_id;
			ResultSet rs = st.executeQuery(query);
			DefaultTableModel tm = (DefaultTableModel)admin_tablo_musteri.getModel();
			tm.setRowCount(0);
		
		
			while (rs.next()) {
				   
				    String o[] = {rs.getString("customer_id".toString()), rs.getString("hotel_id"),
				    		rs.getString("customer_name"),rs.getString("customer_email"), rs.getString("customer_adress")};
			
				  
				    	tm.addRow(o);
				   
				   
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	
	
	// veritaban�ndan oda listesini al�p tabloya aktaran fonksiyon
	public void odaListele(String otel_id) {
		try {
			
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM room WHERE hotel_id="+otel_id;
			ResultSet rs = st.executeQuery(query);
			DefaultTableModel tm = (DefaultTableModel)admin_tablo_oda.getModel();
			tm.setRowCount(0);
		
			while (rs.next()) {
				   
				    String o[] = {rs.getString("room_id".toString()), 
				    		rs.getString("room_no"),rs.getString("room_type"), rs.getString("room_price")};
				    
				
				    	tm.addRow(o);
				    	
				    }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	//veritaban�ndan yemek listesini al�r.
	
	public void yemekListele(String otel_id) {
		try {

			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM meal WHERE hotel_id="+otel_id;
			ResultSet rs = st.executeQuery(query);
			DefaultTableModel tm = (DefaultTableModel)yemek_tablo.getModel();
			tm.setRowCount(0);
			
			while (rs.next()) {
				   
				
				    String o[] = {rs.getString("meal_id".toString()), rs.getString("meal_type"),
				    		rs.getString("meal_price")};
				    
				
				    	tm.addRow(o);
	
				   
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public OtelPanel() {
		setTitle("Personel - Ana Sayfa");
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				String otelid = id_field.getText();
				  odaListele(otelid);
					rezervasyonListele(otelid);
					musteriListele(otelid);
					yemekListele(otelid);
			}
			public void windowLostFocus(WindowEvent e) {
				
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		Image img = new ImageIcon(this.getClass().getResource("/Logo1.png")).getImage();
		this.setIconImage(img);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnOdaEkle = new JButton("Oda Ekle");
		btnOdaEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OtelOdaEkle oda = new OtelOdaEkle();
				oda.odaListele(id_field.getText());
				oda.setOtel_id(id_field.getText().toString());
				oda.setVisible(true);
			}
		});
		btnOdaEkle.setBounds(10, 0, 117, 21);
		panel.add(btnOdaEkle);
		
		JButton btnMusteriEkle = new JButton("M\u00FC\u015Fteri Ekle");
		btnMusteriEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OtelMusteriEkle musteri = new OtelMusteriEkle();
				musteri.setVisible(true);
				musteri.musteriListele(id_field.getText());
				musteri.setJtxt_otel_id(id_field.getText().toString());
			}
		});
		btnMusteriEkle.setBounds(126, 0, 117, 21);
		panel.add(btnMusteriEkle);
		
		JButton btnPersonelEkle = new JButton("Personel Ekle");
		btnPersonelEkle.setPreferredSize(new Dimension(117, 21));
		btnPersonelEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OtelPersonelEkle personel= new OtelPersonelEkle();
				personel.setVisible(true);
				personel.personelListele(id_field.getText());
				personel.setJtxt_otel_id(id_field.getText());
			}
		});
		btnPersonelEkle.setBounds(242, 0, 117, 21);
		panel.add(btnPersonelEkle);
		
		JButton btnCikis = new JButton("\u00C7IKI\u015E");
		btnCikis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "��kmak �stedi�inize Emin Misiniz?", "UYARI",
				        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				    setVisible(false);
				    OtelLoginPanel otel = new OtelLoginPanel();
				    otel.setVisible(true);
				} else {
				    
				}
				
				
			}
		});
		btnCikis.setBounds(691, 0, 85, 21);
		panel.add(btnCikis);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 31, 410, 312);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel jlbl_musteri_ad = new JLabel("Ad:");
		jlbl_musteri_ad.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_musteri_ad.setBounds(10, 16, 58, 13);
		panel_1.add(jlbl_musteri_ad);
		
		jtxt_musteri_email = new JTextField();
		jtxt_musteri_email.setEnabled(false);
		jtxt_musteri_email.setColumns(10);
		jtxt_musteri_email.setBounds(87, 39, 108, 19);
		panel_1.add(jtxt_musteri_email);
		
		JLabel jlbl_musteri_email = new JLabel("Email:");
		jlbl_musteri_email.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_musteri_email.setBounds(10, 42, 58, 13);
		panel_1.add(jlbl_musteri_email);
		
		JLabel jlbl_musteri_adres = new JLabel("Adres:");
		jlbl_musteri_adres.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_musteri_adres.setBounds(10, 68, 58, 13);
		panel_1.add(jlbl_musteri_adres);
		
		jtxt_musteri_adres = new JTextField();
		jtxt_musteri_adres.setEnabled(false);
		jtxt_musteri_adres.setColumns(10);
		jtxt_musteri_adres.setBounds(87, 65, 108, 19);
		panel_1.add(jtxt_musteri_adres);
		
		JLabel jlbl_cikis = new JLabel("\u00C7\u0131k\u0131\u015F Tarihi:");
		jlbl_cikis.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_cikis.setBounds(218, 39, 67, 13);
		panel_1.add(jlbl_cikis);
		
		JLabel jlbl_giris = new JLabel("Giri\u015F Tarihi:");
		jlbl_giris.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_giris.setBounds(218, 13, 67, 13);
		panel_1.add(jlbl_giris);
		
		JLabel jlbl_oda_no = new JLabel("Oda No:");
		jlbl_oda_no.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_oda_no.setBounds(218, 65, 67, 13);
		panel_1.add(jlbl_oda_no);
		
		JLabel jlbl_oda_tipi = new JLabel("Oda Tipi:");
		jlbl_oda_tipi.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_oda_tipi.setBounds(218, 91, 67, 13);
		panel_1.add(jlbl_oda_tipi);
		
		JLabel jlbl_otel_email = new JLabel("Oda \u00DCcreti:");
		jlbl_otel_email.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_otel_email.setBounds(218, 117, 67, 13);
		panel_1.add(jlbl_otel_email);
		
		jtxt_oda_fiyat = new JTextField();
		jtxt_oda_fiyat.setEnabled(false);
		jtxt_oda_fiyat.setColumns(10);
		jtxt_oda_fiyat.setBounds(295, 114, 96, 19);
		panel_1.add(jtxt_oda_fiyat);
		
		jtxt_oda_tip = new JTextField();
		jtxt_oda_tip.setEnabled(false);
		jtxt_oda_tip.setColumns(10);
		jtxt_oda_tip.setBounds(295, 88, 96, 19);
		panel_1.add(jtxt_oda_tip);
		
		jtxt_oda_no = new JTextField();
		jtxt_oda_no.setEnabled(false);
		jtxt_oda_no.setColumns(10);
		jtxt_oda_no.setBounds(295, 62, 96, 19);
		panel_1.add(jtxt_oda_no);
		
		JLabel jlbl_yemek = new JLabel("Yemek Tercihi:");
		jlbl_yemek.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_yemek.setBounds(10, 98, 96, 13);
		panel_1.add(jlbl_yemek);
		
		JLabel jlbl_toplam = new JLabel("Toplam:");
		jlbl_toplam.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_toplam.setBounds(218, 140, 67, 26);
		panel_1.add(jlbl_toplam);
		
		jtxt_toplam = new JTextField();
		jtxt_toplam.setColumns(10);
		jtxt_toplam.setBounds(295, 143, 96, 19);
		panel_1.add(jtxt_toplam);
		
		JDateChooser date_giris = new JDateChooser();
		date_giris.setDateFormatString("dd/MM/yyyy");
		date_giris.setBounds(295, 7, 96, 19);
		panel_1.add(date_giris);
		
		JDateChooser date_cikis = new JDateChooser();
		date_cikis.setDateFormatString("dd/MM/yyyy");
		date_cikis.setBounds(295, 33, 96, 19);
		panel_1.add(date_cikis);
		
	
		
		
		
		JButton btn_reservasyon_ekle = new JButton("EKLE");
		btn_reservasyon_ekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String otel_id = id_field.getText();
				String otelid = otel_id;
				try {
			/*		int musteriSecim = admin_tablo_musteri.getSelectedRow();
					DefaultTableModel tm = (DefaultTableModel)admin_tablo_musteri.getModel();
					
					int odaSecim = admin_tablo_oda.getSelectedRow();
					DefaultTableModel tmo = (DefaultTableModel)admin_tablo_oda.getModel();
					
*/
					
			
												
					
					//	String musteri_id = tm.getValueAt(musteriSecim, 0).toString();
						String musteri_id = gizli_musteri_id.getText();
						String musteriadi = jtxt_musteri_ad.getText();
						String musteriemaili = jtxt_musteri_email.getText();
						String  musteriadresi= jtxt_musteri_adres.getText();
						
					
						Statement st = fonk.conn.createStatement();
						String query = "SELECT hotel_name FROM hotel WHERE hotel_id="+otel_id;
						ResultSet rs = st.executeQuery(query);
						rs.beforeFirst();
						rs.next();
						String otelad =rs.getString(1); // ***************************************;
						
						
					
						
				
							
							
					
						
					//  String giris = date_giris.getDate().toGMTString();
					//	String cikis =date_cikis.getDate().toGMTString();
						
					//****** �u tarih format� �ok u�ra�t�rd�. Burada kullan�c�n�n girdi�i tarihleri a�a��daki formatta string e �evirip veritaban�na ekliyor.
					//****** ayn� �ekilde date_giris ve date_giris alanlar�n�n da format�n� bu �ekilde belirttim.
					//****** ayr�ca rezervasyon tablosuna t�kland���nda da bu formata �evirip date alanlar�na koyuyor. 
						
						
						SimpleDateFormat sdfgiris = new SimpleDateFormat("dd/MM/yyyy");
						String giris = sdfgiris.format(date_giris.getDate()).toString();
						String cikis = sdfgiris.format(date_cikis.getDate()).toString();

					//		String oda_id =tmo.getValueAt(odaSecim, 0).toString();
						
						String oda_id = gizli_oda_id.getText();
						String odano =jtxt_oda_no.getText();
						String odatip= jtxt_oda_tip.getText();
						String odafiyat = jtxt_oda_fiyat.getText();
						String yemek = jtxt_yemek.getText();
						String yemekucret = jtxt_yemek_ucret.getText();
						String toplam = jtxt_toplam.getText();
						
						
						fonk.rezervasyonEkle(musteri_id, musteriadi, musteriemaili, musteriadresi, otelid, otelad, giris,cikis,oda_id,  odano, odatip, odafiyat, yemek, yemekucret, toplam);
						
					
						rezervasyonListele(otelid);
						
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Hatal� Giri�"+ex, "S�STEM MESAJI", JOptionPane.OK_OPTION);
				}
			
				
					
				
				}
				
			}
		);
		btn_reservasyon_ekle.setBounds(10, 204, 85, 21);
		panel_1.add(btn_reservasyon_ekle);
		
		
		
		
		
		
		JButton btn_rezervasyon_duzenle = new JButton("D\u00DCZENLE");
		btn_rezervasyon_duzenle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String otel_id = id_field.getText();
				try {
					String musteri_id = gizli_musteri_id.getText();
					String musteriadi = jtxt_musteri_ad.getText();
					String musteriemaili = jtxt_musteri_email.getText();
					String  musteriadresi= jtxt_musteri_adres.getText();	
					SimpleDateFormat sdfgiris = new SimpleDateFormat("dd/MM/yyyy");
					
			
					Statement st = fonk.conn.createStatement();
					String query = "SELECT hotel_name FROM hotel WHERE hotel_id='"+otel_id+"'";
					ResultSet rs = st.executeQuery(query);
					rs.beforeFirst();
					rs.next();
					String otelad =rs.getString(1);
			
					
					
					String giris = sdfgiris.format(date_giris.getDate()).toString();
					String cikis = sdfgiris.format(date_cikis.getDate()).toString();
					
					String oda_id = gizli_oda_id.getText();
					String odano =jtxt_oda_no.getText();
					String odatip= jtxt_oda_tip.getText();
					String odafiyat = jtxt_oda_fiyat.getText();
					String yemek = jtxt_yemek.getText();
					String yemekucret = jtxt_yemek_ucret.getText();
					String toplam = jtxt_toplam.getText();
					
					
					
					int selectedRow = admin_tablo_rezervasyon.getSelectedRow();
					DefaultTableModel tmo = (DefaultTableModel)admin_tablo_rezervasyon.getModel();
					if(selectedRow==-1){
					        if(tmo.getRowCount()==0){
					        	JOptionPane.showMessageDialog(null, "Liste bo�!", "S�STEM MESAJI", JOptionPane.OK_OPTION);
					        }else { JOptionPane.showMessageDialog(null, "Se�im yap�n�z!", "S�STEM MESAJI", JOptionPane.OK_OPTION);}
					    }else {
					    	int resid = Integer.parseInt(tmo.getValueAt(selectedRow, 0).toString());
					    	
					    	fonk.rezervasyonDuzenle(resid,musteri_id, musteriadi, musteriemaili, musteriadresi, otel_id, otelad, giris,cikis,oda_id,  odano, odatip, odafiyat, yemek, yemekucret, toplam);

					    	JOptionPane.showMessageDialog(null, "D�zenleme Ba�ar�l�", "S�STEM MESAJI", JOptionPane.OK_OPTION);
					    	

					    	rezervasyonListele(otel_id);
					    
					    }
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btn_rezervasyon_duzenle.setBounds(10, 235, 85, 21);
		panel_1.add(btn_rezervasyon_duzenle);
		
		JButton btn_rezervasyon_sil = new JButton("S\u0130L");
		btn_rezervasyon_sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedrow = admin_tablo_rezervasyon.getSelectedRow();
				DefaultTableModel tm = (DefaultTableModel)admin_tablo_rezervasyon.getModel();
				
			      if(selectedrow==-1){
			            if(tm.getRowCount()==0){
			            	JOptionPane.showMessageDialog(null, "Liste bo�!", "S�STEM MESAJI", JOptionPane.OK_OPTION);
			            }else { JOptionPane.showMessageDialog(null, "Se�im yap�n�z!", "S�STEM MESAJI", JOptionPane.OK_OPTION);}
			        }else {
			        	
			        	int resid = Integer.parseInt(tm.getValueAt(selectedrow, 0).toString());
			            fonk.rezervasyonSil(resid);	
			           
		
			           rezervasyonListele(id_field.getText());
			            JOptionPane.showMessageDialog(null, "Silme Ba�ar�l�!", "S�STEM MESAJI", JOptionPane.OK_OPTION);
			        
			        }
			}
		});
		btn_rezervasyon_sil.setBounds(10, 266, 85, 21);
		panel_1.add(btn_rezervasyon_sil);
		
	

		
		
		
		
		JButton btn_toplam = new JButton("TOPLAM");
		btn_toplam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String syemek = jtxt_yemek_ucret.getText();
				String soda = jtxt_oda_fiyat.getText();
				if(syemek.isEmpty()) {
					Float foda = Float.valueOf(soda);
					jtxt_toplam.setText(foda.toString());
				}else {
					Float fyemek = Float.valueOf(syemek);
					Float foda = Float.valueOf(soda);
					Float ftoplam = fyemek + foda;
					jtxt_toplam.setText((ftoplam).toString());
				}
			}
		});
		btn_toplam.setBounds(306, 172, 85, 21);
		panel_1.add(btn_toplam);
		
		jtxt_musteri_ad = new JTextField();
		jtxt_musteri_ad.setEnabled(false);
		jtxt_musteri_ad.setColumns(10);
		jtxt_musteri_ad.setBounds(87, 13, 108, 19);
		panel_1.add(jtxt_musteri_ad);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(193, 204, 198, 98);
		panel_1.add(scrollPane_3);
		
		yemek_tablo = new JTable();
		yemek_tablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedrow = yemek_tablo.getSelectedRow();
				DefaultTableModel tm = (DefaultTableModel)yemek_tablo.getModel();
				jtxt_yemek.enable(true);
				jtxt_yemek_ucret.enable(true);
				jtxt_yemek.setText(tm.getValueAt(selectedrow, 1).toString());
				jtxt_yemek_ucret.setText(tm.getValueAt(selectedrow, 2).toString());
				
				
				
			}
		});
		yemek_tablo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		yemek_tablo.setForeground(Color.BLACK);
		yemek_tablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Yemek ID", "Yemek Tipi", "Yemek Fiyat\u0131"
			}
		));
		scrollPane_3.setViewportView(yemek_tablo);
		
		jtxt_yemek = new JTextField();
		jtxt_yemek.setEnabled(false);
		jtxt_yemek.setColumns(10);
		jtxt_yemek.setBounds(110, 94, 85, 19);
		panel_1.add(jtxt_yemek);
		
		jtxt_yemek_ucret = new JTextField();
		jtxt_yemek_ucret.setEnabled(false);
		jtxt_yemek_ucret.setColumns(10);
		jtxt_yemek_ucret.setBounds(110, 120, 85, 19);
		panel_1.add(jtxt_yemek_ucret);
		
		JLabel jlbl_yemek_ucret = new JLabel("Yemek \u00DCcreti:");
		jlbl_yemek_ucret.setHorizontalAlignment(SwingConstants.LEFT);
		jlbl_yemek_ucret.setBounds(10, 124, 96, 13);
		panel_1.add(jlbl_yemek_ucret);
		
		gizli_oda_id = new JTextField();
		gizli_oda_id.setEnabled(false);
		gizli_oda_id.setEditable(false);
		gizli_oda_id.setVisible(false);

		gizli_oda_id.setBounds(176, 205, 7, 19);
		panel_1.add(gizli_oda_id);
		gizli_oda_id.setColumns(10);
		
		gizli_musteri_id = new JTextField();
		gizli_musteri_id.setEnabled(false);
		gizli_musteri_id.setEditable(false);
		gizli_musteri_id.setVisible(false);
		gizli_musteri_id.setColumns(10);
		gizli_musteri_id.setBounds(176, 236, 7, 19);
		panel_1.add(gizli_musteri_id);
		
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(429, 31, 337, 148);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 10, 337, 138);
		panel_2.add(scrollPane_1);
		
		admin_tablo_oda = new JTable();
		admin_tablo_oda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedrow = admin_tablo_oda.getSelectedRow();
				DefaultTableModel tm = (DefaultTableModel)admin_tablo_oda.getModel();
				jtxt_oda_no.enable(true);
				jtxt_oda_tip.enable(true);
				jtxt_oda_fiyat.enable(true);
				jtxt_oda_no.setText(tm.getValueAt(selectedrow, 1).toString());
				jtxt_oda_tip.setText(tm.getValueAt(selectedrow, 2).toString());
				jtxt_oda_fiyat.setText(tm.getValueAt(selectedrow, 3).toString()); 
				gizli_oda_id.setText(tm.getValueAt(selectedrow, 0).toString());
				
				
			}
		});
		admin_tablo_oda.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Oda ID", "Oda No", "Oda Tipi", "Oda Fiyat\u0131"
			}
		));
		scrollPane_1.setViewportView(admin_tablo_oda);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBounds(0, 353, 766, 190);
		panel.add(panel_2_1);
		panel_2_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 766, 190);
		panel_2_1.add(scrollPane);
		
		admin_tablo_rezervasyon = new JTable();
		admin_tablo_rezervasyon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedrow = admin_tablo_rezervasyon.getSelectedRow();
				DefaultTableModel tmr = (DefaultTableModel)admin_tablo_rezervasyon.getModel();
				
			try {
					String dateValue1 = tmr.getValueAt(selectedrow,5).toString(); // What ever column
					java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateValue1);
					date_giris.setDate(date1);
				
					String dateValue2 = tmr.getValueAt(selectedrow,6).toString(); // What ever column
					java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(dateValue2);
					date_cikis.setDate(date2);
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	

				jtxt_musteri_ad.enable(true);
				jtxt_musteri_email.enable(true);
				jtxt_musteri_adres.enable(true);
				jtxt_oda_no.enable(true);
				jtxt_oda_tip.enable(true);
				jtxt_oda_fiyat.enable(true);
				jtxt_yemek.enable(true);
				jtxt_yemek_ucret.enable(true);
		
				jtxt_musteri_ad.setText(tmr.getValueAt(selectedrow, 1).toString());
				jtxt_musteri_email.setText(tmr.getValueAt(selectedrow, 2).toString());
				jtxt_musteri_adres.setText(tmr.getValueAt(selectedrow, 3).toString());
	
				jtxt_oda_no.setText(tmr.getValueAt(selectedrow, 7).toString());
				jtxt_oda_tip.setText(tmr.getValueAt(selectedrow, 8).toString());
				jtxt_oda_fiyat.setText(tmr.getValueAt(selectedrow, 9).toString());
				jtxt_yemek.setText(tmr.getValueAt(selectedrow, 10).toString());
				jtxt_yemek_ucret.setText(tmr.getValueAt(selectedrow, 11).toString());
				jtxt_toplam.setText(tmr.getValueAt(selectedrow, 12).toString());
				
				
				
				
				
				
				String reservid = tmr.getValueAt(selectedrow, 0).toString();
				try {
					Statement st = fonk.conn.createStatement();
					String query = "SELECT customer_id, room_id FROM reservation WHERE reservation_id="+reservid;
					ResultSet rs = st.executeQuery(query);
					
					
					
					
					while (rs.next()) {
						String c = rs.getString("customer_id");
						String r = rs.getString("room_id");
						gizli_musteri_id.setText(c);
						gizli_oda_id.setText(r);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		admin_tablo_rezervasyon.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Rezervasyon ID", "Ad", "Email", "Adres", "Otel Ad\u0131", "Giri\u015F Tarihi", "\u00C7\u0131k\u0131\u015F Tarihi", "Oda No", "Oda Tipi", "Oda Fiyat\u0131", "Yemek Tercihi", "Yemek Fiyat\u0131", "Toplam"
			}
		));
		scrollPane.setViewportView(admin_tablo_rezervasyon);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(430, 189, 336, 154);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 0, 336, 144);
		panel_3.add(scrollPane_2);
		
		admin_tablo_musteri = new JTable();
		admin_tablo_musteri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedrow = admin_tablo_musteri.getSelectedRow();
				DefaultTableModel tm = (DefaultTableModel)admin_tablo_musteri.getModel();
				
				jtxt_musteri_ad.enable(true);
				jtxt_musteri_email.enable(true);
				jtxt_musteri_adres.enable(true);
				gizli_musteri_id.setText(tm.getValueAt(selectedrow, 0).toString());
				jtxt_musteri_ad.setText(tm.getValueAt(selectedrow, 2).toString());
				jtxt_musteri_email.setText(tm.getValueAt(selectedrow, 3).toString());
				jtxt_musteri_adres.setText(tm.getValueAt(selectedrow, 4).toString());
			}
		});
		admin_tablo_musteri.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00FC\u015Fteri ID", "Otel ID", "Ad\u0131", "Email", "Adres"
			}
		));
		scrollPane_2.setViewportView(admin_tablo_musteri);
		
		JButton btnYemekEkle = new JButton("Yemek Tercihi Ekle");
		btnYemekEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OtelYemekEkle yemek = new OtelYemekEkle();
				yemek.setJtxt_otel_id(id_field.getText().toString());
				yemek.yemekListele(id_field.getText());
				yemek.setVisible(true);
				
		
			}
		});
		btnYemekEkle.setBounds(359, 0, 160, 21);
		panel.add(btnYemekEkle);
		
		id_field = new JTextField();
		id_field.setEnabled(false);
		id_field.setEditable(false);
		id_field.setBounds(656, 0, 25, 21);
		panel.add(id_field);
		id_field.setColumns(10);
		
		
	}
	public JTextField setId_field(String id) {
		id_field.setText(id);
		return id_field;
	}

}
