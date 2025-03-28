// SolitaireCipher.java
// Copyright (C) 1999  Jeff Gold
//   This program is free software; you can redistribute it and/or 
// modify it under the terms of the GNU General Public License as 
// published by the Free Software Foundation; either version 2 of the 
// License, or (at your option) any later version.
//   This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//   You should have received a copy of the GNU General Public License
// along with this program (see License.txt); if not, see
//     http://www.gnu.org 
// or write to the Free Software Foundation, Inc., 59 Temple Place, 
// Suite 330, Boston, MA  02111-1307  USA
//   NOTE: the Solitaire encryption algorithm is strong cryptography.
// That means the security it affords is based on the secrecy of the 
// key rather than secrecy of the algorithm itself.  That also means 
// that this program and programs derived from it may be treated as 
// a munition for the purpose of export regulation in the United States 
// and other countries.  You are encouraged to seek competent legal 
// counsel before distributing copies of this program.
// 
//   This package contains a Java implementation of the Solitaire 
// encryption algorithm, as designed by Bruce Schneier and described at: 
//     http://www.counterpane.com/solitaire.html
// as well as in Neil Stephenson's novel Cryptonomicon.
//   Solitaire is a cryptographically strong stream cipher that can
// be implemented using a deck of playing cards.  This implementation 
// is not designed for high performance -- this is a Java program, after 
// all.  Instead it is intended to be clean, portable, and easy to 
// understand.

import java.io.Serializable;

public final class SolitaireCipher implements Serializable, Cloneable {
  private static final long serialVersionUID = 42l;

  // Representation Data
  // ===================
  protected Deck keyDeck;

  // Constructors
  // ============
  public SolitaireCipher(Deck keyDeck) {
    // Effect: creates a cipher with a key based on the
    //   order of the cards in the specified deck.
    if (keyDeck != null) {
      this.keyDeck = (Deck)keyDeck.clone();
    } else this.keyDeck = new Deck(true);
  } // SolitaireCipher(Deck)

  public SolitaireCipher(String keyPhrase) {
    // Effect: creates a cipher with a key based on the 
    //   specified string.
    keyDeck = new Deck(true);

    // Shuffle deck according to key phrase. 
    for (int i = 0; i < keyPhrase.length(); i++) {
      int cut_size = getCharValue(keyPhrase.charAt(i));
      if (cut_size > 0) {
        nextKeyStream();
        keyDeck.tripleCut(1, keyDeck.count() - cut_size);
        keyDeck.cutTop(1);
      } // if (cut_size > 0)
    } // for i
  } // SolitaireCipher(String)

  // Generic Methods
  // ===============
  public Object clone() {
    return new SolitaireCipher(keyDeck);
  } // clone()

  public String toString() {
    // Effect: return a string that represents the state of 
    //   this cipher.
    return keyDeck.toString();
  } // toString()

  // Deck Methods
  // ============
  public Deck getDeck() {
    // Effect: return a copy of the current key deck.
    return (Deck)keyDeck.clone();
  } // getDeck()

  // Utility Methods
  // ===============
  protected static int getCardValue(byte card) {
    // Effect: returns a numeric value for the specified card.
    if (Deck.isJoker(card)) {
      return 53;
    } else if (Deck.isClub(card)) {
      return Deck.getFaceValue(card);
    } else if (Deck.isDiamond(card)) {
      return 13 + Deck.getFaceValue(card);
    } else if (Deck.isHeart(card)) {
      return 26 + Deck.getFaceValue(card);
    } else if (Deck.isSpade(card)) {
      return 39 + Deck.getFaceValue(card);
    } else {
      return 0;  // Something bad happened.
    } // if card
  } // getCardValue(byte)

  protected static int getCharValue(char c) {
    // Effect: return the position of the specified character in
    //   the alphabet regardless of case, or zero if the character
    //   is not in the Latin alphabet.
    if (c >= 'A' && c <= 'Z') {
      return (int)c - 'A' + 1;
    } else if (c >= 'a' && c <= 'z') {
      return (int)c - 'a' + 1;
    } else {
      return 0;
    } // if c
  } // getCharValue(char)

