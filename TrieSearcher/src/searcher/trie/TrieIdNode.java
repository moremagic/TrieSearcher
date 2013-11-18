/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher.trie;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author moremagic
 */
public class TrieIdNode {
    public static final int NODE_BYTE_SIZE = 4 + 1 + (4 * 256) + 4 + 4;//ID + nodeLabel + childsNodeID + data_pointer + data_length
    private static long NODE_COUNTER = -1;
    private long node_id = -1;
    private char node_label;
    private long value_pos;
    private String value;
    private List<Long> chiled = new ArrayList<Long>();
    
    
    public TrieIdNode(char c) {
        node_label = c;
        node_id = ++NODE_COUNTER;
    }

    public long getNodeId() {
        return node_id;
    }
    
    public void setNodeId(long id){
        node_id = id;
    }
    public char getNodeLabel() {
        return node_label;
    }

    public void setValuePos(long l){
        this.value_pos = l;
    }
    
    public long getValuePos(){
        return this.value_pos;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long[] getChileds() {
        return chiled.toArray(new Long[0]);
    }

    public void addChiled(Long chiled) {
        this.chiled.add(chiled);
    }    
    
    public void removeChiled(Long chiled) {
        this.chiled.remove(chiled);
    }    
}