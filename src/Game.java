//import map.Cell;
//import map.FinalCell;
//import map.WorldMap;
//import role.Master;
//import role.Role;
//import role.Warrior;
//
//import java.util.Scanner;
//
//public class Game {
//    static String aa;// 姓名
//    private Role role;
//    private int width = 10;
//    private int height = 10;
//    private Cell currCell;
//    private WorldMap worldMap = new WorldMap(width, height);
//    Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) {
//        Game game = new Game();
//        game.createRole();
//        game.run();
//    }
//
//
//    public void createRole() {
//        System.out.println("""
//                 ________       _________    ________      ________      _________        ________      ________      _____ ______       _______     \s
//                |\\   ____\\     |\\___   ___\\ |\\   __  \\    |\\   __  \\    |\\___   ___\\     |\\   ____\\    |\\   __  \\    |\\   _ \\  _   \\    |\\  ___ \\    \s
//                \\ \\  \\___|_    \\|___ \\  \\_| \\ \\  \\|\\  \\   \\ \\  \\|\\  \\   \\|___ \\  \\_|     \\ \\  \\___|    \\ \\  \\|\\  \\   \\ \\  \\\\\\__\\ \\  \\   \\ \\   __/|   \s
//                 \\ \\_____  \\        \\ \\  \\   \\ \\   __  \\   \\ \\   _  _\\       \\ \\  \\       \\ \\  \\  ___   \\ \\   __  \\   \\ \\  \\\\|__| \\  \\   \\ \\  \\_|/__ \s
//                  \\|____|\\  \\        \\ \\  \\   \\ \\  \\ \\  \\   \\ \\  \\\\  \\|       \\ \\  \\       \\ \\  \\|\\  \\   \\ \\  \\ \\  \\   \\ \\  \\    \\ \\  \\   \\ \\  \\_|\\ \\\s
//                    ____\\_\\  \\        \\ \\__\\   \\ \\__\\ \\__\\   \\ \\__\\\\ _\\        \\ \\__\\       \\ \\_______\\   \\ \\__\\ \\__\\   \\ \\__\\    \\ \\__\\   \\ \\_______\\
//                   |\\_________\\        \\|__|    \\|__|\\|__|    \\|__|\\|__|        \\|__|        \\|_______|    \\|__|\\|__|    \\|__|     \\|__|    \\|_______|
//                   \\|_________|                                                                                                                      \s
//                                                                                                                                                     \s
//                                                                                                                                                     \s""");
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("欢迎来到小雨游戏世界,现在,勇士,您该开启您的冒险啦,祝您旅程愉快!");
//        System.out.print("请选择职业(1-战士 2-法师):");
//        int ss = scanner.nextInt();
//        System.out.print("请输入角色姓名:");
//        aa = scanner.next();
//        if (ss == 1) {
//            role = new Warrior(aa);
//        } else {
//            role = new Master(aa);
//        }
//
//    }
//
//    private void showMap() {
//        System.out.println("\n************ 世界地图 ************");
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                Cell cell = worldMap.getCell(j, i);
//                if (!cell.isExplored()) {
//                    System.out.print(" ? ");
//                } else if (cell instanceof FinalCell) {
//                    System.out.print(" $ ");
//                } else if (cell == currCell) {
//                    System.out.print(" P ");
//                } else if (cell.getEvent() != null) {
//                    System.out.print(String.format(" %s ", cell.getEvent().flag()));
//                } else {
//                    System.out.print(" _ ");
//                }
//            }
//            System.out.println();
//        }
//        System.out.println("");
//    }
//
//    private void run() {
//        currCell = worldMap.randBirthCell();
//
//        while (true) {
//            System.out.print("输入wasd选择前进方向：");
//            System.out.print("输入N为个人属性面板,0为世界地图");
//            char index = scanner.next().charAt(0);
//            if (index == '0') {
//                showMap();
//                continue;
//            } else if (index == 'n') {
//                showRoleInfo();
//                continue;
//            }
//            forward(index);
//
//        }
//    }
//
//    /**
//     * 前进
//     */
//    private void forward(char direction) {
////		Random r = new Random();
////		int dot = r.nextInt(diceMaxDot - 1) + 1;
////		System.out.println("骰子摇了 " + dot + "点");
//        int dot = 1;
//        Cell cell = switch (direction + 0) {
//            case 119 -> {
//                // 上
//                // 边界判断
//                System.out.println("向【上】前进 " + dot + "步");
//                yield worldMap.action(currCell.getX(), currCell.getY() - dot);
//            }
//            case 100 -> {
//                // 右
//                System.out.println("向【右】前进 " + dot + "步");
//                yield worldMap.action(currCell.getX() + dot, currCell.getY());
//            }
//            case 115 -> {
//                // 下
//                System.out.println("向【下】前进 " + dot + "步");
//                yield worldMap.action(currCell.getX(), currCell.getY() + dot);
//            }
//            case 97 -> {
//                // 左
//                System.out.println("向【左】前进 " + dot + "步");
//                yield worldMap.action(currCell.getX() - dot, currCell.getY());
//            }
//            default -> {
//                System.out.println("没有向任何方向前进");
//                yield null;
//            }
//        };
//
//        if (cell == currCell) {
//            System.err.println("\n*** 似乎移动到了世界的边缘 ***\n");
//
//        }
//        if (cell != null) {
//            cell.setExplored(true);
//            currCell = cell;
//            if (cell.getEvent() != null && !cell.getEvent().isTriggered()) {
//                System.out.println("发生了事件，按回车键继续...");
//                scanner.nextLine();
//                scanner.nextLine();
//                cell.getEvent().trigger(role);
//            } else {
//                System.out.println("风平浪静，什么事情也没有发生。\n");
//            }
//        }
//        System.out.println(String.format("目前所在位置%d, %d", currCell.getX(), currCell.getY()));
//
//        if (currCell instanceof FinalCell) {
//            showMap();
//            System.out.println(String.format("你找到了宝藏，你用聪明的智慧，超凡的勇气，赢得了最终的胜利，祝福你%s", role.getName()));
//            System.out.println("游戏结束。");
//            System.exit(0);
//        }
//    }
//
//    private void showRoleInfo() {
//        System.out.println("\n************ 角色信息 ************");
//        System.out.println("昵称：" + role.getName());
//        System.out.println("血量：" + role.getHp());
//        System.out.println("力量：" + role.getPower());
//        System.out.println("法术：" + role.getMagic());
//        System.out.println();
//    }
//
//
//}
