/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher.trie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * trieノード
 * @author moremagic
 */
public class TrieNode implements Serializable{    
    private char node_label;
    private String value = null;
    private List<TrieNode> chiled = new ArrayList<TrieNode>();
    
    public TrieNode(char c){
        this.node_label = c;
    }    

    public char getNodeLabel() {
        return node_label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TrieNode[] getChileds() {
        return chiled.toArray(new TrieNode[0]);
    }

    public void addChiled(TrieNode chiled) {
        this.chiled.add(chiled);
    }    
    
    public void removeChiled(TrieNode chiled) {
        this.chiled.remove(chiled);
    }    
}
