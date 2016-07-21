HEADER_FILE := reminders\Reminder.h
PARENT_FILE := Item.h


FUNCTION_IGNORE := bool operator==(const Items::Item& item);


RENAME_FUNC : operator== : equals
RENAME_FUNC : operator> : isAfter
RENAME_FUNC : operator< : isBefore

IMPORT_HEADER := headers\LocalTimeWrapper.h
IMPORT_HEADER := headers\ContentValuesWrapper.h

IMPORT_SOURCE := IntUtils.h
IMPORT_SOURCE := StringUtils.h