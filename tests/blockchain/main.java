// Hacked from https://www.geeksforgeeks.org/implementation-of-blockchain-in-java/

import java.util.ArrayList;
import java.util.Random;

public class main {

    static long CHAIN_LENGTH = 10000; // Number of chain elements
    static int MAX_ELEM_SIZE = 32767; // Maximum size of a single element
    static boolean VERBOSE = false;

    static ArrayList<Block> blockchain
            = new ArrayList<Block>();

	public static void rptErrorBlock(String label, int index, String obsHash, String expHash) {
		System.out.print("*** ERROR, in ");
		System.out.print(label);
		System.out.print(", index ");
		System.out.print(index);
		System.out.print(", observed hash ");
		System.out.print(obsHash);
		System.out.print(", expected hash ");
		System.out.println(expHash);
	}

    // Check validity of the blockchain
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        // Iterating through
        // all the blocks
        for (int ii = 1;
             ii < blockchain.size();
             ii++) {

            // Storing the current block
            // and the previous block
            currentBlock = blockchain.get(ii);
            previousBlock = blockchain.get(ii - 1);

            // Checking if the current hash
            // is equal to the
            // calculated hash or not
            if (!currentBlock.getCurHash()
                    .equals(
                            currentBlock
                                    .calculateHash())) {
                rptErrorBlock("CURRENT block", ii, currentBlock.getCurHash(), currentBlock.calculateHash());
                return false;
            }

            // Checking of the previous hash
            // is equal to the calculated
            // previous hash or not
            if (!previousBlock
                    .getCurHash()
                    .equals(
                            currentBlock
                                    .getPrevHash())) {
                rptErrorBlock("PREVIOUS block", ii - 1, currentBlock.getCurHash(), currentBlock.calculateHash());
                return false;
            }
        }

        // If all the hashes are equal
        // to the calculated hashes,
        // then the blockchain is valid
        return true;
    }

    public static void printLabeledString(String label, String value) {
        System.out.print(label);
        System.out.println(value);
    }

    // Program entry point
    public static void main(String[] args) {
        System.out.println("Blockchain exercise: create, series of adds, and chain verify");
        printLabeledString("Chain length in blocks: ", String.valueOf(CHAIN_LENGTH));
        printLabeledString("Maximum element size in bytes: ", String.valueOf(MAX_ELEM_SIZE));

        Random RR = new Random();

        // Generate the block chain
        System.out.println("Generate .....");
        int elemSize;
        byte[] elemBytes;
        long totalPayloadSize = 0L;
        long totalChainSize = 0L;
        Block block;
        for (int ii = 0; ii < CHAIN_LENGTH; ++ii) {
            elemSize = RR.nextInt(MAX_ELEM_SIZE);
            totalPayloadSize += (long) elemSize;
            elemBytes = new byte[MAX_ELEM_SIZE];
            RR.nextBytes(elemBytes);
            if (VERBOSE) {
                System.out.print(ii);
                System.out.print(" ");
            }
            if (ii == 0)
                block = new Block(elemBytes, "0", VERBOSE);
            else
                block = new Block(elemBytes,
                        blockchain
                                .get(blockchain.size() - 1)
                                .getCurHash(),
                        VERBOSE);
            blockchain.add(block);
            totalChainSize += block.getSize();
        }

        // How did we do?
        System.out.println("Validate .....");
        if (isChainValid()) {
            System.out.println("Valid blockchain at the end");
            printLabeledString("Total chain size in bytes: ", String.valueOf(totalChainSize));
            printLabeledString("Total payload size in bytes: ", String.valueOf(totalPayloadSize));
        } else {
            System.out.println("INVALID blockchain at the end");
            System.exit(1);
        }
    }
}

