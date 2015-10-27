package util;

import java.io.File;
import java.io.IOException;

import com.oreilly.servlet.multipart.FileRenamePolicy;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class MyFileRenamePolicy implements FileRenamePolicy{
	@Override
	public File rename(File f){
		
		String name = f.getName();
		String body = null;
		String ext = null;
		
		int dot = name.lastIndexOf(".");
		if( dot != -1 ){
			body = name.substring(0,dot);
			ext = name.substring(dot);
		} 
		f.delete();
		String tempName = "tempExcel" + ext;
		f = new File(f.getParent(), tempName );
		if( createNewFile(f) ){
			return f;
		} else {
			f.delete();
			return f;
		}
		
		
	}

	private boolean createNewFile(File f) {
		// TODO Auto-generated method stub
		try{
			return f.createNewFile();
		} catch ( IOException e ){
			return false;
		}
	}
}
