import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.google.gson.reflect.TypeToken;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/UpdateBook")
public class UpdateBook extends HttpServlet {
   final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   final static String URL = "jdbc:mysql://123.60.47.94/linux_final";
   final static String USER = "root";
   final static String PASS = "09010914Chen@";
   final static String SQL_UPDATE_BOOK = "UPDATE t_book SET name = ? , author = ? WHERE id=?";

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      Book req = getRequestBody(request);
      PrintWriter out = response.getWriter();

      out.println(updateBook(req));
      out.flush();
      out.close();
   }

   private Book getRequestBody(HttpServletRequest request) throws IOException {
      Book stu = new Book();
      StringBuffer bodyJ = new StringBuffer();
      String line = null;
      BufferedReader reader = request.getReader();
      while ((line = reader.readLine()) != null)
 	bodyJ.append(line);
      Gson gson = new Gson();
      stu = gson.fromJson(bodyJ.toString(), new TypeToken<Book>() {
      }.getType());
      return stu;
   }

   private int updateBook(Book req) {
      Connection conn = null;
      PreparedStatement stmt = null;
      int retcode = -1;
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(URL, USER, PASS);
         stmt = conn.prepareStatement(SQL_UPDATE_BOOK);

         stmt.setString(1, req.name);
         stmt.setString(2, req.author);
	 stmt.setInt(3,req.id);

         int row = stmt.executeUpdate();
         if (row > 0)
            retcode = 1;

         stmt.close();
         conn.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               stmt.close();
            if (conn != null)
               conn.close();
  	} catch (SQLException se) {
            se.printStackTrace();
         }
      }
      return retcode;
   }
public class Book {
    int id;
    String name;
    String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
 }
}
}
                     
