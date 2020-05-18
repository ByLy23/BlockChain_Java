/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author byron
 */
public class Bloque {

    public Bloque(String previousHash, String timeStap, String newHash, String index, String nonce) {
        this.previousHash = previousHash;
        this.timeStap = timeStap;
        this.newHash = newHash;
        this.index = index;
        this.nonce = nonce;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getTimeStap() {
        return timeStap;
    }

    public void setTimeStap(String timeStap) {
        this.timeStap = timeStap;
    }

    public String getNewHash() {
        return newHash;
    }

    public void setNewHash(String newHash) {
        this.newHash = newHash;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
    String previousHash;
    String timeStap;
    String newHash;
    String index;
    String nonce;
}
