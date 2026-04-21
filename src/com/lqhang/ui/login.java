package com.lqhang.ui;

import com.lqhang.domain.User;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class login {
    public void start() {
        System.out.println("打开了登录注册页面");

        ArrayList<User> list = new ArrayList<User>();

        while (true) {
            System.out.println("============================");
            System.out.println("=     欢迎来到文字格斗游戏     =");
            System.out.println("============================");
            System.out.println("请选择操作：1登录 2注册 3退出");

            Scanner sc = new Scanner(System.in);
            String choice = sc.next();

            switch (choice){
                case"1"->login(list);
                case"2"->register(list);
                case"3"-> {
                    System.out.println("用户选择了退出操作");
                    System.exit(0);      //停止虚拟机
                }
                default -> System.out.println("输入了无效的操作");
            }
        }
    }


    public void login(ArrayList<User> list){
        System.out.println("用户选择了登录操作");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();
        if (!contains(list,username)){
            System.out.println("用户名不存在");
            return;
        }
        int index = findIndex(list,username);
        User u = list.get(index);
        if (!u.isStatus()){
            System.out.println("用户"+ u.getUsername()+"已被禁用");
            return;
        }

        String rightPassword = u.getPassword();

        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码：");
            String password = sc.next();
            while (true) {
                System.out.println("请输入验证码：");
                String rightCode = grtCode();
                System.out.println("正确验证码是："+rightCode);
                String code = sc.next();
                if (code.equalsIgnoreCase(rightCode)){
                    System.out.println("验证码输入正确");
                    break;
                }else {
                    System.out.println("验证码输入错误");
                    continue;
                }
            }
            if (password.equals(rightPassword)){
                System.out.println("用户"+u.getUsername()+"登录成功！游戏启动");
                TextGame tg=new TextGame();
                tg.start(username);
                break;
            }else {
                System.out.println("用户"+u.getUsername()+"登录失败");
                if (i == 2){
                    u.setStatus(false);
                    System.out.println("当前用户已被锁定");
                    return;
                }
                else {
                    System.out.println("密码错误，你还有"+(2-i)+"次机会");
                }
            }
        }
    }

    public void register(ArrayList< User>  list){
        System.out.println("用户选择了注册操作");

        User u = new User();
        Scanner sc = new Scanner(System.in);
        //ctrl+alt+T  语句包裹
        while (true) {
            System.out.println("请输入用户名：");
            String username = sc.next();
            //验证用户名是否合法
            if (!checklen(username,3,16)) {
                System.out.println("用户名长度必须在3-16之间");
                continue;
            }
            if (!checkUsername(username)) {
                System.out.println("用户名只能且必须包含字母和数字");
                continue;
            }
            if (contains(list,username)){
                System.out.println("用户名已存在,请重新输入");
                continue;
            }
            u.setUsername(username);
            break;
        }   //输入用户名

        while (true) {
            System.out.println("请输入密码：");
            String password1 = sc.next();
            System.out.println("请再次输入密码：");
            String password2 = sc.next();
            if (!checklen(password1,3,8)){
                System.out.println("密码长度必须在3-8之间");
                continue;
            }
            if (!checkPassword(password1)){
                System.out.println("密码只能且必须包含字母和数字");
                continue;
            }
            if (!password1.equals(password2)){
                System.out.println("两次输入的密码不一致");
                continue;
            }
            u.setPassword(password1);
            break;
        }   //输入密码
        list.add(u);
        System.out.println("用户"+u.getUsername()+"注册成功！");

    }

    public int findIndex(ArrayList<User> list,String username){
        for (int i = 0; i < list.size(); i++){
            User u = list.get(i);
            if (u.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkPassword(String password){
        int[] counts = getcount(password);
        return counts[0] > 0 && counts[1] > 0 && counts[2] == 0;
    }

    public static String grtCode(){
        ArrayList<Character> list = new ArrayList<Character>();
        for (int i = 0; i < 26; i++){
            list.add((char)('a' + i));
            list.add((char)('A' + i));
        }

        Random r=new Random();
        StringBuilder sb = new StringBuilder();
        int rnum = r.nextInt(5);
        for (int i = 0; i < 5; i++){
            if (i == rnum){
                sb.append((char)('0' + r.nextInt(10)));
            }else {
                int index = r.nextInt(list.size());
                char c = list.get(index);
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public int[] getcount(String userinfo){
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;
        for (int i = 0; i < userinfo.length(); i++) {
            char c = userinfo.charAt(i);
            if (Character.isDigit(c)) {
                numCount++;
            }
            else if (Character.isLetter(c)) {
                charCount++;
            }
            else {
                otherCount++;
            }
        }
        return new int[]{charCount,numCount,otherCount};
    }

    public boolean contains(ArrayList<User> list,String username){
        for (int i = 0; i < list.size(); i++){
            User u = list.get(i);
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUsername(String username){
        int[] counts = getcount(username);
        return counts[0] > 0 && counts[1] >= 0 && counts[2] == 0;
    }

    public boolean checklen(String username,int min,int max){
        return username.length() >= min && username.length() <= max;
    }
}
