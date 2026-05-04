/* reverse-complement
 * The Computer Language Benchmarks Game
 * https://salsa.debian.org/benchmarksgame-team/benchmarksgame/

 * contributed by Tao Lei
 */

import java.io.FileInputStream;
import java.util.zip.CRC32;

public class main {

    static final String FILE_INPUT = "sample.fasta";
    static final String transFrom = "ACGTUMRWSYKVHDBN";
    static final String transTo = "TGCAAKYWSRMBDHVN";
    static final byte[] transMap = new byte[128];
    static int LINE_WIDTH = 0;
    static byte[] data = new byte[1 << 20];
    static int size;
    static byte[] outputBuffer = new byte[65536];
    static int outputPos = 0;
    static CRC32 crc32 = new CRC32();

    static {
        for (int i = 0; i < transMap.length; i++) transMap[i] = (byte) i;
        for (int i = 0; i < transFrom.length(); i++) {
            char c = transFrom.charAt(i);
            transMap[c] = transMap[Character.toLowerCase(c)] = (byte) transTo.charAt(i);
        }
    }

    static byte[] buffer = new byte[65536];
    static FileInputStream fis;
    static int pos, limit;
    static int start, end;

    public static void main(String[] args) throws Exception {
        fis = new FileInputStream(FILE_INPUT);
        solve(fis);
        long ckvalue = crc32.getValue();
        int errorCount = Checkers.checker("CRC32 check value", ckvalue, 585220925L);
        Checkers.theEnd(errorCount);
    }

    static void solve(FileInputStream argFis) throws Exception {
        pos = limit = 0;
        outputPos = 0;
        resetData();
        while (nextLine()) {
            if (buffer[start] == '>') {
                finishData();
                write(buffer, start, pos - start);
            } else {
                appendLine();
            }
        }
        finishData();
        if (outputPos > 0) flushData();
        System.out.flush();
    }

    static int endPos() {
        for (int off = pos; off < limit; off++) {
            if (buffer[off] == '\n')
                return off;
        }
        return -1;
    }

    static boolean nextLine() throws Exception {
        for (; ; ) {
            end = endPos();
            if (end >= 0) {
                start = pos;
                pos = end + 1;
                if (buffer[end - 1] == '\r')
                    end--;
                while (buffer[start] == ' ') start++;
                while (end > start && buffer[end - 1] == ' ') end--;
                if (end > start)
                    return true;
            } else {
                if (pos > 0 && limit > pos) {
                    limit -= pos;
                    System.arraycopy(buffer, pos, buffer, 0, limit);
                    pos = 0;
                } else {
                    pos = limit = 0;
                }
                int r = fis.read(buffer, limit, buffer.length - limit);
                if (r < 0)
                    return false;
                limit += r;
            }
        }
    }

    static void flushData() throws Exception {
        System.out.write(outputBuffer, 0, outputPos);
        outputPos = 0;
    }

    static void prepareWrite(int len) throws Exception {
        if (outputPos + len > outputBuffer.length)
            flushData();
    }

    static void write(int argInt) throws Exception {
        outputBuffer[outputPos++] = (byte) argInt;
        crc32.update(argInt);
    }

    static void write(byte[] ba, int off, int len) throws Exception {
        prepareWrite(len);
        System.arraycopy(ba, off, outputBuffer, outputPos, len);
        outputPos += len;
        crc32.update(ba, off, len);
    }

    static void finishData() throws Exception {
        while (size > 0) {
            int len = Math.min(LINE_WIDTH, size);
            prepareWrite(len + 1);
            while (len-- != 0) {
                write(data[--size]);
            }
            write('\n');
        }
        resetData();
    }

    static void resetData() {
        LINE_WIDTH = 0;
        size = 0;
    }

    static void appendLine() throws Exception {
        int len = end - start;
        if (LINE_WIDTH == 0) LINE_WIDTH = len;
        if (size + len > data.length) {
            byte[] data0 = data;
            data = new byte[data.length * 2];
            System.arraycopy(data0, 0, data, 0, size);
        }
        for (int i = start; i < end; i++) {
            data[size++] = transMap[buffer[i]];
        }
    }

}
