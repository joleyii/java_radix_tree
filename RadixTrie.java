package com.comet.myapplication.dic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by nujjj on 2018/3/12.
 */

public class RadixTrie {
    public Node[] nodes = new Node[52];

    public RadixTrie() {
        initRootNode();
    }

    private void initRootNode() {
        nodes[0] = new Node('a');
        nodes[1] = new Node('b');
        nodes[2] = new Node('c');
        nodes[3] = new Node('d');
        nodes[4] = new Node('e');
        nodes[5] = new Node('f');
        nodes[6] = new Node('g');
        nodes[7] = new Node('h');
        nodes[8] = new Node('i');
        nodes[9] = new Node('j');
        nodes[10] = new Node('k');
        nodes[11] = new Node('l');
        nodes[12] = new Node('m');
        nodes[13] = new Node('n');
        nodes[14] = new Node('o');
        nodes[15] = new Node('p');
        nodes[16] = new Node('q');
        nodes[17] = new Node('r');
        nodes[18] = new Node('s');
        nodes[19] = new Node('t');
        nodes[20] = new Node('u');
        nodes[21] = new Node('v');
        nodes[22] = new Node('w');
        nodes[23] = new Node('x');
        nodes[24] = new Node('y');
        nodes[25] = new Node('z');

        nodes[26] = new Node('A');
        nodes[27] = new Node('B');
        nodes[28] = new Node('C');
        nodes[29] = new Node('D');

        nodes[30] = new Node('E');
        nodes[31] = new Node('F');
        nodes[32] = new Node('G');
        nodes[33] = new Node('H');
        nodes[34] = new Node('I');
        nodes[35] = new Node('J');
        nodes[36] = new Node('K');
        nodes[37] = new Node('L');
        nodes[38] = new Node('M');
        nodes[39] = new Node('N');

        nodes[40] = new Node('O');
        nodes[41] = new Node('P');
        nodes[42] = new Node('Q');
        nodes[43] = new Node('R');
        nodes[44] = new Node('S');
        nodes[45] = new Node('T');
        nodes[46] = new Node('U');
        nodes[47] = new Node('V');
        nodes[48] = new Node('W');
        nodes[49] = new Node('X');

        nodes[50] = new Node('Y');
        nodes[51] = new Node('Z');
    }

    public void insertFromDB(char[] chars, int count) {
        if (chars.length > 1) {
            char one = chars[0];
            int index;
            if (Character.isUpperCase(one)) {
                index = (int) one - 39;
            } else {
                index = (int) one - 97;
            }

            Node node = nodes[index];
            int lengthSubstract = chars.length;
            char[] nextRest = new char[lengthSubstract - 1];
            System.arraycopy(chars, 1, nextRest, 0, lengthSubstract - 1);
            insert(node, nextRest, count);
        }
    }

    //abd ab abcd a ed
    public static void insert(Node root, char[] resident, int count) {
        if (resident.length == 1) {//如果剩余长度等于0 则到此为止可以成为一个单词
            Node node = new Node(resident[0]);//建立下级node 放入这一级的node数组中

            int lengthChildren = root.children.length;
            Node[] nextChildren = new Node[lengthChildren + 1];
            System.arraycopy(root.children, 0, nextChildren, 0, lengthChildren);
            nextChildren[lengthChildren] = node;
            node.stop = true;
            node.count = count;
            root.children = nextChildren;
            return;
        }
        Node[] children = root.getChildren();
        for (Node child : children) {
            char[] childValue = child.getValue();//查子目录是否有这个char
            if (childValue[0] == resident[0]) {// 如果有这个字node 且还有剩余的需要插入的char 进入这个子node
                int lengthSubstract = resident.length;
                char[] nextRest = new char[lengthSubstract - 1];
                System.arraycopy(resident, 1, nextRest, 0, lengthSubstract - 1);
                insert(child, nextRest, count);
                return;
            }
        }
        //子目录没有 新建子node 插入 这一集的数组
        buildChild(root, resident, count);
    }

    public static void buildChild(Node root, char c[], int count) {
        Node node = new Node(c[0]);//建立下级node 放入这一级的node数组中

        int lengthChildren = root.children.length;
        Node[] nextChildren = new Node[lengthChildren + 1];
        System.arraycopy(root.children, 0, nextChildren, 0, lengthChildren);
        nextChildren[lengthChildren] = node;
        root.children = nextChildren;

        if (c.length > 1) { //如果剩余长度不为0 继续分配
            int lengthSubstract = c.length;
            char[] nextRest = new char[lengthSubstract - 1];
            System.arraycopy(c, 1, nextRest, 0, lengthSubstract - 1);
            insert(node, nextRest, count);
        }
    }

