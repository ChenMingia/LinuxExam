import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@WebServlet(urlPatterns = "/QueryBook")
public class QueryBook extends HttpServlet {
   final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   final static String URL = "jdbc:mysql://123.60.47.94/linux_final";
   final static String USER = "root";
   final static String PASS = "09010914Chen@";
   final static String SQL_QURERY_ALL_BOOK = "SELECT * FROM t_book;";
   Connection conn = null;

     public void init() {
	      try {
	         Class.forName(JDBC_DRIVER);
	         conn = DriverManager.getConnection(URL, USER, PASS);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

	   public void destroy() {
	      try {
	         conn.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

	   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      response.setContentType("application/json");
	      response.setCharacterEncoding("UTF-8");
	      PrintWriter out = response.getWriter();

	      List<Book> stuList = getAllBook();
	      Gson gson = new Gson();
	      String json = gson.toJson(stuList, new TypeToken<List<Book>>() {
	      }.getType());
	      out.println(json);
	      out.flush();
	      out.close();
	   }

	   private List<Book> getAllBook() {
	      List<Book> stuList = new ArrayList<Book>();
	      Statement stmt = null;
	      try {
	         stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(SQL_QURERY_ALL_BOOK);
	         while (rs.next()) {
	            Book stu = new Book();
	            stu.id = rs.getInt("id");
	            stu.name = rs.getString("name");
	            stu.author = rs.getString("author");
	            stuList.add(stu);
	         }
	         rs.close();
	         stmt.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (stmt != null)
	               stmt.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }

	      return stuList;
	   }
}

 class Book {
   public  int id;
   public String name;
   public String author;
}
