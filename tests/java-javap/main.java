import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// javap written in Java
public class main {

    private static int errorCount = 0;

    // Control writing of analysis updates to stdout
    private static final boolean LOGGING = true;
    
    // Debug output control
    private static final boolean DEBUG = false;

    // Class file magic number
    private static final int CLASS_MAGIC = 0xCAFEBABE;

    // constant pool tags (JVM Spec 4.4)
    private static final int CONSTANT_UTF8 = 1;
    private static final int CONSTANT_INTEGER = 3;
    private static final int CONSTANT_FLOAT = 4;
    private static final int CONSTANT_LONG = 5;
    private static final int CONSTANT_DOUBLE = 6;
    private static final int CONSTANT_CLASS = 7;
    private static final int CONSTANT_STRING = 8;
    private static final int CONSTANT_FIELDREF = 9;
    private static final int CONSTANT_METHODREF = 10;
    private static final int CONSTANT_INTERFACE_METHODREF = 11;
    private static final int CONSTANT_NAME_AND_TYPE = 12;
    private static final int CONSTANT_METHOD_HANDLE = 15;
    private static final int CONSTANT_METHOD_TYPE = 16;
    private static final int CONSTANT_DYNAMIC = 17;
    private static final int CONSTANT_INVOKE_DYNAMIC = 18;
    private static final int CONSTANT_MODULE = 19;
    private static final int CONSTANT_PACKAGE = 20;

    public static void main(String[] args) throws IOException{
        String classfile = "";

        if (args.length == 0) {
            classfile = "hello.class";
        } else if (args.length == 1) {
            classfile = args[0];
        } else {
            classfile = parseArgs(args);
            if (classfile.isEmpty()) {
                showUsage();
                return;
            }
        }

        byte[] classBytes = read(classfile);
        analyze(classBytes);
        
        Checkers.theEnd(errorCount);
    }

    private static String parseArgs(String[] args) {
        return "";
    }

    private static byte[] read(String path) {
        byte[] classBytes = new byte[0];
        try {
            classBytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.print("*** IOException while reading file: ");
            System.out.println(path);
            System.exit(1);
        }
        return classBytes;
    }

    private static void showUsage() {
        System.out.println("open-source Java class disassembler (c) 2026 The Jacobin Team");
        System.out.println("Usage: javap [options] filename");
        System.out.println("       output is written to the console");
    }

    private static void analyze(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);

        int magic = reader.readU4();
        if (magic != CLASS_MAGIC) {
            System.out.println("invalid Java class file");
            return;
        }

        int minorVersion = reader.readU2();
        int majorVersion = reader.readU2();

        if (LOGGING) {
            String versionName = versionName(majorVersion);
            if (!versionName.isEmpty()) {
                System.out.printf("Java version: %s (minor %d, major %d)%n", versionName, minorVersion, majorVersion);
            }
        }

