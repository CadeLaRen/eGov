<%@ page language="java" import="java.sql.*,org.egov.infstr.utils.HibernateUtil" %>

	<%

	Connection con=null;
	ResultSet rs=null;
	Statement stmt=null;
	String values = "";
	StringBuffer result = new StringBuffer();
	StringBuffer type=new StringBuffer();
	StringBuffer chartOfAccounts_ID=new StringBuffer();
	StringBuffer chartOfAccounts_name=new StringBuffer();
	StringBuffer chartOfAccounts_parentId = new StringBuffer();
	StringBuffer chartOfAccounts_glCode = new StringBuffer();
	try
	{
	con = null;//HibernateUtil.getCurrentSession();
	stmt=con.createStatement();
	}
	catch(Exception e)
	{
	throw new Exception("Not able to get a connection");
	}



	String parentId = request.getParameter("parentId");
	
	String query;
	if(parentId==null)
	{
	
	 query=" SELECT '' AS \"type\", ID AS \"chartOfAccounts_ID\", name AS \"chartOfAccounts_name\", parentId AS \"chartOfAccounts_parentId\", glcode AS \"chartOfAccounts_glCode\" FROM  chartOfAccounts where parentId is null order by id asc";
	}
	else
	{
	 query=" SELECT '' AS \"type\", ID AS \"chartOfAccounts_ID\", name AS \"chartOfAccounts_name\", parentId AS \"chartOfAccounts_parentId\", glcode AS \"chartOfAccounts_glCode\" FROM  chartOfAccounts where parentId ="+parentId+" order by id asc";
	
	}
	

	int i = 0;
	try
	{
	if(query != "")
	{
	rs=stmt.executeQuery(query);

		while(rs.next()){

		if(i > 0)
		{
		type.append("+");
		type.append(rs.getString(1));
		chartOfAccounts_ID.append("+");
		chartOfAccounts_ID.append(rs.getString(2));
		chartOfAccounts_name.append("+");
		chartOfAccounts_name.append(rs.getString(3));
		chartOfAccounts_parentId.append("+");
		chartOfAccounts_parentId.append(rs.getString(4));
		chartOfAccounts_glCode.append("+");
		chartOfAccounts_glCode.append(rs.getString(5));

		}
		else
		{
		type.append(rs.getString(1));
		chartOfAccounts_ID.append(rs.getString(2));
		chartOfAccounts_name.append(rs.getString(3));
		chartOfAccounts_parentId.append(rs.getString(4));
		chartOfAccounts_glCode.append(rs.getString(5));

		}
		i++;

		}

	result.append(type);
	result.append("^");
	result.append(chartOfAccounts_ID);
	result.append("^");
	result.append(chartOfAccounts_name);
	result.append("^");
	result.append(chartOfAccounts_parentId);
	result.append("^");
	result.append(chartOfAccounts_glCode);
	result.append("^");


	values=result.toString();
	}
	}


	catch(Exception e)
	{
			
	}

	response.setContentType("text/xml;charset=utf-8");
	response.setHeader("Cache-Control", "no-cache");
	response.getWriter().write(values);
	%>