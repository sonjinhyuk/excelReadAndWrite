package com.grape;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators.PrecedingIterator;

import ChartDirector.XYChart;

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
		response.setContentType("text/html; charset=euc-kr");
		String op = request.getParameter("op");
		PrintWriter pw = response.getWriter();
		GrapeDAO gdao;
		if( op.equals("grape") ){
			String type = request.getParameter("data");
			String period = request.getParameter("period");
			double calDate = 0;
			String endDate;
			type = "PAF";//COP-A�� �����ϴ�.
			if( type.trim().equals("") || period.trim().equals("") ) {
				JSONObject returndata = new JSONObject();
				returndata.put("success", "fail");
				pw.print(returndata);
			}
			else {
				//type �� �Ⱓ ����
				if( period.equals("1") || period.equals("0.5") || period.equals("3") || period.equals("5")){
					endDate = "";
					calDate = Double.parseDouble(period);
					period = "today";
				} else{
					endDate = period.split(" ")[1];
					period = period.split(" ")[0];
				}
				gdao = new GrapeDAO();
				XYChart c = gdao.getDataCharDirectorChart(type,period,endDate,calDate);
				String chartURL = c.makeSession(request, "chart1");
				JSONObject returndata = new JSONObject();
				String chartImageMap = c.getHTMLImageMap("", " ","title='Check Date' class=[{dataSetName}] value = '{xLabel}' onclick = popMsg(this);");
//				String chartImageMap = c.getHTMLImageMap("javascript:popMsg('the legend key [{dataSetName}]');", " ",
//			    "title='This legend key is clickable!'");
				System.out.println(chartImageMap);
				returndata.put("chart", chartURL);
				returndata.put("Image", chartImageMap);
				pw.print(returndata);//Ÿ�԰� ���� ���ñ��� ��������� today, Ư�� �⵵ �� �� �̶�� �տ� ���ڴ� �⵵, �ڿ� ���ڴ� �� �� ��Ÿ��
			}
		}
		
		pw.flush();pw.close();
	}

}
