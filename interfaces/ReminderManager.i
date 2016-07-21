HEADER_FILE := reminders\ReminderManager.h
PARENT_FILE := ItemManager.h := true

FUNCTION_IGNORE :=	Reminder* getFromQuery(SQLite::Statement& query);

MEMOWN := static ReminderManager* getInstance();

IMPORT_HEADER := headers\ReminderWrapper.h
IMPORT_HEADER := headers\ContentValuesWrapper.h
IMPORT_HEADER := headers\ReminderDataHolderWrapper.h
IMPORT_HEADER := headers\ReminderFilterWrapper.h

IMPORT_SOURCE := StringUtils.h