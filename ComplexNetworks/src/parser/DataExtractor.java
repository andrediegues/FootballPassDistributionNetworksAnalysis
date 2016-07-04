package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class DataExtractor {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// download all pdfs from url reference
		int nMin = 2013;
		int nMax = 2017;
		int mMin = -2;
		int mMax = 14;
		for(int i = nMin; i < nMax; i++){
			System.out.println("The program is downloading all games available of the season " + (i-1) + "/" + i + "...");
			for(int j = mMin; j < mMax; j++){
				if(j == 0) continue;
				// saving the name of each .pdf to a file
				String filename = "games"+i+j;
				String path = "/home/andre/workspace/IIC/Uefa/" + filename;
				File file = new File(path + ".txt");
				// creates a file if doesn't exists
				if(!file.exists()){
					file.createNewFile();
				}
				getTxtFile(getUrl(i, j), file);
			}
		}
		System.out.println("Done");
	}
	
	public static URL getUrl(int i, int j) throws MalformedURLException{
		URL url;
		url = new URL("http://www.uefa.org/mediaservices/presskits/uefachampionsleague/season="+i+"/md="+j+"/index.html");
		return url;
	}
	
	public static void getTxtFile(URL url, File file) throws IOException, InterruptedException{
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
						String command = "wget -P /home/andre/workspace/IIC/Uefa/ " + urlPdf;
						Process p = Runtime.getRuntime().exec(command);
						p.waitFor();
						String pdfname = urlPdf.substring(32);
						Process q = Runtime.getRuntime().exec("pdftotext -raw /home/andre/workspace/IIC/Uefa/" + pdfname);
						q.waitFor();	
						bw.write(pdfname.substring(0, 11) + ".txt");
						bw.append("\n");
					}
				}
			}
		}
		bw.close();
		br.close();
	}
}
