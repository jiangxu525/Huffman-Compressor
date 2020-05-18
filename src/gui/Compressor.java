package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import huffmancodecompress.Huffman;

public class Compressor {


	/*
	 * @param files: the files that need to be zipped
	 * @return convert the files to byte[] and store the input files in inputMap<File, byte[]>
	 */

	private static HashMap<File, byte[]> readFiles(File[] files) {
		HashMap<File, byte[]> inputMap = new HashMap<File, byte[]>();
		FileInputStream is = null;
		try {
			for (File f : files) {
				is = new FileInputStream(f);
				byte[] b = new byte[is.available()];
				is.read(b);
				inputMap.put(f, b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputMap;
	}
	
	public static File zipFiles(File[] files) {
		HashMap<File, byte[]> inputMap = readFiles(files);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		//create a random file name
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		Random random = new Random();
		int num = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		File file = new File(files[0].getParent()+ "\\"+num+str+".hzip");
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			for (Map.Entry<File, byte[]> entry : inputMap.entrySet()) {
				Huffman huffman = new Huffman();
				byte[] zipBytes = huffman.huffmanZip(entry.getValue());
				oos.writeObject(entry.getKey().getName());
				oos.writeObject(huffman.getHuffmanCodes());
				oos.writeObject(zipBytes);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	private static void outputUnzipFiles(File directory,String name, Map<Byte, String> huffmanCodes, byte[] zipbytes) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(directory+"\\"+name);
			Huffman huffman = new Huffman();
			byte[] resBytes =huffman.unzip(huffmanCodes, zipbytes);
			fos.write(resBytes);
					
		}catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

	@SuppressWarnings("unchecked")
	public static void unzipFiles(File file) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		File directory = new File(file.getParent());
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			Object obj = null;
			while((obj=ois.readObject())!=null) {
				if(obj instanceof String) {
					outputUnzipFiles(directory,(String)obj,(Map<Byte, String>)ois.readObject(),(byte[])ois.readObject());
				}
			}
			
			
//			String name = (String)ois.readObject();
//			Map<Byte, String> codes = (Map<Byte, String>) ois.readObject();
//			byte[] zipBytes = (byte[])ois.readObject();
//			System.out.println(name);
//			codes.entrySet().forEach(entry->{
//			    System.out.println(entry.getKey() + " " + entry.getValue());  
//			 });
//			Huffman huffman = new Huffman();
//			byte[] resBytes =huffman.unzip(codes, zipBytes);
//			System.out.println(new String(resBytes));

			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
