给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第k小的元素。
请注意，它是排序后的第 k 小元素，而不是第 k 个不同的元素。
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int row=matrix.length;
        int col=matrix[0].length;
        int left=matrix[0][0];
        int right=matrix[row-1][col-1];  
        while(left<right){
            int mid=left+(right-left)/2;
            int count=0;
            int i=row-1;
            int j=0;
            while(i>=0&&j<col){
                if(matrix[i][j]<=mid){
                    count+=i+1;
                    j++;
                }else{
                    i--;
                }
            }

            if(count<k){
                left=mid+1;
            }else{
                right=mid;
            }
        }
        return left;
    }
}

在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
class Solution {
    private int res;
    public int findKthLargest(int[] nums, int k) {
        fastSort(nums,0,nums.length-1,nums.length-k);
        return res;
    }
    private void fastSort(int[]nums,int left,int right,int k){
        if(left>right){
            return;
        }
        int pivot=partition(nums,left,right);
        if(pivot==k){
            res=nums[pivot];
            return;
        }else if(pivot>k){
            fastSort(nums,left,pivot-1,k);
        }else{
            fastSort(nums,pivot+1,right,k);
        }
        
    }
    private int partition(int[] nums,int left,int right){
        int pivot=nums[left];
        int i=left;
        int j=right;
        while(i<j){
            while(i<j&&nums[j]>=pivot){
                j--;
            }
            nums[i]=nums[j];
            while(i<j&&nums[i]<=pivot){
                i++;
            }
            nums[j]=nums[i];
        }
        nums[i]=pivot;
        return i;
    }
}

给定一个无序的数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
class Solution {
    public void wiggleSort(int[] nums) {
        int[] arr=new int[nums.length];
        System.arraycopy(nums,0,arr,0,nums.length);
        Arrays.sort(arr);
        int mid=(nums.length-1)/2;
        int i=mid;
        int j=nums.length-1;
        int k=0;
        while(i>=0||j>mid){
            if(i>=0){
                nums[k++]=arr[i--];
            }
            if(j>mid){
                nums[k++]=arr[j--];
            }        
        }
    }
}

给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字）。
class Solution {
    public int maxProduct(int[] nums) {
        int max=nums[0];
        int min=nums[0];
        int res=nums[0];
        for(int i=1;i<nums.length;i++){
            int tmp=max;
            max=Math.max(nums[i],Math.max(max*nums[i],min*nums[i]));
            min=Math.min(nums[i],Math.min(tmp*nums[i],min*nums[i]));
            res=Math.max(max,res);
        }
        return res;
    }
}

给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:

你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
class Solution {
    public int maxProfit(int[] prices) {
        if(prices.length==0) return 0;
        int dp0=0;
        int dp1=-prices[0];
        int pre=0;
        int tmp=0;
        for(int i=1;i<prices.length;i++){
            tmp=dp0;
            dp0=Math.max(dp0,dp1+prices[i]);
            dp1=Math.max(dp1,pre-prices[i]);
            pre=tmp;
        }
        return dp0;
    }
}

给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
class Solution {
    public int numSquares(int n) {
        int[] dp=new int[n+1];
        Arrays.fill(dp,n+1);
        dp[0]=0;
        for(int i=1;i<=n;i++){
            for(int j=1;j<=i/j;j++){
                dp[i]=Math.min(dp[i],dp[i-j*j]+1);
            }
        }
        return dp[n];
    }
}

