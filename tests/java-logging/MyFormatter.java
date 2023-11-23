import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return
        	record.getLevel().toString()
        		+ " :: " + record.getSequenceNumber​()
               	+ " :: " + record.getSourceClassName()
               	+ "." + record.getSourceMethodName()
               	+ " :: " + new Date(record.getMillis())
               	+ String.format(" :: %s\n", record.getMessage());
    }

}
