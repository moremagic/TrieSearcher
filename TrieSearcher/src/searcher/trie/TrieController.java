/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher.trie;

/**
 * トライ木コントローラ
 * @author moremagic
 */
public class TrieController {
    //rootノード
    public TrieNode root = new TrieNode((char)-1);
    
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
    
    private static String searchValue(char[] keys, TrieNode node){
        if(keys.length == 0){
            return node.getValue();
        }else{
            char[] nextKeys = new char[keys.length-1];
            System.arraycopy(keys, 1, nextKeys, 0, nextKeys.length);
            
            for( TrieNode n: node.getChileds() ){
                if(keys[0] == n.getNodeLabel()){
                    return searchValue(nextKeys, n);
                }
            }
            
            //初めてのキーの場合
            return null;
        }
    }
    
    
    private static boolean addValue(char[] keys, String value, TrieNode node){
        if(keys.length == 0){
            node.setValue(value);
            return true;
        }else{
            char[] nextKeys = new char[keys.length-1];
            System.arraycopy(keys, 1, nextKeys, 0, nextKeys.length);
            
            for( TrieNode n: node.getChileds() ){
                if(keys[0] == n.getNodeLabel()){
                    return addValue(nextKeys, value, n);
                }
            }
            
            //初めてのキーの場合
            TrieNode n = new TrieNode(keys[0]);
            node.addChiled(n);
            return addValue(nextKeys, value, n);
        }
    }
}
