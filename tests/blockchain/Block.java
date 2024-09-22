// Hacked from https://www.geeksforgeeks.org/implementation-of-blockchain-in-java/

// Java implementation for creating a single block in a Blockchain.

public class Block {

    // Every block contains a hash, previous hash and the transaction data.
    private String hash = "";
    private String previousHash = "";
    private byte[] txdata = {0};
    private long timeStamp = 0;

    // Concatenate two byte arrays.
    private byte[] concatBytes(byte[] first, byte[] second) {
        byte[] concatenated = new byte[first.length + second.length];
        for (int ii = 0; ii < concatenated.length; ++ii) {
            concatenated[ii] = ii < first.length ? first[ii] : second[ii - first.length];
        }
        return concatenated;
    }

    // Function to calculate the hash
    public String calculateHash() {
        // Calculate the hash value
        // by using the following object fields:
        // * current txdata (byte[], converted to String)
        // * current timestamp (long, converted to String)
        // * previous hash value (String)
        
        String tsString = Long.toString(this.timeStamp); 
        String txString = Helpers.hexStringFromBytes(this.txdata);      
        String hashInput = txString.concat(tsString);
        hashInput = hashInput.concat(this.previousHash);        
        String calculatedhash = SHA256lite.computeSHA256(hashInput);
            
        return calculatedhash;
    }

    // Constructor for the block
    public Block(byte[] txdata,
                 String previousHash,
                 boolean flagVerbose) {
        this.txdata = txdata;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        String tsString = Long.toString(this.timeStamp); 
        String txString = Helpers.hexStringFromBytes(this.txdata);      
        String hashInput = txString.concat(tsString);
        hashInput = hashInput.concat(this.previousHash);        
        this.hash = SHA256lite.computeSHA256(hashInput);
        
        if (flagVerbose) {
            System.out.print("Block: txdata=");
            System.out.print(Helpers.hexStringFromBytes(this.txdata));
            System.out.print(", hash=");
            System.out.print(this.hash);
            System.out.print(", timeStamp=");
            System.out.print(this.timeStamp);
            System.out.print(", previousHash=");
            System.out.println(this.previousHash);
        }
    }

    // Get block size
    public long getSize() {
    	long size = this.hash.length() + this.previousHash.length() + this.txdata.length + 8;
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

