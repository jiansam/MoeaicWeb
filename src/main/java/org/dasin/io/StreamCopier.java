package org.dasin.io;

import java.io.*;

public class StreamCopier {
	public static void copy(InputStream in, OutputStream out){
		byte[] buffer = new byte[1024*64];
		int len;
		
		try{
			while((len = in.read(buffer)) != -1){
				out.write(buffer, 0, len);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		buffer = null;
	}
}