  protected static char getValueChar(int v) {
    // Effect: return the position of the specified character in
    //   the alphabet regardless of case, or zero if the character
    //   is not in the Latin alphabet.
    if (v >= 1 && v <= 26) {
      return (char)((v - 1) + (int)'A');
    } else {
      return '*';
    } // if c
  } // getCharValue(char)

  // Cryptographic Methods
  // =====================
  protected int nextKeyStream() {
    // Effect: performs the basic stream encryption operation
    //   and returns the next element of the key stream.
    byte jokerA = (byte)(Deck.JOKER | Deck.JOKER_A);
    byte jokerB = (byte)(Deck.JOKER | Deck.JOKER_B);

    // Step one: Move Joker A one card down.
    keyDeck.moveDown(keyDeck.findTop(jokerA), 1);

    // Step two: Move Joker B two cards down.
    keyDeck.moveDown(keyDeck.findTop(jokerB), 2);

    // Step three: Perform a triple cut.
    int at_jokerA = keyDeck.findBottom(jokerA);
    int at_jokerB = keyDeck.findBottom(jokerB);
    int minnie = at_jokerA < at_jokerB ? at_jokerA : at_jokerB;
    int maxie = at_jokerA > at_jokerB ? at_jokerA : at_jokerB;
    keyDeck.tripleCut(minnie, maxie + 1);

    // Step four: Perform a count cut.
    byte count = keyDeck.peekBottom(0);
    keyDeck.tripleCut(1, keyDeck.count() - getCardValue(count));
    keyDeck.cutTop(1);

    // Step five: Find the output card.
    byte output = keyDeck.peekTop(getCardValue(keyDeck.peekTop(0)));

    // Step six: Convert output card to a number.
    int result = getCardValue(output);
    if (result == 53) return nextKeyStream();
    else if (result > 26) result = result - 26;
    return result;
  } // nextKeyStream()

  public String decrypt(String ciphertext) {
    // Effect: returns the ciphertext corresponding to the specified
    //   plaintext, according to the current key deck ordering.
    StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < ciphertext.length(); i++) {
      int plain = getCharValue(ciphertext.charAt(i));
      if (plain > 0) {
        plain -= nextKeyStream();
        if (plain < 1) plain += 26;
        buffer.append(getValueChar(plain));
      } // if (plain > 0)
    } // for i

    return buffer.toString();
  } // decrypt(String)

  public String encrypt(String plaintext) {
    // Effect: returns the ciphertext corresponding to the specified
    //   plaintext, according to the current key deck ordering.  Any
    //   string with a length that is not a multiple of five will be
    //   padded with the character 'X'.
    return encrypt(plaintext, true);
  } // encrypt(String)

  public String encrypt(String plaintext, boolean padded) {
    // Effect: returns the ciphertext corresponding to the specified
    //   plaintext, according to the current key deck ordering.
    StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < plaintext.length(); i++) {
      int cipher = getCharValue(plaintext.charAt(i));
      if (cipher > 0) {
        cipher += nextKeyStream();
        if (cipher > 26) cipher -= 26;
        buffer.append(getValueChar(cipher));
      } // if (cipher > 0)
    } // for i

    if (padded) {
      while (buffer.length() % 5 != 0) {
        buffer.append(encrypt("X", false));
      } // while buffer
    } // if (padded)
    return buffer.toString();
  } // encrypt(String, boolean)

  // Test SolitaireCipher
  public static void main(String args[]) {
    SolitaireCipher sc = new SolitaireCipher("BigSecret");
    String cleartext_1 = "Mary had a little lamb";
    System.out.printf("Cleartext 1: %s\n", cleartext_1);
    String ciphertext = sc.encrypt(cleartext_1);
    System.out.printf("Ciphertext: %s\n", ciphertext);
    String cleartext_2 = sc.decrypt(ciphertext);
    System.out.printf("Cleartext 2: %s\n", cleartext_2);
  } // main(String[])

} // class SolitaireCipher
