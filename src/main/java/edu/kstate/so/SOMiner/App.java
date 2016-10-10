package edu.kstate.so.SOMiner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Arrays;
import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) {
		// iterate through a directory containing all split posts.xml files and call VTD parser for extracting attributes and values
		// CSVUtils is used to write title and body in text file
		
		App app = new App();
		ClassLoader classloader = app.getClass().getClassLoader();
		File dir = new File(classloader.getResource("posts/").getFile());
		if(dir.isDirectory()){
			String[] files = dir.list();
			int counter = 1001;
			String csvFileBaseName = "SO-";
			for(String filename : files){
				System.out.println("Now processing this file ==> "+filename);
				app.runVTDParser(new File(dir, filename), csvFileBaseName+counter+".txt");
				counter++;
			}
		}
	}
	
	private void runVTDParser(File xmlFile, String csvFile){
		try {
			FileWriter writer = new FileWriter(csvFile, true);
			String csv = "data.csv";
//		    CSVWriter csvWriter = new CSVWriter(writer);
//			File f = new File(
//					"/Users/sajid/MySpace/Research/stackoverflow-neo4j/extracted/Posts.xml");
			// counting child elements of parlist
			int count = 0;
			// counting child elements of parlist named "par"
			int par_count = 0;
			FileInputStream fis = new FileInputStream(xmlFile);
			byte[] b = new byte[(int) xmlFile.length()];
			fis.read(b);
			VTDGen vg = new VTDGen();
			vg.setDoc(b);
			vg.parse(true);
			VTDNav vn = vg.getNav();
			if (vn.matchElement("posts")) { // match "posts"
				// to first child named "row"
				if (vn.toElement(VTDNav.FC, "row")) {
					do {
						AutoPilot ap = new AutoPilot(vn);
						ap.selectAttr("*");
						int i = -1;
						StringBuilder sb = new StringBuilder();
						Post temp = new Post();
						while ((i = ap.iterateAttr()) != -1) {
							if("id".equalsIgnoreCase(vn.toString(i)))
								temp.setPostID(vn.toString(i+1));
						else if("posttypeid".equalsIgnoreCase(vn.toString(i)))
								temp.setPostTypeId(vn.toString(i+1));
							// i will be attr name, i+1 will be attribute value
							else if ("title".equalsIgnoreCase(vn.toString(i)))
								temp.setTitle(vn.toString(i + 1));
							else if ("body".equalsIgnoreCase(vn.toString(i)))
								temp.setBody(vn.toString(i + 1));
							else if("tags".equalsIgnoreCase(vn.toString(i)))
								temp.setTags(vn.toString(i+1));
							else if("AcceptedAnswerId".equalsIgnoreCase(vn.toString(i)))
								temp.setAcceptedAnswerId(vn.toString(i+1));
							else if("CreationDate".equalsIgnoreCase(vn.toString(i)))
								temp.setCreationDate(vn.toString(i+1));
							else if("Score".equalsIgnoreCase(vn.toString(i)))
								temp.setScore(vn.toString(i+1));
							else if("viewcount".equalsIgnoreCase(vn.toString(i)))
								temp.setViewCount(vn.toString(i+1));
							else
								continue;

							// System.out.println("attr name ==> "+vn.toString(i)
							// + "\t===attr value ==> "+vn.toString(i+1));
						}
						
						//we're only interested in question type posts, i.e. postTypeId = 1
						String REGEX = "<php>|<java>|<c#>|<asp.net>|<encryption>|<javascript>";
						boolean hasTag = temp.getTags().contains("php") ||
								temp.getTags().contains("java") ||
								temp.getTags().contains("c#") ||
								temp.getTags().contains("asp.net") ||
								temp.getTags().contains("encryption") ||
								temp.getTags().contains("javascript");
								
						if(temp.getPostTypeId().equals("1") && hasTag){
							String titleWithBody = temp.getTitle().concat(temp.getBody()).trim();
							CSVUtils.writeLine(writer,
									Arrays.asList(temp.getPostID(),temp.getCreationDate(), titleWithBody));
//											temp.getAcceptedAnswerId(),
//									temp.getCreationDate(),
//									temp.getScore(),
//									temp.getViewCount(),
//									temp.getTags()));
							//csvWriter.writeNext(Arrays.asList(temp.getPostID(),temp.getTitle(), temp.getBody()));
						}

					} while (vn.toElement(VTDNav.NS, "row")); // to next sibling
																// named
																// "row"

					writer.flush();
					writer.close();
				} else
					System.out.println(" no child element named 'row' ");
			} else
				System.out.println(" Root is not 'posts' ");
		} catch (ParseException e) {
			System.out.println(" XML file parsing error \n" + e);
		} catch (NavException e) {
			System.out.println(" Exception during navigation " + e);
		} catch (java.io.IOException e) {
			System.out.println(" IO exception condition" + e);
		}
	}
}
