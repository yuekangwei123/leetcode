1.两数之和
方法一:
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for(int i= 0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                if (nums[i]+nums[j] == target){
                    int arr [] = {i,j};
                    return arr;
                }     
            }
        }
        return null;
        //throw new IllegalArgumentException("No two sum solution");       
}


方法二:
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for(int i= 0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                if (nums[i]+nums[j] == target){
                    return new int []{i,j};
                }     
            }
        }
        return null;
        //throw new IllegalArgumentException("No two sum solution");       
}


2.两数相加
//程序思想:遍历两个链表,设置一个carry用来记录进位,分别将每个链表对应的数字相加,直到遍历完为止

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        while(l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;
            
            carry = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);

            cur = cur.next;
            if(l1 != null)
                l1 = l1.next;
            if(l2 != null)
                l2 = l2.next;
        }
        if(carry == 1) {
            cur.next = new ListNode(carry);				//如果遍历完两个链表后,最后一位相加为10,那么就进一位,下一位设置为1
        }
        return pre.next;
    }
}

3.无重复字符的最长子串

方法一:暴力解法
public int lengthOfLongestSubstring(String s) {
        //两次循环应该可以搞定
        //1.
        if (s == null) {
            return 0;
        }
        //2.
        int len = s.length();
        List<Character> list = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                if (list.contains(s.charAt(j))) {
                    break;
                } else {
                    list.add(s.charAt(j));
                }
            }
            count = Math.max(count, list.size());
            //当count大于等于剩下字符串的长度时，不再遍历
            if (count >= len - 1 - i) {
                break;
            }
            list.clear();
        }
        return count;
    }




方法二:利用滑动窗口法		(不太理解)

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }
}


4.寻找两个正序数组的中位数

方法一:暴力解法

/*解题思路:
首先将讲个数组进行合并,每次取较小的元素加入新的数组中,直到合并完毕,
最后判断数组长度为奇数还是偶数,如果是奇数,直接返回中间值,如果是偶数,则返回中间两个值*/

class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        
        int mid =0;
        int a = nums1.length;
        int b = nums2.length;
        int [] nums = new int[a+b] ;
        int i=0;
        int j=0;
        while(i<a && j< b){						//将两个数组进行合并,每次将较小值加入到新的列表中
            if(nums1[i] > nums2[j]){
                nums[mid] = nums2[j];
                ++mid;
                ++j;
                
            }else{
                nums[mid] = nums1[i];
                ++mid;
                ++i;
            }
        }
        if(j < b){							//如果数组2中还有剩余元素,则将剩余元素加入新的数组中
            while(j<b){
                nums[mid] = nums2[j];
                ++mid;
                ++j;
            }
        }
        if(i < a){
            while(i <a){
                nums[mid] = nums1[i];		//如果数组1中还有剩余元素,则将剩余元素加入到新的数组中
                ++mid;
                ++i;
            }
        }
        if(nums.length %2 == 1){			//如果是奇数,则中位数就是中间值
            return nums[nums.length/2];
        }else{
            return (nums[nums.length/2] + nums[nums.length/2-1])/2;
        }
    }
}

方法二:
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        ArrayList<Integer> list = new ArrayList<>();
        int a=nums1.length,b = nums2.length;
        int i =0,j =0;
        while(i <a && j< b){
            if(nums1[i] > nums2[j]){
                list.add(nums2[j]);
                j++;
            }else{
                list.add(nums1[i]);
                i++;
            }    
        }
        if (i<a){
            while(i<a){
                list.add(nums1[i]);
                i++;    
            }
        }
        if(j <b){
            while(j <b){
                list.add(nums2[j]);
                j++;
            }
        }
        if(list.size() %2 == 1){
            return list.get(list.size()/2);
        }else{
            return (list.get(list.size()/2) + list.get(list.size()/2 -1))/2;
        }

    }
}

5.最长回文子串

方法一:暴力解法

class Solution {
    public String longestPalindrome(String s) {
        if (s.length() <2){
            return s;
        }
        int maxlen = 1;
        int start =0;
        int end =0;
        char [] arr = s.toCharArray();
        
        for(int i = 0;i<arr.length;i++){
            for(int j = i+1;j<arr.length;j++){
                if(j-i+1 > maxlen && judge(arr,i,j)){
                    maxlen= j-i+1;
                    start = i;
                    end  = j;
                }
            }
        }
        return s.substring(start,end+1);
    }
    private static boolean judge(char [] arr,int i,int j){
        while(i <j){
            if (arr[i] == arr[j]){
                i ++;
                j --;
            }else{
                return false;
            }
        }
        return true;
    }
}


