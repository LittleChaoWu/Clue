package com.sfb.baselib.route.activityroute;

/**
 * 任务模块的Activity路由
 */
public interface TaskActivityRoute {

    String TASK_PATH = "/task/TaskActivity";

    String TASK_DETAIL_PATH = "/task/TaskDetailActivity";

    String RECEIVE_TASK_PATH = "/task/ReceiveTaskActivity";

    String TASK_ABORT_PATH = "/task/TaskAbortActivity";

    String TASK_EXECUTE_PATH = "/task/TaskExecuteActivity";

    String ONLINE_EXECUTOR_PATH = "/task/OnlineExecutorActivity";

    String TASK_REPORT_PATH = "/task/TaskReportActivity";

    String TASK_FILE_PATH = "/task/TaskFileActivity";

    String POLICE_TASK_PATH = "/task/PoliceTaskActivity";

    String POLICE_TASK_EXECUTE_DIALOG_PATH = "/task/PoliceTaskExecuteDialogActivity";

    String POLICE_TASK_EXECUTE_PATH = "/task/PoliceTaskExecuteActivity";

    String ADD_TASK_PATH = "/task/AddTaskActivity";

    String PICK_TASK_RECEIVER_PATH = "/task/PickTaskReceiverActivity";

    String ALLOT_PERSON_PATH = "/task/AllotPersonActivity";

    String TASK_RECEIVER_PATH = "/task/TaskReceiverActivity";

    String TEMPLATE_PATH = "/task/TemplateActivity";

    String TASK_ADD_CLUE_PATH = "/task/TaskAddClueActivity";

    String TASK_CLUE_DETAIL_PATH = "/task/TaskClueDetailActivity";

    String TASK_CAR_COLLECT_PATH = "/task/TaskCarCollectActivity";

    String TASK_CAR_DETAIL_PATH = "/task/TaskCarDetailActivity";

    String TASK_MAP_PATH = "/task/TaskMapActivity";

    String TASK_PERSON_COLLECT_PATH = "/task/TaskPersonCollectActivity";

    String TASK_PERSON_COLLECT_DETAIL_PATH = "/task/TaskPersonCollectDetailActivity";

    interface TaskActivityKey{
        String TASK_ID_KEY = "taskId";
        String TASK_RECEIVE_CLASS_NAME = "com.sfb.tasklib.task.ReceiveTaskActivity";
        String TASK_NOTIFICATION_ACTION = "com.sfb.baselib.notification.action";
    }
}
