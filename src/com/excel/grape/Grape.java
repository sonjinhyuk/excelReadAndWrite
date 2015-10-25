package com.excel.grape;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class Grape
 */
@WebServlet("/Grape.do")
public class Grape extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Grape() {
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
		String op = request.getParameter("op");
		PrintWriter pw = response.getWriter();
		GrapeDAO gdao;
		if( op.equals("grape") ){
			String type = request.getParameter("data");
			String period = request.getParameter("period");
			double calDate = 0;
			String endDate;
			type = "COP-A";
			if( type.trim().equals("") || period.trim().equals("") ) {
				JSONObject returndata = new JSONObject();
				returndata.put("success", "fail");
				pw.print(returndata);
			}
			else {
				//type 및 기간 설정
				if( period.equals("1") || period.equals("0.5") || period.equals("3") || period.equals("5")){
					endDate = "";
					calDate = Double.parseDouble(period);
					period = "today";
				} else{
					endDate = period.split(" ")[1];
					period = period.split(" ")[0];
				}
				gdao = new GrapeDAO();
				pw.print(gdao.getData(type,period,endDate,calDate));//타입과 만약 오늘기준 개월수라면 today, 특정 년도 월 일 이라면 앞에 인자는 년도, 뒤에 인자는 월 을 나타냄
			}
		}
		
		pw.flush();pw.close();
	}

}
