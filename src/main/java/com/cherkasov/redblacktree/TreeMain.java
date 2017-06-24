package com.cherkasov.redblacktree;

/* Разбираемся в красно-черном дереве
*/
public class TreeMain {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        RedBlackTree rbt = new RedBlackTree();
        rbt.insert(10);
        rbt.insert(7);
        rbt.insert(30);
        rbt.insert(15);
        System.out.println(rbt.isEmpty());
        System.out.println(rbt.toString());


    }
}
