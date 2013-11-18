/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher.trie;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Map
 * @author moremagic
 */
public class TrieIdNodeMap implements Map<Long, TrieIdNode>{
    private File dbFile = null;
    private File valueFile = null;
    private Map<Long, TrieIdNode> map = new HashMap<Long, TrieIdNode>();    
    
    public TrieIdNodeMap(File db, File value){
        this.dbFile = db;
        this.valueFile = value;
    }
    
    @Override
    public int size() {
        return (int)(this.dbFile.length() / TrieIdNode.NODE_BYTE_SIZE);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty() && this.dbFile.length() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int id = ((Long)key).intValue();
        return (size() > id);
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrieIdNode get(Object key) {
        if(!map.containsKey((Long)key)){
            try {
                map.put((Long)key, readNode(dbFile, valueFile, ((Long)key).longValue()));
            } catch (IOException ex) {
                Logger.getLogger(TrieIdNodeMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        TrieIdNode ret = map.get(key);
        return ret;
    }

    @Override
    public TrieIdNode put(Long key, TrieIdNode value) {
        TrieIdNode ret = map.put(key, value);
        try {
            writeNode(dbFile, valueFile, value);
        } catch (IOException ex) {
            Logger.getLogger(TrieIdNodeMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public TrieIdNode remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Long, ? extends TrieIdNode> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Long> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<TrieIdNode> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Long, TrieIdNode>> entrySet() {
        return map.entrySet();
    }
    
    protected void writeNode(File db, File valueFile, TrieIdNode node) throws IOException {
        RandomAccessFile out = null;
        RandomAccessFile fw = null;
        try {
            out = new RandomAccessFile(db, "rw");
            fw = new RandomAccessFile(valueFile, "rw");
            
            out.seek(node.getNodeId() * TrieIdNode.NODE_BYTE_SIZE);
            out.writeLong(node.getNodeId());
            out.writeChar(node.getNodeLabel());

            Long[] chileds = (Long[]) node.getChileds();
            for (int i = 0; i <= 256; i++) {
                if (chileds.length > i) {
                    out.writeLong(chileds[i]);
                } else {
                    out.writeLong(-1L);
                }
            }
            
            String value = (node.getValue() == null)?"":node.getValue();
            out.writeLong(valueFile.length());
            out.writeInt(value.toCharArray().length);
            
            fw.seek(valueFile.length());
            fw.write(value.getBytes());
System.out.println("write: " + node.getNodeId() + " " + valueFile.length() + ", " + node.getValue());            
        } finally {
            if (fw != null)fw.close();
            if (out != null)out.close();
        }
    }
    
    protected TrieIdNode readNode(File db, File valueFile, long id) throws IOException {
        TrieIdNode ret = null;
        
        RandomAccessFile in = null;
        RandomAccessFile fr = null;
        try {
            in = new RandomAccessFile(db, "r");
            fr = new RandomAccessFile(valueFile, "r");

            in.seek(id * TrieIdNode.NODE_BYTE_SIZE);
            id = in.readLong();
            char label = in.readChar();
            ret = new TrieIdNode(label);
            ret.setNodeId(id);

            for (int i = 0; i <= 256; i++) {
                long childId = in.readLong();
                if(childId != -1L){
                    ret.addChiled(new Long(childId));
                }
            }

            long valuePos = in.readLong();
            int valueLen = in.readInt();
            
            //root対策
            valueLen = valueLen<0?0:valueLen;
            valuePos = valuePos<0?0:valuePos;
            
            byte[] values = new byte[valueLen];
            fr.seek(valuePos);
            fr.read(values, 0, valueLen);
            
            ret.setValue(new String(values));
            ret.setValuePos(valuePos);

System.out.println("read: " + id + " " + valuePos + ", " + valueLen);            
            
            return ret;
        } finally {
            if (in != null)in.close();
            if (fr != null)fr.close();
        }
    }


}