方法二:动态规划(有点不理解)
public class Solution {

    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;
        // s.charAt(i) 每次都会检查数组下标越界，因此先转换成字符数组
        char[] charArray = s.toCharArray();

        // 枚举所有长度大于 1 的子串 charArray[i..j]
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (j - i + 1 > maxLen && validPalindromic(charArray, i, j)) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * 验证子串 s[left..right] 是否为回文串
     */
    private boolean validPalindromic(char[] charArray, int left, int right) {
        while (left < right) {
            if (charArray[left] != charArray[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}


6.Z字形变换
方法一:参考自leetcode别人解析

class Solution {
    public String convert(String s, int numRows) {
        if(numRows < 2) return s;
        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        for(int i = 0; i < numRows; i++) rows.add(new StringBuilder());
        int i = 0, flag = -1;
        for(char c : s.toCharArray()) {
            rows.get(i).append(c);
            if(i == 0 || i == numRows -1) flag = - flag;
            i += flag;
        }
        StringBuilder res = new StringBuilder();
        for(StringBuilder row : rows) res.append(row);
        return res.toString();
    }
}

方法二:

解题思路:
// 创建一个列表Arraylist,类型为字符串可变类型StringBuffer,在列表中创建numrows个字符串,遍历字符串,每次将对应的字符加入到列表中对应的位置;
//用flag作为标志符,是否换行,最后将字符串进行拼接

class Solution {
    public String convert(String s, int numRows) {
        ArrayList<StringBuffer> list = new ArrayList<>();		//设置一个列表用来存放字符,每个位置的类型为StringBuffer类型
        for(int i = 0;i<numRows;i++){
            list.add(new StringBuffer());        				//设置3个位置的可变字符串,用来模拟3行
        }
        int i =0,flag = -1;										//设置flag,用来记录是否换行
        for(char c : s.toCharArray()){
            list.get(i).append(c);								//将每个字符加入到列表对应的位置
            if(i ==0 || i == numRows -1){						//当i=0或者i=最后一行时,将flag进行换行
                flag = -flag;
            }
            i += flag;
        }
        StringBuffer res = new StringBuffer();					//创建一个新的可变字符串对象
        for(StringBuffer ss :list){								//使用增强for循环
            res.append(ss);										//将列表中的每个字符加入到res这个大的字符串中
        }
        return res.toString();
    }
}

7.整数反转

class Solution {
    public int reverse(int x) {
        
        
        int y =0,n =0;
        while(x!=0){
            if(y >214748364  || y < -214748364 ){			//如果反转后数字超出范围,则返回0
                return 0;
            }
            // y = y*10 + x%10;
            // x = x/10;
            n = x %10;
            y = y*10 +n;
            x =x /10;
        }   
        return y;
    }
}


8.字符串转换整数(感觉这题没有意义,没有仔细看)

public class Solution {
    public int myAtoi(String str) {
        char[] chars = str.toCharArray();
        int n = chars.length;
        int idx = 0;
        while (idx < n && chars[idx] == ' ') {
            // 去掉前导空格
            idx++;
        }
        if (idx == n) {
            //去掉前导空格以后到了末尾了
            return 0;
        }
        boolean negative = false;
        if (chars[idx] == '-') {
            //遇到负号
            negative = true;
            idx++;
        } else if (chars[idx] == '+') {
            // 遇到正号
            idx++;
        } else if (!Character.isDigit(chars[idx])) {
            // 其他符号
            return 0;
        }
        int ans = 0;
        while (idx < n && Character.isDigit(chars[idx])) {
            int digit = chars[idx] - '0';
            if (ans > (Integer.MAX_VALUE - digit) / 10) {
                // 本来应该是 ans * 10 + digit > Integer.MAX_VALUE
                // 但是 *10 和 + digit 都有可能越界，所有都移动到右边去就可以了。
                return negative? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            ans = ans * 10 + digit;
            idx++;
        }
        return negative? -ans : ans;
    }
}

9.回文数

解题思路:(自己想的)
首先将int类型转换为字符串类型,然后将字符串转化为数组类型,最后比较数组是否是对称数组.

class Solution {
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        String s = String.valueOf(x);				//将int类型转换为字符串类型
        char [] s_list = s.toCharArray();			//将字符串转换为数组类型
        int i = 0;
        int j = s_list.length-1;
        while(i<j){
            if(s_list[i] != s_list[j]){
                return false;
            }
            ++i;
            --j;
        }
        return true;
    }
}

10.正则表达式匹配

太难了,直接忽略

11.盛最多水的容器

解题思路:
设置两个指针,分别指向数组的首尾,能装多少水取决于两边最短的边,高度*面积就是能装的水的数量,然后哪边高度短就移动哪边的指针,
长的一边不需要移动,因为把相对较短的移动后,高度有可能增加

解法一:三元组表达式法
class Solution {
    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1, res = 0;
        while(i < j){
            res = height[i] < height[j] ? 
            Math.max(res, (j - i) * height[i++]): 
            Math.max(res, (j - i) * height[j--]); 
        }
        return res;
    }
}

解法二:常规法
class Solution {
    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1, res = 0;
        while(i < j){
            if(height[i]>height[j]){
                res= Math.max(res,(j-i)*height[j]);
                j--;
            }else{
                res = Math.max(res,(j-i)*height[i]);
                i++;
            }
        }   
        return res;
    }
}


12.整数转罗马数字

解法一:参考别人的解法

public class Solution {

    public String intToRoman(int num) {
        // 把阿拉伯数字与罗马数字可能出现的所有情况和对应关系，放在两个数组中
        // 并且按照阿拉伯数字的大小降序排列，这是贪心选择思想
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        while (index < 13) {
            // 特别注意：这里是等号
            while (num >= nums[index]) {
                // 注意：这里是等于号，表示尽量使用大的"面值"
                stringBuilder.append(romans[index]);
                num -= nums[index];
            }
            index++;
        }
        return stringBuilder.toString();
    }
}

作者：liweiwei1419
链接：https://leetcode-cn.com/problems/integer-to-roman/solution/tan-xin-suan-fa-by-liweiwei1419/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


解法二:
class Solution {
    public String intToRoman(int num) {
        int[] nums ={1000,900,500,400,100,90,50,40,10,9,5,4,1};			//设置一个数组,里面一共有13个数字
        String [] roman = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};			//设置对应的罗马符号,与数组中的数字一一对应

        StringBuffer s = new StringBuffer();			//创建一个可变字符串s
        int index = 0;
        while(index < 13){								
            while(num >= nums[index]){					//每次先取较大的数字对应的罗马符号加入,再取第二大的数字
                s.append(roman[index]);
                num = num -nums[index];
            }
            index ++;
        }
        return s.toString();							//最后别忘了将变长字符串s转换为字符;
    }
}


13.罗马数字转整数

解题思路:
设置一个hashmap,罗马字符作为键,值为对应的数字,遍历字符串,如果字符串中连续两个字符在hashmap中,那么就取出对应的值进行累加,如果字符串中连续两个字符不在
hashmap中,那么就说明这连续两个字符不是一个数字,而是每个字符代表一个数字,然后进行累加,求最终的和;

class Solution {
    public int romanToInt(String s) {
        Map<String,Integer> m = new HashMap<String,Integer>();
        m.put("I",1);
        m.put("IV",4);
        m.put("V",5);
        m.put("IX",9);
        m.put("X",10);
        m.put("XL",40);
        m.put("L",50);
        m.put("XC",90);
        m.put("C",100);
        m.put("CD",400);
        m.put("D",500);
        m.put("CM",900);
        m.put("M",1000);
        
        int num =0;
        for(int i =0;i<s.length();){
            if(i + 1 < s.length() && m.containsKey(s.substring(i,i+2))){
                num += m.get(s.substring(i,i+2));
                i+=2;
            }else{
                num += m.get(s.substring(i,i+1));
                i+=1;
            }
        }
        return num;
    }
}

14.最长公共前缀
解题思路:先拿第一个字符串和第二个字符串找出最大是前缀,三个字符串的最大公共前缀不可能比这个还要大,找出了两个最大公共前缀以后,再和第三个字符串找公共前缀
然后截取最大的前缀区间

class Solution {
    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0) 
            return "";
        String ans = strs[0];
        for(int i =1;i<strs.length;i++) {
            int j=0;
            for(;j<ans.length() && j < strs[i].length();j++) {
                if(ans.charAt(j) != strs[i].charAt(j))
                    break;
            }
            ans = ans.substring(0, j);
            if(ans.equals(""))
                return ans;
        }
        return ans;
    }
}

作者：guanpengchn
链接：https://leetcode-cn.com/problems/longest-common-prefix/solution/hua-jie-suan-fa-14-zui-chang-gong-gong-qian-zhui-b/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

15.三数之和(不太懂)