    public List<CharSequence> search(StringBuilder searchName) {
        ArrayList<WordBean> arrayList = new ArrayList<>();
        char one = searchName.charAt(0);
        if (!isAlphabet(one)) {
            return new ArrayList<>();
        }
        StringBuilder removeS = new StringBuilder();
        removeS.append(searchName);
        removeS.deleteCharAt(searchName.length() - 1);
        int index;
        if (Character.isUpperCase(one)) {
            index = (int) one - 39;
        } else {
            index = (int) one - 97;
        }
        Node node = nodes[index];
        StringBuilder stringBuilder = new StringBuilder(one + "");
        recursion(arrayList, node, stringBuilder, searchName.deleteCharAt(0));
        Collections.sort(arrayList, new Comparator<WordBean>() {
            @Override
            public int compare(WordBean o1, WordBean o2) {
                if (o1.getCount() > o2.getCount()) {
                    return -1;
                } else if (o1.getCount() == o2.getCount()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        ArrayList<CharSequence> charSequences = new ArrayList<>();
        String replace = searchName.insert(0, one) + "";
        for (WordBean wordBean : arrayList) {
            String s = wordBean.getWord() + "";
            charSequences.add(s.replace(replace, ""));
        }
        List<CharSequence> charSequences2 =
                charSequences.subList(0, charSequences.size() > 11 ? 11 : charSequences.size());
        if (charSequences2.size() > 0) {
            int indexC = -1;
            for (int i = 0; i < charSequences2.size(); i++) {
                if (charSequences2.get(i).toString().equals(removeS + "")) {
                    indexC = i;
                }
            }
            if (indexC != -1) {
                charSequences2.remove(indexC);
            }
        }
        return charSequences2;
    }


    public List<CharSequence> searchBi(StringBuilder searchName, StringBuilder second) {
        List<CharSequence> charSequences = search(searchName);
        if (charSequences.size() < 10) {
            List<CharSequence> one = search(second);
            for (int i = 0; i < 10 - charSequences.size(); i++) {
                if (one.size() > i) {
                    charSequences.add(one.get(i));
                }
            }
        }
        return charSequences;
    }

    //abd abc a ed
    public void recursion(ArrayList<WordBean> result, Node node, StringBuilder before, StringBuilder left) {
        if (node.isStop()) {//先判断node是否可以独立为一个单词
            result.add(new WordBean(before.toString(), node.count));
        }

        if (left.length() > 0) {// 查看是否还有残留 有残留且node有子node 且子节点value是残留的
            Node[] childrenNode = node.getChildren();
            for (Node childNode : childrenNode) {
                if (left.charAt(0) == childNode.getValue()[0]) {//和当前节点中的 节点数组中的某一个中的value吻合 进入这个吻合节点
                    StringBuilder beforeNew = new StringBuilder();
                    beforeNew.append(before);
                    beforeNew.append(left.charAt(0));
                    StringBuilder leftt = new StringBuilder();
                    leftt.append(left);
                    leftt.deleteCharAt(0);
                    recursion(result, childNode, beforeNew, leftt);
                }
            }
        } else {//没有残留 把所有子节点的东西放入
            Node[] childrenNode = node.getChildren();
            for (Node childNode : childrenNode) {
                StringBuilder beforeNew = new StringBuilder();
                beforeNew.append(before);
                beforeNew.append(childNode.getValue());
                recursion(result, childNode, beforeNew, left);
            }
        }
    }

    public static boolean isAlphabet(char c) {
        switch (c) {
            case 'a':
                return true;
            case 'b':
                return true;
            case 'c':
                return true;
            case 'd':
                return true;
            case 'e':
                return true;
            case 'f':
                return true;
            case 'g':
                return true;
            case 'h':
                return true;
            case 'i':
                return true;
            case 'j':
                return true;
            case 'k':
                return true;
            case 'l':
                return true;
            case 'm':
                return true;
            case 'n':
                return true;
            case 'o':
                return true;
            case 'p':
                return true;
            case 'q':
                return true;
            case 'r':
                return true;
            case 's':
                return true;
            case 't':
                return true;
            case 'u':
                return true;
            case 'v':
                return true;
            case 'w':
                return true;
            case 'x':
                return true;
            case 'y':
                return true;
            case 'z':
                return true;
            case 'A':
                return true;
            case 'B':
                return true;
            case 'C':
                return true;
            case 'D':
                return true;
            case 'E':
                return true;
            case 'F':
                return true;
            case 'G':
                return true;
            case 'H':
                return true;
            case 'I':
                return true;
            case 'J':
                return true;
            case 'K':
                return true;
            case 'L':
                return true;
            case 'M':
                return true;
            case 'N':
                return true;
            case 'O':
                return true;
            case 'P':
                return true;
            case 'Q':
                return true;
            case 'R':
                return true;
            case 'S':
                return true;
            case 'T':
                return true;
            case 'U':
                return true;
            case 'V':
                return true;
            case 'W':
                return true;
            case 'X':
                return true;
            case 'Y':
                return true;
            case 'Z':
                return true;
            default:
                return false;
        }
    }
}
