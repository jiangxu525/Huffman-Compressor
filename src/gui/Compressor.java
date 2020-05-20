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
		
		
		//create a random file name
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		Random random = new Random();
		int num = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		File file = new File(files[0].getParent()+ "\\"+num+str+".hzip");
		try (FileOutputStream fos = new FileOutputStream(file);ObjectOutputStream oos =new ObjectOutputStream(fos) ) {
			for (Map.Entry<File, byte[]> entry : inputMap.entrySet()) {
				Huffman huffman = new Huffman();
				byte[] zipBytes = huffman.huffmanZip(entry.getValue());
				oos.writeObject(entry.getKey().getName());
				oos.writeObject(huffman.getHuffmanCodes());
				oos.writeObject(zipBytes);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return file;
	}
	
	private static void outputUnzipFiles(File directory,String name, Map<Byte, String> huffmanCodes, byte[] zipbytes) {
		try (FileOutputStream fos = new FileOutputStream(directory+"\\"+name)){
			Huffman huffman = new Huffman();
			byte[] resBytes =huffman.unzip(huffmanCodes, zipbytes);
			fos.write(resBytes);
					
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static void unzipFiles(File file) {
		File directory = new File(file.getParent());
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
			Object obj = null;
			while((obj=ois.readObject())!=null) {
				if(obj instanceof String) {
					outputUnzipFiles(directory,(String)obj,(Map<Byte, String>)ois.readObject(),(byte[])ois.readObject());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 

	}

}
