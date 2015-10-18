package com.excel.output;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import com.oreilly.servlet.MultipartRequest;
import com.sun.jmx.snmp.Enumerated;

import util.MyFileRenamePolicy;

/**
 * Servlet implementation class Excel
 */
@WebServlet("/Excel.do")
public class Excel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Excel() {
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
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=euc-kr");
		String op = request.getParameter("op");
		ExcelDAO edao = new ExcelDAO();
		PrintWriter pw = response.getWriter();
		if( op.equals("fileInput") ){
			JSONArray returndata = null;
//			String cp = request.getContextPath();
//			String root = pageContext.getServletContext().getRealPath("/");
			// 웹서버에 접근해서 다운로드 받을 수 있게 하기 위해 위치 지정을 이렇게.!
			
			
			String path = "D:\\tempFolder";
			File dir = new File(path); // 디렉토리 위치 지정
			if (!dir.exists()) { // 디렉토리가 존재하지 않으면
				dir.mkdirs(); // 디렉토리 생성.!
			}

			// 업로드된 파일에 대한 처리
			String enctype = "UTF-8";
			// 파일 이름 한글이 깨질 시에는
			// String enctype = "EUC-KR";
			int maxFileSize = 15 * 1024 * 1024; // 5Mbytes로 업로드 파일 용량 제한
			try {
				MultipartRequest req = null;
				req = new MultipartRequest(request, path // 물리적으로 저장될 위치
						, maxFileSize, enctype, new MyFileRenamePolicy());
				String fileNameElemnt = (String) req.getFileNames().nextElement();
				String fileName = req.getFilesystemName(fileNameElemnt);
				String filePath = path + "\\" + fileName;
				int pos = fileName.lastIndexOf( "." );
				String ext = fileName.substring( pos + 1 );
				if( ext.equals("xlsx") || ext.equals("xls")){
					returndata = edao.dataAccept(filePath,0);//첫행만 가져옴
				}else {
					File f = new File(filePath);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			pw.print(returndata);
		} else if( op.equals("init") ){
			JSONArray returndata = new JSONArray();
			returndata = edao.init();
			pw.print(returndata);
		} else if( op.equals("tableCoulmn")){
			JSONArray returndata = new JSONArray();
			String tableName = request.getParameter("tableName");
			returndata = edao.tableCoulmnList(tableName);
			pw.print(returndata);
		} else if( op.equals("insetDB") ){
			String columnDatas = request.getParameter("columnData");//,(comma)로 이어져 있음.
			String column[] = columnDatas.split(",");
			JSONArray excelData = edao.dataAccept("D:\\tempFolder\\tempExcel.xlsx", column.length);//아이디어필요
																								   //후보 1. 쿠키나 세션
																								   //후보 2. 전역변수
			pw.print(edao.insertDB(column,excelData));
		} else if( op.equals("grape") ){
			String type = request.getParameter("data");
			String period = request.getParameter("period");
			double calDate = 0;
			String endDate;
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
				pw.print(edao.getData(type,period,endDate,calDate));//타입과 만약 오늘기준 개월수라면 today, 특정 년도 월 일 이라면 앞에 인자는 년도, 뒤에 인자는 월 을 나타냄
			}
		}
		pw.flush();
		pw.close();
	}
}
