package tchain;
import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class TChain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	
	public static int difficulty = 2;

	public static void main(String[] args) {
		
		//Adds blocks to blockchain
		blockchain.add(new Block("The first block", "0"));
		System.out.println("Trying to mine block 1...");
		blockchain.get(0).mineBlock(difficulty);
		
		blockchain.add(new Block("The second block", blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to mine block 2...");
		blockchain.get(1).mineBlock(difficulty);
		
		blockchain.add(new Block("The third block", blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to mine block 3...");
		blockchain.get(2).mineBlock(difficulty);
		
		System.out.println("\nBlockchain is valid: " + isChainValid());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}
	
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		
		// loop through blockchain to check hashes:
		for(int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			
			// compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes is not equal");
				return false;
			}
			
			// compare previous hash and registered previous hash:
			if(!previousBlock.hash.equals(previousBlock.calculateHash())) {
				System.out.println("Previous Hashes is not equal");
				return false;
			}
			
			if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		
		return true;
	}
}

