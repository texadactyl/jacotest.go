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
 
 	static String[][] zipTable = {
			{"0.main.log",		"1161",		"2022-10-03T15:20:42Z"},
			{"1.biostar.log",	"2257",		"2021-02-19T00:06:02Z"},
			{"1.pizerow.log",	"9502",		"2022-10-03T14:58:02Z"},
			{"1.rpi4.log",		"2139",		"2020-07-02T16:55:14Z"},
			{"2.pizerow.log",	"1744",		"2021-02-19T00:06:54Z"},
			{"2.rpi2.log",		"9598",		"2022-10-03T14:52:32Z"},
			{"2.zotac.log",		"17364",	"2020-01-15T17:52:38Z"},
			{"3.rpi2.log",		"1778",		"2021-02-19T00:06:18Z"},
			{"3.rpi3.log",		"4492",		"2020-07-04T15:44:24Z"},
			{"3.rpi4.log",		"175008",	"2022-10-03T15:20:40Z"},
			{"4.rpi3.log",		"20006",	"2020-01-15T18:27:48Z"},
			{"4.rpi4.log",		"1961",		"2021-02-19T00:06:04Z"},
			{"4.zotac.log",		"10885",	"2022-10-03T14:53:20Z"},
			{"5.zotac.log",		"1831",		"2021-02-19T00:06:18Z"},
			{"remcmd.cfg",		"192",		"2021-02-21T17:11:46Z"},
			{"remdown.cfg",		"393",		"2021-02-21T17:11:56Z"},
			{"remping.cfg",		"189",		"2021-02-21T17:12:06Z"},
			{"remupd.cfg",		"325",		"2021-02-21T17:12:14Z"},
			{"remx_cfg.py",		"3541",		"2020-01-15T23:03:50Z"},
			{"remx_main.py",	"4089",		"2020-01-15T23:04:54Z"},
			{"remx_utilities.py", "6019",	"2020-01-15T23:07:22Z"}
        }; // 21 rows

	public static int lookup(String name, String size, String stamp) {
		boolean found = false;
		int ix = -1;
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
			return 1;
		}
		if (! stamp.equals(zipTable[ix][2])) {
			System.out.printf("ERROR :: For name %s, expected stamp = %s but observed %s\n", name, zipTable[ix][2], stamp);
			return 1;
		}
		return 0;
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
            Zs = new ZipInputStream(
                new BufferedInputStream(fs));
 
            // Loop to read and print the zip file name till
            // the end
            while ((ze = Zs.getNextEntry()) != null) {
            	if (ze.isDirectory())
                	System.out.println("\t" + ze.getName());
                else {
                	Path path = Paths.get(ze.getName());
                	String fileName = path.getFileName().toString();
                	String size = String.valueOf(ze.getSize());
                	String stamp = ze.getLastModifiedTime().toString();
                	errorCount += lookup(fileName, size, stamp);
                	System.out.printf("\t\t%20s  %10s  %s\n", fileName, size, stamp);
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


