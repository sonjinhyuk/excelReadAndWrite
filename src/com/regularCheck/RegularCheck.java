package com.regularCheck;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

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
			pw.print(rdao.getLevelPlant(generatorCode, level));
		} else if( op.equals("checkUp")){
			String data = request.getParameter("data");
			Priority p = new Priority();
			pw.println(p.calPrioString(data));
		} else if( op.equals("saveData") ){
			Priority p = new Priority();
			JSONArray json = new JSONArray();
			
			String co = request.getParameter("co");                               
			String plant = request.getParameter("plant");                               
			String generator = request.getParameter("generator");                               
			String checkName = request.getParameter("checkName");                               
			String level1 = request.getParameter("level1");                               
			String level2 = request.getParameter("level2");                               
			String level3 = request.getParameter("level3");                               
			String level4 = request.getParameter("level4");                               
			String level5 = request.getParameter("level5");                               
			String inputResultDate = request.getParameter("inputResultDate");                               
			String inputResultTime = request.getParameter("inputResultTime");                               
			String startDate = request.getParameter("startDate");                               
			String startTime = request.getParameter("startTime");                               
			String endDate = request.getParameter("endDate");                               
			String endTime = request.getParameter("endTime");                               
			String faultLocation = request.getParameter("faultLocation");                               
			String faultLocationContent = request.getParameter("faultLocationContent");                               
			String faultContent = request.getParameter("faultContent");                               
			String faultContentContent = request.getParameter("faultContentContent");                               
			String faultCause = request.getParameter("faultCause");                               
			String faultCauseContent = request.getParameter("faultCauseContent");                               
			String faultPrevention = request.getParameter("faultPrevention");                               
			String faultPreventionContent = request.getParameter("faultPreventionContent");                               
			String workContent = request.getParameter("workContent");                               
			String nextCheckReflection = request.getParameter("nextCheckReflection");                               
			json = p.calPrioString(workContent);
			//테이블을 몰라 넣기 직전까지 만듬 현재 무조건 true 반환
			pw.println(rdao.insertDataBase(co, plant, generator, checkName, level1, level2, level3, level4, level5, inputResultDate, inputResultTime, startDate, startTime, endDate, endTime, faultLocation, faultLocationContent, faultContent, faultContentContent, faultCause, faultCauseContent, faultPrevention, faultPreventionContent, workContent, nextCheckReflection, json));
		}
		pw.flush();pw.close();
	}

}
