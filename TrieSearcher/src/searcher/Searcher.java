/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            try {
                ObjectOutputStream out = null;
                try {
                    out = new ObjectOutputStream(new FileOutputStream(new File("trie.db")));
                    out.writeObject(con);
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("書き出し時間[ms]：" + (System.currentTimeMillis() - start_time));            
        }
        {
            long start_time = System.currentTimeMillis();
            try {
                ObjectInputStream in = null;
                try {
                    in = new ObjectInputStream(new FileInputStream(new File("trie.db")));
                    con = (TrieController) in.readObject();
                } finally {
                    if (in != null) {
                        in.close();
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Searcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("read時間[ms]：" + (System.currentTimeMillis() - start_time));

            System.out.println(con.searchData("0xffe3"));
            System.out.println("検索時間[ms]：" + (System.currentTimeMillis() - start_time));

        }
    }
}
