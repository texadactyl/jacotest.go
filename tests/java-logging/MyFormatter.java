import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
		// The result is a newline-terminated String value.
        String result = String.format("%s :: %03d :: %s.%s :: %s :: %s\n", 
        	record.getLevel().toString(),
        	record.getSequenceNumber(),
               	record.getSourceClassName(),
               	record.getSourceMethodName(),
               	new Date(record.getMillis()),
               	record.getMessage() );
        return result;
    }

}
