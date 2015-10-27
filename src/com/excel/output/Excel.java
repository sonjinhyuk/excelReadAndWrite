package com.excel.output;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Date;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

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
//				HttpSession session = request.getSession();
//				String id = (String) session.getAttribute("id"); 아이디 따서 폴더 생성함
				path += "\\id";//paht += "\\"+id;
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
//			HttpSession session = request.getSession();
//			String id = (String) session.getAttribute("id"); 아이디로 저장
			Calendar cal = Calendar.getInstance();
			long todayMil = cal.getTimeInMillis();
			File f = new File("D:\\tempFolder");//Path path = Paths.get("D:\\tempFolder\\" + id);
			File[] list = f.listFiles();
			String filePaht = "";
			Date fileDate = null;
			Date latestDate = null;
			String etx;
			int index;
			String fileName = "";
			int fileIndex = 0;
			for( int i = 0; i < list.length; i++ ){
				fileName = list[i].getName();
				index = fileName.lastIndexOf(".");
				if( index == -1 ){
					continue;
				}
				etx = fileName.substring(index+1);
				if( etx.equals("xls") || etx.equals("xlsx") ){
					fileDate = new Date(list[i].lastModified());
					if( latestDate == null ){
						latestDate = fileDate;
						fileIndex = i;
						fileName = list[i].getName();
					}
					else {
						if( latestDate.getTime() < fileDate.getTime() ){
							fileName = list[i].getName();
						} else {
							fileName = list[fileIndex].getName();
						}
					}
				}
			}
			JSONArray excelData = edao.dataAccept("D:\\tempFolder\\" + filePaht, column.length);//아이디어필요 시간으로 해결함.
			pw.print(edao.insertDB(column,excelData));
		} 
		pw.flush();
		pw.close();
	}
}
