# Notion-Helper
Personalized scripts and Android widgets to improve my Notion experience

## How to use
In Constants.java, the following variables should be filled out:

    BEARER: Your Notion generated secret
  
In NotionObjectIds.java, the following variables should be filled out:

    DAILY_TASK_DATABASE: The Notion ID of your daily task database

    DAILY_TASK_TEMPLATE_X: The Notion ID's of the pages you want updated by Update Daily Tasks
    
Clone the repository and open in Android Studio. For short-term use just pair device with AS and run.

For long-term use go Build -> Build Bundle(s) / APK(s) -> Build APK(s) and download the generated APK on your device.

### How to add an Item
1. Create a new class that extends Item in /model/items
2. Override the runItem(ItemFragment fragment, ImageView responseGif) method with the Item functionality.
3. Update ItemFactory to create the new Item based on its id. Also remember to update the relevant Command or Script methods.
4. The Item will be created with the default UI. A new class that extends ItemFragment can be created in /view/fragments to provide a unique item UI.

## To-do List
1. Add functionality to UpdateDailyTasksItem *DONE*
   
   a. Each template page will be PATCHED (Update Page) so that it's reset to date = today and status = 'not started' *DONE*
      
        i. Create new API for Update Page *DONE*
      
        ii. Store template page ID's somewhere *DONE*
      
        iii. Create logic for doing the reset *DONE*
2. Create widget for AddDailyTaskItem
5. Create ViewNextDailyTaskItem that will display the task that has status="not started", date=today and order=min(tasks)
6. Create widget for ViewNextDailyTaskItem
7. Add a habit tracker command and perhaps widget
8. Add a script-running widget
10. Clean up code smells and create an architecture diagram
11. Add clean up database command
