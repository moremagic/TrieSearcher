/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher.trie;

import java.io.File;

/**
 * トライ木コントローラ
 * @author moremagic
 */
public class TrieController1 {
    public static TrieIdNode root;
    public static TrieIdNodeMap map = new TrieIdNodeMap(new File("db.txt"), new File("value.txt"));
    
    public TrieController1(){
        root = map.get(new Long(0));
        if(root == null){
            root = new TrieIdNode((char)-1);
        }
    }
    
    /**
     * データを登録する
     * @param key
     * @param value 
     */
    public boolean addValue(String key, String value){
        return addValue(key.toCharArray(), value, root);
    }
    
    /**
     * 検索を行う。(完全一致)
     * @param key
     * @return 
     */
    public String searchData(String key){
        return searchValue(key.toCharArray(), root);
    }
    
    private static String searchValue(char[] keys, TrieIdNode node){
        if(keys.length == 0){
            return node.getValue();
        }else{
            char[] nextKeys = new char[keys.length-1];
            System.arraycopy(keys, 1, nextKeys, 0, nextKeys.length);
            
            for(Long id: node.getChileds() ){
                TrieIdNode n = map.get(id);
                
                if(n != null){
                    System.out.println("id ;" + id + " [" + ((char)n.getNodeLabel()) + "]");
                }
                
                if(n != null && keys[0] == n.getNodeLabel()){
                    return searchValue(nextKeys, n);
                }
            }
            
            //初めてのキーの場合
            return null;
        }
    }
    
    
    private static boolean addValue(char[] keys, String value, TrieIdNode node){
        if(keys.length == 0){
            node.setValue(value);
            map.put(node.getNodeId(), node);
            return true;
        }else{
            char[] nextKeys = new char[keys.length-1];
            System.arraycopy(keys, 1, nextKeys, 0, nextKeys.length);
            
            for(Long id: node.getChileds()){
                TrieIdNode n = map.get(id);
                if(n != null && keys[0] == n.getNodeLabel()){
                    return addValue(nextKeys, value, n);
                }
            }
            
            //初めてのキーの場合
            TrieIdNode n = new TrieIdNode(keys[0]);
            node.addChiled(n.getNodeId());
            map.put(node.getNodeId(), node);
            map.put(n.getNodeId(), n);
            
            return addValue(nextKeys, value, n);
        }
    }
}
