/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher;

import searcher.trie.TrieController;

/**
 *
 * @author moremagic
 */
public class Searcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TrieController con = new TrieController();

        {
            long start_time = System.currentTimeMillis();
            for (long l = 0; l < 1000000; l++) {
                //System.out.println("登録中... " + l);
                con.addValue("0x" + Long.toHexString(l), Long.toString(l));
            }
            System.out.println("登録時間[ms]：" + (System.currentTimeMillis() - start_time));
        }
        {
            long start_time = System.currentTimeMillis();
            String searchKey = "0xffff";
            System.out.println(searchKey + "->" + con.searchData(searchKey));
            System.out.println("検索時間[ms]：" + (System.currentTimeMillis() - start_time));
        }
    }
}