        try {
            if (DEBUG) System.out.printf("=== analyze try: bytes.length=%d)%n", bytes.length);
            ConstantPoolEntry[] constantPool = readConstantPool(reader);
            if (DEBUG) System.out.println("=== analyze try: ConstantPoolEntry[] constantPool = readConstantPool(reader) ... ok");

            if (LOGGING) {
                printConstantPool(constantPool);
                if (DEBUG) System.out.println("=== analyze try: printConstantPool(constantPool) ... ok");
            }

            int accessFlags = reader.readU2();
            int thisClass = reader.readU2();
            int superClass = reader.readU2();

            if (LOGGING) {
                System.out.printf("access flags: 0x%04x (%s)%n", accessFlags, decodeClassAccessFlags(accessFlags));
                System.out.printf("this class:   #%d // %s%n", thisClass, resolveClassName(constantPool, thisClass));
                if (superClass != 0) {
                    System.out.printf("super class:  #%d // %s%n", superClass, resolveClassName(constantPool, superClass));
                }
            }

            int interfaceCount = reader.readU2();
            if (LOGGING) {
                System.out.printf("interfaces: %d%n", interfaceCount);
            }
            for (int i = 0; i < interfaceCount; i++) {
                int interfaceIndex = reader.readU2();
                if (LOGGING) {
                    System.out.printf("  #%d // %s%n", interfaceIndex, resolveClassName(constantPool, interfaceIndex));
                }
            }

            int fieldsCount = reader.readU2();
            if (LOGGING) {
                System.out.printf("fields: %d%n", fieldsCount);
            }
            for (int i = 0; i < fieldsCount; i++) {
                readMember(reader, constantPool, "field");
            }

            int methodsCount = reader.readU2();
            if (LOGGING) {
                System.out.printf("methods: %d%n", methodsCount);
            }
            for (int i = 0; i < methodsCount; i++) {
                readMember(reader, constantPool, "method");
            }

            int classAttributesCount = reader.readU2();
            if (LOGGING) {
                System.out.printf("class attributes: %d%n", classAttributesCount);
            }
            for (int i = 0; i < classAttributesCount; i++) {
                readAttribute(reader, constantPool, 2);
            }
        } catch (RuntimeException e) {
            System.out.println("*** RuntimeException: malformed or truncated class file");
            throw e;
        }
    }

    private static String versionName(int majorVersion) {
        String versionName = "";

        if (majorVersion < 55) {
            versionName = "pre-JDK 11";
        } else if (majorVersion == 55) {
            versionName = "JDK 11";
        } else if (majorVersion == 56) {
            versionName = "JDK 12";
        } else if (majorVersion == 57) {
            versionName = "JDK 13";
        } else if (majorVersion == 58) {
            versionName = "JDK 14";
        } else if (majorVersion == 59) {
            versionName = "JDK 15";
        } else if (majorVersion == 60) {
            versionName = "JDK 16";
        } else if (majorVersion == 61) {
            versionName = "JDK 17";
        } else if (majorVersion == 62) {
            versionName = "JDK 18";
        } else if (majorVersion == 63) {
            versionName = "JDK 19";
        } else if (majorVersion == 64) {
            versionName = "JDK 20";
        } else if (majorVersion == 65) {
            versionName = "JDK 21";
        } else if (majorVersion == 66) {
            versionName = "JDK 22";
        } else if (majorVersion == 67) {
            versionName = "JDK 23";
        } else if (majorVersion == 68) {
            versionName = "JDK 24";
        } else if (majorVersion == 69) {
            versionName = "JDK 25";
        } else if (majorVersion == 70) {
            versionName = "JDK 26";
        }

        return versionName;
    }

    // ---- constant pool parsing ----

    private static ConstantPoolEntry[] readConstantPool(ClassReader reader) {
        int constantPoolCount = reader.readU2();
        ConstantPoolEntry[] pool = new ConstantPoolEntry[constantPoolCount];

        for (int i = 1; i < constantPoolCount; i++) {
            int tag = reader.readU1();
            ConstantPoolEntry entry = new ConstantPoolEntry();
            entry.tag = tag;

            switch (tag) {
                case CONSTANT_UTF8:
                    entry.utf8Value = reader.readUtf8();
                    break;
                case CONSTANT_INTEGER:
                    entry.intValue = reader.readU4();
                    break;
                case CONSTANT_FLOAT:
                    entry.floatValue = Float.intBitsToFloat(reader.readU4());
                    break;
                case CONSTANT_LONG: {
                    long high = reader.readU4() & 0xFFFFFFFFL;
                    long low = reader.readU4() & 0xFFFFFFFFL;
                    entry.longValue = (high << 32) | low;
                    pool[i] = entry;
                    i++; // long takes two constant pool slots; the next slot is unusable
                    continue;
                }
                case CONSTANT_DOUBLE: {
                    long high = reader.readU4() & 0xFFFFFFFFL;
                    long low = reader.readU4() & 0xFFFFFFFFL;
                    entry.doubleValue = Double.longBitsToDouble((high << 32) | low);
                    pool[i] = entry;
                    i++; // double takes two constant pool slots; the next slot is unusable
                    continue;
                }
                case CONSTANT_CLASS:
                case CONSTANT_METHOD_TYPE:
                case CONSTANT_MODULE:
                case CONSTANT_PACKAGE:
                    entry.nameIndex = reader.readU2();
                    break;
                case CONSTANT_STRING:
                    entry.stringIndex = reader.readU2();
                    break;
                case CONSTANT_FIELDREF:
                case CONSTANT_METHODREF:
                case CONSTANT_INTERFACE_METHODREF:
                    entry.classIndex = reader.readU2();
                    entry.nameAndTypeIndex = reader.readU2();
                    break;
                case CONSTANT_NAME_AND_TYPE:
                    entry.nameIndex = reader.readU2();
                    entry.descriptorIndex = reader.readU2();
                    break;
                case CONSTANT_METHOD_HANDLE:
                    entry.referenceKind = reader.readU1();
                    entry.referenceIndex = reader.readU2();
                    break;
                case CONSTANT_DYNAMIC:
                case CONSTANT_INVOKE_DYNAMIC:
                    entry.bootstrapMethodAttrIndex = reader.readU2();
                    entry.nameAndTypeIndex = reader.readU2();
                    break;
                default:
                    throw new IllegalStateException(String.format("unknown constant pool tag %d at index %d", tag, i));
            }

            pool[i] = entry;
        }

        return pool;
    }

    private static void printConstantPool(ConstantPoolEntry[] pool) {
        System.out.println("Constant pool:");
        for (int i = 1; i < pool.length; i++) {
            ConstantPoolEntry entry = pool[i];
            if (entry == null) {
                continue; // unused slot following a Long or Double entry
            }
            String fpe = formatConstantPoolEntry(pool, entry);
            System.out.printf("  #%-4d= %s%n", i, fpe);
            if (i == 1) {
                errorCount += Checkers.checker("CP entry #1", "Methodref          #2.#3 // java/lang/Object.<init>:()V", fpe);
            }
        }
    }

    private static String formatConstantPoolEntry(ConstantPoolEntry[] pool, ConstantPoolEntry entry) {
        if (DEBUG)
            System.out.printf("=== formatConstantPoolEntry begin: entry.tag=%d, entry.nameIndex=%d, entry.stringIndex=%d, entry.descriptorIndex=%d, entry.nameAndTypeIndex=%d, pool.length=%d\n", 
                entry.tag, entry.nameIndex, entry.stringIndex, entry.descriptorIndex, entry.nameAndTypeIndex, pool.length);
        switch (entry.tag) {
            case CONSTANT_UTF8:
                return String.format("Utf8               %s", entry.utf8Value);
            case CONSTANT_INTEGER:
                return String.format("Integer            %d", entry.intValue);
            case CONSTANT_FLOAT:
                return String.format("Float              %f", entry.floatValue);
            case CONSTANT_LONG:
                return String.format("Long               %d", entry.longValue);
            case CONSTANT_DOUBLE:
                return String.format("Double             %f", entry.doubleValue);
            case CONSTANT_CLASS:
                return String.format("Class              #%d // %s", entry.nameIndex, utf8At(pool, entry.nameIndex));
            case CONSTANT_STRING:
                return String.format("String             #%d // %s", entry.stringIndex, utf8At(pool, entry.stringIndex));
            case CONSTANT_FIELDREF:
                return String.format("Fieldref           #%d.#%d // %s", entry.classIndex, entry.nameAndTypeIndex, describeRef(pool, entry));
            case CONSTANT_METHODREF:
                return String.format("Methodref          #%d.#%d // %s", entry.classIndex, entry.nameAndTypeIndex, describeRef(pool, entry));
            case CONSTANT_INTERFACE_METHODREF:
                return String.format("InterfaceMethodref #%d.#%d // %s", entry.classIndex, entry.nameAndTypeIndex, describeRef(pool, entry));
            case CONSTANT_NAME_AND_TYPE:
                return String.format("NameAndType        #%d:#%d // %s:%s", entry.nameIndex, entry.descriptorIndex, utf8At(pool, entry.nameIndex), utf8At(pool, entry.descriptorIndex));
            case CONSTANT_METHOD_HANDLE:
                return String.format("MethodHandle       kind=%d #%d", entry.referenceKind, entry.referenceIndex);
            case CONSTANT_METHOD_TYPE:
                return String.format("MethodType         #%d // %s", entry.nameIndex, utf8At(pool, entry.nameIndex));
            case CONSTANT_DYNAMIC:
                return String.format("Dynamic            #%d:#%d", entry.bootstrapMethodAttrIndex, entry.nameAndTypeIndex);
            case CONSTANT_INVOKE_DYNAMIC:
                return String.format("InvokeDynamic      #%d:#%d", entry.bootstrapMethodAttrIndex, entry.nameAndTypeIndex);
            case CONSTANT_MODULE:
                return String.format("Module             #%d // %s", entry.nameIndex, utf8At(pool, entry.nameIndex));
            case CONSTANT_PACKAGE:
                return String.format("Package            #%d // %s", entry.nameIndex, utf8At(pool, entry.nameIndex));
            default:
                return String.format("Unknown tag %d", entry.tag);
        }
    }

    private static String utf8At(ConstantPoolEntry[] pool, int index) {
        if (DEBUG)
            System.out.printf("=== utf8At begin: index=%d, pool.length=%d\n", index, pool.length);
        if (index <= 0 || index >= pool.length || pool[index] == null) {
            throw new IllegalArgumentException(String.format("*** utf8At index: %d, pool length: %d", index, pool.length));
        }
        if (DEBUG) {
            System.out.printf("=== utf8At end: pool[index=%d] ", index);
            System.out.println(pool[index]);
        }
        return pool[index].utf8Value;
    }

    private static String describeRef(ConstantPoolEntry[] pool, ConstantPoolEntry entry) {
        String className = resolveClassName(pool, entry.classIndex);
        if (DEBUG)
            System.out.printf("=== describeRef begin: className=%s\n", className);
        if (entry.nameAndTypeIndex <= 0 || entry.nameAndTypeIndex >= pool.length || pool[entry.nameAndTypeIndex] == null) {
            return String.format("%s.? ", className);
        }
        ConstantPoolEntry nameAndType = pool[entry.nameAndTypeIndex];
        String name = utf8At(pool, nameAndType.nameIndex);
        String descriptor = utf8At(pool, nameAndType.descriptorIndex);
        return String.format("%s.%s:%s", className, name, descriptor);
    }

    private static String resolveClassName(ConstantPoolEntry[] pool, int classIndex) {
        if (DEBUG)
            System.out.printf("=== resolveClassName begin: classIndex=%d\n", classIndex);
        if (classIndex <= 0 || classIndex >= pool.length || pool[classIndex] == null) {
            return "?";
        }
        if (DEBUG)
            System.out.printf("=== resolveClassName end: pool[classIndex].nameIndex=%d\n", pool[classIndex].nameIndex);
        return utf8At(pool, pool[classIndex].nameIndex);
    }

    private static String decodeClassAccessFlags(int flags) {
        List<String> names = new ArrayList<>();
        if ((flags & 0x0001) != 0) {
            names.add("public");
        }
        if ((flags & 0x0010) != 0) {
            names.add("final");
        }
        if ((flags & 0x0020) != 0) {
            names.add("super");
        }
        if ((flags & 0x0200) != 0) {
            names.add("interface");
        }
        if ((flags & 0x0400) != 0) {
            names.add("abstract");
        }
        if ((flags & 0x1000) != 0) {
            names.add("synthetic");
        }
        if ((flags & 0x2000) != 0) {
            names.add("annotation");
        }
        if ((flags & 0x4000) != 0) {
            names.add("enum");
        }
        if ((flags & 0x8000) != 0) {
            names.add("module");
        }
        String retValue = "";
        for (String name : names) {
            retValue.concat(name);
        }
        return retValue;
    }

    // ---- field_info / method_info / attribute_info traversal ----

    private static void readMember(ClassReader reader, ConstantPoolEntry[] pool, String type) {
        int accessFlags = reader.readU2();
        int nameIndex = reader.readU2();
        int descriptorIndex = reader.readU2();
        int attributesCount = reader.readU2();

        if (LOGGING) {
            System.out.printf("  %s %s %s%n", type, utf8At(pool, nameIndex), utf8At(pool, descriptorIndex));
        }

        for (int i = 0; i < attributesCount; i++) {
            readAttribute(reader, pool, 4);
        }
    }

    private static void readAttribute(ClassReader reader, ConstantPoolEntry[] pool, int indent) {
        int nameIndex = reader.readU2();
        int length = reader.readU4();
        String name = utf8At(pool, nameIndex);

        String indentStr = " ".repeat(indent);
        if (LOGGING) {
            System.out.printf("%sAttribute: %s (length: %d)%n", indentStr, name, length);
        }

        if ("Code".equals(name)) {
            int maxStack = reader.readU2();
            int maxLocals = reader.readU2();
            int codeLength = reader.readU4();
            byte[] code = reader.readBytes(codeLength);

            if (LOGGING) {
                System.out.printf("%s  stack=%d, locals=%d, code_length=%d%n", indentStr, maxStack, maxLocals, codeLength);
                decodeInstructions(code, pool, indent + 4);
            }

            int exceptionTableLength = reader.readU2();
            if (exceptionTableLength > 0 && LOGGING) {
                System.out.printf("%s  Exception table:%n", indentStr);
                System.out.printf("%s     from    to  target type%n", indentStr);
            }
            for (int i = 0; i < exceptionTableLength; i++) {
                int start = reader.readU2();
                int end = reader.readU2();
                int handler = reader.readU2();
                int catchType = reader.readU2();
                if (LOGGING) {
                    String typeStr = (catchType == 0) ? "any" : resolveClassName(pool, catchType);
                    System.out.printf("%s     %4d  %4d  %4d   %s%n", indentStr, start, end, handler, typeStr);
                }
            }

            int attributesCount = reader.readU2();
            for (int i = 0; i < attributesCount; i++) {
                readAttribute(reader, pool, indent + 2);
            }
        } else if ("LineNumberTable".equals(name) && LOGGING) {
            int lineTableLength = reader.readU2();
            for (int i = 0; i < lineTableLength; i++) {
                int startPc = reader.readU2();
                int lineNumber = reader.readU2();
                System.out.printf("%s  line %d: %d%n", indentStr, lineNumber, startPc);
            }
        } else {
            reader.skip(length);
        }
    }

    private static void decodeInstructions(byte[] code, ConstantPoolEntry[] pool, int indent) {
        String indentStr = " ".repeat(indent);
        int pc = 0;
        while (pc < code.length) {
            int opcode = code[pc] & 0xFF;
            String name = Opcode.name(opcode);
            int len = Opcode.length(opcode, code, pc);
            
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s%4d: %-15s", indentStr, pc, name));
            
            String comment = "";
            if (len > 1 && pc + len <= code.length) {
                switch (opcode) {
                    case 0x12: { // ldc
                        int index = code[pc + 1] & 0xFF;
                        sb.append(String.format("#%-18d", index));
                        comment = formatConstantPoolComment(pool, index);
                        break;
                    }
                    case 0x13: // ldc_w
                    case 0x14: { // ldc2_w
                        int index = ((code[pc + 1] & 0xFF) << 8) | (code[pc + 2] & 0xFF);
                        sb.append(String.format("#%-18d", index));
                        comment = formatConstantPoolComment(pool, index);
                        break;
                    }
                    case 0x10: { // bipush
                        int val = code[pc + 1];
                        sb.append(String.format("%-19d", val));
                        break;
                    }
                    case 0x11: { // sipush
                        short val = (short) (((code[pc + 1] & 0xFF) << 8) | (code[pc + 2] & 0xFF));
                        sb.append(String.format("%-19d", val));
                        break;
                    }
                    case 0xb2: // getstatic
                    case 0xb3: // putstatic
                    case 0xb4: // getfield
                    case 0xb5: // putfield
                    case 0xb6: // invokevirtual
                    case 0xb7: // invokespecial
                    case 0xb8: // invokestatic
                    case 0xbb: // new
                    case 0xbd: // anewarray
                    case 0xc0: // checkcast
                    case 0xc1: { // instanceof
                        int index = ((code[pc + 1] & 0xFF) << 8) | (code[pc + 2] & 0xFF);
                        sb.append(String.format("#%-18d", index));
                        comment = formatConstantPoolComment(pool, index);
                        break;
                    }
                    case 0xb9: // invokeinterface
                    case 0xba: { // invokedynamic
                        int index = ((code[pc + 1] & 0xFF) << 8) | (code[pc + 2] & 0xFF);
                        sb.append(String.format("#%-18d", index));
                        comment = formatConstantPoolComment(pool, index);
                        break;
                    }
                    case 0x99: case 0x9a: case 0x9b: case 0x9c: case 0x9d: case 0x9e:
                    case 0x9f: case 0xa0: case 0xa1: case 0xa2: case 0xa3: case 0xa4:
                    case 0xa5: case 0xa6: case 0xa7: case 0xa8: case 0xc6: case 0xc7: {
                        short offset = (short) (((code[pc + 1] & 0xFF) << 8) | (code[pc + 2] & 0xFF));
                        sb.append(String.format("%-19d", pc + offset));
                        break;
                    }
                    case 0xc8: case 0xc9: { // goto_w, jsr_w
                        int offset = ((code[pc + 1] & 0xFF) << 24) | ((code[pc + 2] & 0xFF) << 16) | ((code[pc + 3] & 0xFF) << 8) | (code[pc + 4] & 0xFF);
                        sb.append(String.format("%-19d", pc + offset));
                        break;
                    }
                    case 0x84: { // iinc
                        int index = code[pc + 1] & 0xFF;
                        int constVal = code[pc + 2];
                        sb.append(String.format("%d, %-16d", index, constVal));
                        break;
                    }
                    default: {
                        sb.append(" // ");
                        for (int i = 1; i < len; i++) {
                            sb.append(String.format("%02x ", code[pc + i]));
                        }
                    }
                }
            }
            
            if (!comment.isEmpty()) {
                sb.append(" // ").append(comment);
            }
            
            System.out.println(sb.toString().stripTrailing());
            pc += len;
        }
    }

    private static String formatConstantPoolComment(ConstantPoolEntry[] pool, int index) {
        if (index <= 0 || index >= pool.length || pool[index] == null) {
            return "";
        }
        ConstantPoolEntry entry = pool[index];
        return switch (entry.tag) {
            case CONSTANT_CLASS -> "Class " + resolveClassName(pool, index);
            case CONSTANT_STRING -> "String " + utf8At(pool, entry.stringIndex);
            case CONSTANT_FIELDREF -> "Field " + describeRef(pool, entry);
            case CONSTANT_METHODREF -> "Method " + describeRef(pool, entry);
            case CONSTANT_INTERFACE_METHODREF -> "InterfaceMethod " + describeRef(pool, entry);
            case CONSTANT_NAME_AND_TYPE -> "NameAndType " + utf8At(pool, entry.nameIndex) + ":" + utf8At(pool, entry.descriptorIndex);
            case CONSTANT_INTEGER -> "int " + entry.intValue;
            case CONSTANT_FLOAT -> "float " + entry.floatValue + "f";
            case CONSTANT_LONG -> "long " + entry.longValue + "l";
            case CONSTANT_DOUBLE -> "double " + entry.doubleValue + "d";
            case CONSTANT_UTF8 -> "Utf8 " + entry.utf8Value;
            case CONSTANT_INVOKE_DYNAMIC -> "InvokeDynamic #" + entry.bootstrapMethodAttrIndex + ":#" + entry.nameAndTypeIndex;
            default -> "";
        };
    }

    private static class Opcode {
        static String name(int opcode) {
            return switch (opcode) {
                case 0x00 -> "nop";
                case 0x01 -> "aconst_null";
                case 0x02 -> "iconst_m1";
                case 0x03 -> "iconst_0";
                case 0x04 -> "iconst_1";
                case 0x05 -> "iconst_2";
                case 0x06 -> "iconst_3";
                case 0x07 -> "iconst_4";
                case 0x08 -> "iconst_5";
                case 0x09 -> "lconst_0";
                case 0x0a -> "lconst_1";
                case 0x0b -> "fconst_0";
                case 0x0c -> "fconst_1";
                case 0x0d -> "fconst_2";
                case 0x0e -> "dconst_0";
                case 0x0f -> "dconst_1";
                case 0x10 -> "bipush";
                case 0x11 -> "sipush";
                case 0x12 -> "ldc";
                case 0x13 -> "ldc_w";
                case 0x14 -> "ldc2_w";
                case 0x15 -> "iload";
                case 0x16 -> "lload";
                case 0x17 -> "fload";
                case 0x18 -> "dload";
                case 0x19 -> "aload";
                case 0x1a -> "iload_0";
                case 0x1b -> "iload_1";
                case 0x1c -> "iload_2";
                case 0x1d -> "iload_3";
                case 0x1e -> "lload_0";
                case 0x1f -> "lload_1";
                case 0x20 -> "lload_2";
                case 0x21 -> "lload_3";
                case 0x22 -> "fload_0";
                case 0x23 -> "fload_1";
                case 0x24 -> "fload_2";
                case 0x25 -> "fload_3";
                case 0x26 -> "aload_0";
                case 0x27 -> "aload_1";
                case 0x28 -> "aload_2";
                case 0x29 -> "aload_3";
                case 0x2a -> "aload_0";
                case 0x2b -> "aload_1";
                case 0x2c -> "aload_2";
                case 0x2d -> "aload_3";
                case 0x2e -> "iaload";
                case 0x2f -> "laload";
                case 0x30 -> "faload";
                case 0x31 -> "daload";
                case 0x32 -> "aaload";
                case 0x33 -> "baload";
                case 0x34 -> "caload";
                case 0x35 -> "saload";
                case 0x36 -> "istore";
                case 0x37 -> "lstore";
                case 0x38 -> "fstore";
                case 0x39 -> "dstore";
                case 0x3a -> "astore";
                case 0x3b -> "istore_0";
                case 0x3c -> "istore_1";
                case 0x3d -> "istore_2";
                case 0x3e -> "istore_3";
                case 0x3f -> "lstore_0";
                case 0x40 -> "lstore_1";
                case 0x41 -> "lstore_2";
                case 0x42 -> "lstore_3";
                case 0x43 -> "fstore_0";
                case 0x44 -> "fstore_1";
                case 0x45 -> "fstore_2";
                case 0x46 -> "fstore_3";
                case 0x47 -> "astore_0";
                case 0x48 -> "astore_1";
                case 0x49 -> "astore_2";
                case 0x4a -> "astore_3";
                case 0x4b -> "astore_0";
                case 0x4c -> "astore_1";
                case 0x4d -> "astore_2";
                case 0x4e -> "astore_3";
                case 0x4f -> "iastore";
                case 0x50 -> "lastore";
                case 0x51 -> "fastore";
                case 0x52 -> "dastore";
                case 0x53 -> "aastore";
                case 0x54 -> "bastore";
                case 0x55 -> "castore";
                case 0x56 -> "sastore";
                case 0x57 -> "pop";
                case 0x58 -> "pop2";
                case 0x59 -> "dup";
                case 0x5a -> "dup_x1";
                case 0x5b -> "dup_x2";
                case 0x5c -> "dup2";
                case 0x5d -> "dup2_x1";
                case 0x5e -> "dup2_x2";
                case 0x5f -> "swap";
                case 0x60 -> "iadd";
                case 0x61 -> "ladd";
                case 0x62 -> "fadd";
                case 0x63 -> "dadd";
                case 0x64 -> "isub";
                case 0x65 -> "lsub";
                case 0x66 -> "fsub";
                case 0x67 -> "dsub";
                case 0x68 -> "imul";
                case 0x69 -> "lmul";
                case 0x6a -> "fmul";
                case 0x6b -> "dmul";
                case 0x6c -> "idiv";
                case 0x6d -> "ldiv";
                case 0x6e -> "fdiv";
                case 0x6f -> "ddiv";
                case 0x70 -> "irem";
                case 0x71 -> "lrem";
                case 0x72 -> "frem";
                case 0x73 -> "drem";
                case 0x74 -> "ineg";
                case 0x75 -> "lneg";
                case 0x76 -> "fneg";
                case 0x77 -> "dneg";
                case 0x78 -> "ishl";
                case 0x79 -> "lshl";
                case 0x7a -> "ishr";
                case 0x7b -> "lshr";
                case 0x7c -> "iushr";
                case 0x7d -> "lushr";
                case 0x7e -> "iand";
                case 0x7f -> "land";
                case 0x80 -> "ior";
                case 0x81 -> "lor";
                case 0x82 -> "ixor";
                case 0x83 -> "lxor";
                case 0x84 -> "iinc";
                case 0x85 -> "i2l";
                case 0x86 -> "i2f";
                case 0x87 -> "i2d";
                case 0x88 -> "l2i";
                case 0x89 -> "l2f";
                case 0x8a -> "l2d";
                case 0x8b -> "f2i";
                case 0x8c -> "f2l";
                case 0x8d -> "f2d";
                case 0x8e -> "d2i";
                case 0x8f -> "d2l";
                case 0x90 -> "d2f";
                case 0x91 -> "i2b";
                case 0x92 -> "i2c";
                case 0x93 -> "i2s";
                case 0x94 -> "lcmp";
                case 0x95 -> "fcmpl";
                case 0x96 -> "fcmpg";
                case 0x97 -> "dcmpl";
                case 0x98 -> "dcmpg";
                case 0x99 -> "ifeq";
                case 0x9a -> "ifne";
                case 0x9b -> "iflt";
                case 0x9c -> "ifge";
                case 0x9d -> "ifgt";
                case 0x9e -> "ifle";
                case 0x9f -> "if_icmpeq";
                case 0xa0 -> "if_icmpne";
                case 0xa1 -> "if_icmplt";
                case 0xa2 -> "if_icmpge";
                case 0xa3 -> "if_icmpgt";
                case 0xa4 -> "if_icmple";
                case 0xa5 -> "if_acmpeq";
                case 0xa6 -> "if_acmpne";
                case 0xa7 -> "goto";
                case 0xa8 -> "jsr";
                case 0xa9 -> "ret";
                case 0xaa -> "tableswitch";
                case 0xab -> "lookupswitch";
                case 0xac -> "ireturn";
                case 0xad -> "lreturn";
                case 0xae -> "freturn";
                case 0xaf -> "dreturn";
                case 0xb0 -> "areturn";
                case 0xb1 -> "return";
                case 0xb2 -> "getstatic";
                case 0xb3 -> "putstatic";
                case 0xb4 -> "getfield";
                case 0xb5 -> "putfield";
                case 0xb6 -> "invokevirtual";
                case 0xb7 -> "invokespecial";
                case 0xb8 -> "invokestatic";
                case 0xb9 -> "invokeinterface";
                case 0xba -> "invokedynamic";
                case 0xbb -> "new";
                case 0xbc -> "newarray";
                case 0xbd -> "anewarray";
                case 0xbe -> "arraylength";
                case 0xbf -> "athrow";
                case 0xc0 -> "checkcast";
                case 0xc1 -> "instanceof";
                case 0xc2 -> "monitorenter";
                case 0xc3 -> "monitorexit";
                case 0xc4 -> "wide";
                case 0xc5 -> "multianewarray";
                case 0xc6 -> "ifnull";
                case 0xc7 -> "ifnonnull";
                case 0xc8 -> "goto_w";
                case 0xc9 -> "jsr_w";
                default -> "unknown (" + String.format("0x%02x", opcode) + ")";
            };
        }

        static int length(int opcode, byte[] code, int pc) {
            return switch (opcode) {
                case 0x10, 0x12, 0x15, 0x16, 0x17, 0x18, 0x19, 0x36, 0x37, 0x38, 0x39, 0x3a, 0xbc -> 2;
                case 0x11, 0x13, 0x14, 0x84, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e, 0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xbb, 0xbd, 0xc0, 0xc1, 0xc6, 0xc7 -> 3;
                case 0xc5 -> 4;
                case 0xb9, 0xba, 0xc8, 0xc9 -> 5;
                case 0xaa -> { // tableswitch
                    int alignedPc = (pc + 4) & ~3;
                    int low = ((code[alignedPc + 4] & 0xFF) << 24) | ((code[alignedPc + 5] & 0xFF) << 16) | ((code[alignedPc + 6] & 0xFF) << 8) | (code[alignedPc + 7] & 0xFF);
                    int high = ((code[alignedPc + 8] & 0xFF) << 24) | ((code[alignedPc + 9] & 0xFF) << 16) | ((code[alignedPc + 10] & 0xFF) << 8) | (code[alignedPc + 11] & 0xFF);
                    yield (alignedPc - pc) + 12 + (high - low + 1) * 4;
                }
                case 0xab -> { // lookupswitch
                    int alignedPc = (pc + 4) & ~3;
                    int npairs = ((code[alignedPc + 4] & 0xFF) << 24) | ((code[alignedPc + 5] & 0xFF) << 16) | ((code[alignedPc + 6] & 0xFF) << 8) | (code[alignedPc + 7] & 0xFF);
                    yield (alignedPc - pc) + 8 + npairs * 8;
                }
                case 0xc4 -> { // wide
                    int subOpcode = code[pc + 1] & 0xFF;
                    yield (subOpcode == 0x84) ? 6 : 4;
                }
                default -> 1;
            };
        }
    }

    // ---- low-level byte cursor over the class file ----

    private static class ClassReader {
        private final byte[] data;
        private int position;

        ClassReader(byte[] data) {
            this.data = data;
            this.position = 0;
        }

        int readU1() {
            return data[position++] & 0xFF;
        }

        int readU2() {
            int high = readU1();
            int low = readU1();
            return (high << 8) | low;
        }

        int readU4() {
            int b1 = readU1();
            int b2 = readU1();
            int b3 = readU1();
            int b4 = readU1();
            return (b1 << 24) | (b2 << 16) | (b3 << 8) | b4;
        }

        byte[] readBytes(int n) {
            byte[] bytes = new byte[n];
            System.arraycopy(data, position, bytes, 0, n);
            position += n;
            return bytes;
        }

        String readUtf8() {
            int length = readU2();
            byte[] utf8Bytes = new byte[length];
            System.arraycopy(data, position, utf8Bytes, 0, length);
            position += length;
            return new String(utf8Bytes);
        }

        void skip(int n) {
            position += n;
        }
    }

    // ---- constant pool entry storage ----

    private static class ConstantPoolEntry {
        int tag;
        int nameIndex;
        int classIndex;
        int nameAndTypeIndex;
        int stringIndex;
        int descriptorIndex;
        int referenceKind;
        int referenceIndex;
        int bootstrapMethodAttrIndex;
        int intValue;
        float floatValue;
        long longValue;
        double doubleValue;
        String utf8Value;
    }
}
