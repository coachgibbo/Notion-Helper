# Notion-Helper
Personalized scripts and Android widgets to improve my Notion experience

## Features
1. A main UI that displays two tabs, commands and scripts. These list the functionality available to the user and are dynamically populated by ItemFactory.
   
   <img src=github/main-ui.png height=200px>
2. A UI template for Items that displays basic metadata and a run button. ItemFragment's can be added to these to allow for request parameters and responses.

   <img src=github/item-ui.png height=200px>
   <img src=github/item-ui3.png height=200px>
   <img src=github/item-ui2.png height=200px>
3. Items can be either commands or scripts. Commands contain a run method that defines functionality, generally a simple interaction with the Notion API.
4. Scripts consist of stages, and each stage can be programmed to contain one API call or other action. Each stage is run individually and the UI is updated with the completion status of each stage.
5. A widget that contains some key Notion functionality. It updates to show the next task in a database based on some provided filters and allows for quick adding and completing of tasks in a Notion database


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


