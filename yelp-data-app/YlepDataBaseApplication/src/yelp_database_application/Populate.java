package yelp_database_application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class YelpUser {
	
	String yelping_since;
	int votes;
	int review_count;
	String name;
	int friends;
	String user_id;
	double average_stars;
	
	public YelpUser(String yelping_since, int votes, int review_count, 
			String name, int friends, String user_id, double average_stars) {
		this.yelping_since = yelping_since;
		this.votes = votes;
		this.review_count = review_count;
		this.name = name;
		this.friends = friends;
		this.user_id = user_id;
		this.average_stars = average_stars;
	}	
}

class Business {
	
	String business_id;
	String city;
	String state;
	int review_count;
	String name;
	double stars;
	
	public Business(String business_id, String city, String state, 
			int review_count, String name, double stars) {
		this.business_id = business_id;
		this.city = city;
		this.state = state;
		this.review_count = review_count;
		this.name = name;
		this.stars = stars;
	}	
}

class Category {
	
	String business_id;
	String name;
	
	public Category(String business_id, String name) {
		this.business_id = business_id;
		this.name = name;
	}		
}
	
class SubCategory {
	
	String business_id;
	String name;
	
	public SubCategory(String business_id, String name) {
		this.business_id = business_id;
		this.name = name;
	}		
}

class Attribute {
	
	String business_id;
	String name;
	
	public Attribute(String business_id, String name) {
		super();
		this.business_id = business_id;
		this.name = name;
	}	
}

class Review {
	
	int votes;	
	String user_id;				
	String review_id;			
	int stars;				
	String review_date;				
	String business_id;
	
	public Review(int votes, String user_id, String review_id, 
			int stars, String date, String business_id) {
		this.votes = votes;
		this.user_id = user_id;
		this.review_id = review_id;
		this.stars = stars;
		this.review_date = date;
		this.business_id = business_id;
	}	
}

class BusinessTuple<Business, Category, SubCategory> {
	
	Business business;
	Category category;
	SubCategory subCategory;
	
	public BusinessTuple(Business business, Category category, SubCategory subCategory) {
		this.business = business;
		this.category = category;
		this.subCategory = subCategory;
	}			
}

public class Populate {
	
	public static void main(String[] args) {
		//init all the json files
		File businessFile = new File(args[0]);
		File reviewFile = new File(args[1]);
		File checkinFile = new File(args[2]);
		File yelpUserfile = new File(args[3]);
		//build the database connection
		Populate populate = new Populate();
		Connection connection = populate.getConection();
				
		ArrayList<YelpUser> UserList = new ArrayList<YelpUser>();
		ArrayList<Business> businessList = new ArrayList<Business>();
		ArrayList<Category> categoryList = new ArrayList<Category>();
		ArrayList<SubCategory> subCategoryList = new ArrayList<SubCategory>();
		ArrayList<Review> reviewList = new ArrayList<Review>();
		ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
		
		//parse data		
		UserList = populate.parseYlepUser(yelpUserfile);
		BusinessTuple<ArrayList<Business>, ArrayList<Category>, ArrayList<SubCategory>> businessTuple = populate.parseBusiness(businessFile);
		businessList = businessTuple.business;
		categoryList = businessTuple.category;
		subCategoryList = businessTuple.subCategory;
		attributeList = populate.parseAttributes(businessFile);
		reviewList = populate.parseReview(reviewFile);
		
		//insert data
		populate.insertYelpUser(connection, UserList);
		populate.insertBusiness(connection, businessList);
		populate.insertCategory(connection, categoryList);
		populate.insertSubCategory(connection, subCategoryList);
		populate.insertAttribute(connection, attributeList);
		populate.insertReview(connection, reviewList);	
		//close the connection
		populate.closeConnection(connection);
	}
	
