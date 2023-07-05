// Hacked from https://www.geeksforgeeks.org/implementation-of-blockchain-in-java/

// Java implementation for creating
// a block in a Blockchain

import java.util.Date;

public class Block {

	private boolean verbose = false;

    // Every block contains
    // a hash, previous hash and
    // data of the transaction made
    private String hash;
    private String previousHash;
    private byte[] data;
    private long timeStamp;

    // Function to calculate the hash
    public String calculateHash() {
        // Calculate the hash value
        // by using the following object fields:
        // * current data
        // * current timestamp
        // * previous hash value
        String calculatedhash = HashGen.sha256(this.previousHash + Long.toString(this.timeStamp) + this.data);
        return calculatedhash;
    }

    // Constructor for the block
    public Block(byte[] data,
                 String previousHash,
                 boolean verbose) {
        this.data = data;
        this.timeStamp = new Date().getTime();
        this.previousHash = previousHash;
        this.hash = calculateHash();
        if (verbose)
            System.out.print("Block verbose: data[0]=");
            System.out.print(this.data[0]);
            System.out.print(", hash=");
            System.out.print(this.hash);
            System.out.print(", previousHash=");
            System.out.print(this.previousHash);
            System.out.print(", timeStamp=");
            System.out.println(this.timeStamp);
    }

    // Get block size
    public long getSize() {
    	long size = this.hash.length() + this.previousHash.length() + this.data.length + 8;
        return size;
    }

    // Get current hash
    public String getCurHash() {
        return this.hash;
    }

    // Get previous hash
    public String getPrevHash() {
        return this.previousHash;
    }

    // Set current hash
    public void setCurHash(String hash) {
        this.hash = hash;
    }

    // Set previous hash
    public void setPrevHash(String hash) {
        this.previousHash = hash;
    }
}

