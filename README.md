# Notion-Helper
Personalized scripts and widgets to improve my Notion experience

## How to use
The app can be cloned and ran via Android Studio.

In Constants.java, the following variables should be filled out:
  
    DAILY_TASK_DATABASE: The Notion ID of your daily task database
  
    BEARER: Your Notion generated secret
  
Once I've completed the functionality I'll move it onto AWS for 24/7 uptime.

## To-do List
1. Rename "Tasks" to "Commands"
2. Add functionality to UpdateDailyTasksItem
3. Create widget for AddDailyTaskItem
4. Change Item.runItem() to take and return a DataCallback object so that the layouts can do stuff with response data
5. Move the parameter section of activity_item.xml into a separate layout, so that this layout can be reused for different items
6. Create ViewNextDailyTaskItem that will display the task that has status="not started", date=today and order=min(tasks)
7. Create widget for ViewNextDailyTaskItem