	private ArrayList<String> getAttributeName(JSONObject josonAttributeObject, JSONArray jsonArrayCategory, final String prefix) {
		ArrayList<String> resultList = new ArrayList<String>();
		String s;
		for(int i = 0; i < jsonArrayCategory.length(); i++) {
			if(josonAttributeObject.opt(jsonArrayCategory.getString(i)) instanceof JSONObject ) {
				JSONObject josonObjectTemp = josonAttributeObject.getJSONObject(jsonArrayCategory.getString(i));
				JSONArray jsonArrayTemp = josonObjectTemp.names();
				if(jsonArrayTemp != null) {
					String prefixTemp = jsonArrayCategory.getString(i) + prefix;
					ArrayList<String> listTemp = getAttributeName(josonObjectTemp, jsonArrayTemp, prefixTemp);
					for(String ss: listTemp) {
						resultList.add(ss);
					}
				}
			} else if(josonAttributeObject.opt(jsonArrayCategory.getString(i)) instanceof Boolean) {
				if(prefix.equals("")) {
					s = jsonArrayCategory.getString(i) + "-"
							+ josonAttributeObject.getBoolean(jsonArrayCategory.getString(i));
					resultList.add(s);
				} else {
					s = prefix + "-" + jsonArrayCategory.getString(i) + "-"
							+ josonAttributeObject.getBoolean(jsonArrayCategory.getString(i));
					resultList.add(s);
				}				 
			} else if(josonAttributeObject.opt(jsonArrayCategory.getString(i)) instanceof Integer) {
				if(prefix.equals("")) {
					s = jsonArrayCategory.getString(i) + "-"
							+ josonAttributeObject.getInt(jsonArrayCategory.getString(i));
					resultList.add(s);
				} else {
					s = prefix + "-" + jsonArrayCategory.getString(i) + "-"
							+ josonAttributeObject.getInt(jsonArrayCategory.getString(i));				
					resultList.add(s);
				}				
			} else if(josonAttributeObject.opt(jsonArrayCategory.getString(i)) instanceof Double) {
				if(prefix.equals("")) {
					s = jsonArrayCategory.getString(i) + "-"
							+ josonAttributeObject.getDouble(jsonArrayCategory.getString(i));
					resultList.add(s);
				} else {
					s = prefix + "-" + jsonArrayCategory.getString(i) + "-"
							+ josonAttributeObject.getDouble(jsonArrayCategory.getString(i));				
					resultList.add(s);
				}				
			} else if(josonAttributeObject.opt(jsonArrayCategory.getString(i)) instanceof String) {
				if(prefix.equals("")) {
					s = jsonArrayCategory.getString(i) + "-"
							+ josonAttributeObject.getString(jsonArrayCategory.getString(i));
					resultList.add(s);
				} else {
					s = prefix + "-" + jsonArrayCategory.getString(i) + "-"
							+ josonAttributeObject.getString(jsonArrayCategory.getString(i));				
					resultList.add(s);
				}				
			}
		}
		return resultList;
	}
	
