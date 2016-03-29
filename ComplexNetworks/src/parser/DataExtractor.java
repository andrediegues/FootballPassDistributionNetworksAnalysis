package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class DataExtractor {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// download all pdfs from url reference
		Scanner in = new Scanner(System.in);
		System.out.println("Introduce the URL from which you want to download all .pdf files:");
		URL url = new URL(in.next());
		// saving the name of each .pdf to a file
		System.out.println("Introduce a name for the txt file to be saved (omit the '.txt' extension):");
		String filename = in.next();
		String path = "/home/andre/Documents/Uefa/" + filename;
		File file = new File(path + ".txt");
		// creates a file if doesn't exists
		if(!file.exists()){
			file.createNewFile();
		}
		// reads url html code and transforms into the link to download converting it to .txt format
		URLConnection connection = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String line;
		while((line = br.readLine()) != null){
			if(line.contains("tpd.pdf")){
				for(int i = 0; i < line.length()-7; i++){
					if(line.substring(i, i+7).equals("tpd.pdf")){
						String urlPdf = "www.uefa.org" + line.substring(i - 28, i + 7);
						String command = "wget -P /home/andre/Documents/Uefa/ " + urlPdf;
						Process p = Runtime.getRuntime().exec(command);
						p.waitFor();
						String pdfname = urlPdf.substring(32);
						Process q = Runtime.getRuntime().exec("pdftotext /home/andre/Documents/Uefa/" + pdfname);
						q.waitFor();
						//System.out.println(q.exitValue());	
						bw.write(pdfname.substring(0, 11) + ".txt");
						bw.append("\n");
					}
				}
			}
		}
		bw.close();
		br.close();
		in.close();
		System.out.println("Done");
	}

}
