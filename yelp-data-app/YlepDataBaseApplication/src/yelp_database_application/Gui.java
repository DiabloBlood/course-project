package yelp_database_application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Gui {	
	// JDBC driver name and database URL
	public static final String HOST = "localhost";
	public static final String PORT = "1521";
	public static final String DB_NAME = "orcl";
	public static final String USER_NAME = "scott";
	public static final String PASSWORD = "tiger";
	public static final String DB_URL = "jdbc:oracle:thin:@" + HOST + ":" + PORT + ":" + DB_NAME;
	//global database variable
	static Connection connection = null;
	static Statement statement = null;
	//all the fonts
	public final static Font TAHOMA_FONT_18 = new Font("Tahoma", Font.BOLD, 18);
	public final static Font TAHOMA_FONT_16 = new Font("Tahoma", Font.BOLD, 16);
	public final static Font TAHOMA_FONT_14 = new Font("Tahoma", Font.BOLD, 14);
	public final static Font TAHOMA_FONT_12 = new Font("Tahoma", Font.BOLD, 12);
	public final static Font TAHOMA_FONT_10 = new Font("Tahoma", Font.BOLD, 10);
	//all the colors
	public final static Color CORN_FLOWER_BLUE = new Color(100, 149, 237);
	public final static Color LIGHT_SLATE_GRAY = new Color(119, 136, 153);
	public final static Color DARK_SLATE_BLUE = new Color(72, 61, 139);
	//(30, 144, 255), (0, 0, 128), (72, 61, 139)
	// frame bounds
	public final static int FRAME_OFFSET_X = 0;
	public final static int FRAME_OFFSET_Y = 0;
	public final static int FRAME_WIDTH = 1200;
	public final static int FRAME_HEIGHT = 700;
	//labelBusiness bounds
	public final static int LABEL_BUSINESS_OFFSET_X = 10;
	public final static int LABEL_BUSINESS_OFFSET_Y = 10;
	public final static int LABEL_BUSINESS_WIDTH = 600;
	public final static int LABEL_BUSINESS_HEIGHT = 30;
	//labelCategory bounds
	public final static int LABEL_CATEGORY_OFFSET_X = LABEL_BUSINESS_OFFSET_X;
	public final static int LABEL_CATEGORY_OFFSET_Y = LABEL_BUSINESS_OFFSET_Y + LABEL_BUSINESS_HEIGHT;
	public final static int LABEL_CATEGORY_WIDTH = LABEL_BUSINESS_WIDTH / 3;
	public final static int LABEL_CATEGORY_HEIGHT = LABEL_BUSINESS_HEIGHT + 5;
	//labelSubCategory bounds
	public final static int LABEL_SUB_CATEGORY_OFFSET_X = LABEL_BUSINESS_OFFSET_X + LABEL_CATEGORY_WIDTH;
	public final static int LABEL_SUB_CATEGORY_OFFSET_Y = LABEL_CATEGORY_OFFSET_Y;
	public final static int LABEL_SUB_CATEGORY_WIDTH = LABEL_BUSINESS_WIDTH / 3;
	public final static int LABEL_SUB_CATEGORY_HEIGHT = LABEL_CATEGORY_HEIGHT;
	//labelAttribute bounds
	public final static int LABEL_ARRIBUTE_OFFSET_X = LABEL_SUB_CATEGORY_OFFSET_X + LABEL_SUB_CATEGORY_WIDTH;
	public final static int LABEL_ARRIBUTE_OFFSET_Y = LABEL_CATEGORY_OFFSET_Y;
	public final static int LABEL_ARRIBUTE_WIDTH = LABEL_BUSINESS_WIDTH / 3;
	public final static int LABEL_ARRIBUTE_HEIGHT = LABEL_CATEGORY_HEIGHT;
	//scrollPaneCategory bounds
	public final static int SCROLL_CATEGORY_OFFSET_X = LABEL_CATEGORY_OFFSET_X;
	public final static int SCROLL_CATEGORY_OFFSET_Y = LABEL_CATEGORY_OFFSET_Y + LABEL_CATEGORY_HEIGHT;
	public final static int SCROLL_CATEGORY_WIDTH = LABEL_CATEGORY_WIDTH;
	public final static int SCROLL_CATEGORY_HEIGHT = 260;
	//scrollPaneSubCategory bounds
	public final static int SCROLL_SUB_CATEGORY_OFFSET_X = LABEL_SUB_CATEGORY_OFFSET_X;
	public final static int SCROLL_SUB_CATEGORY_OFFSET_Y = SCROLL_CATEGORY_OFFSET_Y;
	public final static int SCROLL_SUB_CATEGORY_WIDTH = LABEL_SUB_CATEGORY_WIDTH;
	public final static int SCROLL_SUB_CATEGORY_HEIGHT = SCROLL_CATEGORY_HEIGHT;
	//scrollPaneAttribute bounds
	public final static int SCROLL_ATTRIBUTE_OFFSET_X = LABEL_ARRIBUTE_OFFSET_X;
	public final static int SCROLL_ATTRIBUTE_OFFSET_Y = SCROLL_CATEGORY_OFFSET_Y;
	public final static int SCROLL_ATTRIBUTE_WIDTH = LABEL_SUB_CATEGORY_WIDTH;
	public final static int SCROLL_ATTRIBUTE_HEIGHT = SCROLL_CATEGORY_HEIGHT;
	//set CheckBox sizes
	public final static int CHECKBOX_WIDTH = LABEL_CATEGORY_WIDTH;
	public final static int CHECKBOX_HEIGHT = 20;
	//Search for
	public final static int LABEL_SEARCH_FOR_OFFSET_X = LABEL_BUSINESS_OFFSET_X + 10;
	public final static int LABEL_SEARCH_FOR_OFFSET_Y = SCROLL_CATEGORY_OFFSET_Y + SCROLL_CATEGORY_HEIGHT + 5;
	public final static int LABEL_SEARCH_FOR_WIDTH = 160;
	public final static int LABEL_SEARCH_FOR_HEIGHT = 30;
	
	public final static int COMBOX_SEARCH_FOR_OFFSET_X = LABEL_SEARCH_FOR_OFFSET_X;
	public final static int COMBOX_SEARCH_FOR_OFFSET_Y = LABEL_SEARCH_FOR_OFFSET_Y + LABEL_SEARCH_FOR_HEIGHT;
	public final static int COMBOX_SEARCH_FOR_WIDTH = 320;
	public final static int COMBOX_SEARCH_FOR_HEIGHT = 25;
	//label Review bounds
	public final static int LABEL_REVIEW_OFFSET_X = LABEL_BUSINESS_OFFSET_X + LABEL_BUSINESS_WIDTH + 10;
	public final static int LABEL_REVIEW_OFFSET_Y = LABEL_CATEGORY_OFFSET_Y;
	public final static int LABEL_REVIEW_WIDTH = LABEL_BUSINESS_WIDTH / 3;
	public final static int LABEL_REVIEW_HEIGHT = LABEL_CATEGORY_HEIGHT;
	//panelReveiw bounds
	public final static int PANEL_REVIEW_OFFSET_X = LABEL_REVIEW_OFFSET_X;
	public final static int PANEL_REVIEW_OFFSET_Y = LABEL_REVIEW_OFFSET_Y + LABEL_REVIEW_HEIGHT;
	public final static int PANEL_REVIEW_WIDTH = LABEL_REVIEW_WIDTH;
	public final static int PANEL_REVIEW_HEIGHT = SCROLL_CATEGORY_HEIGHT;
	//review rows dimention
	public final static int LABEL_REVIEW_FIELD_WIDTH = 40;
	public final static int LABEL_REVIEW_FIELD_HEIGHT = 30;
	public final static int SELECTION_REVIEW_FIELD_WIDTH = 130;
	public final static int SELECTION_REVIEW_FIELD_HEIGHT = LABEL_REVIEW_FIELD_HEIGHT;
	//label users
	public final static int LABEL_USERS_OFFSET_X = LABEL_BUSINESS_OFFSET_X;
	public final static int LABEL_USERS_OFFSET_Y = LABEL_BUSINESS_OFFSET_Y + 400;
	public final static int LABEL_USERS_WIDTH = LABEL_BUSINESS_WIDTH ;
	public final static int LABEL_USERS_HEIGHT = LABEL_BUSINESS_HEIGHT;
	//panel users bounds
	public final static int PANEL_USERS_OFFSET_X = LABEL_USERS_OFFSET_X;
	public final static int PANEL_USERS_OFFSET_Y = LABEL_USERS_OFFSET_Y + LABEL_USERS_HEIGHT;
	public final static int PANEL_USERS_WIDTH = LABEL_USERS_WIDTH;
	public final static int PANEL_USERS_HEIGHT = 205;
	//rows dimention of users
	public final static int LABEL_USERS_TITLE_WIDTH = 140;
	public final static int LABEL_USERS_TITLE_HEIGHT = 28;
	
	public final static int COMBOX_USERS_WIDTH = 180;
	public final static int COMBOX_USERS_HEIGHT = LABEL_USERS_TITLE_HEIGHT;
	
	public final static int LABEL_USERS_VALUE_WIDTH = 60;
	public final static int LABEL_USERS_VALUE_HEIGHT = LABEL_USERS_TITLE_HEIGHT;
	
	public final static int TEXT_USERS_VALUE_WIDTH = 180;
	public final static int TEXT_USERS_VALUE_HEIGHT = LABEL_USERS_TITLE_HEIGHT;
	
	public final static int COMBOX_USERS_SELECTION_WIDTH = LABEL_USERS_TITLE_WIDTH + COMBOX_USERS_WIDTH + 5 ;
	public final static int COMBOX_USERS_SELECTION_HEIGHT = 25;
	//label result bounds
	public final static int LABEL_RESULT_OFFSET_X = LABEL_REVIEW_OFFSET_X + LABEL_REVIEW_WIDTH + 10;
	public final static int LABEL_RESULT_OFFSET_Y = LABEL_REVIEW_OFFSET_Y ;
	public final static int LABEL_RESULT_WIDTH = 340;
	public final static int LABEL_RESULT_HEIGHT = LABEL_CATEGORY_HEIGHT;
	//panel results bounds
	public final static int PANEL_RESULT_OFFSET_X = LABEL_RESULT_OFFSET_X;
	public final static int PANEL_RESULT_OFFSET_Y = LABEL_RESULT_OFFSET_Y + LABEL_RESULT_HEIGHT;
	public final static int PANEL_RESULT_WIDTH = LABEL_RESULT_WIDTH;
	public final static int PANEL_RESULT_HEIGHT = PANEL_REVIEW_HEIGHT;
	//text area bounds
	public final static int TEXT_QUERY_OFFSET_X = LABEL_REVIEW_OFFSET_X;
	public final static int TEXT_QUERY_OFFSET_Y = PANEL_REVIEW_OFFSET_Y + PANEL_REVIEW_HEIGHT + 40;
	public final static int TEXT_QUERY_WIDTH = LABEL_REVIEW_WIDTH + LABEL_RESULT_WIDTH + 10;
	public final static int TEXT_QUERY_HEIGHT = 180;
	//execute query button
	public final static int BUTTON_GAP = 50;
	public final static int BUTTON_WIDTH = 180;
	public final static int BUTTON_HEIGHT = 40;
	public final static int BUTTON_EDGE_OFFSET = (TEXT_QUERY_WIDTH - BUTTON_GAP - 2 * BUTTON_WIDTH) / 2;
	
	public final static int BUTTON_EXECUTE_OFFSET_X = TEXT_QUERY_OFFSET_X + BUTTON_EDGE_OFFSET;
	public final static int BUTTON_EXECUTE_OFFSET_Y = TEXT_QUERY_OFFSET_Y + TEXT_QUERY_HEIGHT  + 35;
	public final static int BUTTON_EXECUTE_WIDTH = BUTTON_WIDTH;
	public final static int BUTTON_EXECUTE_HEIGHT = BUTTON_HEIGHT;
	//clear query button
	public final static int BUTTON_CLEAR_OFFSET_X = BUTTON_EXECUTE_OFFSET_X + BUTTON_EXECUTE_WIDTH + BUTTON_GAP;
	public final static int BUTTON_CLEAR_OFFSET_Y = BUTTON_EXECUTE_OFFSET_Y;
	public final static int BUTTON_CLEAR_WIDTH = BUTTON_WIDTH;
	public final static int BUTTON_CLEAR_HEIGHT = BUTTON_HEIGHT;
	
		
	public static void main(String[] args) {
		
		connection = Gui.getConection();		
		//Gui.closeConnection(connection);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui gui = new Gui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Gui() {
		initBaseFrame();
		initBusinessLableField();
		initBusinessPanelField();
		initBusinessSearchField();
		initReviewField();
		initUsersField();
		initResultField();
		initShowQueryField();
		initExecuteQueryButton();
		initClearQueryButton();
	}
	
	//set parent container
	JFrame frame;
	//set Business field labels
	JLabel labelBusiness;
	JLabel labelCategory;
	JLabel labelSubCategory;
	JLabel labelAttribute;
	//set Business field panels
	JPanel panelCategory;
	JPanel panelSubCategory;
	JPanel panelAttribute;
	JScrollPane scrollPaneCategory;
	JScrollPane scrollPaneSubCategory;
	JScrollPane scrollPaneAttribute;
	
	JLabel labelSearchFor;
	JComboBox comboBoxBusinessSelect;
	// Category, SubCategory, Attribute lists
	ArrayList<JCheckBox> categoryCheckBoxGroup = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> subCategoryCheckBoxGroup = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> attributeCheckBoxGroup = new ArrayList<JCheckBox>();
	ArrayList<String> selectedCategory = new ArrayList<String>();
	ArrayList<String> selectedSubCategory = new ArrayList<String>();
	ArrayList<String> selectedAttribute = new ArrayList<String>();
	
	//set Review field
	JLabel labelReview;
	JPanel panelReview;
	
	JLabel labelReviewFrom;
	JLabel labelReviewTo;
	JLabel labelReviewStar;
	JLabel labelReviewStarValue;
	JLabel labelReviewVotes;
	JLabel labelReviewVotesValue;
	
	JComboBox comboBoxReviewStar;
	JComboBox comboBoxReviewVotes;
	JTextField textReviewStarValue;
	JTextField textReviewVotesValue;
	
	UtilDateModel dateModelReviewFrom;
	JDatePanelImpl datePanelReviewFrom;
	JDatePickerImpl datePickerReviewFrom;
	
	UtilDateModel dateModelReviewTo;
	JDatePanelImpl datePanelReviewTo;
	JDatePickerImpl datePickerReviewTo;
	//set Users Field
	JLabel labelUsers;
	JPanel panelUsers;
	
	JLabel labelMemberSince;
	JLabel labelReviewCount;
	JLabel labelNumFriends;
	JLabel labelAvgStars;
	JLabel labelNumVotes;
	
	UtilDateModel dateModelMemberSince;
	JDatePanelImpl datePanelMemberSince;
	JDatePickerImpl datePickerMemberSince;	
	JComboBox comboBoxReviewCount;
	JComboBox comboBoxNumFriends;
	JComboBox comboBoxAvgStars;
	JComboBox comboBoxNumVotes;
	
	JLabel labelMemberSinceValue;
	JLabel labelReviewCountValue;
	JLabel labelNumFriendsValue;
	JLabel labelAvgStarsValue;
	JLabel labelNumVotesValue;
	
	JTextField textMemberSinceValue;
	JTextField textReviewCountValue;
	JTextField textNumFriendsValue;
	JTextField textAvgStarsValue;
	JTextField textNumVotesValue;
	
	JComboBox comboBoxUsersSelect;
	//set result field
	JLabel labelResult;
	JPanel panelResult;
	JScrollPane scrollPanelResult;
	JTable tableResult;
	//set text area
	JTextArea textShowQuery;
	//set two buttons
	JButton buttonExecuteQuery;
	JButton buttonClearQuery;
				
	private void initBaseFrame() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Gui.DARK_SLATE_BLUE);
		frame.setBounds(FRAME_OFFSET_X, FRAME_OFFSET_Y, FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
	}
	
	private void initBusinessLableField() {
		//label of Business
		labelBusiness = new JLabel("Business");
		labelBusiness.setFont(Gui.TAHOMA_FONT_16);
		labelBusiness.setBounds(LABEL_BUSINESS_OFFSET_X, LABEL_BUSINESS_OFFSET_Y, 
				LABEL_BUSINESS_WIDTH, LABEL_BUSINESS_HEIGHT);
		labelBusiness.setHorizontalAlignment(SwingConstants.CENTER);
		labelBusiness.setBackground(Gui.CORN_FLOWER_BLUE);
		labelBusiness.setOpaque(true);
		labelBusiness.setBorder(BorderFactory.createRaisedBevelBorder());
		//label of Category
		labelCategory = new JLabel("Category");
		labelCategory.setFont(Gui.TAHOMA_FONT_16);
		labelCategory.setBounds(LABEL_CATEGORY_OFFSET_X, LABEL_CATEGORY_OFFSET_Y,
				LABEL_CATEGORY_WIDTH, LABEL_CATEGORY_HEIGHT);
		labelCategory.setHorizontalAlignment(SwingConstants.CENTER);
		labelCategory.setBackground(Color.lightGray);
		labelCategory.setOpaque(true);
		labelCategory.setBorder(BorderFactory.createLineBorder(Color.black));
		//lable of SubCategory
		labelSubCategory = new JLabel("Sub-category");
		labelSubCategory.setFont(Gui.TAHOMA_FONT_16);
		labelSubCategory.setBounds(LABEL_SUB_CATEGORY_OFFSET_X, LABEL_SUB_CATEGORY_OFFSET_Y,
				LABEL_SUB_CATEGORY_WIDTH, LABEL_SUB_CATEGORY_HEIGHT);
		labelSubCategory.setHorizontalAlignment(SwingConstants.CENTER);
		labelSubCategory.setBackground(Color.lightGray);
		labelSubCategory.setOpaque(true);
		labelSubCategory.setBorder(BorderFactory.createLineBorder(Color.black));
		//lable of Attribute
		labelAttribute = new JLabel("Attribute");
		labelAttribute.setFont(Gui.TAHOMA_FONT_16);
		labelAttribute.setBounds(LABEL_ARRIBUTE_OFFSET_X, LABEL_ARRIBUTE_OFFSET_Y ,
				LABEL_ARRIBUTE_WIDTH, LABEL_ARRIBUTE_HEIGHT);
		labelAttribute.setHorizontalAlignment(SwingConstants.CENTER);
		labelAttribute.setBackground(Color.lightGray);
		labelAttribute.setOpaque(true);
		labelAttribute.setBorder(BorderFactory.createLineBorder(Color.black));
		//add them to frame
		frame.getContentPane().add(labelBusiness);
		frame.getContentPane().add(labelCategory);
		frame.getContentPane().add(labelSubCategory);
		frame.getContentPane().add(labelAttribute);
	}
	
	private void initBusinessPanelField() {
		//panel of Category
		panelCategory = new JPanel();
		panelCategory.setLayout(new GridLayout(0, 1));
		String[] categoryArray = {
				"Active Life", "Arts & Entertainment", "Automotive", "Car Rental", "Cafes",
				"Beauty & Spas", "Convenience Stores", "Dentists", "Doctors", "Drugstores",
				"Department Stores", "Education", "Event Planning & Services", "Flowers & Gifts", "Food",
				"Health & Medical", "Home Services", "Home & Garden", "Hospitals", "Hotels & Travel",
				"Hardware Stores", "Grocery", "Medical Centers", "Nurseries & Gardening", "Nightlife",
				"Restaurants", "Shopping", "Transportation"
			};
		
		for(int i = 0; i < categoryArray.length; i++) {
			JCheckBox checkBoxCategory = new JCheckBox(categoryArray[i]);
			checkBoxCategory.setFont(Gui.TAHOMA_FONT_12);
			categoryCheckBoxGroup.add(checkBoxCategory);			
			panelCategory.add(checkBoxCategory);
			String currCategory = categoryArray[i];
			checkBoxCategory.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(selectedCategory.contains(currCategory)) {
						selectedCategory.remove(currCategory);
					} else {
						selectedCategory.add(currCategory);
					}
					try {
						HashSet<String> resultSubCategory = Gui.getSubCategoryList(selectedCategory);
						panelSubCategory.removeAll();
						for(String subCategoryName: resultSubCategory) {
							JCheckBox checkBoxSubCategory = new JCheckBox(subCategoryName);
							checkBoxSubCategory.setFont(Gui.TAHOMA_FONT_12);
							subCategoryCheckBoxGroup.add(checkBoxSubCategory);
							panelSubCategory.add(checkBoxSubCategory);
							String currSubCategory = subCategoryName;
							checkBoxSubCategory.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									if(selectedSubCategory.contains(currSubCategory)) {
										selectedSubCategory.remove(currSubCategory);
									} else {
										selectedSubCategory.add(currSubCategory);
									}
									
									
									SwingWorker<String, Object> worker = new SwingWorker<String, Object>() {

										@Override
										protected void done() {
											panelAttribute.revalidate();
											panelAttribute.repaint();
										}
										
										@Override
										protected String doInBackground() throws Exception {
											try {
												HashSet<String> resultAttribute = Gui.getAttributeList(selectedCategory, selectedSubCategory);
												panelAttribute.removeAll();
												for(String attributeName: resultAttribute) {
													JCheckBox checkBoxAttribute = new JCheckBox(attributeName);
													checkBoxAttribute.setFont(Gui.TAHOMA_FONT_12);
													attributeCheckBoxGroup.add(checkBoxAttribute);
													String currAttribute = attributeName;
													checkBoxAttribute.addActionListener(new ActionListener() {

														@Override
														public void actionPerformed(ActionEvent arg0) {
															if(selectedAttribute.contains(currAttribute)) {
																selectedAttribute.remove(currAttribute);
															} else {
																selectedAttribute.add(currAttribute);
															}
														}
														
													});
													panelAttribute.add(checkBoxAttribute);
													//panelAttribute.revalidate();
													//panelAttribute.repaint();
												}
											} catch (SQLException e) {
												e.printStackTrace();
											}	
											return null;
										}							
									};
									worker.execute();
								}								
							});
							panelSubCategory.revalidate();
							panelSubCategory.repaint();
						}
					} catch (SQLException sqlException) {
						sqlException.printStackTrace();
					}
					
					/*for(int i = 0; i < selectedCategory.size(); i++) {
						System.out.println(selectedCategory.size());
						System.out.println(selectedCategory.get(i));
						System.out.println("-----------------");
					}*/
				}
				
			});
		}
		
		scrollPaneCategory = new JScrollPane(panelCategory);
		scrollPaneCategory.setBounds(SCROLL_CATEGORY_OFFSET_X, SCROLL_CATEGORY_OFFSET_Y,
				SCROLL_CATEGORY_WIDTH, SCROLL_CATEGORY_HEIGHT);
		scrollPaneCategory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneCategory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCategory.setBorder(BorderFactory.createLineBorder(Color.black));
		//panel of SubCategory
		panelSubCategory = new JPanel();
		panelSubCategory.setLayout(new GridLayout(0, 1));
		scrollPaneSubCategory = new JScrollPane(panelSubCategory);
		scrollPaneSubCategory.setBounds(SCROLL_SUB_CATEGORY_OFFSET_X, SCROLL_SUB_CATEGORY_OFFSET_Y,
				SCROLL_SUB_CATEGORY_WIDTH, SCROLL_SUB_CATEGORY_HEIGHT);
		scrollPaneSubCategory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneSubCategory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneSubCategory.setBorder(BorderFactory.createLineBorder(Color.black));
		//panel of Attribute
		panelAttribute = new JPanel();
		panelAttribute.setLayout(new GridLayout(0, 1));
		scrollPaneAttribute = new JScrollPane(panelAttribute);
		scrollPaneAttribute.setBounds(SCROLL_ATTRIBUTE_OFFSET_X, SCROLL_ATTRIBUTE_OFFSET_Y,
				SCROLL_ATTRIBUTE_WIDTH, SCROLL_ATTRIBUTE_HEIGHT);
		scrollPaneAttribute.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneAttribute.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneAttribute.setBorder(BorderFactory.createLineBorder(Color.black));
				
		frame.getContentPane().add(scrollPaneCategory);
		frame.getContentPane().add(scrollPaneSubCategory);
		frame.getContentPane().add(scrollPaneAttribute);
		
	}
	
	private void initBusinessSearchField() {
		
		labelSearchFor = new JLabel("Search For");
		labelSearchFor.setFont(Gui.TAHOMA_FONT_18);
		labelSearchFor.setBounds(LABEL_SEARCH_FOR_OFFSET_X, LABEL_SEARCH_FOR_OFFSET_Y , 
				LABEL_SEARCH_FOR_WIDTH, LABEL_SEARCH_FOR_HEIGHT);
		//labelSearchFor.setHorizontalAlignment(SwingConstants.CENTER);
		labelSearchFor.setBackground(Gui.DARK_SLATE_BLUE);
		labelSearchFor.setForeground(Color.white);
		labelSearchFor.setOpaque(true);
		//labelSearchFor.setBorder(BorderFactory.createRaisedBevelBorder());
		frame.add(labelSearchFor);
		
		comboBoxBusinessSelect = new JComboBox();
		comboBoxBusinessSelect.setFont(Gui.TAHOMA_FONT_12);
		comboBoxBusinessSelect.setBounds(COMBOX_SEARCH_FOR_OFFSET_X, COMBOX_SEARCH_FOR_OFFSET_Y,
				COMBOX_SEARCH_FOR_WIDTH, COMBOX_SEARCH_FOR_HEIGHT);
		comboBoxBusinessSelect.addItem("Select AND, OR between attributes");
		comboBoxBusinessSelect.addItem("AND");
		comboBoxBusinessSelect.addItem("OR");
		frame.add(comboBoxBusinessSelect);
		
	}
	
	private void initReviewField() {		
		//label of Review		
		labelReview = new JLabel("Review");
		labelReview.setFont(Gui.TAHOMA_FONT_16);
		labelReview.setBounds(LABEL_REVIEW_OFFSET_X, LABEL_REVIEW_OFFSET_Y, 
				LABEL_REVIEW_WIDTH, LABEL_REVIEW_HEIGHT);
		labelReview.setHorizontalAlignment(SwingConstants.CENTER);
		labelReview.setBackground(Color.LIGHT_GRAY);
		labelReview.setOpaque(true);		
		labelReview.setBorder(BorderFactory.createLineBorder(Color.black));
		
		frame.getContentPane().add(labelReview);				
		//Review Panel as parent
		panelReview = new JPanel();
		panelReview.setLayout(new FlowLayout());
		panelReview.setBounds(PANEL_REVIEW_OFFSET_X, PANEL_REVIEW_OFFSET_Y, 
				PANEL_REVIEW_WIDTH, PANEL_REVIEW_HEIGHT);
		panelReview.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.getContentPane().add(panelReview);
		
		//add label from
		labelReviewFrom = new JLabel("from");
		labelReviewFrom.setFont(Gui.TAHOMA_FONT_12);
		labelReviewFrom.setPreferredSize(new Dimension(LABEL_REVIEW_FIELD_WIDTH, LABEL_REVIEW_FIELD_HEIGHT));
		panelReview.add(labelReviewFrom);				
		//add date from 
		dateModelReviewFrom = new UtilDateModel();
		datePanelReviewFrom = new JDatePanelImpl(dateModelReviewFrom);
		datePickerReviewFrom = new JDatePickerImpl(datePanelReviewFrom);
		datePickerReviewFrom.setPreferredSize(new Dimension(SELECTION_REVIEW_FIELD_WIDTH, SELECTION_REVIEW_FIELD_HEIGHT));
		panelReview.add(datePickerReviewFrom);
		//add label to
		labelReviewTo = new JLabel("to");
		labelReviewTo.setFont(Gui.TAHOMA_FONT_12);
		labelReviewTo.setPreferredSize(new Dimension(LABEL_REVIEW_FIELD_WIDTH, LABEL_REVIEW_FIELD_HEIGHT));
		panelReview.add(labelReviewTo);
		//add date to
		dateModelReviewTo = new UtilDateModel();
		datePanelReviewTo = new JDatePanelImpl(dateModelReviewTo);
		datePickerReviewTo = new JDatePickerImpl(datePanelReviewTo);
		datePickerReviewTo.setPreferredSize(new Dimension(SELECTION_REVIEW_FIELD_WIDTH, SELECTION_REVIEW_FIELD_HEIGHT));
		//datePickerReviewTo.setBorder(BorderFactory.createLineBorder(Color.black));
		panelReview.add(datePickerReviewTo);
		//add star
		labelReviewStar = new JLabel("stars:");
		labelReviewStar.setFont(Gui.TAHOMA_FONT_12);
		labelReviewStar.setPreferredSize(new Dimension(LABEL_REVIEW_FIELD_WIDTH, LABEL_REVIEW_FIELD_HEIGHT));
		panelReview.add(labelReviewStar);
		//add star comboBox
		comboBoxReviewStar = new JComboBox();
		comboBoxReviewStar.setFont(Gui.TAHOMA_FONT_12);
		comboBoxReviewStar.setPreferredSize(new Dimension(SELECTION_REVIEW_FIELD_WIDTH, SELECTION_REVIEW_FIELD_HEIGHT));
		//comboBoxReviewStar.setBorder(BorderFactory.createLineBorder(Color.black));
		comboBoxReviewStar.addItem("=, >, <");
		comboBoxReviewStar.addItem("=");
		comboBoxReviewStar.addItem(">");
		comboBoxReviewStar.addItem("<");
		panelReview.add(comboBoxReviewStar);		
		//add value label
		labelReviewStarValue = new JLabel("value:");
		labelReviewStarValue.setFont(Gui.TAHOMA_FONT_12);
		labelReviewStarValue.setPreferredSize(new Dimension(LABEL_REVIEW_FIELD_WIDTH, LABEL_REVIEW_FIELD_HEIGHT));
		panelReview.add(labelReviewStarValue);
		//add value textField
		textReviewStarValue = new JTextField();
		textReviewStarValue.setPreferredSize(new Dimension(SELECTION_REVIEW_FIELD_WIDTH, SELECTION_REVIEW_FIELD_HEIGHT));
		textReviewStarValue.setBorder(BorderFactory.createLineBorder(Color.black));
		panelReview.add(textReviewStarValue);
		//add votes label
		labelReviewVotes = new JLabel("votes:");
		labelReviewVotes.setFont(Gui.TAHOMA_FONT_12);
		labelReviewVotes.setPreferredSize(new Dimension(LABEL_REVIEW_FIELD_WIDTH, LABEL_REVIEW_FIELD_HEIGHT));
		panelReview.add(labelReviewVotes);
		//add votes comboBox
		comboBoxReviewVotes = new JComboBox();
		comboBoxReviewVotes.setFont(Gui.TAHOMA_FONT_12);
		comboBoxReviewVotes.setPreferredSize(new Dimension(SELECTION_REVIEW_FIELD_WIDTH, SELECTION_REVIEW_FIELD_HEIGHT));
		comboBoxReviewVotes.addItem("=, >, <");
		comboBoxReviewVotes.addItem("=");
		comboBoxReviewVotes.addItem(">");
		comboBoxReviewVotes.addItem("<");
		panelReview.add(comboBoxReviewVotes);
		//add votes value label
		labelReviewVotesValue = new JLabel("value:");
		labelReviewVotesValue.setFont(Gui.TAHOMA_FONT_12);
		labelReviewVotesValue.setPreferredSize(new Dimension(LABEL_REVIEW_FIELD_WIDTH, LABEL_REVIEW_FIELD_HEIGHT));
		panelReview.add(labelReviewVotesValue);
		//add votes value text
		textReviewVotesValue = new JTextField();
		textReviewVotesValue.setPreferredSize(new Dimension(SELECTION_REVIEW_FIELD_WIDTH, SELECTION_REVIEW_FIELD_HEIGHT));
		textReviewVotesValue.setBorder(BorderFactory.createLineBorder(Color.black));
		panelReview.add(textReviewVotesValue);		
	}
	
	private void initUsersField() {
		//add label users
		labelUsers = new JLabel("Users");
		labelUsers.setFont(Gui.TAHOMA_FONT_16);
		labelUsers.setBounds(LABEL_USERS_OFFSET_X, LABEL_USERS_OFFSET_Y, 
				LABEL_USERS_WIDTH, LABEL_USERS_HEIGHT);
		labelUsers.setHorizontalAlignment(SwingConstants.CENTER);
		labelUsers.setBackground(Gui.CORN_FLOWER_BLUE);
		labelUsers.setOpaque(true);
		labelUsers.setBorder(BorderFactory.createRaisedBevelBorder());
		frame.getContentPane().add(labelUsers);
		//add panel as parent
		panelUsers = new JPanel();
		panelUsers.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelUsers.setBounds(PANEL_USERS_OFFSET_X, PANEL_USERS_OFFSET_Y, 
				PANEL_USERS_WIDTH, PANEL_USERS_HEIGHT);
		panelUsers.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.getContentPane().add(panelUsers);
		/*
		 * 第一行		
		 */
		//add label member since		
		labelMemberSince = new JLabel("Member Since");
		labelMemberSince.setFont(Gui.TAHOMA_FONT_12);
		labelMemberSince.setPreferredSize(new Dimension(LABEL_USERS_TITLE_WIDTH, LABEL_USERS_TITLE_HEIGHT));
		panelUsers.add(labelMemberSince);
		//add date member since
		dateModelMemberSince = new UtilDateModel();
		datePanelMemberSince = new JDatePanelImpl(dateModelMemberSince);
		datePickerMemberSince = new JDatePickerImpl(datePanelMemberSince);
		datePickerMemberSince.setPreferredSize(new Dimension(COMBOX_USERS_WIDTH, COMBOX_USERS_HEIGHT));
		panelUsers.add(datePickerMemberSince);	
		//add value member since
		labelMemberSinceValue = new JLabel("Value:");
		labelMemberSinceValue.setFont(Gui.TAHOMA_FONT_12);
		labelMemberSinceValue.setPreferredSize(new Dimension(LABEL_USERS_VALUE_WIDTH, LABEL_USERS_VALUE_HEIGHT));
		labelMemberSinceValue.setHorizontalAlignment(SwingConstants.RIGHT);
		panelUsers.add(labelMemberSinceValue);
		//add value text member since
		textMemberSinceValue = new JTextField();
		textMemberSinceValue.setPreferredSize(new Dimension(TEXT_USERS_VALUE_WIDTH, TEXT_USERS_VALUE_HEIGHT));
		textMemberSinceValue.setBorder(BorderFactory.createLineBorder(Color.black));		
		panelUsers.add(textMemberSinceValue);		
		/*
		 * 第二行
		 */
		//add label review count
		labelReviewCount = new JLabel("Review Count");
		labelReviewCount.setFont(Gui.TAHOMA_FONT_12);
		labelReviewCount.setPreferredSize(new Dimension(LABEL_USERS_TITLE_WIDTH, LABEL_USERS_TITLE_HEIGHT));
		panelUsers.add(labelReviewCount);
		//add comboBox review count
		comboBoxReviewCount = new JComboBox();
		comboBoxReviewCount.setFont(Gui.TAHOMA_FONT_12);
		comboBoxReviewCount.setPreferredSize(new Dimension(COMBOX_USERS_WIDTH, COMBOX_USERS_HEIGHT));
		comboBoxReviewCount.addItem("=, >, <");
		comboBoxReviewCount.addItem("=");
		comboBoxReviewCount.addItem(">");
		comboBoxReviewCount.addItem("<");
		panelUsers.add(comboBoxReviewCount);
		//add value review count
		labelReviewCountValue = new JLabel("Value:");
		labelReviewCountValue.setFont(Gui.TAHOMA_FONT_12);
		labelReviewCountValue.setPreferredSize(new Dimension(LABEL_USERS_VALUE_WIDTH, LABEL_USERS_VALUE_HEIGHT));
		labelReviewCountValue.setHorizontalAlignment(SwingConstants.RIGHT);
		panelUsers.add(labelReviewCountValue);
		//add value text review count
		textReviewCountValue = new JTextField();
		textReviewCountValue.setPreferredSize(new Dimension(TEXT_USERS_VALUE_WIDTH, TEXT_USERS_VALUE_HEIGHT));
		textReviewCountValue.setBorder(BorderFactory.createLineBorder(Color.black));
		panelUsers.add(textReviewCountValue);	
		/*
		 * 第三行
		 */		
		//add label number of friends
		labelNumFriends = new JLabel("Number of Friends");
		labelNumFriends.setFont(Gui.TAHOMA_FONT_12);
		labelNumFriends.setPreferredSize(new Dimension(LABEL_USERS_TITLE_WIDTH, LABEL_USERS_TITLE_HEIGHT));
		panelUsers.add(labelNumFriends);
		//add comboBox number of friends
		comboBoxNumFriends = new JComboBox();
		comboBoxNumFriends.setFont(Gui.TAHOMA_FONT_12);
		comboBoxNumFriends.setPreferredSize(new Dimension(COMBOX_USERS_WIDTH, COMBOX_USERS_HEIGHT));
		comboBoxNumFriends.addItem("=, >, <");
		comboBoxNumFriends.addItem("=");
		comboBoxNumFriends.addItem(">");
		comboBoxNumFriends.addItem("<");
		panelUsers.add(comboBoxNumFriends);
		//add value number of friends
		labelNumFriendsValue = new JLabel("Value:");
		labelNumFriendsValue.setFont(Gui.TAHOMA_FONT_12);
		labelNumFriendsValue.setPreferredSize(new Dimension(LABEL_USERS_VALUE_WIDTH, LABEL_USERS_VALUE_HEIGHT));
		labelNumFriendsValue.setHorizontalAlignment(SwingConstants.RIGHT);
		panelUsers.add(labelNumFriendsValue);
		//add value text number of friends
		textNumFriendsValue = new JTextField();
		textNumFriendsValue.setPreferredSize(new Dimension(TEXT_USERS_VALUE_WIDTH, TEXT_USERS_VALUE_HEIGHT));
		textNumFriendsValue.setBorder(BorderFactory.createLineBorder(Color.black));
		panelUsers.add(textNumFriendsValue);			
		/*
		 * 第四行
		 */
		//add label average stars
		labelAvgStars= new JLabel("Average Stars");
		labelAvgStars.setFont(Gui.TAHOMA_FONT_12);
		labelAvgStars.setPreferredSize(new Dimension(LABEL_USERS_TITLE_WIDTH, LABEL_USERS_TITLE_HEIGHT));
		panelUsers.add(labelAvgStars);
		//add comboBox average stars
		comboBoxAvgStars = new JComboBox();
		comboBoxAvgStars.setFont(Gui.TAHOMA_FONT_12);
		comboBoxAvgStars.setPreferredSize(new Dimension(COMBOX_USERS_WIDTH, COMBOX_USERS_HEIGHT));
		comboBoxAvgStars.addItem("=, >, <");
		comboBoxAvgStars.addItem("=");
		comboBoxAvgStars.addItem(">");
		comboBoxAvgStars.addItem("<");
		panelUsers.add(comboBoxAvgStars);
		//add value average stars
		labelAvgStarsValue = new JLabel("Value:");
		labelAvgStarsValue.setFont(Gui.TAHOMA_FONT_12);
		labelAvgStarsValue.setPreferredSize(new Dimension(LABEL_USERS_VALUE_WIDTH, LABEL_USERS_VALUE_HEIGHT));
		labelAvgStarsValue.setHorizontalAlignment(SwingConstants.RIGHT);
		panelUsers.add(labelAvgStarsValue);
		//add value text average stars
		textAvgStarsValue = new JTextField();
		textAvgStarsValue.setPreferredSize(new Dimension(TEXT_USERS_VALUE_WIDTH, TEXT_USERS_VALUE_HEIGHT));
		textAvgStarsValue.setBorder(BorderFactory.createLineBorder(Color.black));
		panelUsers.add(textAvgStarsValue);			
		/*
		 * 第五行
		 */
		//add label number of votes
		labelNumVotes = new JLabel("Number of Votes");
		labelNumVotes.setFont(Gui.TAHOMA_FONT_12);
		labelNumVotes.setPreferredSize(new Dimension(LABEL_USERS_TITLE_WIDTH, LABEL_USERS_TITLE_HEIGHT));
		panelUsers.add(labelNumVotes);
		//add comboBox number of votes
		comboBoxNumVotes = new JComboBox();
		comboBoxNumVotes.setFont(Gui.TAHOMA_FONT_12);
		comboBoxNumVotes.setPreferredSize(new Dimension(COMBOX_USERS_WIDTH, COMBOX_USERS_HEIGHT));
		comboBoxNumVotes.addItem("=, >, <");
		comboBoxNumVotes.addItem("=");
		comboBoxNumVotes.addItem(">");
		comboBoxNumVotes.addItem("<");
		panelUsers.add(comboBoxNumVotes);
		//add value number of votes
		labelNumVotesValue = new JLabel("Value:");
		labelNumVotesValue.setFont(Gui.TAHOMA_FONT_12);
		labelNumVotesValue.setPreferredSize(new Dimension(LABEL_USERS_VALUE_WIDTH, LABEL_USERS_VALUE_HEIGHT));
		labelNumVotesValue.setHorizontalAlignment(SwingConstants.RIGHT);
		panelUsers.add(labelNumVotesValue);
		//add value text number of votes
		textNumVotesValue = new JTextField();
		textNumVotesValue.setPreferredSize(new Dimension(TEXT_USERS_VALUE_WIDTH, TEXT_USERS_VALUE_HEIGHT));
		textNumVotesValue.setBorder(BorderFactory.createLineBorder(Color.black));
		panelUsers.add(textNumVotesValue);
		
		comboBoxUsersSelect = new JComboBox();
		comboBoxUsersSelect.setFont(Gui.TAHOMA_FONT_12);
		comboBoxUsersSelect.setPreferredSize(new Dimension(COMBOX_USERS_SELECTION_WIDTH, COMBOX_USERS_SELECTION_HEIGHT));
		comboBoxUsersSelect.addItem("Select AND, OR between attributes");
		comboBoxUsersSelect.addItem("AND");
		comboBoxUsersSelect.addItem("OR");
		panelUsers.add(comboBoxUsersSelect);	
	}
	
	private void initResultField() {
		//labe of result
		labelResult = new JLabel("Result");
		labelResult.setFont(Gui.TAHOMA_FONT_16);
		labelResult.setBounds(LABEL_RESULT_OFFSET_X, LABEL_RESULT_OFFSET_Y,
				LABEL_RESULT_WIDTH, LABEL_RESULT_HEIGHT);
		labelResult.setHorizontalAlignment(SwingConstants.CENTER);
		labelResult.setBackground(Color.lightGray);
		labelResult.setOpaque(true);
		labelResult.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(labelResult);
		//panel of result
		panelResult = new JPanel();
		panelResult.setLayout(new FlowLayout());
		panelResult.setBounds(PANEL_RESULT_OFFSET_X, PANEL_RESULT_OFFSET_Y, 
				PANEL_RESULT_WIDTH, PANEL_RESULT_HEIGHT);
		panelResult.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.getContentPane().add(panelResult);
	}
	
	private void initShowQueryField() {
		textShowQuery = new JTextArea("< Show Query Here: >");
		textShowQuery.setFont(TAHOMA_FONT_14);
		textShowQuery.setBounds(TEXT_QUERY_OFFSET_X, TEXT_QUERY_OFFSET_Y,
				TEXT_QUERY_WIDTH, TEXT_QUERY_HEIGHT);
		textShowQuery.setOpaque(true);
		textShowQuery.setLineWrap(true);
		textShowQuery.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.getContentPane().add(textShowQuery);
	}
	
	private void initExecuteQueryButton() {
		buttonExecuteQuery = new JButton("Execute Query");
		buttonExecuteQuery.setFont(TAHOMA_FONT_18);
		buttonExecuteQuery.setBackground(CORN_FLOWER_BLUE);
		buttonExecuteQuery.setForeground(Color.white);
		buttonExecuteQuery.setBorder(BorderFactory.createRaisedBevelBorder());
		buttonExecuteQuery.setBounds(BUTTON_EXECUTE_OFFSET_X, BUTTON_EXECUTE_OFFSET_Y,
				BUTTON_EXECUTE_WIDTH, BUTTON_EXECUTE_HEIGHT);		
		frame.getContentPane().add(buttonExecuteQuery);
		buttonExecuteQuery.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selectCategoryClause = null;
				if(selectedCategory.size()!=0) {
					selectCategoryClause = Gui.getSelectBusinessItemsClause(selectedCategory);
				}
				
				String selectSubCategoryClause = null;
				if(selectedSubCategory.size() != 0) {
					selectSubCategoryClause = Gui.getSelectBusinessItemsClause(selectedSubCategory);
				}
				
				String selectAttributeClause = null;
				if(selectedAttribute.size() != 0) {
					selectAttributeClause = Gui.getSelectBusinessItemsClause(selectedAttribute);
				}
				
				String selectBusinessItemQuery = null;
				if(selectSubCategoryClause != null && selectCategoryClause != null &&
						!selectSubCategoryClause.isEmpty() && !selectCategoryClause.isEmpty() &&
						selectAttributeClause != null && !selectAttributeClause.isEmpty()){
					selectBusinessItemQuery = "select DISTINCT business_id from Category where " + selectCategoryClause + " INTERSECT " +  
							"select DISTINCT business_id from SubCategory where " + selectSubCategoryClause + " INTERSECT " + 
							"select DISTINCT business_id from BusinessAttribute where " + selectAttributeClause;
					//System.out.println(selectBusinessItemQuery);
				}
				
				String reviewDateFrom = datePickerReviewFrom.getJFormattedTextField().getText();
				String reviewDateTo = datePickerReviewTo.getJFormattedTextField().getText();
				
				String reviewStarCompare = comboBoxReviewStar.getSelectedItem().toString();
				String reviewsSarValue = textReviewStarValue.getText();;
				
				String reviewVotesCompare = comboBoxReviewVotes.getSelectedItem().toString();
				String reviewVotesValue = textReviewVotesValue.getText();
				
				String selectReviewDate = null;
				if(!reviewDateFrom.isEmpty() && !reviewDateTo.isEmpty()){
					selectReviewDate = Gui.selectReviewDate(reviewDateFrom, reviewDateTo);
				}
				
				String selectReviewStarsClause = null;
				if(!reviewStarCompare.equals("=, >, <") && !reviewsSarValue.isEmpty()){
					selectReviewStarsClause = Gui.selectReviewStars(reviewsSarValue, reviewStarCompare);
				}
				String selectReviewVotesClause = null;
				if(!reviewVotesCompare.equals("=, >, <") && !reviewVotesValue.isEmpty()){
					selectReviewVotesClause = Gui.selectReviewVotes(reviewVotesValue, reviewVotesCompare);
				}
				
				String selectReviewQuery = null;
				if(selectReviewStarsClause != null && selectReviewVotesClause != null){
					selectReviewQuery = "SELECT DISTINCT business_id from Review WHERE " + selectReviewDate + 
							" AND " + selectReviewStarsClause + " AND " + selectReviewVotesClause;
				}
				System.out.println(selectReviewQuery);
										
				String memberSince = datePickerMemberSince.getJFormattedTextField().getText();
				String reviewCountCompare = comboBoxReviewCount.getSelectedItem().toString();
				String reviewCountValue = textReviewCountValue.getText();
				String numFriendsCompare = comboBoxNumFriends.getSelectedItem().toString();
				String numFriendsValue = textNumFriendsValue.getText();
				String avgStarsCompare = comboBoxAvgStars.getSelectedItem().toString();
				String avgStarsValue = textAvgStarsValue.getText();
				String numVotesCompare = comboBoxNumVotes.getSelectedItem().toString();
				String numVotesValue = textNumVotesValue.getText();
				
				String userSelectedAndOr = " " + comboBoxUsersSelect.getSelectedItem().toString() + " ";
				
				String memberSinceClause = null;
				if(!memberSince.isEmpty()){
					memberSinceClause = Gui.selectUserSince(memberSince);
				}
				
				String reviewCountClause = null;
				if(!reviewCountCompare.equals("=, >, <") && !reviewCountValue.isEmpty()){
					reviewCountClause = Gui.selectReviewCount(reviewCountValue, reviewCountCompare);
				}
				
				String numFriendsClause = null;
				if(!numFriendsCompare.equals("=, >, <") && !numFriendsValue.isEmpty()){
					numFriendsClause = Gui.selectFriendsInfo(numFriendsValue, numFriendsCompare);
				}
				
				String avgStarsClause = null;
				if(!avgStarsCompare.equals("=, >, <") && !avgStarsValue.isEmpty()){
					avgStarsClause = Gui.selectAvgStar(avgStarsValue, avgStarsCompare);
				}		
				
				String numVotesClause = null;
				if(!numVotesCompare.equals("=, >, <") && !numVotesCompare.isEmpty()) {
					numVotesClause = Gui.selectNumVotes(numVotesValue, numVotesCompare);
				}
				String selectUserQuery = null;
				if(!userSelectedAndOr.equals(" Select AND, OR between attributes ")) {
					selectUserQuery = "select review_count, friends, average_stars, votes from YelpUser where " 
							+ memberSinceClause + userSelectedAndOr 
							+ reviewCountClause + userSelectedAndOr 
							+ numFriendsClause + userSelectedAndOr 
							+ avgStarsClause + userSelectedAndOr + numVotesClause;
					System.out.println(selectUserQuery);
				}
				
				String query = null;
				String queryPrefix = null;
				if(selectUserQuery != null) {
					query = selectUserQuery;
				} else {
					queryPrefix = "select business_id as BIDD, name as Business, city as City, state as State, "
							+ "stars as Star from Business where business_id IN ";
					if(selectBusinessItemQuery != null) {
						query = queryPrefix + "(" + selectBusinessItemQuery + ")";
					}
					if(selectReviewQuery != null) {
						if(query != null) {
							query = queryPrefix + "(" + selectReviewQuery + ")" + " INTERSECT " + query;
						}else{
							query = queryPrefix + "(" + selectReviewQuery + ")";
						}
					}
				}
				System.out.println("aaa " + query);
				textShowQuery.setText(query);
				//query = "select yelping_since, review_count, friends, average_stars, votes from YelpUser where review_count > 300 AND friends > 20AND average_stars > 4 AND votes > 50";
				try{
					statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(query);
					
					ResultSetMetaData rsmd = resultSet.getMetaData();
					int columnCount = rsmd.getColumnCount();
					
					ArrayList<String> columnNames = new ArrayList<String>();
					for (int i = 1; i <= columnCount; i++ ) {
						columnNames.add(rsmd.getColumnName(i));
					}
					
					Model model = new Model(columnNames);
					while(resultSet.next()) {
						ArrayList<String> record = new ArrayList<String>();
						for (int i = 1; i <= columnCount; i++ ) {
							record.add(resultSet.getString(i));
						}
						model.addRow(record);
					}
					
					tableResult = new JTable(model);
					tableResult.addMouseListener(new MouseAdapter() {
					    public void mousePressed(MouseEvent event) {
					    	JTable table =(JTable) event.getSource();
					        Point p = event.getPoint();
					        int row = table.rowAtPoint(p);
					        if (event.getClickCount() == 2) {
					        	System.out.println(row);
					        	String id = (String)table.getValueAt(row, 0);
					        	System.out.println(id);
					        	
					        	String queryReview = null;
					        	String firstColumnName = tableResult.getModel().getColumnName(0);
					        	System.out.println(firstColumnName);
					        								if(firstColumnName != null && (firstColumnName.equals("USERID"))) {
					        		queryReview = "select user_id as UserName, business_id as BusinessName, review_date as PublishDate, votes as Vote from Review where user_id=\'" + id + "\'";
					        	} else {
					        		queryReview = "select user_id as UserName, business_id as BusinessName, review_date as PublishDate, votes as vote from Review where business_id=\'" + id + "\'";
					        	}

					        	System.out.println("Review querying: " + queryReview);
					    		ResultSet rsReview = null;
					    		try {
					    			statement = connection.createStatement();
									rsReview = statement.executeQuery(queryReview);
						    		ResultSetMetaData rsmd = rsReview.getMetaData();
									int columnCount = rsmd.getColumnCount();
									StringBuilder sb = new StringBuilder();
									while(rsReview.next()) {
										for (int i = 1; i <= columnCount; i++ ) {
											sb.append(rsmd.getColumnName(i) + ": ");
											sb.append(rsReview.getString(i));
											sb.append("\n");
										}
										sb.append("\n");
										sb.append("--------------------------------------------");
										sb.append("\n");
									}
									
						        	JDialog dialog = new JDialog();
									JTextArea textArea = new JTextArea(sb.toString());
									textArea.setLineWrap(true);
									textArea.setWrapStyleWord(true);
									JScrollPane scrollPane = new JScrollPane(textArea);
									
									dialog.setLocationRelativeTo(null);
																dialog.setTitle("Dialog");
									dialog.getContentPane().add(scrollPane);
									dialog.pack();
									dialog.setVisible(true);
									dialog.setBounds(400, 200, 600, 400);
									
					    		} catch (SQLException e) {
									e.printStackTrace();
								}
					        }
					    }
					});
					
					tableResult.setAutoResizeMode(JTable.WIDTH);
					tableResult.setFillsViewportHeight(true);
					tableResult.setPreferredScrollableViewportSize(new Dimension(500, 70));
					JScrollPane scrollPaneResult = new JScrollPane(tableResult);
					scrollPaneResult.setBounds(PANEL_RESULT_OFFSET_X, PANEL_RESULT_OFFSET_Y, PANEL_RESULT_WIDTH, PANEL_RESULT_HEIGHT);
					frame.getContentPane().add(scrollPaneResult);
						
				} catch(SQLException E) {
					
				}
			}
		});
	
}
			


	
	private void initClearQueryButton() {
		buttonClearQuery = new JButton("Clear Query");
		buttonClearQuery.setFont(TAHOMA_FONT_18);
		buttonClearQuery.setBackground(CORN_FLOWER_BLUE);
		buttonClearQuery.setForeground(Color.white);
		buttonClearQuery.setBorder(BorderFactory.createRaisedBevelBorder());
		buttonClearQuery.setBounds(BUTTON_CLEAR_OFFSET_X, BUTTON_CLEAR_OFFSET_Y,
				BUTTON_CLEAR_WIDTH, BUTTON_CLEAR_HEIGHT);
		frame.getContentPane().add(buttonClearQuery);
		buttonClearQuery.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Business Field
				for(JCheckBox cb : categoryCheckBoxGroup) {
					cb.setSelected(false);
				}
				
				for(JCheckBox subcb : subCategoryCheckBoxGroup) {
					panelSubCategory.remove(subcb);
				}
				
				for(JCheckBox acb : attributeCheckBoxGroup) {
					panelAttribute.remove(acb);
				}
				comboBoxBusinessSelect.setSelectedIndex(0);
				panelSubCategory.revalidate();
				panelSubCategory.repaint();
				panelAttribute.revalidate();
				panelAttribute.repaint();
				
				subCategoryCheckBoxGroup.clear();
				attributeCheckBoxGroup.clear();
				selectedCategory.clear();
				selectedSubCategory.clear();
				selectedAttribute.clear();
				//review field
				comboBoxReviewStar.setSelectedIndex(0);
				comboBoxReviewVotes.setSelectedIndex(0);
				textReviewStarValue.setText("");
				textReviewVotesValue.setText("");
				datePickerReviewFrom.getJFormattedTextField().setText("");
				datePickerReviewTo.getJFormattedTextField().setText("");
				//Users field		
				datePickerMemberSince.getJFormattedTextField().setText("");				
				comboBoxReviewCount.setSelectedIndex(0);
				comboBoxNumFriends.setSelectedIndex(0);
				comboBoxAvgStars.setSelectedIndex(0);
				comboBoxNumVotes.setSelectedIndex(0);
				
				textMemberSinceValue.setText("");
				textReviewCountValue.setText("");
				textNumFriendsValue.setText("");
				textAvgStarsValue.setText("");
				textNumVotesValue.setText("");
				
				comboBoxUsersSelect.setSelectedIndex(0);
				//text area	
				textShowQuery.setText("< Show Query Here: >");
				frame.revalidate();
				frame.repaint();
			}
			
		});
	}
	
	public static Connection getConection(){
		Connection connection = null;
		try {
			System.out.println("Open Oracle Database Connection...");
			connection = openConnection();
		} catch(SQLException e) {
			System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
		} catch(ClassNotFoundException e) {
			System.err.println("Cannot find the database driver"); 
		} 
		return connection;
	}
		
	public static Connection openConnection() throws SQLException, ClassNotFoundException {
		System.out.println("Open Oracle Database DriverManager...");
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD );
	}
	
	
	public static void closeConnection(Connection connection) { 
		try { 
			System.out.println("Close Oracle Database Connection...");
			connection.close(); 
		} catch (SQLException e) { 
			System.err.println("Cannot close connection: " + e.getMessage()); 
		} 
	}
	
	public static HashSet<String> getSubCategoryList(List<String> selectedCategory) throws SQLException{
		statement = connection.createStatement();
		String query = "";
		if(selectedCategory.size() >= 1) {
			query = "select DISTINCT S.name from SubCategory S, Category C where S.business_id = C.business_id AND C.name=\'" + selectedCategory.get(0) + "\'";
		} 
		for(int i = 1; i < selectedCategory.size(); i++){
			query = "select DISTINCT S.name from SubCategory S, Category C where S.business_id = C.business_id AND C.name=\'" + selectedCategory.get(i) + "\' \nINTERSECT " + query ;
		}
		HashSet<String> result = new HashSet<String>();
		if(!query.isEmpty()) {
			//System.out.println(query + "\n");
			ResultSet resultSet = statement.executeQuery(query);		
			JDialog dialog = new JDialog();
			JLabel label = new JLabel("Query Finished");
			dialog.setLocationRelativeTo(null);
			dialog.setTitle("Dialog");
			dialog.add(label);
			dialog.pack();			
			while(resultSet.next()) {
				result.add(resultSet.getString(1));
			}			
		}
		return result;
	}
	
	public static HashSet<String> getAttributeList(List<String> selectedCategory, List<String>selectedSubCategory) throws SQLException {
		statement = connection.createStatement();
		String query = "";
		if(selectedSubCategory.size() >= 1) {
			query = "select DISTINCT B.name from BusinessAttribute B, SubCategory S, Category C"
					+ " where B.business_id = S.business_id AND S.business_id = C.business_id"
					+ " AND C.name = \'" + selectedCategory.get(0) + "\'"
					+ " AND S.name = \'" + selectedSubCategory.get(0) + "\'";
		} 
		for(int i = 1; i < selectedSubCategory.size(); i++){
			query = "select DISTINCT B.name from BusinessAttribute B, SubCategory S, Category C"
					+ " where B.business_id = S.business_id AND S.business_id = C.business_id"
					+ " AND C.name = \'" + selectedCategory.get(i)
					+ " AND S.name = \'" + selectedSubCategory.get(i)
					+ "\' \nINTERSECT " + query ;
		}
		HashSet<String> result = new HashSet<String>();
		if(!query.isEmpty()) {
			//System.out.println(query + "\n");
			ResultSet resultSet = statement.executeQuery(query);		
			JDialog dialog = new JDialog();
			JLabel label = new JLabel("Query Finished");
			dialog.setLocationRelativeTo(null);
			dialog.setTitle("Dialog");
			dialog.add(label);
			dialog.pack();			
			while(resultSet.next()) {
				result.add(resultSet.getString(1));
			}			
		}
		return result;
	}
	
	public static String getSelectBusinessItemsClause(List<String> selectedList) {
		String selectBusinessItemsClause = "";
		if(selectedList.size() >= 1) {
			selectBusinessItemsClause = "name = \'" + selectedList.get(0)+"\'";
		}
		for(int i=1; i<selectedList.size();i++){
			selectBusinessItemsClause = "name = \'" + selectedList.get(i)+ "\' AND " + selectBusinessItemsClause;
		}
		return selectBusinessItemsClause;
	}
	
	public static String selectReviewDate(String date1, String date2){
		String selectReviewDate = "review_date > to_date(\'"+date1+"\',\'YYYY-MM-DD\') AND review_date < to_date(\'"+date2+"\',\'YYYY-MM-DD\')"; 
		return selectReviewDate;
	}

	public static String selectReviewStars(String star, String starCompare){
		String selectReviewStars = "stars " + starCompare + " " + Integer.valueOf(star);
		return selectReviewStars;
	}

	public static String selectReviewVotes(String vote, String voteCompare){
		String selectReviewVotes = "votes " + voteCompare + " " + vote;
		return selectReviewVotes;
	}
	// for users query
	public static String selectUserSince(String sinceDate){
		String selectUserSince = "yelping_since > to_date(\'" + sinceDate + "\',\'YYYY-MM-DD\')";
		return selectUserSince;
	}

	public static String selectReviewCount(String value, String valueCompare){
		String selectReviewCount = "review_count " + valueCompare + " " + value;
		return selectReviewCount;
	}

	public static String selectFriendsInfo(String value, String valueCompare){
		String selectFriendsInfo = "friends " + valueCompare + " " + value;
		return selectFriendsInfo;
	}

	public static String selectAvgStar(String value, String valueCompare){
		String selectAvgStar = "average_stars " + valueCompare + " " + value;
		return selectAvgStar;
	}
	
	public static String selectNumVotes(String value, String valueCompare)  {
		String selectNumVotes = "votes " + valueCompare + " " + value;
		return selectNumVotes;
	}
	
	public static class Model extends AbstractTableModel {
		private List<String> columnNames = new ArrayList();
		private List<List> data = new ArrayList();
		
		public Model(List<String> names) {
			for(String name : names) {
				columnNames.add(name);
			}
		}

		public void addRow(List rowData) {
			data.add(rowData);
			fireTableRowsInserted(data.size() - 1, data.size() - 1);
		}

		public int getColumnCount() {
			return columnNames.size();
		}

		public int getRowCount() {
			return data.size();
		}

		public String getColumnName(int col) {
			try {
				return columnNames.get(col);
			} catch (Exception e) {
				return null;
			}
		}

		public Object getValueAt(int row, int col) {
			return data.get(row).get(col);
		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
	};
}














