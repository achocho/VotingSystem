package voting2;

import java.sql.*;
import java.util.*;

import voting.Vote;

public class Counter {

public void Count() throws Exception
{
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "vote";
	String driver = "com.mysql.cj.jdbc.Driver";
	String userName = "root";
	String password = "";
	Class.forName(driver).newInstance();
	conn = DriverManager.getConnection(url+dbName,userName,password);
	Statement stmt = conn.createStatement();
	String sql = "SELECT * from votes";
	ResultSet rs = stmt.executeQuery(sql);
	List<Integer> vote=new ArrayList<>();
	while(rs.next()) 
	{
		vote.add(rs.getInt(3));
		
	}
	int[][] vot=Votes(vote);
	System.out.println("Voted for :");
	System.out.println();
	System.out.println("--------------------------");
	for (int i = 0; i < vot.length; i++) {

		if (vot[i].length != 1) {
			System.out.println("Option " + vot[i][0] + " :" + vot[i].length + " votes");
		} else {
			System.out.println("Option " + vot[i][0] + " :" + vot[i].length + " vote");

		}
		System.out.println("--------------------------");

	}
	Winner(vot);
}
public static int[][] Votes(List<Integer> list) {
	List<Integer> vote = new ArrayList<>();

	for (int i = 0; i < list.size(); i++) {
		vote.add(list.get(i));

	}
	int buff = 0;
	for (int i = 0; i < vote.size() - 1; i++) {
		for (int j = 0; j < vote.size() - 1; j++) {
			int vote1 = vote.get(j);
			int vote2 = vote.get(j + 1);
			if (vote1 > vote2) {
				buff = vote1;
				vote.set(j, vote2);
				vote.set(j + 1, buff);

			}

		}

	}

	int size = 1;
	for (int i = 0; i < vote.size() - 1; i++) {

		if (vote.get(i) != vote.get(i + 1)) {
			size++;

		}

	}
	int size1 = 0;
	int temCurr = vote.get(0);
	int temSize = 0;
	int[] sizes = new int[size];
	int s3 = 0;
	while (temSize < vote.size()) {
		if (temCurr == vote.get(temSize)) {
			temSize++;
			size1++;
			sizes[s3] = size1;
		} else {
			size1 = 0;
			s3++;
			temCurr = vote.get(temSize);
		}
	}

	int currSize = 0;
	int[][] eq = new int[size][];
	for (int i = 0; i < eq.length; i++) {
		eq[i] = new int[sizes[i]];

	}
	int s1 = 0;
	int s2 = 0;
	int curr = vote.get(0);
	while (currSize < vote.size()) {
		if (curr == vote.get(currSize)) {

			eq[s1][s2] = vote.get(currSize);

			currSize++;
			s2++;
		} else {
			s2 = 0;
			s1++;

			curr = vote.get(currSize);
		}
	}

	return eq;

}
public static void Winner(int[][] vot) 
{
	int[] sizes = new int[vot.length];
	int[] els = new int[vot.length];
	for (int i = 0; i < vot.length; i++) {
		sizes[i] = vot[i].length;
		els[i] = vot[i][0];
	}

	int buff = 0;
	int buff1 = 0;
	for (int i = 0; i < sizes.length - 1; i++) {
		for (int j = 0; j < sizes.length - 1; j++) {
			int l1 = sizes[j];
			int l2 = sizes[j + 1];
			if (l1 > l2) {
				buff1 = els[j];
				els[j] = els[j + 1];
				els[j + 1] = buff1;
				buff = l1;
				sizes[j] = l2;
				sizes[j + 1] = buff;

			}

		}

	}
	int len1 = 0;
	for (int i = 0; i < sizes.length; i++) {
		if (len1 < sizes[i]) {
			len1 = sizes[i];

		}

	}
	int Min = 0;
	for (int i = 0; i < sizes.length; i++) {
		if (len1 == sizes[i]) {
			Min = i;
			
			break;
		}

	}
	int Max = 0;
	for (int i = Min; i < sizes.length - 1; i++) {
		if (sizes[i] == sizes[i + 1]) {
			Max = i;

		}

	}

	boolean rem = false;

	if (len1 > sizes[Max]) {
		rem = false;

	} else if (len1 == sizes[Max] && sizes.length > 1) {
		rem = true;

	}
	List<Integer> equals = new ArrayList<>();

	if (rem) {
		for (int i = Min; i < vot.length; i++) {
			equals.add(els[i]);

		}
		System.out.print("Equality between: ");
		for (int i = 0; i < equals.size() - 1; i++) {
			System.out.print(equals.get(i) + "-");

		}
		System.out.print(equals.get(equals.size() - 1));
		if(len1!=1) {
		System.out.print(" with " + len1 + " votes");
		}else 
		{
			System.out.print(" with " + len1 + " vote");
		}
	} else {
		if(len1!=1) {
		System.out.println("The winner is " + els[els.length - 1] + " with " + len1 + " votes");
		}else 
		{
			System.out.println("The winner is " + els[els.length - 1] + " with " + len1 + " vote");
			
		}
	}	
}
}
