package com.regularCheck;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegularCheck
 */
@WebServlet("/RegularCheck.do")
public class RegularCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegularCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=euc-kr");
		String op = request.getParameter("op");
		PrintWriter pw = response.getWriter();
		RegularCheckDAO rdao = new RegularCheckDAO();
		if( op.equals("searchCo") ){
			pw.println(rdao.getCo());
		} else if( op.equals("searchPlant") ){//현재는 회사랑 같이 해놓았음
			pw.println(rdao.getCo());
		} else if( op.equals("searchGenerator") ){
			pw.println(rdao.getPlant(request.getParameter("data")));
		} else if( op.equals("searchClass") ){
			String index = request.getParameter("data");
			String generatorCode = request.getParameter("generatorCode");
			int level = Integer.parseInt(index) + 2;
			System.out.println(generatorCode);
			pw.print(rdao.getLevelPlant(generatorCode, level));
		} else if( op.equals("checkUp")){
			String data = request.getParameter("data");
			Priority p = new Priority();
			pw.println(p.calPrioString(data));
		}
		pw.flush();pw.close();
	}

}