	public ArrayList<Attribute> parseAttributes(File file) {
		ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
		try {
			System.out.println(file.getName() + " parsing data...");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			do {
				line = bufferedReader.readLine();
				if(line != null) {
					ArrayList<String> result;
					JSONObject jsonObject = new JSONObject(line);
					JSONObject josonAttributeObject = jsonObject.getJSONObject("attributes");
					String business_id = jsonObject.getString("business_id");
					JSONArray jsonArrayCategory = josonAttributeObject.names();
					if(jsonArrayCategory != null) {
						String prefix = "";
						result = getAttributeName(josonAttributeObject, jsonArrayCategory, prefix);
						for(String sName: result) {
							Attribute attribute = new Attribute(business_id, sName);
							attributeList.add(attribute);
						}
					}					
				}									
			} while(line != null);
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(attributeList.size() + " Attributes parsed!");
		return attributeList;
	}
	
	public static final String HOST = "localhost";
	public static final String PORT = "1521";
	public static final String DB_NAME = "orcl";
	public static final String USER_NAME = "scott";
	public static final String PASSWORD = "tiger";
	public static final String DB_URL = "jdbc:oracle:thin:@" + HOST + ":" + PORT + ":" + DB_NAME;

	public ArrayList<YelpUser> parseYlepUser(File file) {
		ArrayList<YelpUser> UserList = new ArrayList<YelpUser>();
		try {
			System.out.println(file.getName() + " parsing data...");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			do {
				line = bufferedReader.readLine();
				if(line != null) {
					JSONObject jsonObject = new JSONObject(line);					
					String yelping_since = jsonObject.getString("yelping_since");							
					JSONObject jsonVotes = jsonObject.getJSONObject("votes");
					int votes = jsonVotes.getInt("funny") + jsonVotes.getInt("useful") + jsonVotes.getInt("cool");								
					int review_count = jsonObject.getInt("review_count");
					String name = jsonObject.getString("name");
					int friends = jsonObject.getJSONArray("friends").length();
					String user_id = jsonObject.getString("user_id");
					double average_stars = jsonObject.getDouble("average_stars");
					YelpUser user = new YelpUser(yelping_since, votes, review_count, name, friends, user_id, average_stars);
					UserList.add(user);
				}				
			} while(line != null);
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(UserList.size() + " YelpUsers parsed!");
		return UserList;	
	}
	
	public BusinessTuple<ArrayList<Business>, ArrayList<Category>, ArrayList<SubCategory>> parseBusiness(File file) {
		ArrayList<Business> businessList = new ArrayList<Business>();
		ArrayList<Category> categoryList = new ArrayList<Category>();
		ArrayList<SubCategory> subCategoryList = new ArrayList<SubCategory>();		
		try {
			System.out.println(file.getName() + " parsing data...");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			do {
				line = bufferedReader.readLine();
				if(line != null) {
					//init the business
					JSONObject jsonObject = new JSONObject(line);					
					String business_id = jsonObject.getString("business_id");
					String city = jsonObject.getString("city");
					String state = jsonObject.getString("state");
					int review_count = jsonObject.getInt("review_count");
					String name = jsonObject.getString("name");
					double stars = jsonObject.getDouble("stars");
					Business business = new Business(business_id, city, state, review_count, name, stars);
					businessList.add(business);
					//init the category
					JSONArray jsonArrayCategory = jsonObject.getJSONArray("categories");
					for(int i = 0; i < jsonArrayCategory.length(); i++) {
						String categoryName = jsonArrayCategory.getString(i);
						if(categoryName.equals("Active Life") || categoryName.equals("Arts & Entertainment") 
								|| categoryName.equals("Automotive") || categoryName.equals("Car Rental") 
								|| categoryName.equals("Cafes") || categoryName.equals("Beauty & Spas") 
								|| categoryName.equals("Convenience Stores") || categoryName.equals("Dentists") 
								|| categoryName.equals("Doctors") || categoryName.equals("Drugstores") 
								|| categoryName.equals("Department Stores") || categoryName.equals("Education") 
								|| categoryName.equals("Event Planning & Services") || categoryName.equals("Flowers & Gifts") 
								|| categoryName.equals("Food") || categoryName.equals("Health & Medical") 
								|| categoryName.equals("Home Services") || categoryName.equals("Home & Garden") 
								|| categoryName.equals("Hospitals") || categoryName.equals("Hotels & Travel") 
								|| categoryName.equals("Hardware Stores") || categoryName.equals("Grocery") 
								|| categoryName.equals("Medical Centers") || categoryName.equals("Nurseries & Gardening") 
								|| categoryName.equals("Nightlife") || categoryName.equals("Restaurants") 
								|| categoryName.equals("Shopping") || categoryName.equals("Transportation")) {					
							Category category = new Category(business_id, categoryName);
							categoryList.add(category);							
						} else {
							SubCategory subCategory = new SubCategory(business_id, categoryName);
							subCategoryList.add(subCategory);							
						}
					}
				}				
			} while(line != null);
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(businessList.size() + " All Businesses parsed!");
		System.out.println(categoryList.size() + " All Categories parsed!");
		System.out.println(subCategoryList.size() + " All SubCategories parsed!");
		return new BusinessTuple<ArrayList<Business>, ArrayList<Category>, ArrayList<SubCategory>>(businessList, categoryList, subCategoryList);
	}
		
	public ArrayList<Review> parseReview(File file) {
		ArrayList<Review> reviewList = new ArrayList<Review>();
		try {
			System.out.println(file.getName() + " parsing data...");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			do {
				line = bufferedReader.readLine();
				if(line != null) {
					JSONObject jsonObject = new JSONObject(line);
					JSONObject jsonVotes = jsonObject.getJSONObject("votes");
					int votes = jsonVotes.getInt("funny") + jsonVotes.getInt("useful") + jsonVotes.getInt("cool");					
					String user_id = jsonObject.getString("user_id");
					String review_id = jsonObject.getString("review_id");
					int stars = jsonObject.getInt("stars");
					String review_date = jsonObject.getString("date");
					String business_id = jsonObject.getString("business_id");
					Review review = new Review(votes, user_id, review_id, stars, review_date, business_id);
					reviewList.add(review);
				}				
			} while(line != null);
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(reviewList.size() + " Reviews parsed!");
		return reviewList;
	}
	
	public void insertYelpUser(Connection connection, ArrayList<YelpUser> UserList) {
		try {
			System.out.println("Inserting YelpUser...");
			String insertClause = "INSERT INTO YelpUser\n" +
							"(yelping_since, votes, review_count, name, friends, user_id, average_stars)\n" +
							"VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertClause);
			for (YelpUser yelpUser : UserList) {
				
				
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM"); 
				try {
					date = (Date) dateFormat.parse(yelpUser.yelping_since);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				insertStatement.setDate(1, new java.sql.Date(date.getTime()));
				insertStatement.setInt(2, yelpUser.votes);
				insertStatement.setInt(3, yelpUser.review_count);
				insertStatement.setString(4, yelpUser.name);
				insertStatement.setInt(5, yelpUser.friends);
				insertStatement.setString(6, yelpUser.user_id);
				insertStatement.setDouble(7, yelpUser.average_stars);
				insertStatement.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println("YelpUser - failed inserting!");
			e.printStackTrace();
		}
		System.out.println("YelpUser - inserted!");
	}
	
	public void insertBusiness(Connection connection, ArrayList<Business> businessList) {
		
		try {
			System.out.println("Inserting Business...");
			String insertClause = "INSERT INTO Business\n" +
							"(business_id, city, state, review_count, name, stars)\n" +
							"VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertClause);
			for (Business business : businessList) {
				insertStatement.setString(1, business.business_id);
				insertStatement.setString(2, business.city);
				insertStatement.setString(3, business.state);
				insertStatement.setInt(4, business.review_count);
				insertStatement.setString(5, business.name);
				insertStatement.setDouble(6, business.stars);
				insertStatement.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println("Business - failed inserting!");
			e.printStackTrace();
		}
		System.out.println("Business - inserted!");
	}
	
	public void insertCategory(Connection connection, ArrayList<Category> categoryList) {
		
		try {
			System.out.println("Inserting Category...");
			String insertClause = "INSERT INTO Category\n" +
							"(business_id, name)\n" +
							"VALUES (?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertClause);
			for (Category category : categoryList) {
				insertStatement.setString(1, category.business_id);
				insertStatement.setString(2, category.name);
				insertStatement.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println("Category - failed inserting!");
			e.printStackTrace();
		}
		System.out.println("Category - inserted!");
	}
	
public void insertAttribute(Connection connection, ArrayList<Attribute> attributeList) {
		
		try {
			System.out.println("Inserting BusinessAttribute...");
			String insertClause = "INSERT INTO BusinessAttribute\n" +
							"(business_id, name)\n" +
							"VALUES (?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertClause);
			for (Attribute attribute : attributeList) {
				insertStatement.setString(1, attribute.business_id);
				insertStatement.setString(2, attribute.name);
				insertStatement.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println("Attribute - failed inserting!");
			e.printStackTrace();
		}
		System.out.println("Attribute - inserted!");
	}
	
	public void insertSubCategory(Connection connection, ArrayList<SubCategory> subCategoryList) {
		
		try {
			System.out.println("Inserting SubCategory...");
			String insertClause = "INSERT INTO SubCategory\n" +
							"(business_id, name)\n" +
							"VALUES (?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertClause);
			for (SubCategory subCategory : subCategoryList) {
				insertStatement.setString(1, subCategory.business_id);
				insertStatement.setString(2, subCategory.name);
				insertStatement.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println("Category - failed inserting!");
			e.printStackTrace();
		}
		System.out.println("Category - inserted!");
	}
	
	public void insertReview(Connection connection, ArrayList<Review> reviewList) {
		
		try {
			System.out.println("Inserting Review...");
			String insertClause = "INSERT INTO Review\n" +
							"(votes, user_id, review_id, stars, review_date, business_id)\n" +
							"VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(insertClause);
			for (Review review : reviewList) {
				insertStatement.setInt(1, review.votes);
				insertStatement.setString(2, review.user_id);
				insertStatement.setString(3, review.review_id);
				insertStatement.setInt(4, review.stars);
				insertStatement.setDate(5, java.sql.Date.valueOf(review.review_date));
				insertStatement.setString(6, review.business_id);
				insertStatement.executeQuery();
			}
		} catch (SQLException e) {
			System.out.println("Review - failed inserting!");
			e.printStackTrace();
		}
		System.out.println("Review - inserted!");		
	}
	
	public Connection getConection(){
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
		
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		System.out.println("Open Oracle Database DriverManager...");
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD );
	}
	
	
	public void closeConnection(Connection connection) { 
		try { 
			System.out.println("Close Oracle Database Connection...");
			connection.close(); 
		} catch (SQLException e) { 
			System.err.println("Cannot close connection: " + e.getMessage()); 
		} 
	} 
		
}
