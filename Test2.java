给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set=new HashSet<>(wordDict);
        int n=s.length();
        boolean[] dp=new boolean[n+1];
        dp[0]=true;
        for(int i=1;i<=n;i++){
            for(int j=0;j<i;j++){
                if(dp[j]&&set.contains(s.substring(j,i))){
                    dp[i]=true;
                    break;
                }
            }
        }
        return dp[n];
    }
}

给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，在字符串中增加空格来构建一个句子，使得句子中所有的单词都在词典中。返回所有这些可能的句子。
class Solution {
    List<String> res=new ArrayList<>();
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> set=new HashSet<>(wordDict);
        int n=s.length();
        boolean[] dp=new boolean[n+1];
        dp[0]=true;
        for(int i=1;i<=n;i++){
            for(int j=0;j<i;j++){
                if(dp[j]&&set.contains(s.substring(j,i))){
                    dp[i]=true;
                    break;
                }
            }
        }
        if(!dp[n]){
            return res;
        }
        back(s,set,0,n,new ArrayList<>());
        return res;
    }
    private void back(String s,Set<String> set,int begin,int len,List<String>row){
        if(begin==len){
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<row.size();i++){
                if(i!=row.size()-1)
                sb.append(row.get(i)).append(" ");
                else sb.append(row.get(i));
            }
            res.add(new String(sb));
        }
        for(int i=begin;i<len;i++){
            String str=s.substring(begin,i+1);
            if(set.contains(str)){
                row.add(str);
                back(s,set,i+1,len,row);
                row.remove(row.size()-1);
            }
        }
    }
}

有 n 个气球，编号为0 到 n-1，每个气球上都标有一个数字，这些数字存在数组 nums 中。

现在要求你戳破所有的气球。每当你戳破一个气球 i 时，你可以获得 nums[left] * nums[i] * nums[right] 个硬币。 这里的 left 和 right 代表和 i 相邻的两个气球的序号。注意当你戳破了气球 i 后，气球 left 和气球 right 就变成了相邻的气球。

求所能获得硬币的最大数量。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/burst-balloons
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

class Solution {
    public int maxCoins(int[] nums) {
        int n=nums.length+2;
        int[] arr=new int[n];
        for(int i=0;i<n-2;i++){
            arr[i+1]=nums[i];
        }
        arr[0]=1;
        arr[n-1]=1;
        int[][] dp=new int[n][n];
        for(int left=n-2;left>=0;left--){
            for(int right=left+2;right<n;right++){
                for(int i=left+1;i<right;i++){
                    dp[left][right]=Math.max(dp[left][right],
                          dp[left][i]+dp[i][right]+arr[left]*arr[i]*arr[right]);
                }
            }
        }
        return dp[0][n-1];
    }
}

运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。

获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
写入数据 put(key, value) - 如果密钥已经存在，则变更其数据值；如果密钥不存在，则插入该组「密钥/数据值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。

class LRUCache {
    private static class Node{
        private int key;
        private int val;
        public Node(int key,int val){
            this.key=key;
            this.val=val;
        }
    }
    
    private Map<Integer,Node> map;
    private int capacity;
    private LinkedList<Node> list;
    public LRUCache(int capacity) {
        map=new HashMap<>();
        list=new LinkedList<>();
        this.capacity=capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node node=map.get(key);
        list.remove(node);
        list.addFirst(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node=map.get(key);
            node.val=value;
            list.remove(node);
            list.addFirst(node);
        }else{
            if(map.size()==capacity){
                Node delete=list.removeLast();
                map.remove(delete.key);
            }
            Node node=new Node(key,value);
            map.put(key,node);
            list.addFirst(node);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
 
 实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
 class Trie {
    private static class Node{
        private Node[] next;
        private boolean isEnd;
        public Node(){
            next=new Node[26];
            //isEnd=false;
        }
    }
    
    private Node root;
    /** Initialize your data structure here. */
    public Trie() {
        root=new Node();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node cur=root;
        for(int i=0;i<word.length();i++){
            int index=word.charAt(i)-'a';
            if(cur.next[index]==null){
                cur.next[index]=new Node();
            }
            cur=cur.next[index];
        }
        cur.isEnd=true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node cur=root;
        for(int i=0;i<word.length();i++){
            int index=word.charAt(i)-'a';
            if(cur.next[index]==null){
                return false;
            }
            cur=cur.next[index];
        }
        return cur.isEnd;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node cur=root;
        for(int i=0;i<prefix.length();i++){
            int index=prefix.charAt(i)-'a';
            if(cur.next[index]==null){
                return false;
            }
            cur=cur.next[index];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
 
 给你一个嵌套的整型列表。请你设计一个迭代器，使其能够遍历这个整型列表中的所有整数。

列表中的每一项或者为一个整数，或者是另一个列表。其中列表的元素也可能是整数或是其他列表。
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    private Queue<Integer> queue=new LinkedList<>();
    public NestedIterator(List<NestedInteger> nestedList) {
        add(nestedList);
    }
    private void add(List<NestedInteger> nestedList){
        for(NestedInteger i:nestedList){
            if(i.isInteger()){
                queue.offer(i.getInteger());
            }else{
                add(i.getList());
            }
        }
    }

    @Override
    public Integer next() {
        return queue.poll();
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
 
 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。

例如，

[2,3,4] 的中位数是 3

[2,3] 的中位数是 (2 + 3) / 2 = 2.5

设计一个支持以下两种操作的数据结构：

void addNum(int num) - 从数据流中添加一个整数到数据结构中。
double findMedian() - 返回目前所有元素的中位数。
class MedianFinder {
    private PriorityQueue<Integer> min;
    private PriorityQueue<Integer> max;
    private int count;
    /** initialize your data structure here. */
    public MedianFinder() {
        min=new PriorityQueue<>();
        max=new PriorityQueue<>((a,b) -> {return  b - a ;});
    }
    
    public void addNum(int num) {
        count++;
        max.offer(num);
        min.offer(max.poll());
        if((count&1)==1){
            max.offer(min.poll());
        }
    }
    
    public double findMedian() {
        if((count&1)==1){
            return max.peek();
        }
        return (max.peek()+min.peek())/2.0;
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */

