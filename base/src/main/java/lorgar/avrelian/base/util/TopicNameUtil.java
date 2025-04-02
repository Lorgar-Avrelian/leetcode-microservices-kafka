package lorgar.avrelian.base.util;

public class TopicNameUtil {
    public static String formatName(int taskId) {
        String taskName = taskId + "";
        StringBuilder sb = new StringBuilder(taskName);
        if (taskName.length() < 4) {
            for (int i = 0; i < 4 - taskName.length(); i++) {
                sb.insert(0, "0");
            }
        }
        return "task-" + sb;
    }
}
