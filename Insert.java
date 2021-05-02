package voting2;
import java.sql.*;
import java.util.*;
public class Insert {

	public static void main(String[] args) throws Exception{
		System.out.println();
	Connection conn=conn();
	Scanner scan=new Scanner(System.in);
	Scanner scan1=new Scanner(System.in);
	String name="";
	String Egn="";
	int vote=0;
	System.out.println("Possible votes 1-12");
	System.out.println("---------------------");
	Counter cr=new Counter();
	System.out.println("Enter admin username and password(if you have)");
	System.out.print("username :");
	String username=scan.nextLine();
	System.out.print("password :");
	String password=scan.nextLine();
	boolean admin=AdminPanel(conn,username,password);
	if(!admin) 
	{
		System.out.println("The vote starts");
		System.out.println("---------------------");
	}
	while(true&&!admin&&CheckClosed(conn)) 
	{
		try {
		
		System.out.println("Enter name");
	name=scan.nextLine();
	System.out.println("Enter Egn");
	Egn=scan.nextLine();
	System.out.println("Enter vote");
	vote=scan1.nextInt();
	if(CheckVote(conn,Egn)&&CheckEGN(Egn)&&vote>0&&vote<13) 
	{
		InsertVote(conn,name,Egn,vote);
		System.out.println("Voted");
		
	}else 
	{
		System.out.println("The person already voted or the EGN is invalid or vote is below the minimum or above the maximum");
		continue;
	}
	String exit=scan1.nextLine();
	if(exit.equalsIgnoreCase("yes")) 
	{
		break;
		
	}
		}
		catch(Exception e) 
		{
			System.out.println("Enter valid information");
			
		}
	}
	if(!admin&&!CheckClosed(conn)) 
	{
		System.out.println("The vote is already closed!!!");
		cr.Count();
		
	}
	if(admin&&CheckClosed(conn)) 
	{
		System.out.println("Do you want to close the voting?");
		String answer=scan.nextLine();
		if(answer.equalsIgnoreCase("yes")) 
		{
			
		Close(conn);	
		}
		
	}else if(!CheckClosed(conn)&&admin) 
	{
		System.out.println("Do you want to open the vote?");
		String answer=scan.nextLine();
		if(answer.equalsIgnoreCase("yes")) 
		{
			Open(conn);
			
		}
		
	}
	System.out.println();
	System.out.println("---------------------------");
System.out.println("Do you want to continue");
String answer=scan.nextLine();
if(answer.equals("yes")) 
{
	main(args);
}
	}
public static Connection conn() throws Exception
{
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "vote";
	String driver = "com.mysql.cj.jdbc.Driver";
	String userName = "root";
	String password = "";
	Class.forName(driver).newInstance();
	conn = DriverManager.getConnection(url+dbName,userName,password);
	return conn;

}
public static void InsertVote(Connection conn,String name,String EGN,int vote) throws Exception
{
	PreparedStatement ps=conn.prepareStatement("insert into votes values(?,?,?,?)");
 
    
	ps.setString(1,name);
	ps.setString(2, EGN);
	ps.setInt(3, vote);
	ps.executeUpdate();	
}
public static boolean CheckVote(Connection conn,String EGN) throws Exception
{
	String sql1="Select Count(*) from votes Where EGN = "+"'"+EGN+"'";
	PreparedStatement statement = null;
    ResultSet resultSet = null;
    statement = conn.prepareStatement(sql1);
    resultSet = statement.executeQuery();
    int out=0;
    if(resultSet.next()) {
      out=resultSet.getInt(1);
 
  
    }
    if(out==0) 
    {
    	return true;
    	
    }
return false;
}
public static boolean CheckEGN(String EGN) 
{
	if(EGN.length()!=10) 
	{
		return false;
		
	}
	boolean checkMonth=false;
	boolean checkDay=false;
	boolean checkValidEGN=false;
	String month=EGN.substring(2,4);
	String days=EGN.substring(4,6);
int mon=Integer.parseInt(month);
int day=Integer.parseInt(days);
if(mon>40&&mon<53) 
{
	
	checkMonth=true;
}
if(day>0&&day<32) 
{
	
	checkDay=true;
}
int[] Egns=new int[EGN.length()];
for(int i=0;i<Egns.length;i++) 
{

	Egns[i]=EGN.charAt(i)-48;

}
int control=Egns[0]*2+Egns[1]*4+Egns[2]*8+Egns[3]*5+Egns[4]*10+Egns[5]*9+Egns[6]*7+Egns[7]*3+Egns[8]*6;
control=control%11;
if(control==10) 
{
	control=0;
}
if(control==Egns[9]) 
{
	checkValidEGN=true;
}
if(checkValidEGN&&checkMonth&&checkDay) 
{
	return true;
}
return false;
}
public static boolean AdminPanel(Connection conn,String username,String password) throws Exception 
{
	String sql1="Select Count(*) from adminpanel Where username = "+"'"+username+"'"+" And password ="+"'"+password+"'";
	PreparedStatement statement = null;
    ResultSet resultSet = null;
    statement = conn.prepareStatement(sql1);
    resultSet = statement.executeQuery();
    int out=0;
    if(resultSet.next()) {
      out=resultSet.getInt(1);
 
  
    }
    if(out>=1) 
    {
    	return true;
    	
    }
return false;
}
public static void Close(Connection conn) throws Exception
{
	PreparedStatement ps=conn.prepareStatement("insert into close values(?)");
 
  
	ps.setString(1, "yes");
	ps.executeUpdate();	
 
}
public static boolean CheckClosed(Connection conn) throws Exception
{
	String sql1="Select Count(*) from close Where close = 'yes'";
	PreparedStatement statement = null;
    ResultSet resultSet = null;
    statement = conn.prepareStatement(sql1);
    resultSet = statement.executeQuery();
    int out=0;
    if(resultSet.next()) {
      out=resultSet.getInt(1);
 
  
    }
    if(out==0) 
    {
    	return true;
    	
    }
return false;
}
public static void Open(Connection conn) throws Exception
{
	PreparedStatement ps1=null;
	  ps1=conn.prepareStatement("Delete from close;");
	   
	  

		ps1.executeUpdate();	
}
}

