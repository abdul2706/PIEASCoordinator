//package com.example.pieascoordinator.testingdata;
//
//import com.example.pieascoordinator.datatypes.PostGroup;
//import com.example.pieascoordinator.datatypes.User;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class OurDatabase {
//
//    private static String[] bscs16 = {"BSCS1601", "BSCS1602", "BSCS1603"};
//    private static String[] bscs17 = {"BSCS1701", "BSCS1702", "BSCS1703"};
//    private static String[] bscs18 = {"BSCS1801", "BSCS1802", "BSCS1803"};
//    private static String[][] bscs = {bscs16, bscs17, bscs18};
//    private static String[] bscs16Passwords = {"CS1601", "CS1602", "CS1603"};
//    private static String[] bscs17Passwords = {"CS1701", "CS1702", "CS1703"};
//    private static String[] bscs18Passwords = {"CS1801", "CS1802", "CS1803"};
//    private static String[][] bscsPasswords = {bscs16Passwords, bscs17Passwords, bscs18Passwords};
//    private static String[] teachers = {"Sir Nauman Shamim", "Dr. Javaid Khursheed", "Dr. Tauseef Jamal", "Dr. Anila Usman", "Dr. Aneela Zameer", "Dr. Shahzad Ahmed Qureshi", "Dr. Kamraan Safdar", "Dr. Umar Faiz", "Sir Qazi Iqbal", "Dr. Abdul Basit", "Sir Irfan Hamid"};
//    private static String[] teachersPassword = {"Nauman", "Javaid", "Tauseef", "Anila", "Aneela", "Shahzad", "Kamraan", "Umar", "Qazi", "Abdul", "Irfan"};
//
//    private static PostGroup CFP = new PostGroup("CFP", "Sir Nauman Shamim", "123");
//    private static PostGroup CAG = new PostGroup("CAG", "Dr. Anila Usman", "123");
//    private static PostGroup LA = new PostGroup("LA", "Dr. Aneela Zameer", "123");
//    private static PostGroup CG = new PostGroup("CG", "Sir Qazi Iqbal", "123");
//    private static PostGroup OOP = new PostGroup("OOP", "Sir Nauman Shamim", "123");
//    private static PostGroup CS = new PostGroup("CS", "Sir Qazi Iqbal", "123");
//    private static PostGroup DM = new PostGroup("DM", "Dr. Abdul Basit", "123");
//    private static PostGroup DLD = new PostGroup("DLD", "Dr. Javaid Khursheed", "123");
//    private static PostGroup DSA = new PostGroup("DSA", "Sir Nauman Shamim", "123");
//    private static PostGroup OOAD = new PostGroup("OOAD", "Dr. Kamraan Safdar", "123");
//    private static PostGroup PAS = new PostGroup("PAS", "Dr. Shahzad Ahmed Qureshi", "123");
//    private static PostGroup DCN = new PostGroup("DCN", "Dr. Tauseef Jamal", "123");
//    private static PostGroup DE = new PostGroup("DE", "Dr. Aneela Zameer", "123");
//    private static PostGroup AA = new PostGroup("AA", "Dr. Kamraan Safdar", "123");
//    private static PostGroup MI = new PostGroup("MI", "Dr. Tauseef Jamal", "123");
//    private static PostGroup OS = new PostGroup("OS", "Dr. Anila Usman", "123");
//    private static PostGroup SE = new PostGroup("SE", "Sir Irfan Hamid", "123");
//    private static PostGroup TW = new PostGroup("TW", "Dr. Umar Faiz", "123");
//
//    private static PostGroup announcements = new PostGroup("Announcements", "Sir Nauman Shamim", "000");
//    private static PostGroup discussion = new PostGroup("Discussion", "Sir Nauman Shamim", "000");
//    private static PostGroup news = new PostGroup("News", "Sir Nauman Shamim", "000");
//    private static PostGroup notification = new PostGroup("Notification", "Sir Nauman Shamim", "000");
//    private static PostGroup internship = new PostGroup("Internship", "Sir Nauman Shamim", "000");
//    private static PostGroup FYP = new PostGroup("FYP", "Sir Nauman Shamim", "000");
//
//
//    //    public static PostGroup[] subjectsArray = {CFP, CAG, LA, CG, OOP, CS, DM, DLD, DSA, OOAD, PAS, DCN, DE, AA, MI, OS, SE, TW};
//    public static PostGroup[] subjectsArray = {announcements, discussion, news, notification, internship, FYP};
//
//
////    public static User[] userTeachers = {
////            new User(teachers[0], new ArrayList<>(Arrays.asList(CFP, OOP, DSA)), true),
////            new User(teachers[1], new ArrayList<>(Arrays.asList(DLD)), true),
////            new User(teachers[2], new ArrayList<>(Arrays.asList(DCN, MI)), true),
////            new User(teachers[3], new ArrayList<>(Arrays.asList(CAG, OS)), true),
////            new User(teachers[4], new ArrayList<>(Arrays.asList(LA, DE)), true),
////            new User(teachers[5], new ArrayList<>(Arrays.asList(PAS)), true),
////            new User(teachers[6], new ArrayList<>(Arrays.asList(OOAD, AA)), true),
////            new User(teachers[7], new ArrayList<>(Arrays.asList(TW)), true),
////            new User(teachers[8], new ArrayList<>(Arrays.asList(CG, CS)), true),
////            new User(teachers[9], new ArrayList<>(Arrays.asList(DM)), true),
////            new User(teachers[10], new ArrayList<>(Arrays.asList(SE)), true)
////    };
//
//    public static User[] userTeachers = {
//            new User(teachers[0], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification, internship, FYP)), true)
//    };
//
////    public static User[] userStudents = {
////            new User(bscs16[0], new ArrayList<>(Arrays.asList(OOP, DM, CS, DLD, DCN, OOAD)), false),
////            new User(bscs16[1], new ArrayList<>(Arrays.asList(OOP, DM, CS, DLD, DCN, DSA, OOAD)), false),
////            new User(bscs16[2], new ArrayList<>(Arrays.asList(OOP, DM, CS, DLD, DSA, OOAD)), false),
////            new User(bscs17[0], new ArrayList<>(Arrays.asList(DE, OS, AA, DCN, MI, TW, SE)), false),
////            new User(bscs17[1], new ArrayList<>(Arrays.asList(DE, OS, DCN, AA, MI, TW, SE)), false),
////            new User(bscs17[2], new ArrayList<>(Arrays.asList(DE, OS, AA, MI, TW)), false),
////            new User(bscs18[0], new ArrayList<>(Arrays.asList(CFP, CAG, LA, CG, PAS, DCN, DE)), false),
////            new User(bscs18[1], new ArrayList<>(Arrays.asList(CFP, CAG, LA, CG, PAS, DCN, DE)), false),
////            new User(bscs18[2], new ArrayList<>(Arrays.asList(CFP, CAG, LA, CG, PAS, DCN, DE)), false)
////    };
//
//    public static User[][] userStudents = {
//            {new User(bscs16[0], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification, internship, FYP)), false),
//                    new User(bscs16[1], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification, internship, FYP)), false),
//                    new User(bscs16[2], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification, internship, FYP)), false),
//            },
//            {new User(bscs17[0], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification, internship)), false),
//                    new User(bscs17[1], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification, internship)), false),
//                    new User(bscs17[2], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification, internship)), false),
//            },
//            {new User(bscs18[0], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification)), false),
//                    new User(bscs18[1], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification)), false),
//                    new User(bscs18[2], new ArrayList<>(Arrays.asList(announcements, discussion, news, notification)), false)
//            }
//    };
//
//    public static String getInstructorNameOf(String subjectName) {
//        for (int i = 0; i < subjectsArray.length; i++) {
//            if (subjectsArray[i].getSubjectName().toLowerCase().equals(subjectName.toLowerCase())) {
//                return subjectsArray[i].getSubjectInstructor();
//            }
//        }
//        return "Instructor Name Not Found!";
//    }
//
//
//    public static void showAllTeachers() {
//        for (User user :
//                userTeachers) {
//            System.out.println(user);
//        }
//
//    }
//
////    public static void showAllStudents() {
////        for (User user :
////                userStudents) {
////            System.out.println(user);
////        }
////    }
//
//    public static int[] getIndexOfStudent(String userName) {
//        for (int i = 0; i < bscs.length; i++) {
//            for (int j = 0; j < bscs[i].length; j++) {
//                if (bscs[i][j].toLowerCase().equals(userName.toLowerCase())) {
//                    return new int[]{i, j};
//                }
//            }
//        }
//        return new int[]{-1, -1};
//    }
//
//    public static String getPasswordOfStudent(int i, int j) {
//        if (i < 0 || j < 0) {
//            return "INVALID_INDEX";
//        } else {
//            return bscsPasswords[i][j];
//        }
//    }
//
//    public static String getPasswordOfStudent(int[] i) {
//        return getPasswordOfStudent(i[0], i[1]);
//    }
//
//    public static int getIndexOfTeacher(String userName) {
//        for (int i = 0; i < teachers.length; i++) {
//            if (teachers[i].toLowerCase().equals(userName.toLowerCase())) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    public static String getPasswordOfTeacher(int i) {
//        if (i < 0) {
//            return "INVALID_INDEX";
//        } else {
//            return teachersPassword[i];
//        }
//    }
//
//    public static boolean authenticateStudent(String userName, String password) {
//        return getPasswordOfStudent(getIndexOfStudent(userName)).toLowerCase().equals(password.toLowerCase()) || password.equals("11");
//    }
//
//    public static boolean authenticateTeacher(String userName, String password) {
//        return getPasswordOfTeacher(getIndexOfTeacher(userName)).toLowerCase().equals(password.toLowerCase()) || password.equals("11");
//    }
//
//    public static boolean isTeacher(String input) {
//        for (int i = 0; i < teachers.length; i++) {
//            if (input.toLowerCase().equals(teachers[i].toLowerCase())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static boolean isStudent(String input) {
//        for (int i = 0; i < bscs.length; i++) {
//            for (int j = 0; j < bscs[i].length; j++) {
//                if (input.toLowerCase().equals(bscs[i][j].toLowerCase())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public static String[] getSubjectsNamesOfThisTeacher(String input) {
//        for (int i = 0; i < teachers.length; i++) {
//            if (input.toLowerCase().equals(teachers[i].toLowerCase())) {
//                return userTeachers[i].getSubjectNamesAsArray();
//            }
//        }
//        return new String[]{"No PostGroup Found"};
//    }
//
//    public static String[] getSubjectsNamesOfThisStudent(String input) {
//        for (int i = 0; i < bscs.length; i++) {
//            for (int j = 0; j < bscs[i].length; j++) {
//                if (input.toLowerCase().equals(bscs[i][j].toLowerCase())) {
//                    return userStudents[i][j].getSubjectNamesAsArray();
//                }
//            }
//        }
//        return new String[]{"No PostGroup Found"};
//    }
//
//    public static String getBatchOfThisStudent(String studentName) {
//        int temp[] = getIndexOfStudent(studentName);
//        if (temp[0] == 0) {
//            return "BSCS 16-20";
//        } else if (temp[0] == 1) {
//            return "BSCS 17-21";
//        } else if (temp[0] == 2) {
//            return "BSCS 18-22";
//        }
//        return "Batch Not Found";
//    }
//
//    public static String[] getSubjectsNamesOfThisUser(String userName) {
//        if (isStudent(userName)) return getSubjectsNamesOfThisStudent(userName);
//        else if (isTeacher(userName)) return getSubjectsNamesOfThisTeacher(userName);
//        else return new String[]{"User not Found in Database"};
//    }
//
//}
