// Hacked from https://www.geeksforgeeks.org/java-program-to-read-and-print-all-files-from-a-zip-file/

// Importing input output classes
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
 
public class main {

	final static String IN_ZIP_FILE = "input.zip";
 
 	static String[][] zipTable = { // file name, uncompressed size, uncompressed CRC
			{"0.main.log",		"1161",		"1216827134"},
			{"1.biostar.log",	"2257",		"1550240362"},
			{"1.pizerow.log",	"9502",		"3844065707"},
			{"1.rpi4.log",		"2139",		"2050768053"},
			{"2.pizerow.log",	"1744",		"1380747039"},
			{"2.rpi2.log",		"9598",		"3929933469"},
			{"2.zotac.log",		"17364",	"3963511932"},
			{"3.rpi2.log",		"1778",		"277903632"},
			{"3.rpi3.log",		"4492",		"1929418946"},
			{"3.rpi4.log",		"175008",	"1706934587"},
			{"4.rpi3.log",		"20006",	"2491861642"},
			{"4.rpi4.log",		"1961",		"1267000413"},
			{"4.zotac.log",		"10885",	"1887076250"},
			{"5.zotac.log",		"1831",		"1064895305"},
			{"remcmd.cfg",		"192",		"2638084272"},
			{"remdown.cfg",		"393",		"921907977"},
			{"remping.cfg",		"189",		"4082986205"},
			{"remupd.cfg",		"325",		"2386648222"},
			{"remx_cfg.py",		"3541",		"1411510599"},
			{"remx_main.py",	"4089",		"4086809879"},
			{"remx_utilities.py", "6019",	"2295793381"}
        }; // 21 rows

	public static int lookup(String name, String size, String crc) {
		boolean found = false;
		int ix = -1;
		int returnCode = 0;
		for (int ii = 0; ii < 21; ++ii) {
			if (name.equals(zipTable[ii][0])) {
				found = true;
				ix = ii;
				break;
			}
		}
		if (! found) {
			System.out.printf("ERROR :: name %s not found\n", name);
			return 1;
		}
		if (! size.equals(zipTable[ix][1])) {
			System.out.printf("ERROR :: For name %s, expected size = %s but observed %s\n", name, zipTable[ix][1], size);
			returnCode = 1;
		}
		if (! crc.equals(zipTable[ix][2])) {
			System.out.printf("ERROR :: For name %s, expected crc = %s but observed %s\n", name, zipTable[ix][2], crc);
			returnCode = 1;
		}
		return returnCode;
	}
	
    public static void main(String[] args) throws IOException {
 
        FileInputStream fs = null;
        ZipInputStream Zs = null;
        ZipEntry ze = null;
        int errorCount = 0;
 
        // Try to read zip file.
        try {
 
            System.out.println("Directory of the zip is as follows:");
 
            fs = new FileInputStream(IN_ZIP_FILE);
            Zs = new ZipInputStream(new BufferedInputStream(fs));
 
            // Loop to read and print the zip file name till
            // the end
            while ((ze = Zs.getNextEntry()) != null) {
            	if (ze.isDirectory())
                	System.out.println("\t" + ze.getName());
                else {
                	Path path = Paths.get(ze.getName());
                	String fileName = path.getFileName().toString();
                	String size = String.valueOf(ze.getSize());
                	String crc = String.valueOf(ze.getCrc());
                	errorCount += lookup(fileName, size, crc);
                	System.out.printf("\t\t%20s  %10s  %s\n", fileName, size, crc);
                }
            }
 
            Zs.close();
        }
 
        catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
 
        catch (IOException ie) {
            ie.printStackTrace();
        }
        
        if (errorCount == 0) {
            System.out.println("All entries have been verified");
        } else {
            System.out.print("Number of errors = ");
            System.out.println(errorCount);
            System.exit(1);
        }
        
    }
 
}


