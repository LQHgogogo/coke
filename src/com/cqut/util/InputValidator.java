package com.cqut.util;

import com.cqut.domain.User;
import java.util.ArrayList;
import java.util.Scanner;

public class InputValidator
{

    private static final int MAX_RETRY = 3;

    public static int validateMenuChoice(Scanner sc, int min, int max)
    {
        int retryCount = 0;
        while (retryCount < MAX_RETRY)
        {
            if (sc.hasNextInt())
            {
                int choice = sc.nextInt();
                if (choice >= min && choice <= max)
                {
                    return choice;
                }
                else
                {
                    System.out.println("输入超出范围，请输入 " + min + "-" + max + " 之间的数字：");
                }
            }
            else
            {
                System.out.println("请输入数字：");
                sc.next();
            }
            retryCount++;
        }
        System.out.println("输入错误次数过多，默认选择：" + min);
        return min;
    }

    public static String validateUsername(Scanner sc, ArrayList<User> userList)
    {
        while (true)
        {
            System.out.println("请输入用户名：");
            String username = sc.next();

            if (!checkLength(username, 3, 16))
            {
                System.out.println("用户名长度必须在3-16之间");
                continue;
            }

            if (!checkUsernameFormat(username))
            {
                System.out.println("用户名必须包含字母，只能包含字母和数字");
                continue;
            }

            if (containsUser(userList, username))
            {
                System.out.println("用户名已存在，请重新输入");
                continue;
            }

            return username;
        }
    }

    public static String validatePassword(Scanner sc)
    {
        while (true)
        {
            System.out.println("请输入密码：");
            String password1 = sc.next();
            System.out.println("请再次输入密码：");
            String password2 = sc.next();

            if (!checkLength(password1, 3, 8))
            {
                System.out.println("密码长度必须在3-8之间");
                continue;
            }

            if (!checkPasswordFormat(password1))
            {
                System.out.println("密码必须同时包含字母和数字，不能包含特殊字符");
                continue;
            }

            if (!password1.equals(password2))
            {
                System.out.println("两次输入的密码不一致");
                continue;
            }

            return password1;
        }
    }

    public static int validateAttributePoint(Scanner sc, int remainingPoints)
    {
        if (remainingPoints == 0)
        {
            System.out.println("剩余点数为0，自动分配0点");
            return 0;
        }

        while (true)
        {
            if (sc.hasNextInt())
            {
                int input = sc.nextInt();

                if (input < 0)
                {
                    System.out.println("无效输入，自动设为0");
                    return 0;
                }

                if (input > remainingPoints)
                {
                    System.out.println("属性点不足，自动分配所有剩余点数：" + remainingPoints);
                    return remainingPoints;
                }

                return input;
            }
            else
            {
                System.out.println("请输入数字：");
                sc.next();
            }
        }
    }

    public static String validateYesNo(Scanner sc)
    {
        int retryCount = 0;
        while (retryCount < MAX_RETRY)
        {
            String input = sc.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("N"))
            {
                return input;
            }
            else
            {
                System.out.println("无效输入，请输入 Y 或 N：");
            }
            retryCount++;
        }
        System.out.println("输入错误次数过多，默认选择：N");
        return "N";
    }

    public static int validateRoomChoice(Scanner sc, int roomCount)
    {
        int retryCount = 0;
        while (retryCount < MAX_RETRY)
        {
            if (sc.hasNextInt())
            {
                int choice = sc.nextInt();
                if (choice >= 0 && choice <= roomCount)
                {
                    return choice;
                }
                else
                {
                    System.out.println("输入超出范围，请输入 0-" + roomCount + " 之间的数字：");
                }
            }
            else
            {
                System.out.println("请输入数字：");
                sc.next();
            }
            retryCount++;
        }
        System.out.println("输入错误次数过多，默认返回");
        return 0;
    }

    public static int validateSkillChoice(Scanner sc, int skillCount)
    {
        while (true)
        {
            if (sc.hasNextInt())
            {
                int input = sc.nextInt();
                if (input >= 1 && input <= skillCount)
                {
                    return input - 1;
                }
                else
                {
                    System.out.println("无效技能编号，请输入 1-" + skillCount + " 之间的数字：");
                }
            }
            else
            {
                System.out.println("请输入数字：");
                sc.next();
            }
        }
    }

    private static int[] countCharTypes(String input)
    {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;

        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (Character.isDigit(c))
            {
                numCount++;
            }
            else if (Character.isLetter(c))
            {
                charCount++;
            }
            else
            {
                otherCount++;
            }
        }

        return new int[]{charCount, numCount, otherCount};
    }

    private static boolean checkLength(String input, int min, int max)
    {
        return input.length() >= min && input.length() <= max;
    }

    private static boolean checkUsernameFormat(String username)
    {
        int[] counts = countCharTypes(username);
        return counts[0] > 0 && counts[1] >= 0 && counts[2] == 0;
    }

    private static boolean checkPasswordFormat(String password)
    {
        int[] counts = countCharTypes(password);
        return counts[0] > 0 && counts[1] > 0 && counts[2] == 0;
    }

    private static boolean containsUser(ArrayList<User> userList, String username)
    {
        if (userList == null)
        {
            return false;
        }

        for (User user : userList)
        {
            if (user.getUsername().equals(username))
            {
                return true;
            }
        }

        return false;
    }
}
