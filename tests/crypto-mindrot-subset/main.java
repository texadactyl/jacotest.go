// Copyright (c) 2006 Damien Miller <djm@mindrot.org>
//
// Permission to use, copy, modify, and distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

/**
 * JUnit unit tests for BCrypt routines
 * @author Damien Miller
 * @version 0.2
 */
import java.io.UnsupportedEncodingException;
public class main {

    static int errorCount = 0;
    static boolean debugging = false;
    
	static String test_vectors[][] = {
			{ "", 
			"$2a$06$DCq7YPn5Rq63x1Lad4cll.",
			"$2a$06$DCq7YPn5Rq63x1Lad4cll.TV4S6ytwfsfvkgY8jIucDrjc8deX1s." },
		};

	/**
	 * Entry point for unit tests
	 * @param args unused
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		testHashpw();
	    testGensaltInt();
	    // SLOW! testGensalt();
	    testCheckpw_success();
	    // SLOW! testInternationalChars();
		Checkers.theEnd(errorCount);
	}

	/**
	 * Test method for 'BCrypt.hashpw(String, String)'
	 */
	public static void testHashpw() throws UnsupportedEncodingException {
		for (int i = 0; i < test_vectors.length; i++) {
			String plain = test_vectors[i][0];
			String salt = test_vectors[i][1];
			String expected = test_vectors[i][2];
			String hashed = BCrypt.hashpw(plain, salt);
			String label = String.format("testHashpw [%d]", i);
			errorCount += Checkers.checker(label, expected, hashed);
			if (errorCount > 0) return;
			if (debugging) return;
		}
	}

	/**
	 * Test method for 'BCrypt.gensalt(int)'
	 */
	public static void testGensaltInt() throws UnsupportedEncodingException {
		for (int i = 4; i < 7; i++) {
			for (int j = 0; j < test_vectors.length; j += 4) {
				String plain = test_vectors[j][0];
				String salt = BCrypt.gensalt(i);
				String hashed1 = BCrypt.hashpw(plain, salt);
				String hashed2 = BCrypt.hashpw(plain, hashed1);
			    String label = String.format("testGensaltInt [%d] [%d]", i, j);
			    errorCount += Checkers.checker(label, hashed2, hashed1);
			    if (errorCount > 0) return;
			}
		}
	}

	/**
	 * Test method for 'BCrypt.gensalt()'
	 */
	public static void testGensalt() throws UnsupportedEncodingException {
		for (int i = 0; i < test_vectors.length; i += 4) {
			String plain = test_vectors[i][0];
			String salt = BCrypt.gensalt();
			String hashed1 = BCrypt.hashpw(plain, salt);
			String hashed2 = BCrypt.hashpw(plain, hashed1);
			String label = String.format("testGensalt [%d]", i);
			errorCount += Checkers.checker(label, hashed2, hashed1);
			if (errorCount > 0) return;
		}
	}

	/**
	 * Test method for 'BCrypt.checkpw(String, String)'
	 * expecting success
	 */
	public static void testCheckpw_success() throws UnsupportedEncodingException {
		for (int i = 0; i < test_vectors.length; i++) {
			String plain = test_vectors[i][0];
			String expected = test_vectors[i][2];
			String label = String.format("testCheckpw_success [%d]", i);
			errorCount += Checkers.checker(label, true, BCrypt.checkpw(plain, expected));
			if (errorCount > 0) return;
		}
	}

	/**
	 * Test for correct hashing of non-US-ASCII passwords
	 */
	public static void testInternationalChars() throws UnsupportedEncodingException {
		String pw1 = "\u2605\u2605\u2605\u2605\u2605\u2605\u2605\u2605";
		String pw2 = "????????";

		String h1 = BCrypt.hashpw(pw1, BCrypt.gensalt());
        errorCount += Checkers.checker("testInternationalChars", false, BCrypt.checkpw(pw2, h1));
	    if (errorCount > 0) return;
        
		String h2 = BCrypt.hashpw(pw2, BCrypt.gensalt());
        errorCount += Checkers.checker("testInternationalChars", false, BCrypt.checkpw(pw1, h2));
	}

}
