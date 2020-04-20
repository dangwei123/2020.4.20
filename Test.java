给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。

岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。

此外，你可以假设该网格的四条边均被水包围。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/number-of-islands
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    private int row;
    private int col;
    public int numIslands(char[][] grid) {
        row=grid.length;
        if(row==0) return 0;
        col=grid[0].length;
        int res=0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(grid[i][j]=='1'){
                    dfs(grid,i,j);
                    res++;
                }
            }
        }
        return res;
    }
    private void dfs(char[][] board,int i,int j){
        if(i<0||j<0||i>=row||j>=col||board[i][j]=='0'){
            return;
        }
        board[i][j]='0';
        dfs(board,i+1,j);
        dfs(board,i-1,j);
        dfs(board,i,j+1);
        dfs(board,i,j-1);
    }
}

删除最小数量的无效括号，使得输入的字符串有效，返回所有可能的结果。

说明: 输入可能包含了除 ( 和 ) 以外的字符。
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res=new ArrayList<>();
        Queue<String> queue=new LinkedList<>();
        Set<String> set=new HashSet<>();
        queue.offer(s);
        set.add(s);
        boolean isFind=false;
        while(!queue.isEmpty()){
            String t=queue.poll();
            if(isValid(t)){
                res.add(t);
                isFind=true;
            }
            if(isFind){
                continue;
            }
            for(int i=0;i<t.length();i++){
                if(t.charAt(i)=='('||t.charAt(i)==')'){
                    String str;
                    if(i==t.length()-1){
                        str=t.substring(0,t.length()-1);
                    }else{
                        str=t.substring(0,i)+t.substring(i+1,t.length());
                    }
                    if(set.add(str)){
                        queue.offer(str);
                    }
                }
            }
        }
        if(res.isEmpty()){
            res.add("");
        }
        return res;
    }
    private boolean isValid(String s){
        int left=0;
        int right=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c=='('){
                left++;
            }
            if(c==')'){
                right++;
            }
            if(right>left){
                return false;
            }
        }
        return left==right;
    }
}

给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。

请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。

你可以假设 nums1 和 nums2 不会同时为空。
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m=nums1.length;
        int n=nums2.length;
        int half=(m+n+1)/2;
        if(m>n){
            int[] tmp=nums1;
            nums1=nums2;
            nums2=tmp;
            
            int t=m;
            m=n;
            n=t;
        }
        int left=0;
        int right=m;
        while(left<=right){
            int i=left+(right-left)/2;
            int j=half-i;
            if(i>0&&nums1[i-1]>nums2[j]){
                right=i-1;
            }else if(i<m&&nums1[i]<nums2[j-1]){
                left=i+1;
            }else{
                int maxleft=0;
                if(i==0){
                    maxleft=nums2[j-1];
                }else if(j==0){
                    maxleft=nums1[i-1];
                }else{
                    maxleft=Math.max(nums1[i-1],nums2[j-1]);
                }
                
                if((m+n)%2==1){
                    return maxleft;
                }
                int minright=0;
                if(i==m){
                    minright=nums2[j];
                }else if(j==n){
                    minright=nums1[i];
                }else{
                    minright=Math.min(nums1[i],nums2[j]);
                }
                
                return (minright+maxleft)*1.0/2;
            }
        }
        return 0.0;
    }
}





